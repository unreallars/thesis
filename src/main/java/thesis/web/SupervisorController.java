package thesis.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import thesis.domain.Supervisor;
import thesis.service.SupervisorService;

@Controller
@Secured("ROLE_SUPERVISOR")
public class SupervisorController {
  private final SupervisorService supervisorService;

  public SupervisorController(SupervisorService supervisorService) {

    this.supervisorService = supervisorService;
  }


  @GetMapping("/dashboard")
  public String dashboard(@AuthenticationPrincipal OAuth2User user, Model model) {
    Supervisor supervisor = supervisorService.getSupervisorById(user.getAttribute("id"));
    model.addAttribute("supervisor", supervisor);
    return "dashboard";
  }

  @PostMapping("/updateSupervisor")
  public String updateSupervisors(@AuthenticationPrincipal OAuth2User user, String username,
                                  String mail) {
    supervisorService.updateSupervisor(user.getAttribute("id"), username, mail);
    System.out.println("updateSupervisors: " + username);
    return "redirect:/dashboard";
  }
}
