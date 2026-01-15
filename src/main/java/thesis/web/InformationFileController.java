package thesis.web;

import java.io.IOException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import thesis.service.InformationFileService;

@Controller
@Secured("ROLE_SUPERVISOR")
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
                       @RequestParam String targetType,
                       @RequestParam(required = false) String topicId,
                       @AuthenticationPrincipal OAuth2User user,
                       RedirectAttributes redirectAttributes) {

    try {
      informationFileService.uploadFile(file, title, description, user, targetType, topicId);
      redirectAttributes.addFlashAttribute("message", "Upload erfolgreich");
    } catch (IOException e) {
      redirectAttributes.addFlashAttribute("message",
          "Fehler: " + e.getMessage());
    }
    return "redirect:/upload";
  }
}
