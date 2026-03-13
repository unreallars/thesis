package thesis.web;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import thesis.domain.User;
import thesis.helper.WithMockOAuth2User;
import thesis.service.SupervisorService;
import thesis.service.UserService;
import thesis.service.config.AppUserService;
import thesis.service.config.MethodSecurityConfig;
import thesis.service.config.SecurityConfig;

@WebMvcTest(AdminController.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class})
public class AdminControllerTest {
  @Autowired
  MockMvc mockMvc;
  @MockitoBean
  AppUserService appUserService;
  @MockitoBean
  SupervisorService supervisorService;
  @MockitoBean
  UserService userService;

  @Test
  @DisplayName("Die Verwaltungsseite ist unangemeldet nicht erreichbar")
  void test_1() throws Exception {
    mockMvc.perform(get("/manage")).andExpect(status().is3xxRedirection());
  }

  @Test
  @DisplayName("Die Verwaltungsseite funktioniert für Admins")
  @WithMockOAuth2User(roles = "ADMIN")
  void test_2() throws Exception {
    User user = new User(66, "Username");
    when(userService.findByGithubId(66)).thenReturn(user);
    when(supervisorService.createSupervisor(66, "Username")).thenReturn(
        "User wurde erfolgreich die Rolle Betreuer zugewiesen");

    mockMvc.perform(get("/manage")).andExpect(status().isOk());
    mockMvc.perform(post("/manage").with(csrf())).andExpect(status().isOk());
    mockMvc.perform(post(("/manage")).param("userGithubId", "66").with(csrf()))
        .andExpect(status().is3xxRedirection()).andExpect(
            flash().attribute("message", "User wurde erfolgreich die Rolle Betreuer zugewiesen"));
  }

  @Test
  @DisplayName("Die Verwaltungsseite ist für Studenten und Supervisor nicht erreichbar")
  @WithMockOAuth2User(roles = {"STUDENT", "SUPERVISOR"})
  void test_3() throws Exception {
    mockMvc.perform(get("/manage")).andExpect(status().isForbidden());
    mockMvc.perform(post("/manage").with(csrf())).andExpect(status().isForbidden());
  }
}
