package thesis.service;

import java.util.List;
import java.util.UUID;
import thesis.domain.Supervisor;
import thesis.domain.Topic;

public interface TopicRepository {
  Topic findById(UUID id);

  void save(Topic topic);

  List<Topic> findAll();

  List<Topic> findBySupervisorId(Integer supervisorId);
}
