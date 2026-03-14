package thesis.domain.dto;

import java.util.UUID;

public record TopicMatch(UUID supervisorId, String supervisorName, String topicTitle,
                         String topicDescription, Integer matchScore) {
}
