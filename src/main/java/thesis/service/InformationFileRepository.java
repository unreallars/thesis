package thesis.service;

import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import thesis.domain.InformationFile;

public interface InformationFileRepository extends CrudRepository<InformationFile, UUID> {
  List<InformationFile> findAll();

  List<InformationFile> findBySupervisorId(Integer supervisorId);

  List<InformationFile> findByTopicId(UUID topicId);


}
