package thesis.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import thesis.domain.Link;
import thesis.domain.Supervisor;
import thesis.domain.SupervisorTag;
import thesis.domain.dto.SupervisorMatchDto;
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
    return supervisor.map(
        value -> new SupervisorProfileDto(value, topicRepository.findBySupervisorId(supervisorId),
            informationFileRepository.findBySupervisorId(supervisorId),
            linkRepository.findBySupervisorId(supervisorId))).orElse(null);
  }

  public void createSupervisor(Integer githubId, String username, String mail) {
    Supervisor supervisor = new Supervisor(githubId, username, mail);
    supervisorRepository.save(supervisor);
  }

  public void updateMail(Integer supervisorId, String mail) {
    Optional<Supervisor> supervisor = supervisorRepository.findById(supervisorId);
    supervisor.ifPresent(value -> supervisorRepository.save(
        new Supervisor(supervisorId, value.getUsername(), value.getGithubId(), mail,
            value.getSupervisorTags())));
  }

  public Supervisor findByGithubId(Integer id) {
    return supervisorRepository.findByGithubId(id).orElse(null);
  }

  public void createLink(Integer supervisorId, String url, String linktext) {
    Optional<Supervisor> supervisor = supervisorRepository.findById(supervisorId);
    if (supervisor.isPresent()) {
      linkRepository.save(new Link(url, linktext, supervisorId));
    }
  }

  public void createTags(Integer supervisorId, String tags) {
    Optional<Supervisor> supervisor = supervisorRepository.findById(supervisorId);
    supervisor.ifPresent(supervisorPresent -> {
      List<String> extractedTags =
          Arrays.stream(tags.split(",")).map(String::trim).filter(s -> !s.isEmpty()).toList();
      List<SupervisorTag> supervisorTags = new ArrayList<>(supervisorPresent.getSupervisorTags());
      extractedTags.stream().map(SupervisorTag::new).forEach(supervisorTags::add);
      supervisorRepository.save(
          new Supervisor(supervisorPresent.getId(), supervisorPresent.getUsername(),
              supervisorPresent.getGithubId(), supervisorPresent.getMail(),
              supervisorTags));
    });
  }

  public List<SupervisorMatchDto> findMatchingSupervisors(List<String> studentTags) {
    return supervisorRepository.findMatchingSupervisors(String.join(",", studentTags));
  }
}

