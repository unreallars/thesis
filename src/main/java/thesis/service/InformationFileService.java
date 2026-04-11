package thesis.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import thesis.domain.InformationFile;
import thesis.domain.Supervisor;
import thesis.domain.Topic;

@Service
public class InformationFileService {
  private final InformationFileRepository informationFileRepository;
  private final SupervisorService supervisorService;
  private final TopicService topicService;

  public InformationFileService(InformationFileRepository informationFileRepository,
                                SupervisorService supervisorService, TopicService topicService) {
    this.informationFileRepository = informationFileRepository;
    this.supervisorService = supervisorService;
    this.topicService = topicService;
  }


  public String uploadFile(MultipartFile file, String title, String description, OAuth2User user,
                           String targetType, String topicId) {

    try {
      if (file == null || file.getOriginalFilename() == null
          || file.getOriginalFilename().isBlank()) {
        return "Ungültige Datei";
      }
      String filename = file.getOriginalFilename();

      String fileType = filename.substring(filename.lastIndexOf('.') + 1);
      if (!fileType.toLowerCase().matches("pdf|zip|md")) {
        return "Ungültiger Dateityp";
      }

      String storedFilename = UUID.randomUUID() + "." + fileType;
      Path filePath = Path.of("./uploads/" + storedFilename);

      Supervisor supervisor = supervisorService.findByGithubId(user.getAttribute("id"));
      if (supervisor == null) {
        return "Betreuer nicht gefunden";
      }
      if ("SUPERVISOR".equals(targetType)) {
        InformationFile informationFile =
            InformationFile.createSupervisorInformationFile(fileType, filePath.toString(), title,
                description, supervisor.id());
        informationFileRepository.save(informationFile);

        file.transferTo(filePath);

      } else if ("TOPIC".equals(targetType) && !topicId.isEmpty()) {
        UUID uuid = UUID.fromString(topicId);
        Topic topic = topicService.findById(uuid);
        if (topic == null) {
          return "Das Thema existiert nicht";
        }
        if (!topic.getSupervisorId().equals(supervisor.id())) {
          return "Das angegebene Thema ist von einem anderen Betreuer";
        }
        InformationFile informationFile =
            InformationFile.createTopicInformationFile(fileType, filePath.toString(), title,
                description, topic.getId());
        informationFileRepository.save(informationFile);
        file.transferTo(filePath);

      } else {
        return "Es ist ein Fehler aufgetreten";
      }
    } catch (IllegalArgumentException e) {
      return "Die eingegebene Id ist ungültig";
    } catch (IOException e) {
      return "Fehler: " + e.getMessage();
    }
    return "Upload erfolgreich";
  }

  public ResponseEntity<Resource> downloadFile(UUID id) {
    try {
      InformationFile informationFile = informationFileRepository.findById(id).orElse(null);
      if (informationFile == null) {
        return ResponseEntity.notFound().build();
      }

      Path filePath = Path.of(informationFile.filePath());
      byte[] fileContent = Files.readAllBytes(filePath);
      ByteArrayResource resource = new ByteArrayResource(fileContent);
      HttpHeaders headers = new HttpHeaders();

      if (informationFile.fileType().equalsIgnoreCase("md")) {

        Parser parser = Parser.builder().build();
        Node document = parser.parse(new String(fileContent, StandardCharsets.UTF_8));
        String html = HtmlRenderer.builder().build().render(document);
        html = Jsoup.clean(html, Safelist.relaxed());

        resource = new ByteArrayResource(html.getBytes(StandardCharsets.UTF_8));

        headers.add(HttpHeaders.CONTENT_TYPE, "text/html; charset=UTF-8");
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
            "inline; filename=" + informationFile.title() + "." + informationFile.fileType());
      } else {
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=" + informationFile.title()
                + "." + informationFile.fileType());
      }
      return ResponseEntity.ok().headers(headers).body(resource);

    } catch (IOException e) {
      return ResponseEntity.notFound().build();
    }
  }
}