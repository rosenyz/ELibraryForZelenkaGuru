package com.example.project_for_zelenka_guru.repositories;

import com.example.project_for_zelenka_guru.models.Book;
import com.example.project_for_zelenka_guru.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// BookRepository позволяет сохранять/изменять/удалять данные о книгах
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByGenresContains(Genre genre);

    // К сожалению, эту функцию проще записать никак нельзя, т.к. Spring Jpa использует маски для поиска :(
    // Функция для поиска книг по автору или имени (регистр любой) и жанру
    List<Book> findBooksByAuthorContainingIgnoreCaseOrNameContainingIgnoreCaseAndGenresContains(String author, String name, Genre genres);

    // К сожалению, эту функцию проще записать никак нельзя, т.к. Spring Jpa использует маски для поиска :(
    // Функция для поиска книг по автору или имени (регистр любой)
    List<Book> findBooksByAuthorContainingIgnoreCaseOrNameContainingIgnoreCase(String author, String name);
}
