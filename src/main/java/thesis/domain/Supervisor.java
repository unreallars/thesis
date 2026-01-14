package thesis.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

public class Supervisor {
  @Id
  private final Integer id;
  private final String username;
  private final Integer githubId;
  private final String mail;
  private final List<SupervisorTag> supervisorTags;

  @PersistenceCreator
  public Supervisor(Integer id, String username, Integer githubId, String mail,
                    List<SupervisorTag> supervisorTags) {
    this.id = id;
    this.username = username;
    this.githubId = githubId;
    this.mail = mail;
    this.supervisorTags = supervisorTags;
  }

  public Supervisor(Integer githubId, String username, String mail) {
    this(null, username, githubId, mail, new ArrayList<>());
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

  public Integer getGithubId() {
    return githubId;
  }

  public List<SupervisorTag> getSupervisorTags() {
    return Collections.unmodifiableList(supervisorTags);
  }

  public void addTag(String tag) {
    this.supervisorTags.add(new SupervisorTag(tag));
  }
}
