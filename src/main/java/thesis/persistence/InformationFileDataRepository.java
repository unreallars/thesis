package thesis.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import thesis.persistence.dto.InformationFileDto;

public interface InformationFileDataRepository extends CrudRepository<InformationFileDto, Integer> {
  @NonNull
  List<InformationFileDto> findAll();

  List<InformationFileDto> findBySupervisorId(UUID supervisorId);

  List<InformationFileDto> findByTopicId(UUID topicId);

  Optional<InformationFileDto> findByFachId(UUID id);


}
