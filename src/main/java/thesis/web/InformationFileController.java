package thesis.web;

import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import thesis.domain.dto.UploadForm;
import thesis.service.InformationFileService;

@Controller
public class InformationFileController {

  private final InformationFileService informationFileService;

  public InformationFileController(InformationFileService informationFileService) {
    this.informationFileService = informationFileService;
  }

  @Secured("ROLE_SUPERVISOR")
  @GetMapping("/upload")
  public String upload(Model model) {
    model.addAttribute("uploadForm", new UploadForm());
    return "upload";
  }

  @Secured("ROLE_SUPERVISOR")
  @PostMapping("/upload")
  public String upload(
      @Valid UploadForm uploadForm,
      BindingResult bindingResult,
      @AuthenticationPrincipal OAuth2User user,
      RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      return "upload";
    }
    String message =
        informationFileService.uploadFile(
            uploadForm.getFile(),
            uploadForm.getTitle(),
            uploadForm.getDescription(),
            user,
            uploadForm.getTargetType(),
            uploadForm.getTopicId());
    redirectAttributes.addFlashAttribute("message", message);

    return "redirect:/upload";
  }

  @GetMapping("/dl/{id}")
  public ResponseEntity<Resource> download(@PathVariable UUID id) {
    return informationFileService.downloadFile(id);
  }
}
