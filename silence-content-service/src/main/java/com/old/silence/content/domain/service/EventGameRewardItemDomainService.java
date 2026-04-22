package com.old.silence.content.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.old.silence.content.domain.repository.EventGameRewardItemRepository;

import java.math.BigInteger;
import java.util.List;

@Service
public class EventGameRewardItemDomainService {
	private static final Logger logger = LoggerFactory.getLogger(EventGameRewardItemDomainService.class);

	private final EventGameRewardItemRepository eventGameRewardItemRepository;

    public EventGameRewardItemDomainService(EventGameRewardItemRepository eventGameRewardItemRepository) {
        this.eventGameRewardItemRepository = eventGameRewardItemRepository;
    }


    public <T> List<T> findByEventGameId(BigInteger eventGameId, Class<T> projectionType) {
		return eventGameRewardItemRepository.findByEventGameId(eventGameId, projectionType);
	}


}
