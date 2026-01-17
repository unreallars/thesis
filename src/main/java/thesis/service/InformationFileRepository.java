package thesis.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import thesis.domain.InformationFile;

public interface InformationFileRepository {
  List<InformationFile> findAll();

  List<InformationFile> findBySupervisorId(UUID supervisorId);

  List<InformationFile> findByTopicId(UUID topicId);


  Optional<InformationFile> findById(UUID id);

  void save(InformationFile informationFile);
}
