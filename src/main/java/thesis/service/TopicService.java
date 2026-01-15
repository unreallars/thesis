package thesis.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import thesis.domain.Link;
import thesis.domain.Topic;
import thesis.domain.dto.TopicProfileDto;

@Service
public class TopicService {
  private final TopicRepository topicRepository;
  private final InformationFileRepository informationFileRepository;
  private final LinkRepository linkRepository;

  public TopicService(TopicRepository topicRepository,
                      InformationFileRepository informationFileRepository,
                      LinkRepository linkRepository) {

    this.topicRepository = topicRepository;
    this.informationFileRepository = informationFileRepository;
    this.linkRepository = linkRepository;
  }

  public Topic findById(UUID id) {
    return topicRepository.findById(id).orElse(null);
  }

  public List<Topic> findAll() {
    return topicRepository.findAll();
  }

  public TopicProfileDto getTopic(UUID topicId) {
    Optional<Topic> topic = topicRepository.findById(topicId);
    return topic.map(
        value -> new TopicProfileDto(value, informationFileRepository.findByTopicId(topicId),
            linkRepository.findByTopicId(topicId))).orElse(null);
  }

  public Topic save(Topic topic) {
    return topicRepository.save(topic);
  }

  public void addTagsAndReq(Topic topic, String tags, String requirements) {

    List<String> extractedTags = extractCommaSeparated(tags);
    extractedTags.forEach(topic::addTag);

    List<String> extractedReq = extractCommaSeparated(requirements);
    extractedReq.forEach(topic::addRequirement);
    topicRepository.save(topic);
  }

  private List<String> extractCommaSeparated(String input) {
    if (input == null || input.isBlank()) {
      return List.of();
    }

    return Arrays.stream(input.split(","))
        .map(String::trim)
        .filter(s -> !s.isEmpty())
        .toList();
  }

  public void addLink(UUID topicId, String url, String linktext) {
    Optional<Topic> topic = topicRepository.findById(topicId);
    if (topic.isPresent()) {
      linkRepository.save(new Link(url, linktext, topicId));
    }
  }
}
