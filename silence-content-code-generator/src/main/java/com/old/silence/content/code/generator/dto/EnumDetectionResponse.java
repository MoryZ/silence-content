package com.old.silence.content.code.generator.dto;

import com.old.silence.content.code.generator.config.EnumConfig;

import java.util.List;

/**
 * 枚举识别响应
 *
 * @author moryzang
 */
public class EnumDetectionResponse {

    /**
     * 识别到的潜在枚举字段列表
     */
    private List<EnumSuggestion> suggestions;

    public static class EnumSuggestion {
        /**
         * 表名
         */
        private String tableName;

        /**
         * 字段名
         */
        private String columnName;

        /**
         * 数据库类型
         */
        private String dbType;

        /**
         * 字段注释
         */
        private String comment;

        /**
         * 建议的枚举类名
         */
        private String suggestedEnumClassName;

        /**
         * 从注释中解析出的枚举值（如果有）
         */
        private List<EnumConfig.EnumValue> parsedValues;

        /**
         * 是否强烈建议生成枚举
         * true: 字段类型是 tinyint 且注释包含枚举信息
         * false: 需要用户判断
         */
        private Boolean strongSuggestion;

        /**
         * 建议原因
         */
        private String reason;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public String getDbType() {
            return dbType;
        }

        public void setDbType(String dbType) {
            this.dbType = dbType;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getSuggestedEnumClassName() {
            return suggestedEnumClassName;
        }

        public void setSuggestedEnumClassName(String suggestedEnumClassName) {
            this.suggestedEnumClassName = suggestedEnumClassName;
        }

        public List<EnumConfig.EnumValue> getParsedValues() {
            return parsedValues;
        }

        public void setParsedValues(List<EnumConfig.EnumValue> parsedValues) {
            this.parsedValues = parsedValues;
        }

        public Boolean getStrongSuggestion() {
            return strongSuggestion;
        }

        public void setStrongSuggestion(Boolean strongSuggestion) {
            this.strongSuggestion = strongSuggestion;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

    public List<EnumSuggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<EnumSuggestion> suggestions) {
        this.suggestions = suggestions;
    }
}
