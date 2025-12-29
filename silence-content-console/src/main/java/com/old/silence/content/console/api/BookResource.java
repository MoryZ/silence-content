package com.old.silence.content.console.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;

import java.math.BigInteger;

/**
 * Book资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class BookResource {
    private final BookClient bookClient;
    private final BookCommandMapper bookCommandMapper;
    private final BookQueryMapper bookQueryMapper;

    public BookResource(BookClient bookClient,
                        BookCommandMapper bookCommandMapper,
                        BookQueryMapper bookQueryMapper) {
        this.bookClient = bookClient;
        this.bookCommandMapper = bookCommandMapper;
        this.bookQueryMapper = bookQueryMapper;
    }

    @GetMapping(value = "/books/{id}")
    public BookConsoleView findById(@PathVariable BigInteger id) {
        return bookClient.findById(id, BookConsoleView.class)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping(value = "/books", params = {"pageNo", "pageSize"})
    public Page<BookConsoleView> query(BookConsoleQuery query, Pageable pageable) {
        var BookQuery = bookQueryMapper.convert(query);
        return bookClient.query(BookQuery, pageable, BookConsoleView.class);
    }

    @PostJsonMapping("/books")
    public BigInteger create(@RequestBody BookConsoleCommand command) {
        var bookCommand = bookCommandMapper.convert(command);
        return bookClient.create(bookCommand);
    }

    @PutJsonMapping(value = "/books/{id}")
    public void update(@PathVariable BigInteger id, @RequestBody BookConsoleCommand command) {
        var bookCommand = bookCommandMapper.convert(command);
        bookClient.update(id, bookCommand);
    }

    @DeleteMapping("/books/{id}")
    public void deleteById(@PathVariable BigInteger id) {
        bookClient.deleteById(id);
    }
}