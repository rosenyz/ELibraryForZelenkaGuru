package com.example.project_for_zelenka_guru.models.enums;

import org.springframework.security.core.GrantedAuthority;

// Роли для пользователя, возможность добавить роль администратора и в дальнейшем сделать панель администратора
public enum Role implements GrantedAuthority {
    ROLE_USER; // заготовка для роли администратора

    @Override
    public String getAuthority() {
        return name();
    }
}
