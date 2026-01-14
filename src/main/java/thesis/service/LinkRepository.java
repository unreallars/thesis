package thesis.service;

import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import thesis.domain.Link;

public interface LinkRepository extends CrudRepository<Link, UUID> {
  List<Link> findAll();

  List<Link> findBySupervisorId(Integer supervisorId);

  List<Link> findByTopicId(UUID topicId);
}
