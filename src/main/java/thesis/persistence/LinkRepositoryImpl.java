package thesis.persistence;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import thesis.domain.Link;
import thesis.persistence.dto.LinkDto;
import thesis.service.LinkRepository;

@Repository
public class LinkRepositoryImpl implements LinkRepository {
  private final LinkDataRepository linkDataRepository;

  public LinkRepositoryImpl(LinkDataRepository linkDataRepository) {
    this.linkDataRepository = linkDataRepository;
  }

  @Override
  public List<Link> findAll() {
    return linkDataRepository.findAll().stream().map(this::toLink).toList();
  }

  private Link toLink(LinkDto linkDto) {
    return new Link(linkDto.fachId(), linkDto.url(), linkDto.text(), linkDto.supervisorId(),
        linkDto.topicId());
  }

  @Override
  public List<Link> findBySupervisorId(UUID supervisorId) {
    return linkDataRepository.findBySupervisorId(supervisorId).stream().map(this::toLink).toList();
  }

  @Override
  public List<Link> findByTopicId(UUID topicId) {
    return linkDataRepository.findByTopicId(topicId).stream().map(this::toLink).toList();
  }

  @Override
  public void save(Link link) {
    LinkDto linkDto = toLinkDto(link);
    linkDataRepository.save(linkDto);
  }

  private LinkDto toLinkDto(Link link) {
    Integer existingDbKey =
        linkDataRepository.findByFachId(link.getId()).map(LinkDto::id).orElse(null);
    return new LinkDto(existingDbKey, link.getId(), link.getUrl(), link.getText(),
        link.getSupervisorId(),
        link.getTopicId());
  }
}
