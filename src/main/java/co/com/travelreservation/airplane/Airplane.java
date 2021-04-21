package co.com.travelreservation.airplane;
import co.com.travelreservation.application.ColumnNamesAdapter;
import co.com.travelreservation.seat.Seat;
import co.com.travelreservation.seat.SeatType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class Airplane {
  private int amountOfEconomyRows = 7;
  private int amountOfEconomyColumns = 6;
  private int amountOfPremiumRows = 2;
  private int amountOfPremiumColumns = 4;
  private Seat[][] economySeats = new Seat[amountOfEconomyRows][amountOfEconomyColumns];
  private Seat[][] premiumSeats = new Seat[amountOfPremiumRows][amountOfPremiumColumns];
  private ColumnNamesAdapter columnNamesAdapter;

  public Airplane() {
    this.columnNamesAdapter = new ColumnNamesAdapter();
    addDefaultEconomySeats();
    addDefaultPremiumSeats();
  }
  @JsonIgnore
  public Seat[][] getLeftEconomySeats() {
    return getEconomySeatsSeatsByColumnRange(0, 3);
  }
  @JsonIgnore
  public Seat[][] getRightEconomySeats() {
    return getEconomySeatsSeatsByColumnRange(3, 6);
  }
  @JsonIgnore
  public Seat[][] getLeftPremiumSeats() {
    return getPremiumSeatsByColumnRange(0, 2);
  }
  @JsonIgnore
  public Seat[][] getRightPremiumSeats() {
    return getPremiumSeatsByColumnRange(2, 4);
  }
  @JsonIgnore
  public Seat setSeat(Seat seat) {
    if (SeatType.ECONOMIC == seat.getSeatType()) {
      economySeats[seat.getRow() - 1][columnNamesAdapter.getPositionByName(seat.getColumn()) - 1] = seat;
    } else {
      premiumSeats[seat.getRow() - 1][columnNamesAdapter.getPositionByName(seat.getColumn()) - 1] = seat;
    }
    return seat;
  }
  public List<Seat> passengerSearch(String dni) {
    return Stream.concat(searchByType(dni, economySeats).stream(), searchByType(dni, premiumSeats).stream())
        .collect(Collectors.toList());
  }
  @JsonIgnore
  public Double getBusyPercentage() {
    int total = getBusyPercentageByType(economySeats) + getBusyPercentageByType(premiumSeats);
    return total / 50.0 * 100.0;
  }
  public Seat isPremiumSmoking(int row, String column) {
    return premiumSeats[row - 1][columnNamesAdapter.getPositionByName(column) - 1];
  }
  @JsonIgnore
  public Seat getEconomicFreeSeatInWindow() {
    Seat seat = getEconomicFreeSeatInColumn(0);
    return seat != null ? seat : getEconomicFreeSeatInColumn(5);
  }
  @JsonIgnore
  private Seat getEconomicFreeSeatInColumn(int column) {
    for (int i = 0; i < economySeats.length; i++) {
      Seat windowSeat = economySeats[i][column];
      if (windowSeat.getPerson() == null) {
        return windowSeat;
      }
    }
    return null;
  }
  @JsonIgnore
  public SeatType getMostBusyClassInWindow() {
    int economicCount = getBusyCountInColumnByType(0, economySeats) + getBusyCountInColumnByType(5, economySeats);
    int premiumCount = getBusyCountInColumnByType(0, premiumSeats) + getBusyCountInColumnByType(3, premiumSeats);
    if (economicCount > premiumCount) {
      return SeatType.ECONOMIC;
    } else if (premiumCount > economicCount) {
      return SeatType.PREMIUM;
    } else {
      return null;
    }
  }
  @JsonIgnore
  public int getSmokingSeatsBtn() {
    return getSmokingSeatsAmountByType(premiumSeats) +
        getSmokingSeatsAmountByType(economySeats);
  }
  @JsonIgnore
  public int getEconomySmokingSeats() {
    return getSmokingSeatsAmountByType(economySeats);
  }
  @JsonIgnore
  public int getSmokingSeatsAmountByType(Seat[][] seats) {
    int count = 0;
    for (int i = 0; i < seats.length; i++) {
      for (int j = 0; j < seats[0].length; j++) {
        Seat seat = seats[i][j];
        if (seat.isSmokers()) {
          count++;
        }
      }
    }
    return count;
  }
  @JsonIgnore
  public int getAmountOfEconomyRows() {
    return amountOfEconomyRows;
  }
  @JsonIgnore
  public int getAmountOfEconomyColumns() {
    return amountOfEconomyColumns;
  }
  @JsonIgnore
  public int getAmountOfPremiumRows() {
    return amountOfPremiumRows;
  }
  @JsonIgnore
  public int getAmountOfPremiumColumns() {
    return amountOfPremiumColumns;
  }
  @JsonIgnore
  private int getBusyCountInColumnByType(int column, Seat[][] seats) {
    int count = 0;
    for (int i = 0; i < seats.length; i++) {
      Seat seat = seats[i][column];
      if (seat.getPerson() != null) {
        count++;
      }
    }
    return count;
  }
  @JsonIgnore
  private int getBusyPercentageByType(Seat[][] typeSeats) {
    int count = 0;
    for (int i = 0; i < typeSeats.length; i++) {
      for (int j = 0; j < typeSeats[0].length; j++) {
        Seat seat = typeSeats[i][j];
        if (seat.getPerson() != null) {
          count++;
        }
      }
    }
    return count;
  }
  private List<Seat> searchByType(String dni, Seat[][] typeSeats) {
    List<Seat> seats = new ArrayList<>();
    for (int i = 0; i < typeSeats.length; i++) {
      for (int j = 0; j < typeSeats[0].length; j++) {
        Seat seat = typeSeats[i][j];
        if (seat.getPerson() != null && seat.getPerson().getId().equals(dni)) {
          seats.add(seat);
        }
      }
    }
    return seats;
  }
  private void addDefaultEconomySeats() {
    addSeatsByType(amountOfEconomyRows, amountOfEconomyColumns, economySeats, SeatType.ECONOMIC);
  }
  private void addDefaultPremiumSeats() {
    addSeatsByType(amountOfPremiumRows, amountOfPremiumColumns, premiumSeats, SeatType.PREMIUM);
  }
  private void addSeatsByType(int rows, int columns, Seat[][] seats, SeatType seatType) {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        seats[i][j] = new Seat(i + 1, columnNamesAdapter.getNameByPosition(j + 1),
            null, seatType, new Random().nextBoolean());
      }
    }
  }
  @JsonIgnore
  private Seat[][] getEconomySeatsSeatsByColumnRange(int columnStart, int columnEnd) {
    Seat[][] sideSeats = new Seat[amountOfEconomyRows][amountOfEconomyColumns / 2];
    for (int i = 0; i < amountOfEconomyRows; i++) {
      int columnPosition = -1;
      for (int j = columnStart; j < columnEnd; j++) {
        columnPosition++;
        sideSeats[i][columnPosition] = economySeats[i][j];
      }
    }
    return sideSeats;
  }
  @JsonIgnore
  private Seat[][] getPremiumSeatsByColumnRange(int columnStart, int columnEnd) {
    Seat[][] sideSeats = new Seat[amountOfPremiumRows][amountOfPremiumColumns / 2];
    for (int i = 0; i < amountOfPremiumRows; i++) {
      int columnPosition = -1;
      for (int j = columnStart; j < columnEnd; j++) {
        columnPosition++;
        sideSeats[i][columnPosition] = premiumSeats[i][j];
      }
    }
    return sideSeats;
  }
  public Seat[][] getEconomySeats() {
    return economySeats;
  }
  public void setEconomySeats(Seat[][] economySeats) {
    this.economySeats = economySeats;
  }
  public Seat[][] getPremiumSeats() {
    return premiumSeats;
  }
  public void setPremiumSeats(Seat[][] premiumSeats) {
    this.premiumSeats = premiumSeats;
  }
}