package co.com.travelreservation.actions;

import co.com.travelreservation.airplane.Airplane;
import co.com.travelreservation.person.Person;
import co.com.travelreservation.seat.Seat;
import co.com.travelreservation.seat.SeatType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class ButtonActionListener implements ActionListener {

  private Airplane airplane;
  private JButton button;
  private Integer actionId;
  private Map<Integer, ButtonActionExecutor> buttonActions = Map
      .of(
          1, this::addPersonToSeat,
          2, this::removeReservation,
          3, this::passengerSearch,
          4, this::getBusyPercentage,
          5, this::getEconomySmokingSeatsBtn,
          6, this::isPremiumSmoking,
          7, this::getSmokingSeatsAmount,
          8, this::getMostBusyClassInWindow,
          9, this::getEconomicFreeSeatInWindow
      );
  private Map<String, JButton> buttonMap;

  public ButtonActionListener(Airplane airplane, JButton button, Integer actionId) {
    this.airplane = airplane;
    this.button = button;
    this.actionId = actionId;
  }

  public ButtonActionListener(Airplane airplane, Map<String, JButton> buttonMap, Integer actionId) {
    this.airplane = airplane;
    this.buttonMap = buttonMap;
    this.actionId = actionId;
  }

  public ButtonActionListener(Airplane airplane, Integer actionId) {
    this.airplane = airplane;
    this.actionId = actionId;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    buttonActions.get(actionId).execute();
  }

  private void getEconomicFreeSeatInWindow() {
    Seat seat = airplane.getEconomicFreeSeatInWindow();
    if (seat != null) {
      JOptionPane.showMessageDialog(null, "La silla económica libre en ventana es: " +
          seat.getRow() + ", " + seat.getColumn());
    } else {
      JOptionPane.showMessageDialog(null, "No hay silla económica libre en ventana");
    }
  }

  private void getMostBusyClassInWindow() {
    SeatType seatType = airplane.getMostBusyClassInWindow();
    if (seatType != null) {
      JOptionPane.showMessageDialog(null, "La clase con más cantidad de sillas ocupadas en ventana es: " + seatType.getValue());
    } else {
      JOptionPane.showMessageDialog(null, "No hay clase con más cantidad de sillas ocupadas en ventana");
    }
  }

  private void getSmokingSeatsAmount() {
    int smokingSeatsAmount = airplane.getSmokingSeatsBtn();
    JOptionPane.showMessageDialog(null, "La cantidad de sillas para fumadores en el avión es:" + smokingSeatsAmount);
  }

  private void isPremiumSmoking() {
    String seatPos = JOptionPane.showInputDialog("Ingrese silla con el siguiente formato fila,columna");
    String[] strSeat = seatPos.split(",");
    Seat seat = airplane.isPremiumSmoking(Integer.parseInt(strSeat[0]), strSeat[1]);
    if (seat.getPerson() == null && seat.isSmokers()) {
      JOptionPane.showMessageDialog(null, "Esa silla es de fumador, es ejecutiva y está libre");
    } else {
      JOptionPane.showMessageDialog(null, "Silla ocupada o de no fumador");
    }

  }

  private void addPersonToSeat() {
    String personData = JOptionPane.showInputDialog("Ingrese número de cédula, nombre, apellido y edad, separados por -");
    String[] person = personData.split("-");
    Person personInfo = new Person(person[0], person[1], person[2], person[3]);
    Seat seat = (Seat) button.getClientProperty("seat");
    seat.setPerson(personInfo);
    airplane.setSeat(seat);
    button.setEnabled(false);
  }

  private void removeReservation() {
    String btnPosition = JOptionPane.showInputDialog("Ingrese silla con el siguiente formato Premium-fila-columna o Economic-fila-columna");
    JButton btnToRemove = buttonMap.get(btnPosition);
    Seat seat = (Seat) btnToRemove.getClientProperty("seat");
    seat.setPerson(null);
    airplane.setSeat(seat);
    btnToRemove.setEnabled(true);
  }

  private void passengerSearch() {
    String dni = JOptionPane.showInputDialog("Ingrese cédula del pasajero");
    List<Seat> seats = airplane.passengerSearch(dni);
    StringBuilder sb = new StringBuilder();
    for (Seat seat: seats) {
      sb.append("- " + seat.getSeatType().getValue() + " " + seat.getRow() + ", " + seat.getColumn() + " - ");
    }
    JOptionPane.showMessageDialog(null, "Las sillas del pasajero son: " + sb.toString());
  }

  private void getBusyPercentage() {
    Double percentage = airplane.getBusyPercentage();
    JOptionPane.showMessageDialog(null, "El porcentaje de ocupación del avión es: " + percentage);
  }

  private void getEconomySmokingSeatsBtn() {
    int amountSmokingSeats = airplane.getEconomySmokingSeats();
    JOptionPane.showMessageDialog(null,
        "La cantidad de asientos económicos para fumadores es: " + amountSmokingSeats);
  }

}
