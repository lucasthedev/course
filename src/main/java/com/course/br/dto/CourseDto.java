package com.course.br.dto;

import com.course.br.enums.CourseLevel;
import com.course.br.enums.CourseStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CourseDto(
        @NotBlank
        String name,
        @NotBlank
        String description,
        String imageUrl,
        @NotNull
        CourseStatus courseStatus,
        @NotNull
        UUID userInstructor,
        @NotNull
        CourseLevel courseLevel) {
}
