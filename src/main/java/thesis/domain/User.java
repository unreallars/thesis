package thesis.domain;

public class User {
  final Integer id;

  public void setSupervisor(boolean supervisor) {
    isSupervisor = supervisor;
  }

  boolean isSupervisor;
  final String username;

  public String getUsername() {
    return username;
  }

  public Integer getId() {
    return id;
  }

  public boolean isSupervisor() {
    return isSupervisor;
  }

  public User(Integer id, String username) {
    this.id = id;
    this.username = username;
  }
}
