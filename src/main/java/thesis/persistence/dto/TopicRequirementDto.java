package thesis.persistence.dto;

import org.springframework.data.relational.core.mapping.Table;

@Table("topic_requirement")
public record TopicRequirementDto(String name) {}
