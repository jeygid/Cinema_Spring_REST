package cinema.DTO;

import cinema.Entities.Seat;

public class ReturnedTicket {
    private Seat returned_ticket;

    public ReturnedTicket(Seat returned_ticket) {
        this.returned_ticket = returned_ticket;
    }

    public ReturnedTicket() {
    }

    public Seat getReturned_ticket() {
        return returned_ticket;
    }
}
