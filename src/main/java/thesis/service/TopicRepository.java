package thesis.service;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import thesis.domain.Topic;
import thesis.domain.dto.MatchResultDto;

public interface TopicRepository extends CrudRepository<Topic, UUID> {
  List<Topic> findAll();

  List<Topic> findBySupervisorId(Integer supervisorId);

  @Query("SELECT DISTINCT tr.name FROM topic_requirement tr JOIN topic ON tr.topic=topic.uuid")
  List<String> findAllDistinctTopicRequirementNames();

  @Query("""
          SELECT s.id as supervisor_id,
                 s.username as supervisor_username,
                 t.title as topic_title,
                 t.description as topic_description,
                 COUNT(DISTINCT tt.tag) * 10 as match_score
          FROM supervisor s
          JOIN topic t ON s.id = t.supervisor_id
          JOIN topic_tag tt ON t.uuid = tt.topic
          JOIN topic_requirement tr ON t.uuid = tr.topic
          WHERE LOWER(tt.tag) = ANY(string_to_array(:tagsCsv, ','))
          AND tr.name = ANY(string_to_array(:requirementsCsv, ','))
          GROUP BY s.id, s.username, t.uuid, t.title, t.description
          HAVING COUNT(DISTINCT tr.name) = (
              SELECT COUNT(*)
              FROM topic_requirement tr2
              WHERE tr2.topic = t.uuid
          )
          ORDER BY match_score DESC
          LIMIT 10
      """)
  List<MatchResultDto> findMatchingTopics(
      @Param("tagsCsv") String tagsCsv,
      @Param("requirementsCsv") String requirementsCsv
  );

}
