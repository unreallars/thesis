package thesis.domain;

import java.util.List;
import java.util.UUID;

public class Topic {
  private final UUID uuid;
  private final String title;
  private final String description;
  private final Integer supervisorId;
  private final List<TopicRequirement> requirements;
  private final List<TopicTag> tags;

  public Topic(UUID uuid, String title, String description, Integer supervisorId,
               List<TopicRequirement> requirements, List<TopicTag> tags) {
    this.uuid = uuid;
    this.title = title;
    this.description = description;
    this.supervisorId = supervisorId;
    this.requirements = requirements;
    this.tags = tags;
  }

  public List<TopicTag> getTags() {
    return tags;
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
