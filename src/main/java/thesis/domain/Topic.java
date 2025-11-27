package thesis.domain;

import java.util.List;

public class Topic {
  private final Integer id;
  private final String title;
  private final String description;
  private final List<Requirement> requirements;

  public Topic(Integer id, String title, String description, List<Requirement> requirements) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.requirements = requirements;
  }

  public Integer getId() {
    return id;
  }

  public List<Requirement> getRequirements() {
    return requirements;
  }

  public String getDescription() {
    return description;
  }

  public String getTitle() {
    return title;
  }
}
