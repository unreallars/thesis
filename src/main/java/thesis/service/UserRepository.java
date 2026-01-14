package thesis.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import thesis.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {
  List<User> findAll();

  Optional<User> findByGithubId(Integer githubId);
}
