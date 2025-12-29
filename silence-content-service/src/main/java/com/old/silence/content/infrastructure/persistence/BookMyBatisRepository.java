package com.old.silence.content.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.stereotype.Repository;
import com.old.silence.content.domain.model.Book;
import com.old.silence.content.domain.repository.BookRepository;
import com.old.silence.content.infrastructure.persistence.dao.BookDao;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Book仓储实现
 */
@Repository
public class BookMyBatisRepository implements BookRepository {
    private final BookDao bookDao;

    public BookMyBatisRepository(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return bookDao.findById(id, projectionType);
    }

    @Override
    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {
        return bookDao.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public int create(Book book) {
        return bookDao.insert(book);
    }

    @Override
    public int update(Book book) {
        return bookDao.update(book);
    }

    @Override
    public int deleteById(BigInteger id) {
        return bookDao.deleteById(id);
    }
}