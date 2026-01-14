package thesis.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

public class User {
  @Id
  private final Integer id;


  private final Integer githubId;
  private final String username;

  public String getUsername() {
    return username;
  }

  public Integer getId() {
    return id;
  }

  public Integer getGithubId() {
    return githubId;
  }


  public User(Integer githubId, String username) {
    this.id = null;
    this.githubId = githubId;
    this.username = username;
  }

  @PersistenceCreator
  public User(Integer id, Integer githubId, String username) {
    this.id = id;
    this.githubId = githubId;
    this.username = username;
  }
}
