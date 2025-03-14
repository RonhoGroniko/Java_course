import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Session {
    private Movie movie;
    private Hall hall;
    private Date startTime;
    private List<Ticket> tickets;
    private Seat[][] seats; // Массив мест для этого сеанса

    public Session(Movie movie, Hall hall, Date startTime) {
        this.movie = movie;
        this.hall = hall;
        this.startTime = startTime;
        this.tickets = new ArrayList<>();
        this.seats = new Seat[hall.getRows()][hall.getCols()]; // Инициализируем места

        // Инициализируем места
        for (int i = 0; i < hall.getRows(); i++) {
            for (int j = 0; j < hall.getCols(); j++) {
                seats[i][j] = new Seat(i, j);
            }
        }
    }

    public Movie getMovie() {
        return movie;
    }

    public Hall getHall() {
        return hall;
    }

    public Date getStartTime() {
        return startTime;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public Seat[][] getSeats() {
        return seats;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public void printSeats() {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                System.out.print(seats[i][j].isOccupied() ? "[X]" : "[ ]");
            }
            System.out.println();
        }
    }
}