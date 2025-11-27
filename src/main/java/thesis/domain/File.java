package thesis.domain;

import java.time.LocalDateTime;

public class File {
  private final Long id;
  private final String uploader;
  private final LocalDateTime creationDate;
  private final String type;
  private final String title;
  private final String description;

  public File(Long id, String uploader, LocalDateTime creationDate, String type, String title,
              String description) {
    this.id = id;
    this.uploader = uploader;
    this.creationDate = creationDate;
    this.type = type;
    this.title = title;
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public String getTitle() {
    return title;
  }

  public String getType() {
    return type;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public String getUploader() {
    return uploader;
  }

  public Long getId() {
    return id;
  }
}
