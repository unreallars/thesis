package thesis.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import thesis.domain.Topic;
import thesis.service.TopicRepository;

@Repository
public class TopicRepositoryImpl implements TopicRepository {
  private final Map<UUID, Topic> topicMap = new HashMap<>();

  public TopicRepositoryImpl() {
    List.of(
        new Topic(UUID.randomUUID(), "Programmierung in Spring", "Spring 3.x", 1),
        new Topic(UUID.randomUUID(), "Mikroarchitektur", "CPU+GPU", 1)
    ).forEach(this::save);
  }

  public Topic findById(UUID id) {
    return topicMap.get(id);
  }

  @Override
  public void save(Topic topic) {
    topicMap.put(topic.getId(), topic);
  }

  @Override
  public List<Topic> findAll() {
    return topicMap.values().stream().toList();
  }

  @Override
  public List<Topic> findBySupervisorId(Integer supervisorId) {
    return topicMap.values().stream().filter(topic -> topic.getSupervisorId().equals(supervisorId))
        .toList();
  }
}
