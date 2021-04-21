package co.com.travelreservation.person;

public class Person {

  private String id;
  private String name;
  private String lastName;
  private String age;

  public Person() {
  }

  public Person(String id, String name, String lastName, String age) {
    this.id = id;
    this.name = name;
    this.lastName = lastName;
    this.age = age;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setAge(String age) {
    this.age = age;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getLastName() {
    return lastName;
  }

  public String getAge() {
    return age;
  }
}
