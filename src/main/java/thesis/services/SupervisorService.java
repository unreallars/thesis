package thesis.services;

import java.util.List;
import org.springframework.stereotype.Service;
import thesis.domain.Supervisor;
import thesis.domain.SupervisorRepository;

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
}
