package co.com.travelreservation.application;

import java.util.Map;

public class ColumnNamesAdapter {

  private Map<Integer, String> columnNames = Map.of(
      1, "A",
      2, "B",
      3, "C",
      4, "D",
      5, "E",
      6, "F"
  );

  private Map<String, Integer> columnNumbers = Map.of(
      "A", 1,
      "B", 2,
      "C", 3,
      "D", 4,
      "E", 5,
      "F", 6
  );

  public String getNameByPosition(Integer position) {
    return columnNames.get(position);
  }

  public int getPositionByName(String name) {
    return columnNumbers.get(name);
  }

}
