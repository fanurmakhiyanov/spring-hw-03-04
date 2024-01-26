package ru.gb.springdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.repository.BookRepository;
import ru.gb.springdemo.repository.IssueRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final IssueRepository issueRepository;

    public Boolean addBook(Book book) {
        return bookRepository.addBook(book);
    }
    public Book getBookById(long bookId) {
        return bookRepository.getBookById(bookId);
    }
    public List<Book> getAllAccessibleBooks() {
        List<Long> issueList = issueRepository.getAllOpenIssue().stream()
                .map(Issue::getBookId)
                .toList();
        return bookRepository.getAllBooks().stream()
                .filter(book -> !issueList.contains(book.getId()))
                .collect(Collectors.toList());
    }
    public Boolean removeBookById(long bookId) {
        return bookRepository.removeBookById(bookId);
    }



}