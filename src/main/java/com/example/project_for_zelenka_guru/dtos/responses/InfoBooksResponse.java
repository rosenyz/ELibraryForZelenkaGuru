package com.example.project_for_zelenka_guru.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

// DTO для вывода всех книг
@Data
@AllArgsConstructor
public class InfoBooksResponse {
    private Long id;

    private String name;

    private String author;
}
