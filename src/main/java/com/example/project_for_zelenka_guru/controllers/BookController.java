package com.example.project_for_zelenka_guru.controllers;

import com.example.project_for_zelenka_guru.dtos.requests.AddBookRequest;
import com.example.project_for_zelenka_guru.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    // внедрение зависимости BookService ( dependency injection )
    // логика всех эндпоинтов описана в BookService
    private final BookService bookService;

    // получение подробных данных о книге по id, только аутентифицированный пользователь
    @GetMapping("/{book_id}")
    public ResponseEntity<?> getBookById(@PathVariable(name = "book_id") Long id) {
        return bookService.getBookById(id);
    }

    // просмотр всех книг, присутствуют дополнительные фильтры
    // /api/v1/books?search=s&genre_id=i&limit=n&page=k для просмотра всех книг
    // где s - слово/фраза для поиска книг, i - id жанра, n - лимит книг на странице, k - номер страницы
    @GetMapping
    public ResponseEntity<?> getBooks(@RequestParam(name = "search", required = false) String searchValue,
                                      @RequestParam(name = "genre_id", required = false) Long genreId,
                                      @RequestParam(name = "limit", required = false) Short limit,
                                      @RequestParam(name = "page", required = false) Short page)
    {
        return bookService.getBooks(searchValue, genreId, limit, page);
    }

    // удаление существующей книги, удалить может ее только тот, кто ее загрузил
    @GetMapping("/{book_id}/delete")
    public ResponseEntity<?> deleteBookById(@PathVariable(name = "book_id") Long id, Principal principal) {
        return bookService.deleteBookById(id, principal);
    }

    // добавление новой книги, только аутентифицированный пользователь
    // @Valid - смотрите в AddBookRequest
    @PostMapping("/add")
    public ResponseEntity<?> addBook(@Valid @RequestBody AddBookRequest addBookRequest, Principal principal) {
        return bookService.addBook(addBookRequest, principal);
    }

}
