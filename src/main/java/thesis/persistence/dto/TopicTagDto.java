package thesis.persistence.dto;

import org.springframework.data.relational.core.mapping.Table;

@Table("topic_tag")
public record TopicTagDto(String tag) {}
