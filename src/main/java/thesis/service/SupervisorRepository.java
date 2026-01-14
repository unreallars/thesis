package thesis.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import thesis.domain.Supervisor;

public interface SupervisorRepository extends CrudRepository<Supervisor, Integer> {
  List<Supervisor> findAll();

  Optional<Supervisor> findByGithubId(Integer id);
}
