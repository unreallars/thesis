package thesis.services;

import java.util.List;
import org.springframework.stereotype.Service;
import thesis.domain.Topic;
import thesis.domain.TopicRepository;

@Service
public class TopicService {
  private final TopicRepository topicRepository;

  public TopicService(TopicRepository topicRepository) {

    this.topicRepository = topicRepository;
  }

  public Topic getTopicById(Integer id) {
    return topicRepository.findTopicById(id);
  }

  public List<Topic> getAllTopics() {
    return topicRepository.getAllTopics();
  }
}
