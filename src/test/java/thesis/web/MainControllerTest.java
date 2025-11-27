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
import thesis.services.config.AppUserService;
import thesis.services.config.MethodSecurityConfig;
import thesis.services.config.SecurityConfig;
import thesis.services.SupervisorService;
import thesis.services.TopicService;

@WebMvcTest(MainController.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class})
public class MainControllerTest {

  @Autowired
  MockMvc mockMvc;
  @MockitoBean
  AppUserService appUserService;
  @MockitoBean
  SupervisorService supervisorService;
  @MockitoBean
  TopicService topicService;

  @Test
  @DisplayName("Startseite ist für jeden erreichbar")
  void test_1() throws Exception {
    mockMvc.perform(get("/")).andExpect(view().name("index")).andExpect(status().isOk());
  }
  @Test
  @DisplayName("Account-Seite ist unangemeldet nicht erreichbar")
  void test_2() throws Exception {
    mockMvc.perform(get("/account")).andExpect(status().is3xxRedirection());
  }
  @Test
  @DisplayName("Account-Seite ist angemeldet erreichbar")
  @WithMockUser()
  void test_3() throws Exception {
    mockMvc.perform(get("/account")).andExpect(status().isOk());
  }

  @Test
  @DisplayName("Startseite enthält die richtigen Daten")
  void test_4() {
    throw new RuntimeException("Not implemented yet");
  }
}
