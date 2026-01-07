package thesis.service;

import java.util.List;
import org.springframework.stereotype.Service;
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
    return supervisorRepository.findById(id);
  }

  public List<Supervisor> findAll() {
    return supervisorRepository.findAll();
  }

  public SupervisorProfileDto getSupervisorProfile(Integer supervisorId) {
    return new SupervisorProfileDto(supervisorRepository.findById(supervisorId),
        topicRepository.findBySupervisorId(supervisorId),
        informationFileRepository.findBySupervisorId(supervisorId),
        linkRepository.findBySupervisorId(supervisorId));
  }

  public void updateSupervisor(Integer id, String username, String mail) {
    Supervisor supervisor = supervisorRepository.findById(id);
    supervisor.setMail(mail);
    supervisor.setUsername(username);
    supervisorRepository.save(supervisor);
  }
}
