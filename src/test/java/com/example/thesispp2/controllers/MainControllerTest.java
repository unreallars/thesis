package com.example.thesispp2.controllers;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.example.thesispp2.config.MethodSecurityConfig;
import com.example.thesispp2.config.SecurityConfig;
import com.example.thesispp2.services.SupervisorService;
import com.example.thesispp2.services.TopicService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MainController.class)
@Import({SecurityConfig.class, MethodSecurityConfig.class})
public class MainControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  SupervisorService supervisorService;
  @MockitoBean
  TopicService topicService;

  @Test
  @DisplayName("Die Startseite ist erreichbar")
  void test_1() throws Exception {
    mockMvc.perform(get("/")).andExpect(view().name("index")).andExpect(status().isOk());
  }

  @Test
  @DisplayName("Die Startseite enthält die richtigen Daten")
  void test_2() {
    throw new RuntimeException("Not implemented yet");
  }
}
