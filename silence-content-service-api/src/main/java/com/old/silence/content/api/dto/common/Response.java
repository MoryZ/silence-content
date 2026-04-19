package com.old.silence.content.api.dto.common;

public class Response<T> {

	public static final String SUCCESS_CODE = "00";

	/**
	 * 通用错误返回码
	 */
	public static final String ERROR_CODE = "5001";

	private String code;

	private String message;

	private  T data;

	public Response(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public Response(T data) {
		this.data = data;
	}

	public static <T> Response<T> success(T data) {
		return new Response<>(SUCCESS_CODE, "", data);
	}

	public static <T> Response<T> fail(String message) {
		return new Response<>(ERROR_CODE, message, null);
	}

	public static <T> Response<T> fail(String code, String message) {
		return new Response<>(code, message, null);
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
}
