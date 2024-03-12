package com.course.br.usecase.impl;

import com.course.br.entity.CourseEntity;
import com.course.br.entity.ModuleEntity;
import com.course.br.repository.CourseRepository;
import com.course.br.repository.LessonRepository;
import com.course.br.repository.ModuleRepository;
import com.course.br.usecase.CourseUseCase;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseUseCaseImpl implements CourseUseCase {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Transactional
    @Override
    public void delete(CourseEntity course) {
        List<ModuleEntity> modules = moduleRepository.findAllModulesByCourseId(course.getCourseId());

        if(!modules.isEmpty()){
            for(ModuleEntity module: modules){
                var lessons = lessonRepository.findAllLessonsByModuleId(module.getModuleId());
                if(!lessons.isEmpty()){
                    lessonRepository.deleteAll(lessons);
                }
            }
            moduleRepository.deleteAll(modules);
        }
        courseRepository.delete(course);
    }

    @Override
    public CourseEntity save(CourseEntity course) {
        return courseRepository.save(course);
    }

    @Override
    public Optional<CourseEntity> findBydId(UUID courseId) {
        return courseRepository.findById(courseId);
    }

    @Override
    public CourseEntity update(CourseEntity courseEntity) {
        return courseRepository.save(courseEntity);
    }

    @Override
    public List<CourseEntity> findAll() {
        return courseRepository.findAll();
    }
}
