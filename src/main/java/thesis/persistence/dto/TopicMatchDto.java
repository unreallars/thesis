package thesis.persistence.dto;

import java.util.UUID;

public record TopicMatchDto(
    UUID topicId, String topicTitle, String topicDescription, Integer matchScore) {}
