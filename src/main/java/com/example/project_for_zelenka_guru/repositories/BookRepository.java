package com.example.project_for_zelenka_guru.repositories;

import com.example.project_for_zelenka_guru.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
