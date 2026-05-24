package thesis.service;

import java.util.UUID;
import org.springframework.stereotype.Service;
import thesis.domain.User;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User findById(UUID id) {
    return userRepository.findById(id).orElse(null);
  }

  public void save(User user) {
    userRepository.save(user);
  }

  public User findByGithubId(Integer id) {
    return userRepository.findByGithubId(id).orElse(null);
  }
}
