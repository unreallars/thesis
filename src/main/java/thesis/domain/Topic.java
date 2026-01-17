package thesis.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Topic {
  private final UUID uuid;
  private final String title;
  private final String description;
  private final UUID supervisorId;
  private final List<TopicRequirement> requirements;
  private final List<TopicTag> topicTags;

  public Topic(UUID id, String title, String description, UUID supervisorId,
               List<TopicRequirement> requirements, List<TopicTag> topicTags) {
    this.uuid = id;
    this.title = title;
    this.description = description;
    this.supervisorId = supervisorId;
    this.requirements = List.copyOf(requirements);
    this.topicTags = List.copyOf(topicTags);
  }

  public Topic(String title, String description, UUID supervisorId) {
    this(UUID.randomUUID(), title, description, supervisorId, new ArrayList<>(), new ArrayList<>());
  }

  public Topic(String title, String description, UUID supervisorId, List<TopicTag> extractedTags,
               List<TopicRequirement> extractedReq) {
    this(UUID.randomUUID(), title, description, supervisorId, extractedReq, extractedTags);
  }

  public List<TopicTag> getTopicTags() {
    return topicTags;
  }

  public List<TopicRequirement> getRequirements() {
    return requirements;
  }

  public UUID getId() {
    return uuid;
  }

  public String getDescription() {
    return description;
  }

  public String getTitle() {
    return title;
  }

  public UUID getSupervisorId() {
    return supervisorId;
  }
}
