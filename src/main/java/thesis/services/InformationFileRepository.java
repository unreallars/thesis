package thesis.services;

import java.util.List;
import thesis.domain.InformationFile;

public interface InformationFileRepository {
  List<InformationFile> findFileBySupervisorId(Integer supervisorId);

  InformationFile findFileById(String id);

  void save(InformationFile informationFile);

  List<InformationFile> getAllInformationFiles();
}
