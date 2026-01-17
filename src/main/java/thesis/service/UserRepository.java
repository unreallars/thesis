package thesis.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import thesis.domain.User;

public interface UserRepository {
  List<User> findAll();

  Optional<User> findByGithubId(Integer githubId);

  Optional<User> findById(UUID id);

  void save(User user);
}
