package com.example.project_for_zelenka_guru.repositories;

import com.example.project_for_zelenka_guru.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// GenreRepository позволяет сохранять/изменять/удалять данные о жанрах
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    // Функция проверяет, есть ли жанр под именем {name} в базе данных
    Boolean existsByNameLikeIgnoreCase(String name);
}
