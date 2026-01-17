package thesis.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import thesis.persistence.dto.LinkDto;

public interface LinkDataRepository extends CrudRepository<LinkDto, Integer> {
  @NonNull
  List<LinkDto> findAll();

  List<LinkDto> findBySupervisorId(UUID supervisorId);

  List<LinkDto> findByTopicId(UUID topicId);

  Optional<LinkDto> findByFachId(UUID id);
}
