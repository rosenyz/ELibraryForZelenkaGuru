package com.example.project_for_zelenka_guru.controllers;

import com.example.project_for_zelenka_guru.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/create") // эндпоинт для добавления жанра
    public ResponseEntity<?> createGenre(@RequestParam(name = "genre") String name) {
        return genreService.createGenre(name);
    }

    @GetMapping // эндпоинт /api/v1/genres?limit=n&page=k для просмотра всех жанров, где n - лимит жанров на странице, а k - страница
    public ResponseEntity<?> getGenres(@RequestParam(name = "limit", required = false) Short limit,
                                       @RequestParam(name = "page", required = false) Short page)
    {
        return genreService.getGenres(limit, page);
    }

}
