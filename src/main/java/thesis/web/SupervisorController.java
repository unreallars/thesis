package thesis.web;

import java.util.UUID;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import thesis.domain.Supervisor;
import thesis.domain.Topic;
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
    return "dashboard";
  }

  @PostMapping("/updateMail")
  public String updateSupervisors(@AuthenticationPrincipal OAuth2User user, String mail) {
    Supervisor supervisor = supervisorService.findByGithubId(user.getAttribute("id"));
    if (supervisor != null && !supervisor.getMail().equals(mail)) {
      supervisorService.updateMail(supervisor.getId(), mail);
    }
    return "redirect:/dashboard";
  }

  @GetMapping("/addTopic")
  public String addTopic() {
    return "addtopic";
  }

  @PostMapping("/addTopic")
  public String addTopic(@AuthenticationPrincipal OAuth2User user, String title, String description,
                         RedirectAttributes redirectAttributes, String tags, String requirements) {
    Supervisor supervisor = supervisorService.findByGithubId(user.getAttribute("id"));
    if (supervisor == null) {
      redirectAttributes.addFlashAttribute("message", "Betreuer nicht gefunden");
      return "redirect:/addTopic";
    }
    redirectAttributes.addFlashAttribute("message", "Thema erfolgreich hinzugefügt");
    topicService.createTopic(title, description, supervisor.getId(), tags, requirements);

    return "redirect:/addTopic";
  }

  @PostMapping("/addTag")
  public String addTags(@AuthenticationPrincipal OAuth2User user, String tags) {
    Supervisor supervisor = supervisorService.findByGithubId(user.getAttribute("id"));
    if (supervisor != null && tags != null && !tags.isBlank()) {
      supervisorService.createTags(supervisor.getId(), tags);
    }

    return "redirect:/dashboard";
  }

  @GetMapping("/addLink")
  public String showForm() {
    return "addlink";
  }

  @PostMapping("/addLink")
  public String submitForm(@RequestParam String url, @RequestParam String linkText,
                           @RequestParam String targetType,
                           @RequestParam(required = false) String topicId,
                           RedirectAttributes redirectAttributes,
                           @AuthenticationPrincipal OAuth2User user) {

    Supervisor supervisor = supervisorService.findByGithubId(user.getAttribute("id"));
    if (supervisor == null) {
      redirectAttributes.addFlashAttribute("message", "Betreuer nicht gefunden");
      return "redirect:/addLink";
    }
    if ("SUPERVISOR".equals(targetType)) {
      redirectAttributes.addFlashAttribute("message",
          "Link erfolgreich zum Betreuer-Profil hinzugefügt");
      supervisorService.createLink(supervisor.getId(), url, linkText);

    } else if ("TOPIC".equals(targetType) && topicId != null) {
      try {
        UUID uuid = UUID.fromString(topicId);
        Topic topic = topicService.findById(uuid);
        if (topic == null || topic.getSupervisorId() == null) {
          redirectAttributes.addFlashAttribute("message", "Das angegebene Thema existiert nicht");
        } else if (!topic.getSupervisorId().equals(supervisor.getId())) {
          redirectAttributes.addFlashAttribute("message",
              "Das angegebene Thema ist von einem anderen Betreuer");
        } else {
          topicService.createLink(uuid, url, linkText);
          redirectAttributes.addFlashAttribute("message", "Link erfolgreich zum Thema hinzugefügt");
        }
      } catch (IllegalArgumentException e) {
        redirectAttributes.addFlashAttribute("message", "Die eingegebene Id ist ungültig");
      }
    } else {
      redirectAttributes.addFlashAttribute("message", "Es ist ein Fehler aufgetreten.");
    }

    return "redirect:/addLink";
  }
}
