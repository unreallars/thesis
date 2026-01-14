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

@Service
public class InformationFileService {
  private final InformationFileRepository informationFileRepository;
  private final SupervisorService supervisorService;

  public InformationFileService(InformationFileRepository informationFileRepository,
                                SupervisorService supervisorService) {
    this.informationFileRepository = informationFileRepository;
    this.supervisorService = supervisorService;
  }


  public void uploadFile(MultipartFile file, String title, String description, OAuth2User user)
      throws IOException {
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

    Integer supervisorGithubId = user.getAttribute("id");
    Supervisor supervisor = supervisorService.findByGithubId(supervisorGithubId);

    InformationFile informationFile =
        new InformationFile(fileType, filePath.toString(), title, description, supervisor.getId());
    informationFileRepository.save(informationFile);
    file.transferTo(filePath);
  }

  public ResponseEntity<Resource> downloadFile(UUID id) {
    try {
      InformationFile informationFile = informationFileRepository.findById(id).orElse(null);
      if (informationFile == null) {
        return ResponseEntity.notFound().build();
      }

      Path filePath =
          Path.of(informationFile.getFilePath());
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
