package thesis.domain.dto;

import java.util.List;
import thesis.domain.InformationFile;
import thesis.domain.Link;
import thesis.domain.Topic;

public record TopicProfile(Topic topic, List<InformationFile> files, List<Link> links) {

  public TopicProfile(Topic topic, List<InformationFile> files, List<Link> links) {
    this.topic =
        new Topic(
            topic.getId(),
            topic.getTitle(),
            topic.getDescription(),
            topic.getSupervisorId(),
            List.copyOf(topic.getRequirements()),
            List.copyOf(topic.getTopicTags()));
    this.files = List.copyOf(files);
    this.links = List.copyOf(links);
  }

  @Override
  public Topic topic() {
    return new Topic(
        topic.getId(),
        topic.getTitle(),
        topic.getDescription(),
        topic.getSupervisorId(),
        List.copyOf(topic.getRequirements()),
        List.copyOf(topic.getTopicTags()));
  }
}
