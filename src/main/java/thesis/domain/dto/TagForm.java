package thesis.domain.dto;

import jakarta.validation.constraints.NotBlank;

public class TagForm {
  @NotBlank(message = "Bitte mindestens einen Tag angeben")
  private String tags;

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }
}
