package thesis.service;

import java.util.List;
import java.util.UUID;
import thesis.domain.Link;

public interface LinkRepository {
  List<Link> findAll();

  List<Link> findBySupervisorId(UUID supervisorId);

  List<Link> findByTopicId(UUID topicId);

  void save(Link link);
}
