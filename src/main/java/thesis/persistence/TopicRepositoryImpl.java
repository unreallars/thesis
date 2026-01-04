package thesis.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import thesis.domain.Requirement;
import thesis.domain.Topic;
import thesis.service.TopicRepository;

@Repository
public class TopicRepositoryImpl implements TopicRepository {
  private final Map<Integer, Topic> topicMap = new HashMap<>();

  public TopicRepositoryImpl() {
    List<Topic> topicList = List.of(
        new Topic(2, "Programmierung in Spring", "Spring 3.x",
            List.of(new Requirement("Programmierung"))),
        new Topic(35, "Mikroarchitektur", "CPU+GPU",
            List.of(new Requirement("Mikroarchitektur"), new Requirement("Programmierung")))
    );
    for (Topic topic : topicList) {
      topicMap.put(topic.getId(), topic);
    }
  }

  @Override
  public Topic findTopicById(Integer id) {
    return topicMap.get(id);
  }

  @Override
  public void save(Topic topic) {
    topicMap.put(topic.getId(), topic);
  }

  @Override
  public List<Topic> getAllTopics() {
    return topicMap.values().stream().toList();
  }
}
