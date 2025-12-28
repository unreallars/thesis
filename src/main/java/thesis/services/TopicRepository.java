package thesis.services;

import java.util.List;
import thesis.domain.Topic;

public interface TopicRepository {
  Topic findTopicById(Integer id);

  void save(Topic topic);

  List<Topic> getAllTopics();
}
