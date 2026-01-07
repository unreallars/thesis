package thesis;

import org.springframework.boot.SpringApplication;

public class EntwicklungszeitApplication {

  static void main(String[] args) {
    SpringApplication.from(Thesispp2Application::main)
        .with(TestcontainersConfiguration.class)
        .run(args);
  }

}
