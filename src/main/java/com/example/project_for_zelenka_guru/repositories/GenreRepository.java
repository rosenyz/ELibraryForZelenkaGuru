package com.example.project_for_zelenka_guru.repositories;

import com.example.project_for_zelenka_guru.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Boolean existsByNameLikeIgnoreCase(String name);
}
