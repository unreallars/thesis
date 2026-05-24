package thesis.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import thesis.domain.Link;
import thesis.domain.Supervisor;
import thesis.domain.SupervisorTag;
import thesis.domain.Topic;
import thesis.domain.TopicRequirement;
import thesis.domain.TopicTag;
import thesis.domain.dto.SupervisorMatch;
import thesis.domain.dto.SupervisorProfile;

@Service
public class SupervisorService {

  private final SupervisorRepository supervisorRepository;
  private final TopicRepository topicRepository;
  private final InformationFileRepository informationFileRepository;
  private final LinkRepository linkRepository;

  public SupervisorService(
      SupervisorRepository supervisorRepository,
      TopicRepository topicRepository,
      InformationFileRepository informationFileRepository,
      LinkRepository linkRepository) {

    this.supervisorRepository = supervisorRepository;
    this.linkRepository = linkRepository;
    this.topicRepository = topicRepository;
    this.informationFileRepository = informationFileRepository;
  }

  public String createTopic(
      String title,
      String description,
      Integer supervisorGithubId,
      String tags,
      String requirements) {
    Supervisor supervisor = findByGithubId(supervisorGithubId);
    if (supervisor == null) {
      return "Betreuer nicht gefunden";
    }
    List<TopicTag> extractedTags =
        Arrays.stream(tags.split(","))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .map(TopicTag::new)
            .toList();
    List<TopicRequirement> extractedReq =
        Arrays.stream(requirements.split(","))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .map(TopicRequirement::new)
            .toList();
    Topic topic = new Topic(title, description, supervisor.id(), extractedTags, extractedReq);

    topicRepository.save(topic);
    return "Thema erfolgreich hinzugefügt";
  }

  public Supervisor findById(UUID id) {
    return supervisorRepository.findById(id).orElse(null);
  }

  public List<Supervisor> findAll() {
    return supervisorRepository.findAll();
  }

  public SupervisorProfile getSupervisorProfile(UUID supervisorId) {
    Optional<Supervisor> supervisor = supervisorRepository.findById(supervisorId);
    return supervisor
        .map(
            value ->
                new SupervisorProfile(
                    value,
                    topicRepository.findBySupervisorId(supervisorId),
                    informationFileRepository.findBySupervisorId(supervisorId),
                    linkRepository.findBySupervisorId(supervisorId)))
        .orElse(null);
  }

  public String createSupervisor(Integer githubId, String username) {
    if (findByGithubId(githubId) != null) {
      return "User ist bereits Betreuer";
    }
    Supervisor supervisor = new Supervisor(githubId, username, "Nicht angegeben");
    supervisorRepository.save(supervisor);
    return "User wurde erfolgreich die Rolle Betreuer zugewiesen";
  }

  public void updateMail(Integer supervisorGithubId, String mail) {

    Supervisor supervisor = findByGithubId(supervisorGithubId);
    if (supervisor != null) {
      supervisorRepository.save(
          new Supervisor(
              supervisor.id(),
              supervisor.username(),
              supervisor.githubId(),
              mail,
              supervisor.supervisorTags()));
    }
  }

  public Supervisor findByGithubId(Integer id) {
    return supervisorRepository.findByGithubId(id).orElse(null);
  }

  public String createLink(Integer supervisorGithubId, String url, String linktext) {
    Supervisor supervisor = findByGithubId(supervisorGithubId);
    if (supervisor != null) {
      linkRepository.save(Link.createSupervisorLink(url, linktext, supervisor.id()));
      return "Link erfolgreich zum Betreuer-Profil hinzugefügt";
    }
    return "Betreuer nicht gefunden";
  }

  public void createTags(Integer supervisorGithubId, String tags) {
    Supervisor supervisor = findByGithubId(supervisorGithubId);
    if (supervisor != null) {
      List<String> extractedTags =
          Arrays.stream(tags.split(",")).map(String::trim).filter(s -> !s.isEmpty()).toList();
      List<SupervisorTag> supervisorTags = new ArrayList<>(supervisor.supervisorTags());
      extractedTags.stream().map(SupervisorTag::new).forEach(supervisorTags::add);
      if (!supervisorTags.isEmpty()) {
        supervisorRepository.save(
            new Supervisor(
                supervisor.id(),
                supervisor.username(),
                supervisor.githubId(),
                supervisor.mail(),
                supervisorTags));
      }
    }
  }

  public List<SupervisorMatch> findMatchingSupervisors(String interests) {
    List<String> tags =
        Arrays.stream(interests.split(",")).map(String::trim).filter(s -> !s.isEmpty()).toList();
    if (tags.isEmpty()) {
      return List.of();
    }
    return supervisorRepository.findMatchingSupervisors(tags);
  }
}
