package thesis.persistence.dto;

import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("user")
public record UserDto(@Id Integer id, UUID fachId, Integer githubId, String username) {
}
