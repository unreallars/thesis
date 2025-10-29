package com.example.thesispp2.services;

import com.example.thesispp2.data.Supervisor;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class SupervisorService {
  Map<Long, Supervisor> supervisors = new HashMap<>();

  public Supervisor getSupervisorById(Long id) {
    Supervisor supervisor = new Supervisor();
    supervisor.setName("Example Supervisor");
    supervisor.setMail("Example Mail");
    return supervisor;
  }
}
