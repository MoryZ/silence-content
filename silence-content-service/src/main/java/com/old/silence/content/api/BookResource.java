package com.old.silence.content.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.BookMapper;
import com.old.silence.content.api.dto.BookCommand;
import com.old.silence.content.api.dto.BookQuery;
import com.old.silence.content.domain.model.Book;
import com.old.silence.content.domain.repository.BookRepository;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/api/v1")
public class BookResource implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookResource(BookRepository bookRepository,
                        BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }


    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return bookRepository.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> query(BookQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, Book.class);
        return bookRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(BookCommand command) {
        var book = bookMapper.convert(command);
        bookRepository.create(book);
        return book.getId(); // NOSONAR
    }

    @Override
    public void update(BigInteger id, BookCommand command) {
        var book = bookMapper.convert(command);
        book.setId(id); // NOSONAR
        validateModifyingResult(bookRepository.update(book));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(bookRepository.deleteById(id));
    }
}
