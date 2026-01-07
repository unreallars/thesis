package thesis.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
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
    return topicRepository.findById(id);
  }

  public List<Topic> findAll() {
    return topicRepository.findAll();
  }
  public TopicProfileDto getTopic(UUID topicId) {
    return new TopicProfileDto(topicRepository.findById(topicId),
        informationFileRepository.findByTopicId(topicId),
        linkRepository.findByTopicId(topicId));
  }
}
