package com.old.silence.content.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.old.silence.content.domain.repository.MarketingEventRepository;

import java.math.BigInteger;
import java.util.*;


@Service
public class MarketingEventDomainService {

    private static final Logger logger = LoggerFactory.getLogger(MarketingEventDomainService.class);
    /**
     * 如果机构的范围是所有机构，判断时使用此标识code
     */

    private final MarketingEventRepository marketingEventRepository;

    public MarketingEventDomainService(MarketingEventRepository marketingEventRepository) {
        this.marketingEventRepository = marketingEventRepository;
    }

    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return marketingEventRepository.findById(id, projectionType);
    }




}
