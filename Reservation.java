public class Reservation {
    private String reservationID;
    private int startTime; // represented in minutes or a simple integer format
    private int endTime;

    public Reservation(String id, int start, int end) {
        this.reservationID = id;
        this.startTime = start;
        this.endTime = end;
    }

    // Getter Methods
    public int getStart() { return startTime; }
    public int getEnd() { return endTime; }
    public String getID() { return reservationID; }
}