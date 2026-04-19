package com.old.silence.content.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.old.silence.content.domain.enums.MarketingRuleType;
import com.old.silence.content.domain.enums.ParticipantType;
import com.old.silence.content.domain.model.MarketingRule;
import com.old.silence.content.domain.repository.MarketingRuleRepository;
import com.old.silence.json.JacksonMapper;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MarketingRuleDomainService {


    private final MarketingRuleRepository marketingRuleRepository;

    public MarketingRuleDomainService(MarketingRuleRepository marketingRuleRepository) {
        this.marketingRuleRepository = marketingRuleRepository;
    }


    public <T> List<T> findAllById(Iterable<BigInteger> idList, Class<T> projectionType) {
        return marketingRuleRepository.findAllById(idList, projectionType);
    }


}
