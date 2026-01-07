package thesis.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import thesis.domain.Link;
import thesis.service.LinkRepository;

@Repository
public class LinkRepositoryImpl implements LinkRepository {
  Map<UUID, Link> linkMap = new HashMap<>();

  public LinkRepositoryImpl() {
    save(new Link(UUID.randomUUID(), "https://www.youtube.com/", "Youtube Yeah", 1, null));
  }

  @Override
  public Link findById(UUID id) {
    return linkMap.get(id);
  }

  @Override
  public void save(Link link) {
    linkMap.put(link.getId(), link);
  }

  @Override
  public List<Link> findAll() {
    return linkMap.values().stream().toList();
  }

  @Override
  public List<Link> findBySupervisorId(Integer supervisorId) {
    return linkMap.values().stream()
        .filter(link -> Objects.equals(link.getSupervisorId(), supervisorId))
        .toList();
  }

  @Override
  public List<Link> findByTopicId(UUID topicId) {
    return linkMap.values().stream().filter(link -> Objects.equals(link.getTopicId(), topicId))
        .toList();
  }
}
