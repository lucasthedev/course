package com.course.br.usecase.impl;

import com.course.br.repository.LessonRepository;
import com.course.br.usecase.LessonUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonUseCaseImpl implements LessonUseCase {
    @Autowired
    private LessonRepository repository;
}
