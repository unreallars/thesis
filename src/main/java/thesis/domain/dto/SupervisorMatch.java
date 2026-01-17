package thesis.domain.dto;

import java.util.UUID;

public record SupervisorMatch(UUID supervisorId, String supervisorUsername,
                              Integer matchScore) {
}
