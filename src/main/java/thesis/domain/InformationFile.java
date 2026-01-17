package thesis.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class InformationFile {
  private final UUID id;
  private final LocalDateTime uploadTime;
  private final String fileType;
  private final String filePath;
  private final String title;
  private final String description;
  private final UUID supervisorId;
  private final UUID topicId;

  public InformationFile(UUID id, LocalDateTime uploadTime, String fileType, String filePath,
                         String title,
                         String description, UUID supervisorId, UUID topicId) {
    this.uploadTime = uploadTime;
    this.filePath = filePath;
    this.id = id;
    this.fileType = fileType;
    this.title = title;
    this.description = description;
    this.supervisorId = supervisorId;
    this.topicId = topicId;
  }

  public static InformationFile createSupervisorInformationFile(String fileType, String filePath,
                                                                String title,
                                                                String description,
                                                                UUID supervisorId) {
    return new InformationFile(UUID.randomUUID(), LocalDateTime.now(), fileType, filePath, title,
        description,
        supervisorId, null);
  }

  public static InformationFile createTopicInformationFile(String fileType, String filePath,
                                                           String title,
                                                           String description,
                                                           UUID topicId) {
    return new InformationFile(UUID.randomUUID(), LocalDateTime.now(), fileType, filePath, title,
        description, null,
        topicId);
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

  public UUID getSupervisorId() {
    return supervisorId;
  }

  public UUID getTopicId() {
    return topicId;
  }

  public String getFilePath() {
    return filePath;
  }
}
