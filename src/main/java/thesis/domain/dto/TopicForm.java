package thesis.domain.dto;

import jakarta.validation.constraints.NotBlank;

public class TopicForm {
  @NotBlank(message = "Bitte einen Titel für das Thema angeben")
  private String title;
  private String description;
  private String tags;
  private String requirements;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public String getRequirements() {
    return requirements;
  }

  public void setRequirements(String requirements) {
    this.requirements = requirements;
  }
}
