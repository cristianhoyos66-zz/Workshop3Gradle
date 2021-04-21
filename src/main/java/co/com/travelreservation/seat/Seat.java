package co.com.travelreservation.seat;

import co.com.travelreservation.person.Person;

public class Seat {

  private int row;
  private String column;
  private Person person;
  private SeatType seatType;
  private Boolean smokers;

  public Seat() {
  }

  public Seat(int row, String column, Person person, SeatType seatType, Boolean smokers) {
    this.row = row;
    this.column = column;
    this.person = person;
    this.seatType = seatType;
    this.smokers = smokers;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public String getColumn() {
    return column;
  }

  public void setColumn(String column) {
    this.column = column;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public SeatType getSeatType() {
    return seatType;
  }

  public void setSeatType(SeatType seatType) {
    this.seatType = seatType;
  }

  public Boolean isSmokers() {
    return smokers;
  }

  public void setSmokers(Boolean smokers) {
    this.smokers = smokers;
  }
}
