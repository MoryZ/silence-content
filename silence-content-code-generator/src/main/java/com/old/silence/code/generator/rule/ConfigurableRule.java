package com.old.silence.code.generator.rule;

import java.util.List;
import java.util.Map;

import com.old.silence.code.generator.model.Rule;

/**
 * @author moryzang
 */

public class ConfigurableRule implements Rule {
    private String name;
    private String type;
    private String condition;
    private List<ActionConfig> actions;
    private int priority;
    private boolean enabled = true;

    private final transient ExpressionEvaluator evaluator = new ExpressionEvaluator();
    private final transient ActionExecutor actionExecutor = new ActionExecutor();

    @Override
    public boolean evaluateCondition(Map<String, Object> context) {
        return evaluator.evaluateBoolean(condition, context);
    }

    @Override
    public void executeAction(Map<String, Object> context) {
        for (ActionConfig actionConfig : actions) {
            actionExecutor.executeAction(actionConfig, context);
        }
    }

    // Getter和Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public List<ActionConfig> getActions() { return actions; }
    public void setActions(List<ActionConfig> actions) { this.actions = actions; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}

