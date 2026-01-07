package thesis.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class InformationFile {
  private final UUID id;
  private final LocalDateTime uploadTime = LocalDateTime.now();
  private final String fileType;
  private final String title;
  private final String description;
  private final Integer supervisorId;
  private final UUID topicId;

  public InformationFile(UUID id, String fileType,
                         String title,
                         String description, Integer supervisorId, UUID topicId) {
    this.id = id;
    this.fileType = fileType;
    this.title = title;
    this.description = description;
    this.supervisorId = supervisorId;
    this.topicId = topicId;
  }

  public String getDescription() {
    return description;
  }

  public String getTitle() {
    return title;
  }

  public String getFileType() {
    return fileType;
  }

  public LocalDateTime getUploadTime() {
    return uploadTime;
  }


  public UUID getId() {
    return id;
  }

  public Integer getSupervisorId() {
    return supervisorId;
  }

  public UUID getTopicId() {
    return topicId;
  }
}
