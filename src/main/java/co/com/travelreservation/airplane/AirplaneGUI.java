package co.com.travelreservation.airplane;

import co.com.travelreservation.actions.ButtonActionListener;
import co.com.travelreservation.seat.Seat;
import co.com.travelreservation.seat.SeatType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AirplaneGUI extends JFrame {

  private JPanel globalPanel;
  private JPanel seatsPanel;
  private JPanel economyLeftPanel;
  private JPanel economyRightPanel;
  private JPanel premiumLeftPanel;
  private JPanel premiumRightPanel;
  private JPanel buttonsPanel;
  private Airplane airplane;
  private Map<String, JButton> buttonMap = new HashMap();

  public AirplaneGUI(String name, Airplane airplane) {
    super(name);
    this.airplane = airplane;
    guiDefinitions();
  }

  private void guiDefinitions() {
    setGlobalPanelOptions();
    drawActionButtons();
    drawEconomySeats();
    drawPremiumSeats();
    setContentPane(globalPanel);
  }

  private void drawEconomySeats() {
    Seat[][] leftSeats = airplane.getLeftEconomySeats();
    Seat[][] rightSeats = airplane.getRightEconomySeats();
    drawSeats(leftSeats, economyLeftPanel, SeatType.ECONOMIC);
    drawSeats(rightSeats, economyRightPanel, SeatType.ECONOMIC);
  }

  private void drawPremiumSeats() {
    Seat[][] leftSeats = airplane.getLeftPremiumSeats();
    Seat[][] rightSeats = airplane.getRightPremiumSeats();
    drawSeats(leftSeats, premiumLeftPanel, SeatType.PREMIUM);
    drawSeats(rightSeats, premiumRightPanel, SeatType.PREMIUM);
  }

  private void drawSeats(Seat[][] seats, JPanel panel, SeatType seatType) {
    for (int i = 0; i < seats.length; i++) {
      for (int j = 0; j < seats[0].length; j++) {
        Seat seat = seats[i][j];
        JButton button = new JButton("(" + seat.getRow() + ", " + seat.getColumn() + ")",
            new ImageIcon("images/icons8-flight-seat-40.png"));
        button.putClientProperty("seat", seat);
        button.setToolTipText("(" + seat.getRow() + ", " + seat.getColumn() + ")");
        button.addActionListener(new ButtonActionListener(airplane, button, 1));
        buttonMap.put(seatType.getValue() + "-" + seat.getRow() + "-" + seat.getColumn(), button);
        if (seat.getPerson() != null) {
          button.setEnabled(false);
        }
        panel.add(button);
      }
    }
  }

  private void addSidePanelOptions() {
    addEconomySidePanelOptions();
    addPremiumSidePanelOptions();
  }

  private void setButtonsPanel() {
    buttonsPanel = new JPanel(new GridLayout(8, 1, 0, 10));
    buttonsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    globalPanel.add(buttonsPanel);
  }

  private void drawActionButtons() {
    drawRemoveReservationBtn();
    drawPassengerSearch();
    drawGetBusyPercentage();
    drawGetEconomySmokingSeatsBtn();
    drawIsPremiumSmokingBtn();
    drawGetSmokingSeats();
    drawMostBusySeatTypeInWindowBtn();
    drawGetEconomicFreeSeatInWindowBtn();
  }

  private void drawGetEconomicFreeSeatInWindowBtn() {
    JButton getFreeEconomySeatInWindowBtn = new JButton("Silla libre económica en ventana");
    getFreeEconomySeatInWindowBtn.addActionListener(new ButtonActionListener(airplane, 9));
    buttonsPanel.add(getFreeEconomySeatInWindowBtn);
  }

  private void drawMostBusySeatTypeInWindowBtn() {
    JButton getMostBusySeatTypeInWindowBtn = new JButton("Clase con mayor cantidad de sillas ocupadas en ventana");
    getMostBusySeatTypeInWindowBtn.addActionListener(new ButtonActionListener(airplane, 8));
    buttonsPanel.add(getMostBusySeatTypeInWindowBtn);
  }

  private void drawGetSmokingSeats() {
    JButton getSmokingSeatsBtn = new JButton("Cantidad de sillas para fumador en todo el avión");
    getSmokingSeatsBtn.addActionListener(new ButtonActionListener(airplane, 7));
    buttonsPanel.add(getSmokingSeatsBtn);
  }

  private void drawIsPremiumSmokingBtn() {
    JButton isPremiumSmokingBtn = new JButton("¿Silla ejecutiva para fumador libre?");
    isPremiumSmokingBtn.addActionListener(new ButtonActionListener(airplane, 6));
    buttonsPanel.add(isPremiumSmokingBtn);
  }

  private void drawGetEconomySmokingSeatsBtn() {
    JButton getEconomySmokingSeatsBtn = new JButton("Cantidad de sillas económicas para fumadores");
    getEconomySmokingSeatsBtn.addActionListener(new ButtonActionListener(airplane, 5));
    buttonsPanel.add(getEconomySmokingSeatsBtn);
  }

  private void drawGetBusyPercentage() {
    JButton getBusyPercentageBtn = new JButton("Porcentaje de ocupación del avión");
    getBusyPercentageBtn.addActionListener(new ButtonActionListener(airplane, 4));
    buttonsPanel.add(getBusyPercentageBtn);
  }

  private void drawPassengerSearch() {
    JButton searchPassenger = new JButton("Buscar pasajero");
    searchPassenger.addActionListener(new ButtonActionListener(airplane, 3));
    buttonsPanel.add(searchPassenger);
  }

  private void drawRemoveReservationBtn() {
    JButton removeReservationBtn = new JButton("Eliminar Reserva");
    removeReservationBtn.addActionListener(new ButtonActionListener(airplane, buttonMap, 2));
    buttonsPanel.add(removeReservationBtn);
  }

  private void addEconomySidePanelOptions() {
    economyLeftPanel = new JPanel(new GridLayout(airplane.getAmountOfEconomyRows(),
        airplane.getAmountOfEconomyColumns() / 2, 10, 10));
    economyRightPanel = new JPanel(new GridLayout(airplane.getAmountOfEconomyRows(),
        airplane.getAmountOfEconomyColumns() / 2, 10, 10));
    seatsPanel.add(economyLeftPanel);
    seatsPanel.add(economyRightPanel);
  }

  private void addPremiumSidePanelOptions() {
    premiumLeftPanel = new JPanel(new GridLayout(airplane.getAmountOfPremiumRows(),
        airplane.getAmountOfPremiumColumns() / 2, 10, 10));
    premiumLeftPanel.setSize(5, 10);
    premiumRightPanel = new JPanel(new GridLayout(airplane.getAmountOfPremiumRows(),
        airplane.getAmountOfPremiumColumns() / 2, 10, 10));
    seatsPanel.add(premiumLeftPanel);
    seatsPanel.add(premiumRightPanel);
  }

  private void setSeatsPanelOptions() {
    seatsPanel = new JPanel(new GridLayout(2, 2, 50, 50));
    seatsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    globalPanel.add(seatsPanel);
    addSidePanelOptions();
  }

  private void setGlobalPanelOptions() {
    globalPanel = new JPanel(new FlowLayout());
    setButtonsPanel();
    setSeatsPanelOptions();
  }

  public Airplane getAirplane() {
    return airplane;
  }

  public void setAirplane(Airplane airplane) {
    this.airplane = airplane;
  }
}
