package com.course.br.repository;

import com.course.br.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<LessonEntity, UUID> {
    @Query(value = "select * from tb_lessons where module_module_id = :moduleId", nativeQuery = true)
    List<LessonEntity> findAllLessonsByModuleId(@Param("moduleId") UUID moduleId);

    @Query(value = "select * from tb_lessons where module_module_id = :moduleId and lesson_id = :lessonId",
            nativeQuery = true)
    Optional<LessonEntity> findLessonIntoModule(@Param("moduleId") UUID moduleId,
                                                @Param("lessonId") UUID lessonId);
}
