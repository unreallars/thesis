package thesis.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

public class InformationFile {
  @Id
  private final UUID id;
  private final LocalDateTime uploadTime;
  private final String fileType;
  private final String filePath;
  private final String title;
  private final String description;
  private final Integer supervisorId;
  private final UUID topicId;

  @PersistenceCreator
  public InformationFile(UUID id, LocalDateTime uploadTime, String fileType, String filePath,
                         String title,
                         String description, Integer supervisorId, UUID topicId) {
    this.uploadTime = uploadTime;
    this.filePath = filePath;
    this.id = id;
    this.fileType = fileType;
    this.title = title;
    this.description = description;
    this.supervisorId = supervisorId;
    this.topicId = topicId;
  }

  public InformationFile(String fileType, String filePath, String title, String description,
                         Integer supervisorId) {
    this.filePath = filePath;
    this.id = null;
    this.fileType = fileType;
    this.title = title;
    this.description = description;
    this.supervisorId = supervisorId;
    this.topicId = null;
    this.uploadTime = LocalDateTime.now();
  }

  public InformationFile(String fileType, String filePath, String title, String description,
                         UUID topicId) {
    this.filePath = filePath;
    this.id = null;
    this.fileType = fileType;
    this.title = title;
    this.description = description;
    this.supervisorId = null;
    this.topicId = topicId;
    this.uploadTime = LocalDateTime.now();
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

  public String getFilePath() {
    return filePath;
  }
}
