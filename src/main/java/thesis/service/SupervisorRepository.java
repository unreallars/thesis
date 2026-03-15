package thesis.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import thesis.domain.Supervisor;
import thesis.domain.dto.SupervisorMatch;

public interface SupervisorRepository {
  List<Supervisor> findAll();

  Optional<Supervisor> findByGithubId(Integer id);

  List<SupervisorMatch> findMatchingSupervisors(List<String> tags);

  Optional<Supervisor> findById(UUID id);

  void save(Supervisor supervisor);
}
