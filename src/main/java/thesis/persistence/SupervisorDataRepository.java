package thesis.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import thesis.persistence.dto.SupervisorDto;
import thesis.persistence.dto.SupervisorMatchDto;

public interface SupervisorDataRepository extends CrudRepository<SupervisorDto, Integer> {

  @NonNull List<SupervisorDto> findAll();

  Optional<SupervisorDto> findByGithubId(Integer id);

  @Query(
      """
      SELECT DISTINCT
          s.fach_id AS supervisor_id,
          s.username AS supervisor_username,
          COUNT(DISTINCT st.tag) AS match_score
      FROM supervisor s
      JOIN supervisor_tag st ON s.id = st.supervisor
      WHERE st.tag IN (:tags)
      GROUP BY s.fach_id, s.username
      ORDER BY COUNT(DISTINCT st.tag) DESC
      LIMIT 10
      """)
  List<SupervisorMatchDto> findMatchingSupervisors(@Param("tags") List<String> tags);

  Optional<SupervisorDto> findByFachId(UUID id);
}
