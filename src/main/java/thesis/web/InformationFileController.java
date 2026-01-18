package thesis.web;

import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import thesis.service.InformationFileService;

@Controller
public class InformationFileController {
  private final InformationFileService informationFileService;

  public InformationFileController(InformationFileService informationFileService) {
    this.informationFileService = informationFileService;
  }

  @Secured("ROLE_SUPERVISOR")
  @GetMapping("/upload")
  public String upload() {
    return "upload";
  }

  @Secured("ROLE_SUPERVISOR")
  @PostMapping("/upload")
  public String upload(MultipartFile file, String title, String description,
                       @RequestParam(required = false) String targetType,
                       @RequestParam(required = false) String topicId,
                       @AuthenticationPrincipal OAuth2User user,
                       RedirectAttributes redirectAttributes) {
    String message =
        informationFileService.uploadFile(file, title, description, user, targetType, topicId);
    redirectAttributes.addFlashAttribute("message", message);

    return "redirect:/upload";
  }

  @GetMapping("/dl/{id}")
  public ResponseEntity<Resource> download(
      @PathVariable UUID id) {
    return informationFileService.downloadFile(id);
  }
}
