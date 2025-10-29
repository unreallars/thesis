package com.example.thesispp2.controllers;

import java.io.IOException;
import java.nio.file.Path;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class SupervisorController {
  @GetMapping("/upload")
  public String upload() {
    return "upload";
  }

  @PostMapping("/upload")
  public String upload(@RequestParam("file") MultipartFile file) {
    try {
      String name = file.getOriginalFilename();
      file.transferTo(Path.of("./upload_" + name));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return "redirect:/";
  }
}
