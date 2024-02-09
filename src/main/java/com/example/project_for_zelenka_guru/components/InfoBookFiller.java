package com.example.project_for_zelenka_guru.components;

import com.example.project_for_zelenka_guru.dtos.responses.InfoBooksResponse;
import com.example.project_for_zelenka_guru.models.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InfoBookFiller {

    // функция для заполнения DTO данными о книге
    public List<InfoBooksResponse> fillInfoBooks(List<Book> books) {
        List<InfoBooksResponse> booksInfo = new ArrayList<>();

        for (Book book : books) {
            InfoBooksResponse bookInfo = new InfoBooksResponse(
                    book.getId(),
                    book.getName(),
                    book.getAuthor()
            );

            booksInfo.add(bookInfo);
        }

        return booksInfo;
    }

}
