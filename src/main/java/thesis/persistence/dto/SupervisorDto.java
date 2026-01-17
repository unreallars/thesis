package thesis.persistence.dto;

import java.util.List;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("supervisor")
public record SupervisorDto(@Id Integer id, UUID fachId, String username, Integer githubId,
                            String mail,
                            List<SupervisorTagDto> supervisorTags) {
  public SupervisorDto(Integer id, UUID fachId, String username, Integer githubId, String mail,
                       List<SupervisorTagDto> supervisorTags) {
    this.id = id;
    this.fachId = fachId;
    this.username = username;
    this.githubId = githubId;
    this.mail = mail;
    this.supervisorTags = List.copyOf(supervisorTags);
  }
}
