package com.example.thesispp2.services;

import com.example.thesispp2.config.AppConfig;
import org.springframework.stereotype.Component;

@Component
public class RoleService {
  private final SupervisorService supervisorService;
  private final AppConfig appConfig;

  public RoleService(SupervisorService supervisorService, AppConfig appConfig) {
    this.supervisorService = supervisorService;
    this.appConfig = appConfig;
  }

  public boolean isSupervisor(Integer id) {
    return supervisorService.isSupervisor(id);
  }

  public boolean isAdmin(Integer id) {
    return appConfig.getAdmins().contains(id);
  }

}
