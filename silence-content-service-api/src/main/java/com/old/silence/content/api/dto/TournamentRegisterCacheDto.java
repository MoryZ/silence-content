package com.old.silence.content.api.dto;


import java.math.BigInteger;
import java.time.Instant;

public class TournamentRegisterCacheDto {

    private BigInteger id;

    private Instant registrationTime;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Instant getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Instant registrationTime) {
        this.registrationTime = registrationTime;
    }
}
