package com.old.silence.content.domain.repository;

import com.old.silence.content.domain.model.Content;

import java.math.BigInteger;
import java.util.Optional;


public interface ContentRepository {
    
    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);


    int create(Content content);


    int update(Content content);


    int deleteById(Long id);
}
