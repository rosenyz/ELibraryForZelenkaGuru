package com.example.project_for_zelenka_guru.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// модель Book
@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "loader_user_id")
    private Long loaderUserId; // id того, кто загрузил информацию о книге

    @Column(name = "date_of_load")
    private LocalDateTime dateOfLoad; // дата информации о книге

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "description", columnDefinition = "text", length = 1000)
    private String description;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private List<Genre> genres = new ArrayList<>(); // жанры, привязанные к книге

    // конструктор для класса Book
    public Book(String name, String author, String description, Long loaderUserId) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.loaderUserId = loaderUserId;
    }

    // Инициализация книги
    @PrePersist
    public void init() {
        this.dateOfLoad = LocalDateTime.now();
    } // устанавливается дата загрузки
}
