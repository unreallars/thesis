package thesis.service;

import static java.util.Objects.requireNonNullElse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import thesis.domain.Link;
import thesis.domain.Topic;
import thesis.domain.dto.TopicMatch;
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


  public String createLink(String uuid, String url, String linktext) {
    if (uuid.isEmpty()) {
      return "Keine gültige Topic-Id angegeben";
    }

    Topic topic = findById(UUID.fromString(uuid));
    if (topic == null || topic.getSupervisorId() == null) {
      return "Das angegebene Thema existiert nicht";
    }
    linkRepository.save(Link.createTopicLink(url, linktext, UUID.fromString(uuid)));
    return "Link erfolgreich zum Thema hinzugefügt";
  }


  public List<String> findAllRequirements() {
    return topicRepository.findAllDistinctTopicRequirementNames();
  }

  public List<TopicMatch> findMatchingTopics(String interestsInput,
                                             List<String> completedRequirements) {
    completedRequirements = requireNonNullElse(completedRequirements, new ArrayList<>());
    interestsInput = requireNonNullElse(interestsInput, "");

    List<String> studentTags =
        Arrays.stream(interestsInput.split(",")).map(String::trim).filter(s -> !s.isEmpty())
            .toList();
    return topicRepository.findMatchingTopics(String.join(",", studentTags),
        String.join(",", completedRequirements));
  }
}
