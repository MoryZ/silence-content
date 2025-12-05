package com.old.silence.content.api.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * @author moryzang
 */
public class CodeGenDatabaseCommand {

    @NotBlank
    private String databaseName;

    @NotBlank
    private String databaseUrl;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
