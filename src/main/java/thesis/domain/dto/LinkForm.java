package thesis.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.UUID;

@SuppressWarnings("unused")
public class LinkForm {

  @NotBlank(message = "Bitte gültige URL angeben")
  @URL(message = "Bitte gültige URL angeben")
  private String url;

  @NotBlank(message = "Bitte gültigen Linktext angeben")
  private String linkText;

  @NotNull(message = "Bitte eine Verknüpfung auswählen")
  private String targetType;

  @UUID(allowEmpty = true, message = "Bitte gültige UUID angeben")
  private String topicId;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getLinkText() {
    return linkText;
  }

  public void setLinkText(String linkText) {
    this.linkText = linkText;
  }

  public String getTargetType() {
    return targetType;
  }

  public void setTargetType(String targetType) {
    this.targetType = targetType;
  }

  public String getTopicId() {
    return topicId;
  }

  public void setTopicId(String topicId) {
    this.topicId = topicId;
  }
}
