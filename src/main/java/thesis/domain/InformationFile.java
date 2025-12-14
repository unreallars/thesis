package thesis.domain;

import java.time.LocalDateTime;

public class InformationFile {
  private final String id;
  private final Integer supervisorId;
  private final LocalDateTime uploadTime;
  private final String fileType;
  private final String title;
  private final String description;

  public InformationFile(String id, Integer supervisorId, LocalDateTime uploadTime, String fileType,
                         String title,
                         String description) {
    this.id = id;
    this.supervisorId = supervisorId;
    this.uploadTime = uploadTime;
    this.fileType = fileType;
    this.title = title;
    this.description = description;
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

  public Integer getSupervisorId() {
    return supervisorId;
  }

  public String getId() {
    return id;
  }
}
