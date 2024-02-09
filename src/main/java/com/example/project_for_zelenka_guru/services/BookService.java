package com.example.project_for_zelenka_guru.services;

import com.example.project_for_zelenka_guru.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
}
