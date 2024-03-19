package com.course.br.controller;

import com.course.br.dto.LessonDto;
import com.course.br.entity.LessonEntity;
import com.course.br.entity.ModuleEntity;
import com.course.br.specification.SpecificationTemplate;
import com.course.br.usecase.LessonUseCase;
import com.course.br.usecase.ModuleUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonController {
    private final LessonUseCase lessonUseCase;
    private final ModuleUseCase moduleUseCase;

    public LessonController(LessonUseCase lessonUseCase, ModuleUseCase moduleUseCase) {
        this.lessonUseCase = lessonUseCase;
        this.moduleUseCase = moduleUseCase;
    }

    @PostMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<Object> createLesson(@PathVariable(value = "moduleId") UUID moduleId, @RequestBody @Valid LessonDto lessonDto){
        Optional<ModuleEntity> module = moduleUseCase.findModuleById(moduleId);

        if(module.isPresent()){
            LessonEntity lesson = new LessonEntity();
            BeanUtils.copyProperties(lessonDto, lesson);
            lesson.setModule(module.get());
            lesson.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
            return ResponseEntity.status(HttpStatus.CREATED).body(lessonUseCase.save(lesson));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O modulo informado não existe!");
    }

    @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> deleteLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                               @PathVariable(value = "lessonId") UUID lessonId){
        Optional<LessonEntity> lesson = lessonUseCase.findLessonIntoModule(moduleId, lessonId);
        if(!lesson.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O modulo ou a lição não existem");
        }
        lessonUseCase.delete(lesson.get());
        return ResponseEntity.status(HttpStatus.OK).body("a lição foi removida");
    }

    @PutMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> updateLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                               @PathVariable(value = "lessonId") UUID lessonId,
                                               @RequestBody @Valid LessonDto lessonDto){
        Optional<LessonEntity> lesson = lessonUseCase.findLessonIntoModule(moduleId, lessonId);
        if(!lesson.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O modulo ou a lição não existem");
        }
        LessonEntity lessonEntity = lesson.get();
        lessonEntity.setTitle(lessonDto.title());
        lessonEntity.setDescription(lessonDto.description());
        lessonEntity.setVideoUrl(lessonDto.videoUrl());

        LessonEntity lessonUpdated = lessonUseCase.update(lessonEntity);

        return ResponseEntity.status(HttpStatus.OK).body(lessonUpdated);
    }

    @GetMapping("/modules/{moduleId}/lessons")
    public Page<LessonEntity> getAllLessons(@PathVariable(value = "moduleId") UUID moduleId,
                                            @PageableDefault(page = 0, size = 10, sort = "lessonId", direction = Sort.Direction.ASC)
                                            Pageable pageable,
                                            SpecificationTemplate.LessonSpecification spec){
        return lessonUseCase.findAllLessonsByModuleId(SpecificationTemplate.lessonModuleId(moduleId).and(spec), pageable);
    }

    @GetMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> getOneLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                               @PathVariable(value = "lessonId") UUID lessonId){
        Optional<LessonEntity> lesson = lessonUseCase.findLessonIntoModule(moduleId, lessonId);
        if(!lesson.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O modulo ou a lição não existem");
        }

        return ResponseEntity.status(HttpStatus.OK).body(lesson.get());
    }
}
