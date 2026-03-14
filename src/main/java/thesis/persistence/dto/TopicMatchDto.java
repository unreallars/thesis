package thesis.persistence.dto;

import java.util.UUID;

public record TopicMatchDto(UUID supervisorId, String supervisorName, String topicTitle,
                            String topicDescription, Integer matchScore) {
}
