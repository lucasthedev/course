package com.course.br.usecase.impl;

import com.course.br.repository.CourseRepository;
import com.course.br.usecase.CourseUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseUseCaseImpl implements CourseUseCase {
    @Autowired
    private CourseRepository repository;
}
