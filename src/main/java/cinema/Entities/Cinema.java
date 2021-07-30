package cinema.Entities;

import java.util.ArrayList;
import java.util.List;

public class Cinema  {
    
    private int total_rows = 9;
    private int total_columns = 9;
    private List<Seat> available_seats = new ArrayList<>();

    public Cinema() {

        for (int i = 1; i <= 9; i++) {

            for (int j = 1; j <= 9; j++) {

                int price;

                price = (i <= 4) ? 10 : 8;

                available_seats.add(new Seat(i,j, price));
            }
        }
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public List<Seat> getAvailable_seats() {
        return available_seats;
    }
}
