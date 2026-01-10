package thesis.service;

import java.util.List;
import thesis.domain.User;

public interface UserRepository {
  User findById(Integer id);

  void save(User user);

  List<User> findAll();
}
