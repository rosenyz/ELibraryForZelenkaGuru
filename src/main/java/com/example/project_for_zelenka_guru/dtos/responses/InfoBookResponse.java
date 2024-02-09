package com.example.project_for_zelenka_guru.dtos.responses;

import com.example.project_for_zelenka_guru.models.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

// DTO для вывода подробной информации о книге
@Data
@AllArgsConstructor
public class InfoBookResponse {
    private Long id;

    private String name;

    private String author;

    private String description;

    private List<Genre> genres;
}
