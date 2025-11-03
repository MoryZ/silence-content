package com.old.silence.code.generator.model;

/**
 * @author moryzang
 */
public class ResponseInfo<T> {

    private String code;        // 状态码
    private String message;     // 提示信息
    private T data;             // 返回数据

    // 带参数构造函数
    public ResponseInfo(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 成功响应的静态工厂方法
    public static <T> ResponseInfo<T> success(T data) {
        return new ResponseInfo<>("200", "成功", data);
    }

    public static <T> ResponseInfo<T> success(String message, T data) {
        return new ResponseInfo<>("200", message, data);
    }

    // 错误响应的静态工厂方法
    public static <T> ResponseInfo<T> error(String code, String message) {
        return new ResponseInfo<>(code, message, null);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseInfo{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
