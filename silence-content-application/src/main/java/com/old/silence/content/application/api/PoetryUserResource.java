package com.old.silence.content.application.api;

import java.math.BigInteger;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.PoetryUserClient;
import com.old.silence.content.application.api.assembler.PoetryUserApplicationMapper;
import com.old.silence.content.application.api.dto.PoetryUserApplicationCommand;


/**
 * PoetryUser资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryUserResource implements PoetryUserApplicationService {

    private final PoetryUserClient poetryUserClient;
    private final PoetryUserApplicationMapper poetryUserApplicationMapper;

    public PoetryUserResource(PoetryUserClient poetryUserClient,
                              PoetryUserApplicationMapper poetryUserApplicationMapper) {
        this.poetryUserClient = poetryUserClient;
        this.poetryUserApplicationMapper = poetryUserApplicationMapper;
    }

    @Override
    public <T> Optional<T> findByOpenid(String openid, Class<T> projectionType) {
        return poetryUserClient.findByOpenid(openid, projectionType);
    }

    @Override
    public BigInteger create(PoetryUserApplicationCommand command) {
        var poetryUserCommand = poetryUserApplicationMapper.convert(command);
        return poetryUserClient.create(poetryUserCommand);
    }

    @Override
    public void update(BigInteger id, PoetryUserApplicationCommand command) {
        var poetryUserCommand = poetryUserApplicationMapper.convert(command);
        poetryUserClient.update(id, poetryUserCommand);
    }
}