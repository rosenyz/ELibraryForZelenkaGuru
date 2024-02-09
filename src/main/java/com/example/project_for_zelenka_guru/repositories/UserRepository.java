package com.example.project_for_zelenka_guru.repositories;

import com.example.project_for_zelenka_guru.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// UserRepository позволяет сохранять/изменять/удалять данные о юзерах
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Функция проверяет есть ли пользователь с именем {username} в базе данных
    Boolean existsByUsername(String username);

    // Поиск юзера по юзернейму в базе данных ! Возвращает Optional<User> !
    Optional<User> findByUsername(String username);
}
