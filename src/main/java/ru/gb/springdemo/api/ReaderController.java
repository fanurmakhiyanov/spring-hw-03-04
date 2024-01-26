package ru.gb.springdemo.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Reader;

import ru.gb.springdemo.service.ReaderService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reader")
@RequiredArgsConstructor
public class ReaderController {

    private final ReaderService readerService;

    @GetMapping("/{readerId}")
    public ResponseEntity<Reader> getReaderInfo(@PathVariable long readerId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(readerService.getReaderById(readerId));
    }

    @GetMapping("/{readerId}/issue")
    public ResponseEntity<List<Issue>> getAllIssuesReader(@PathVariable long readerId) {
        return ResponseEntity.status(HttpStatus.OK).body(readerService.getAllIssueReader(readerId));
    }

    @DeleteMapping("/{readerId}")
    public ResponseEntity<Boolean> deleteReader(@PathVariable long readerId) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(readerService.removeReaderById(readerId));
    }

    @PostMapping
    public ResponseEntity<Boolean> addReader(@RequestBody Reader reader ) {
        log.info("Запрос на добавления читателя: readerName = {}", reader.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(readerService.addReader(reader));
    }
}