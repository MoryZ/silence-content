package com.old.silence.content.api;

import org.springframework.web.bind.annotation.RequestBody;


import com.old.silence.content.api.dto.CompleteChallengeCommand;
import com.old.silence.content.api.dto.StartChallengeCommand;
import com.old.silence.content.api.dto.common.Response;
import com.old.silence.content.api.vo.TournamentFinishVO;
import com.old.silence.content.api.vo.TournamentStartVO;
import com.old.silence.web.bind.annotation.PostJsonMapping;

/**
 * @author moryzang
 */
public interface TournamentChallengeService {
    /**
     * 开始挑战
     * @param command
     * @return
     */
    @PostJsonMapping("/tournament/challenge/start")
    Response<TournamentStartVO> startChallenge(@RequestBody StartChallengeCommand command);

    /**
     * 完成挑战
     * @param command
     * @return
     */
    @PostJsonMapping("/tournament/challenge/complete")
    Response<TournamentFinishVO> completeChallenge(@RequestBody CompleteChallengeCommand command);
}
