package thesis.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import thesis.domain.User;
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
  public String manage() {
    return "manage";
  }

  @PostMapping("/manage")
  public String makeSupervisor(Integer userId, RedirectAttributes redirectAttributes) {
    String message;
    User user = userService.findByGithubId(userId);
    if (user == null) {
      message = "User nicht gefunden";
    } else {
      message = supervisorService.createSupervisor(user.getGithubId(), user.getUsername());
    }
    redirectAttributes.addFlashAttribute("message", message);
    return "redirect:/manage";
  }
}
