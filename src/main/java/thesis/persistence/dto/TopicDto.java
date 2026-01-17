package thesis.persistence.dto;

import java.util.List;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("topic")
public record TopicDto(@Id Integer id, UUID fachId, String title, String description,
                       UUID supervisorId,
                       List<TopicRequirementDto> requirements, List<TopicTagDto> topicTags) {
  public TopicDto(Integer id, UUID fachId, String title, String description, UUID supervisorId,
                  List<TopicRequirementDto> requirements, List<TopicTagDto> topicTags) {
    this.id = id;
    this.fachId = fachId;
    this.title = title;
    this.description = description;
    this.supervisorId = supervisorId;
    this.requirements = List.copyOf(requirements);
    this.topicTags = List.copyOf(topicTags);
  }
}
