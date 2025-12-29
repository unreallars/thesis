package thesis.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import thesis.services.SupervisorService;
import thesis.services.config.AppUserService;
import thesis.services.config.MethodSecurityConfig;
import thesis.services.config.SecurityConfig;

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
  @WithMockUser(roles = "STUDENT")
  void test_2() throws Exception {
    mockMvc.perform(get("/dashboard")).andExpect(status().isForbidden());
  }
  @Test
  @DisplayName("Die Dashboardseite ist für Supervisor erreichbar")
  @WithMockUser(roles = "SUPERVISOR")
  void test_3() throws Exception {
    mockMvc.perform(get("/dashboard")).andExpect(status().isOk());
  }
}
