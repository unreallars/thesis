package thesis.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Supervisor {
  private final Integer id;
  private final String username;
  private final String mail;
  private final List<SupervisorTag> tags;

  private Supervisor(int id, String username, String mail, List<SupervisorTag> tags) {
    this.id = id;
    this.username = username;
    this.mail = mail;
    this.tags = tags;
  }

  public Supervisor(int id, String username, String mail) {
    this(id, username, mail, new ArrayList<>());
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

  public List<SupervisorTag> getTags() {
    return Collections.unmodifiableList(tags);
  }

}
