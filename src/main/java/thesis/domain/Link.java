package thesis.domain;

import java.util.UUID;

public class Link {
  public UUID getId() {
    return id;
  }

  public Integer getSupervisorId() {
    return supervisorId;
  }

  public UUID getTopicId() {
    return topicId;
  }

  private final UUID id;
  private final String url;
  private final String text;
  private final Integer supervisorId;
  private final UUID topicId;

  public Link(UUID id, String url, String text, Integer supervisorId, UUID topicId) {
    this.id = id;
    this.url = url;
    this.text = text;
    this.supervisorId = supervisorId;
    this.topicId = topicId;
  }

  public String getUrl() {
    return url;
  }

  public String getText() {
    return text;
  }

}
