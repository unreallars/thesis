package thesis.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.multipart.MultipartFile;
import thesis.domain.InformationFile;
import thesis.domain.Supervisor;
import thesis.domain.Topic;

class InformationFileServiceTest {


  @Test
  @DisplayName("Falscher Dateiname wird erkannt")
  void test_01() {
    OAuth2User oAuth2User = mock(OAuth2User.class);
    MultipartFile multipartFile = mock(MultipartFile.class);
    when(multipartFile.getOriginalFilename()).thenReturn(null);
    InformationFileService informationFileService = new InformationFileService(null, null, null);
    String result =
        informationFileService.uploadFile(multipartFile, "Titel", "Beschreibung", oAuth2User,
            "SUPERVISOR", null);

    assertThat(result).isEqualTo("Ungültige Eingabe");
  }

  @Test
  @DisplayName("Falscher Dateityp wird erkannt")
  void test_2() {
    MultipartFile multipartFile = mock(MultipartFile.class);
    OAuth2User oAuth2User = mock(OAuth2User.class);
    when(multipartFile.getOriginalFilename()).thenReturn("file.txt");
    InformationFileService informationFileService = new InformationFileService(null, null, null);
    String result =
        informationFileService.uploadFile(multipartFile, "Titel", "Beschreibung", oAuth2User,
            "SUPERVISOR", null);

    assertThat(result).isEqualTo("Ungültiger Dateityp");
  }

  @Test
  @DisplayName("Falsche Betreuer-Id wird erkannt")
  void test_03() {
    SupervisorService supervisorService = mock(SupervisorService.class);
    MultipartFile multipartFile = mock(MultipartFile.class);
    OAuth2User oAuth2User = mock(OAuth2User.class);
    when(multipartFile.getOriginalFilename()).thenReturn("file.pdf");
    when(oAuth2User.getAttribute("id")).thenReturn(66);
    when(supervisorService.findByGithubId(66)).thenReturn(null);
    InformationFileService informationFileService =
        new InformationFileService(null, supervisorService, null);
    String result =
        informationFileService.uploadFile(multipartFile, "Titel", "Beschreibung", oAuth2User,
            "SUPERVISOR", null);

    assertEquals("Betreuer nicht gefunden", result);
  }

  @Test
  @DisplayName("Upload funktioniert für Betreuer")
  void test_04() throws Exception {
    InformationFileRepository informationFileRepository = mock(InformationFileRepository.class);
    SupervisorService supervisorService = mock(SupervisorService.class);
    MultipartFile multipartFile = mock(MultipartFile.class);
    OAuth2User oAuth2User = mock(OAuth2User.class);

    when(multipartFile.getOriginalFilename()).thenReturn("file.pdf");
    when(oAuth2User.getAttribute("id")).thenReturn(66);

    Supervisor supervisor = new Supervisor(66, "Username", "Mail");
    when(supervisorService.findByGithubId(66)).thenReturn(supervisor);

    InformationFileService informationFileService =
        new InformationFileService(informationFileRepository, supervisorService, null);

    String result =
        informationFileService.uploadFile(multipartFile, "Titel", "Beschreibung", oAuth2User,
            "SUPERVISOR", null);

    assertEquals("Upload erfolgreich", result);
    verify(informationFileRepository).save(any(InformationFile.class));
    verify(multipartFile).transferTo(any(Path.class));
  }
  @Test
  @DisplayName("Upload funktioniert für Themen")
  void test_07() throws Exception {
    InformationFileRepository informationFileRepository = mock(InformationFileRepository.class);
    TopicService topicService = mock(TopicService.class);
    SupervisorService supervisorService = mock(SupervisorService.class);
    MultipartFile multipartFile = mock(MultipartFile.class);
    OAuth2User oAuth2User = mock(OAuth2User.class);

    when(multipartFile.getOriginalFilename()).thenReturn("file.pdf");
    when(oAuth2User.getAttribute("id")).thenReturn(66);
    UUID id=UUID.randomUUID();

    Supervisor supervisor = new Supervisor(66, "Username", "Mail");

    when(topicService.findById(id)).thenReturn(new Topic("Titel","Desc",supervisor.getId()));
    when(supervisorService.findByGithubId(66)).thenReturn(supervisor);

    InformationFileService informationFileService =
        new InformationFileService(informationFileRepository, supervisorService, topicService);

    String result =
        informationFileService.uploadFile(multipartFile, "Titel", "Beschreibung", oAuth2User,
            "TOPIC", id.toString());

    assertEquals("Upload erfolgreich", result);
    verify(informationFileRepository).save(any(InformationFile.class));
    verify(multipartFile).transferTo(any(Path.class));
  }

  @Test
  @DisplayName("PDF-Download funktioniert")
  void test_05() throws Exception {
    InformationFileRepository informationFileRepository = mock(InformationFileRepository.class);
    SupervisorService supervisorService = mock(SupervisorService.class);

    Path temp = Files.createTempFile("test-file", ".pdf");
    Files.writeString(temp, "PDF content");

    InformationFile infoFile =
        InformationFile.createSupervisorInformationFile("pdf", temp.toString(), "test-file", "Desc",
            UUID.randomUUID());

    when(informationFileRepository.findById(infoFile.getId())).thenReturn(Optional.of(infoFile));
    InformationFileService informationFileService =
        new InformationFileService(informationFileRepository, supervisorService, null);

    ResponseEntity<Resource> response = informationFileService.downloadFile(infoFile.getId());

    assertThat(response.getStatusCode().value()).isEqualTo(200);

    HttpHeaders headers = response.getHeaders();
    assertThat(headers.getFirst(HttpHeaders.CONTENT_TYPE)).isEqualTo("application/octet-stream");
    assertThat(headers.getFirst(HttpHeaders.CONTENT_DISPOSITION)).startsWith(
        "attachment; filename=test-file.pdf");

    Files.deleteIfExists(temp);
  }

  @Test
  @DisplayName("Markdown-Download funktioniert")
  void test_06() throws Exception {
    InformationFileRepository informationFileRepository = mock(InformationFileRepository.class);
    SupervisorService supervisorService = mock(SupervisorService.class);

    Path temp = Files.createTempFile("test-md", ".md");
    String mdContent = "# Überschrift\n\nText";
    Files.writeString(temp, mdContent);

    InformationFile infoFile =
        InformationFile.createSupervisorInformationFile("md", temp.toString(), "test-md", "Desc",
            UUID.randomUUID());

    when(informationFileRepository.findById(infoFile.getId())).thenReturn(Optional.of(infoFile));
    InformationFileService informationFileService =
        new InformationFileService(informationFileRepository, supervisorService, null);
    ResponseEntity<Resource> response = informationFileService.downloadFile(infoFile.getId());

    assertThat(response.getStatusCode().value()).isEqualTo(200);

    HttpHeaders headers = response.getHeaders();
    assertThat(headers.getFirst(HttpHeaders.CONTENT_TYPE)).isEqualTo("text/html; charset=UTF-8");
    assertThat(headers.getFirst(HttpHeaders.CONTENT_DISPOSITION)).startsWith(
        "inline; filename=test-md.md");

    Files.deleteIfExists(temp);
  }
}

