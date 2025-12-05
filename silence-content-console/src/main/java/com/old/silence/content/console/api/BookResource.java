package com.old.silence.content.console.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.BookClient;
import com.old.silence.content.console.api.assembler.BookCommandMapper;
import com.old.silence.content.console.api.assembler.BookQueryMapper;
import com.old.silence.content.console.dto.BookConsoleCommand;
import com.old.silence.content.console.dto.BookConsoleQuery;
import com.old.silence.content.console.vo.BookConsoleView;
import com.old.silence.core.exception.ResourceNotFoundException;

import java.math.BigInteger;

/**
 *
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class BookResource {
    private final BookClient bookClient;
    private final BookCommandMapper bookCommandMapper;
    private final BookQueryMapper bookQueryMapper;

    public BookResource(BookClient bookClient,
                        BookCommandMapper bookCommandMapper, BookQueryMapper bookQueryMapper) {
        this.bookClient = bookClient;
        this.bookCommandMapper = bookCommandMapper;
        this.bookQueryMapper = bookQueryMapper;
    }


    @GetMapping("/books/{id}")
    public BookConsoleView findById(BigInteger id) {
        return bookClient.findById(id, BookConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/books", params = {"pageNo", "pageSize"})
    public Page<BookConsoleView> query(BookConsoleQuery query, Pageable pageable) {
        var bookQuery = bookQueryMapper.convert(query);
        return bookClient.query(bookQuery, pageable, BookConsoleView.class);
    }

    @PostMapping(value = "/books")
    public BigInteger create(@RequestBody BookConsoleCommand command) {
        var book = bookCommandMapper.convert(command);
        return bookClient.create(book);
    }

    @PutMapping(value = "/books/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody BookConsoleCommand command) {
        var book = bookCommandMapper.convert(command);
        bookClient.update(id, book);
    }

    @DeleteMapping(value = "/books/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        bookClient.deleteById(id);
    }
}
