package com.old.silence.code.generator.rule;

import java.util.Map;

/**
 * @author moryzang
 */
public record ActionConfig(String type, Map<String, Object> config) {
    // Record 自动生成构造函数、getter、equals、hashCode、toString
}