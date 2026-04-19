package com.old.silence.content.infrastructure.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import com.old.silence.json.JacksonMapper;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Component
public class HsmsClient {

    private static final Logger logger = LoggerFactory.getLogger(HsmsClient.class);

    private final JacksonMapper jacksonMapper;


    private final static String JMP_CODE = "yx_zljmp";
    private final static String JMP_CODE_KMH = "yx_zljmp_day";
    public HsmsClient(JacksonMapper jacksonMapper) {
        this.jacksonMapper = jacksonMapper;
    }

    public CompetitionActivitiesResp checkCompetitionActivitiesRegister(BigInteger eventGameId, BigInteger participantId){
        CompetitionActivitiesRequest request = new CompetitionActivitiesRequest();
        request.setCustomerNo(participantId.toString());
        request.setEventGameId(eventGameId.toString());

        var competitionActivitiesResp = new CompetitionActivitiesResp();
        competitionActivitiesResp.setCheckRegister(true);
        competitionActivitiesResp.setDistChannel("V1");
        competitionActivitiesResp.setCustomerNo(participantId.toString());
        return  competitionActivitiesResp;
    }

}
