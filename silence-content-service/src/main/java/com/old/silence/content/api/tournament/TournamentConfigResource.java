package com.old.silence.content.api.tournament;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.tournament.TournamentConfigMapper;
import com.old.silence.content.api.dto.common.Response;
import com.old.silence.content.api.tournament.tournament.dto.TournamentConfigDomainCommand;
import com.old.silence.content.api.tournament.tournament.dto.TournamentConfigQuery;
import com.old.silence.content.api.tournament.tournament.dto.TournamentUserParticipationInfoCommand;
import com.old.silence.content.api.tournament.tournament.service.TournamentConfigService;
import com.old.silence.content.api.tournament.tournament.vo.TournamentConfigVo;
import com.old.silence.content.api.tournament.tournament.vo.TournamentUserParticipationInfoVo;
import com.old.silence.content.domain.common.MarketingBizException;
import com.old.silence.content.domain.model.TournamentConfig;
import com.old.silence.content.domain.repository.tournament.TournamentConfigRepository;
import com.old.silence.content.domain.service.tournament.TournamentConfigCacheDomainService;
import com.old.silence.content.domain.service.tournament.TournamentConfigDomainService;
import com.old.silence.content.domain.service.tournament.TournamentParticipationRecordService;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;

import java.math.BigInteger;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1")
public class TournamentConfigResource implements TournamentConfigService {

    private static final Logger logger = LoggerFactory.getLogger(TournamentConfigResource.class);


    private final TournamentConfigCacheDomainService tournamentConfigCacheDomainService;
    private final TournamentParticipationRecordService tournamentParticipationRecordService;
    private final TournamentConfigDomainService tournamentConfigDomainService;

    private final TournamentConfigRepository tournamentConfigRepository;
    private final TournamentConfigMapper tournamentConfigMapper;


    public TournamentConfigResource(TournamentConfigCacheDomainService tournamentConfigCacheDomainService,
                                    TournamentParticipationRecordService tournamentParticipationRecordService,
                                    TournamentConfigDomainService tournamentConfigDomainService,
            TournamentConfigRepository tournamentConfigRepository,
            TournamentConfigMapper tournamentConfigMapper) {
        this.tournamentConfigCacheDomainService = tournamentConfigCacheDomainService;
        this.tournamentParticipationRecordService = tournamentParticipationRecordService;
        this.tournamentConfigDomainService = tournamentConfigDomainService;
        this.tournamentConfigRepository = tournamentConfigRepository;
        this.tournamentConfigMapper = tournamentConfigMapper;
    }

    @Override
    public Response<TournamentConfigVo> getTournamentDetail(BigInteger eventGameId) {
        try {
            TournamentConfigVo tournamentConfigVo = tournamentConfigCacheDomainService.queryTournamentConfigVo(eventGameId);
            int registrationCount = tournamentParticipationRecordService.countTournamentParticipation(eventGameId);
            tournamentConfigVo.setRegistrationCount(registrationCount);
            return Response.success(tournamentConfigVo);
        }catch (MarketingBizException marketingBizException){
            return Response.fail(String.valueOf(marketingBizException.getErrorCode()), marketingBizException.getMessage());
        }catch (Exception ex){
            logger.info("getTournamentDetail fail,reason is",ex);
            return Response.fail("服务器正忙，请稍后重试");
        }
    }

    @Override
    public <T> Optional<T> findByEventGameId(BigInteger eventGameId, Class<T> projectionType) {
        return tournamentConfigDomainService.findByEventGameId(eventGameId, projectionType);
    }

    @Override
    public <T> Page<T> query(TournamentConfigQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, TournamentConfig.class);
        return tournamentConfigDomainService.query(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(TournamentConfigDomainCommand tournamentConfigDomainCommand) {
        return tournamentConfigDomainService.create(tournamentConfigDomainCommand);
    }

    @Override
    public int update(BigInteger id, TournamentConfigDomainCommand tournamentConfigDomainCommand) {
        return tournamentConfigDomainService.update(id, tournamentConfigDomainCommand);
    }

    @PutMapping("/tournamentConfigs/inner/{id}")
    public int updateBack(BigInteger id, TournamentConfigDomainCommand command) {
        var tournamentConfig = tournamentConfigMapper.convert(command);
        tournamentConfig.setEventGameId(id);
        int row = tournamentConfigRepository.updateNonNull(tournamentConfig);
        return row;
    }

    @Override
    public Response<TournamentUserParticipationInfoVo> queryTournamentUserParticipationInfo(TournamentUserParticipationInfoCommand command) {
        try {
            TournamentUserParticipationInfoVo tournamentUserParticipationInfoVo = tournamentConfigDomainService.queryTournamentUserParticipationInfo(command);
            return Response.success(tournamentUserParticipationInfoVo);
        }catch (MarketingBizException marketingBizException){
            return Response.fail(String.valueOf(marketingBizException.getErrorCode()), marketingBizException.getMessage());
        }catch (Exception ex){
            logger.info("queryTournamentUserParticipationInfo fail,reason is",ex);
            return Response.fail("服务器正忙，请稍后重试");
        }
    }
}
