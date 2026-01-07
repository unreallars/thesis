package thesis.web;

import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import thesis.domain.Supervisor;
import thesis.domain.Topic;
import thesis.domain.dto.SupervisorProfileDto;
import thesis.domain.dto.TopicProfileDto;
import thesis.service.InformationFileService;
import thesis.service.SupervisorService;
import thesis.service.TopicService;

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
    model.addAttribute("supervisors", supervisorService.findAll());
    model.addAttribute("topics", topicService.findAll());
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
  public String getTopic(@PathVariable UUID id, Model model) {
    Topic topic = topicService.findById(id);
    if (topic != null) {
      TopicProfileDto topicProfile = topicService.getTopic(id);
      model.addAttribute("topicprofile", topicProfile);
      return "topic";
    }
    return "redirect:/";
  }

  @GetMapping("/supervisor/{id}")
  public String getSupervisor(@PathVariable Integer id, Model model) {
    Supervisor supervisor = supervisorService.findById(id);
    if (supervisor != null) {
      SupervisorProfileDto supervisorProfile = supervisorService.getSupervisorProfile(id);
      model.addAttribute("supervisorprofile", supervisorProfile);
      return "supervisor";
    }
    return "redirect:/";
  }

  @GetMapping("/dl/{id}")
  public ResponseEntity<Resource> download(
      @PathVariable UUID id) {
    return informationFileService.downloadFile(id);
  }
}
