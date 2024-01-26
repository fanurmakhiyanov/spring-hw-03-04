package ru.gb.springdemo.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class Book {

    public static long sequence = 1L;
    private final long id;
    private final String name;

    public Book(String name) {
        this(sequence++, name);
    }

}