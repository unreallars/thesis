package com.example.thesispp2.services;

import com.example.thesispp2.data.SpecialField;
import com.example.thesispp2.data.Supervisor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SupervisorService {
  List<Supervisor> supervisors = new ArrayList<>();

  public SupervisorService() {
    Supervisor s1 = new Supervisor();
    s1.setName("Prof. Dr. Hacker");
    s1.setId(1);
    SpecialField sf11 = new SpecialField();
    sf11.setName("KI");
    s1.setSpecialFields(List.of(sf11));
    supervisors.add(s1);

    Supervisor s2 = new Supervisor();
    s2.setId(4501);
    s2.setName("Dr. Test");
    SpecialField sf21 = new SpecialField();
    sf21.setName("Programmierung");
    SpecialField sf22 = new SpecialField();
    sf22.setName("Datenbanken");

    s2.setSpecialFields(List.of(sf21, sf22));
    supervisors.add(s2);

    Supervisor s3 = new Supervisor();
    s3.setId(456);
    s3.setName("Schmidt");
    SpecialField sf31 = new SpecialField();
    sf31.setName("Computernetze");
    s3.setSpecialFields(List.of(sf31));
    supervisors.add(s3);
  }

  public Supervisor getSupervisorById(Integer id) {
    return supervisors.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
  }

  public List<Supervisor> getAllSupervisors() {
    return supervisors;
  }

  public boolean isSupervisor(Integer id) {
    return supervisors.stream().anyMatch(s -> s.getId() == id);
  }
}
