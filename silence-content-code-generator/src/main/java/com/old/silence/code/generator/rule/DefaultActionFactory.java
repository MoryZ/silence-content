package com.old.silence.code.generator.rule;

import java.util.HashMap;
import java.util.Map;

/**
 * @author moryzang
 */
public class DefaultActionFactory implements ActionFactory {
    private final Map<String, Action> actionCache = new HashMap<>();

    @Override
    public Action createAction(String actionType) {
        return actionCache.computeIfAbsent(actionType, this::createNewAction);
    }

    private Action createNewAction(String actionType) {
        return switch (actionType) {
            case "regex_replace" -> new RegexReplaceAction();
            case "expression" -> new ExpressionAction();
            default -> throw new IllegalArgumentException("未知的动作类型: " + actionType);
        };
    }
}