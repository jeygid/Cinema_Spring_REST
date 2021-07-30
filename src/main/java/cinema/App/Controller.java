package cinema.App;

import cinema.DTO.ErrorResponse;
import cinema.DTO.ReturnedTicket;
import cinema.DTO.Statistics;
import cinema.DTO.TokenRequest;
import cinema.Entities.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class Controller {

    private Cinema cinema = new Cinema();
    private List<Ticket> soldTickets = new ArrayList<>();
    private Statistics stats = new Statistics();

    @GetMapping("/seats")
    public Cinema showSeats() {
        return cinema;
    }

    @PostMapping("/purchase")
    public ResponseEntity purchaseSeat(@RequestBody Seat purchasedSeat) {

        Seat seat = cinema.getAvailable_seats().stream()
                .filter(x -> x.getRow() == purchasedSeat.getRow())
                .filter(x -> x.getColumn() == purchasedSeat.getColumn())
                .findAny()
                .orElse(null);

        if (seat == null) {
            return new ResponseEntity(new ErrorResponse("The number of a row or a column is out of bounds!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (seat.isPurchased()) {
            return new ResponseEntity(new ErrorResponse("The ticket has been already purchased!"),
                    HttpStatus.BAD_REQUEST);
        } else {
            seat.setPurchased(true);
            UUID uuid = UUID.randomUUID();
            Ticket ticket = new Ticket(uuid, seat);
            soldTickets.add(ticket);
            stats.setCurrent_income(stats.getCurrent_income() + ticket.getTicket().getPrice());
            stats.setNumber_of_available_seats(stats.getNumber_of_available_seats() - 1);
            stats.setNumber_of_purchased_tickets(stats.getNumber_of_purchased_tickets() + 1);
            return new ResponseEntity(ticket, HttpStatus.OK);
        }
    }

    @PostMapping("/return")
    public ResponseEntity purchaseSeat(@RequestBody TokenRequest tokenRequest) {

        Ticket ticket = soldTickets.stream()
                .filter(x -> x.getToken().toString().equals(tokenRequest.getToken().toString()))
                .findAny()
                .orElse(null);

        if (ticket == null) {
            return new ResponseEntity(new ErrorResponse("Wrong token!"), HttpStatus.BAD_REQUEST);
        } else {
            ticket.getTicket().setPurchased(false);
            soldTickets.remove(ticket);
            stats.setCurrent_income(stats.getCurrent_income() - ticket.getTicket().getPrice());
            stats.setNumber_of_available_seats(stats.getNumber_of_available_seats() + 1);
            stats.setNumber_of_purchased_tickets(stats.getNumber_of_purchased_tickets() - 1);
            return new ResponseEntity(new ReturnedTicket(ticket.getTicket()), HttpStatus.OK);
        }
    }

    @PostMapping("/stats")
    public ResponseEntity getStats(@RequestParam(required = false) String password) {

            if ("super_secret".equals(password)) {
                return new ResponseEntity(stats, HttpStatus.OK);
            } else {
                return new ResponseEntity(new ErrorResponse("The password is wrong!"), HttpStatus.UNAUTHORIZED);
            }
    }



}


