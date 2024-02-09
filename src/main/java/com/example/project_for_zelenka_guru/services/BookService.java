package com.example.project_for_zelenka_guru.services;

import com.example.project_for_zelenka_guru.components.InfoBookFiller;
import com.example.project_for_zelenka_guru.dtos.requests.AddBookRequest;
import com.example.project_for_zelenka_guru.dtos.responses.InfoBookResponse;
import com.example.project_for_zelenka_guru.models.Book;
import com.example.project_for_zelenka_guru.models.Genre;
import com.example.project_for_zelenka_guru.repositories.BookRepository;
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
public class BookService {
    private final BookRepository bookRepository;
    private final GenreService genreService;
    private final InfoBookFiller filler;

    public ResponseEntity<?> getBooks(Long genreId, Short limit, Short page)
    {
        List<Book> books;
        List<Book> showBooks = new ArrayList<>(); // список для отсортированных книг по жанру, странице и лимитам

        if (genreId == null) {
            books = bookRepository.findAll();
        }
        else {
            Genre genre = genreService.getGenreById(genreId);

            if (genre == null)
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Такого жанра не существует!");

            books = bookRepository.findBooksByGenresContains(genre);
        }

        if (books.isEmpty()) {
            log.info("List books isEmpty = {}", true);

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Ни одной книги не было добавлено");
        }

        if (limit != null && page == null) { page = 1; }

        if (limit == null || page < 1) {
            log.info("Found all books with genreId = {}", genreId);

            return ResponseEntity.ok(filler.fillInfoBooks(books));
        }

        if (books.size() <= limit) {
            log.info("Found all books with genreId = {}", genreId);

            return ResponseEntity.ok(filler.fillInfoBooks(books));
        }

        short allowedPages = (short) Math.ceil((double) books.size() / limit);

        if (page > allowedPages || page == 1) {
            for (short i = 0; i < limit; i++) {
                showBooks.add(books.get(i));
            }

            log.info("Found books with genreId = {}, (page > allowedPages || page == 1), limit = {}", genreId, limit);

            return ResponseEntity.ok(filler.fillInfoBooks(showBooks));
        }

        for (int i = page * limit; i < page * limit + limit; i++) {
            if (i - limit < books.size())
                showBooks.add(books.get(i - limit));
        }

        log.info("Found books with genreId = {}, page = {}, limit = {}", genreId, page, limit);

        return ResponseEntity.ok(filler.fillInfoBooks(showBooks));
    }

    public ResponseEntity<?> addBook(AddBookRequest addBookRequest) {
        Book book = new Book(
                addBookRequest.getName(),
                addBookRequest.getAuthor(),
                addBookRequest.getDescription()
        );

        for (Long genreId : addBookRequest.getGenres()) {
            if (genreService.getGenreById(genreId) != null)
                book.getGenres().add(genreService.getGenreById(genreId));
        }

        bookRepository.save(book);

        log.info("Saved book with lines:\nname = {}\nauthor = {}\ndescription = {}\ngenresIds = {}",
                addBookRequest.getName(),
                addBookRequest.getAuthor(),
                addBookRequest.getDescription(),
                addBookRequest.getGenres()
        );

        return ResponseEntity.ok(book);
    }

    public ResponseEntity<?> getBookById(Long id) {
        Book book = bookRepository.findById(id).orElse(null);

        log.info("Found book with id = {} status = {}", id, book != null);

        if (book == null) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Такой книги не существует!");
        }

        InfoBookResponse bookInfo = new InfoBookResponse(
                book.getId(),
                book.getName(),
                book.getAuthor(),
                book.getDescription(),
                book.getGenres()
        );

        return ResponseEntity.ok(bookInfo);
    }
}
