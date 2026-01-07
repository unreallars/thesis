package thesis.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import thesis.domain.Supervisor;
import thesis.helper.WithMockOAuth2User;
import thesis.service.SupervisorService;
import thesis.service.config.AppUserService;
import thesis.service.config.MethodSecurityConfig;
import thesis.service.config.SecurityConfig;

@WebMvcTest(SupervisorController.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class})
public class SupervisorControllerTest {
  @Autowired
  MockMvc mockMvc;
  @MockitoBean
  AppUserService appUserService;
  @MockitoBean
  SupervisorService supervisorService;

  @Test
  @DisplayName("Die Dashboardseite ist unangemeldet nicht erreichbar")
  void test_1() throws Exception {
    mockMvc.perform(get("/dashboard")).andExpect(status().is3xxRedirection());
  }

  @Test
  @DisplayName("Die Dashboardseite ist für Studenten nicht erreichbar")
  @WithMockOAuth2User(roles = "STUDENT")
  void test_2() throws Exception {
    mockMvc.perform(get("/dashboard")).andExpect(status().isForbidden());
  }

  @Test
  @DisplayName("Die Dashboardseite ist für Supervisor erreichbar")
  @WithMockOAuth2User(roles = "SUPERVISOR")
  void test_3() throws Exception {
    when(supervisorService.findById(666666)).thenReturn(
        new Supervisor(666666, "Test", "test@example.com"));
    mockMvc.perform(get("/dashboard")).andExpect(status().isOk());
  }
}
