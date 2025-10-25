package com.old.silence.content.console.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * @author moryzang
 */
public class BindPhoneRequest {

    @NotBlank(message = "code不能为空")
    private String code;

    @NotBlank(message = "加密数据不能为空")
    private String encryptedData;

    @NotBlank(message = "IV不能为空")
    private String iv;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }
}
