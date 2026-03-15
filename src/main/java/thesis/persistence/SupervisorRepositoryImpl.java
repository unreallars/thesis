package thesis.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import thesis.domain.Supervisor;
import thesis.domain.SupervisorTag;
import thesis.domain.dto.SupervisorMatch;
import thesis.persistence.dto.SupervisorDto;
import thesis.persistence.dto.SupervisorMatchDto;
import thesis.persistence.dto.SupervisorTagDto;
import thesis.service.SupervisorRepository;

@Repository
public class SupervisorRepositoryImpl implements SupervisorRepository {
  private final SupervisorDataRepository supervisorDataRepository;

  public SupervisorRepositoryImpl(SupervisorDataRepository supervisorDataRepository) {
    this.supervisorDataRepository = supervisorDataRepository;
  }

  @Override
  public List<Supervisor> findAll() {
    return supervisorDataRepository.findAll().stream().map(this::toSupervisor).toList();
  }

  private Supervisor toSupervisor(SupervisorDto supervisorDto) {
    return new Supervisor(supervisorDto.fachId(), supervisorDto.username(),
        supervisorDto.githubId(),
        supervisorDto.mail(), translateSupervisorTags(supervisorDto.supervisorTags()));
  }

  private List<SupervisorTag> translateSupervisorTags(List<SupervisorTagDto> supervisorTagDtos) {
    return supervisorTagDtos.stream().map(this::toSupervisorTag).toList();
  }

  private SupervisorTag toSupervisorTag(SupervisorTagDto supervisorTagDto) {
    return new SupervisorTag(supervisorTagDto.tag());
  }

  @Override
  public Optional<Supervisor> findByGithubId(Integer id) {
    return supervisorDataRepository.findByGithubId(id).map(this::toSupervisor);
  }

  @Override
  public List<SupervisorMatch> findMatchingSupervisors(List<String> tags) {
    return supervisorDataRepository.findMatchingSupervisors(tags).stream()
        .map(this::toSupervisorMatch).toList();
  }

  private SupervisorMatch toSupervisorMatch(SupervisorMatchDto supervisorMatchDto) {
    return new SupervisorMatch(supervisorMatchDto.supervisorId(),
        supervisorMatchDto.supervisorUsername(), supervisorMatchDto.matchScore());
  }

  @Override
  public Optional<Supervisor> findById(UUID id) {
    return supervisorDataRepository.findByFachId(id).map(this::toSupervisor);
  }

  @Override
  public void save(Supervisor supervisor) {
    SupervisorDto supervisorDto = toSupervisorDto(supervisor);
    supervisorDataRepository.save(supervisorDto);
  }

  private SupervisorDto toSupervisorDto(Supervisor supervisor) {
    Integer existingDbKey =
        supervisorDataRepository.findByFachId(supervisor.id()).map(SupervisorDto::id)
            .orElse(null);
    return new SupervisorDto(existingDbKey, supervisor.id(), supervisor.username(),
        supervisor.githubId(),
        supervisor.mail(), translateSupervisorTagsDto(supervisor.supervisorTags()));
  }

  private List<SupervisorTagDto> translateSupervisorTagsDto(List<SupervisorTag> supervisorTags) {
    return supervisorTags.stream().map(this::toSupervisorTagDto).toList();
  }

  private SupervisorTagDto toSupervisorTagDto(SupervisorTag tag) {
    return new SupervisorTagDto(tag.tag());
  }
}
