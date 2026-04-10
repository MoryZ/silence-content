package com.old.silence.content.domain.service.tournament.task;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;
import com.old.silence.content.domain.enums.tournament.TaskTypeEnum;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 赛事任务处理器工厂
 *
 * @author moryzang
 */
@Component
public class TournamentTaskHandlerFactory {

    private final Map<TaskTypeEnum, ITournamentTaskHandler> handlerMap;

    public TournamentTaskHandlerFactory(ObjectProvider<ITournamentTaskHandler> handlerProvider) {
        this.handlerMap = handlerProvider.stream()
                .collect(Collectors.toMap(ITournamentTaskHandler::getTaskType, Function.identity()));
    }

    public ITournamentTaskHandler getHandler(TaskTypeEnum taskType) {
        ITournamentTaskHandler handler = handlerMap.get(taskType);
        if (handler == null) {
            throw new IllegalArgumentException("No handler found for task type: " + taskType);
        }
        return handler;
    }
}
