package ru.gb.springdemo.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Issue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class IssueRepository {

    private final List<Issue> issues;

    public IssueRepository() {
        this.issues = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        issues.add(new Issue(1,1));
        issues.add(new Issue(2,1));
    }

    public void save(Issue issue) {
        issues.add(issue);
    }

    public Issue getIssueById(long issueId) {
        return issues.stream().filter(it -> Objects.equals(it.getId(), issueId))
                .findFirst()
                .orElse(null);
    }

    public List<Issue> getAllOpenIssue() {
        return issues.stream()
                .filter(issue -> issue.getReturned_at() == null)
                .collect(Collectors.toList());
    }

    public List<Issue> getAllIssue() {
        return List.copyOf(issues);
    }
}