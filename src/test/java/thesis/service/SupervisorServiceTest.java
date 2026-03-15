package thesis.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import thesis.domain.Link;
import thesis.domain.Supervisor;
import thesis.domain.dto.SupervisorProfile;

class SupervisorServiceTest {

  private SupervisorRepository supervisorRepository;
  private LinkRepository linkRepository;
  private SupervisorService supervisorService;
  private Supervisor supervisor;

  @BeforeEach
  void setUp() {
    supervisorRepository = mock(SupervisorRepository.class);
    TopicRepository topicRepository = mock(TopicRepository.class);
    InformationFileRepository informationFileRepository = mock(InformationFileRepository.class);
    linkRepository = mock(LinkRepository.class);
    supervisor = mock(Supervisor.class);

    supervisorService =
        new SupervisorService(supervisorRepository, topicRepository, informationFileRepository,
            linkRepository);
  }

  @Test
  @DisplayName("createTopic – Betreuer nicht gefunden")
  void test_01() {
    when(supervisorRepository.findByGithubId(123)).thenReturn(Optional.empty());

    String result =
        supervisorService.createTopic("Titel", "Beschreibung", 123, "tag1, tag2", "req1, req2");
    assertThat(result).isEqualTo("Betreuer nicht gefunden");
  }

  @Test
  @DisplayName("createTopic – Thema wird erfolreich gespeichert")
  void test_02() {
    UUID supervisorId = UUID.randomUUID();
    when(supervisorRepository.findByGithubId(123)).thenReturn(Optional.of(supervisor));
    when(supervisor.id()).thenReturn(supervisorId);

    String result =
        supervisorService.createTopic("Titel", "Beschreibung", 123, "tag1, tag2 , ", "req1,req2");

    assertThat(result).isEqualTo("Thema erfolgreich hinzugefügt");
  }

  @Test
  @DisplayName("findById – Supervisor vorhanden")
  void test_03() {
    UUID id = UUID.randomUUID();
    when(supervisorRepository.findById(id)).thenReturn(Optional.of(supervisor));

    Supervisor result = supervisorService.findById(id);

    assertThat(result).isSameAs(supervisor);
  }

  @Test
  @DisplayName("findById – Supervisor nicht vorhanden")
  void test_04() {
    UUID id = UUID.randomUUID();
    when(supervisorRepository.findById(id)).thenReturn(Optional.empty());

    Supervisor result = supervisorService.findById(id);

    assertThat(result).isNull();
  }

  @Test
  @DisplayName("getSupervisorProfile – Supervisor nicht gefunden")
  void test_05() {
    UUID id = UUID.randomUUID();
    when(supervisorRepository.findById(id)).thenReturn(Optional.empty());

    SupervisorProfile result = supervisorService.getSupervisorProfile(id);

    assertThat(result).isNull();
  }

  @Test
  @DisplayName("createSupervisor – User ist bereits Betreuer")
  void test_06() {
    when(supervisorRepository.findByGithubId(123)).thenReturn(Optional.of(mock(Supervisor.class)));

    String result = supervisorService.createSupervisor(123, "user");

    assertThat(result).isEqualTo("User ist bereits Betreuer");
  }

  @Test
  @DisplayName("createSupervisor – neuer Betreuer wird angelegt")
  void test_07() {
    when(supervisorRepository.findByGithubId(123)).thenReturn(Optional.empty());

    String result = supervisorService.createSupervisor(123, "user");

    assertThat(result).isEqualTo("User wurde erfolgreich die Rolle Betreuer zugewiesen");
  }

  @Test
  @DisplayName("createLink – Supervisor nicht gefunden")
  void test_08() {
    when(supervisorRepository.findByGithubId(123)).thenReturn(Optional.empty());

    String result = supervisorService.createLink(123, "https://example.com", "Example");

    assertThat(result).isEqualTo("Betreuer nicht gefunden");
  }

  @Test
  @DisplayName("createLink – Link wird gespeichert")
  void test_09() {
    UUID supervisorId = UUID.randomUUID();
    when(supervisorRepository.findByGithubId(123)).thenReturn(Optional.of(supervisor));
    when(supervisor.id()).thenReturn(supervisorId);

    String result = supervisorService.createLink(123, "https://example.com", "Example");

    assertThat(result).isEqualTo("Link erfolgreich zum Betreuer-Profil hinzugefügt");
    verify(linkRepository).save(any(Link.class));
  }

  @Test
  @DisplayName("createTags – Supervisor nicht gefunden")
  void test_10() {
    when(supervisorRepository.findByGithubId(123)).thenReturn(Optional.empty());

    supervisorService.createTags(123, "a, b");

    verify(supervisorRepository, never()).save(any());
  }

  @Test
  @DisplayName("createTags – Nur Leerzeichen")
  void test_11() {
    when(supervisorRepository.findByGithubId(123)).thenReturn(Optional.of(supervisor));

    supervisorService.createTags(123, " ");

    verify(supervisorRepository, never()).save(any());
  }
}

