package thesis;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class OnionArchitectureTest {


  private final JavaClasses klassen =
      new ClassFileImporter().importPackagesOf(Thesispp2Application.class);

  @Test
  @DisplayName("Thesis hat eine Onion-Architektur")
  public void rule1() {
    ArchRule rule = onionArchitecture()
        .domainModels("thesis.domain..")
        .domainServices("thesis.services..")
        .applicationServices("thesis.services..")
        .adapter("web", "thesis.web")
        .adapter("persistence", "thesis.repositories");

    rule.check(klassen);
  }
}
