package ru.gb.springdemo.dto;

import java.time.LocalDateTime;

public record IssueResponseDTO(String bookName, String readerName, LocalDateTime get, LocalDateTime set) {
}