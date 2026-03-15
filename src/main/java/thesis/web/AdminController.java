package thesis.web;

import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import thesis.domain.User;
import thesis.domain.dto.SupervisorCreationForm;
import thesis.service.SupervisorService;
import thesis.service.UserService;

@Controller
@Secured("ROLE_ADMIN")
public class AdminController {
  private final UserService userService;
  private final SupervisorService supervisorService;

  public AdminController(UserService userService, SupervisorService supervisorService) {
    this.userService = userService;
    this.supervisorService = supervisorService;
  }

  @GetMapping("/manage")
  public String manage(Model model) {
    model.addAttribute("supervisorCreationForm", new SupervisorCreationForm());
    return "manage";
  }

  @PostMapping("/manage")
  public String makeSupervisor(RedirectAttributes redirectAttributes,
                               @Valid SupervisorCreationForm supervisorCreationForm,
                               BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "manage";
    }
    String message;
    User user = userService.findByGithubId(supervisorCreationForm.getUserGithubId());
    if (user == null) {
      message = "User nicht gefunden";
    } else {
      message = supervisorService.createSupervisor(user.githubId(), user.username());
    }
    redirectAttributes.addFlashAttribute("message", message);
    return "redirect:/manage";
  }
}
