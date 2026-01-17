package thesis.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import thesis.domain.Topic;
import thesis.domain.dto.MatchResult;

public interface TopicRepository {
  List<Topic> findAll();

  List<Topic> findBySupervisorId(UUID supervisorId);

  List<String> findAllDistinctTopicRequirementNames();

  List<MatchResult> findMatchingTopics(String tagsCsv, String requirementsCsv);

  Optional<Topic> findById(UUID id);

  void save(Topic topic);
}
