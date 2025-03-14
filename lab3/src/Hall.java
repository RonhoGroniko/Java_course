import java.util.*;

public class Hall {
    private String name;
    private int rows;
    private int cols;
    private Cinema cinema;

    public Hall(String name, int rows, int cols, Cinema cinema) {
        this.name = name;
        this.rows = rows;
        this.cols = cols;
        this.cinema = cinema;
    }

    public String getName() {
        return name;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Cinema getCinema() {
        return cinema;
    }
}
