package com.course.br.repository;

import com.course.br.entity.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<ModuleEntity, UUID> {
    @Query(value = "select * from tb_modules where course_course_id = :courseId", nativeQuery = true)
    List<ModuleEntity> findAllModulesByCourseId(@Param("courseId") UUID courseId);

    @Query(value = "select * from tb_modules where course_course_id = :courseId and module_id = :moduleId",
            nativeQuery = true)
    Optional<ModuleEntity> findModuleIntoCourse(@Param("courseId") UUID courseId,
                                                @Param("moduleId") UUID moduleId);
}
