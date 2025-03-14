public class Ticket {
    private Session session;
    private Seat seat;

    public Ticket(Session session, Seat seat) {
        this.session = session;
        this.seat = seat;
        seat.book();
    }

    public Session getSession() {
        return session;
    }

    public Seat getSeat() {
        return seat;
    }
}