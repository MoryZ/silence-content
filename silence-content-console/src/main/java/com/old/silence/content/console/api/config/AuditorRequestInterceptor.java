package com.old.silence.content.console.api.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import com.old.silence.hall.security.SilenceHallContextHolder;

/**
 * @author moryzang
 */
@Component
public class AuditorRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        SilenceHallContextHolder.getAuthenticatedUserName()
                .ifPresent(username -> template.header("SILENCE_HALL_USERNAME",  username));

    }
}
