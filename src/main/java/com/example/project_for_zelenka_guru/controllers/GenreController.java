package com.example.project_for_zelenka_guru.controllers;

import com.example.project_for_zelenka_guru.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
public class GenreController {
    // внедрение зависимости BookService ( dependency injection )
    // логика всех эндпоинтов описана в BookService
    private final GenreService genreService;

    // создание жанра для книг, только аутентифицированный пользователь
    @GetMapping("/create")
    public ResponseEntity<?> createGenre(@RequestParam(name = "genre") String name) {
        return genreService.createGenre(name);
    }

    // /api/v1/genres?limit=n&page=k для просмотра всех жанров
    // где n - лимит жанров на странице, а k - номер страницы
    @GetMapping
    public ResponseEntity<?> getGenres(@RequestParam(name = "limit", required = false) Short limit,
                                       @RequestParam(name = "page", required = false) Short page)
    {
        return genreService.getGenres(limit, page);
    }

}
