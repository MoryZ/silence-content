package com.old.silence.code.generator.model;

import java.util.Map;

/**
 * @author moryzang
 */

public interface Rule {
    String getName();
    String getType();
    boolean isEnabled();
    int getPriority();
    boolean evaluateCondition(Map<String, Object> context);
    void executeAction(Map<String, Object> context);
}