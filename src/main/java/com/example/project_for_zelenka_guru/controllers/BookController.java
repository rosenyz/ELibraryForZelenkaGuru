package com.example.project_for_zelenka_guru.controllers;

import com.example.project_for_zelenka_guru.dtos.requests.AddBookRequest;
import com.example.project_for_zelenka_guru.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/{book_id}")
    public ResponseEntity<?> getBookById(@PathVariable(name = "book_id") Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping // эндпоинт /api/v1/books?limit=n&page=k для просмотра всех жанров, где n - лимит жанров на странице, а k - страница
    public ResponseEntity<?> getBooks(@RequestParam(name = "genre_id", required = false) Long genreId,
                                      @RequestParam(name = "limit", required = false) Short limit,
                                      @RequestParam(name = "page", required = false) Short page)
    {
        return bookService.getBooks(genreId, limit, page);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@Valid @RequestBody AddBookRequest addBookRequest) {
        return bookService.addBook(addBookRequest);
    }

}
