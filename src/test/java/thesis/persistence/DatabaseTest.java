package thesis.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jdbc.test.autoconfigure.DataJdbcTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import thesis.TestcontainersConfiguration;
import thesis.domain.Supervisor;
import thesis.domain.Topic;
import thesis.domain.User;
import thesis.service.SupervisorRepository;
import thesis.service.TopicRepository;
import thesis.service.UserRepository;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
public class DatabaseTest {

  @Autowired UserDataRepository userDataRepository;
  @Autowired SupervisorDataRepository supervisorDataRepository;
  @Autowired TopicDataRepository topicDataRepository;

  UserRepository userRepository;
  SupervisorRepository supervisorRepository;
  TopicRepository topicRepository;

  @BeforeEach
  void setUp() {
    userRepository = new UserRepositoryImpl(userDataRepository);
    supervisorRepository = new SupervisorRepositoryImpl(supervisorDataRepository);
    topicRepository = new TopicRepositoryImpl(topicDataRepository);
  }

  @Test
  @DisplayName("Die Datenbank kann Benutzer speichern und laden")
  void test1() {
    User user = new User(666, "TestUser");
    userRepository.save(user);
    Optional<User> loadedUser = userRepository.findByGithubId(666);
    assertThat(loadedUser).isPresent();
  }

  @Test
  @DisplayName("Die Datenbank kann Betreuer speichern und laden")
  void test2() {
    Supervisor supervisor = new Supervisor(666, "Test");
    supervisorRepository.save(supervisor);
    Optional<Supervisor> loadedUser = supervisorRepository.findByGithubId(666);
    assertThat(loadedUser).isPresent();
  }

  @Test
  @DisplayName("Die Datenbank kann Themen speichern und laden")
  void test3() {
    UUID id = UUID.randomUUID();
    Supervisor supervisor = new Supervisor(id, "Username", 666, "a@b.de", new ArrayList<>());
    supervisorRepository.save(supervisor);
    Topic topic = new Topic("Titel", "Test", id);
    topicRepository.save(topic);
    List<Topic> loadedUser = topicRepository.findBySupervisorId(id);
    assertThat(loadedUser).isNotEmpty();
  }
}
