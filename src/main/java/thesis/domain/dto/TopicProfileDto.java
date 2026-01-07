package thesis.domain.dto;

import java.util.List;
import thesis.domain.InformationFile;
import thesis.domain.Link;
import thesis.domain.Topic;

public record TopicProfileDto(Topic topic, List<InformationFile> files, List<Link> links) {
}
