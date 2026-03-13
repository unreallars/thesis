package thesis.web;

import java.util.List;
import java.util.UUID;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import thesis.domain.dto.MatchResult;
import thesis.domain.dto.SupervisorMatch;
import thesis.domain.dto.SupervisorProfile;
import thesis.domain.dto.TopicProfile;
import thesis.service.SupervisorService;
import thesis.service.TopicService;

@Controller
public class MainController {
  private final SupervisorService supervisorService;
  private final TopicService topicService;

  public MainController(SupervisorService supervisorService, TopicService topicService) {
    this.supervisorService = supervisorService;
    this.topicService = topicService;
  }

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("supervisors", supervisorService.findAll());
    model.addAttribute("topics", topicService.findAll());
    return "index";
  }

  @GetMapping("/match")
  public String match(Model model) {
    model.addAttribute("requirements", topicService.findAllRequirements());
    return "match";
  }

  @PostMapping("/match")
  public String match(@RequestParam(value = "completedRequirements", required = false)
                      List<String> completedRequirements, String interestsInput,
                      RedirectAttributes redirectAttributes) {

    List<MatchResult> matches =
        topicService.findMatchingTopics(interestsInput, completedRequirements);
    List<SupervisorMatch> matches2 =
        supervisorService.findMatchingSupervisors(interestsInput);
    redirectAttributes.addFlashAttribute("result", true);
    redirectAttributes.addFlashAttribute("matches", matches);
    redirectAttributes.addFlashAttribute("matches2", matches2);
    return "redirect:/match";
  }

  @GetMapping("/account")
  public String account(@AuthenticationPrincipal OAuth2User user, Model model) {
    model.addAttribute("username", user.getAttribute("login"));
    model.addAttribute("githubId", user.getAttribute("id"));
    return "account";
  }

  @GetMapping("/topic/{id}")
  public String getTopic(@PathVariable UUID id, Model model) {
    TopicProfile topicProfile = topicService.getTopic(id);
    if (topicProfile != null) {
      model.addAttribute("topicprofile", topicProfile);
      return "topic";
    }
    return "redirect:/";
  }

  @GetMapping("/supervisor/{id}")
  public String getSupervisor(@PathVariable UUID id, Model model) {
    SupervisorProfile supervisorProfile = supervisorService.getSupervisorProfile(id);
    if (supervisorProfile != null) {
      model.addAttribute("supervisorprofile", supervisorProfile);
      return "supervisor";
    }
    return "redirect:/";
  }
}
