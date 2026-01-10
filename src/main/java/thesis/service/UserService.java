package thesis.service;

import org.springframework.stereotype.Service;
import thesis.domain.User;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User findById(Integer id) {
    return userRepository.findById(id);
  }

  public void save(User user) {
    userRepository.save(user);
  }
}
