package thesis.web;

import static org.mockito.ArgumentMatchers.any;
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
import thesis.domain.Supervisor;
import thesis.helper.WithMockOAuth2User;
import thesis.service.SupervisorService;
import thesis.service.TopicService;
import thesis.service.config.AppUserService;
import thesis.service.config.MethodSecurityConfig;
import thesis.service.config.SecurityConfig;

@WebMvcTest(SupervisorController.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class})
public class SupervisorControllerTest {

  @Autowired MockMvc mockMvc;
  @MockitoBean AppUserService appUserService;
  @MockitoBean SupervisorService supervisorService;
  @MockitoBean TopicService topicService;

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
    when(supervisorService.findByGithubId(666666))
        .thenReturn(new Supervisor(666666, "Test"));
    mockMvc.perform(get("/dashboard")).andExpect(status().isOk());
  }

  @Test
  @DisplayName("Die Änderungsseiten ist für Supervisor erreichbar")
  @WithMockOAuth2User(roles = "SUPERVISOR")
  void test_4() throws Exception {
    when(supervisorService.findByGithubId(666666))
        .thenReturn(new Supervisor(666666, "Test"));

    mockMvc.perform(get("/addTopic")).andExpect(status().isOk());
    mockMvc.perform(get("/addLink")).andExpect(status().isOk());

    mockMvc
        .perform(post("/updateMail").param("email", "a@b.de").with(csrf()))
        .andExpect(status().is3xxRedirection());
    mockMvc.perform(post("/addTopic").with(csrf())).andExpect(status().isOk());
    mockMvc.perform(post("/addTags").with(csrf())).andExpect(status().isOk());
  }

  @Test
  @DisplayName("Das Hinzufügen von Links gibt die richtigen Messages zurück")
  @WithMockOAuth2User(roles = "SUPERVISOR")
  void test_5() throws Exception {
    when(supervisorService.findByGithubId(666666))
        .thenReturn(new Supervisor(666666, "Test"));
    when(supervisorService.createLink(any(), any(), any()))
        .thenReturn("Link erfolgreich zum Betreuer-Profil hinzugefügt");
    when(topicService.createLink(any(), any(), any()))
        .thenReturn("Link erfolgreich zum Thema hinzugefügt");

    mockMvc.perform(post("/addLink").with(csrf())).andExpect(status().isOk());
    mockMvc
        .perform(
            post("/addLink")
                .with(csrf())
                .param("targetType", "SUPERVISOR")
                .param("url", "https://a.de")
                .param("linkText", "Test"))
        .andExpect(status().is3xxRedirection())
        .andExpect(
            flash().attribute("message", "Link erfolgreich zum Betreuer-Profil hinzugefügt"));
    mockMvc
        .perform(
            post("/addLink")
                .with(csrf())
                .param("targetType", "TOPIC")
                .param("url", "https://a.de")
                .param("linkText", "Test"))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attribute("message", "Link erfolgreich zum Thema hinzugefügt"));
  }
}
