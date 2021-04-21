package co.com.travelreservation.airplane;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

public class AirplaneWindowListener implements WindowListener {

  private Airplane airplane;

  public AirplaneWindowListener(Airplane airplane) {
    this.airplane = airplane;
  }

  @Override
  public void windowOpened(WindowEvent e) {
    System.out.println("Hello!");
  }

  @Override
  public void windowClosing(WindowEvent e) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.writeValue(new File("src/main/resources/Airplane.json"), airplane);
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }

  @Override
  public void windowClosed(WindowEvent e) {

  }

  @Override
  public void windowIconified(WindowEvent e) {

  }

  @Override
  public void windowDeiconified(WindowEvent e) {

  }

  @Override
  public void windowActivated(WindowEvent e) {

  }

  @Override
  public void windowDeactivated(WindowEvent e) {

  }
}
