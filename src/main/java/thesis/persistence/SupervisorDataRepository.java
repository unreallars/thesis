package thesis.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import thesis.persistence.dto.SupervisorDto;
import thesis.persistence.dto.SupervisorMatchDto;

public interface SupervisorDataRepository extends CrudRepository<SupervisorDto, Integer> {
  @NonNull
  List<SupervisorDto> findAll();

  Optional<SupervisorDto> findByGithubId(Integer id);

  @Query
      ("""
              SELECT s.fach_id as supervisor_id,
                     s.username as supervisor_username,
                     COUNT(DISTINCT st.tag) * 10 as match_score
              FROM supervisor s
              JOIN supervisor_tag st ON s.id = st.supervisor
              WHERE st.tag = ANY(string_to_array(:tagsCsv, ','))
              GROUP BY s.fach_id, s.username
              HAVING COUNT(DISTINCT st.tag) = (
                  SELECT cardinality(string_to_array(:tagsCsv, ','))
                  WHERE cardinality(string_to_array(:tagsCsv, ',')) > 0
              )
              ORDER BY match_score DESC
              LIMIT 10
          """)
  List<SupervisorMatchDto> findMatchingSupervisors(
      @Param("tagsCsv") String tagsCsv
  );

  Optional<SupervisorDto> findByFachId(UUID id);
}
