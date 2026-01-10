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
    Supervisor supervisor = supervisorService.findById(user.getAttribute("id"));
    model.addAttribute("supervisor", supervisor);
    return "dashboard";
  }

  @PostMapping("/updateMail")
  public String updateSupervisors(@AuthenticationPrincipal OAuth2User user, String mail) {
    Supervisor supervisor = supervisorService.findById(user.getAttribute("id"));
    if (supervisor == null
        || supervisor.getMail().equals(mail)) {
      return "redirect:/dashboard";
    }
    supervisorService.save(new Supervisor(supervisor.getId(), supervisor.getMail(), mail));
    return "redirect:/dashboard";
  }
}
