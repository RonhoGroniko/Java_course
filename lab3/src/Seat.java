public class Seat {
    private int row;
    private int col;
    private boolean booked;

    public Seat(int row, int col) {
        this.row = row;
        this.col = col;
        this.booked = false;
    }

    public boolean isOccupied() {
        return booked;
    }

    public void book() {
        this.booked = true;
    }
}