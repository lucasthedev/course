package com.course.br.usecase.impl;

import com.course.br.repository.ModuleRepository;
import com.course.br.usecase.ModuleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleUseCaseImpl implements ModuleUseCase {
    @Autowired
    private ModuleRepository repository;
}
