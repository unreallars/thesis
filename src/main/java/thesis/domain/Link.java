package thesis.domain;

import java.util.UUID;

public class Link {
  private final UUID id;
  private final String url;
  private final String text;
  private final UUID supervisorId;
  private final UUID topicId;

  public Link(UUID id, String url, String text, UUID supervisorId, UUID topicId) {
    this.id = id;
    this.url = url;
    this.text = text;
    this.supervisorId = supervisorId;
    this.topicId = topicId;
  }

  public static Link createSupervisorLink(String url, String text, UUID supervisorId) {
    return new Link(UUID.randomUUID(), url, text, supervisorId, null);
  }

  public static Link createTopicLink(String url, String text, UUID topicId) {
    return new Link(UUID.randomUUID(), url, text, null, topicId);
  }

  public String getUrl() {
    return url;
  }

  public String getText() {
    return text;
  }

  public UUID getId() {
    return id;
  }

  public UUID getSupervisorId() {
    return supervisorId;
  }

  public UUID getTopicId() {
    return topicId;
  }

}
