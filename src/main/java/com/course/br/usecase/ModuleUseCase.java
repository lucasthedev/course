package com.course.br.usecase;

import com.course.br.entity.ModuleEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleUseCase {
    void delete(ModuleEntity module);

    ModuleEntity save(ModuleEntity module);

    Optional<ModuleEntity> findModuleIntoCourse(UUID courseId, UUID moduleId);

    ModuleEntity update(ModuleEntity moduleEntity);

    List<ModuleEntity> findAllByCourse(UUID courseId);

}
