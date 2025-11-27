package thesis.domain;

import java.util.List;

public interface TopicRepository {
  Topic findTopicById(Integer id);

  void save(Topic topic);

  List<Topic> getAllTopics();
}
