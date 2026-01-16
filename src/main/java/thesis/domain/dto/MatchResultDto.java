package thesis.domain.dto;

public record MatchResultDto(Integer supervisorId, String supervisorName, String topicTitle,
                             String topicDescription, Integer matchScore) {
}
