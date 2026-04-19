package com.old.silence.content.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.old.silence.content.api.dto.TournamentCurrentGroupVO;
import com.old.silence.content.api.dto.TournamentGlobalRankQuery;
import com.old.silence.content.api.dto.TournamentRankListVO;
import com.old.silence.content.api.dto.common.Response;
import com.old.silence.content.api.tournament.tournament.dto.TournamentRankingDto;
import com.old.silence.web.bind.annotation.GetJsonMapping;
import com.old.silence.web.bind.annotation.PostJsonMapping;

import java.math.BigInteger;
import java.util.List;

/**
 * @author moryzang
 */
public interface TournamentRankService {
    @GetJsonMapping("/tournament/ranking/detail")
    Response<TournamentRankListVO> getRankDetail(TournamentGlobalRankQuery query);

    @GetJsonMapping("/tournament/ranking/personal")
    Response<TournamentCurrentGroupVO> getPersonalRank(TournamentPersonalRankQuery query);

    @PostJsonMapping("/tournament/ranking/batchAddRankRecord")
    void batchAddRankRecord(@RequestBody List<TournamentRankingDto> tournamentRankingList);

    @DeleteMapping(value = "/tournamentRankings/{id}")
    Integer delete(@PathVariable BigInteger id);

}
