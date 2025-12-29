package thesis.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import thesis.services.config.AppUserService;
import thesis.services.config.MethodSecurityConfig;
import thesis.services.config.SecurityConfig;

@WebMvcTest(AdminController.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class})
public class AdminControllerTest {
  @Autowired
  MockMvc mockMvc;
  @MockitoBean
  AppUserService appUserService;
  @Test
  @DisplayName("Die Verwaltungsseite ist unangemeldet nicht erreichbar")
  void test_1() throws Exception {
    mockMvc.perform(get("/manage")).andExpect(status().is3xxRedirection());
  }
  @Test
  @DisplayName("Die Verwaltungsseite ist für Admins erreichbar")
  @WithMockUser(roles = "ADMIN")
  void test_2() throws Exception {
    mockMvc.perform(get("/manage")).andExpect(status().isOk());
  }
  @Test
  @DisplayName("Die Verwaltungsseite ist für Studenten und Supervisor nicht erreichbar")
  @WithMockUser(roles = {"STUDENT","SUPERVISOR"})
  void test_3() throws Exception {
    mockMvc.perform(get("/manage")).andExpect(status().isForbidden());
  }
}
