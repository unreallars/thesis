package thesis.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import thesis.domain.User;
import thesis.persistence.dto.UserDto;
import thesis.service.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {

  private final UserDataRepository userDataRepository;

  public UserRepositoryImpl(UserDataRepository userDataRepository) {
    this.userDataRepository = userDataRepository;
  }

  @Override
  public List<User> findAll() {
    return userDataRepository.findAll().stream().map(this::toUser).toList();
  }

  private User toUser(UserDto userDto) {
    return new User(userDto.fachId(), userDto.githubId(), userDto.username());
  }

  @Override
  public Optional<User> findByGithubId(Integer githubId) {
    return userDataRepository.findByGithubId(githubId).map(this::toUser);
  }

  @Override
  public Optional<User> findById(UUID id) {
    return userDataRepository.findByFachId(id).map(this::toUser);
  }

  @Override
  public void save(User user) {
    UserDto userDto = toUserDto(user);
    userDataRepository.save(userDto);
  }

  private UserDto toUserDto(User user) {
    Integer existingDbKey =
        userDataRepository.findByFachId(user.id()).map(UserDto::id).orElse(null);
    return new UserDto(existingDbKey, user.id(), user.githubId(), user.username());
  }
}
