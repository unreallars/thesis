package com.example.thesispp2.services;

import com.example.thesispp2.data.Supervisor;
import com.example.thesispp2.data.Topic;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class TopicService {
  Map<Long, Supervisor> topics = new HashMap<>();

  public Topic getTopicById(Long id) {
    Topic topic = new Topic();
    topic.setTitle("Example Title");
    topic.setDescription("Example Description");
    return topic;
  }
}
