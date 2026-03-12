package thesis.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import thesis.domain.Link;
import thesis.domain.Topic;
import thesis.domain.dto.MatchResult;
import thesis.domain.dto.TopicProfile;

class TopicServiceTest {

  private TopicRepository topicRepository;
  private InformationFileRepository informationFileRepository;
  private LinkRepository linkRepository;
  private TopicService topicService;
  private Topic topic;

  @BeforeEach
  void setUp() {
    topicRepository = mock(TopicRepository.class);
    informationFileRepository = mock(InformationFileRepository.class);
    linkRepository = mock(LinkRepository.class);
    topic = mock(Topic.class);

    topicService = new TopicService(topicRepository, informationFileRepository, linkRepository);
  }

  @Test
  @DisplayName("findById – Topic vorhanden")
  void test_01() {
    UUID id = UUID.randomUUID();
    when(topicRepository.findById(id)).thenReturn(Optional.of(topic));

    Topic result = topicService.findById(id);

    assertThat(result).isSameAs(topic);
  }

  @Test
  @DisplayName("test_02: findById – Topic nicht vorhanden")
  void test_02() {
    UUID id = UUID.randomUUID();
    when(topicRepository.findById(id)).thenReturn(Optional.empty());

    Topic result = topicService.findById(id);

    assertThat(result).isNull();
  }


  @Test
  @DisplayName("getTopic – Topic nicht gefunden")
  void test_03() {
    UUID id = UUID.randomUUID();
    when(topicRepository.findById(id)).thenReturn(Optional.empty());

    TopicProfile result = topicService.getTopic(id);

    assertThat(result).isNull();
  }

  @Test
  @DisplayName("getTopic – TopicProfile wird korrekt aufgebaut")
  void test_04() {
    UUID id = UUID.randomUUID();
    when(topicRepository.findById(id)).thenReturn(Optional.of(topic));
    when(informationFileRepository.findByTopicId(id)).thenReturn(List.of());
    when(linkRepository.findByTopicId(id)).thenReturn(List.of(mock(Link.class)));

    TopicProfile result = topicService.getTopic(id);

    assertThat(result).isNotNull();
  }

  @Test
  @DisplayName("createLink – Topic-Id ist null")
  void test_05() {
    String result = topicService.createLink(null, "https://example.com", "Example");

    assertThat(result).isEqualTo("Ungültige Topic-Id");
    verify(linkRepository, never()).save(any());
  }

  @Test
  @DisplayName("createLink – Topic existiert nicht oder hat keinen Betreuer")
  void test_06() {
    UUID id = UUID.randomUUID();
    when(topicRepository.findById(id)).thenReturn(Optional.empty());

    String result = topicService.createLink(id.toString(), "https://example.com", "Example");
    assertThat(result).isEqualTo("Das angegebene Thema existiert nicht");
    verify(linkRepository, never()).save(any());

    Topic topic = mock(Topic.class);
    when(topicRepository.findById(id)).thenReturn(Optional.of(topic));
    when(topic.getSupervisorId()).thenReturn(null);

    String result2 = topicService.createLink(id.toString(), "https://example.com", "Example");
    assertThat(result2).isEqualTo("Das angegebene Thema existiert nicht");
    verify(linkRepository, never()).save(any());
  }

  @Test
  @DisplayName("createLink – Link wird gespeichert")
  void test_07() {
    UUID id = UUID.randomUUID();
    when(topicRepository.findById(id)).thenReturn(Optional.of(topic));
    when(topic.getSupervisorId()).thenReturn(UUID.randomUUID());

    String result = topicService.createLink(id.toString(), "https://example.com", "Example");

    assertThat(result).isEqualTo("Link erfolgreich zum Thema hinzugefügt");
    verify(linkRepository).save(any(Link.class));
  }

  @Test
  @DisplayName("findMatchingTopics - Keine Angaben")
  void test_08() {
    when(topicRepository.findMatchingTopics("", "")).thenReturn(List.of());

    List<MatchResult> result = topicService.findMatchingTopics(null, null);

    assertThat(result).isEmpty();
    verify(topicRepository).findMatchingTopics("", "");
  }

  @Test
  @DisplayName("findMatchingTopics - Angabe enthält einen leeres Feld")
  void test_09() {
    String interestsInput = " Spring ,  ,Rust, funktionale Programmierung ";

    topicService.findMatchingTopics(interestsInput, null);

    verify(topicRepository).findMatchingTopics("Spring,Rust,funktionale Programmierung", "");
  }

  @Test
  @DisplayName("findMatchingTopics - Angabe besteht nur aus Leerzeichen")
  void test_10() {
    String interestsInput = "  ";

    topicService.findMatchingTopics(interestsInput, null);

    verify(topicRepository).findMatchingTopics("", "");
  }
}

