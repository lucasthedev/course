package com.course.br.usecase;

import com.course.br.entity.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface CourseUseCase {

    void delete(CourseEntity course);

    CourseEntity save(CourseEntity course);

    Optional<CourseEntity> findBydId(UUID courseId);

    CourseEntity update(CourseEntity courseEntity);

    Page<CourseEntity> findAll( Pageable pageable);

}
