package thesis.service;

import java.util.List;
import java.util.UUID;
import thesis.domain.InformationFile;

public interface InformationFileRepository {

  InformationFile findById(UUID id);

  void save(InformationFile informationFile);

  List<InformationFile> findAll();

  List<InformationFile> findBySupervisorId(Integer supervisorId);

  List<InformationFile> findByTopicId(UUID topicId);


}
