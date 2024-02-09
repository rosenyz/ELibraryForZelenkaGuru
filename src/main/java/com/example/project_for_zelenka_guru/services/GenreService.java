package com.example.project_for_zelenka_guru.services;

import com.example.project_for_zelenka_guru.models.Genre;
import com.example.project_for_zelenka_guru.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreService {
    private final GenreRepository genreRepository;

    public ResponseEntity<?> createGenre(String name) {
        if (name == null || name.length() <= 2) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Название жанра не может быть пустым или меньше 2 символов.");
        }

        if (genreRepository.existsByNameLikeIgnoreCase(name)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Жанр с таким именем уже существует.");
        }

        Genre genre = new Genre();

        // Сеттер с отформатированным названием жанра ( с большой буквы )
        genre.setName(name.substring(0, 1).toUpperCase() + name.substring(1));

        genreRepository.save(genre);
        log.info("Saved genre with id = {} , name = '{}'", genre.getId(), genre.getName());

        return ResponseEntity.ok("Жанр успешно добавлен.");
    }

    public ResponseEntity<?> getGenres(Short limit, Short page)
    {
        List<Genre> genres = genreRepository.findAll();
        List<Genre> showGenres = new ArrayList<>(); // список для отсортированных жанров по странице и лимитам

        if (genres.isEmpty()) {
            log.info("List genres isEmpty = {}", true);

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Ни одного жанра не было добавлено");
        }

        if (limit != null && page == null) { page = 1; }

        if (limit == null || page < 1) {
            log.info("Found all genres");

            return ResponseEntity.ok(genres);
        }

        if (genres.size() <= limit) {
            log.info("Found all genres");

            return ResponseEntity.ok(genres);
        }

        short allowedPages = (short) Math.ceil((double) genres.size() / limit);

        if (page > allowedPages || page == 1) {
            for (short i = 0; i < limit; i++) {
                showGenres.add(genres.get(i));
            }

            log.info("Found genres with (page > allowedPages), limit = {}", limit);

            return ResponseEntity.ok(showGenres);
        }

        for (int i = page * limit; i < page * limit + limit; i++) {
            if (i - limit < genres.size())
                showGenres.add(genres.get(i - limit));
        }

        log.info("Found genres with page = {}, limit = {}", page, limit);

        return ResponseEntity.ok(showGenres);
    }

    public Genre getGenreById(Long id) {
        return genreRepository.findById(id).orElse(null);
    }
}
