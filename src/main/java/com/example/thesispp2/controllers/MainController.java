package com.example.thesispp2.controllers;

import com.example.thesispp2.data.Supervisor;
import com.example.thesispp2.data.Topic;
import com.example.thesispp2.services.SupervisorService;
import com.example.thesispp2.services.TopicService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {
  SupervisorService supervisorService;
  TopicService topicService;

  public MainController(SupervisorService supervisorService, TopicService topicService) {
    this.supervisorService = supervisorService;
    this.topicService = topicService;
  }

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("supervisors", supervisorService.getAllSupervisors());
    model.addAttribute("topics", topicService.getAllTopics());
    return "index";
  }

  @GetMapping("/match")
  public String match() {
    return "match";
  }

  @GetMapping("/account")
  public String account(OAuth2AuthenticationToken authentication, Model model) {
    OAuth2User user = authentication.getPrincipal();
    model.addAttribute("user", user);
    return "account";
  }

  @GetMapping("/login")
  public String login(Authentication authentication) {
    if (authentication != null && authentication.isAuthenticated()
        && !(authentication instanceof AnonymousAuthenticationToken)) {
      return "redirect:/";
    }
    return "login";
  }

  @GetMapping("/topics/{id}")
  public String getTopics(@PathVariable int id, Model model) {
    Topic topic = topicService.getTopicById(id);
    model.addAttribute("topic", topic);
    return "topics";
  }

  @GetMapping("/supervisors/{id}")
  public String getSupervisors(@PathVariable int id, Model model) {
    Supervisor supervisor = supervisorService.getSupervisorById(id);
    model.addAttribute("supervisor", supervisor);
    return "supervisors";
  }
}
