package thesis.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Topic {
  private final UUID uuid;
  private final String title;
  private final String description;
  private final Integer supervisorId;
  private final List<TopicRequirement> requirements;
  private final List<TopicTag> tags;

  private Topic(UUID uuid, String title, String description, Integer supervisorId,
                List<TopicRequirement> requirements, List<TopicTag> tags) {
    this.uuid = uuid;
    this.title = title;
    this.description = description;
    this.supervisorId = supervisorId;
    this.requirements = requirements;
    this.tags = tags;
  }

  public Topic(UUID uuid, String title, String description, Integer supervisorId) {
    this(uuid, title, description, supervisorId, new ArrayList<>(), new ArrayList<>());
  }

  public List<TopicTag> getTags() {
    return Collections.unmodifiableList(tags);
  }

  public List<TopicRequirement> getRequirements() {
    return Collections.unmodifiableList(requirements);
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
