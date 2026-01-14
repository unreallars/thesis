package thesis.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    if (supervisor == null
        || supervisor.getMail().equals(mail)) {
      return "redirect:/dashboard";
    }
    supervisorService.save(
        new Supervisor(supervisor.getId(), supervisor.getUsername(), supervisor.getGithubId(), mail,
            supervisor.getSupervisorTags()));
    return "redirect:/dashboard";
  }

  @GetMapping("/addTopic")
  public String addTopic() {
    return "addtopic";
  }

  @PostMapping("addTopic")
  public String addTopic(@AuthenticationPrincipal OAuth2User user, String title,
                         String description) {
    Supervisor supervisor = supervisorService.findByGithubId(user.getAttribute("id"));
    if (supervisor == null) {
      return "redirect:/dashboard";
    }
    Topic topic = new Topic(title, description, supervisor.getId());
    topicService.save(topic);
    return "redirect:/dashboard";
  }
}
