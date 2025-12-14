package thesis.web;

import java.io.IOException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import thesis.services.InformationFileService;

@Controller
public class InformationFileController {
  private final InformationFileService informationFileService;

  public InformationFileController(InformationFileService informationFileService) {
    this.informationFileService = informationFileService;
  }

  @GetMapping("/upload")
  public String upload() {
    return "upload";
  }

  @PostMapping("/upload")
  public String upload(MultipartFile file, String title, String description,
                       @AuthenticationPrincipal
                       OAuth2User user,
                       RedirectAttributes redirectAttributes) {
    try {
      informationFileService.store(file, title, description, user);
      redirectAttributes.addFlashAttribute("message", "Upload erfolgreich");
    } catch (IOException e) {
      redirectAttributes.addFlashAttribute("message",
          "Fehler: " + e.getMessage());
    }
    return "redirect:/upload";
  }

  @GetMapping("/dl/{id}")
  public ResponseEntity<Resource> download(
      @PathVariable String id) {
    return informationFileService.getById(id);
  }
}
