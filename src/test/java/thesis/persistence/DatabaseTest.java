package thesis.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import thesis.TestcontainersConfiguration;
import thesis.domain.User;
import thesis.service.UserRepository;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
public class DatabaseTest {

  @Autowired
  UserDataRepository dataRepository;

  UserRepository repository;

  @BeforeEach
  void setUp() {
    repository = new UserRepositoryImpl(dataRepository);
  }
  @Test
  @DisplayName("Die Datenbank kann Benutzer speichern und laden")
  void test1(){
    User user=new User(666,"TestUser");
    repository.save(user);
    Optional<User> loadedUser=repository.findByGithubId(666);
    assertThat(loadedUser).isPresent();
  }

}
