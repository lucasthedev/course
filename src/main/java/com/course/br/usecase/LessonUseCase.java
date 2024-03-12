package com.course.br.usecase;

import com.course.br.entity.LessonEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonUseCase {
    LessonEntity save(LessonEntity lesson);

    Optional<LessonEntity> findLessonIntoModule(UUID moduleId, UUID lessonId);

    void delete(LessonEntity lessonEntity);

    LessonEntity update(LessonEntity lessonEntity);

    List<LessonEntity> findAllLessonsByModuleId(UUID moduleId);

}
