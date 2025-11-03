package com.old.silence.code.generator.rule;

import java.util.Map;

/**
 * @author moryzang
 */
public interface Action {

    String getType();

    Object execute(Object target, Map<String, Object> config, Map<String, Object> context);
}