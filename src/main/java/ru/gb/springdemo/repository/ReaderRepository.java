package ru.gb.springdemo.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ReaderRepository {

    private final List<Reader> readers;

    public ReaderRepository() {
        this.readers = new ArrayList<>();
    }

    @PostConstruct
    public void generateData() {
        readers.addAll(List.of(
                new ru.gb.springdemo.model.Reader("Фанур Махиянов"),
                new ru.gb.springdemo.model.Reader("Игорь Честнов")
        ));
    }

    public Reader getReaderById(long id) {
        return readers.stream().filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public boolean removeReaderById(long id) {
        return readers.removeIf(x -> x.getId() == id);
    }

    public boolean addReader(Reader reader) {
        return readers.add(new Reader(reader.getName()));
    }

    public List<Reader> getAllReaders() {
        return List.copyOf(readers);
    }

}