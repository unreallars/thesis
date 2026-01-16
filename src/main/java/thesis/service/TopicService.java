package thesis.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import thesis.domain.Link;
import thesis.domain.Topic;
import thesis.domain.TopicRequirement;
import thesis.domain.TopicTag;
import thesis.domain.dto.MatchResultDto;
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

  public Topic createTopic(String title, String description, int supervisorId) {
    return topicRepository.save(new Topic(title, description, supervisorId));
  }

  public void createTagsAndReq(UUID topicId, String tags, String requirements) {
    Optional<Topic> topic = topicRepository.findById(topicId);
    topic.ifPresent(topicPresent -> {
      List<String> extractedTags =
          Arrays.stream(tags.split(",")).map(String::trim).filter(s -> !s.isEmpty()).toList();
      List<TopicTag> topicTags = new ArrayList<>(topicPresent.getTopicTags());
      extractedTags.stream().map(TopicTag::new).forEach(topicTags::add);
      List<String> extractedReq =
          Arrays.stream(requirements.split(",")).map(String::trim).filter(s -> !s.isEmpty())
              .toList();
      List<TopicRequirement> topicReq = new ArrayList<>(topicPresent.getRequirements());
      extractedReq.stream().map(TopicRequirement::new).forEach(topicReq::add);

      topicRepository.save(
          new Topic(topicPresent.getId(), topicPresent.getTitle(),
              topicPresent.getDescription(), topicPresent.getSupervisorId(),
              topicReq, topicTags));
    });
  }

  public void createLink(UUID topicId, String url, String linktext) {
    Optional<Topic> topic = topicRepository.findById(topicId);
    if (topic.isPresent()) {
      linkRepository.save(new Link(url, linktext, topicId));
    }
  }


  public List<String> findAllRequirements() {
    return topicRepository.findAllDistinctTopicRequirementNames();
  }

  public List<MatchResultDto> findMatchingTopics(List<String> studentTags,
                                                 List<String> completedRequirements) {
    return topicRepository.findMatchingTopics(String.join(",", studentTags),
    String.join(",", completedRequirements));
  }
}
