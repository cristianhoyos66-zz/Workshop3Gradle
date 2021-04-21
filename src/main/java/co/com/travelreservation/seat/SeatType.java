package co.com.travelreservation.seat;

public enum SeatType {
  PREMIUM("Premium"),
  ECONOMIC("Economic");

  private final String seatType;

  SeatType(String seatType) {
    this.seatType = seatType;
  }

  public String getValue() {
    return seatType;
  }
}
