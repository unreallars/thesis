package thesis.service;

import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import thesis.domain.Topic;

public interface TopicRepository extends CrudRepository<Topic, UUID> {
  List<Topic> findAll();

  List<Topic> findBySupervisorId(Integer supervisorId);
}
