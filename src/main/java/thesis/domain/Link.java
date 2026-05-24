package thesis.domain;

import java.util.UUID;

public record Link(UUID id, String url, String text, UUID supervisorId, UUID topicId) {

  public static Link createSupervisorLink(String url, String text, UUID supervisorId) {
    return new Link(UUID.randomUUID(), url, text, supervisorId, null);
  }

  public static Link createTopicLink(String url, String text, UUID topicId) {
    return new Link(UUID.randomUUID(), url, text, null, topicId);
  }
}
