package thesis.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.jspecify.annotations.NonNull;
import org.springframework.data.repository.CrudRepository;
import thesis.persistence.dto.UserDto;

public interface UserDataRepository extends CrudRepository<UserDto, Integer> {
  @NonNull
  List<UserDto> findAll();

  Optional<UserDto> findByGithubId(Integer githubId);

  Optional<UserDto> findByFachId(UUID id);
}
