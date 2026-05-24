package thesis.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public record InformationFile(
    UUID id,
    LocalDateTime uploadTime,
    String fileType,
    String filePath,
    String title,
    String description,
    UUID supervisorId,
    UUID topicId) {

  public static InformationFile createSupervisorInformationFile(
      String fileType, String filePath, String title, String description, UUID supervisorId) {
    return new InformationFile(
        UUID.randomUUID(),
        LocalDateTime.now(),
        fileType,
        filePath,
        title,
        description,
        supervisorId,
        null);
  }

  public static InformationFile createTopicInformationFile(
      String fileType, String filePath, String title, String description, UUID topicId) {
    return new InformationFile(
        UUID.randomUUID(),
        LocalDateTime.now(),
        fileType,
        filePath,
        title,
        description,
        null,
        topicId);
  }
}
