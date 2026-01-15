package thesis.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import thesis.domain.Link;
import thesis.domain.Supervisor;
import thesis.domain.dto.SupervisorProfileDto;

@Service
public class SupervisorService {
  private final SupervisorRepository supervisorRepository;
  private final TopicRepository topicRepository;
  private final InformationFileRepository informationFileRepository;
  private final LinkRepository linkRepository;

  public SupervisorService(SupervisorRepository supervisorRepository,
                           TopicRepository topicRepository,
                           InformationFileRepository informationFileRepository,
                           LinkRepository linkRepository) {

    this.supervisorRepository = supervisorRepository;
    this.linkRepository = linkRepository;
    this.topicRepository = topicRepository;
    this.informationFileRepository = informationFileRepository;
  }

  public Supervisor findById(Integer id) {
    return supervisorRepository.findById(id).orElse(null);
  }

  public List<Supervisor> findAll() {
    return supervisorRepository.findAll();
  }

  public SupervisorProfileDto getSupervisorProfile(Integer supervisorId) {
    Optional<Supervisor> supervisor = supervisorRepository.findById(supervisorId);
    return supervisor.map(value -> new SupervisorProfileDto(value,
        topicRepository.findBySupervisorId(supervisorId),
        informationFileRepository.findBySupervisorId(supervisorId),
        linkRepository.findBySupervisorId(supervisorId))).orElse(null);
  }

  public void save(Supervisor supervisor) {
    supervisorRepository.save(supervisor);
  }

  public Supervisor findByGithubId(Integer id) {
    return supervisorRepository.findByGithubId(id).orElse(null);
  }

  public void addLink(Integer supervisorId, String url, String linktext) {
    Optional<Supervisor> supervisor = supervisorRepository.findById(supervisorId);
    if (supervisor.isPresent()) {
      linkRepository.save(new Link(url, linktext, supervisorId));
    }
  }

  public void addTags(Supervisor supervisor, String tags) {
    List<String> extractedTags = Arrays.stream(tags.split(","))
        .map(String::trim)
        .filter(s -> !s.isEmpty())
        .toList();
    extractedTags.forEach(supervisor::addTag);
    supervisorRepository.save(supervisor);
  }
}

