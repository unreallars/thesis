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


  public void uploadFile(MultipartFile file, String title, String description, OAuth2User user,
                         String targetType, String topicId) throws IOException {

    String filename = file.getOriginalFilename();
    if (filename == null || filename.isEmpty() || title.isEmpty()) {
      throw new IOException("Ungültiger Eingabe");
    }

    String fileType = filename.substring(filename.lastIndexOf('.') + 1);
    if (!fileType.matches("pdf|zip|md")) {
      throw new IOException("Ungültiger Dateityp");
    }

    String storedFilename = UUID.randomUUID() + "." + fileType;
    Path filePath = Path.of("./uploads/" + storedFilename);

    Supervisor supervisor = supervisorService.findByGithubId(user.getAttribute("id"));
    if (supervisor == null) {
      throw new IOException("Betreuer nicht gefunden");
    }
    if ("SUPERVISOR".equals(targetType)) {
      InformationFile informationFile =
          InformationFile.createSupervisorInformationFile(fileType, filePath.toString(), title,
              description,
              supervisor.getId());
      informationFileRepository.save(informationFile);
      file.transferTo(filePath);

    } else if ("TOPIC".equals(targetType) && topicId != null) {
      try {
        UUID uuid = UUID.fromString(topicId);
        Topic topic = topicService.findById(uuid);
        if (topic == null) {
          throw new IOException("Das Thema existiert nicht");
        }
        if (!topic.getSupervisorId().equals(supervisor.getId())) {
          throw new IOException("Das angegebene Thema ist von einem anderen Betreuer");
        }
        InformationFile informationFile =
            InformationFile.createTopicInformationFile(fileType, filePath.toString(), title,
                description, topic.getId());
        informationFileRepository.save(informationFile);
        file.transferTo(filePath);

      } catch (IllegalArgumentException e) {
        throw new IOException("Die eingegebene Id ist ungültig");
      }
    } else {
      throw new IOException("Es ist ein Fehler aufgetreten");
    }
  }

  public ResponseEntity<Resource> downloadFile(UUID id) {
    try {
      InformationFile informationFile = informationFileRepository.findById(id).orElse(null);
      if (informationFile == null) {
        return ResponseEntity.notFound().build();
      }

      Path filePath = Path.of(informationFile.getFilePath());
      byte[] fileContent = Files.readAllBytes(filePath);
      ByteArrayResource resource = new ByteArrayResource(fileContent);
      HttpHeaders headers = new HttpHeaders();

      if (informationFile.getFileType().equals("md")) {

        Parser parser = Parser.builder().build();
        Node document = parser.parse(new String(fileContent, StandardCharsets.UTF_8));
        String html = HtmlRenderer.builder().build().render(document);
        html = Jsoup.clean(html, Safelist.relaxed());

        resource = new ByteArrayResource(html.getBytes(StandardCharsets.UTF_8));

        headers.add(HttpHeaders.CONTENT_TYPE, "text/html; charset=UTF-8");
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
            "inline; filename=" + informationFile.getTitle() + "." + informationFile.getFileType());
      } else {
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=" + informationFile.getTitle() + "."
                + informationFile.getFileType());
      }
      return ResponseEntity.ok().headers(headers).body(resource);

    } catch (IOException e) {
      return ResponseEntity.notFound().build();
    }
  }
}