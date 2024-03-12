package com.course.br.usecase.impl;

import com.course.br.entity.LessonEntity;
import com.course.br.repository.LessonRepository;
import com.course.br.usecase.LessonUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LessonUseCaseImpl implements LessonUseCase {
    @Autowired
    private LessonRepository repository;

    @Override
    public LessonEntity save(LessonEntity lesson) {
        return repository.save(lesson);
    }

    @Override
    public Optional<LessonEntity> findLessonIntoModule(UUID moduleId, UUID lessonId) {
        return repository.findLessonIntoModule(moduleId, lessonId);
    }

    @Override
    public void delete(LessonEntity lessonEntity) {
        repository.delete(lessonEntity);
    }

    @Override
    public LessonEntity update(LessonEntity lessonEntity) {
        return repository.save(lessonEntity);
    }

    @Override
    public List<LessonEntity> findAllLessonsByModuleId(UUID moduleId) {
        return repository.findAllLessonsByModuleId(moduleId);
    }
}
