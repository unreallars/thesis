package thesis.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import thesis.domain.Topic;
import thesis.domain.dto.TopicMatch;

public interface TopicRepository {

  List<Topic> findAll();

  List<Topic> findBySupervisorId(UUID supervisorId);

  List<String> findAllTopicRequirements();

  List<TopicMatch> findMatchingTopics(List<String> tags, List<String> requirements);

  Optional<Topic> findById(UUID id);

  void save(Topic topic);
}
