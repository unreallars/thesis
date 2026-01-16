package thesis.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import thesis.domain.Supervisor;
import thesis.domain.dto.SupervisorMatchDto;

public interface SupervisorRepository extends CrudRepository<Supervisor, Integer> {
  List<Supervisor> findAll();

  Optional<Supervisor> findByGithubId(Integer id);

  @Query
      ("""
              SELECT s.id as supervisor_id,
                     s.username as supervisor_username,
                     COUNT(DISTINCT st.tag) * 10 as match_score
              FROM supervisor s
              JOIN supervisor_tag st ON s.id = st.supervisor
              WHERE st.tag = ANY(string_to_array(:tagsCsv, ','))
              GROUP BY s.id, s.username
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
}
