package com.old.silence.content.code.generator.dto;

/**
 * 枚举识别请求
 * 用于从表结构中识别潜在的枚举字段
 *
 * @author moryzang
 */
public class EnumDetectionRequest {

    /**
     * 表名，为空则检测所有表
     */
    private String tableName;

    /**
     * 数据库连接配置
     */
    private String dbUrl;
    private String username;
    private String password;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
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
