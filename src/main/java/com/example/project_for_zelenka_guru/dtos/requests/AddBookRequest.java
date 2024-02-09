package com.example.project_for_zelenka_guru.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AddBookRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String author;

    @NotBlank
    private String description;

    @NotNull
    private List<Long> genres;
}
