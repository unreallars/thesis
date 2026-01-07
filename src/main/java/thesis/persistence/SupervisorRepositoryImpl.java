package thesis.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import thesis.domain.Supervisor;
import thesis.service.SupervisorRepository;

@Repository
public class SupervisorRepositoryImpl implements SupervisorRepository {
  private final Map<Integer, Supervisor> supervisorMap = new HashMap<>();

  public SupervisorRepositoryImpl() {
    List.of(
        new Supervisor(1, "Prof. Dr. Hacker", "hacker@b.de", new ArrayList<>()),
        new Supervisor(4501, "Dr. Test", "test@b.de", new ArrayList<>()),
        new Supervisor(456, "Schmidt", "schmidt@b.de", new ArrayList<>()),
        new Supervisor(184363268, "unrealLars", "noreply@b.de", new ArrayList<>())
    ).forEach(this::save);
  }

  @Override
  public Supervisor findById(Integer id) {
    return supervisorMap.get(id);
  }

  @Override
  public void save(Supervisor supervisor) {
    supervisorMap.put(supervisor.getId(), supervisor);
  }

  @Override
  public List<Supervisor> findAll() {
    return supervisorMap.values().stream().toList();
  }
}
