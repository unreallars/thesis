package thesis.service;

import java.util.List;
import java.util.UUID;
import thesis.domain.Link;

public interface LinkRepository {
  Link findById(UUID id);

  void save(Link link);

  List<Link> findAll();

  List<Link> findBySupervisorId(Integer supervisorId);

  List<Link> findByTopicId(UUID topicId);
}
