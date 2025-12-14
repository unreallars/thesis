package thesis.web;

import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import thesis.domain.InformationFile;
import thesis.domain.Supervisor;
import thesis.domain.Topic;
import thesis.services.InformationFileService;
import thesis.services.SupervisorService;
import thesis.services.TopicService;

@Controller
public class MainController {
  private final SupervisorService supervisorService;
  private final TopicService topicService;
  private final InformationFileService informationFileService;

  public MainController(SupervisorService supervisorService, TopicService topicService,
                        InformationFileService informationFileService) {
    this.supervisorService = supervisorService;
    this.topicService = topicService;
    this.informationFileService = informationFileService;
  }

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("supervisors", supervisorService.getAllSupervisors());
    model.addAttribute("topics", topicService.getAllTopics());
    return "index";
  }

  @GetMapping("/match")
  public String match() {
    return "match";
  }

  @GetMapping("/account")
  public String account(@AuthenticationPrincipal OAuth2User user, Model model) {
    model.addAttribute("user", user != null ? user.getAttribute("login") : null);
    return "account";
  }

  @GetMapping("/topic/{id}")
  public String getTopic(@PathVariable int id, Model model) {
    Topic topic = topicService.getTopicById(id);
    if (topic != null) {
      model.addAttribute("topic", topic);
      return "topic";
    }
    return "redirect:/";
  }

  @GetMapping("/supervisor/{id}")
  public String getSupervisor(@PathVariable int id, Model model) {
    Supervisor supervisor = supervisorService.getSupervisorById(id);
    if (supervisor != null) {
      model.addAttribute("supervisor", supervisor);
      List<InformationFile> files = informationFileService.getBySupervisor(id);
      model.addAttribute("files", files);
      return "supervisor";
    }
    return "redirect:/";
  }
}
