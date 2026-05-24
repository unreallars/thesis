package thesis.domain.dto;

import jakarta.validation.constraints.NotBlank;

@SuppressWarnings("unused")
public class TagForm {

  @NotBlank(message = "Bitte ein Fachgebiet angeben")
  private String tags;

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }
}
