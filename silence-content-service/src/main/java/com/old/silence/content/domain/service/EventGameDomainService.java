package com.old.silence.content.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.old.silence.content.domain.repository.EventGameRepository;

import java.math.BigInteger;
import java.util.Optional;


/**
 * @author yangwenchang
 */
@Service
public class EventGameDomainService {

    private static final Logger logger = LoggerFactory.getLogger(EventGameDomainService.class);


    private final EventGameRepository eventGameRepository;

    public EventGameDomainService(EventGameRepository eventGameRepository) {
        this.eventGameRepository = eventGameRepository;
    }


    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return eventGameRepository.findById(id, projectionType);
    }

}
