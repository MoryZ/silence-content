package com.old.silence.content.console.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.old.silence.content.domain.enums.ContentConstants;

/**
 * @author MurrayZhang
 * @Description
 */
@Component
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Value("${silence.content.jwt.secret:silence-hall}")
    private String jwtSecret;

    @Value("${silence.hall.jwt.expiration:3600}")
    private Long jwtExpirationSeconds;


    public String issueToken(String openid) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        Map<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("alg", algorithm.getName());
        headerClaims.put("typ", ContentConstants.TOKEN_TYPE);

        Instant now = Instant.now();
        return JWT.create()
                .withHeader(headerClaims)
                .withSubject(openid)
                .withIssuer(ContentConstants.TOKEN_ISSUER)
                .withAudience(ContentConstants.TOKEN_AUDIENCE)
                .withIssuedAt(now)
                .withExpiresAt(now.plusSeconds(jwtExpirationSeconds))
                .sign(algorithm);
    }

    public boolean verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            verifier.verify(token);
        } catch (JWTDecodeException | SignatureVerificationException ex) {
            LOGGER.error("verify token failed:{}", ex.getLocalizedMessage());
            return false;
        } catch (TokenExpiredException ex) {
            LOGGER.warn("The token is expired:{}", token);
            return false;
        }
        return true;
    }

    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256(jwtSecret))
                .build().verify(token)
                .getSubject();
    }
}
