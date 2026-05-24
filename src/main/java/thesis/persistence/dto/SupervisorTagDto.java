package thesis.persistence.dto;

import org.springframework.data.relational.core.mapping.Table;

@Table("supervisor_tag")
public record SupervisorTagDto(String tag) {}
