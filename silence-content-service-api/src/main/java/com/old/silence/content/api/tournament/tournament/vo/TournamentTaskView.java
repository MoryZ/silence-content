package com.old.silence.content.api.tournament.tournament.vo;


import org.springframework.data.web.ProjectedPayload;
import com.old.silence.data.commons.domain.AuditableView;

import java.math.BigInteger;

@ProjectedPayload
public interface TournamentTaskView extends AuditableView {

    BigInteger getId();
}
