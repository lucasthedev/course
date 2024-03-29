package com.course.br.dto;

import jakarta.validation.constraints.NotBlank;

public record LessonDto(@NotBlank String title, String description, @NotBlank String videoUrl) {
}
