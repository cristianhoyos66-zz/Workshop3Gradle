package co.com.travelreservation.application;

import co.com.travelreservation.airplane.Airplane;
import co.com.travelreservation.airplane.AirplaneGUI;
import co.com.travelreservation.airplane.AirplaneWindowListener;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class GUIMaker {

  public static void main(String[] args) throws IOException {
    AirplaneGUI airplaneGUI = new AirplaneGUI("Travel Reservation", readMainAirplaneObj());
    setupWindow(airplaneGUI);
  }

  private static void setupWindow (AirplaneGUI airplaneGUI) {
    airplaneGUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    airplaneGUI.addWindowListener(new AirplaneWindowListener(airplaneGUI.getAirplane()));
    airplaneGUI.setResizable(false);
    airplaneGUI.pack();
    airplaneGUI.setLocationRelativeTo(null);
    airplaneGUI.setVisible(true);
  }

  private static Airplane readMainAirplaneObj() throws IOException {
    File file = new File("src/main/resources/Airplane.json");
    if (file.exists()) {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(file, Airplane.class);
    }
    return new Airplane();
  }

}
