package thesis.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import thesis.domain.Link;
import thesis.domain.Topic;
import thesis.domain.TopicRequirement;
import thesis.domain.TopicTag;
import thesis.domain.dto.MatchResult;
import thesis.domain.dto.TopicProfile;

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

  public TopicProfile getTopic(UUID topicId) {
    Optional<Topic> topic = topicRepository.findById(topicId);
    return topic.map(
        value -> new TopicProfile(value, informationFileRepository.findByTopicId(topicId),
            linkRepository.findByTopicId(topicId))).orElse(null);
  }

  public void createTopic(String title, String description, UUID supervisorId, String tags,
                          String requirements) {
    List<TopicTag> extractedTags =
        Arrays.stream(tags.split(",")).map(String::trim).filter(s -> !s.isEmpty())
            .map(TopicTag::new).toList();
    List<TopicRequirement> extractedReq =
        Arrays.stream(requirements.split(",")).map(String::trim).filter(s -> !s.isEmpty())
            .map(TopicRequirement::new).toList();
    Topic topic = new Topic(title, description, supervisorId, extractedTags, extractedReq);

    topicRepository.save(topic);
  }

  public void createLink(UUID topicId, String url, String linktext) {
    Optional<Topic> topic = topicRepository.findById(topicId);
    if (topic.isPresent()) {
      linkRepository.save(Link.createTopicLink(url, linktext, topicId));
    }
  }


  public List<String> findAllRequirements() {
    return topicRepository.findAllDistinctTopicRequirementNames();
  }

  public List<MatchResult> findMatchingTopics(List<String> studentTags,
                                              List<String> completedRequirements) {
    return topicRepository.findMatchingTopics(String.join(",", studentTags),
        String.join(",", completedRequirements));
  }
}
