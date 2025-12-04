package com.old.silence.content.code.generator.model;

import java.util.List;
import java.util.Map;

/**
 * @author moryzang
 */
public class ApiEndpoint {

    private String method;
    private String path;
    private String description;
    private List<Parameter> parameters;
    private String requestJsonExample;
    private ResponseInfo<?> response;

    private Map<String, Object> metadata;

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public String getRequestJsonExample() {
        return requestJsonExample;
    }

    public void setRequestJsonExample(String requestJsonExample) {
        this.requestJsonExample = requestJsonExample;
    }

    public ResponseInfo<?> getResponse() {
        return response;
    }

    public void setResponse(ResponseInfo<?> response) {
        this.response = response;
    }
}
