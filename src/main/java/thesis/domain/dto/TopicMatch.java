package thesis.domain.dto;

import java.util.UUID;

public record TopicMatch(UUID topicId, String topicTitle,
                         String topicDescription, Integer matchScore) {
}
