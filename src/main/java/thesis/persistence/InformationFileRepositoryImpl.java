package thesis.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import thesis.domain.InformationFile;
import thesis.persistence.dto.InformationFileDto;
import thesis.service.InformationFileRepository;

@Repository
public class InformationFileRepositoryImpl implements InformationFileRepository {
  private final InformationFileDataRepository informationFileDataRepository;

  public InformationFileRepositoryImpl(
      InformationFileDataRepository informationFileDataRepository) {
    this.informationFileDataRepository = informationFileDataRepository;
  }

  @Override
  public List<InformationFile> findAll() {
    return informationFileDataRepository.findAll().stream().map(this::toInformationFile).toList();
  }

  private InformationFile toInformationFile(InformationFileDto informationFileDto) {
    return new InformationFile(informationFileDto.fachId(), informationFileDto.uploadTime(),
        informationFileDto.fileType(), informationFileDto.filePath(), informationFileDto.title(),
        informationFileDto.description(), informationFileDto.supervisorId(),
        informationFileDto.topicId());
  }

  @Override
  public List<InformationFile> findBySupervisorId(UUID supervisorId) {
    return informationFileDataRepository.findBySupervisorId(supervisorId).stream()
        .map(this::toInformationFile).toList();
  }

  @Override
  public List<InformationFile> findByTopicId(UUID topicId) {
    return informationFileDataRepository.findByTopicId(topicId).stream()
        .map(this::toInformationFile).toList();
  }

  @Override
  public Optional<InformationFile> findById(UUID id) {
    return informationFileDataRepository.findByFachId(id).map(this::toInformationFile);
  }

  @Override
  public void save(InformationFile informationFile) {
    InformationFileDto informationFileDto = toInformationFileDto(informationFile);
    informationFileDataRepository.save(informationFileDto);
  }

  private InformationFileDto toInformationFileDto(InformationFile informationFile) {
    Integer existingDbKey =
        informationFileDataRepository.findByFachId(informationFile.id())
            .map(InformationFileDto::id)
            .orElse(null);
    return new InformationFileDto(existingDbKey, informationFile.id(),
        informationFile.uploadTime(),
        informationFile.fileType(), informationFile.filePath(), informationFile.title(),
        informationFile.description(),
        informationFile.supervisorId(), informationFile.topicId());
  }
}
