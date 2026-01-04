package thesis.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import thesis.domain.SpecialField;
import thesis.domain.Supervisor;
import thesis.service.SupervisorRepository;

@Repository
public class SupervisorRepositoryImpl implements SupervisorRepository {
  private final Map<Integer, Supervisor> supervisorMap = new HashMap<>();

  public SupervisorRepositoryImpl() {
    List<Supervisor> supervisorList = List.of(
        new Supervisor(1, "Prof. Dr. Hacker", "hacker@b.de", List.of(new SpecialField("KI")),
            null, null),
        new Supervisor(4501, "Dr. Test", "test@b.de",
            List.of(new SpecialField("Programmierung"), new SpecialField("Datenbanken")),
            null,
            null),
        new Supervisor(456, "Schmidt", "schmidt@b.de",
            List.of(new SpecialField("Computernetze")), null,
            null),
        new Supervisor(184363268, "unrealLars", null, null, null, null)
    );
    for (Supervisor supervisor : supervisorList) {
      supervisorMap.put(supervisor.getId(), supervisor);
    }
  }

  @Override
  public Supervisor findSupervisorById(Integer id) {
    return supervisorMap.get(id);
  }

  @Override
  public void save(Supervisor supervisor) {
    supervisorMap.put(supervisor.getId(), supervisor);
  }

  @Override
  public List<Supervisor> getAllSupervisors() {
    return supervisorMap.values().stream().toList();
  }
}
