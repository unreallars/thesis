package com.example.thesispp2.services;

import com.example.thesispp2.data.Requirement;
import com.example.thesispp2.data.Topic;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class TopicService {
  public TopicService() {
    Topic t1 = new Topic();
    t1.setId(2);
    t1.setTitle("Programmierung in Spring");
    Requirement r1 = new Requirement();
    r1.setName("Programmierung");
    t1.setRequirements(List.of(r1));
    topics.add(t1);

    Topic t2 = new Topic();
    t2.setId(35);
    t2.setTitle("Mikroarchitektur");
    Requirement r2 = new Requirement();
    r2.setName("Rechnerarchitektur");
    Requirement r3 = new Requirement();
    r3.setName("Programmierung");
    t2.setRequirements(List.of(r2, r3));
    topics.add(t2);
  }

  List<Topic> topics = new ArrayList<>();

  public Topic getTopicById(Integer id) {
    return topics.stream().filter(s -> Objects.equals(s.getId(), id)).findFirst().orElse(null);
  }

  public List<Topic> getAllTopics() {
    return topics;
  }
}
