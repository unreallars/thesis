package thesis.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

public class Topic {
  @Id
  private final UUID uuid;
  private final String title;
  private final String description;
  private final Integer supervisorId;
  private final List<TopicRequirement> requirements;
  private final List<TopicTag> topicTags;

  @PersistenceCreator
  public Topic(UUID uuid, String title, String description, Integer supervisorId,
               List<TopicRequirement> requirements, List<TopicTag> topicTags) {
    this.uuid = uuid;
    this.title = title;
    this.description = description;
    this.supervisorId = supervisorId;
    this.requirements = List.copyOf(requirements);
    this.topicTags = List.copyOf(topicTags);
  }

  public Topic(String title, String description, Integer supervisorId) {
    this(null, title, description, supervisorId, new ArrayList<>(), new ArrayList<>());
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

  public Integer getSupervisorId() {
    return supervisorId;
  }
}
