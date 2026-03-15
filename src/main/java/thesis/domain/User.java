package thesis.domain;

import java.util.UUID;

public record User(UUID id, Integer githubId, String username) {


  public User(Integer githubId, String username) {
    this(UUID.randomUUID(), githubId, username);
  }

}
