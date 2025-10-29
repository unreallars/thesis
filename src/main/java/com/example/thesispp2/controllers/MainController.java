package com.example.thesispp2.controllers;

import com.example.thesispp2.data.Supervisor;
import com.example.thesispp2.data.Topic;
import com.example.thesispp2.services.SupervisorService;
import com.example.thesispp2.services.TopicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {
  SupervisorService supervisorService;
  TopicService topicService;

  public MainController(SupervisorService supervisorService, TopicService topicService) {
    this.supervisorService = supervisorService;
    this.topicService = topicService;
  }

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/match")
  public String match() {
    return "match";
  }

  @GetMapping("/results")
  public String results() {
    return "match";
  }

  @GetMapping("/topics/{id}")
  public String getTopics(@PathVariable long id, Model model) {
    Topic topic = topicService.getTopicById(id);
    model.addAttribute("topic", topic);
    return "topics";
  }

  @GetMapping("/supervisors/{id}")
  public String getSupervisors(@PathVariable long id, Model model) {
    Supervisor supervisor = supervisorService.getSupervisorById(id);
    model.addAttribute("supervisor", supervisor);
    return "supervisors";
  }
}
