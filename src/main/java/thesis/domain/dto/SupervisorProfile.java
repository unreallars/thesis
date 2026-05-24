package thesis.domain.dto;

import java.util.List;
import thesis.domain.InformationFile;
import thesis.domain.Link;
import thesis.domain.Supervisor;
import thesis.domain.Topic;

public record SupervisorProfile(
    Supervisor supervisor, List<Topic> topics, List<InformationFile> files, List<Link> links) {

  public SupervisorProfile(
      Supervisor supervisor, List<Topic> topics, List<InformationFile> files, List<Link> links) {
    this.supervisor =
        new Supervisor(
            supervisor.id(),
            supervisor.username(),
            supervisor.githubId(),
            supervisor.mail(),
            List.copyOf(supervisor.supervisorTags()));
    this.topics = List.copyOf(topics);
    this.files = List.copyOf(files);
    this.links = List.copyOf(links);
  }

  @Override
  public Supervisor supervisor() {
    return new Supervisor(
        supervisor.id(),
        supervisor.username(),
        supervisor.githubId(),
        supervisor.mail(),
        List.copyOf(supervisor.supervisorTags()));
  }
}
