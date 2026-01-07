package thesis.service;

import java.util.List;
import java.util.UUID;
import thesis.domain.Supervisor;

public interface SupervisorRepository {
  Supervisor findById(Integer id);

  void save(Supervisor supervisor);

  List<Supervisor> findAll();
}
