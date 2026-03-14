package thesis.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import thesis.persistence.dto.TopicDto;

public interface TopicDataRepository extends CrudRepository<TopicDto, Integer> {
  @NonNull
  List<TopicDto> findAll();

  List<TopicDto> findBySupervisorId(UUID supervisorId);

  @Query("SELECT DISTINCT tr.name FROM topic_requirement tr JOIN topic ON tr.topic=topic.id")
  List<String> findAllDistinctTopicRequirementNames();

  @Query("""
          SELECT s.fach_id as supervisor_id,
                 s.username as supervisor_username,
                 t.title as topic_title,
                 t.description as topic_description,
                 COUNT(DISTINCT tt.tag) * 10 as match_score
          FROM supervisor s
          JOIN topic t ON s.fach_id = t.supervisor_id
          JOIN topic_tag tt ON t.id = tt.topic
          JOIN topic_requirement tr ON t.id = tr.topic
          WHERE LOWER(tt.tag) = ANY(string_to_array(:tagsCsv, ','))
          AND tr.name = ANY(string_to_array(:requirementsCsv, ','))
          GROUP BY s.fach_id, s.username, t.id, t.title, t.description
          HAVING COUNT(DISTINCT tr.name) = (
              SELECT COUNT(*)
              FROM topic_requirement tr2
              WHERE tr2.topic = t.id
          )
          ORDER BY match_score DESC
          LIMIT 10
      """)
  List<MatchResultDto> findMatchingTopics(
      @Param("tagsCsv") String tagsCsv,
      @Param("requirementsCsv") String requirementsCsv
  );

  Optional<TopicDto> findByFachId(UUID id);

}
