package thesis.persistence.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("information_file")
public record InformationFileDto(
    @Id Integer id,
    UUID fachId,
    LocalDateTime uploadTime,
    String fileType,
    String filePath,
    String title,
    String description,
    UUID supervisorId,
    UUID topicId) {}
