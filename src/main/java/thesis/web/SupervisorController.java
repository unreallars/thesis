package thesis.web;

import java.io.IOException;
import java.nio.file.Path;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import thesis.services.SupervisorService;

@Controller
@Secured("ROLE_SUPERVISOR")
public class SupervisorController {
  private final SupervisorService supervisorService;

  public SupervisorController(SupervisorService supervisorService) {

    this.supervisorService = supervisorService;
  }


  @GetMapping("/dashboard")
  public String dashboard() {
    return "dashboard";
  }
}
