package com.example.project_for_zelenka_guru.services;

import com.example.project_for_zelenka_guru.components.InfoBookFiller;
import com.example.project_for_zelenka_guru.dtos.requests.AddBookRequest;
import com.example.project_for_zelenka_guru.dtos.responses.InfoBookResponse;
import com.example.project_for_zelenka_guru.models.Book;
import com.example.project_for_zelenka_guru.models.Genre;
import com.example.project_for_zelenka_guru.models.User;
import com.example.project_for_zelenka_guru.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j // логирование действий
public class BookService {
    // внедрение зависимостей ( dependency injection )
    private final BookRepository bookRepository;
    private final GenreService genreService;
    private final UserService userService;
    private final InfoBookFiller filler;

    // поиск книг по ключевому слову, жанру и разбиение списка на страницы ! будет возвращать {limit} книг
    public ResponseEntity<?> getBooks(String searchValue, Long genreId, Short limit, Short page)
    {
        List<Book> books;
        List<Book> showBooks = new ArrayList<>(); // список для отсортированных книг по жанру, странице и лимитам

        if (genreId == null) {
            books = getBooksBySearchingValue(searchValue, null);
        }
        else {
            // если genre != null, то ищем его в базе данных
            Genre genre = genreService.getGenreById(genreId);

            if (genre == null)
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Такого жанра не существует!");

            books = getBooksBySearchingValue(searchValue, genre);
        }

        if (books.isEmpty()) {
            log.info("List books isEmpty = {}", true);

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Ни одной книги не было добавлено");
        }

        // если есть лимит вывода книг, но страница не указана, то по умолчанию отобразится 1-ая страница
        if (limit != null && page == null) { page = 1; }

        // если лимит не был указан или номер страницы меньше 1, то выводим весь список книг
        if (limit == null || page < 1) {
            log.info("Found all books with genreId = {}", genreId);

            return ResponseEntity.ok(filler.fillInfoBooks(books));
        }

        // если размер списка меньше, чем лимит книг на странице, то выводим все книги
        if (books.size() <= limit) {
            log.info("Found all books with genreId = {}", genreId);

            return ResponseEntity.ok(filler.fillInfoBooks(books));
        }

        // считаем количество страниц при установленном лимите и кастим значение к short, для экономии ресурсов
        short allowedPages = (short) Math.ceil((double) books.size() / limit);

        // если указанная страница не в диапозоне количества посчитанных страниц или указанная страница = 1, то выводим первую
        if (page > allowedPages || page == 1) {
            for (short i = 0; i < limit; i++) {
                showBooks.add(books.get(i));
            }

            log.info("Found books with genreId = {}, (page > allowedPages || page == 1), limit = {}", genreId, limit);

            return ResponseEntity.ok(filler.fillInfoBooks(showBooks));
        }

        // если все хорошо, то выводим опр. кол-во книг на опр. странице
        for (int i = page * limit; i < page * limit + limit; i++) {
            if (i - limit < books.size())
                showBooks.add(books.get(i - limit));
        }

        log.info("Found books with genreId = {}, page = {}, limit = {}", genreId, page, limit);

        return ResponseEntity.ok(filler.fillInfoBooks(showBooks));
    }

    // добавление информации о книге
    public ResponseEntity<?> addBook(AddBookRequest addBookRequest, Principal principal) {
        // principal - авторизация, если ее нет, то кидаем 401
        if (principal == null) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED"); }

        User user = userService.findUserByPrincipal(principal);

        // заполняем entity книги полученными данными
        Book book = new Book(
                addBookRequest.getName(),
                addBookRequest.getAuthor(),
                addBookRequest.getDescription(),
                user.getId()
        );

        // добавление жанров к книге, если жанра не существует, то он просто не добавиться
        for (Long genreId : addBookRequest.getGenres()) {
            if (genreService.getGenreById(genreId) != null)
                book.getGenres().add(genreService.getGenreById(genreId));
        }

        // добавление книги в модель пользователя
        user.getBooks().add(book);

        bookRepository.save(book);
        userService.save(user);

        log.info("\nSaved book with lines:\nname = {}\nauthor = {}\ndescription = {}\ngenresIds = {}\nloaderUserId = {}",
                addBookRequest.getName(),
                addBookRequest.getAuthor(),
                addBookRequest.getDescription(),
                addBookRequest.getGenres(),
                user.getId()
        );

        return ResponseEntity.ok(book);
    }

    // удаление данных о книге, аналогично с созданием
    public ResponseEntity<?> deleteBookById(Long id, Principal principal) {
        if (principal == null) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED"); }

        Book book = bookRepository.findById(id).orElse(null);
        User user = userService.findUserByPrincipal(principal);

        if (book == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Такой книги не существует!");
        }

        if (!book.getLoaderUserId().equals(user.getId())) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Удалить книгу может только тот, кто ее загрузил.");
        }

        user.getBooks().remove(book);

        bookRepository.delete(book);
        userService.save(user);

        return ResponseEntity.ok("Книга «%s» успешно удалена!".formatted(book.getName()));
    }

    // система поиска книги в базе данных по ключевому слову и жанру
    public List<Book> getBooksBySearchingValue(String searchValue, Genre genre) {
        if (searchValue == null) {
            if (genre == null)
                return bookRepository.findAll(); // если не указаны ни жанр, ни ключевые слова

            return bookRepository.findBooksByGenresContains(genre); // если указан только жанр
        }

        log.info("Used searching value = {}", searchValue);

        // если жанр не указан
        if (genre != null) {
            // смотрите определение функции в BookRepository
            return bookRepository.findBooksByAuthorContainingIgnoreCaseOrNameContainingIgnoreCaseAndGenresContains(searchValue, searchValue, genre);
        }

        // если указан и жанр, и ключевые слова
        // смотрите определение функции в BookRepository
        return bookRepository.findBooksByAuthorContainingIgnoreCaseOrNameContainingIgnoreCase(searchValue, searchValue);
    }

    // получение подробных данных о книге по id
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
