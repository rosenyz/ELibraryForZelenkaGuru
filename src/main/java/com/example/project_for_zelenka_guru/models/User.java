package com.example.project_for_zelenka_guru.models;

import com.example.project_for_zelenka_guru.models.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// модель User
@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password; // пароль в базе данных храниться в зашифрованном виде

    @Column(name = "active")
    private Boolean active; // если false = пользователь заблокирован

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "loaderUserId")
    private List<Book> books = new ArrayList<>(); // User в себе может хранить созданные им книги

    @Column(name = "date_of_create")
    private LocalDateTime dateOfCreate; // дата создания пользователя

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>(); // роли

    // Инициализация пользователя
    @PrePersist
    void init() {
        this.dateOfCreate = LocalDateTime.now();
    }
}
