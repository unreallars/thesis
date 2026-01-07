package thesis.domain;

import java.util.List;

public class Supervisor {
  private final Integer id;
  private String username;
  private String mail;
  private final List<SupervisorTag> tags;

  public Supervisor(int id, String username, String mail, List<SupervisorTag> tags) {
    this.id = id;
    this.username = username;
    this.mail = mail;
    this.tags = tags;
  }

  public int getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<SupervisorTag> getTags() {
    return tags;
  }

}
