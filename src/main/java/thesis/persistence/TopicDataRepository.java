package thesis.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import thesis.persistence.dto.TopicDto;
import thesis.persistence.dto.TopicMatchDto;

public interface TopicDataRepository extends CrudRepository<TopicDto, Integer> {
  @NonNull List<TopicDto> findAll();

  List<TopicDto> findBySupervisorId(UUID supervisorId);

  @Query("SELECT DISTINCT tr.name FROM topic_requirement tr ORDER BY tr.name")
  List<String> findAllTopicRequirements();

  @Query("""
      SELECT topic_id, topic_title, topic_description
      FROM (
          SELECT DISTINCT
              t.fach_id AS topic_id,
              t.title AS topic_title,
              t.description AS topic_description,
              t.id AS topic_internal_id
          FROM topic t
          WHERE EXISTS (
              SELECT 1 FROM topic_tag tt
              WHERE tt.topic = t.id AND tt.tag IN (:tags)
          )
          AND (
              SELECT COUNT(*) FROM topic_requirement tr
              WHERE tr.topic = t.id
          ) = (
              SELECT COUNT(*) FROM topic_requirement tr2
              WHERE tr2.topic = t.id AND tr2.name IN (:requirements)
          )
      ) ranked_topics
      ORDER BY (
          SELECT COUNT(DISTINCT tt2.tag)
          FROM topic_tag tt2
          WHERE tt2.topic = ranked_topics.topic_internal_id AND tt2.tag IN (:tags)
      ) DESC, topic_title
      LIMIT 10
      """)
  List<TopicMatchDto> findMatchingTopics(@Param("tags") List<String> tags,
                                         @Param("requirements") List<String> requirements);

  Optional<TopicDto> findByFachId(UUID id);

}
