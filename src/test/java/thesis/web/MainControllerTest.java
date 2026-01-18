package thesis.web;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.UUID;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import thesis.domain.Supervisor;
import thesis.domain.Topic;
import thesis.domain.dto.SupervisorProfile;
import thesis.domain.dto.TopicProfile;
import thesis.helper.WithMockOAuth2User;
import thesis.service.SupervisorService;
import thesis.service.TopicService;
import thesis.service.config.AppUserService;
import thesis.service.config.MethodSecurityConfig;
import thesis.service.config.SecurityConfig;

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
  @DisplayName("Startseite ist unangemeldet erreichbar")
  void test_1() throws Exception {
    mockMvc.perform(get("/")).andExpect(view().name("index")).andExpect(status().isOk());
  }

  @Test
  @DisplayName("Account-Seite und Matching sind unangemeldet nicht erreichbar")
  void test_2() throws Exception {
    mockMvc.perform(get("/account")).andExpect(status().is3xxRedirection());
    mockMvc.perform(get("/match")).andExpect(status().is3xxRedirection());
    mockMvc.perform(post("/match")).andExpect(status().isForbidden());
  }

  @Test
  @DisplayName("Account-Seite und Matching sind angemeldet erreichbar")
  @WithMockOAuth2User(roles = "STUDENT")
  void test_3() throws Exception {
    mockMvc.perform(get("/account")).andExpect(status().isOk());
    mockMvc.perform(get("/match")).andExpect(status().isOk());
    mockMvc.perform(post("/match").with(csrf())).andExpect(status().is3xxRedirection());
  }

  @Test
  @WithMockOAuth2User(roles = "STUDENT")
  @DisplayName("Startseite zeigt angemeldeten Benutzern Themen und Profile")
  void test_4() throws Exception {
    String htmlText = mockMvc.perform(get("/")).andReturn().getResponse().getContentAsString();
    Document html = Jsoup.parse(htmlText);
    assertThat(html.select("section#topics")).isNotEmpty();
    assertThat(html.select("section#supervisors")).isNotEmpty();
  }

  @Test
  @DisplayName("Supervisor- und Topic-Seite sind angemeldet erreichbar")
  @WithMockOAuth2User(roles = "STUDENT")
  void test_5() throws Exception {
    UUID uuid = UUID.randomUUID();
    Supervisor supervisor = new Supervisor(0, "Username", "a@b.de");
    when(supervisorService.getSupervisorProfile(uuid)).thenReturn(
        new SupervisorProfile(supervisor, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
    Topic topic = new Topic("Topic", "Desc", UUID.randomUUID());
    when(topicService.getTopic(uuid)).thenReturn(
        new TopicProfile(topic, new ArrayList<>(), new ArrayList<>()));

    mockMvc.perform(get("/topic/{id}", uuid)).andExpect(status().isOk());
    mockMvc.perform(get("/supervisor/{id}", uuid)).andExpect(status().isOk());

    mockMvc.perform(get("/topic/{id}", UUID.randomUUID())).andExpect(status().is3xxRedirection());
    mockMvc.perform(get("/supervisor/{id}", UUID.randomUUID())).andExpect(status().is3xxRedirection());
  }
}
