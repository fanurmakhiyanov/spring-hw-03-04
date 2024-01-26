package ru.gb.springdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.dto.IssueRequest;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.repository.BookRepository;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.repository.ReaderRepository;
import ru.gb.springdemo.util.IssueRejectedException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class IssuerService {

    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final IssueRepository issueRepository;

    private long maxCountBooks;

    @Autowired
    public void setMaxCountBooks(@Value("${application.max-allowed-books:1}") long maxCountBooks) {
        this.maxCountBooks = maxCountBooks;
    }

    public Issue issue(IssueRequest request) throws IssueRejectedException {
        validateRequest(request);
        Issue issue = new Issue(request.getBookId(), request.getReaderId());
        issueRepository.save(issue);
        return issue;
    }

    public Issue getIssueById(long issueId) {
        return issueRepository.getIssueById(issueId);
    }

    public Boolean returnedBook(long issueId) {
        issueRepository.getIssueById(issueId).setReturned_at(LocalDateTime.now());
        return true;
    }

    public List<Issue> getIssues() {
        return issueRepository.getAllIssue();
    }

    private void validateRequest(IssueRequest request) throws IssueRejectedException {
        if (bookRepository.getBookById(request.getBookId()) == null) {
            throw new NoSuchElementException("Не найдена книга с идентификатором \"" + request.getBookId() + "\"");
        }
        if (readerRepository.getReaderById(request.getReaderId()) == null) {
            throw new NoSuchElementException("Не найден читатель с идентификатором \"" + request.getReaderId() + "\"");
        }
        if (!bookIsAcceptably(request.getBookId())) {
            throw new IssueRejectedException("Нет свободной книги");
        }
        if (!readerCanTakeBook(request.getReaderId())) {
            throw new IssueRejectedException("Читателю отказанно в получение книги");
        }
    }

    private boolean bookIsAcceptably(long bookId) {
        List<Issue> issueList = issueRepository.getAllOpenIssue();
        if (issueList.size() == 0) {return true;}
        return issueList.stream().noneMatch(x -> x.getBookId() == bookId);
    }

    private boolean readerCanTakeBook(long readerId) {
        List<Issue> issueList = issueRepository.getAllOpenIssue();
        if (issueList.size() == 0) {return true;}
        return issueList.stream().filter(x -> x.getReaderId() == readerId).count() < maxCountBooks;
    }
}