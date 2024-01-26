package ru.gb.springdemo.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.dto.IssueRequest;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.service.IssuerService;
import ru.gb.springdemo.util.IssueRejectedException;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/issue")
@RequiredArgsConstructor
public class IssuerController {

    private final IssuerService issuerService;

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getInfo(@PathVariable long issueId) {
        log.info("Запрос на информацию выдачи : issueId = {}", issueId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(issuerService.getIssueById(issueId));
    }

    @PutMapping("/{issueId}")
    public ResponseEntity<Boolean> returnedBook(@PathVariable long issueId) {
        log.info("Возвращение книги: issueId = {}", issueId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(issuerService.returnedBook(issueId));
    }

    @PostMapping
    public ResponseEntity<Issue> issueBook(@RequestBody IssueRequest request) {
        log.info("Получен запрос на выдачу: readerId = {}, bookId = {}", request.getReaderId(), request.getBookId());

        final Issue issue;
        try {
            issue = issuerService.issue(request);
        } catch (NoSuchElementException e) {
            log.info("Запрос на выдачу отклонен причина: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (IssueRejectedException e) {
            log.info("Запрос на выдачу отклонен причина: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(issue);
    }

}