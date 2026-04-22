package com.old.silence.content.api.tournament;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.dto.TournamentRegisterCommand;
import com.old.silence.content.api.dto.common.Response;
import com.old.silence.content.api.tournament.tournament.dto.TournamentParticipationRecordQuery;
import com.old.silence.content.api.tournament.tournament.service.TournamentParticipantService;
import com.old.silence.content.api.vo.TournamentRegisterVo;
import com.old.silence.content.domain.common.MarketingBizException;
import com.old.silence.content.domain.model.TournamentParticipationRecord;
import com.old.silence.content.domain.service.tournament.TournamentParticipationRecordService;
import com.old.silence.content.infrastructure.persistence.tournament.dao.TournamentParticipationRecordDao;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TournamentParticipantResource implements TournamentParticipantService {
    private static final Logger logger = LoggerFactory.getLogger(TournamentParticipantResource.class);

    private final TournamentParticipationRecordService tournamentParticipationRecordService;

    private final TournamentParticipationRecordDao tournamentParticipationRecordDao;

    public TournamentParticipantResource(TournamentParticipationRecordService tournamentParticipationRecordService,
            TournamentParticipationRecordDao tournamentParticipationRecordDao) {
        this.tournamentParticipationRecordService = tournamentParticipationRecordService;
        this.tournamentParticipationRecordDao = tournamentParticipationRecordDao;
    }

    public Response<TournamentRegisterVo> tournamentRegister(TournamentRegisterCommand command){
        try {
            TournamentRegisterVo tournamentRegisterVo = tournamentParticipationRecordService.tournamentRegister(command);
            return Response.success(tournamentRegisterVo);
        }catch (MarketingBizException marketingBizException){
            return Response.fail(String.valueOf(marketingBizException.getErrorCode()), marketingBizException.getMessage());
        }catch (Exception ex){
            logger.info("tournamentRegister fail,reason is",ex);
            return Response.fail("服务器正忙，请稍后重试");
        }
    }

    @Override
    public <T> List<T> queryRobotRecord(BigInteger eventGameId, BigInteger id, Pageable pageable, Class<T> projectionType) {
        return tournamentParticipationRecordService.queryRobotRecord(eventGameId, id, pageable, projectionType);
    }

    @Override
    public <T> Page<T> query(TournamentParticipationRecordQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, TournamentParticipationRecord.class);
        return tournamentParticipationRecordDao.findByCriteria(criteria, pageable, projectionType);
    }

    @PostJsonMapping(value = "/tournamentParticipantRecords")
    public BigInteger create(@RequestBody TournamentParticipationRecord tournamentParticipantRecord) {
        tournamentParticipationRecordDao.insert(tournamentParticipantRecord);
        return tournamentParticipantRecord.getId();
    }

    @PutJsonMapping(value = "/tournamentParticipantRecords/{id}")
    public Integer update(@PathVariable BigInteger id, @RequestBody TournamentParticipationRecord tournamentParticipantRecord) {
        tournamentParticipantRecord.setId(id);
        return tournamentParticipationRecordDao.updateNonNull(tournamentParticipantRecord);
    }

    @DeleteMapping(value = "/tournamentParticipantRecords/{id}")
    public Integer delete(@PathVariable BigInteger id) {
        return tournamentParticipationRecordDao.deleteById(id);
    }
}
