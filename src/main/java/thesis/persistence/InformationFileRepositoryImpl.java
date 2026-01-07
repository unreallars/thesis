package thesis.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import thesis.domain.InformationFile;
import thesis.service.InformationFileRepository;

@Repository
public class InformationFileRepositoryImpl implements InformationFileRepository {
  Map<UUID, InformationFile> fileMap = new HashMap<>();

  public InformationFileRepositoryImpl() {
    save(new InformationFile(UUID.fromString("a6e35b67-345e-4e8f-a333-4b02eb1bf050"), "pdf",
        "Testdatei", "Beschreibungen sind gut", 1, null));
    save(new InformationFile(UUID.fromString("4ac9344c-3944-4cac-a6b9-5c7596c3460f"), "pdf",
        "Testdatei", "Beschreibungen sind gut", 1, null));
  }

  @Override
  public InformationFile findById(UUID id) {
    return fileMap.get(id);
  }

  @Override
  public void save(InformationFile informationFile) {
    fileMap.put(informationFile.getId(), informationFile);
  }

  @Override
  public List<InformationFile> findBySupervisorId(Integer supervisorId) {
    return fileMap.values().stream()
        .filter(informationFile -> Objects.equals(informationFile.getSupervisorId(), supervisorId))
        .toList();
  }

  @Override
  public List<InformationFile> findByTopicId(UUID topicId) {
    return fileMap.values().stream()
        .filter(informationFile -> Objects.equals(informationFile.getTopicId(), topicId)).toList();
  }

  @Override
  public List<InformationFile> findAll() {
    return fileMap.values().stream().toList();
  }
}
