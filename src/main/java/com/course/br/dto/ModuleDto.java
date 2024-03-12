package com.course.br.dto;

import jakarta.validation.constraints.NotBlank;

public record ModuleDto(@NotBlank String title, @NotBlank String description) {
}
