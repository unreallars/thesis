package thesis.domain;

import java.util.List;

public interface SupervisorRepository {
  Supervisor findSupervisorById(Integer id);

  void save(Supervisor supervisor);

  List<Supervisor> getAllSupervisors();
}
