package ru.gb.springdemo.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Book;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Repository
public class BookRepository {

    private final List<Book> books;

    public BookRepository() {
        this.books = new ArrayList<>();
    }

    @PostConstruct
    public void generateData() {
        books.addAll(List.of(
                new Book("\"Война и мир\""),
                new Book("\"Мертвые души\""),
                new Book("\"Чистый код\"")
        ));
    }

    public Book getBookById(long id) {
        return books.stream().filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public boolean removeBookById(long id) {
        return books.removeIf(x -> x.getId() == id);
    }

    public boolean addBook(Book book) {
        return books.add(book);
    }

    public List<Book> getAllBooks() {
        return List.copyOf(books);
    }
}