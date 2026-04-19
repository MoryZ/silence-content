package com.old.silence.bp.vo;

import java.time.Instant;
import java.util.Map;

public interface TournamentConfigBpView {

    Instant getTournamentEndTime();

    Map<String, Object> getAttributes();

}
