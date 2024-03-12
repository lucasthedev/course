package com.course.br.usecase.impl;

import com.course.br.entity.ModuleEntity;
import com.course.br.repository.LessonRepository;
import com.course.br.repository.ModuleRepository;
import com.course.br.usecase.ModuleUseCase;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ModuleUseCaseImpl implements ModuleUseCase {
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private LessonRepository lessonRepository;

    @Transactional
    @Override
    public void delete(ModuleEntity module) {
        var lessons = lessonRepository.findAllLessonsByModuleId(module.getModuleId());
        if(!lessons.isEmpty()){
            lessonRepository.deleteAll(lessons);
        }
        moduleRepository.delete(module);
    }

    @Override
    public ModuleEntity save(ModuleEntity module) {
        return moduleRepository.save(module);
    }

    @Override
    public Optional<ModuleEntity> findModuleIntoCourse(UUID courseId, UUID moduleId) {
        return moduleRepository.findModuleIntoCourse(courseId, moduleId);
    }

    @Override
    public ModuleEntity update(ModuleEntity moduleEntity) {
        return moduleRepository.save(moduleEntity);
    }

    @Override
    public List<ModuleEntity> findAllByCourse(UUID courseId) {
        return moduleRepository.findAllModulesByCourseId(courseId);
    }

}
