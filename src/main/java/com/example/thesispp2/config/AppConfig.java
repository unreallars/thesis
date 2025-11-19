package com.example.thesispp2.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {

  private List<Integer> admins = new ArrayList<>();

  public List<Integer> getAdmins() {
    System.out.println(admins);
    return admins;
  }

  public void setAdmins(List<Integer> admins) {
    this.admins = admins;
  }
}

