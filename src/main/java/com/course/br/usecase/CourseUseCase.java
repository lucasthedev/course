package com.course.br.usecase;

import com.course.br.entity.CourseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseUseCase {

    void delete(CourseEntity course);

    CourseEntity save(CourseEntity course);

    Optional<CourseEntity> findBydId(UUID courseId);

    CourseEntity update(CourseEntity courseEntity);

    List<CourseEntity> findAll();

}
