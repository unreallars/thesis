package thesis.domain;

import java.util.UUID;

public class User {
  private final UUID id;

  private final Integer githubId;
  private final String username;

  public String getUsername() {
    return username;
  }

  public UUID getId() {
    return id;
  }

  public Integer getGithubId() {
    return githubId;
  }


  public User(Integer githubId, String username) {
    this(UUID.randomUUID(), githubId, username);
  }

  public User(UUID id, Integer githubId, String username) {
    this.id = id;
    this.githubId = githubId;
    this.username = username;
  }
}
