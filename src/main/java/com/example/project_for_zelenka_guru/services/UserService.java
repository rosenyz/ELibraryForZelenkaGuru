package com.example.project_for_zelenka_guru.services;

import com.example.project_for_zelenka_guru.models.User;
import com.example.project_for_zelenka_guru.repositories.UserRepository;
import com.example.project_for_zelenka_guru.repositories.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    // внедрение зависимостей ( dependency injection )
    private final UserRepository userRepository;

    // поиск юзера по авторизации ( principal ), если не найден, то возвращаем null
    // в каждой функции написана проверка на null, поэтому в нужный момент бэк кинет пользователя на 401
    public User findUserByPrincipal(Principal principal) {
        return userRepository.findByUsername(principal.getName()).orElse(null);
    }

    // определение смотрите в UserRepository
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    // сохранение юзера
    public void save(User user) {
        userRepository.save(user);
    }

    // перегруженный метод из UserDetailsService, билдит пользователя при авторизации
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Пользователь не найден!"));
        return UserDetailsImpl.build(user);
    }
}
