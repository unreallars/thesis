package thesis.web;

import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import thesis.domain.Supervisor;
import thesis.domain.dto.EmailForm;
import thesis.service.SupervisorService;
import thesis.service.TopicService;

@Controller
@Secured("ROLE_SUPERVISOR")
public class SupervisorController {
  private final SupervisorService supervisorService;
  private final TopicService topicService;

  public SupervisorController(SupervisorService supervisorService, TopicService topicService) {

    this.supervisorService = supervisorService;
    this.topicService = topicService;
  }


  @GetMapping("/dashboard")
  public String dashboard(@AuthenticationPrincipal OAuth2User user, Model model) {
    Supervisor supervisor = supervisorService.findByGithubId(user.getAttribute("id"));
    model.addAttribute("supervisor", supervisor);
    EmailForm emailForm = new EmailForm();
    emailForm.setEmail(supervisor.getMail());
    model.addAttribute("emailForm", emailForm);
    return "dashboard";
  }

  @PostMapping("/updateMail")
  public String updateMail(@AuthenticationPrincipal OAuth2User user, @Valid EmailForm emailForm,
                           BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "dashboard";
    }
    supervisorService.updateMail(user.getAttribute("id"), emailForm.getEmail());
    return "redirect:/dashboard";
  }

  @GetMapping("/addTopic")
  public String addTopic() {
    return "addtopic";
  }

  @PostMapping("/addTopic")
  public String addTopic(@AuthenticationPrincipal OAuth2User user, String title, String description,
                         RedirectAttributes redirectAttributes, String tags, String requirements) {

    String message =
        supervisorService.createTopic(title, description, user.getAttribute("id"), tags,
            requirements);
    redirectAttributes.addFlashAttribute("message", message);
    return "redirect:/addTopic";
  }

  @PostMapping("/addTag")
  public String addTags(@AuthenticationPrincipal OAuth2User user, String tags) {
    supervisorService.createTags(user.getAttribute("id"), tags);
    return "redirect:/dashboard";
  }

  @GetMapping("/addLink")
  public String showForm() {
    return "addlink";
  }

  @PostMapping("/addLink")
  public String submitForm(String url, String linkText, String targetType, String topicId,
                           RedirectAttributes redirectAttributes,
                           @AuthenticationPrincipal OAuth2User user) {
    String message;
    if ("SUPERVISOR".equals(targetType)) {
      message = supervisorService.createLink(user.getAttribute("id"), url, linkText);
    } else if ("TOPIC".equals(targetType)) {
      message = topicService.createLink(topicId, url, linkText);
    } else {
      message = "Kein gültiger Typ angegeben";
    }
    redirectAttributes.addFlashAttribute("message", message);
    return "redirect:/addLink";
  }
}
