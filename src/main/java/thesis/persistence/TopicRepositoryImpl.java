package thesis.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import thesis.domain.Topic;
import thesis.domain.TopicRequirement;
import thesis.domain.TopicTag;
import thesis.domain.dto.TopicMatch;
import thesis.persistence.dto.TopicDto;
import thesis.persistence.dto.TopicMatchDto;
import thesis.persistence.dto.TopicRequirementDto;
import thesis.persistence.dto.TopicTagDto;
import thesis.service.TopicRepository;

@Repository
public class TopicRepositoryImpl implements TopicRepository {
  private final TopicDataRepository topicDataRepository;

  public TopicRepositoryImpl(TopicDataRepository topicDataRepository) {
    this.topicDataRepository = topicDataRepository;
  }

  @Override
  public List<Topic> findAll() {
    return topicDataRepository.findAll().stream().map(this::toTopic).toList();
  }

  private Topic toTopic(TopicDto topicDto) {
    return new Topic(topicDto.fachId(), topicDto.title(), topicDto.description(),
        topicDto.supervisorId(), translateTopicRequirements(topicDto.requirements()),
        translateTopicTags(topicDto.topicTags()));
  }

  private List<TopicTag> translateTopicTags(List<TopicTagDto> topicTagDtos) {
    return topicTagDtos.stream().map(this::toTopicTag).toList();
  }

  private TopicTag toTopicTag(TopicTagDto topicTagDto) {
    return new TopicTag(topicTagDto.tag());
  }

  private List<TopicRequirement> translateTopicRequirements(
      List<TopicRequirementDto> requirements) {
    return requirements.stream().map(this::toTopicRequirement).toList();
  }

  private TopicRequirement toTopicRequirement(TopicRequirementDto topicRequirementDto) {
    return new TopicRequirement(topicRequirementDto.name());
  }

  @Override
  public List<Topic> findBySupervisorId(UUID supervisorId) {
    return topicDataRepository.findBySupervisorId(supervisorId).stream().map(this::toTopic)
        .toList();
  }

  @Override
  public List<String> findAllTopicRequirements() {
    return topicDataRepository.findAllTopicRequirements();
  }

  @Override
  public List<TopicMatch> findMatchingTopics(String tagsCsv, String requirementsCsv) {
    return topicDataRepository.findMatchingTopics(tagsCsv, requirementsCsv).stream()
        .map(this::toMatchResult).toList();
  }

  private TopicMatch toMatchResult(TopicMatchDto topicMatchDto) {
    return new TopicMatch(topicMatchDto.supervisorId(), topicMatchDto.supervisorName(),
        topicMatchDto.topicTitle(),
        topicMatchDto.topicDescription(), topicMatchDto.matchScore());
  }

  @Override
  public Optional<Topic> findById(UUID id) {
    return topicDataRepository.findByFachId(id).map(this::toTopic);
  }

  @Override
  public void save(Topic topic) {
    TopicDto topicDto = toTopicDto(topic);
    topicDataRepository.save(topicDto);
  }

  private TopicDto toTopicDto(Topic topic) {
    Integer existingDbKey =
        topicDataRepository.findByFachId(topic.getId()).map(TopicDto::id).orElse(null);
    return new TopicDto(existingDbKey, topic.getId(), topic.getTitle(), topic.getDescription(),
        topic.getSupervisorId(), translateTopicRequierementsDto(topic.getRequirements()),
        translateTopicTagsDto(topic.getTopicTags()));
  }

  private List<TopicTagDto> translateTopicTagsDto(List<TopicTag> topicTags) {
    return topicTags.stream().map(this::toTopicTagDto).toList();
  }

  private TopicTagDto toTopicTagDto(TopicTag topicTag) {
    return new TopicTagDto(topicTag.tag());
  }

  private List<TopicRequirementDto> translateTopicRequierementsDto(
      List<TopicRequirement> requirements) {
    return requirements.stream().map(this::toTopicRequirementDto).toList();
  }

  private TopicRequirementDto toTopicRequirementDto(TopicRequirement topicRequirement) {
    return new TopicRequirementDto(topicRequirement.name());
  }
}
