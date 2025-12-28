package thesis.services;

import java.util.List;
import thesis.domain.Supervisor;

public interface SupervisorRepository {
  Supervisor findSupervisorById(Integer id);

  void save(Supervisor supervisor);

  List<Supervisor> getAllSupervisors();
}
