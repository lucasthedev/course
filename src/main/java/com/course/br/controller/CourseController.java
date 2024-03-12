package com.course.br.controller;

import com.course.br.dto.CourseDto;
import com.course.br.entity.CourseEntity;
import com.course.br.usecase.CourseUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("/courses")
public class CourseController {

    private final CourseUseCase courseUseCase;

    CourseController(CourseUseCase courseUseCase){
        this.courseUseCase = courseUseCase;
    }

    @PostMapping
    public ResponseEntity<Object> createCourse(@RequestBody @Valid CourseDto courseDto){
        var course = new CourseEntity();
        BeanUtils.copyProperties(courseDto, course);
        course.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        course.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        CourseEntity courseEntity = courseUseCase.save(course);

        return ResponseEntity.status(HttpStatus.CREATED).body(courseEntity);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable(value = "courseId") UUID courseId){
        Optional<CourseEntity> course = courseUseCase.findBydId(courseId);
        if(!course.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O curso informado não existe");
        }
        courseUseCase.delete(course.get());
        return ResponseEntity.status(HttpStatus.OK).body("o curso foi removido");
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Object> updateCourse(@PathVariable(value = "courseId") UUID courseId,
                                               @RequestBody @Valid CourseDto courseDto){
        Optional<CourseEntity> course = courseUseCase.findBydId(courseId);
        if(!course.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O curso informado não existe");
        }
        CourseEntity courseEntity = course.get();
        courseEntity.setName(courseDto.name());
        courseEntity.setDescription(courseDto.description());
        courseEntity.setImageUrl(courseDto.imageUrl());
        courseEntity.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        courseEntity.setCourseStatus(courseDto.courseStatus());
        courseEntity.setCourseLevel(courseDto.courseLevel());
        courseEntity.setUserInstructor(courseDto.userInstructor());

        CourseEntity CourseUpdated = courseUseCase.update(courseEntity);

        return ResponseEntity.status(HttpStatus.OK).body(CourseUpdated);
    }

    @GetMapping
    public ResponseEntity<List<CourseEntity>> getAllCourses(){
        return ResponseEntity.status(HttpStatus.OK).body(courseUseCase.findAll());
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Object> getCourseById(@PathVariable(value = "courseId") UUID courseId){
        Optional<CourseEntity> course = courseUseCase.findBydId(courseId);
        if(course.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(course);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso informado não foi encontrado");
    }

}
