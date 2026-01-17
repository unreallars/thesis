package thesis.persistence.dto;

import java.util.UUID;

public record MatchResultDto(UUID supervisorId, String supervisorName, String topicTitle,
                             String topicDescription, Integer matchScore) {
}
