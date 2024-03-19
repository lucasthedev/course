package com.course.br.usecase;

import com.course.br.entity.LessonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonUseCase {
    LessonEntity save(LessonEntity lesson);

    Optional<LessonEntity> findLessonIntoModule(UUID moduleId, UUID lessonId);

    void delete(LessonEntity lessonEntity);

    LessonEntity update(LessonEntity lessonEntity);

    Page<LessonEntity> findAllLessonsByModuleId(Specification<LessonEntity> spec, Pageable pageable);

}
