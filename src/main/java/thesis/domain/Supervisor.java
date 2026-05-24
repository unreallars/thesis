package thesis.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record Supervisor(
    UUID id, String username, Integer githubId, String mail, List<SupervisorTag> supervisorTags) {

  public Supervisor(
      UUID id, String username, Integer githubId, String mail, List<SupervisorTag> supervisorTags) {
    this.id = id;
    this.username = username;
    this.githubId = githubId;
    this.mail = mail;
    this.supervisorTags = List.copyOf(supervisorTags);
  }

  public Supervisor(Integer githubId, String username, String mail) {
    this(UUID.randomUUID(), username, githubId, mail, new ArrayList<>());
  }
}
