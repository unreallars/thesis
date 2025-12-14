package thesis.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Secured("ROLE_ADMIN")
public class AdminController {

  @GetMapping("/manage")
  public String manage() {
    return "manage";
  }
}
