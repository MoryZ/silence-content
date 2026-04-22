package com.old.silence.content.api.tournament;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.TournamentPersonalRankQuery;
import com.old.silence.content.api.TournamentRankService;
import com.old.silence.content.api.dto.TournamentCurrentGroupVO;
import com.old.silence.content.api.dto.TournamentGlobalRankQuery;
import com.old.silence.content.api.dto.TournamentRankListVO;
import com.old.silence.content.api.dto.common.Response;
import com.old.silence.content.api.tournament.tournament.dto.TournamentRankingDto;
import com.old.silence.content.domain.common.MarketingBizException;
import com.old.silence.content.domain.model.TournamentRanking;
import com.old.silence.content.domain.service.tournament.TournamentRankDomainService;
import com.old.silence.content.infrastructure.persistence.tournament.dao.TournamentRankingDao;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

/**
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class TournamentRankResource implements TournamentRankService {
    private static final Logger logger = LoggerFactory.getLogger(TournamentRankResource.class);

    private final TournamentRankDomainService tournamentRankDomainService;

    private final TournamentRankingDao tournamentRankingDao;

    public TournamentRankResource(TournamentRankDomainService tournamentRankDomainService,
            TournamentRankingDao tournamentRankingDao) {
        this.tournamentRankDomainService = tournamentRankDomainService;
        this.tournamentRankingDao = tournamentRankingDao;
    }

    @Override
    public Response<TournamentRankListVO> getRankDetail(TournamentGlobalRankQuery query) {
        try {
            // 校验入参（可选：已在@Validated中处理）
            if (Objects.isNull(query)) {
                return Response.fail("请求参数不能为空");
            }
            TournamentRankListVO rankListVO = tournamentRankDomainService.getRankDetail(query);
            return Response.success(rankListVO);
        } catch (MarketingBizException e) {
            logger.warn("业务异常 - 开始挑战失败: {}", e.getMessage(), e);
            return Response.fail(e.getMessage());
        } catch (Exception e) {
            logger.error("系统异常 - 开始挑战失败", e);
            return Response.fail("系统异常，请稍后重试");
        }
    }

    @Override
    public Response<TournamentCurrentGroupVO> getPersonalRank(TournamentPersonalRankQuery query) {
        try {
            // 校验入参（可选：已在@Validated中处理）
            if (Objects.isNull(query)) {
                return Response.fail("请求参数不能为空");
            }

            TournamentCurrentGroupVO currentGroupVO = tournamentRankDomainService.getPersonalRank(query);
            return Response.success(currentGroupVO);
        } catch (MarketingBizException e) {
            logger.warn("业务异常 - 开始挑战失败: {}", e.getMessage(), e);
            return Response.fail(e.getMessage());
        } catch (Exception e) {
            logger.error("系统异常 - 开始挑战失败", e);
            return Response.fail("系统异常，请稍后重试");
        }
    }


    @PostJsonMapping(value = "/tournamentRankings")
    public BigInteger create(@RequestBody TournamentRanking tournamentRanking) {
        tournamentRankingDao.insert(tournamentRanking);
        return tournamentRanking.getId();
    }

    @PutJsonMapping(value = "/tournamentRankings/{id}")
    public Integer update(@PathVariable BigInteger id, @RequestBody TournamentRanking tournamentRanking) {
        tournamentRanking.setId(id);
        return tournamentRankingDao.updateNonNull(tournamentRanking);
    }

    public Integer delete(@PathVariable BigInteger id) {
        return tournamentRankingDao.deleteById(id);
    }


    @Override
    public void batchAddRankRecord(List<TournamentRankingDto> tournamentRankingList) {
        tournamentRankDomainService.batchAddRankRecord(tournamentRankingList);
    }
}
