package thesis.web;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import thesis.helper.WithMockOAuth2User;
import thesis.service.InformationFileService;
import thesis.service.config.AppUserService;
import thesis.service.config.MethodSecurityConfig;
import thesis.service.config.SecurityConfig;

@WebMvcTest(InformationFileController.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class})
public class InformationFileControllerTest {

  @Autowired MockMvc mockMvc;
  @MockitoBean AppUserService appUserService;
  @MockitoBean InformationFileService informationFileService;

  @Test
  @DisplayName("Der Upload ist unangemeldet nicht erreichbar")
  void test_1() throws Exception {
    mockMvc.perform(get("/upload")).andExpect(status().is3xxRedirection());
    mockMvc.perform(post("/upload")).andExpect(status().isForbidden());
  }

  @Test
  @DisplayName("Der Upload ist als Student oder Admin nicht erreichbar")
  @WithMockOAuth2User(roles = {"STUDENT", "ADMIN"})
  void test_2() throws Exception {
    mockMvc.perform(get("/upload")).andExpect(status().isForbidden());
    mockMvc.perform(post("/upload").with(csrf())).andExpect(status().isForbidden());
  }

  @Test
  @DisplayName("Der Upload ist als Supervisor erreichbar")
  @WithMockOAuth2User(roles = "SUPERVISOR")
  void test_3() throws Exception {
    mockMvc.perform(get("/upload")).andExpect(status().isOk());
    mockMvc.perform(post("/upload").with(csrf())).andExpect(status().isOk());
  }

  @Test
  @DisplayName("Der Download ist angemeldet erreichbar")
  @WithMockOAuth2User(roles = "STUDENT")
  void test_4() throws Exception {
    UUID id = UUID.randomUUID();
    when(informationFileService.downloadFile(id))
        .thenReturn(new ResponseEntity<>(HttpStatusCode.valueOf(200)));
    mockMvc.perform(get("/dl/{id}", id)).andExpect(status().isOk());
  }

  @Test
  @DisplayName("Der Download ist unangemeldet nicht erreichbar")
  void test_5() throws Exception {
    UUID id = UUID.randomUUID();
    when(informationFileService.downloadFile(id))
        .thenReturn(new ResponseEntity<>(HttpStatusCode.valueOf(200)));
    mockMvc.perform(get("/dl/{id}", id)).andExpect(status().is3xxRedirection());
  }
}
