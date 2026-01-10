package thesis.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import thesis.domain.User;
import thesis.service.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {
  private final Map<Integer, User> users = new HashMap<>();

  @Override
  public User findById(Integer id) {
    return users.get(id);
  }

  @Override
  public void save(User user) {
    users.put(user.getId(), user);
  }

  @Override
  public List<User> findAll() {
    return users.values().stream().toList();
  }
}
