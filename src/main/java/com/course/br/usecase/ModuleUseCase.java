package com.course.br.usecase;

import com.course.br.entity.ModuleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface ModuleUseCase {
    void delete(ModuleEntity module);

    ModuleEntity save(ModuleEntity module);

    Optional<ModuleEntity> findModuleIntoCourse(UUID courseId, UUID moduleId);

    ModuleEntity update(ModuleEntity moduleEntity);

    Page<ModuleEntity> findAllByCourse(Specification<ModuleEntity> spec, Pageable pageable);

    Optional<ModuleEntity> findModuleById(UUID moduleId);

}
