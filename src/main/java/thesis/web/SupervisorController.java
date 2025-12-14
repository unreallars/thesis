package thesis.web;

import java.io.IOException;
import java.nio.file.Path;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR"})
public class SupervisorController {
  @GetMapping("/upload")
  public String upload() {
    return "upload";
  }


  @GetMapping("/dashboard")
  public String dashboard() {
    return "dashboard";
  }
}
