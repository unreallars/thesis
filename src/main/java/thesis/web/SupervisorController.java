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
import thesis.domain.dto.LinkForm;
import thesis.domain.dto.TagForm;
import thesis.domain.dto.TopicForm;
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

    EmailForm emailForm = new EmailForm();
    emailForm.setEmail(supervisor.mail());
    model.addAttribute("emailForm", emailForm);
    TagForm tagForm = new TagForm();
    model.addAttribute("tagForm", tagForm);
    return "dashboard";
  }

  @PostMapping("/updateMail")
  public String updateMail(@AuthenticationPrincipal OAuth2User user, @Valid EmailForm emailForm,
                           BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      TagForm tagForm = new TagForm();
      model.addAttribute("tagForm", tagForm);
      return "dashboard";
    }
    supervisorService.updateMail(user.getAttribute("id"), emailForm.getEmail());
    return "redirect:/dashboard";
  }

  @GetMapping("/addTopic")
  public String addTopic(Model model) {
    model.addAttribute("topicForm", new TopicForm());
    return "addtopic";
  }

  @PostMapping("/addTopic")
  public String addTopic(@AuthenticationPrincipal OAuth2User user,
                         RedirectAttributes redirectAttributes, @Valid TopicForm topicForm,
                         BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "addtopic";
    }
    String message = supervisorService.createTopic(topicForm.getTitle(), topicForm.getDescription(),
        user.getAttribute("id"), topicForm.getTags(), topicForm.getRequirements());
    redirectAttributes.addFlashAttribute("message", message);
    return "redirect:/addTopic";
  }

  @PostMapping("/addTags")
  public String addTags(@AuthenticationPrincipal OAuth2User user, @Valid TagForm tagForm,
                        BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      Supervisor supervisor = supervisorService.findByGithubId(user.getAttribute("id"));
      EmailForm emailForm = new EmailForm();
      emailForm.setEmail(supervisor.mail());
      model.addAttribute("emailForm", emailForm);
      return "dashboard";
    }
    supervisorService.createTags(user.getAttribute("id"), tagForm.getTags());
    return "redirect:/dashboard";
  }

  @GetMapping("/addLink")
  public String showForm(Model model) {
    model.addAttribute("linkForm", new LinkForm());
    return "addlink";
  }

  @PostMapping("/addLink")
  public String submitForm(RedirectAttributes redirectAttributes,
                           @AuthenticationPrincipal OAuth2User user, @Valid LinkForm linkForm,
                           BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "addlink";
    }

    String message = switch (linkForm.getTargetType()) {
      case "SUPERVISOR" -> supervisorService.createLink(user.getAttribute("id"), linkForm.getUrl(),
          linkForm.getLinkText());
      case "TOPIC" -> topicService.createLink(linkForm.getTopicId(), linkForm.getUrl(),
          linkForm.getLinkText());
      default -> "Kein gültiger Typ angegeben";
    };

    redirectAttributes.addFlashAttribute("message", message);
    return "redirect:/addLink";
  }
}
