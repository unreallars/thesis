package thesis.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import thesis.domain.InformationFile;
import thesis.services.InformationFileRepository;

@Repository
public class InformationFileRepositoryImpl implements InformationFileRepository {
  Map<String, InformationFile> fileMap = new HashMap<>();

  @Override
  public List<InformationFile> findFileBySupervisorId(Integer supervisorId) {
    return fileMap.values().stream().filter(file -> file.getSupervisorId().equals(supervisorId))
        .toList();
  }

  @Override
  public InformationFile findFileById(String id) {
    return fileMap.get(id);
  }

  @Override
  public void save(InformationFile informationFile) {
    fileMap.put(informationFile.getId(), informationFile);
  }

  @Override
  public List<InformationFile> getAllInformationFiles() {
    return fileMap.values().stream().toList();
  }
}
