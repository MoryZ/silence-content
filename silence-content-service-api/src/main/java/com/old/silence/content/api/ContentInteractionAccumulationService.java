package com.old.silence.content.api;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import com.old.silence.content.api.dto.ContentInteractionAccumulationCommand;
import com.old.silence.web.bind.annotation.PostJsonMapping;

import java.util.List;

/**
 * 互动累计同步服务接口
 */
interface ContentInteractionAccumulationService {

    @PostJsonMapping("/contentInteractionAccumulations/batchUpsert")
    int batchUpsert(@RequestBody @Validated List<ContentInteractionAccumulationCommand> commands);
}