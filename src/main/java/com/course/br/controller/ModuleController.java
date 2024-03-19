package com.course.br.controller;

import com.course.br.dto.ModuleDto;
import com.course.br.entity.CourseEntity;
import com.course.br.entity.ModuleEntity;
import com.course.br.specification.SpecificationTemplate;
import com.course.br.usecase.CourseUseCase;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {
    private final ModuleUseCase moduleUseCase;

    private final CourseUseCase courseUseCase;

    ModuleController(ModuleUseCase moduleUseCase, CourseUseCase courseUseCase){
        this.moduleUseCase = moduleUseCase;
        this.courseUseCase = courseUseCase;
    }

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<Object> createModule(@PathVariable(value = "courseId") UUID courseId, @RequestBody @Valid ModuleDto moduleDto){
        Optional<CourseEntity> course = courseUseCase.findBydId(courseId);
        if(course.isPresent()){
            var module = new ModuleEntity();
            BeanUtils.copyProperties(moduleDto, module);
            module.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
            module.setCourse(course.get());
            ModuleEntity moduleCreated = moduleUseCase.save(module);
            return ResponseEntity.status(HttpStatus.CREATED).body(moduleCreated);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso informado não existe");
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> deleteModule(@PathVariable(value = "courseId") UUID courseId,
                                               @PathVariable(value = "moduleId") UUID moduleId){
        Optional<ModuleEntity> module = moduleUseCase.findModuleIntoCourse(courseId, moduleId);
        if(!module.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O curso ou o modulo informado não existem");
        }
        moduleUseCase.delete(module.get());
        return ResponseEntity.status(HttpStatus.OK).body("o modulo foi removido");
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> updateModule(@PathVariable(value = "courseId") UUID courseId,
                                               @PathVariable(value = "moduleId") UUID moduleId,
                                               @RequestBody @Valid ModuleDto moduleDto){
        Optional<ModuleEntity> module = moduleUseCase.findModuleIntoCourse(courseId, moduleId);
        if(!module.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O curso ou o modulo informado não existem");
        }
        ModuleEntity moduleEntity = module.get();
        moduleEntity.setTitle(moduleDto.title());
        moduleEntity.setDescription(moduleDto.description());

        ModuleEntity moduleUpdated = moduleUseCase.update(moduleEntity);

        return ResponseEntity.status(HttpStatus.OK).body(moduleUpdated);
    }

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<Page<ModuleEntity>> getAllModules(@PageableDefault(page = 0, size = 10, sort = "moduleId", direction = Sort.Direction.ASC)
                                                                Pageable pageable,
                                                            SpecificationTemplate.ModuleSpecification spec,
                                                            @PathVariable(value = "courseId") UUID courseId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(moduleUseCase.findAllByCourse(SpecificationTemplate.moduleCourseId(courseId).and(spec), pageable));
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> getOneModule(@PathVariable(value = "courseId") UUID courseId,
                                                            @PathVariable(value = "moduleId") UUID moduleId){
        Optional<ModuleEntity> module = moduleUseCase.findModuleIntoCourse(courseId, moduleId);
        if(module.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(module.get());

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O curso ou o modulo informado não existem");
    }
}
