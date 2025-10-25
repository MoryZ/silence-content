package com.old.silence.content.console.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * @author moryzang
 */
public class LoginRequest {

    @NotBlank
    private String code;
    private String rawData;
    private String signature;
    private String encryptedData;
    private String iv;
    private String phoneEncryptedData;
    private String phoneIv;
    private String phoneKey;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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

    public String getPhoneEncryptedData() {
        return phoneEncryptedData;
    }

    public void setPhoneEncryptedData(String phoneEncryptedData) {
        this.phoneEncryptedData = phoneEncryptedData;
    }

    public String getPhoneIv() {
        return phoneIv;
    }

    public void setPhoneIv(String phoneIv) {
        this.phoneIv = phoneIv;
    }

    public String getPhoneKey() {
        return phoneKey;
    }

    public void setPhoneKey(String phoneKey) {
        this.phoneKey = phoneKey;
    }
}
