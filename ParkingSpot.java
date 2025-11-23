class ParkingSpot {
    enum Status { AVAILABLE, RESERVED, OUT_OF_SERVICE }
    private final int spotId;
    private Status status;

    public ParkingSpot(int spotId, Status status) {
        this.spotId = spotId;
        this.status = status;
    }

    public int getSpotId() { return spotId; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}

