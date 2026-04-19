package com.old.silence.content.api.tournament;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.TournamentChallengeService;
import com.old.silence.content.api.dto.CompleteChallengeCommand;
import com.old.silence.content.api.dto.StartChallengeCommand;
import com.old.silence.content.api.dto.common.Response;
import com.old.silence.content.api.vo.TournamentFinishVO;
import com.old.silence.content.api.vo.TournamentStartVO;
import com.old.silence.content.domain.common.MarketingBizException;
import com.old.silence.content.domain.model.TournamentChallengeRecord;
import com.old.silence.content.domain.service.tournament.TournamentChallengeDomainService;
import com.old.silence.content.infrastructure.persistence.tournament.dao.TournamentChallengeDao;
import com.old.silence.web.bind.annotation.PostJsonMapping;
import com.old.silence.web.bind.annotation.PutJsonMapping;

import java.math.BigInteger;
import java.util.Objects;

/**
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class TournamentChallengeResource implements TournamentChallengeService {

    private static final Logger logger = LoggerFactory.getLogger(TournamentChallengeResource.class);

    private final TournamentChallengeDomainService tournamentChallengeDomainService;

    private final TournamentChallengeDao tournamentChallengeDao;

    public TournamentChallengeResource(TournamentChallengeDomainService tournamentChallengeDomainService,
            TournamentChallengeDao tournamentChallengeDao) {
        this.tournamentChallengeDomainService = tournamentChallengeDomainService;
        this.tournamentChallengeDao = tournamentChallengeDao;
    }

    @Override
    public Response<TournamentStartVO> startChallenge(StartChallengeCommand command) {
        try {
            // 校验入参（可选：已在@Validated中处理）
            if (Objects.isNull(command)) {
                return Response.fail("请求参数不能为空");
            }

            TournamentStartVO startVO = tournamentChallengeDomainService.startChallenge(command);
            return Response.success(startVO);
        } catch (MarketingBizException e) {
            logger.warn("业务异常 - 开始挑战失败: {}", e.getMessage(), e);
            return Response.fail(e.getMessage());
        } catch (Exception e) {
            logger.error("系统异常 - 开始挑战失败", e);
            return Response.fail("系统异常，请稍后重试");
        }
    }

    @Override
    public Response<TournamentFinishVO> completeChallenge(CompleteChallengeCommand command) {
        try {
            if (Objects.isNull(command)) {
                return Response.fail("请求参数不能为空");
            }
            TournamentFinishVO finishVO = tournamentChallengeDomainService.completeChallenge(command);
            return Response.success(finishVO);

        } catch (MarketingBizException e) {
            logger.warn("业务异常 - 完成挑战失败: {}", e.getMessage(), e);
            return Response.fail(e.getMessage());
        } catch (Exception e) {
            logger.error("系统异常 - 完成挑战失败", e);
            return Response.fail("系统异常，请稍后重试");
        }
    }


    @PostJsonMapping(value = "/tournamentChallenges")
    public BigInteger create(@RequestBody TournamentChallengeRecord tournamentChallengeRecord) {
        tournamentChallengeDao.insert(tournamentChallengeRecord);
        return tournamentChallengeRecord.getId();
    }

    @PutJsonMapping(value = "/tournamentChallenges/{id}")
    public Integer update(@PathVariable BigInteger id, @RequestBody TournamentChallengeRecord tournamentChallengeRecord) {
        tournamentChallengeRecord.setId(id);
        return tournamentChallengeDao.updateNonNull(tournamentChallengeRecord);
    }

    @DeleteMapping(value = "/tournamentChallenges/{id}")
    public Integer delete(@PathVariable BigInteger id) {
        return tournamentChallengeDao.deleteById(id);
    }
}
