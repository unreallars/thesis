package thesis.persistence.dto;

import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("link")
public record LinkDto(
    @Id Integer id, UUID fachId, String url, String text, UUID supervisorId, UUID topicId) {}
