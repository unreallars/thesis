package thesis.services;

import java.util.List;
import org.springframework.stereotype.Service;
import thesis.domain.Supervisor;

@Service
public class SupervisorService {
  private final SupervisorRepository supervisorRepository;

  public SupervisorService(SupervisorRepository supervisorRepository) {

    this.supervisorRepository = supervisorRepository;
  }

  public Supervisor getSupervisorById(Integer id) {
    return supervisorRepository.findSupervisorById(id);
  }

  public boolean isSupervisor(Integer id) {
    return supervisorRepository.findSupervisorById(id) != null;
  }

  public List<Supervisor> getAllSupervisors() {
    return supervisorRepository.getAllSupervisors();
  }

  public void updateSupervisor(Integer id, String username, String mail) {
    supervisorRepository.save(new Supervisor(id, username, mail, null, null, null));
  }
}
