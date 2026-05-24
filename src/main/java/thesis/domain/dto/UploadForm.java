package thesis.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UUID;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("unused")
public class UploadForm {

  private MultipartFile file;

  @NotBlank(message = "Bitte einen gültigen Titel angeben")
  private String title;

  private String description;

  @NotNull(message = "Bitte eine gültige Verknüpfung auswählen")
  private String targetType;

  @UUID(allowEmpty = true, message = "Bitte eine gültige UUID angeben")
  private String topicId;

  public MultipartFile getFile() {
    return file;
  }

  public void setFile(MultipartFile file) {
    this.file = file;
  }

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
