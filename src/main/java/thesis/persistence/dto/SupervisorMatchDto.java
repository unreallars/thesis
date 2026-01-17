package thesis.persistence.dto;

import java.util.UUID;

public record SupervisorMatchDto(UUID supervisorId, String supervisorUsername,
                                 Integer matchScore) {
}
