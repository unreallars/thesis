package thesis.web;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import thesis.domain.Supervisor;
import thesis.domain.Topic;
import thesis.domain.dto.MatchResultDto;
import thesis.domain.dto.SupervisorMatchDto;
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
  public String match(Model model) {
    model.addAttribute("requirements", topicService.findAllRequirements());
    return "match";
  }

  @PostMapping("/match")
  public String match(@RequestParam(value = "completedRequirements", required = false)
                      List<String> completedRequirements,
                      @RequestParam("interestsInput") String interestsInput,
                      RedirectAttributes redirectAttributes) {
    List<String> studentTags = List.of();
    if (completedRequirements == null) {
      completedRequirements = List.of();
    }
    if (interestsInput != null && !interestsInput.isBlank()) {
      studentTags = Arrays.stream(interestsInput.split(","))
          .map(String::trim).filter(s -> !s.isEmpty()).toList();
    }

    List<MatchResultDto> matches =
        topicService.findMatchingTopics(studentTags, completedRequirements);
    List<SupervisorMatchDto> matches2 =
        supervisorService.findMatchingSupervisors(studentTags);
    redirectAttributes.addFlashAttribute("result", true);
    redirectAttributes.addFlashAttribute("matches", matches);
    redirectAttributes.addFlashAttribute("matches2", matches2);
    return "redirect:/match";
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
