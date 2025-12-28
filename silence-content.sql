/*
 Navicat Premium Dump SQL

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 90000 (9.0.0)
 Source Host           : localhost:3306
 Source Schema         : silence-content

 Target Server Type    : MySQL
 Target Server Version : 90000 (9.0.0)
 File Encoding         : 65001

 Date: 29/12/2025 01:10:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '父图书ID(多册时指向第一册)',
  `book_type` tinyint UNSIGNED NOT NULL DEFAULT 1 COMMENT '图书类型:1-单册书 2-多册书的主册 3-多册书的子册',
  `isbn` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ISBN',
  `isbn_series` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '丛书ISBN(多册书使用)',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图书名称',
  `series_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '丛书总名称(多册书使用)',
  `volume_number` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '册号(1,2,3...)',
  `volume_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '册名称(上册/下册)',
  `cover_image_reference` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '封面引用',
  `content_reference` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '正文引用',
  `status` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '状态',
  `published_at` timestamp NULL DEFAULT NULL COMMENT '发布时间',
  `author` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '作者',
  `price` decimal(10, 2) NOT NULL COMMENT '价格',
  `press` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '出版社',
  `owner` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所有者',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '描述',
  `total_volumes` tinyint UNSIGNED NULL DEFAULT 1 COMMENT '总册数',
  `sort_order` int NULL DEFAULT 0 COMMENT '排序',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` timestamp(3) NULL DEFAULT NULL,
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id` ASC) USING BTREE,
  INDEX `idx_book_type`(`book_type` ASC) USING BTREE,
  INDEX `idx_isbn`(`isbn` ASC) USING BTREE,
  INDEX `idx_series_isbn`(`isbn_series` ASC) USING BTREE,
  CONSTRAINT `fk_book_parent` FOREIGN KEY (`parent_id`) REFERENCES `book` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '图书表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of book
-- ----------------------------

-- ----------------------------
-- Table structure for book_content_tag
-- ----------------------------
DROP TABLE IF EXISTS `book_content_tag`;
CREATE TABLE `book_content_tag`  (
  `book_id` bigint UNSIGNED NOT NULL COMMENT '图书ID',
  `tag_id` bigint UNSIGNED NOT NULL COMMENT '标签ID',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '图书标签关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of book_content_tag
-- ----------------------------

-- ----------------------------
-- Table structure for code_api_document
-- ----------------------------
DROP TABLE IF EXISTS `code_api_document`;
CREATE TABLE `code_api_document`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `table_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '对应的表名',
  `api_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'API文档名称',
  `detail` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '完整API文档JSON(包含endpoints、parameters、responses)',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人',
  `updated_date` timestamp(3) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '代码生成器-API文档' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of code_api_document
-- ----------------------------

-- ----------------------------
-- Table structure for code_file_spec
-- ----------------------------
DROP TABLE IF EXISTS `code_file_spec`;
CREATE TABLE `code_file_spec`  (
  `id` bigint UNSIGNED NOT NULL COMMENT '主键',
  `template_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '模板文件名（如 command.ftl）',
  `module_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '所属模块类型（CONSOLE/SERVICE/SERVICE_API/ENUM）',
  `package_suffix` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '包名后缀（如 .api.dto）',
  `relative_dir` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '相对输出目录（如 api/dto）',
  `file_name_suffix` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '文件名后缀（如 Command, Query）',
  `file_type_tag` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '文件类型标签（dto, vo, service 等，用于预览分组）',
  `generation_condition` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '生成条件（ALWAYS/HAS_ANY_ENDPOINT/HAS_ENDPOINT）',
  `endpoint_names` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '触发生成的端点名称（JSON数组），用于 HAS_ENDPOINT 条件',
  `display_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '显示名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '描述文本',
  `version` int NOT NULL COMMENT '版本号',
  `is_enabled` bit(1) NOT NULL COMMENT '是否启用',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `template_name`(`template_name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '代码文件规格表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of code_file_spec
-- ----------------------------
INSERT INTO `code_file_spec` VALUES (1, 'command.ftl', 'SERVICE_API', '.api.dto', 'api/dto', 'Command', 'dto', 'HAS_ENDPOINT', '[\"创建\",\"更新\"]', '创建/更新命令对象', '用于创建和更新操作的DTO对象', 1, b'1', 'moryzang', '2025-12-14 22:34:55.787', 'moryzang', NULL);
INSERT INTO `code_file_spec` VALUES (2, 'query.ftl', 'SERVICE_API', '.api.dto', 'api/dto', 'Query', 'dto', 'HAS_ENDPOINT', '[\"分页查询\"]', '分页查询对象', '用于分页查询的DTO对象', 1, b'1', 'moryzang', '2025-12-14 22:34:55.787', 'moryzang', NULL);
INSERT INTO `code_file_spec` VALUES (3, 'view.ftl', 'SERVICE_API', '.api.vo', 'api/vo', 'View', 'vo', 'HAS_ENDPOINT', '[\"根据主键查询\",\"分页查询\"]', '响应视图对象', '用于API响应的视图对象', 1, b'1', 'moryzang', '2025-12-14 22:34:55.787', 'moryzang', NULL);
INSERT INTO `code_file_spec` VALUES (4, 'service.ftl', 'SERVICE_API', '.api', 'api', 'Service', 'service', 'HAS_ANY_ENDPOINT', NULL, '服务接口', '定义业务服务的接口契约', 1, b'1', 'moryzang', '2025-12-14 22:34:55.787', 'moryzang', NULL);
INSERT INTO `code_file_spec` VALUES (5, 'client.ftl', 'SERVICE_API', '.api', 'api', 'Client', 'client', 'HAS_ANY_ENDPOINT', NULL, 'Feign客户端', '用于服务间通信的Feign客户端', 1, b'1', 'moryzang', '2025-12-14 22:34:55.787', 'moryzang', NULL);
INSERT INTO `code_file_spec` VALUES (6, 'model.ftl', 'SERVICE', '.domain.model', 'domain/model', '', 'model', 'ALWAYS', NULL, '领域模型', '领域层的核心实体模型', 1, b'1', 'moryzang', '2025-12-14 22:34:55.787', 'moryzang', NULL);
INSERT INTO `code_file_spec` VALUES (7, 'mapper.ftl', 'SERVICE', '.api.assembler', 'api/assembler', 'Mapper', 'mapper', 'HAS_ENDPOINT', '[\"创建\",\"更新\",\"根据主键查询\",\"分页查询\"]', '数据转换器', '用于DTO和领域模型之间的转换', 1, b'1', 'moryzang', '2025-12-14 22:34:55.787', 'moryzang', NULL);
INSERT INTO `code_file_spec` VALUES (8, 'dao.ftl', 'SERVICE', '.infrastructure.persistence.dao', 'infrastructure/persistence/dao', 'Dao', 'dao', 'ALWAYS', NULL, '数据访问层', '直接操作数据库的DAO层', 1, b'1', 'moryzang', '2025-12-14 22:34:55.787', 'moryzang', NULL);
INSERT INTO `code_file_spec` VALUES (9, 'repository.ftl', 'SERVICE', '.domain.repository', 'domain/repository', 'Repository', 'repository', 'ALWAYS', NULL, '仓储接口', '定义领域对象的仓储契约', 1, b'1', 'moryzang', '2025-12-14 22:34:55.787', 'moryzang', NULL);
INSERT INTO `code_file_spec` VALUES (10, 'repository-impl.ftl', 'SERVICE', '.infrastructure.persistence', 'infrastructure/persistence', 'MyBatisRepository', 'repository-impl', 'ALWAYS', NULL, '仓储实现', '使用MyBatis实现的仓储', 1, b'1', 'moryzang', '2025-12-14 22:34:55.787', 'moryzang', NULL);
INSERT INTO `code_file_spec` VALUES (11, 'resource.ftl', 'SERVICE', '.api', 'api', 'Resource', 'resource', 'HAS_ANY_ENDPOINT', NULL, 'REST资源', 'REST API资源类', 1, b'1', 'moryzang', '2025-12-14 22:34:55.787', 'moryzang', NULL);
INSERT INTO `code_file_spec` VALUES (12, 'consoleCommand.ftl', 'CONSOLE', '.dto', 'dto', 'ConsoleCommand', 'dto', 'HAS_ENDPOINT', '[\"创建\",\"更新\"]', '控制台命令对象', '控制台层的创建/更新命令对象', 1, b'1', 'moryzang', '2025-12-14 22:34:55.787', 'SYSTEM', '2025-12-27 13:04:30.698');
INSERT INTO `code_file_spec` VALUES (13, 'consoleQuery.ftl', 'CONSOLE', '.dto', 'dto', 'ConsoleQuery', 'dto', 'HAS_ENDPOINT', '[\"分页查询\"]', '控制台查询对象', '控制台层的分页查询对象', 1, b'1', 'moryzang', '2025-12-14 22:34:55.787', 'SYSTEM', '2025-12-27 13:04:50.262');
INSERT INTO `code_file_spec` VALUES (14, 'consoleView.ftl', 'CONSOLE', '.vo', 'vo', 'ConsoleView', 'vo', 'HAS_ENDPOINT', '[\"根据主键查询\",\"分页查询\"]', '控制台视图对象', '控制台层的响应视图对象', 1, b'1', 'moryzang', '2025-12-14 22:34:55.787', 'SYSTEM', '2025-12-27 13:04:59.993');
INSERT INTO `code_file_spec` VALUES (15, 'commandMapper.ftl', 'CONSOLE', '.api.assembler', 'api/assembler', 'CommandMapper', 'mapper', 'HAS_ENDPOINT', '[\"创建\",\"更新\"]', '控制台命令转换器', '控制台命令对象与领域模型的转换', 1, b'1', 'moryzang', '2025-12-14 22:34:55.787', 'SYSTEM', '2025-12-27 13:05:10.026');
INSERT INTO `code_file_spec` VALUES (16, 'queryMapper.ftl', 'CONSOLE', '.api.assembler', 'api/assembler', 'QueryMapper', 'mapper', 'HAS_ENDPOINT', '[\"分页查询\"]', '控制台查询转换器', '控制台查询对象与领域模型的转换', 1, b'1', 'moryzang', '2025-12-14 22:34:55.787', 'SYSTEM', '2025-12-27 13:05:34.676');
INSERT INTO `code_file_spec` VALUES (17, 'consoleResource.ftl', 'CONSOLE', '.api', 'api', 'Resource', 'resource', 'HAS_ANY_ENDPOINT', '[\"创建\",\"更新\"]', '控制台资源', '控制台的REST API资源类', 1, b'1', 'moryzang', '2025-12-14 22:34:55.787', 'SYSTEM', '2025-12-27 13:05:17.624');
INSERT INTO `code_file_spec` VALUES (18, 'enum.ftl', 'ENUM', '.domain.enums', 'domain/enums', '', 'enum', 'ALWAYS', NULL, '枚举类', '根据表字段动态生成的枚举类', 1, b'1', 'moryzang', '2025-12-14 22:34:55.787', 'moryzang', NULL);

-- ----------------------------
-- Table structure for code_file_template
-- ----------------------------
DROP TABLE IF EXISTS `code_file_template`;
CREATE TABLE `code_file_template`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `module_id` bigint NOT NULL COMMENT '模块id',
  `template_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模板名称',
  `template_type` tinyint NULL DEFAULT NULL COMMENT '模板类型',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模板内容',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改人员',
  `updated_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'freemarker模板表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of code_file_template
-- ----------------------------
INSERT INTO `code_file_template` VALUES (1, 5, 'api.ftl', 1, '<#-- API 模板 -->\r\nimport request from \'@/utils/request\'\r\nimport type {\r\n  ${typeName},\r\n  ${queryParamsName},\r\n  ${createParamsName},\r\n  ${updateParamsName}\r\n} from \'@/types/${camelName}\'\r\nimport type { PaginationResponse } from \'@/types/common\'\r\n\r\n<#-- 列表接口 -->\r\n<#if listRoute??>\r\nexport function get${typeName}List(params: ${queryParamsName}) {\r\n  return request.get<PaginationResponse<${typeName}>>(\'${apiBasePath}\', { params })\r\n}\r\n</#if>\r\n\r\n<#-- 详情接口 -->\r\n<#if detailRoute??>\r\nexport function get${typeName}ById(id: number) {\r\n  return request.get<${typeName}>(`${apiBasePath}/${\'$\'}{id}`)\r\n}\r\n</#if>\r\n\r\n<#-- 创建接口 -->\r\n<#if createRoute??>\r\nexport function create${typeName}(data: ${createParamsName}) {\r\n  return request.post<${typeName}>(\'${apiBasePath}\', data)\r\n}\r\n</#if>\r\n\r\n<#-- 更新接口 -->\r\n<#if updateRoute??>\r\nexport function update${typeName}(id: number, data: ${updateParamsName}) {\r\n  return request.put<${typeName}>(`${apiBasePath}/${\'$\'}{id}`, data)\r\n}\r\n</#if>\r\n\r\n<#-- 删除接口 -->\r\n<#if deleteRoute??>\r\nexport function delete${typeName}(id: number) {\r\n  return request.delete<void>(`${apiBasePath}/${\'$\'}{id}`)\r\n}\r\n</#if>\r\n', '1', 'SYSTEM', '2025-12-09 14:53:16.211', 'SYSTEM', '2025-12-09 14:53:16.211');
INSERT INTO `code_file_template` VALUES (2, 2, 'client.ftl', 2, 'package ${packageName};\r\n\r\nimport org.springframework.cloud.openfeign.FeignClient;\r\n\r\n/**\r\n* ${className}Feign客户端\r\n*/\r\n@FeignClient(name = \"${applicationName!\"应用名\"}\", contextId = \"${contextId}\", path = \"/api/v1\")\r\npublic interface ${className}Client extends ${className}Service {\r\n}', '1', 'SYSTEM', '2025-12-09 14:54:26.532', 'SYSTEM', '2025-12-09 14:54:26.532');
INSERT INTO `code_file_template` VALUES (3, 2, 'command.ftl', 3, 'package ${packageName};\r\n\r\n<#if enumSet?has_content>\r\n    <#list enumSet as enumName>\r\nimport com.old.silence.content.domain.enums.${enumName};\r\n    </#list>\r\n</#if>\r\n<#if hasNotBlank>import jakarta.validation.constraints.NotBlank;</#if>\r\n<#if hasSize>import jakarta.validation.constraints.Size;</#if>\r\n<#if hasNotNull>import jakarta.validation.constraints.NotNull;</#if>\r\n<#if hasInstantType>import java.time.Instant;\r\n</#if>\r\n<#if hasBigDecimalType>import java.math.BigDecimal;\r\n</#if>\r\n<#if hasBigIntegerType>import java.math.BigInteger;\r\n</#if>\r\n/**\r\n* ${className}命令对象\r\n*/\r\npublic class ${className}Command {\r\n<#list columnInfos as column>\r\n    <#if !column.nullable>\r\n        <#if  getJavaType(column) == \"String\">\r\n    @NotBlank\r\n    @Size(max = ${column.length?c})\r\n    <#elseif isCollectionType(column)>\r\n        @NotEmpty\r\n    <#elseif isNumericType(column) || isBooleanType(column) || isInstantType(column)>    @NotNull\r\n    </#if>\r\n    </#if>\r\n    private ${getJavaType(column)} ${column.fieldName};\r\n</#list>\r\n\r\n<#-- Getter和Setter方法 -->\r\n<#list columnInfos as column>\r\n    public ${getJavaType(column)} get${toCamelCase(column.fieldName, true)}() {\r\n        return this.${toCamelCase(column.fieldName, false)};\r\n    }\r\n\r\n    public void set${toCamelCase(column.fieldName, true)}(${getJavaType(column)} ${toCamelCase(column.fieldName, false)}) {\r\n        this.${toCamelCase(column.fieldName, false)} = ${toCamelCase(column.fieldName, false)};\r\n    }\r\n</#list>\r\n}', '2', 'SYSTEM', '2025-12-29 00:55:50.786', 'SYSTEM', '2025-12-28 16:55:50.785');
INSERT INTO `code_file_template` VALUES (4, 4, 'commandMapper.ftl', 4, 'package ${packageName};\r\n\r\nimport org.mapstruct.Mapper;\r\nimport org.springframework.core.convert.converter.Converter;\r\n\r\nimport com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;\r\nimport ${basePackage}.dto.${className}ConsoleCommand;\r\nimport ${serviceApiPackage}.api.dto.${className}Command;\r\n\r\n/**\r\n* ${className}Command映射器\r\n*/\r\n@Mapper(uses = SilenceMapStructSpringConfig.class)\r\npublic interface ${className}CommandMapper extends Converter<${className}ConsoleCommand, ${className}Command>{\r\n\r\n    @Override\r\n    ${className}Command convert(${className}ConsoleCommand command);\r\n}', NULL, 'SYSTEM', '2025-12-29 01:00:04.678', 'SYSTEM', '2025-12-28 17:00:04.676');
INSERT INTO `code_file_template` VALUES (5, 4, 'consoleCommand.ftl', 5, 'package ${packageName};\r\n\r\n<#if enumSet?has_content>\r\n    <#list enumSet as enumName>\r\nimport com.old.silence.content.domain.enums.${enumName};\r\n    </#list>\r\n</#if>\r\n\r\n<#if hasNotBlank>import jakarta.validation.constraints.NotBlank;</#if>\r\n<#if hasSize>import jakarta.validation.constraints.Size;</#if>\r\n<#if hasNotNull>import jakarta.validation.constraints.NotNull;</#if>\r\n<#if hasInstantType>import java.time.Instant;\r\n</#if>\r\n<#if hasBigDecimalType>import java.math.BigDecimal;\r\n</#if>\r\n<#if hasBigIntegerType>import java.math.BigInteger;\r\n</#if>\r\n/**\r\n* ${className}命令对象\r\n*/\r\npublic class ${className}ConsoleCommand {\r\n<#list columnInfos as column>\r\n    <#if !column.nullable>\r\n        <#if  getJavaType(column) == \"String\">\r\n    @NotBlank\r\n    @Size(max = ${column.length?c})\r\n    <#elseif isCollectionType(column)>\r\n        @NotEmpty\r\n    <#elseif isNumericType(column) || isBooleanType(column) || isInstantType(column)>    @NotNull\r\n    </#if>\r\n    </#if>\r\n    private ${getJavaType(column)} ${toCamelCase(column.fieldName, false)};\r\n</#list>\r\n\r\n<#-- Getter和Setter方法 -->\r\n<#list columnInfos as column>\r\n    public ${getJavaType(column)} get${toCamelCase(column.fieldName, true)}() {\r\n        return this.${toCamelCase(column.fieldName, false)};\r\n    }\r\n\r\n    public void set${toCamelCase(column.fieldName, true)}(${getJavaType(column)} ${toCamelCase(column.fieldName, false)}) {\r\n        this.${toCamelCase(column.fieldName, false)} = ${toCamelCase(column.fieldName, false)};\r\n    }\r\n</#list>\r\n}', NULL, 'SYSTEM', '2025-12-29 00:55:30.483', 'SYSTEM', '2025-12-28 16:55:30.481');
INSERT INTO `code_file_template` VALUES (6, 4, 'consoleQuery.ftl', 6, 'package ${packageName};\r\n\r\n<#if enumSet?has_content>\r\n    <#list enumSet as enumName>\r\nimport com.old.silence.content.domain.enums.${enumName};\r\n    </#list>\r\n</#if>\r\n<#if hasInstantType>import java.time.Instant;\r\n</#if>\r\n<#if hasBigDecimalType>import java.math.BigDecimal;\r\n</#if>\r\n<#if hasBigIntegerType>import java.math.BigInteger;\r\n</#if>\r\n<#-- Imports needed for foreign key query fields -->\r\n<#if tableInfo.foreignKeys?? && tableInfo.foreignKeys?has_content>\r\nimport java.util.List;\r\nimport java.math.BigInteger;\r\n</#if>\r\n\r\n/**\r\n* ${className}查询对象\r\n*/\r\npublic class ${className}ConsoleQuery {\r\n<#list columnInfos as column>\r\n    <#if isQueryableField(column)>\r\n        <#if column.type?contains(\"varchar\") || column.type?contains(\"char\")>\r\n    private ${getJavaType(column)} ${toCamelCase(column.fieldName, false)};\r\n\r\n        <#elseif column.type?contains(\"int\") || isEnumField(column)>\r\n    private ${getJavaType(column)} ${toCamelCase(column.fieldName, false)};\r\n        <#elseif column.type?contains(\"date\") || column.type?contains(\"time\")>\r\n\r\n    private ${getJavaType(column)} ${toCamelCase(column.fieldName, false)}Start;\r\n\r\n    private ${getJavaType(column)} ${toCamelCase(column.fieldName, false)}End;\r\n        </#if>\r\n    </#if>\r\n</#list>\r\n\r\n<#-- 关联查询字段 -->\r\n<#if tableInfo.foreignKeys?? && tableInfo.foreignKeys?has_content>\r\n<#list tableInfo.foreignKeys as fk>\r\n@RelationalQueryProperty(name = \"${fk.columnName}.id\", type = Part.Type.IN)\r\nprivate List&lt;BigInteger> ${toCamelCase(fk.referencedTable, false)}Ids;\r\n    </#list>\r\n</#if>\r\n\r\n    <#-- Getter和Setter方法 -->\r\n    <#list columnInfos as column>\r\n        <#if isQueryableField(column)>\r\n            <#if column.type?contains(\"varchar\") || column.type?contains(\"char\") || column.type?contains(\"int\") || isEnumField(column)>\r\n    public ${getJavaType(column)} get${toCamelCase(column.fieldName, true)}() {\r\n        return this.${toCamelCase(column.fieldName, false)};\r\n    }\r\n\r\n    public void set${toCamelCase(column.fieldName, true)}(${getJavaType(column)} ${toCamelCase(column.fieldName, false)}) {\r\n        this.${toCamelCase(column.fieldName, false)} = ${toCamelCase(column.fieldName, false)};\r\n    }\r\n            <#elseif column.type?contains(\"date\") || column.type?contains(\"time\")>\r\n    public ${getJavaType(column)} get${toCamelCase(column.fieldName, true)}Start() {\r\n        return this.${toCamelCase(column.fieldName, false)}Start;\r\n    }\r\n\r\n    public void set${toCamelCase(column.fieldName, true)}Start(${getJavaType(column)} ${toCamelCase(column.fieldName, false)}Start) {\r\n        this.${toCamelCase(column.fieldName, false)}Start = ${toCamelCase(column.fieldName, false)}Start;\r\n    }\r\n\r\n    public ${getJavaType(column)} get${toCamelCase(column.fieldName, true)}End() {\r\n        return this.${toCamelCase(column.fieldName, false)}End;\r\n    }\r\n\r\n    public void set${toCamelCase(column.fieldName, true)}End(${getJavaType(column)} ${toCamelCase(column.fieldName, false)}End) {\r\n        this.${toCamelCase(column.fieldName, false)}End = ${toCamelCase(column.fieldName, false)}End;\r\n    }\r\n            </#if>\r\n        </#if>\r\n    </#list>\r\n\r\n    <#-- 关联查询字段的Getter和Setter -->\r\n    <#if tableInfo.foreignKeys?? && tableInfo.foreignKeys?has_content>\r\n    <#list tableInfo.foreignKeys as fk>\r\n    public List&lt;BigInteger> get${toCamelCase(fk.referencedTable, true)}Ids() {\r\n        return this.${toCamelCase(fk.referencedTable, false)}Ids;\r\n    }\r\n\r\n    public void set${toCamelCase(fk.referencedTable, true)}Ids(List&lt;BigInteger> ${toCamelCase(fk.referencedTable, false)}Ids) {\r\n        this.${toCamelCase(fk.referencedTable, false)}Ids = ${toCamelCase(fk.referencedTable, false)}Ids;\r\n    }\r\n            </#list>\r\n    </#if>\r\n}', NULL, 'SYSTEM', '2025-12-29 00:55:36.453', 'SYSTEM', '2025-12-28 16:55:36.450');
INSERT INTO `code_file_template` VALUES (7, 4, 'consoleResource.ftl', 7, 'package ${packageName};\r\n\r\nimport org.springframework.data.domain.Page;\r\nimport org.springframework.data.domain.Pageable;\r\nimport org.springframework.web.bind.annotation.RequestBody;\r\nimport org.springframework.web.bind.annotation.RequestMapping;\r\nimport org.springframework.web.bind.annotation.PathVariable;\r\nimport org.springframework.web.bind.annotation.DeleteMapping;\r\nimport org.springframework.web.bind.annotation.GetMapping;\r\nimport org.springframework.web.bind.annotation.RestController;\r\n\r\nimport ${basePackage}.api.assembler.${className}CommandMapper;\r\nimport ${basePackage}.api.assembler.${className}QueryMapper;\r\nimport ${basePackage}.dto.${className}ConsoleCommand;\r\nimport ${basePackage}.dto.${className}ConsoleQuery;\r\nimport ${basePackage}.vo.${className}ConsoleView;\r\nimport com.old.silence.core.exception.ResourceNotFoundException;\r\nimport ${serviceApiPackage}.api.${className}Client;\r\nimport com.old.silence.web.bind.annotation.PostJsonMapping;\r\nimport com.old.silence.web.bind.annotation.PutJsonMapping;\r\n\r\nimport java.math.BigInteger;\r\n\r\n/**\r\n* ${className}资源控制器\r\n*/\r\n@RestController\r\n@RequestMapping(\"/api/v1\")\r\npublic class ${className}Resource {\r\n    private final ${className}Client ${className?uncap_first}Client;\r\n    private final ${className}CommandMapper ${className?uncap_first}CommandMapper;\r\n    private final ${className}QueryMapper ${className?uncap_first}QueryMapper;\r\n\r\n    public ${className}Resource(${className}Client ${className?uncap_first}Client,\r\n                                ${className}CommandMapper ${className?uncap_first}CommandMapper,\r\n                                ${className}QueryMapper ${className?uncap_first}QueryMapper) {\r\n        this.${className?uncap_first}Client = ${className?uncap_first}Client;\r\n        this.${className?uncap_first}CommandMapper = ${className?uncap_first}CommandMapper;\r\n        this.${className?uncap_first}QueryMapper = ${className?uncap_first}QueryMapper;\r\n    }\r\n\r\n    @GetMapping(value = \"/${apiName}/{id}\")\r\n    public ${className}ConsoleView findById(@PathVariable BigInteger id) {\r\n        return ${className?uncap_first}Client.findById(id, ${className}ConsoleView.class)\r\n                .orElseThrow(ResourceNotFoundException::new);\r\n    }\r\n\r\n    @GetMapping(value = \"/${apiName}\", params = {\"pageNo\", \"pageSize\"})\r\n    public Page<${className}ConsoleView> query(${className}ConsoleQuery query, Pageable pageable) {\r\n        var ${className}Query = ${className?uncap_first}QueryMapper.convert(query);\r\n        return ${className?uncap_first}Client.query(${className}Query, pageable, ${className}ConsoleView.class);\r\n    }\r\n\r\n    @PostJsonMapping(\"/${apiName}\")\r\n    public BigInteger create(@RequestBody ${className}ConsoleCommand command) {\r\n        var ${className?uncap_first}Command = ${className?uncap_first}CommandMapper.convert(command);\r\n        return ${className?uncap_first}Client.create(${className?uncap_first}Command);\r\n    }\r\n\r\n    @PutJsonMapping(value = \"/${apiName}/{id}\")\r\n    public void update(@PathVariable BigInteger id, @RequestBody ${className}ConsoleCommand command) {\r\n        var ${className?uncap_first}Command = ${className?uncap_first}CommandMapper.convert(command);\r\n        ${className?uncap_first}Client.update(id, ${className?uncap_first}Command);\r\n    }\r\n\r\n    @DeleteMapping(\"/${apiName}/{id}\")\r\n    public void deleteById(@PathVariable BigInteger id) {\r\n        ${className?uncap_first}Client.deleteById(id);\r\n    }\r\n}', NULL, 'SYSTEM', '2025-12-29 00:40:56.248', 'SYSTEM', '2025-12-28 16:40:56.236');
INSERT INTO `code_file_template` VALUES (8, 4, 'consoleView.ftl', 8, 'package ${packageName};\r\n\r\nimport com.old.silence.data.commons.domain.AuditableView;\r\n\r\n<#if enumSet?has_content>\r\n    <#list enumSet as enumName>\r\nimport com.old.silence.content.domain.enums.${enumName};\r\n    </#list>\r\n</#if>\r\n\r\n<#if hasInstantType>import java.time.Instant;\r\n</#if>\r\n<#if hasBigDecimalType>import java.math.BigDecimal;\r\n</#if>\r\n<#if hasBigIntegerType>import java.math.BigInteger;\r\n</#if>\r\n<#-- Imports for relation views when foreign keys exist -->\r\n<#if tableInfo.foreignKeys?? && tableInfo.foreignKeys?has_content>\r\nimport java.util.List;\r\n</#if>\r\n\r\n/**\r\n* ${className}视图接口\r\n*/\r\npublic interface ${className}ConsoleView extends AuditableView {\r\n    BigInteger getId();\r\n\r\n<#list columnInfos as column>\r\n<#if !column.primaryKey>\r\n    ${getJavaType(column)} get${toCamelCase(column.fieldName, true)}();\r\n</#if>\r\n</#list>\r\n\r\n<#-- 关联视图 -->\r\n<#if tableInfo.foreignKeys?? && tableInfo.foreignKeys?has_content>\r\n<#list tableInfo.foreignKeys as fk>\r\n    <#assign refViewName = toCamelCase(fk.referencedTable, true) + \"ConsoleView\">\r\n    List<${refViewName}> get${toCamelCase(fk.referencedTable, true)}List();\r\n</#list>\r\n</#if>\r\n}', '2', 'SYSTEM', '2025-12-29 00:56:31.276', 'SYSTEM', '2025-12-28 16:56:31.275');
INSERT INTO `code_file_template` VALUES (9, 3, 'dao.ftl', 9, 'package ${packageName};\r\n\r\nimport ${basePackage}.domain.model.${className};\r\nimport com.old.silence.data.jdbc.repository.JdbcRepository;\r\n\r\n<#if hasBigIntegerType>import java.math.BigInteger;\r\n</#if>\r\n\r\n/**\r\n* ${className}数据访问接口\r\n*/\r\npublic interface ${className}Dao extends JdbcRepository<${className}, ${primaryKeyType}> {\r\n\r\n}', NULL, 'SYSTEM', '2025-12-09 15:00:39.695', 'SYSTEM', '2025-12-09 15:00:39.695');
INSERT INTO `code_file_template` VALUES (10, 1, 'enum.ftl', 10, 'package ${basePackage};\r\n\r\nimport com.old.silence.core.enums.DescribedEnumValue;\r\n\r\n/**\r\n* @author ${authorName!\"作者\"}\r\n*/\r\npublic enum ${enumName} implements DescribedEnumValue<Byte> {\r\n    SINGLE_CHOICE(1, \"单选\"),\r\n    MULTI_CHOICE(2, \"多选\"),\r\n    FILL_BLANK(3, \"填空\"),\r\n    ORDER_SORT(4, \"排序\"),\r\n    TRUE_FALSE(5, \"判断\"),\r\n    ;\r\n\r\n    private final Byte value;\r\n    private final String description;\r\n\r\n    ${enumName}(int value, String description) {\r\n        this.value = (byte)value;\r\n        this.description = description;\r\n    }\r\n\r\n    public Byte getValue() {\r\n        return value;\r\n    }\r\n\r\n    public String getDescription() {\r\n        return description;\r\n    }\r\n}', NULL, 'SYSTEM', '2025-12-27 22:03:04.855', 'SYSTEM', '2025-12-27 14:03:04.848');
INSERT INTO `code_file_template` VALUES (11, 3, 'mapper.ftl', 11, 'package ${packageName};\r\n\r\nimport org.mapstruct.Mapper;\r\nimport org.springframework.core.convert.converter.Converter;\r\n\r\nimport com.old.silence.content.api.config.SilenceMapStructSpringConfig;\r\nimport ${basePackage}.api.dto.${className}Command;\r\nimport ${basePackage}.domain.model.${className};\r\n\r\n/**\r\n* ${className}映射器\r\n*/\r\n@Mapper(uses = SilenceMapStructSpringConfig.class)\r\npublic interface ${className}Mapper extends Converter<${className}Command, ${className}>{\r\n\r\n    @Override\r\n    ${className} convert(${className}Command command);\r\n}', NULL, 'SYSTEM', '2025-12-09 15:01:32.884', 'SYSTEM', '2025-12-09 15:01:32.884');
INSERT INTO `code_file_template` VALUES (12, 3, 'model.ftl', 12, 'package ${packageName};\r\n\r\nimport jakarta.persistence.Column;\r\nimport jakarta.persistence.Entity;\r\n<#if hasMapType>\r\nimport jakarta.persistence.Convert;\r\n</#if>\r\nimport com.old.silence.data.commons.domain.AbstractAutoGeneratedIdAuditable;\r\n<#if hasMapType>\r\nimport com.old.silence.data.jdbc.core.converter.MapAttributeConverter;\r\n</#if>\r\n\r\n<#if enumSet?has_content>\r\n    <#list enumSet as enumName>\r\nimport com.old.silence.content.domain.enums.${enumName};\r\n    </#list>\r\n</#if>\r\n\r\n<#if hasInstantType>\r\nimport java.time.Instant;\r\n</#if>\r\n<#if hasBigDecimalType>\r\nimport java.math.BigDecimal;\r\n</#if>\r\n<#if hasBigIntegerType>\r\nimport java.math.BigInteger;\r\n</#if>\r\n<#if hasMapType>\r\nimport java.util.Map;\r\n</#if>\r\n\r\n/**\r\n * ${tableInfo.comment}实体类\r\n *\r\n * @author ${authorName!\"作者\"}\r\n */\r\n@Entity\r\npublic class ${className} extends AbstractAutoGeneratedIdAuditable<${primaryKeyType}> {\r\n    private static final long serialVersionUID = 1L;\r\n\r\n<#list columnInfos as column>\r\n    <#if !column.primaryKey>\r\n    /**\r\n     * ${column.comment}\r\n     */\r\n    <#if needsColumnAnnotation(column)>\r\n    @Column(name = \"${column.originalName}\")\r\n    </#if>\r\n    <#if isMapField(column)>\r\n    @Convert(converter = MapAttributeConverter.class)\r\n    </#if>\r\n    private ${getJavaType(column)} ${toCamelCase(column.fieldName, false)};\r\n\r\n    </#if>\r\n</#list>\r\n\r\n    <#-- 关联关系 -->\r\n    <#if tableInfo.foreignKeys?? && tableInfo.foreignKeys?has_content>\r\n    <#list tableInfo.foreignKeys as fk>\r\n        <#assign fieldName = toCamelCase(fk.referencedTable, false)>\r\n        <#assign refClassName = toCamelCase(fk.referencedTable, true)>\r\n    @OneToMany(mappedBy = \"${toCamelCase(tableInfo.tableName, false)}\")\r\n    private List<${refClassName}> ${fieldName}List;\r\n    </#list>\r\n    </#if>\r\n\r\n    <#-- Getter和Setter方法 -->\r\n    <#list columnInfos as column>\r\n        <#if !column.primaryKey>\r\n    public ${getJavaType(column)} get${toCamelCase(column.fieldName, true)}() {\r\n        return this.${toCamelCase(column.fieldName, false)};\r\n    }\r\n\r\n    public void set${toCamelCase(column.fieldName, true)}(${getJavaType(column)} ${toCamelCase(column.fieldName, false)}) {\r\n        this.${toCamelCase(column.fieldName, false)} = ${toCamelCase(column.fieldName, false)};\r\n    }\r\n        </#if>\r\n    </#list>\r\n}', NULL, 'SYSTEM', '2025-12-29 00:55:04.193', 'SYSTEM', '2025-12-28 16:55:04.192');
INSERT INTO `code_file_template` VALUES (13, 3, 'query.ftl', 13, 'package ${packageName};\r\n\r\n<#if enumSet?has_content>\r\n    <#list enumSet as enumName>\r\nimport com.old.silence.content.domain.enums.${enumName};\r\n    </#list>\r\n</#if>\r\nimport org.springframework.data.repository.query.parser.Part;\r\nimport com.old.silence.data.commons.annotation.RelationalQueryProperty;\r\n\r\n<#if hasInstantType>import java.time.Instant;\r\n</#if>\r\n<#if hasBigDecimalType>import java.math.BigDecimal;\r\n</#if>\r\n<#if hasBigIntegerType>import java.math.BigInteger;\r\n</#if>\r\n<#-- Imports needed for foreign key query fields -->\r\n<#if tableInfo.foreignKeys?? && tableInfo.foreignKeys?has_content>\r\nimport java.util.List;\r\nimport java.math.BigInteger;\r\n</#if>\r\n\r\n/**\r\n* ${className}查询对象\r\n*/\r\npublic class ${className}Query {\r\n<#list columnInfos as column>\r\n    <#if isQueryableField(column)>\r\n        <#if column.type?contains(\"varchar\") || column.type?contains(\"char\")>\r\n    @RelationalQueryProperty(type = Part.Type.STARTING_WITH)\r\n    private ${getJavaType(column)} ${toCamelCase(column.fieldName, false)};\r\n        <#elseif column.type?contains(\"int\") || isEnumField(column)>\r\n    @RelationalQueryProperty(type = Part.Type.SIMPLE_PROPERTY)\r\n    private ${getJavaType(column)} ${toCamelCase(column.fieldName, false)};\r\n        <#elseif column.type?contains(\"date\") || column.type?contains(\"time\")>\r\n    @RelationalQueryProperty(name = \"${column.fieldName}\", type = Part.Type.GREATER_THAN_EQUAL)\r\n    private ${getJavaType(column)} ${toCamelCase(column.fieldName, false)}Start;\r\n\r\n    @RelationalQueryProperty(name = \"${column.fieldName}\", type = Part.Type.LESS_THAN_EQUAL)\r\n    private ${getJavaType(column)} ${toCamelCase(column.fieldName, false)}End;\r\n        </#if>\r\n    </#if>\r\n</#list>\r\n\r\n<#-- 关联查询字段 -->\r\n<#if tableInfo.foreignKeys?? && tableInfo.foreignKeys?has_content>\r\n<#list tableInfo.foreignKeys as fk>\r\n@RelationalQueryProperty(name = \"${fk.columnName}.id\", type = Part.Type.IN)\r\nprivate List&lt;BigInteger> ${toCamelCase(fk.referencedTable, false)}Ids;\r\n    </#list>\r\n</#if>\r\n\r\n    <#-- Getter和Setter方法 -->\r\n    <#list columnInfos as column>\r\n        <#if isQueryableField(column)>\r\n            <#if column.type?contains(\"varchar\") || column.type?contains(\"char\") || column.type?contains(\"int\") || isEnumField(column)>\r\n    public ${getJavaType(column)} get${toCamelCase(column.fieldName, true)}() {\r\n        return this.${toCamelCase(column.fieldName, false)};\r\n    }\r\n\r\n    public void set${toCamelCase(column.fieldName, true)}(${getJavaType(column)} ${toCamelCase(column.fieldName, false)}) {\r\n        this.${toCamelCase(column.fieldName, false)} = ${toCamelCase(column.fieldName, false)};\r\n    }\r\n            <#elseif column.type?contains(\"date\") || column.type?contains(\"time\")>\r\n    public ${getJavaType(column)} get${toCamelCase(column.fieldName, true)}Start() {\r\n        return this.${toCamelCase(column.fieldName, false)}Start;\r\n    }\r\n\r\n    public void set${toCamelCase(column.fieldName, true)}Start(${getJavaType(column)} ${toCamelCase(column.fieldName, false)}Start) {\r\n        this.${toCamelCase(column.fieldName, false)}Start = ${toCamelCase(column.fieldName, false)}Start;\r\n    }\r\n\r\n    public ${getJavaType(column)} get${toCamelCase(column.fieldName, true)}End() {\r\n        return this.${toCamelCase(column.fieldName, false)}End;\r\n    }\r\n\r\n    public void set${toCamelCase(column.fieldName, true)}End(${getJavaType(column)} ${toCamelCase(column.fieldName, false)}End) {\r\n        this.${toCamelCase(column.fieldName, false)}End = ${toCamelCase(column.fieldName, false)}End;\r\n    }\r\n            </#if>\r\n        </#if>\r\n    </#list>\r\n\r\n    <#-- 关联查询字段的Getter和Setter -->\r\n    <#if tableInfo.foreignKeys?? && tableInfo.foreignKeys?has_content>\r\n    <#list tableInfo.foreignKeys as fk>\r\n    public List&lt;BigInteger> get${toCamelCase(fk.referencedTable, true)}Ids() {\r\n        return this.${toCamelCase(fk.referencedTable, false)}Ids;\r\n    }\r\n\r\n    public void set${toCamelCase(fk.referencedTable, true)}Ids(List&lt;BigInteger> ${toCamelCase(fk.referencedTable, false)}Ids) {\r\n        this.${toCamelCase(fk.referencedTable, false)}Ids = ${toCamelCase(fk.referencedTable, false)}Ids;\r\n    }\r\n            </#list>\r\n    </#if>\r\n}', '2', 'SYSTEM', '2025-12-29 00:56:01.703', 'SYSTEM', '2025-12-28 16:56:01.701');
INSERT INTO `code_file_template` VALUES (14, 3, 'queryMapper.ftl', 14, 'package ${packageName};\r\n\r\nimport org.mapstruct.Mapper;\r\nimport org.springframework.core.convert.converter.Converter;\r\n\r\nimport com.old.silence.content.console.api.config.SilenceMapStructSpringConfig;\r\nimport ${basePackage}.dto.${className}ConsoleQuery;\r\nimport ${serviceApiPackage}.api.dto.${className}Query;\r\n\r\n/**\r\n* ${className}Command映射器\r\n*/\r\n@Mapper(uses = SilenceMapStructSpringConfig.class)\r\npublic interface ${className}QueryMapper extends Converter<${className}ConsoleQuery, ${className}Query>{\r\n\r\n    @Override\r\n    ${className}Query convert(${className}ConsoleQuery query);\r\n}', NULL, 'SYSTEM', '2025-12-29 01:00:19.203', 'SYSTEM', '2025-12-28 17:00:19.201');
INSERT INTO `code_file_template` VALUES (15, 3, 'repository.ftl', 15, 'package ${packageName};\r\n\r\nimport org.springframework.data.domain.Page;\r\nimport org.springframework.data.domain.Pageable;\r\nimport org.springframework.data.relational.core.query.Criteria;\r\n\r\nimport ${basePackage}.domain.model.${className};\r\n\r\nimport java.math.BigInteger;\r\nimport java.util.Optional;\r\n\r\n/**\r\n* ${className}仓储接口\r\n*/\r\npublic interface ${className}Repository {\r\n\r\n    <T> Optional<T> findById(BigInteger id, Class<T> projectionType);\r\n\r\n    <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType);\r\n\r\n    int create(${className} ${className?uncap_first});\r\n\r\n    int update(${className} ${className?uncap_first});\r\n\r\n    int deleteById(BigInteger id);\r\n}', NULL, 'SYSTEM', '2025-12-09 15:04:32.592', 'SYSTEM', '2025-12-09 15:04:32.592');
INSERT INTO `code_file_template` VALUES (16, 3, 'repository-impl.ftl', 16, 'package ${packageName};\r\n\r\nimport org.springframework.data.domain.Page;\r\nimport org.springframework.data.domain.Pageable;\r\nimport org.springframework.data.relational.core.query.Criteria;\r\nimport org.springframework.stereotype.Repository;\r\n\r\nimport ${basePackage}.domain.model.${className};\r\nimport ${basePackage}.domain.repository.${className}Repository;\r\nimport ${basePackage}.infrastructure.persistence.dao.${className}Dao;\r\n\r\nimport java.math.BigInteger;\r\nimport java.util.Optional;\r\n\r\n/**\r\n* ${className}仓储实现\r\n*/\r\n@Repository\r\npublic class ${className}MyBatisRepository implements ${className}Repository {\r\n    private final ${className}Dao ${className?uncap_first}Dao;\r\n\r\n    public ${className}MyBatisRepository(${className}Dao ${className?uncap_first}Dao) {\r\n        this.${className?uncap_first}Dao = ${className?uncap_first}Dao;\r\n    }\r\n\r\n    @Override\r\n    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {\r\n        return ${className?uncap_first}Dao.findById(id, projectionType);\r\n    }\r\n\r\n    @Override\r\n    public <T> Page<T> findByCriteria(Criteria criteria, Pageable pageable, Class<T> projectionType) {\r\n        return ${className?uncap_first}Dao.findByCriteria(criteria, pageable, projectionType);\r\n    }\r\n\r\n    @Override\r\n    public int create(${className} ${className?uncap_first}) {\r\n        return ${className?uncap_first}Dao.insert(${className?uncap_first});\r\n    }\r\n\r\n    @Override\r\n    public int update(${className} ${className?uncap_first}) {\r\n        return ${className?uncap_first}Dao.update(${className?uncap_first});\r\n    }\r\n\r\n    @Override\r\n    public int deleteById(BigInteger id) {\r\n        return ${className?uncap_first}Dao.deleteById(id);\r\n    }\r\n}', NULL, 'SYSTEM', '2025-12-09 15:05:08.275', 'SYSTEM', '2025-12-09 15:05:08.275');
INSERT INTO `code_file_template` VALUES (17, 3, 'resource.ftl', 17, 'package ${packageName};\r\n\r\nimport org.springframework.data.domain.Page;\r\nimport org.springframework.data.domain.Pageable;\r\nimport org.springframework.web.bind.annotation.RequestMapping;\r\nimport org.springframework.web.bind.annotation.RestController;\r\n\r\nimport ${basePackage}.api.assembler.${className}Mapper;\r\nimport ${basePackage}.api.dto.${className}Command;\r\nimport ${basePackage}.api.dto.${className}Query;\r\nimport ${basePackage}.domain.model.${className};\r\nimport ${basePackage}.domain.repository.${className}Repository;\r\nimport com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;\r\n\r\nimport java.math.BigInteger;\r\nimport java.util.Optional;\r\n\r\nimport static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;\r\n\r\n/**\r\n* ${className}资源控制器\r\n*/\r\n@RestController\r\n@RequestMapping(\"/api/v1\")\r\npublic class ${className}Resource implements ${className}Service {\r\n    private final ${className}Repository ${className?uncap_first}Repository;\r\n    private final ${className}Mapper ${className?uncap_first}Mapper;\r\n\r\n    public ${className}Resource(${className}Repository ${className?uncap_first}Repository,\r\n                                ${className}Mapper ${className?uncap_first}Mapper) {\r\n        this.${className?uncap_first}Repository = ${className?uncap_first}Repository;\r\n        this.${className?uncap_first}Mapper = ${className?uncap_first}Mapper;\r\n    }\r\n\r\n    @Override\r\n    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {\r\n        return ${className?uncap_first}Repository.findById(id, projectionType);\r\n    }\r\n\r\n    @Override\r\n    public <T> Page<T> query(${className}Query query, Pageable pageable, Class<T> projectionType) {\r\n        var criteria = QueryCriteriaConverter.convert(query, ${className}.class);\r\n        return ${className?uncap_first}Repository.findByCriteria(criteria, pageable, projectionType);\r\n    }\r\n\r\n    @Override\r\n    public BigInteger create(${className}Command command) {\r\n        var ${className?uncap_first} = ${className?uncap_first}Mapper.convert(command);\r\n        ${className?uncap_first}Repository.create(${className?uncap_first});\r\n                        return ${className?uncap_first}.getId();\r\n                        }\r\n\r\n    @Override\r\n    public void update(BigInteger id, ${className}Command command) {\r\n        var ${className?uncap_first} = ${className?uncap_first}Mapper.convert(command);\r\n        ${className?uncap_first}.setId(id);\r\n        validateModifyingResult(${className?uncap_first}Repository.update(${className?uncap_first}));\r\n    }\r\n\r\n    @Override\r\n    public void deleteById(BigInteger id) {\r\n        validateModifyingResult(${className?uncap_first}Repository.deleteById(id));\r\n    }\r\n}', NULL, 'SYSTEM', '2025-12-09 15:05:35.730', 'SYSTEM', '2025-12-09 15:05:35.730');
INSERT INTO `code_file_template` VALUES (18, 2, 'service.ftl', 18, 'package ${packageName};\r\n\r\nimport org.springframework.cloud.openfeign.SpringQueryMap;\r\nimport org.springframework.data.domain.Page;\r\nimport org.springframework.data.domain.Pageable;\r\nimport org.springframework.validation.annotation.Validated;\r\nimport org.springframework.web.bind.annotation.DeleteMapping;\r\nimport org.springframework.web.bind.annotation.GetMapping;\r\nimport org.springframework.web.bind.annotation.PathVariable;\r\nimport org.springframework.web.bind.annotation.RequestBody;\r\n\r\nimport ${basePackage}.api.dto.${className}Command;\r\nimport ${basePackage}.api.dto.${className}Query;\r\nimport ${basePackage}.api.vo.${className}View;\r\nimport com.old.silence.web.bind.annotation.PostJsonMapping;\r\nimport com.old.silence.web.bind.annotation.PutJsonMapping;\r\nimport com.old.silence.web.data.ProjectedPayloadType;\r\n\r\nimport java.math.BigInteger;\r\nimport java.util.Optional;\r\n\r\n/**\r\n* ${className}服务接口\r\n*/\r\ninterface ${className}Service {\r\n\r\n    @GetMapping(value = \"/${apiName}/{id}\")\r\n    <T> Optional<T> findById(@PathVariable BigInteger id, @ProjectedPayloadType(${className}View.class) Class<T> projectionType);\r\n\r\n    @GetMapping(value = \"/${apiName}\", params = {\"pageNo\", \"pageSize\"})\r\n    <T> Page<T> query(@Validated @SpringQueryMap ${className}Query query, Pageable pageable,\r\n                        @ProjectedPayloadType(${className}View.class) Class<T> projectionType);\r\n\r\n    @PostJsonMapping(\"/${apiName}\")\r\n    BigInteger create(@RequestBody @Validated ${className}Command command);\r\n\r\n    @PutJsonMapping(value = \"/${apiName}/{id}\")\r\n    void update(@PathVariable BigInteger id, @RequestBody @Validated ${className}Command command);\r\n\r\n    @DeleteMapping(\"/${apiName}/{id}\")\r\n    void deleteById(@PathVariable BigInteger id);\r\n}', NULL, 'SYSTEM', '2025-12-09 15:05:59.352', 'SYSTEM', '2025-12-09 15:05:59.352');
INSERT INTO `code_file_template` VALUES (19, 5, 'sql.ftl', 19, 'INSERT INTO `silence-hall`.`menu` ( `parent_id`, `name`, `module_type`, `type`, `path`, `component`, `redirect`, `meta`, `sort`, `status`, `is_deleted`, `created_by`, `created_date`, `updated_by`, `updated_date`) \r\nVALUES ( 87, \'标签管理\', \'CONTENT\', 2, \'/tag\', \'Tag\', \'\', \'{\\\"title\\\":\\\"标签管理\\\",\\\"icon\\\":\\\"EyeFilled\\\",\\\"show\\\":true,\\\"requiresAuth\\\":true}\', 7, b\'1\', b\'0\', \'admin\', \'2025-12-03 21:43:15\', \'admin\', \'2025-12-03 22:32:22\');\r\n', NULL, 'SYSTEM', '2025-12-09 15:07:52.656', 'SYSTEM', '2025-12-09 15:07:52.656');
INSERT INTO `code_file_template` VALUES (20, 5, 'type.ts.ftl', 20, '<#-- TypeScript 类型定义模板 -->\r\n// ${typeName} 实体类型\r\nexport interface ${typeName} {\r\n<#list fields as f>\r\n  ${f.name}<#if f.optional?? && f.optional>? </#if>: ${f.type!\'any\'}\r\n</#list>\r\n}\r\n\r\n// ${typeName} 查询参数类型\r\nexport interface ${queryParamsName} {\r\n  pageNo: number\r\n  pageSize: number\r\n<#list queryParams?default([]) as p>\r\n  ${p.name}?: ${p.type}\r\n</#list>\r\n}\r\n\r\n// 创建${typeName}参数类型\r\nexport interface ${createParamsName} {\r\n<#list createFields?default(fields) as f>\r\n  ${f.name}: ${f.type!\'any\'}\r\n</#list>\r\n}\r\n\r\n// 更新${typeName}参数类型\r\nexport type ${updateParamsName} = Partial<${createParamsName}>', NULL, 'SYSTEM', '2025-12-09 15:08:40.456', 'SYSTEM', '2025-12-09 15:08:40.456');
INSERT INTO `code_file_template` VALUES (21, 3, 'view.ftl', 21, 'package ${packageName};\r\n\r\nimport org.springframework.data.web.ProjectedPayload;\r\nimport com.old.silence.data.commons.domain.AuditableView;\r\n\r\n<#if enumSet?has_content>\r\n    <#list enumSet as enumName>\r\nimport com.old.silence.content.domain.enums.${enumName};\r\n    </#list>\r\n</#if>\r\n<#if hasInstantType>import java.time.Instant;\r\n</#if>\r\n<#if hasBigDecimalType>import java.math.BigDecimal;\r\n</#if>\r\n<#if hasBigIntegerType>\r\nimport java.math.BigInteger;\r\n</#if>\r\n<#-- Imports for relation views when foreign keys exist -->\r\n<#if tableInfo.foreignKeys?? && tableInfo.foreignKeys?has_content>\r\nimport java.util.List;\r\n</#if>\r\n\r\n/**\r\n* ${className}视图接口\r\n*/\r\n@ProjectedPayload\r\npublic interface ${className}View extends AuditableView {\r\n    BigInteger getId();\r\n\r\n<#list columnInfos as column>\r\n<#if !column.primaryKey>\r\n    ${getJavaType(column)} get${toCamelCase(column.fieldName, true)}();\r\n\r\n</#if>\r\n</#list>\r\n\r\n<#-- 关联视图 -->\r\n<#if tableInfo.foreignKeys?? && tableInfo.foreignKeys?has_content>\r\n<#list tableInfo.foreignKeys as fk>\r\n    <#assign refViewName = toCamelCase(fk.referencedTable, true) + \"View\">\r\n    List<${refViewName}> get${toCamelCase(fk.referencedTable, true)}List();\r\n\r\n</#list>\r\n</#if>\r\n}', '2', 'SYSTEM', '2025-12-29 01:06:48.538', 'SYSTEM', '2025-12-28 17:06:48.536');
INSERT INTO `code_file_template` VALUES (22, 5, 'vue.ftl', 22, '<#-- Vue 页面模板（SFC） -->\r\n<!-- ${entityLabel}管理页面 -->\r\n<template>\r\n  <div class=\"${camelName}-page\">\r\n    <div class=\"${camelName}-card\">\r\n      <div class=\"card-header\">\r\n        <h2 class=\"card-title\">${entityLabel}管理</h2>\r\n      </div>\r\n\r\n      <SearchPanel\r\n        :model-value=\"searchParams\"\r\n        :fields=\"searchFields\"\r\n        @search=\"handleSearch\"\r\n        @reset=\"resetSearch\"\r\n        @update:model-value=\"handleSearchFormUpdate\"\r\n      />\r\n\r\n      <div class=\"table-toolbar\">\r\n        <a-button type=\"primary\" class=\"action-button\" @click=\"showCreateModal = true\">\r\n          <plus-outlined />\r\n          新增${entityLabel}\r\n        </a-button>\r\n        <a-button class=\"action-button\" @click=\"fetchItems\">刷新</a-button>\r\n        <ColumnSettings :columns=\"allColumns\" v-model=\"checkedKeys\" @update:columns=\"val => allColumns = val as any\" />\r\n      </div>\r\n\r\n      <div class=\"table-container\">\r\n        <CommonPagination\r\n          :columns=\"displayColumns\"\r\n          :dataSource=\"items\"\r\n          :loading=\"loading\"\r\n          rowKey=\"id\"\r\n          v-model:pageNo=\"pagination.current\"\r\n          v-model:pageSize=\"pagination.pageSize\"\r\n          :total=\"pagination.total\"\r\n          @change=\"handlePaginationChange\"\r\n          @cell-click=\"handleCellClick\"\r\n        >\r\n          <template #bodyCell=\"{ column, record }\">\r\n<#list columns as col>\r\n  <#-- 查找字段类型是否为布尔类型 -->\r\n  <#assign fieldType = \'\'>\r\n  <#list fields as f>\r\n    <#if f.name == col.key>\r\n      <#assign fieldType = f.type?lower_case>\r\n    </#if>\r\n  </#list>\r\n  <#if fieldType == \'boolean\' || fieldType == \'bool\'>\r\n            <template v-if=\"column.key === \'${col.key}\'\">\r\n              <a-tag :color=\"record.${col.key} ? \'success\' : \'error\'\">\r\n                {{ record.${col.key} ? \'是\' : \'否\' }}\r\n              </a-tag>\r\n            </template>\r\n  </#if>\r\n</#list>\r\n            <template v-else-if=\"column.key === \'action\'\">\r\n              <a-space>\r\n                <a-button type=\"link\" size=\"small\" @click=\"handleEdit(record)\">编辑</a-button>\r\n                <a-popconfirm title=\"确定要删除这个${entityLabel}吗？\" @confirm=\"handleDelete(record.id)\">\r\n                  <a-button type=\"link\" size=\"small\" danger>删除</a-button>\r\n                </a-popconfirm>\r\n              </a-space>\r\n            </template>\r\n          </template>\r\n        </CommonPagination>\r\n      </div>\r\n    </div>\r\n\r\n    <DetailDrawer v-model:visible=\"drawerVisible\" :record=\"selectedRecord\" :columns=\"detailColumns\" title=\"${entityLabel}详情\" />\r\n\r\n    <a-modal v-model:open=\"showCreateModal\" :title=\"modalTitle\" ok-text=\"确定\" cancel-text=\"取消\" @ok=\"handleModalOk\" :confirmLoading=\"createLoading\" @cancel=\"handleModalCancel\">\r\n      <a-form :model=\"formData\" :rules=\"rules\" ref=\"formRef\" :label-col=\"{ span: 6 }\" :wrapper-col=\"{ span: 16 }\">\r\n<#list formFields?default(fields) as f>\r\n        <a-form-item label=\"${f.label! f.name}\" name=\"${f.name}\">\r\n  <#if f.type?lower_case == \'boolean\' || f.type?lower_case == \'bool\'>\r\n          <a-switch v-model:checked=\"formData.${f.name}\" />\r\n  <#elseif f.type?lower_case == \'number\'>\r\n          <a-input-number v-model:value=\"formData.${f.name}\" :min=\"0\" :precision=\"0\" style=\"width: 100%;\" placeholder=\"请输入${f.label! f.name}\" />\r\n  <#else>\r\n          <a-input v-model:value=\"formData.${f.name}\" placeholder=\"请输入${f.label! f.name}\" />\r\n  </#if>\r\n        </a-form-item>\r\n</#list>\r\n      </a-form>\r\n    </a-modal>\r\n  </div>\r\n</template>\r\n\r\n<script lang=\"ts\" setup>\r\nimport { ref, reactive, computed, onMounted } from \'vue\'\r\nimport { message } from \'ant-design-vue\'\r\nimport { PlusOutlined } from \'@ant-design/icons-vue\'\r\nimport type { FormInstance } from \'ant-design-vue\'\r\nimport CommonPagination from \'@/components/CommonPagination.vue\'\r\nimport SearchPanel from \'@/components/SearchPanel.vue\'\r\nimport ColumnSettings from \'@/components/ColumnSettings.vue\'\r\nimport DetailDrawer from \'@/components/DetailDrawer.vue\'\r\nimport {\r\n  get${typeName}List,\r\n  create${typeName},\r\n  update${typeName},\r\n  delete${typeName},\r\n  get${typeName}ById\r\n} from \'@/api/${camelName}\'\r\nimport type { ${typeName}, ${queryParamsName} } from \'@/types/${camelName}\'\r\nimport type { PaginationResponse } from \'@/types/common\'\r\n\r\nconst searchParams = reactive({\r\n<#list searchFields as sf>\r\n  ${sf.key}: <#if sf.paramType?lower_case == \'number\'>undefined as number | undefined<#elseif sf.type == \'date\'>undefined as [string, string] | undefined<#elseif sf.type == \'select\'>undefined as string | number | undefined<#else>\'\' as string</#if><#if sf_has_next>,</#if>\r\n</#list>\r\n})\r\n\r\nconst items = ref<${typeName}[]>([])\r\nconst loading = ref(false)\r\nconst pagination = reactive({ current: 1, pageSize: 10, total: 0 })\r\n\r\nconst allColumns = ref([\r\n<#list columns as col>\r\n  {\r\n    title: \'${col.title}\',\r\n    dataIndex: \'${col.dataIndex}\',\r\n    key: \'${col.key}\',\r\n    width: \'${col.width}\',\r\n    visible: true<#if col.clickable?? && col.clickable>, clickable: true</#if>\r\n  }<#if col_has_next>,</#if>\r\n</#list>\r\n])\r\n\r\nconst checkedKeys = ref(allColumns.value.filter(c => c.visible).map(c => c.key))\r\nconst displayColumns = computed(() => allColumns.value.filter(col => checkedKeys.value.includes(col.key)))\r\n\r\nconst drawerVisible = ref(false)\r\nconst selectedRecord = ref<${typeName} | null>(null)\r\n\r\nconst detailColumns = [\r\n<#list detailColumns as dc>\r\n  ${dc}<#if dc_has_next>,</#if>\r\n</#list>\r\n]\r\n\r\nconst showCreateModal = ref(false)\r\nconst createLoading = ref(false)\r\nconst modalTitle = ref(\'新增${entityLabel}\')\r\nconst formRef = ref<FormInstance>()\r\nconst formData = reactive({\r\n  id: undefined as number | undefined,\r\n<#list formFields?default(fields) as f>\r\n  ${f.name}: <#if f.type?lower_case == \'boolean\' || f.type?lower_case == \'bool\'>false<#elseif f.type?lower_case == \'number\'>${(f.name?lower_case?contains(\'order\') || f.name?lower_case?contains(\'sort\'))?string(\'1\',\'0\')}<#else>\'\'</#if><#if f_has_next>,</#if>\r\n</#list>\r\n})\r\n\r\nconst rules: Record<string, any> = {\r\n<#list requiredFields?default([]) as rf>\r\n  ${rf}: [{ required: true, message: \'请输入${rf}\', trigger: \'blur\' }]<#if rf_has_next>,</#if>\r\n</#list>\r\n}\r\n\r\nconst searchFields = [\r\n<#list searchFields as sf>\r\n  {\r\n    key: \'${sf.key}\',\r\n    label: \'${sf.label}\',\r\n    type: \'input\' as const,\r\n    placeholder: \'${sf.placeholder}\',\r\n    style: \'width: 200px\'\r\n  }<#if sf_has_next>,</#if>\r\n</#list>\r\n]\r\n\r\nconst fetchItems = async () => {\r\n  loading.value = true\r\n  try {\r\n    const params: ${queryParamsName} = { pageNo: pagination.current, pageSize: pagination.pageSize }\r\n<#list searchFields as sf>\r\n    <#if sf.key == \'id\'>\r\n    if (searchParams.id) { params.id = searchParams.id }\r\n    <#elseif sf.type == \'date\'>\r\n    if (searchParams.${sf.key} && Array.isArray(searchParams.${sf.key}) && searchParams.${sf.key}.length === 2) {\r\n      params.${sf.key}Start = searchParams.${sf.key}[0]\r\n      params.${sf.key}End = searchParams.${sf.key}[1]\r\n    }\r\n    <#else>\r\n    if (searchParams.${sf.key}) { params.${sf.key} = searchParams.${sf.key} }\r\n    </#if>\r\n</#list>\r\n    const response = await get${typeName}List(params) as unknown as PaginationResponse<${typeName}>\r\n    items.value = response.data || []\r\n    pagination.total = response.total || 0\r\n  } catch (error) {\r\n    console.error(\'获取列表失败:\', error)\r\n    message.error(\'获取列表失败\')\r\n    items.value = []\r\n  } finally {\r\n    loading.value = false\r\n  }\r\n}\r\n\r\nconst handlePaginationChange = (pageNo: number, pageSize: number) => {\r\n  pagination.current = pageNo\r\n  pagination.pageSize = pageSize\r\n  fetchItems()\r\n}\r\n\r\nconst handleSearch = () => { pagination.current = 1; fetchItems() }\r\n\r\nconst resetSearch = () => {\r\n<#list searchFields as sf>\r\n  <#if sf.paramType?lower_case == \'number\' || sf.type == \'date\' || sf.type == \'select\'>\r\n  searchParams.${sf.key} = undefined\r\n  <#else>\r\n  searchParams.${sf.key} = \'\'\r\n  </#if>\r\n</#list>\r\n  pagination.current = 1\r\n  fetchItems()\r\n}\r\n\r\nconst handleSearchFormUpdate = (newForm: any) => { Object.assign(searchParams, newForm) }\r\n\r\nconst handleCellClick = async (record: ${typeName}, column: any) => {\r\n  if (column.key === \'id\') {\r\n    try {\r\n      const data = await get${typeName}ById(record.id) as unknown as ${typeName}\r\n      selectedRecord.value = data\r\n      drawerVisible.value = true\r\n    } catch (error) {\r\n      console.error(\'获取详情失败:\', error)\r\n      message.error(\'获取详情失败\')\r\n    }\r\n  }\r\n}\r\n\r\nconst handleEdit = (record: ${typeName}) => {\r\n  modalTitle.value = \'编辑${entityLabel}\'\r\n  formData.id = record.id\r\n<#list formFields?default(fields) as f>\r\n  formData.${f.name} = record.${f.name}<#if f.type?lower_case == \'string\'> || \'\'</#if>\r\n</#list>\r\n  showCreateModal.value = true\r\n}\r\n\r\nconst handleDelete = async (id: number) => {\r\n  try {\r\n    await delete${typeName}(id)\r\n    message.success(\'删除成功\')\r\n    fetchItems()\r\n  } catch (error) {\r\n    console.error(\'删除失败:\', error)\r\n    message.error(\'删除失败\')\r\n  }\r\n}\r\n\r\nconst handleModalOk = async () => {\r\n  try {\r\n    await formRef.value?.validate()\r\n    createLoading.value = true\r\n    if (formData.id) {\r\n      await update${typeName}(formData.id, {\r\n<#list formFields?default(fields) as f>\r\n        ${f.name}: formData.${f.name}<#if f_has_next>,</#if>\r\n</#list>\r\n      })\r\n      message.success(\'修改成功\')\r\n    } else {\r\n      await create${typeName>({\r\n<#list formFields?default(fields) as f>\r\n        ${f.name}: formData.${f.name}<#if f_has_next>,</#if>\r\n</#list>\r\n      })\r\n      message.success(\'新增成功\')\r\n    }\r\n    showCreateModal.value = false\r\n    resetForm()\r\n    fetchItems()\r\n  } catch (error) {\r\n    console.error(\'保存失败:\', error)\r\n    message.error(formData.id ? \'修改失败\' : \'新增失败\')\r\n  } finally {\r\n    createLoading.value = false\r\n  }\r\n}\r\n\r\nconst handleModalCancel = () => { showCreateModal.value = false; resetForm() }\r\n\r\nconst resetForm = () => {\r\n  formData.id = undefined\r\n<#list formFields?default(fields) as f>\r\n  <#if f.type?lower_case == \'boolean\' || f.type?lower_case == \'bool\'>\r\n  formData.${f.name} = false\r\n  <#elseif f.type?lower_case == \'number\'>\r\n  formData.${f.name} = ${(f.name?lower_case?contains(\'order\') || f.name?lower_case?contains(\'sort\'))?string(\'1\',\'0\')}\r\n  <#else>\r\n  formData.${f.name} = \'\'\r\n  </#if>\r\n</#list>\r\n  formRef.value?.resetFields()\r\n}\r\n\r\nonMounted(() => { fetchItems() })\r\n</script>\r\n\r\n<style scoped>\r\n.${camelName}-page { display: flex; flex-direction: column; background: #f5f7fa }\r\n.${camelName}-card { background: #fff; border-radius: 14px; box-shadow: 0 4px 24px rgba(0,0,0,0.08); margin: 32px 24px 0 24px; padding-bottom: 32px }\r\n.card-header { padding: 28px 40px 12px 40px; display: flex; justify-content: space-between; align-items: center }\r\n.card-title { font-size: 22px; font-weight: 700; color: #222; margin: 0 }\r\n.table-toolbar { display: flex; justify-content: flex-end; align-items: center; gap: 8px; min-height: 32px; margin-bottom: 8px; padding: 0 40px }\r\n.action-button { border-radius: 20px; font-size: 15px; padding: 0 22px; height: 38px }\r\n.table-container { padding: 24px 40px 0 40px; display: flex; flex-direction: column }\r\n:deep(.ant-table-thead > tr > th) { background: #f7faff; font-weight: 600; font-size: 15px }\r\n</style>', NULL, 'SYSTEM', '2025-12-09 15:09:38.560', 'SYSTEM', '2025-12-09 15:09:38.560');

-- ----------------------------
-- Table structure for code_gen_database
-- ----------------------------
DROP TABLE IF EXISTS `code_gen_database`;
CREATE TABLE `code_gen_database`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `database_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据库名称',
  `database_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据库连接url',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据库用户名称',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据库密码',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改人员',
  `updated_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '代码生成数据库表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of code_gen_database
-- ----------------------------
INSERT INTO `code_gen_database` VALUES (1, 'silence-content', 'jdbc:mysql://localhost:3306/silence-content', 'root', '123456', 'SYSTEM', '2025-12-14 21:20:13.505', 'SYSTEM', '2025-12-14 13:20:13.493');

-- ----------------------------
-- Table structure for code_gen_module
-- ----------------------------
DROP TABLE IF EXISTS `code_gen_module`;
CREATE TABLE `code_gen_module`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `module_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模块名称',
  `display_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模块展示名称',
  `module_package_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `module_type` tinyint NOT NULL COMMENT '模块类型',
  `is_enabled` bit(1) NOT NULL COMMENT '是否启用',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改人员',
  `updated_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_module_name`(`module_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '代码生成模块表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of code_gen_module
-- ----------------------------
INSERT INTO `code_gen_module` VALUES (1, 'silence-content-service-enums', '内容枚举模块', 'com.old.silence.content', '1', 1, b'1', 'SYSTEM', '2025-12-29 00:05:38.873', 'SYSTEM', '2025-12-29 00:05:38.873');
INSERT INTO `code_gen_module` VALUES (2, 'silence-content-service-api', '内容service定义', 'com.old.silence.content', '2', 2, b'1', 'SYSTEM', '2025-12-27 21:11:54.998', 'SYSTEM', '2025-12-27 13:11:54.997');
INSERT INTO `code_gen_module` VALUES (3, 'silence-content-service', '内容service服务', 'com.old.silence.content', '3', 3, b'1', 'SYSTEM', '2025-12-17 23:37:21.657', 'SYSTEM', '2025-12-17 23:37:21.657');
INSERT INTO `code_gen_module` VALUES (4, 'silence-content-console', '内容console服务', 'com.old.silence.content.console', '4', 4, b'1', 'SYSTEM', '2025-12-17 23:37:22.489', 'SYSTEM', '2025-12-17 23:37:22.489');

-- ----------------------------
-- Table structure for code_gen_project
-- ----------------------------
DROP TABLE IF EXISTS `code_gen_project`;
CREATE TABLE `code_gen_project`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `project_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目名称',
  `project_type` tinyint NULL DEFAULT NULL COMMENT '项目类型',
  `display_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '项目展示名称',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `base_directory` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '基础目录',
  `repo_url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '仓库地址',
  `owner` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '项目持有者',
  `language` tinyint NOT NULL COMMENT '项目使用语言',
  `build_tool` tinyint NOT NULL COMMENT '构建工具',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改人员',
  `updated_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '代码生成项目表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of code_gen_project
-- ----------------------------
INSERT INTO `code_gen_project` VALUES (1, 'silence-content', 2, '内容项目代码', '1', 'E:\\IdeaProjects\\silence-content', '2', '老默', 1, 1, 'SYSTEM', '2025-12-06 00:21:12.508', 'SYSTEM', '2025-12-06 00:21:12.508');

-- ----------------------------
-- Table structure for code_gen_project_module
-- ----------------------------
DROP TABLE IF EXISTS `code_gen_project_module`;
CREATE TABLE `code_gen_project_module`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `module_id` bigint NOT NULL COMMENT '模块ID',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改人员',
  `updated_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_project_module`(`project_id` ASC, `module_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '代码生成项目模块关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of code_gen_project_module
-- ----------------------------
INSERT INTO `code_gen_project_module` VALUES (1, 1, 1, 'SYSTEM', '2025-12-06 11:51:40.903', 'SYSTEM', '2025-12-06 11:51:40.903');
INSERT INTO `code_gen_project_module` VALUES (2, 1, 2, 'SYSTEM', '2025-12-06 11:51:40.978', 'SYSTEM', '2025-12-06 11:51:40.978');
INSERT INTO `code_gen_project_module` VALUES (3, 1, 3, 'SYSTEM', '2025-12-06 11:51:40.978', 'SYSTEM', '2025-12-06 11:51:40.978');
INSERT INTO `code_gen_project_module` VALUES (4, 1, 4, 'SYSTEM', '2025-12-06 11:51:40.979', 'SYSTEM', '2025-12-06 11:51:40.979');

-- ----------------------------
-- Table structure for code_table_meta
-- ----------------------------
DROP TABLE IF EXISTS `code_table_meta`;
CREATE TABLE `code_table_meta`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `table_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '表名',
  `schema_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '数据库Schema',
  `comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '表注释',
  `detail` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '完整表信息JSON(包含列、索引、外键、主键)',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人',
  `updated_date` timestamp(3) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `table_name`(`table_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '代码生成器-表元数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of code_table_meta
-- ----------------------------

-- ----------------------------
-- Table structure for content
-- ----------------------------
DROP TABLE IF EXISTS `content`;
CREATE TABLE `content`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` tinyint UNSIGNED NOT NULL,
  `status` tinyint UNSIGNED NOT NULL,
  `author` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `cover_image_reference` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cover_image_reference_mode` tinyint UNSIGNED NOT NULL,
  `content_reference_mode` tinyint UNSIGNED NOT NULL,
  `content_reference` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `keywords` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `parent_id` bigint NOT NULL,
  `root_id` bigint NOT NULL,
  `is_sticky_top` bit(1) NULL DEFAULT NULL,
  `sticky_top_at` datetime NULL DEFAULT NULL,
  `attributes` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `expired_at` datetime NULL DEFAULT NULL,
  `published_at` datetime NULL DEFAULT NULL,
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 642 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of content
-- ----------------------------
INSERT INTO `content` VALUES (629, '大雪——沉静深处的回响', 1, 5, '张默默', '8', '4d1380db-4c1f-4fce-a81d-bc81dbd373f5', 1, 1, 'c869fae6-52a5-43d7-a67c-aaddb3b83ffa', '1', 0, 0, b'1', NULL, NULL, NULL, '2025-12-15 12:15:26', 'SYSTEM', '2025-12-16 23:25:02', 'SYSTEM', '2025-12-16 23:25:02');
INSERT INTO `content` VALUES (630, '谷雨--- 光，先于声音抵达', 1, 5, '周志明', '12', '10b79fc5-faa7-47f5-9dc8-59dec6559490', 1, 1, '95d55f0c-7584-4cb2-9f51-788a33656e4f', '1', 0, 0, b'0', NULL, NULL, NULL, '2025-11-28 21:41:18', 'SYSTEM', '2025-12-16 23:25:10', 'SYSTEM', '2025-12-16 23:25:10');
INSERT INTO `content` VALUES (631, '芒种', 1, 5, '周志明', '9', '4d1380db-4c1f-4fce-a81d-bc81dbd373f5', 1, 1, 'c869fae6-52a5-43d7-a67c-aaddb3b83ffa', '1', 0, 0, b'0', NULL, NULL, NULL, '2025-11-28 22:47:37', 'SYSTEM', '2025-12-16 23:25:08', 'SYSTEM', '2025-12-16 23:25:08');
INSERT INTO `content` VALUES (632, '雨水', 1, 5, '周志明', '6', '1863bbcc-7c04-427e-8c86-1843b718e053', 1, 1, '65be9890-c939-44b7-9b63-eb45576c4916', '1', 0, 0, b'0', NULL, NULL, NULL, '2025-11-29 06:59:01', 'SYSTEM', '2025-12-16 23:25:07', 'SYSTEM', '2025-12-16 23:25:07');
INSERT INTO `content` VALUES (637, 'PDF1', 3, 1, '周志明', '3', 'c89c746a-306d-4a78-a0d0-1bbb76d40227', 1, 1, 'de6dd45f-cefc-48a2-a61c-b2738ab0f018', '1', 0, 0, b'0', NULL, NULL, '2025-12-05 16:43:46', '2025-11-29 16:43:25', 'SYSTEM', '2025-11-29 16:43:57', 'SYSTEM', '2025-11-29 16:43:57');
INSERT INTO `content` VALUES (639, '测试海报1', 6, 1, '周志明', '8', '5f59055b-c16e-4c19-b336-1f7e69b91bd0', 1, 1, 'b2fabcad-5f0f-492f-80d7-24f01793d411', '1', 0, 0, b'0', NULL, NULL, '2025-11-30 03:57:13', '2025-11-30 03:56:29', 'SYSTEM', '2025-11-30 03:57:29', 'SYSTEM', '2025-11-30 03:57:29');
INSERT INTO `content` VALUES (640, '测试内容模板1', 4, 1, '周志明', '0', '4da163d5-f24b-41e8-9a57-36b867f8ca07', 1, 1, '3db19ee7-2eb8-4384-a8f7-068bfa6261ef', '1', 0, 0, b'0', NULL, NULL, '2025-11-30 06:48:23', '2025-11-30 06:48:21', 'SYSTEM', '2025-11-30 06:48:32', 'SYSTEM', '2025-11-30 06:48:32');
INSERT INTO `content` VALUES (641, '我们为何既遗忘，又执著地记忆？', 1, 5, '张默默', '10', 'b21d7fee-83dd-473c-b077-cd5fd54959dc', 1, 2, 'https://mp.weixin.qq.com/s/cdNjJQjK8iRUh3bWlgGAxg', '1', 0, 0, NULL, NULL, NULL, NULL, '2025-12-16 15:23:11', 'SYSTEM', '2025-12-16 23:26:25', 'SYSTEM', '2025-12-16 23:26:25');

-- ----------------------------
-- Table structure for content_article
-- ----------------------------
DROP TABLE IF EXISTS `content_article`;
CREATE TABLE `content_article`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `summary` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `reprint_declaration` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 642 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '内容文章表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of content_article
-- ----------------------------
INSERT INTO `content_article` VALUES (629, '萨达', '本文转载自【来源名称】，著作权归原作者所有。仅供学习交流使用，请勿用于商业用途。', 'SYSTEM', '2025-12-15 20:15:36', 'SYSTEM', '2025-12-15 12:15:36');
INSERT INTO `content_article` VALUES (630, '2防守打法收到', '诗词转载声明：本诗文收录自【来源书籍/网站】，著作权归属原作者【作者姓名】。旨在传播中华优秀传统文化，促进诗词学习交流。', 'SYSTEM', '2025-12-15 20:19:17', 'SYSTEM', '2025-12-15 12:19:18');
INSERT INTO `content_article` VALUES (631, '都认为打算', '版权声明：本文内容来源于【来源名称】，系出于传递更多学习信息之目的。若涉及版权问题，请联系我们及时处理。转载请联系原作者获授权。', 'SYSTEM', '2025-12-15 20:25:56', 'SYSTEM', '2025-12-15 12:25:57');
INSERT INTO `content_article` VALUES (632, '11', '本文转载自【来源名称】，著作权归原作者所有。仅供学习交流使用，请勿用于商业用途。', 'SYSTEM', '2025-12-15 20:30:23', 'SYSTEM', '2025-12-15 12:30:24');
INSERT INTO `content_article` VALUES (641, 'aaaa', '本文转载自【来源名称】，著作权归原作者所有。仅供学习交流使用，请勿用于商业用途。', 'SYSTEM', '2025-12-16 15:23:43', 'SYSTEM', '2025-12-16 15:23:43');

-- ----------------------------
-- Table structure for content_content_tag
-- ----------------------------
DROP TABLE IF EXISTS `content_content_tag`;
CREATE TABLE `content_content_tag`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `content_id` bigint UNSIGNED NOT NULL COMMENT '内容ID',
  `tag_id` bigint UNSIGNED NOT NULL COMMENT '标签ID',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_content_id_tag_id`(`content_id` ASC, `tag_id` ASC) USING BTREE,
  INDEX `idx_tag_id`(`tag_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2940 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '内容标签关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of content_content_tag
-- ----------------------------
INSERT INTO `content_content_tag` VALUES (2934, 637, 388, 'SYSTEM', '2025-11-29 16:43:57.430', 'SYSTEM', '2025-11-29 16:43:57.430');
INSERT INTO `content_content_tag` VALUES (2938, 639, 389, 'SYSTEM', '2025-11-30 03:57:29.018', 'SYSTEM', '2025-11-30 03:57:29.018');
INSERT INTO `content_content_tag` VALUES (2939, 640, 389, 'SYSTEM', '2025-11-30 06:48:31.774', 'SYSTEM', '2025-11-30 06:48:31.774');

-- ----------------------------
-- Table structure for content_interaction_accumulation
-- ----------------------------
DROP TABLE IF EXISTS `content_interaction_accumulation`;
CREATE TABLE `content_interaction_accumulation`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` tinyint NOT NULL COMMENT '互动类型',
  `resource_type` tinyint NOT NULL COMMENT '资源类型',
  `resource_id` bigint UNSIGNED NOT NULL COMMENT '资源id',
  `accumulation` bigint UNSIGNED NOT NULL COMMENT '累积量',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1024 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '内容互动数据累积表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of content_interaction_accumulation
-- ----------------------------
INSERT INTO `content_interaction_accumulation` VALUES (1, 3, 1, 291, 16681, 'SYSTEM', '2025-08-04 10:29:22', 'SYSTEM', '2025-08-04 10:29:22');
INSERT INTO `content_interaction_accumulation` VALUES (2, 5, 1, 291, 2539, 'SYSTEM', '2025-08-04 10:29:22', 'SYSTEM', '2025-08-04 10:29:22');
INSERT INTO `content_interaction_accumulation` VALUES (3, 6, 1, 291, 18407, 'SYSTEM', '2025-08-04 10:29:22', 'SYSTEM', '2025-08-04 10:29:22');
INSERT INTO `content_interaction_accumulation` VALUES (4, 3, 1, 292, 6527, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (5, 5, 1, 292, 2726, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (6, 6, 1, 292, 10437, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (7, 3, 1, 293, 19912, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (8, 5, 1, 293, 11821, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (9, 6, 1, 293, 13450, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (10, 3, 1, 294, 2268, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (11, 5, 1, 294, 10131, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (12, 6, 1, 294, 16855, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (13, 3, 1, 295, 16321, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (14, 5, 1, 295, 11520, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (15, 6, 1, 295, 2921, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (16, 3, 1, 296, 13031, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (17, 5, 1, 296, 3566, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (18, 6, 1, 296, 9509, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (19, 3, 1, 297, 12239, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (20, 5, 1, 297, 19574, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (21, 6, 1, 297, 13408, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (22, 3, 1, 298, 7200, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (23, 5, 1, 298, 11721, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (24, 6, 1, 298, 14370, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (25, 3, 1, 299, 13762, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (26, 5, 1, 299, 4391, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (27, 6, 1, 299, 16589, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (28, 3, 1, 300, 14316, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (29, 5, 1, 300, 12955, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (30, 6, 1, 300, 10776, 'SYSTEM', '2025-08-04 10:29:23', 'SYSTEM', '2025-08-04 10:29:23');
INSERT INTO `content_interaction_accumulation` VALUES (31, 3, 1, 301, 4890, 'SYSTEM', '2025-08-04 11:23:11', 'SYSTEM', '2025-08-04 11:23:11');
INSERT INTO `content_interaction_accumulation` VALUES (32, 5, 1, 301, 10733, 'SYSTEM', '2025-08-04 11:23:11', 'SYSTEM', '2025-08-04 11:23:11');
INSERT INTO `content_interaction_accumulation` VALUES (33, 6, 1, 301, 9746, 'SYSTEM', '2025-08-04 11:23:11', 'SYSTEM', '2025-08-04 11:23:11');
INSERT INTO `content_interaction_accumulation` VALUES (34, 3, 1, 302, 7334, 'SYSTEM', '2025-08-04 11:23:11', 'SYSTEM', '2025-08-04 11:23:11');
INSERT INTO `content_interaction_accumulation` VALUES (35, 5, 1, 302, 15856, 'SYSTEM', '2025-08-04 11:23:11', 'SYSTEM', '2025-08-04 11:23:11');
INSERT INTO `content_interaction_accumulation` VALUES (36, 6, 1, 302, 19314, 'SYSTEM', '2025-08-04 11:23:11', 'SYSTEM', '2025-08-04 11:23:11');
INSERT INTO `content_interaction_accumulation` VALUES (37, 3, 1, 303, 6505, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (38, 5, 1, 303, 6983, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (39, 6, 1, 303, 2286, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (40, 3, 1, 304, 3932, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (41, 5, 1, 304, 4867, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (42, 6, 1, 304, 18569, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (43, 3, 1, 305, 6351, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (44, 5, 1, 305, 7742, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (45, 6, 1, 305, 12395, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (46, 3, 1, 306, 9916, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (47, 5, 1, 306, 3003, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (48, 6, 1, 306, 3045, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (49, 3, 1, 307, 3949, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (50, 5, 1, 307, 11016, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (51, 6, 1, 307, 5354, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (52, 3, 1, 308, 2675, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (53, 5, 1, 308, 15369, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (54, 6, 1, 308, 12252, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (55, 3, 1, 309, 1897, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (56, 5, 1, 309, 2919, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (57, 6, 1, 309, 16918, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (58, 3, 1, 310, 5091, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (59, 5, 1, 310, 5248, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (60, 6, 1, 310, 18418, 'SYSTEM', '2025-08-04 11:23:12', 'SYSTEM', '2025-08-04 11:23:12');
INSERT INTO `content_interaction_accumulation` VALUES (61, 3, 1, 311, 9550, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (62, 5, 1, 311, 8720, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (63, 6, 1, 311, 10345, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (64, 3, 1, 312, 10964, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (65, 5, 1, 312, 1562, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (66, 6, 1, 312, 11766, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (67, 3, 1, 313, 19501, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (68, 5, 1, 313, 15730, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (69, 6, 1, 313, 6525, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (70, 3, 1, 314, 15525, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (71, 5, 1, 314, 10648, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (72, 6, 1, 314, 7117, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (73, 3, 1, 315, 17551, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (74, 5, 1, 315, 14954, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (75, 6, 1, 315, 19289, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (76, 3, 1, 316, 5201, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (77, 5, 1, 316, 7996, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (78, 6, 1, 316, 12151, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (79, 3, 1, 317, 6258, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (80, 5, 1, 317, 11631, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (81, 6, 1, 317, 4536, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (82, 3, 1, 318, 17053, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (83, 5, 1, 318, 1302, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (84, 6, 1, 318, 19785, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (85, 3, 1, 319, 4256, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (86, 5, 1, 319, 2780, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (87, 6, 1, 319, 11737, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (88, 3, 1, 320, 3051, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (89, 5, 1, 320, 7984, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (90, 6, 1, 320, 5209, 'SYSTEM', '2025-08-04 11:23:17', 'SYSTEM', '2025-08-04 11:23:17');
INSERT INTO `content_interaction_accumulation` VALUES (91, 3, 1, 321, 14028, 'SYSTEM', '2025-08-04 11:23:19', 'SYSTEM', '2025-08-04 11:23:19');
INSERT INTO `content_interaction_accumulation` VALUES (92, 5, 1, 321, 5262, 'SYSTEM', '2025-08-04 11:23:19', 'SYSTEM', '2025-08-04 11:23:19');
INSERT INTO `content_interaction_accumulation` VALUES (93, 6, 1, 321, 7441, 'SYSTEM', '2025-08-04 11:23:19', 'SYSTEM', '2025-08-04 11:23:19');
INSERT INTO `content_interaction_accumulation` VALUES (94, 3, 1, 322, 19245, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (95, 5, 1, 322, 2116, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (96, 6, 1, 322, 3537, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (97, 3, 1, 323, 9051, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (98, 5, 1, 323, 4636, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (99, 6, 1, 323, 12541, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (100, 3, 1, 324, 5140, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (101, 5, 1, 324, 19320, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (102, 6, 1, 324, 6219, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (103, 3, 1, 325, 17087, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (104, 5, 1, 325, 7400, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (105, 6, 1, 325, 1634, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (106, 3, 1, 326, 10052, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (107, 5, 1, 326, 5627, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (108, 6, 1, 326, 14242, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (109, 3, 1, 327, 9071, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (110, 5, 1, 327, 9118, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (111, 6, 1, 327, 12168, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (112, 3, 1, 328, 3512, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (113, 5, 1, 328, 14450, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (114, 6, 1, 328, 9074, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (115, 3, 1, 329, 13740, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (116, 5, 1, 329, 1016, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (117, 6, 1, 329, 19304, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (118, 3, 1, 330, 6876, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (119, 5, 1, 330, 3793, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (120, 6, 1, 330, 4946, 'SYSTEM', '2025-08-04 11:23:20', 'SYSTEM', '2025-08-04 11:23:20');
INSERT INTO `content_interaction_accumulation` VALUES (121, 3, 1, 331, 8734, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (122, 5, 1, 331, 12132, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (123, 6, 1, 331, 7117, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (124, 3, 1, 332, 14317, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (125, 5, 1, 332, 10286, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (126, 6, 1, 332, 9573, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (127, 3, 1, 333, 14961, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (128, 5, 1, 333, 4016, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (129, 6, 1, 333, 4931, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (130, 3, 1, 334, 8223, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (131, 5, 1, 334, 17089, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (132, 6, 1, 334, 10153, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (133, 3, 1, 335, 7116, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (134, 5, 1, 335, 19597, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (135, 6, 1, 335, 16892, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (136, 3, 1, 336, 3574, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (137, 5, 1, 336, 17473, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (138, 6, 1, 336, 17602, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (139, 3, 1, 337, 10359, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (140, 5, 1, 337, 7703, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (141, 6, 1, 337, 17324, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (142, 3, 1, 338, 10804, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (143, 5, 1, 338, 9833, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (144, 6, 1, 338, 14221, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (145, 3, 1, 339, 8498, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (146, 5, 1, 339, 18203, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (147, 6, 1, 339, 13823, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (148, 3, 1, 340, 10568, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (149, 5, 1, 340, 8116, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (150, 6, 1, 340, 8859, 'SYSTEM', '2025-08-04 11:23:25', 'SYSTEM', '2025-08-04 11:23:25');
INSERT INTO `content_interaction_accumulation` VALUES (151, 3, 1, 341, 10955, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (152, 5, 1, 341, 6810, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (153, 6, 1, 341, 19136, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (154, 3, 1, 342, 2391, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (155, 5, 1, 342, 13201, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (156, 6, 1, 342, 8057, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (157, 3, 1, 343, 9269, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (158, 5, 1, 343, 18723, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (159, 6, 1, 343, 6984, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (160, 3, 1, 344, 4380, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (161, 5, 1, 344, 14677, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (162, 6, 1, 344, 7354, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (163, 3, 1, 345, 8535, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (164, 5, 1, 345, 14991, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (165, 6, 1, 345, 13377, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (166, 3, 1, 346, 5963, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (167, 5, 1, 346, 10010, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (168, 6, 1, 346, 8404, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (169, 3, 1, 347, 10773, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (170, 5, 1, 347, 5212, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (171, 6, 1, 347, 6341, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (172, 3, 1, 348, 18307, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (173, 5, 1, 348, 14949, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (174, 6, 1, 348, 10375, 'SYSTEM', '2025-08-04 11:23:27', 'SYSTEM', '2025-08-04 11:23:27');
INSERT INTO `content_interaction_accumulation` VALUES (175, 3, 1, 349, 17620, 'SYSTEM', '2025-08-04 11:23:28', 'SYSTEM', '2025-08-04 11:23:28');
INSERT INTO `content_interaction_accumulation` VALUES (176, 5, 1, 349, 9997, 'SYSTEM', '2025-08-04 11:23:28', 'SYSTEM', '2025-08-04 11:23:28');
INSERT INTO `content_interaction_accumulation` VALUES (177, 6, 1, 349, 7493, 'SYSTEM', '2025-08-04 11:23:28', 'SYSTEM', '2025-08-04 11:23:28');
INSERT INTO `content_interaction_accumulation` VALUES (178, 3, 1, 350, 18671, 'SYSTEM', '2025-08-04 11:23:28', 'SYSTEM', '2025-08-04 11:23:28');
INSERT INTO `content_interaction_accumulation` VALUES (179, 5, 1, 350, 17958, 'SYSTEM', '2025-08-04 11:23:28', 'SYSTEM', '2025-08-04 11:23:28');
INSERT INTO `content_interaction_accumulation` VALUES (180, 6, 1, 350, 7461, 'SYSTEM', '2025-08-04 11:23:28', 'SYSTEM', '2025-08-04 11:23:28');
INSERT INTO `content_interaction_accumulation` VALUES (181, 3, 1, 351, 2585, 'SYSTEM', '2025-08-04 11:23:28', 'SYSTEM', '2025-08-04 11:23:28');
INSERT INTO `content_interaction_accumulation` VALUES (182, 5, 1, 351, 19064, 'SYSTEM', '2025-08-04 11:23:28', 'SYSTEM', '2025-08-04 11:23:28');
INSERT INTO `content_interaction_accumulation` VALUES (183, 6, 1, 351, 6284, 'SYSTEM', '2025-08-04 11:23:28', 'SYSTEM', '2025-08-04 11:23:28');
INSERT INTO `content_interaction_accumulation` VALUES (184, 3, 1, 352, 18457, 'SYSTEM', '2025-08-04 11:23:28', 'SYSTEM', '2025-08-04 11:23:28');
INSERT INTO `content_interaction_accumulation` VALUES (185, 5, 1, 352, 3860, 'SYSTEM', '2025-08-04 11:23:28', 'SYSTEM', '2025-08-04 11:23:28');
INSERT INTO `content_interaction_accumulation` VALUES (186, 6, 1, 352, 15795, 'SYSTEM', '2025-08-04 11:23:28', 'SYSTEM', '2025-08-04 11:23:28');
INSERT INTO `content_interaction_accumulation` VALUES (187, 3, 1, 353, 5386, 'SYSTEM', '2025-08-04 11:23:28', 'SYSTEM', '2025-08-04 11:23:28');
INSERT INTO `content_interaction_accumulation` VALUES (188, 5, 1, 353, 4397, 'SYSTEM', '2025-08-04 11:23:28', 'SYSTEM', '2025-08-04 11:23:28');
INSERT INTO `content_interaction_accumulation` VALUES (189, 6, 1, 353, 8211, 'SYSTEM', '2025-08-04 11:23:28', 'SYSTEM', '2025-08-04 11:23:28');
INSERT INTO `content_interaction_accumulation` VALUES (190, 3, 1, 354, 9975, 'SYSTEM', '2025-08-04 11:23:28', 'SYSTEM', '2025-08-04 11:23:28');
INSERT INTO `content_interaction_accumulation` VALUES (191, 5, 1, 354, 15668, 'SYSTEM', '2025-08-04 11:23:28', 'SYSTEM', '2025-08-04 11:23:28');
INSERT INTO `content_interaction_accumulation` VALUES (192, 6, 1, 354, 6287, 'SYSTEM', '2025-08-04 11:23:28', 'SYSTEM', '2025-08-04 11:23:28');
INSERT INTO `content_interaction_accumulation` VALUES (193, 3, 1, 355, 9209, 'SYSTEM', '2025-08-04 11:23:28', 'SYSTEM', '2025-08-04 11:23:28');
INSERT INTO `content_interaction_accumulation` VALUES (194, 5, 1, 355, 16216, 'SYSTEM', '2025-08-04 11:23:28', 'SYSTEM', '2025-08-04 11:23:28');
INSERT INTO `content_interaction_accumulation` VALUES (195, 6, 1, 355, 5672, 'SYSTEM', '2025-08-04 11:23:28', 'SYSTEM', '2025-08-04 11:23:28');
INSERT INTO `content_interaction_accumulation` VALUES (196, 3, 1, 356, 13595, 'SYSTEM', '2025-08-04 11:23:29', 'SYSTEM', '2025-08-04 11:23:29');
INSERT INTO `content_interaction_accumulation` VALUES (197, 5, 1, 356, 18088, 'SYSTEM', '2025-08-04 11:23:29', 'SYSTEM', '2025-08-04 11:23:29');
INSERT INTO `content_interaction_accumulation` VALUES (198, 6, 1, 356, 11564, 'SYSTEM', '2025-08-04 11:23:29', 'SYSTEM', '2025-08-04 11:23:29');
INSERT INTO `content_interaction_accumulation` VALUES (199, 3, 1, 357, 6267, 'SYSTEM', '2025-08-04 11:23:29', 'SYSTEM', '2025-08-04 11:23:29');
INSERT INTO `content_interaction_accumulation` VALUES (200, 5, 1, 357, 16586, 'SYSTEM', '2025-08-04 11:23:29', 'SYSTEM', '2025-08-04 11:23:29');
INSERT INTO `content_interaction_accumulation` VALUES (201, 6, 1, 357, 10486, 'SYSTEM', '2025-08-04 11:23:29', 'SYSTEM', '2025-08-04 11:23:29');
INSERT INTO `content_interaction_accumulation` VALUES (202, 3, 1, 358, 9858, 'SYSTEM', '2025-08-04 11:23:29', 'SYSTEM', '2025-08-04 11:23:29');
INSERT INTO `content_interaction_accumulation` VALUES (203, 5, 1, 358, 4605, 'SYSTEM', '2025-08-04 11:23:29', 'SYSTEM', '2025-08-04 11:23:29');
INSERT INTO `content_interaction_accumulation` VALUES (204, 6, 1, 358, 7453, 'SYSTEM', '2025-08-04 11:23:29', 'SYSTEM', '2025-08-04 11:23:29');
INSERT INTO `content_interaction_accumulation` VALUES (205, 3, 1, 359, 11104, 'SYSTEM', '2025-08-04 11:23:29', 'SYSTEM', '2025-08-04 11:23:29');
INSERT INTO `content_interaction_accumulation` VALUES (206, 5, 1, 359, 10034, 'SYSTEM', '2025-08-04 11:23:29', 'SYSTEM', '2025-08-04 11:23:29');
INSERT INTO `content_interaction_accumulation` VALUES (207, 6, 1, 359, 13568, 'SYSTEM', '2025-08-04 11:23:29', 'SYSTEM', '2025-08-04 11:23:29');
INSERT INTO `content_interaction_accumulation` VALUES (208, 3, 1, 360, 18428, 'SYSTEM', '2025-08-04 11:23:29', 'SYSTEM', '2025-08-04 11:23:29');
INSERT INTO `content_interaction_accumulation` VALUES (209, 5, 1, 360, 11327, 'SYSTEM', '2025-08-04 11:23:29', 'SYSTEM', '2025-08-04 11:23:29');
INSERT INTO `content_interaction_accumulation` VALUES (210, 6, 1, 360, 16011, 'SYSTEM', '2025-08-04 11:23:29', 'SYSTEM', '2025-08-04 11:23:29');
INSERT INTO `content_interaction_accumulation` VALUES (211, 3, 1, 361, 9290, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (212, 5, 1, 361, 10024, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (213, 6, 1, 361, 14557, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (214, 3, 1, 362, 15371, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (215, 5, 1, 362, 5607, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (216, 6, 1, 362, 12640, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (217, 3, 1, 363, 16544, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (218, 5, 1, 363, 7302, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (219, 6, 1, 363, 13890, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (220, 3, 1, 364, 4861, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (221, 5, 1, 364, 15420, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (222, 6, 1, 364, 13102, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (223, 3, 1, 365, 14644, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (224, 5, 1, 365, 2101, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (225, 6, 1, 365, 11348, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (226, 3, 1, 366, 16920, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (227, 5, 1, 366, 13563, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (228, 6, 1, 366, 5493, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (229, 3, 1, 367, 6808, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (230, 5, 1, 367, 8491, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (231, 6, 1, 367, 16325, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (232, 3, 1, 368, 15450, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (233, 5, 1, 368, 8438, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (234, 6, 1, 368, 11140, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (235, 3, 1, 369, 17410, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (236, 5, 1, 369, 4771, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (237, 6, 1, 369, 17147, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (238, 3, 1, 370, 13611, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (239, 5, 1, 370, 3537, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (240, 6, 1, 370, 16840, 'SYSTEM', '2025-08-04 11:23:30', 'SYSTEM', '2025-08-04 11:23:30');
INSERT INTO `content_interaction_accumulation` VALUES (241, 3, 1, 371, 12797, 'SYSTEM', '2025-08-04 11:23:31', 'SYSTEM', '2025-08-04 11:23:31');
INSERT INTO `content_interaction_accumulation` VALUES (242, 5, 1, 371, 6996, 'SYSTEM', '2025-08-04 11:23:31', 'SYSTEM', '2025-08-04 11:23:31');
INSERT INTO `content_interaction_accumulation` VALUES (243, 6, 1, 371, 15955, 'SYSTEM', '2025-08-04 11:23:31', 'SYSTEM', '2025-08-04 11:23:31');
INSERT INTO `content_interaction_accumulation` VALUES (244, 3, 1, 372, 18135, 'SYSTEM', '2025-08-04 11:23:31', 'SYSTEM', '2025-08-04 11:23:31');
INSERT INTO `content_interaction_accumulation` VALUES (245, 5, 1, 372, 12247, 'SYSTEM', '2025-08-04 11:23:31', 'SYSTEM', '2025-08-04 11:23:31');
INSERT INTO `content_interaction_accumulation` VALUES (246, 6, 1, 372, 3496, 'SYSTEM', '2025-08-04 11:23:31', 'SYSTEM', '2025-08-04 11:23:31');
INSERT INTO `content_interaction_accumulation` VALUES (247, 3, 1, 373, 3851, 'SYSTEM', '2025-08-04 11:23:31', 'SYSTEM', '2025-08-04 11:23:31');
INSERT INTO `content_interaction_accumulation` VALUES (248, 5, 1, 373, 14131, 'SYSTEM', '2025-08-04 11:23:31', 'SYSTEM', '2025-08-04 11:23:31');
INSERT INTO `content_interaction_accumulation` VALUES (249, 6, 1, 373, 5800, 'SYSTEM', '2025-08-04 11:23:31', 'SYSTEM', '2025-08-04 11:23:31');
INSERT INTO `content_interaction_accumulation` VALUES (250, 3, 1, 374, 7654, 'SYSTEM', '2025-08-04 11:23:31', 'SYSTEM', '2025-08-04 11:23:31');
INSERT INTO `content_interaction_accumulation` VALUES (251, 5, 1, 374, 15468, 'SYSTEM', '2025-08-04 11:23:31', 'SYSTEM', '2025-08-04 11:23:31');
INSERT INTO `content_interaction_accumulation` VALUES (252, 6, 1, 374, 3511, 'SYSTEM', '2025-08-04 11:23:31', 'SYSTEM', '2025-08-04 11:23:31');
INSERT INTO `content_interaction_accumulation` VALUES (253, 3, 1, 375, 2293, 'SYSTEM', '2025-08-04 11:23:31', 'SYSTEM', '2025-08-04 11:23:31');
INSERT INTO `content_interaction_accumulation` VALUES (254, 5, 1, 375, 5328, 'SYSTEM', '2025-08-04 11:23:31', 'SYSTEM', '2025-08-04 11:23:31');
INSERT INTO `content_interaction_accumulation` VALUES (255, 6, 1, 375, 3225, 'SYSTEM', '2025-08-04 11:23:31', 'SYSTEM', '2025-08-04 11:23:31');
INSERT INTO `content_interaction_accumulation` VALUES (256, 3, 1, 376, 17652, 'SYSTEM', '2025-08-04 11:23:31', 'SYSTEM', '2025-08-04 11:23:31');
INSERT INTO `content_interaction_accumulation` VALUES (257, 5, 1, 376, 17008, 'SYSTEM', '2025-08-04 11:23:31', 'SYSTEM', '2025-08-04 11:23:31');
INSERT INTO `content_interaction_accumulation` VALUES (258, 6, 1, 376, 16824, 'SYSTEM', '2025-08-04 11:23:31', 'SYSTEM', '2025-08-04 11:23:31');
INSERT INTO `content_interaction_accumulation` VALUES (259, 3, 1, 377, 9160, 'SYSTEM', '2025-08-04 11:23:31', 'SYSTEM', '2025-08-04 11:23:31');
INSERT INTO `content_interaction_accumulation` VALUES (260, 5, 1, 377, 15408, 'SYSTEM', '2025-08-04 11:23:31', 'SYSTEM', '2025-08-04 11:23:31');
INSERT INTO `content_interaction_accumulation` VALUES (261, 6, 1, 377, 6454, 'SYSTEM', '2025-08-04 11:23:31', 'SYSTEM', '2025-08-04 11:23:31');
INSERT INTO `content_interaction_accumulation` VALUES (262, 3, 1, 378, 14341, 'SYSTEM', '2025-08-04 11:23:32', 'SYSTEM', '2025-08-04 11:23:32');
INSERT INTO `content_interaction_accumulation` VALUES (263, 5, 1, 378, 9396, 'SYSTEM', '2025-08-04 11:23:32', 'SYSTEM', '2025-08-04 11:23:32');
INSERT INTO `content_interaction_accumulation` VALUES (264, 6, 1, 378, 19419, 'SYSTEM', '2025-08-04 11:23:32', 'SYSTEM', '2025-08-04 11:23:32');
INSERT INTO `content_interaction_accumulation` VALUES (265, 3, 1, 379, 14719, 'SYSTEM', '2025-08-04 11:23:32', 'SYSTEM', '2025-08-04 11:23:32');
INSERT INTO `content_interaction_accumulation` VALUES (266, 5, 1, 379, 18284, 'SYSTEM', '2025-08-04 11:23:32', 'SYSTEM', '2025-08-04 11:23:32');
INSERT INTO `content_interaction_accumulation` VALUES (267, 6, 1, 379, 7055, 'SYSTEM', '2025-08-04 11:23:32', 'SYSTEM', '2025-08-04 11:23:32');
INSERT INTO `content_interaction_accumulation` VALUES (268, 3, 1, 380, 5422, 'SYSTEM', '2025-08-04 11:23:32', 'SYSTEM', '2025-08-04 11:23:32');
INSERT INTO `content_interaction_accumulation` VALUES (269, 5, 1, 380, 7608, 'SYSTEM', '2025-08-04 11:23:32', 'SYSTEM', '2025-08-04 11:23:32');
INSERT INTO `content_interaction_accumulation` VALUES (270, 6, 1, 380, 4910, 'SYSTEM', '2025-08-04 11:23:32', 'SYSTEM', '2025-08-04 11:23:32');
INSERT INTO `content_interaction_accumulation` VALUES (271, 3, 1, 381, 18111, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (272, 5, 1, 381, 15448, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (273, 6, 1, 381, 16173, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (274, 3, 1, 382, 1331, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (275, 5, 1, 382, 4068, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (276, 6, 1, 382, 16204, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (277, 3, 1, 383, 15410, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (278, 5, 1, 383, 6023, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (279, 6, 1, 383, 11133, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (280, 3, 1, 384, 3554, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (281, 5, 1, 384, 5979, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (282, 6, 1, 384, 3747, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (283, 3, 1, 385, 17602, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (284, 5, 1, 385, 19617, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (285, 6, 1, 385, 18680, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (286, 3, 1, 386, 6063, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (287, 5, 1, 386, 5680, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (288, 6, 1, 386, 7759, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (289, 3, 1, 387, 16149, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (290, 5, 1, 387, 15590, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (291, 6, 1, 387, 7680, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (292, 3, 1, 388, 3622, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (293, 5, 1, 388, 11848, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (294, 6, 1, 388, 7626, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (295, 3, 1, 389, 8712, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (296, 5, 1, 389, 14212, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (297, 6, 1, 389, 3816, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (298, 3, 1, 390, 9891, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (299, 5, 1, 390, 10435, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (300, 6, 1, 390, 5143, 'SYSTEM', '2025-08-04 11:23:33', 'SYSTEM', '2025-08-04 11:23:33');
INSERT INTO `content_interaction_accumulation` VALUES (301, 3, 1, 391, 18928, 'SYSTEM', '2025-08-04 11:23:34', 'SYSTEM', '2025-08-04 11:23:34');
INSERT INTO `content_interaction_accumulation` VALUES (302, 5, 1, 391, 6811, 'SYSTEM', '2025-08-04 11:23:34', 'SYSTEM', '2025-08-04 11:23:34');
INSERT INTO `content_interaction_accumulation` VALUES (303, 6, 1, 391, 19246, 'SYSTEM', '2025-08-04 11:23:34', 'SYSTEM', '2025-08-04 11:23:34');
INSERT INTO `content_interaction_accumulation` VALUES (304, 3, 1, 392, 12245, 'SYSTEM', '2025-08-04 11:23:34', 'SYSTEM', '2025-08-04 11:23:34');
INSERT INTO `content_interaction_accumulation` VALUES (305, 5, 1, 392, 3527, 'SYSTEM', '2025-08-04 11:23:34', 'SYSTEM', '2025-08-04 11:23:34');
INSERT INTO `content_interaction_accumulation` VALUES (306, 6, 1, 392, 10091, 'SYSTEM', '2025-08-04 11:23:34', 'SYSTEM', '2025-08-04 11:23:34');
INSERT INTO `content_interaction_accumulation` VALUES (307, 3, 1, 393, 3473, 'SYSTEM', '2025-08-04 11:23:34', 'SYSTEM', '2025-08-04 11:23:34');
INSERT INTO `content_interaction_accumulation` VALUES (308, 5, 1, 393, 18914, 'SYSTEM', '2025-08-04 11:23:34', 'SYSTEM', '2025-08-04 11:23:34');
INSERT INTO `content_interaction_accumulation` VALUES (309, 6, 1, 393, 5255, 'SYSTEM', '2025-08-04 11:23:34', 'SYSTEM', '2025-08-04 11:23:34');
INSERT INTO `content_interaction_accumulation` VALUES (310, 3, 1, 394, 18185, 'SYSTEM', '2025-08-04 11:23:35', 'SYSTEM', '2025-08-04 11:23:35');
INSERT INTO `content_interaction_accumulation` VALUES (311, 5, 1, 394, 17600, 'SYSTEM', '2025-08-04 11:23:35', 'SYSTEM', '2025-08-04 11:23:35');
INSERT INTO `content_interaction_accumulation` VALUES (312, 6, 1, 394, 15965, 'SYSTEM', '2025-08-04 11:23:35', 'SYSTEM', '2025-08-04 11:23:35');
INSERT INTO `content_interaction_accumulation` VALUES (313, 3, 1, 395, 9677, 'SYSTEM', '2025-08-04 11:23:35', 'SYSTEM', '2025-08-04 11:23:35');
INSERT INTO `content_interaction_accumulation` VALUES (314, 5, 1, 395, 13767, 'SYSTEM', '2025-08-04 11:23:35', 'SYSTEM', '2025-08-04 11:23:35');
INSERT INTO `content_interaction_accumulation` VALUES (315, 6, 1, 395, 14427, 'SYSTEM', '2025-08-04 11:23:35', 'SYSTEM', '2025-08-04 11:23:35');
INSERT INTO `content_interaction_accumulation` VALUES (316, 3, 1, 396, 2083, 'SYSTEM', '2025-08-04 11:23:35', 'SYSTEM', '2025-08-04 11:23:35');
INSERT INTO `content_interaction_accumulation` VALUES (317, 5, 1, 396, 8977, 'SYSTEM', '2025-08-04 11:23:35', 'SYSTEM', '2025-08-04 11:23:35');
INSERT INTO `content_interaction_accumulation` VALUES (318, 6, 1, 396, 5366, 'SYSTEM', '2025-08-04 11:23:35', 'SYSTEM', '2025-08-04 11:23:35');
INSERT INTO `content_interaction_accumulation` VALUES (319, 3, 1, 397, 18256, 'SYSTEM', '2025-08-04 11:23:35', 'SYSTEM', '2025-08-04 11:23:35');
INSERT INTO `content_interaction_accumulation` VALUES (320, 5, 1, 397, 3499, 'SYSTEM', '2025-08-04 11:23:35', 'SYSTEM', '2025-08-04 11:23:35');
INSERT INTO `content_interaction_accumulation` VALUES (321, 6, 1, 397, 18471, 'SYSTEM', '2025-08-04 11:23:35', 'SYSTEM', '2025-08-04 11:23:35');
INSERT INTO `content_interaction_accumulation` VALUES (322, 3, 1, 398, 15003, 'SYSTEM', '2025-08-04 11:23:35', 'SYSTEM', '2025-08-04 11:23:35');
INSERT INTO `content_interaction_accumulation` VALUES (323, 5, 1, 398, 19718, 'SYSTEM', '2025-08-04 11:23:35', 'SYSTEM', '2025-08-04 11:23:35');
INSERT INTO `content_interaction_accumulation` VALUES (324, 6, 1, 398, 9091, 'SYSTEM', '2025-08-04 11:23:35', 'SYSTEM', '2025-08-04 11:23:35');
INSERT INTO `content_interaction_accumulation` VALUES (325, 3, 1, 399, 12004, 'SYSTEM', '2025-08-04 11:23:35', 'SYSTEM', '2025-08-04 11:23:35');
INSERT INTO `content_interaction_accumulation` VALUES (326, 5, 1, 399, 18935, 'SYSTEM', '2025-08-04 11:23:35', 'SYSTEM', '2025-08-04 11:23:35');
INSERT INTO `content_interaction_accumulation` VALUES (327, 6, 1, 399, 1735, 'SYSTEM', '2025-08-04 11:23:35', 'SYSTEM', '2025-08-04 11:23:35');
INSERT INTO `content_interaction_accumulation` VALUES (328, 3, 1, 400, 19804, 'SYSTEM', '2025-08-04 11:23:35', 'SYSTEM', '2025-08-04 11:23:35');
INSERT INTO `content_interaction_accumulation` VALUES (329, 5, 1, 400, 17501, 'SYSTEM', '2025-08-04 11:23:35', 'SYSTEM', '2025-08-04 11:23:35');
INSERT INTO `content_interaction_accumulation` VALUES (330, 6, 1, 400, 11448, 'SYSTEM', '2025-08-04 11:23:35', 'SYSTEM', '2025-08-04 11:23:35');
INSERT INTO `content_interaction_accumulation` VALUES (331, 3, 1, 401, 2347, 'SYSTEM', '2025-08-04 11:23:36', 'SYSTEM', '2025-08-04 11:23:36');
INSERT INTO `content_interaction_accumulation` VALUES (332, 5, 1, 401, 2053, 'SYSTEM', '2025-08-04 11:23:36', 'SYSTEM', '2025-08-04 11:23:36');
INSERT INTO `content_interaction_accumulation` VALUES (333, 6, 1, 401, 5926, 'SYSTEM', '2025-08-04 11:23:36', 'SYSTEM', '2025-08-04 11:23:36');
INSERT INTO `content_interaction_accumulation` VALUES (334, 3, 1, 402, 12109, 'SYSTEM', '2025-08-04 11:23:36', 'SYSTEM', '2025-08-04 11:23:36');
INSERT INTO `content_interaction_accumulation` VALUES (335, 5, 1, 402, 1676, 'SYSTEM', '2025-08-04 11:23:36', 'SYSTEM', '2025-08-04 11:23:36');
INSERT INTO `content_interaction_accumulation` VALUES (336, 6, 1, 402, 9646, 'SYSTEM', '2025-08-04 11:23:36', 'SYSTEM', '2025-08-04 11:23:36');
INSERT INTO `content_interaction_accumulation` VALUES (337, 3, 1, 403, 19803, 'SYSTEM', '2025-08-04 11:23:36', 'SYSTEM', '2025-08-04 11:23:36');
INSERT INTO `content_interaction_accumulation` VALUES (338, 5, 1, 403, 19912, 'SYSTEM', '2025-08-04 11:23:36', 'SYSTEM', '2025-08-04 11:23:36');
INSERT INTO `content_interaction_accumulation` VALUES (339, 6, 1, 403, 10236, 'SYSTEM', '2025-08-04 11:23:36', 'SYSTEM', '2025-08-04 11:23:36');
INSERT INTO `content_interaction_accumulation` VALUES (340, 3, 1, 404, 7706, 'SYSTEM', '2025-08-04 11:23:36', 'SYSTEM', '2025-08-04 11:23:36');
INSERT INTO `content_interaction_accumulation` VALUES (341, 5, 1, 404, 10116, 'SYSTEM', '2025-08-04 11:23:36', 'SYSTEM', '2025-08-04 11:23:36');
INSERT INTO `content_interaction_accumulation` VALUES (342, 6, 1, 404, 6398, 'SYSTEM', '2025-08-04 11:23:36', 'SYSTEM', '2025-08-04 11:23:36');
INSERT INTO `content_interaction_accumulation` VALUES (343, 3, 1, 405, 1017, 'SYSTEM', '2025-08-04 11:23:36', 'SYSTEM', '2025-08-04 11:23:36');
INSERT INTO `content_interaction_accumulation` VALUES (344, 5, 1, 405, 16183, 'SYSTEM', '2025-08-04 11:23:36', 'SYSTEM', '2025-08-04 11:23:36');
INSERT INTO `content_interaction_accumulation` VALUES (345, 6, 1, 405, 15999, 'SYSTEM', '2025-08-04 11:23:36', 'SYSTEM', '2025-08-04 11:23:36');
INSERT INTO `content_interaction_accumulation` VALUES (346, 3, 1, 406, 15062, 'SYSTEM', '2025-08-04 11:23:36', 'SYSTEM', '2025-08-04 11:23:36');
INSERT INTO `content_interaction_accumulation` VALUES (347, 5, 1, 406, 4416, 'SYSTEM', '2025-08-04 11:23:36', 'SYSTEM', '2025-08-04 11:23:36');
INSERT INTO `content_interaction_accumulation` VALUES (348, 6, 1, 406, 4631, 'SYSTEM', '2025-08-04 11:23:36', 'SYSTEM', '2025-08-04 11:23:36');
INSERT INTO `content_interaction_accumulation` VALUES (349, 3, 1, 407, 12990, 'SYSTEM', '2025-08-04 11:23:36', 'SYSTEM', '2025-08-04 11:23:36');
INSERT INTO `content_interaction_accumulation` VALUES (350, 5, 1, 407, 11169, 'SYSTEM', '2025-08-04 11:23:36', 'SYSTEM', '2025-08-04 11:23:36');
INSERT INTO `content_interaction_accumulation` VALUES (351, 6, 1, 407, 17958, 'SYSTEM', '2025-08-04 11:23:36', 'SYSTEM', '2025-08-04 11:23:36');
INSERT INTO `content_interaction_accumulation` VALUES (352, 3, 1, 408, 17227, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (353, 5, 1, 408, 7867, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (354, 6, 1, 408, 14538, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (355, 3, 1, 409, 13120, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (356, 5, 1, 409, 12301, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (357, 6, 1, 409, 2173, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (358, 3, 1, 410, 17837, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (359, 5, 1, 410, 7118, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (360, 6, 1, 410, 6127, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (361, 3, 1, 411, 6463, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (362, 5, 1, 411, 6572, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (363, 6, 1, 411, 10551, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (364, 3, 1, 412, 3199, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (365, 5, 1, 412, 7041, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (366, 6, 1, 412, 14117, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (367, 3, 1, 413, 4063, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (368, 5, 1, 413, 16884, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (369, 6, 1, 413, 15640, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (370, 3, 1, 414, 9935, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (371, 5, 1, 414, 7889, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (372, 6, 1, 414, 5018, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (373, 3, 1, 415, 5861, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (374, 5, 1, 415, 6552, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (375, 6, 1, 415, 15109, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (376, 3, 1, 416, 6870, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (377, 5, 1, 416, 7568, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (378, 6, 1, 416, 9772, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (379, 3, 1, 417, 12816, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (380, 5, 1, 417, 4709, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (381, 6, 1, 417, 10533, 'SYSTEM', '2025-08-04 11:23:42', 'SYSTEM', '2025-08-04 11:23:42');
INSERT INTO `content_interaction_accumulation` VALUES (382, 3, 1, 418, 6251, 'SYSTEM', '2025-08-04 11:23:43', 'SYSTEM', '2025-08-04 11:23:43');
INSERT INTO `content_interaction_accumulation` VALUES (383, 5, 1, 418, 14078, 'SYSTEM', '2025-08-04 11:23:43', 'SYSTEM', '2025-08-04 11:23:43');
INSERT INTO `content_interaction_accumulation` VALUES (384, 6, 1, 418, 9335, 'SYSTEM', '2025-08-04 11:23:43', 'SYSTEM', '2025-08-04 11:23:43');
INSERT INTO `content_interaction_accumulation` VALUES (385, 3, 1, 419, 17224, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (386, 5, 1, 419, 13439, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (387, 6, 1, 419, 5508, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (388, 3, 1, 420, 10473, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (389, 5, 1, 420, 11073, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (390, 6, 1, 420, 19690, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (391, 3, 1, 421, 14737, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (392, 5, 1, 421, 18239, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (393, 6, 1, 421, 11945, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (394, 3, 1, 422, 4167, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (395, 5, 1, 422, 17602, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (396, 6, 1, 422, 14722, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (397, 3, 1, 423, 12247, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (398, 5, 1, 423, 4141, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (399, 6, 1, 423, 8297, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (400, 3, 1, 424, 6830, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (401, 5, 1, 424, 4976, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (402, 6, 1, 424, 17471, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (403, 3, 1, 425, 10531, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (404, 5, 1, 425, 12394, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (405, 6, 1, 425, 16824, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (406, 3, 1, 426, 18669, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (407, 5, 1, 426, 3414, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (408, 6, 1, 426, 14827, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (409, 3, 1, 427, 15060, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (410, 5, 1, 427, 3750, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (411, 6, 1, 427, 19596, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (412, 3, 1, 428, 7035, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (413, 5, 1, 428, 6442, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (414, 6, 1, 428, 4585, 'SYSTEM', '2025-08-04 16:06:56', 'SYSTEM', '2025-08-04 16:06:56');
INSERT INTO `content_interaction_accumulation` VALUES (415, 3, 1, 429, 10329, 'SYSTEM', '2025-08-04 16:06:58', 'SYSTEM', '2025-08-04 16:06:58');
INSERT INTO `content_interaction_accumulation` VALUES (416, 5, 1, 429, 2383, 'SYSTEM', '2025-08-04 16:06:58', 'SYSTEM', '2025-08-04 16:06:58');
INSERT INTO `content_interaction_accumulation` VALUES (417, 6, 1, 429, 8118, 'SYSTEM', '2025-08-04 16:06:58', 'SYSTEM', '2025-08-04 16:06:58');
INSERT INTO `content_interaction_accumulation` VALUES (418, 3, 1, 430, 18367, 'SYSTEM', '2025-08-04 16:06:58', 'SYSTEM', '2025-08-04 16:06:58');
INSERT INTO `content_interaction_accumulation` VALUES (419, 5, 1, 430, 15944, 'SYSTEM', '2025-08-04 16:06:58', 'SYSTEM', '2025-08-04 16:06:58');
INSERT INTO `content_interaction_accumulation` VALUES (420, 6, 1, 430, 7316, 'SYSTEM', '2025-08-04 16:06:58', 'SYSTEM', '2025-08-04 16:06:58');
INSERT INTO `content_interaction_accumulation` VALUES (421, 3, 1, 431, 11075, 'SYSTEM', '2025-08-04 16:06:58', 'SYSTEM', '2025-08-04 16:06:58');
INSERT INTO `content_interaction_accumulation` VALUES (422, 5, 1, 431, 12354, 'SYSTEM', '2025-08-04 16:06:58', 'SYSTEM', '2025-08-04 16:06:58');
INSERT INTO `content_interaction_accumulation` VALUES (423, 6, 1, 431, 1320, 'SYSTEM', '2025-08-04 16:06:58', 'SYSTEM', '2025-08-04 16:06:58');
INSERT INTO `content_interaction_accumulation` VALUES (424, 3, 1, 432, 1192, 'SYSTEM', '2025-08-04 16:06:58', 'SYSTEM', '2025-08-04 16:06:58');
INSERT INTO `content_interaction_accumulation` VALUES (425, 5, 1, 432, 18123, 'SYSTEM', '2025-08-04 16:06:58', 'SYSTEM', '2025-08-04 16:06:58');
INSERT INTO `content_interaction_accumulation` VALUES (426, 6, 1, 432, 18103, 'SYSTEM', '2025-08-04 16:06:58', 'SYSTEM', '2025-08-04 16:06:58');
INSERT INTO `content_interaction_accumulation` VALUES (427, 3, 1, 433, 12443, 'SYSTEM', '2025-08-04 16:06:58', 'SYSTEM', '2025-08-04 16:06:58');
INSERT INTO `content_interaction_accumulation` VALUES (428, 5, 1, 433, 10915, 'SYSTEM', '2025-08-04 16:06:58', 'SYSTEM', '2025-08-04 16:06:58');
INSERT INTO `content_interaction_accumulation` VALUES (429, 6, 1, 433, 5647, 'SYSTEM', '2025-08-04 16:06:58', 'SYSTEM', '2025-08-04 16:06:58');
INSERT INTO `content_interaction_accumulation` VALUES (430, 3, 1, 434, 13150, 'SYSTEM', '2025-08-04 16:06:59', 'SYSTEM', '2025-08-04 16:06:59');
INSERT INTO `content_interaction_accumulation` VALUES (431, 5, 1, 434, 8906, 'SYSTEM', '2025-08-04 16:06:59', 'SYSTEM', '2025-08-04 16:06:59');
INSERT INTO `content_interaction_accumulation` VALUES (432, 6, 1, 434, 2057, 'SYSTEM', '2025-08-04 16:06:59', 'SYSTEM', '2025-08-04 16:06:59');
INSERT INTO `content_interaction_accumulation` VALUES (433, 3, 1, 435, 10515, 'SYSTEM', '2025-08-04 16:06:59', 'SYSTEM', '2025-08-04 16:06:59');
INSERT INTO `content_interaction_accumulation` VALUES (434, 5, 1, 435, 17362, 'SYSTEM', '2025-08-04 16:06:59', 'SYSTEM', '2025-08-04 16:06:59');
INSERT INTO `content_interaction_accumulation` VALUES (435, 6, 1, 435, 15440, 'SYSTEM', '2025-08-04 16:06:59', 'SYSTEM', '2025-08-04 16:06:59');
INSERT INTO `content_interaction_accumulation` VALUES (436, 3, 1, 436, 16382, 'SYSTEM', '2025-08-04 16:06:59', 'SYSTEM', '2025-08-04 16:06:59');
INSERT INTO `content_interaction_accumulation` VALUES (437, 5, 1, 436, 17417, 'SYSTEM', '2025-08-04 16:06:59', 'SYSTEM', '2025-08-04 16:06:59');
INSERT INTO `content_interaction_accumulation` VALUES (438, 6, 1, 436, 3082, 'SYSTEM', '2025-08-04 16:06:59', 'SYSTEM', '2025-08-04 16:06:59');
INSERT INTO `content_interaction_accumulation` VALUES (439, 3, 1, 437, 1981, 'SYSTEM', '2025-08-04 16:06:59', 'SYSTEM', '2025-08-04 16:06:59');
INSERT INTO `content_interaction_accumulation` VALUES (440, 5, 1, 437, 12999, 'SYSTEM', '2025-08-04 16:06:59', 'SYSTEM', '2025-08-04 16:06:59');
INSERT INTO `content_interaction_accumulation` VALUES (441, 6, 1, 437, 7793, 'SYSTEM', '2025-08-04 16:06:59', 'SYSTEM', '2025-08-04 16:06:59');
INSERT INTO `content_interaction_accumulation` VALUES (442, 3, 1, 438, 5664, 'SYSTEM', '2025-08-04 16:06:59', 'SYSTEM', '2025-08-04 16:06:59');
INSERT INTO `content_interaction_accumulation` VALUES (443, 5, 1, 438, 10702, 'SYSTEM', '2025-08-04 16:06:59', 'SYSTEM', '2025-08-04 16:06:59');
INSERT INTO `content_interaction_accumulation` VALUES (444, 6, 1, 438, 16760, 'SYSTEM', '2025-08-04 16:06:59', 'SYSTEM', '2025-08-04 16:06:59');
INSERT INTO `content_interaction_accumulation` VALUES (445, 3, 1, 439, 16095, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (446, 5, 1, 439, 13053, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (447, 6, 1, 439, 15569, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (448, 3, 1, 440, 16020, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (449, 5, 1, 440, 13607, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (450, 6, 1, 440, 1778, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (451, 3, 1, 441, 5571, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (452, 5, 1, 441, 9470, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (453, 6, 1, 441, 18451, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (454, 3, 1, 442, 2924, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (455, 5, 1, 442, 7589, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (456, 6, 1, 442, 11423, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (457, 3, 1, 443, 17479, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (458, 5, 1, 443, 16552, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (459, 6, 1, 443, 2146, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (460, 3, 1, 444, 4723, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (461, 5, 1, 444, 7864, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (462, 6, 1, 444, 3466, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (463, 3, 1, 445, 3822, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (464, 5, 1, 445, 1317, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (465, 6, 1, 445, 6001, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (466, 3, 1, 446, 5267, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (467, 5, 1, 446, 9010, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (468, 6, 1, 446, 19471, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (469, 3, 1, 447, 15064, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (470, 5, 1, 447, 19273, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (471, 6, 1, 447, 13987, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (472, 3, 1, 448, 15705, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (473, 5, 1, 448, 6113, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (474, 6, 1, 448, 9104, 'SYSTEM', '2025-08-04 16:07:00', 'SYSTEM', '2025-08-04 16:07:00');
INSERT INTO `content_interaction_accumulation` VALUES (475, 3, 1, 449, 13816, 'SYSTEM', '2025-08-04 16:07:01', 'SYSTEM', '2025-08-04 16:07:01');
INSERT INTO `content_interaction_accumulation` VALUES (476, 5, 1, 449, 10648, 'SYSTEM', '2025-08-04 16:07:01', 'SYSTEM', '2025-08-04 16:07:01');
INSERT INTO `content_interaction_accumulation` VALUES (477, 6, 1, 449, 8035, 'SYSTEM', '2025-08-04 16:07:01', 'SYSTEM', '2025-08-04 16:07:01');
INSERT INTO `content_interaction_accumulation` VALUES (478, 3, 1, 450, 8593, 'SYSTEM', '2025-08-04 16:07:01', 'SYSTEM', '2025-08-04 16:07:01');
INSERT INTO `content_interaction_accumulation` VALUES (479, 5, 1, 450, 4590, 'SYSTEM', '2025-08-04 16:07:01', 'SYSTEM', '2025-08-04 16:07:01');
INSERT INTO `content_interaction_accumulation` VALUES (480, 6, 1, 450, 4264, 'SYSTEM', '2025-08-04 16:07:01', 'SYSTEM', '2025-08-04 16:07:01');
INSERT INTO `content_interaction_accumulation` VALUES (481, 3, 1, 451, 19824, 'SYSTEM', '2025-08-04 16:07:01', 'SYSTEM', '2025-08-04 16:07:01');
INSERT INTO `content_interaction_accumulation` VALUES (482, 5, 1, 451, 6023, 'SYSTEM', '2025-08-04 16:07:01', 'SYSTEM', '2025-08-04 16:07:01');
INSERT INTO `content_interaction_accumulation` VALUES (483, 6, 1, 451, 14764, 'SYSTEM', '2025-08-04 16:07:01', 'SYSTEM', '2025-08-04 16:07:01');
INSERT INTO `content_interaction_accumulation` VALUES (484, 3, 1, 452, 10237, 'SYSTEM', '2025-08-04 16:07:02', 'SYSTEM', '2025-08-04 16:07:02');
INSERT INTO `content_interaction_accumulation` VALUES (485, 5, 1, 452, 3826, 'SYSTEM', '2025-08-04 16:07:02', 'SYSTEM', '2025-08-04 16:07:02');
INSERT INTO `content_interaction_accumulation` VALUES (486, 6, 1, 452, 13792, 'SYSTEM', '2025-08-04 16:07:02', 'SYSTEM', '2025-08-04 16:07:02');
INSERT INTO `content_interaction_accumulation` VALUES (487, 3, 1, 453, 9980, 'SYSTEM', '2025-08-04 16:07:02', 'SYSTEM', '2025-08-04 16:07:02');
INSERT INTO `content_interaction_accumulation` VALUES (488, 5, 1, 453, 10751, 'SYSTEM', '2025-08-04 16:07:02', 'SYSTEM', '2025-08-04 16:07:02');
INSERT INTO `content_interaction_accumulation` VALUES (489, 6, 1, 453, 18463, 'SYSTEM', '2025-08-04 16:07:02', 'SYSTEM', '2025-08-04 16:07:02');
INSERT INTO `content_interaction_accumulation` VALUES (490, 3, 1, 454, 3149, 'SYSTEM', '2025-08-04 16:07:02', 'SYSTEM', '2025-08-04 16:07:02');
INSERT INTO `content_interaction_accumulation` VALUES (491, 5, 1, 454, 4764, 'SYSTEM', '2025-08-04 16:07:02', 'SYSTEM', '2025-08-04 16:07:02');
INSERT INTO `content_interaction_accumulation` VALUES (492, 6, 1, 454, 7401, 'SYSTEM', '2025-08-04 16:07:02', 'SYSTEM', '2025-08-04 16:07:02');
INSERT INTO `content_interaction_accumulation` VALUES (493, 3, 1, 455, 2723, 'SYSTEM', '2025-08-04 16:07:02', 'SYSTEM', '2025-08-04 16:07:02');
INSERT INTO `content_interaction_accumulation` VALUES (494, 5, 1, 455, 7864, 'SYSTEM', '2025-08-04 16:07:02', 'SYSTEM', '2025-08-04 16:07:02');
INSERT INTO `content_interaction_accumulation` VALUES (495, 6, 1, 455, 11756, 'SYSTEM', '2025-08-04 16:07:02', 'SYSTEM', '2025-08-04 16:07:02');
INSERT INTO `content_interaction_accumulation` VALUES (496, 3, 1, 456, 6620, 'SYSTEM', '2025-08-04 16:07:02', 'SYSTEM', '2025-08-04 16:07:02');
INSERT INTO `content_interaction_accumulation` VALUES (497, 5, 1, 456, 17345, 'SYSTEM', '2025-08-04 16:07:02', 'SYSTEM', '2025-08-04 16:07:02');
INSERT INTO `content_interaction_accumulation` VALUES (498, 6, 1, 456, 14485, 'SYSTEM', '2025-08-04 16:07:02', 'SYSTEM', '2025-08-04 16:07:02');
INSERT INTO `content_interaction_accumulation` VALUES (499, 3, 1, 457, 15833, 'SYSTEM', '2025-08-04 16:07:02', 'SYSTEM', '2025-08-04 16:07:02');
INSERT INTO `content_interaction_accumulation` VALUES (500, 5, 1, 457, 1696, 'SYSTEM', '2025-08-04 16:07:02', 'SYSTEM', '2025-08-04 16:07:02');
INSERT INTO `content_interaction_accumulation` VALUES (501, 6, 1, 457, 15041, 'SYSTEM', '2025-08-04 16:07:02', 'SYSTEM', '2025-08-04 16:07:02');
INSERT INTO `content_interaction_accumulation` VALUES (502, 3, 1, 458, 15227, 'SYSTEM', '2025-08-04 16:07:02', 'SYSTEM', '2025-08-04 16:07:02');
INSERT INTO `content_interaction_accumulation` VALUES (503, 5, 1, 458, 19469, 'SYSTEM', '2025-08-04 16:07:02', 'SYSTEM', '2025-08-04 16:07:02');
INSERT INTO `content_interaction_accumulation` VALUES (504, 6, 1, 458, 19275, 'SYSTEM', '2025-08-04 16:07:02', 'SYSTEM', '2025-08-04 16:07:02');
INSERT INTO `content_interaction_accumulation` VALUES (505, 3, 1, 459, 9432, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (506, 5, 1, 459, 9509, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (507, 6, 1, 459, 13008, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (508, 3, 1, 460, 10140, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (509, 5, 1, 460, 18500, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (510, 6, 1, 460, 5725, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (511, 3, 1, 461, 11264, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (512, 5, 1, 461, 17072, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (513, 6, 1, 461, 16352, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (514, 3, 1, 462, 18016, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (515, 5, 1, 462, 1827, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (516, 6, 1, 462, 5632, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (517, 3, 1, 463, 11918, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (518, 5, 1, 463, 15554, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (519, 6, 1, 463, 5903, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (520, 3, 1, 464, 5976, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (521, 5, 1, 464, 6889, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (522, 6, 1, 464, 11230, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (523, 3, 1, 465, 15177, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (524, 5, 1, 465, 15993, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (525, 6, 1, 465, 12707, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (526, 3, 1, 466, 3541, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (527, 5, 1, 466, 10307, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (528, 6, 1, 466, 18496, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (529, 3, 1, 467, 7791, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (530, 5, 1, 467, 1063, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (531, 6, 1, 467, 11023, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (532, 3, 1, 468, 9639, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (533, 5, 1, 468, 2392, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (534, 6, 1, 468, 1002, 'SYSTEM', '2025-08-04 16:07:03', 'SYSTEM', '2025-08-04 16:07:03');
INSERT INTO `content_interaction_accumulation` VALUES (535, 3, 1, 469, 9571, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (536, 5, 1, 469, 3277, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (537, 6, 1, 469, 14501, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (538, 3, 1, 470, 6049, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (539, 5, 1, 470, 2359, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (540, 6, 1, 470, 15650, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (541, 3, 1, 471, 19150, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (542, 5, 1, 471, 16100, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (543, 6, 1, 471, 19083, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (544, 3, 1, 472, 8522, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (545, 5, 1, 472, 17174, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (546, 6, 1, 472, 12303, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (547, 3, 1, 473, 4373, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (548, 5, 1, 473, 18738, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (549, 6, 1, 473, 12076, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (550, 3, 1, 474, 13421, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (551, 5, 1, 474, 14200, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (552, 6, 1, 474, 12083, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (553, 3, 1, 475, 6076, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (554, 5, 1, 475, 5582, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (555, 6, 1, 475, 15653, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (556, 3, 1, 476, 10386, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (557, 5, 1, 476, 7683, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (558, 6, 1, 476, 9218, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (559, 3, 1, 477, 13620, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (560, 5, 1, 477, 9890, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (561, 6, 1, 477, 15236, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (562, 3, 1, 478, 3252, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (563, 5, 1, 478, 7966, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (564, 6, 1, 478, 12164, 'SYSTEM', '2025-08-04 16:07:04', 'SYSTEM', '2025-08-04 16:07:04');
INSERT INTO `content_interaction_accumulation` VALUES (565, 3, 1, 479, 3846, 'SYSTEM', '2025-08-04 16:07:05', 'SYSTEM', '2025-08-04 16:07:05');
INSERT INTO `content_interaction_accumulation` VALUES (566, 5, 1, 479, 4504, 'SYSTEM', '2025-08-04 16:07:05', 'SYSTEM', '2025-08-04 16:07:05');
INSERT INTO `content_interaction_accumulation` VALUES (567, 6, 1, 479, 8388, 'SYSTEM', '2025-08-04 16:07:05', 'SYSTEM', '2025-08-04 16:07:05');
INSERT INTO `content_interaction_accumulation` VALUES (568, 3, 1, 480, 10101, 'SYSTEM', '2025-08-04 16:07:05', 'SYSTEM', '2025-08-04 16:07:05');
INSERT INTO `content_interaction_accumulation` VALUES (569, 5, 1, 480, 1927, 'SYSTEM', '2025-08-04 16:07:05', 'SYSTEM', '2025-08-04 16:07:05');
INSERT INTO `content_interaction_accumulation` VALUES (570, 6, 1, 480, 19754, 'SYSTEM', '2025-08-04 16:07:05', 'SYSTEM', '2025-08-04 16:07:05');
INSERT INTO `content_interaction_accumulation` VALUES (571, 3, 1, 481, 17905, 'SYSTEM', '2025-08-04 16:07:05', 'SYSTEM', '2025-08-04 16:07:05');
INSERT INTO `content_interaction_accumulation` VALUES (572, 5, 1, 481, 5987, 'SYSTEM', '2025-08-04 16:07:05', 'SYSTEM', '2025-08-04 16:07:05');
INSERT INTO `content_interaction_accumulation` VALUES (573, 6, 1, 481, 18180, 'SYSTEM', '2025-08-04 16:07:05', 'SYSTEM', '2025-08-04 16:07:05');
INSERT INTO `content_interaction_accumulation` VALUES (574, 3, 1, 482, 14966, 'SYSTEM', '2025-08-04 16:07:05', 'SYSTEM', '2025-08-04 16:07:05');
INSERT INTO `content_interaction_accumulation` VALUES (575, 5, 1, 482, 3189, 'SYSTEM', '2025-08-04 16:07:05', 'SYSTEM', '2025-08-04 16:07:05');
INSERT INTO `content_interaction_accumulation` VALUES (576, 6, 1, 482, 3090, 'SYSTEM', '2025-08-04 16:07:05', 'SYSTEM', '2025-08-04 16:07:05');
INSERT INTO `content_interaction_accumulation` VALUES (577, 3, 1, 483, 10370, 'SYSTEM', '2025-08-04 16:07:05', 'SYSTEM', '2025-08-04 16:07:05');
INSERT INTO `content_interaction_accumulation` VALUES (578, 5, 1, 483, 17880, 'SYSTEM', '2025-08-04 16:07:05', 'SYSTEM', '2025-08-04 16:07:05');
INSERT INTO `content_interaction_accumulation` VALUES (579, 6, 1, 483, 1435, 'SYSTEM', '2025-08-04 16:07:05', 'SYSTEM', '2025-08-04 16:07:05');
INSERT INTO `content_interaction_accumulation` VALUES (580, 3, 1, 484, 14822, 'SYSTEM', '2025-08-04 16:07:05', 'SYSTEM', '2025-08-04 16:07:05');
INSERT INTO `content_interaction_accumulation` VALUES (581, 5, 1, 484, 19575, 'SYSTEM', '2025-08-04 16:07:05', 'SYSTEM', '2025-08-04 16:07:05');
INSERT INTO `content_interaction_accumulation` VALUES (582, 6, 1, 484, 5377, 'SYSTEM', '2025-08-04 16:07:05', 'SYSTEM', '2025-08-04 16:07:05');
INSERT INTO `content_interaction_accumulation` VALUES (583, 3, 1, 485, 4361, 'SYSTEM', '2025-08-04 16:07:06', 'SYSTEM', '2025-08-04 16:07:06');
INSERT INTO `content_interaction_accumulation` VALUES (584, 5, 1, 485, 1193, 'SYSTEM', '2025-08-04 16:07:06', 'SYSTEM', '2025-08-04 16:07:06');
INSERT INTO `content_interaction_accumulation` VALUES (585, 6, 1, 485, 8734, 'SYSTEM', '2025-08-04 16:07:06', 'SYSTEM', '2025-08-04 16:07:06');
INSERT INTO `content_interaction_accumulation` VALUES (586, 3, 1, 486, 16948, 'SYSTEM', '2025-08-04 16:07:06', 'SYSTEM', '2025-08-04 16:07:06');
INSERT INTO `content_interaction_accumulation` VALUES (587, 5, 1, 486, 10160, 'SYSTEM', '2025-08-04 16:07:06', 'SYSTEM', '2025-08-04 16:07:06');
INSERT INTO `content_interaction_accumulation` VALUES (588, 6, 1, 486, 12107, 'SYSTEM', '2025-08-04 16:07:06', 'SYSTEM', '2025-08-04 16:07:06');
INSERT INTO `content_interaction_accumulation` VALUES (589, 3, 1, 487, 8386, 'SYSTEM', '2025-08-04 16:07:06', 'SYSTEM', '2025-08-04 16:07:06');
INSERT INTO `content_interaction_accumulation` VALUES (590, 5, 1, 487, 15981, 'SYSTEM', '2025-08-04 16:07:06', 'SYSTEM', '2025-08-04 16:07:06');
INSERT INTO `content_interaction_accumulation` VALUES (591, 6, 1, 487, 2780, 'SYSTEM', '2025-08-04 16:07:06', 'SYSTEM', '2025-08-04 16:07:06');
INSERT INTO `content_interaction_accumulation` VALUES (592, 3, 1, 488, 18811, 'SYSTEM', '2025-08-04 16:07:06', 'SYSTEM', '2025-08-04 16:07:06');
INSERT INTO `content_interaction_accumulation` VALUES (593, 5, 1, 488, 9306, 'SYSTEM', '2025-08-04 16:07:06', 'SYSTEM', '2025-08-04 16:07:06');
INSERT INTO `content_interaction_accumulation` VALUES (594, 6, 1, 488, 1682, 'SYSTEM', '2025-08-04 16:07:06', 'SYSTEM', '2025-08-04 16:07:06');
INSERT INTO `content_interaction_accumulation` VALUES (595, 3, 1, 489, 17814, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (596, 5, 1, 489, 7905, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (597, 6, 1, 489, 2485, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (598, 3, 1, 490, 13025, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (599, 5, 1, 490, 4549, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (600, 6, 1, 490, 1281, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (601, 3, 1, 491, 3430, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (602, 5, 1, 491, 2392, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (603, 6, 1, 491, 3306, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (604, 3, 1, 492, 18534, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (605, 5, 1, 492, 14170, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (606, 6, 1, 492, 7399, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (607, 3, 1, 493, 15153, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (608, 5, 1, 493, 19663, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (609, 6, 1, 493, 7568, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (610, 3, 1, 494, 18753, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (611, 5, 1, 494, 8343, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (612, 6, 1, 494, 11194, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (613, 3, 1, 495, 12658, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (614, 5, 1, 495, 2090, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (615, 6, 1, 495, 1490, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (616, 3, 1, 496, 8280, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (617, 5, 1, 496, 8595, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (618, 6, 1, 496, 15279, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (619, 3, 1, 497, 6417, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (620, 5, 1, 497, 14875, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (621, 6, 1, 497, 4645, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (622, 3, 1, 498, 17883, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (623, 5, 1, 498, 12969, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (624, 6, 1, 498, 17714, 'SYSTEM', '2025-08-04 16:07:07', 'SYSTEM', '2025-08-04 16:07:07');
INSERT INTO `content_interaction_accumulation` VALUES (625, 3, 1, 499, 19180, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (626, 5, 1, 499, 12314, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (627, 6, 1, 499, 13727, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (628, 3, 1, 500, 4506, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (629, 5, 1, 500, 9160, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (630, 6, 1, 500, 14521, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (631, 3, 1, 501, 1559, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (632, 5, 1, 501, 3587, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (633, 6, 1, 501, 2855, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (634, 3, 1, 502, 16101, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (635, 5, 1, 502, 3293, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (636, 6, 1, 502, 6167, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (637, 3, 1, 503, 11693, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (638, 5, 1, 503, 4265, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (639, 6, 1, 503, 6493, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (640, 3, 1, 504, 19305, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (641, 5, 1, 504, 3139, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (642, 6, 1, 504, 11255, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (643, 3, 1, 505, 5339, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (644, 5, 1, 505, 1827, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (645, 6, 1, 505, 6326, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (646, 3, 1, 506, 13569, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (647, 5, 1, 506, 6016, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (648, 6, 1, 506, 13614, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (649, 3, 1, 507, 17805, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (650, 5, 1, 507, 15738, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (651, 6, 1, 507, 2706, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (652, 3, 1, 508, 14898, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (653, 5, 1, 508, 11362, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (654, 6, 1, 508, 9288, 'SYSTEM', '2025-08-04 16:07:08', 'SYSTEM', '2025-08-04 16:07:08');
INSERT INTO `content_interaction_accumulation` VALUES (655, 3, 1, 509, 16366, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (656, 5, 1, 509, 15656, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (657, 6, 1, 509, 19158, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (658, 3, 1, 510, 13123, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (659, 5, 1, 510, 13874, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (660, 6, 1, 510, 2604, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (661, 3, 1, 511, 5659, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (662, 5, 1, 511, 5654, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (663, 6, 1, 511, 15887, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (664, 3, 1, 512, 12850, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (665, 5, 1, 512, 12931, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (666, 6, 1, 512, 18757, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (667, 3, 1, 513, 19841, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (668, 5, 1, 513, 2940, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (669, 6, 1, 513, 3664, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (670, 3, 1, 514, 2950, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (671, 5, 1, 514, 17960, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (672, 6, 1, 514, 9782, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (673, 3, 1, 515, 14147, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (674, 5, 1, 515, 9116, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (675, 6, 1, 515, 3951, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (676, 3, 1, 516, 19710, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (677, 5, 1, 516, 16883, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (678, 6, 1, 516, 2574, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (679, 3, 1, 517, 13157, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (680, 5, 1, 517, 19979, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (681, 6, 1, 517, 13054, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (682, 3, 1, 518, 18453, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (683, 5, 1, 518, 17429, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (684, 6, 1, 518, 7924, 'SYSTEM', '2025-08-04 16:07:10', 'SYSTEM', '2025-08-04 16:07:10');
INSERT INTO `content_interaction_accumulation` VALUES (685, 3, 1, 519, 15246, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (686, 5, 1, 519, 5966, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (687, 6, 1, 519, 18797, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (688, 3, 1, 520, 13182, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (689, 5, 1, 520, 8989, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (690, 6, 1, 520, 8542, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (691, 3, 1, 521, 17171, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (692, 5, 1, 521, 3569, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (693, 6, 1, 521, 6622, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (694, 3, 1, 522, 4575, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (695, 5, 1, 522, 3802, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (696, 6, 1, 522, 16539, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (697, 3, 1, 523, 4781, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (698, 5, 1, 523, 15113, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (699, 6, 1, 523, 6099, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (700, 3, 1, 524, 9500, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (701, 5, 1, 524, 15453, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (702, 6, 1, 524, 10824, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (703, 3, 1, 525, 12555, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (704, 5, 1, 525, 2830, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (705, 6, 1, 525, 11045, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (706, 3, 1, 526, 15272, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (707, 5, 1, 526, 4111, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (708, 6, 1, 526, 13964, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (709, 3, 1, 527, 12224, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (710, 5, 1, 527, 13777, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (711, 6, 1, 527, 18134, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (712, 3, 1, 528, 1619, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (713, 5, 1, 528, 19467, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (714, 6, 1, 528, 10018, 'SYSTEM', '2025-08-04 16:07:11', 'SYSTEM', '2025-08-04 16:07:11');
INSERT INTO `content_interaction_accumulation` VALUES (715, 3, 1, 529, 9410, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (716, 5, 1, 529, 10043, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (717, 6, 1, 529, 2284, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (718, 3, 1, 530, 13947, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (719, 5, 1, 530, 9136, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (720, 6, 1, 530, 18968, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (721, 3, 1, 531, 3945, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (722, 5, 1, 531, 3654, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (723, 6, 1, 531, 19783, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (724, 3, 1, 532, 2991, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (725, 5, 1, 532, 12212, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (726, 6, 1, 532, 11453, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (727, 3, 1, 533, 1970, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (728, 5, 1, 533, 2103, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (729, 6, 1, 533, 13189, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (730, 3, 1, 534, 3669, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (731, 5, 1, 534, 17088, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (732, 6, 1, 534, 16263, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (733, 3, 1, 535, 1728, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (734, 5, 1, 535, 16469, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (735, 6, 1, 535, 17315, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (736, 3, 1, 536, 14170, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (737, 5, 1, 536, 5126, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (738, 6, 1, 536, 17433, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (739, 3, 1, 537, 13026, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (740, 5, 1, 537, 16707, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (741, 6, 1, 537, 18882, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (742, 3, 1, 538, 3666, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (743, 5, 1, 538, 7137, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (744, 6, 1, 538, 9954, 'SYSTEM', '2025-08-04 16:07:13', 'SYSTEM', '2025-08-04 16:07:13');
INSERT INTO `content_interaction_accumulation` VALUES (745, 3, 1, 539, 9235, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (746, 5, 1, 539, 7749, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (747, 6, 1, 539, 11964, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (748, 3, 1, 540, 7233, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (749, 5, 1, 540, 8983, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (750, 6, 1, 540, 3106, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (751, 3, 1, 541, 8460, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (752, 5, 1, 541, 14221, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (753, 6, 1, 541, 10642, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (754, 3, 1, 542, 2126, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (755, 5, 1, 542, 17364, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (756, 6, 1, 542, 7918, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (757, 3, 1, 543, 3527, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (758, 5, 1, 543, 11762, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (759, 6, 1, 543, 6784, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (760, 3, 1, 544, 8044, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (761, 5, 1, 544, 18603, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (762, 6, 1, 544, 4307, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (763, 3, 1, 545, 4114, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (764, 5, 1, 545, 12343, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (765, 6, 1, 545, 14072, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (766, 3, 1, 546, 3644, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (767, 5, 1, 546, 3831, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (768, 6, 1, 546, 11275, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (769, 3, 1, 547, 7722, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (770, 5, 1, 547, 7400, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (771, 6, 1, 547, 11797, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (772, 3, 1, 548, 10662, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (773, 5, 1, 548, 5813, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (774, 6, 1, 548, 17213, 'SYSTEM', '2025-08-04 16:07:14', 'SYSTEM', '2025-08-04 16:07:14');
INSERT INTO `content_interaction_accumulation` VALUES (775, 3, 1, 549, 10036, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (776, 5, 1, 549, 18027, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (777, 6, 1, 549, 14006, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (778, 3, 1, 550, 13684, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (779, 5, 1, 550, 5692, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (780, 6, 1, 550, 4662, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (781, 3, 1, 551, 7337, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (782, 5, 1, 551, 18522, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (783, 6, 1, 551, 9947, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (784, 3, 1, 552, 16815, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (785, 5, 1, 552, 7610, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (786, 6, 1, 552, 8909, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (787, 3, 1, 553, 12056, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (788, 5, 1, 553, 1839, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (789, 6, 1, 553, 18772, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (790, 3, 1, 554, 3318, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (791, 5, 1, 554, 10457, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (792, 6, 1, 554, 8749, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (793, 3, 1, 555, 4899, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (794, 5, 1, 555, 14661, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (795, 6, 1, 555, 12967, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (796, 3, 1, 556, 18227, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (797, 5, 1, 556, 12327, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (798, 6, 1, 556, 8018, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (799, 3, 1, 557, 1530, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (800, 5, 1, 557, 17896, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (801, 6, 1, 557, 13148, 'SYSTEM', '2025-08-04 16:07:15', 'SYSTEM', '2025-08-04 16:07:15');
INSERT INTO `content_interaction_accumulation` VALUES (802, 3, 1, 558, 5129, 'SYSTEM', '2025-08-04 16:07:16', 'SYSTEM', '2025-08-04 16:07:16');
INSERT INTO `content_interaction_accumulation` VALUES (803, 5, 1, 558, 2791, 'SYSTEM', '2025-08-04 16:07:16', 'SYSTEM', '2025-08-04 16:07:16');
INSERT INTO `content_interaction_accumulation` VALUES (804, 6, 1, 558, 10522, 'SYSTEM', '2025-08-04 16:07:16', 'SYSTEM', '2025-08-04 16:07:16');
INSERT INTO `content_interaction_accumulation` VALUES (805, 3, 1, 559, 19947, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (806, 5, 1, 559, 8271, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (807, 6, 1, 559, 5847, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (808, 3, 1, 560, 16714, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (809, 5, 1, 560, 14013, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (810, 6, 1, 560, 11338, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (811, 3, 1, 561, 5050, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (812, 5, 1, 561, 4739, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (813, 6, 1, 561, 3563, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (814, 3, 1, 562, 11563, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (815, 5, 1, 562, 18528, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (816, 6, 1, 562, 17191, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (817, 3, 1, 563, 6625, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (818, 5, 1, 563, 18667, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (819, 6, 1, 563, 5230, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (820, 3, 1, 564, 9428, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (821, 5, 1, 564, 6169, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (822, 6, 1, 564, 9060, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (823, 3, 1, 565, 3860, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (824, 5, 1, 565, 13074, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (825, 6, 1, 565, 2894, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (826, 3, 1, 566, 9249, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (827, 5, 1, 566, 6841, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (828, 6, 1, 566, 9194, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (829, 3, 1, 567, 3704, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (830, 5, 1, 567, 7629, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (831, 6, 1, 567, 6699, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (832, 3, 1, 568, 14323, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (833, 5, 1, 568, 5401, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (834, 6, 1, 568, 11546, 'SYSTEM', '2025-08-04 16:07:49', 'SYSTEM', '2025-08-04 16:07:49');
INSERT INTO `content_interaction_accumulation` VALUES (835, 3, 1, 569, 4760, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (836, 5, 1, 569, 16062, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (837, 6, 1, 569, 3471, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (838, 3, 1, 570, 6746, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (839, 5, 1, 570, 3945, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (840, 6, 1, 570, 1681, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (841, 3, 1, 571, 2968, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (842, 5, 1, 571, 5202, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (843, 6, 1, 571, 16648, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (844, 3, 1, 572, 8911, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (845, 5, 1, 572, 8581, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (846, 6, 1, 572, 17745, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (847, 3, 1, 573, 19288, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (848, 5, 1, 573, 13995, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (849, 6, 1, 573, 4773, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (850, 3, 1, 574, 8978, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (851, 5, 1, 574, 13985, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (852, 6, 1, 574, 14026, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (853, 3, 1, 575, 4634, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (854, 5, 1, 575, 18584, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (855, 6, 1, 575, 3112, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (856, 3, 1, 576, 15671, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (857, 5, 1, 576, 5604, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (858, 6, 1, 576, 19353, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (859, 3, 1, 577, 17027, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (860, 5, 1, 577, 14497, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (861, 6, 1, 577, 15638, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (862, 3, 1, 578, 8110, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (863, 5, 1, 578, 10259, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (864, 6, 1, 578, 13408, 'SYSTEM', '2025-08-04 16:07:51', 'SYSTEM', '2025-08-04 16:07:51');
INSERT INTO `content_interaction_accumulation` VALUES (865, 3, 1, 579, 7616, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (866, 5, 1, 579, 4112, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (867, 6, 1, 579, 14711, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (868, 3, 1, 580, 7999, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (869, 5, 1, 580, 14879, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (870, 6, 1, 580, 5498, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (871, 3, 1, 581, 16028, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (872, 5, 1, 581, 3090, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (873, 6, 1, 581, 19696, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (874, 3, 1, 582, 8004, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (875, 5, 1, 582, 2310, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (876, 6, 1, 582, 5214, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (877, 3, 1, 583, 13241, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (878, 5, 1, 583, 16317, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (879, 6, 1, 583, 7619, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (880, 3, 1, 584, 16476, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (881, 5, 1, 584, 17280, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (882, 6, 1, 584, 14268, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (883, 3, 1, 585, 16392, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (884, 5, 1, 585, 12882, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (885, 6, 1, 585, 6185, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (886, 3, 1, 586, 10427, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (887, 5, 1, 586, 4919, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (888, 6, 1, 586, 10073, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (889, 3, 1, 587, 2253, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (890, 5, 1, 587, 8643, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (891, 6, 1, 587, 13338, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (892, 3, 1, 588, 12280, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (893, 5, 1, 588, 16389, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (894, 6, 1, 588, 18668, 'SYSTEM', '2025-08-04 16:07:52', 'SYSTEM', '2025-08-04 16:07:52');
INSERT INTO `content_interaction_accumulation` VALUES (895, 3, 1, 589, 11298, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (896, 5, 1, 589, 8332, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (897, 6, 1, 589, 3252, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (898, 3, 1, 590, 13142, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (899, 5, 1, 590, 12523, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (900, 6, 1, 590, 5609, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (901, 3, 1, 591, 17757, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (902, 5, 1, 591, 4003, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (903, 6, 1, 591, 13828, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (904, 3, 1, 592, 6052, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (905, 5, 1, 592, 13511, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (906, 6, 1, 592, 13703, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (907, 3, 1, 593, 17887, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (908, 5, 1, 593, 17573, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (909, 6, 1, 593, 6302, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (910, 3, 1, 594, 3614, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (911, 5, 1, 594, 5120, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (912, 6, 1, 594, 13627, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (913, 3, 1, 595, 1079, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (914, 5, 1, 595, 14504, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (915, 6, 1, 595, 15485, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (916, 3, 1, 596, 1136, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (917, 5, 1, 596, 13114, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (918, 6, 1, 596, 17106, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (919, 3, 1, 597, 3660, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (920, 5, 1, 597, 6522, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (921, 6, 1, 597, 17039, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (922, 3, 1, 598, 19558, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (923, 5, 1, 598, 7296, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (924, 6, 1, 598, 19261, 'SYSTEM', '2025-08-04 16:07:53', 'SYSTEM', '2025-08-04 16:07:53');
INSERT INTO `content_interaction_accumulation` VALUES (925, 3, 1, 599, 11399, 'SYSTEM', '2025-08-04 16:07:54', 'SYSTEM', '2025-08-04 16:07:54');
INSERT INTO `content_interaction_accumulation` VALUES (926, 5, 1, 599, 13791, 'SYSTEM', '2025-08-04 16:07:54', 'SYSTEM', '2025-08-04 16:07:54');
INSERT INTO `content_interaction_accumulation` VALUES (927, 6, 1, 599, 16340, 'SYSTEM', '2025-08-04 16:07:54', 'SYSTEM', '2025-08-04 16:07:54');
INSERT INTO `content_interaction_accumulation` VALUES (928, 3, 1, 600, 1123, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (929, 5, 1, 600, 14051, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (930, 6, 1, 600, 12865, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (931, 3, 1, 601, 8326, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (932, 5, 1, 601, 15085, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (933, 6, 1, 601, 16330, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (934, 3, 1, 602, 18760, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (935, 5, 1, 602, 5583, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (936, 6, 1, 602, 8836, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (937, 3, 1, 603, 18369, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (938, 5, 1, 603, 13246, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (939, 6, 1, 603, 14803, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (940, 3, 1, 604, 7851, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (941, 5, 1, 604, 16165, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (942, 6, 1, 604, 14156, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (943, 3, 1, 605, 4251, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (944, 5, 1, 605, 5079, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (945, 6, 1, 605, 11142, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (946, 3, 1, 606, 18634, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (947, 5, 1, 606, 13013, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (948, 6, 1, 606, 17563, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (949, 3, 1, 607, 4899, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (950, 5, 1, 607, 4893, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (951, 6, 1, 607, 13230, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (952, 3, 1, 608, 16554, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (953, 5, 1, 608, 19638, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (954, 6, 1, 608, 1429, 'SYSTEM', '2025-08-04 16:07:55', 'SYSTEM', '2025-08-04 16:07:55');
INSERT INTO `content_interaction_accumulation` VALUES (955, 3, 1, 609, 8063, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (956, 5, 1, 609, 14150, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (957, 6, 1, 609, 11397, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (958, 3, 1, 610, 13736, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (959, 5, 1, 610, 5376, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (960, 6, 1, 610, 16928, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (961, 3, 1, 611, 11727, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (962, 5, 1, 611, 7244, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (963, 6, 1, 611, 18613, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (964, 3, 1, 612, 1648, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (965, 5, 1, 612, 5750, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (966, 6, 1, 612, 3451, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (967, 3, 1, 613, 3240, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (968, 5, 1, 613, 9824, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (969, 6, 1, 613, 8737, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (970, 3, 1, 614, 15075, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (971, 5, 1, 614, 7377, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (972, 6, 1, 614, 10651, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (973, 3, 1, 615, 5348, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (974, 5, 1, 615, 13486, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (975, 6, 1, 615, 12728, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (976, 3, 1, 616, 17747, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (977, 5, 1, 616, 19543, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (978, 6, 1, 616, 19775, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (979, 3, 1, 617, 6932, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (980, 5, 1, 617, 5050, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (981, 6, 1, 617, 4880, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (982, 3, 1, 618, 5361, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (983, 5, 1, 618, 17623, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (984, 6, 1, 618, 4323, 'SYSTEM', '2025-08-04 16:07:56', 'SYSTEM', '2025-08-04 16:07:56');
INSERT INTO `content_interaction_accumulation` VALUES (985, 3, 1, 619, 11473, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (986, 5, 1, 619, 4368, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (987, 6, 1, 619, 2121, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (988, 3, 1, 620, 17830, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (989, 5, 1, 620, 5249, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (990, 6, 1, 620, 17740, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (991, 3, 1, 621, 16641, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (992, 5, 1, 621, 9024, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (993, 6, 1, 621, 5422, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (994, 3, 1, 622, 6538, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (995, 5, 1, 622, 9110, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (996, 6, 1, 622, 18419, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (997, 3, 1, 623, 3143, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (998, 5, 1, 623, 1856, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (999, 6, 1, 623, 13430, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (1000, 3, 1, 624, 18219, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (1001, 5, 1, 624, 7242, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (1002, 6, 1, 624, 13369, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (1003, 3, 1, 625, 16869, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (1004, 5, 1, 625, 6864, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (1005, 6, 1, 625, 16721, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (1006, 3, 1, 626, 4776, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (1007, 5, 1, 626, 7681, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (1008, 6, 1, 626, 13217, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (1009, 3, 1, 627, 5287, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (1010, 5, 1, 627, 12158, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (1011, 6, 1, 627, 12648, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (1012, 3, 1, 628, 17845, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (1013, 5, 1, 628, 18666, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (1014, 6, 1, 628, 4657, 'SYSTEM', '2025-08-04 16:07:57', 'SYSTEM', '2025-08-04 16:07:57');
INSERT INTO `content_interaction_accumulation` VALUES (1015, 3, 1, 629, 17819, 'SYSTEM', '2025-11-29 13:03:28', 'SYSTEM', '2025-11-29 13:03:28');
INSERT INTO `content_interaction_accumulation` VALUES (1016, 5, 1, 629, 4734, 'SYSTEM', '2025-11-29 13:03:28', 'SYSTEM', '2025-11-29 13:03:28');
INSERT INTO `content_interaction_accumulation` VALUES (1017, 6, 1, 629, 8012, 'SYSTEM', '2025-11-29 13:03:28', 'SYSTEM', '2025-11-29 13:03:28');
INSERT INTO `content_interaction_accumulation` VALUES (1018, 3, 1, 630, 7542, 'SYSTEM', '2025-11-29 13:41:41', 'SYSTEM', '2025-11-29 13:41:41');
INSERT INTO `content_interaction_accumulation` VALUES (1019, 5, 1, 630, 18333, 'SYSTEM', '2025-11-29 13:41:41', 'SYSTEM', '2025-11-29 13:41:41');
INSERT INTO `content_interaction_accumulation` VALUES (1020, 6, 1, 630, 19680, 'SYSTEM', '2025-11-29 13:41:41', 'SYSTEM', '2025-11-29 13:41:41');
INSERT INTO `content_interaction_accumulation` VALUES (1021, 3, 1, 631, 4284, 'SYSTEM', '2025-11-29 14:49:36', 'SYSTEM', '2025-11-29 14:49:36');
INSERT INTO `content_interaction_accumulation` VALUES (1022, 5, 1, 631, 14978, 'SYSTEM', '2025-11-29 14:49:36', 'SYSTEM', '2025-11-29 14:49:36');
INSERT INTO `content_interaction_accumulation` VALUES (1023, 6, 1, 631, 12938, 'SYSTEM', '2025-11-29 14:49:36', 'SYSTEM', '2025-11-29 14:49:36');

-- ----------------------------
-- Table structure for content_tag
-- ----------------------------
DROP TABLE IF EXISTS `content_tag`;
CREATE TABLE `content_tag`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` tinyint UNSIGNED NOT NULL COMMENT '标签类型',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标签名称',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标签编码',
  `parent_id` bigint UNSIGNED NOT NULL COMMENT '标签父id',
  `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '我是一个小标签' COMMENT '描述',
  `icon_reference` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签图标',
  `is_enabled` bit(1) NOT NULL COMMENT '是否可用',
  `display_order` int UNSIGNED NOT NULL COMMENT '排序',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_content_tag_type_code`(`type` ASC, `code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 391 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标签表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of content_tag
-- ----------------------------
INSERT INTO `content_tag` VALUES (388, 1, '诗词', '7b7d6b41-a560-4136-a909-0eec54793bd4', 0, NULL, NULL, b'1', 1, 'SYSTEM', '2025-11-29 10:07:37.232', 'SYSTEM', '2025-11-29 10:07:37.232');
INSERT INTO `content_tag` VALUES (389, 1, '唐诗', '0b612f68-0a37-4f5e-ba64-bbbfa91036b2', 388, NULL, NULL, b'1', 1, 'SYSTEM', '2025-11-29 10:22:43.984', 'SYSTEM', '2025-11-29 10:22:43.984');
INSERT INTO `content_tag` VALUES (390, 9, '古典', '88444100-4114-435b-a01d-d2b567df48c3', 0, NULL, NULL, b'1', 1, 'SYSTEM', '2025-11-29 10:24:15.833', 'SYSTEM', '2025-11-29 10:24:15.833');

-- ----------------------------
-- Table structure for content_video
-- ----------------------------
DROP TABLE IF EXISTS `content_video`;
CREATE TABLE `content_video`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `vertical_cover_image_reference` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `duration` int NOT NULL COMMENT '时长',
  `width` int NOT NULL COMMENT '宽',
  `height` int NOT NULL COMMENT '高',
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 622 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '内容视频表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of content_video
-- ----------------------------

-- ----------------------------
-- Table structure for file_metadata
-- ----------------------------
DROP TABLE IF EXISTS `file_metadata`;
CREATE TABLE `file_metadata`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_date` datetime NULL DEFAULT NULL,
  `updated_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `updated_date` datetime NULL DEFAULT NULL,
  `original_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `minio_object_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `file_size` bigint NULL DEFAULT NULL,
  `content_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_metadata
-- ----------------------------

-- ----------------------------
-- Table structure for poetry_answer_records
-- ----------------------------
DROP TABLE IF EXISTS `poetry_answer_records`;
CREATE TABLE `poetry_answer_records`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_id` bigint UNSIGNED NOT NULL COMMENT '用户ID（逻辑关联）',
  `quiz_id` bigint UNSIGNED NOT NULL COMMENT '题目ID（逻辑关联）',
  `sub_category_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '子分类id',
  `content_id` bigint UNSIGNED NOT NULL COMMENT '学习内容ID（逻辑关联）',
  `user_answer` json NOT NULL COMMENT '用户提交的答案',
  `is_correct` bit(1) NOT NULL COMMENT '是否正确（0/1）',
  `hints_used` tinyint NOT NULL DEFAULT 0 COMMENT '使用提示的次数',
  `session_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '练习会话ID',
  `response_time` int NULL DEFAULT NULL COMMENT '答题耗时（毫秒）',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_content`(`user_id` ASC, `content_id` ASC) USING BTREE,
  INDEX `idx_user_quiz`(`user_id` ASC, `quiz_id` ASC) USING BTREE,
  INDEX `idx_session`(`session_id` ASC) USING BTREE,
  INDEX `idx_created_date`(`created_date` ASC) USING BTREE,
  INDEX `idx_content_created`(`content_id` ASC, `created_date` ASC) USING BTREE,
  INDEX `idx_user_created`(`user_id` ASC, `created_date` ASC) USING BTREE,
  INDEX `idx_quiz_created`(`quiz_id` ASC, `created_date` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户答题记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of poetry_answer_records
-- ----------------------------

-- ----------------------------
-- Table structure for poetry_category
-- ----------------------------
DROP TABLE IF EXISTS `poetry_category`;
CREATE TABLE `poetry_category`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '一级分类诗词/文言文/阅读理解 二级分类唐诗/宋词/虚词/实词等',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类编码',
  `icon` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类图标',
  `sort_order` int UNSIGNED NULL DEFAULT 1,
  `parent_id` bigint UNSIGNED NOT NULL COMMENT '父分类ID',
  `is_enabled` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否有效',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '内容分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of poetry_category
-- ----------------------------
INSERT INTO `poetry_category` VALUES (1, '诗词', 'Poetry', '579ca7be-3f79-465b-bdb9-2dec666b2e3a', 1, 0, b'1', 'SYSTEM', '2025-10-24 11:05:55.000', 'SYSTEM', '2025-12-17 14:58:10.876');
INSERT INTO `poetry_category` VALUES (2, '文言文', 'Classical-Chinese', '579ca7be-3f79-465b-bdb9-2dec666b2e3a', 2, 0, b'1', 'SYSTEM', '2025-10-24 11:05:55.000', 'SYSTEM', '2025-11-08 12:52:59.408');
INSERT INTO `poetry_category` VALUES (3, '阅读理解', 'ReadingComprehension', '579ca7be-3f79-465b-bdb9-2dec666b2e3a', 3, 0, b'1', 'SYSTEM', '2025-10-24 11:05:55.000', 'SYSTEM', '2025-11-08 12:55:25.382');
INSERT INTO `poetry_category` VALUES (4, '唐诗', 'TangPoems', '579ca7be-3f79-465b-bdb9-2dec666b2e3a', 3, 1, b'1', 'SYSTEM', '2025-10-24 11:05:55.000', 'SYSTEM', '2025-11-08 12:56:16.943');
INSERT INTO `poetry_category` VALUES (5, '宋词', 'Song Dynasty Ci Poetry', '579ca7be-3f79-465b-bdb9-2dec666b2e3a', 3, 1, b'1', 'SYSTEM', '2025-10-24 11:05:55.000', 'SYSTEM', '2025-11-08 12:58:11.301');
INSERT INTO `poetry_category` VALUES (6, '虚词', 'FunctionWord', '579ca7be-3f79-465b-bdb9-2dec666b2e3a', 3, 2, b'1', 'SYSTEM', '2025-10-24 11:05:55.000', 'SYSTEM', '2025-11-08 12:59:29.418');
INSERT INTO `poetry_category` VALUES (7, '实词', 'ContentWords', '579ca7be-3f79-465b-bdb9-2dec666b2e3a', 3, 2, b'1', 'SYSTEM', '2025-10-24 11:05:55.000', 'SYSTEM', '2025-11-08 13:01:07.008');
INSERT INTO `poetry_category` VALUES (8, '记叙文', 'Narration', '579ca7be-3f79-465b-bdb9-2dec666b2e3a', 3, 3, b'1', 'SYSTEM', '2025-10-24 11:05:55.000', 'SYSTEM', '2025-11-08 13:09:21.709');
INSERT INTO `poetry_category` VALUES (9, '说明文', 'Exposition', '579ca7be-3f79-465b-bdb9-2dec666b2e3a', 3, 3, b'1', 'SYSTEM', '2025-10-24 11:05:55.000', 'SYSTEM', '2025-11-08 13:10:59.604');
INSERT INTO `poetry_category` VALUES (10, '议论文', 'Argumentation', '579ca7be-3f79-465b-bdb9-2dec666b2e3a', 3, 3, b'1', 'SYSTEM', '2025-10-24 11:05:55.000', 'SYSTEM', '2025-11-08 13:13:07.874');
INSERT INTO `poetry_category` VALUES (11, '诗歌', 'Poems ', '579ca7be-3f79-465b-bdb9-2dec666b2e3a', 3, 3, b'1', 'SYSTEM', '2025-10-24 11:05:55.000', 'SYSTEM', '2025-11-08 13:33:56.594');

-- ----------------------------
-- Table structure for poetry_daily_study_plan
-- ----------------------------
DROP TABLE IF EXISTS `poetry_daily_study_plan`;
CREATE TABLE `poetry_daily_study_plan`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint UNSIGNED NOT NULL COMMENT '用户id',
  `sub_category_id` bigint UNSIGNED NOT NULL COMMENT '子分类ID',
  `plan_date` date NOT NULL COMMENT '计划日期',
  `new_item_ids` json NULL COMMENT '新学内容ID列表',
  `review_item_ids` json NULL COMMENT '复习内容ID列表',
  `completed_new_items` json NULL COMMENT '已完成新学内容',
  `completed_review_items` json NULL COMMENT '已完成复习内容',
  `completion_rate` decimal(5, 2) NULL DEFAULT 0.00 COMMENT '完成率',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_sub_category_id_date`(`user_id` ASC, `sub_category_id` ASC, `plan_date` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '每日学习计划表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of poetry_daily_study_plan
-- ----------------------------

-- ----------------------------
-- Table structure for poetry_grade
-- ----------------------------
DROP TABLE IF EXISTS `poetry_grade`;
CREATE TABLE `poetry_grade`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '年级编码',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '小学/初中/高中',
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '年级表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of poetry_grade
-- ----------------------------
INSERT INTO `poetry_grade` VALUES (1, 'Primary school', '小学', '小学，初等学校', 'SYSTEM', '2025-10-24 10:30:56.000', 'SYSTEM', '2025-10-24 10:31:00.000');
INSERT INTO `poetry_grade` VALUES (2, 'Middle school', '中学', '中学', 'SYSTEM', '2025-10-24 10:30:56.000', 'SYSTEM', '2025-10-24 10:31:00.000');
INSERT INTO `poetry_grade` VALUES (3, 'High school', '高中', '高级中学', 'SYSTEM', '2025-10-24 10:30:56.000', 'SYSTEM', '2025-10-24 10:31:00.000');

-- ----------------------------
-- Table structure for poetry_grade_poetry_category
-- ----------------------------
DROP TABLE IF EXISTS `poetry_grade_poetry_category`;
CREATE TABLE `poetry_grade_poetry_category`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `grade_id` bigint UNSIGNED NOT NULL COMMENT '图书ID',
  `category_id` bigint UNSIGNED NOT NULL COMMENT '标签ID',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '年级分类关联关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of poetry_grade_poetry_category
-- ----------------------------
INSERT INTO `poetry_grade_poetry_category` VALUES (1, 1, 1, 'SYSTEM', '2025-10-24 23:01:01.000', 'SYSTEM', '2025-10-24 23:01:03.000');
INSERT INTO `poetry_grade_poetry_category` VALUES (2, 2, 1, 'SYSTEM', '2025-10-24 23:01:40.620', 'SYSTEM', '2025-10-24 23:01:40.620');
INSERT INTO `poetry_grade_poetry_category` VALUES (3, 2, 2, 'SYSTEM', '2025-10-24 23:01:45.778', 'SYSTEM', '2025-10-24 23:01:45.778');
INSERT INTO `poetry_grade_poetry_category` VALUES (4, 2, 3, 'SYSTEM', '2025-10-24 23:01:01.000', 'SYSTEM', '2025-10-24 23:01:03.000');
INSERT INTO `poetry_grade_poetry_category` VALUES (5, 3, 1, 'SYSTEM', '2025-10-24 23:01:01.000', 'SYSTEM', '2025-10-24 23:01:03.000');
INSERT INTO `poetry_grade_poetry_category` VALUES (6, 3, 2, 'SYSTEM', '2025-10-24 23:01:01.000', 'SYSTEM', '2025-10-24 23:01:03.000');
INSERT INTO `poetry_grade_poetry_category` VALUES (7, 3, 3, 'SYSTEM', '2025-10-24 23:02:49.000', 'SYSTEM', '2025-10-24 23:01:01.000');

-- ----------------------------
-- Table structure for poetry_learning_content
-- ----------------------------
DROP TABLE IF EXISTS `poetry_learning_content`;
CREATE TABLE `poetry_learning_content`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题，如《静夜思》',
  `subtitle` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '副标题，如\"李白\"',
  `content_type` tinyint UNSIGNED NOT NULL COMMENT '内容类型 1:诗词 2:文言文 3:知识点',
  `grade_id` bigint UNSIGNED NOT NULL COMMENT '年级ID',
  `category_id` bigint UNSIGNED NOT NULL COMMENT '分类ID',
  `sub_category_id` bigint UNSIGNED NOT NULL,
  `difficulty` tinyint UNSIGNED NOT NULL COMMENT '难度 1:简单 2:中等 3:困难',
  `original_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '原文',
  `author` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作者',
  `dynasty` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '朝代',
  `background` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创作背景',
  `explanation` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '知识点解释',
  `usage_examples` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用法例句',
  `annotations` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '注释数组',
  `translation` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '翻译',
  `appreciation` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '赏析',
  `audio_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '朗诵音频',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配图URL',
  `view_count` int UNSIGNED NULL DEFAULT 0 COMMENT '学习次数',
  `is_enabled` bit(1) NULL DEFAULT b'1' COMMENT '是否有效',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 310 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '学习内容主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of poetry_learning_content
-- ----------------------------
INSERT INTO `poetry_learning_content` VALUES (1, '水调歌头', '明月几时有', 1, 1, 1, 5, 2, '明月几时有？把酒问青天。不知天上宫阙，今夕是何年。我欲乘风归去，又恐琼楼玉宇，高处不胜寒。起舞弄清影，何似在人间。转朱阁，低绮户，照无眠。不应有恨，何事长向别时圆？人有悲欢离合，月有阴晴圆缺，此事古难全。但愿人长久，千里共婵娟。', '苏轼', '宋代', '写于中秋之夜，表达对弟弟苏辙的思念之情', '宋词豪放派代表作，以月为意象抒发情感', '明月几时有，把酒问青天', '[\"明月\",\"宫阙\",\"青天\"]', '明月从什么时候才开始出现的？我端起酒杯遥问苍天。', '此词通篇咏月，却处处关合人事，表现了作者对人生的思考。', '/audio/shuidiaogetou.mp3', '/image/songci1.jpg', 156, b'1', 'admin', '2025-10-24 11:26:12.221', 'admin', '2025-10-24 11:26:12.221');
INSERT INTO `poetry_learning_content` VALUES (2, '念奴娇·赤壁怀古', '大江东去', 1, 2, 1, 5, 3, '大江东去，浪淘尽，千古风流人物。故垒西边，人道是，三国周郎赤壁。乱石穿空，惊涛拍岸，卷起千堆雪。江山如画，一时多少豪杰。遥想公瑾当年，小乔初嫁了，雄姿英发。羽扇纶巾，谈笑间，樯橹灰飞烟灭。故国神游，多情应笑我，早生华发。人生如梦，一尊还酹江月。', '苏轼', '宋代', '苏轼因乌台诗案被贬黄州时所作', '通过怀古抒发对英雄人物的向往和自己壮志未酬的感慨', '大江东去，浪淘尽，千古风流人物', '[\"赤壁\",\"周郎\",\"三国\"]', '大江浩浩荡荡向东流去，滔滔巨浪淘尽千古英雄人物。', '全词借古抒怀，雄浑苍凉，大气磅礴，笔力遒劲。', '/audio/niannujiao.mp3', '/image/songci2.jpg', 203, b'1', 'admin', '2025-10-24 11:26:12.221', 'admin', '2025-10-24 11:26:12.221');
INSERT INTO `poetry_learning_content` VALUES (3, '声声慢', '寻寻觅觅', 1, 2, 1, 5, 2, '寻寻觅觅，冷冷清清，凄凄惨惨戚戚。乍暖还寒时候，最难将息。三杯两盏淡酒，怎敌他、晚来风急？雁过也，正伤心，却是旧时相识。满地黄花堆积。憔悴损，如今有谁堪摘？守着窗儿，独自怎生得黑？梧桐更兼细雨，到黄昏、点点滴滴。这次第，怎一个愁字了得！', '李清照', '宋代', '李清照晚年作品，反映国破家亡的悲惨境遇', '运用叠字手法，层层递进地表达愁苦心情', '寻寻觅觅，冷冷清清，凄凄惨惨戚戚', '[\"黄花\",\"梧桐\",\"细雨\"]', '苦苦地寻寻觅觅，却只见冷冷清清，怎不让人凄惨悲戚。', '此词通过描写残秋所见所感，抒发自己孤寂落寞、悲凉愁苦的心绪。', '/audio/shengshengman.mp3', '/image/songci3.jpg', 178, b'1', 'admin', '2025-10-24 11:26:12.221', 'admin', '2025-10-24 11:26:12.221');
INSERT INTO `poetry_learning_content` VALUES (4, '雨霖铃', '寒蝉凄切', 1, 3, 1, 5, 3, '寒蝉凄切，对长亭晚，骤雨初歇。都门帐饮无绪，留恋处，兰舟催发。执手相看泪眼，竟无语凝噎。念去去，千里烟波，暮霭沉沉楚天阔。多情自古伤离别，更那堪，冷落清秋节！今宵酒醒何处？杨柳岸，晓风残月。此去经年，应是良辰好景虚设。便纵有千种风情，更与何人说？', '柳永', '宋代', '写离京南下时与恋人的惜别之情', '婉约派代表作，以秋景衬托离别之痛', '多情自古伤离别，更那堪冷落清秋节', '[\"长亭\",\"兰舟\",\"杨柳\"]', '秋后的蝉叫得凄凉而急促，面对着长亭，正是傍晚时分，一阵急雨刚停住。', '全词情景交融，委婉多致，表现了作者对恋人的真挚感情和离别的痛苦。', '/audio/yulinling.mp3', '/image/songci4.jpg', 192, b'1', 'admin', '2025-10-24 11:26:12.221', 'admin', '2025-10-24 11:26:12.221');
INSERT INTO `poetry_learning_content` VALUES (5, '满江红', '怒发冲冠', 1, 3, 1, 5, 3, '怒发冲冠，凭栏处、潇潇雨歇。抬望眼、仰天长啸，壮怀激烈。三十功名尘与土，八千里路云和月。莫等闲、白了少年头，空悲切。靖康耻，犹未雪。臣子恨，何时灭！驾长车，踏破贺兰山缺。壮志饥餐胡虏肉，笑谈渴饮匈奴血。待从头、收拾旧山河，朝天阙。', '岳飞', '宋代', '表达抗金救国的坚定意志和必胜信念', '豪放派爱国词作，气势磅礴，慷慨激昂', '莫等闲、白了少年头，空悲切', '[\"靖康\",\"匈奴\",\"天阙\"]', '我怒发冲冠，独自登高凭栏，骤急的风雨刚刚停歇。', '此词代表了岳飞精忠报国的英雄之志，表现出一种浩然正气、英雄气质。', '/audio/manjianghong.mp3', '/image/songci5.jpg', 245, b'1', 'admin', '2025-10-24 11:26:12.221', 'admin', '2025-10-24 11:26:12.221');
INSERT INTO `poetry_learning_content` VALUES (6, '青玉案·元夕', '东风夜放花千树', 1, 2, 1, 5, 2, '东风夜放花千树。更吹落、星如雨。宝马雕车香满路。凤箫声动，玉壶光转，一夜鱼龙舞。蛾儿雪柳黄金缕，笑语盈盈暗香去。众里寻他千百度，蓦然回首，那人却在，灯火阑珊处。', '辛弃疾', '宋代', '描写元宵节的热闹场景和寻找意中人的心境', '以元宵佳节为背景，寄托理想追求', '众里寻他千百度，蓦然回首，那人却在，灯火阑珊处', '[\"元夕\",\"宝马\",\"雕车\"]', '东风吹开了元宵夜的火树银花，又吹得烟火纷纷，乱落如雨。', '此词从极力渲染元宵节绚丽多彩的热闹场面入手，反衬出一个孤高淡泊的形象。', '/audio/qingyuan.mp3', '/image/songci6.jpg', 167, b'1', 'admin', '2025-10-24 11:26:12.221', 'admin', '2025-10-24 11:26:12.221');
INSERT INTO `poetry_learning_content` VALUES (7, '蝶恋花', '庭院深深深几许', 1, 2, 1, 5, 2, '庭院深深深几许，杨柳堆烟，帘幕无重数。玉勒雕鞍游冶处，楼高不见章台路。雨横风狂三月暮，门掩黄昏，无计留春住。泪眼问花花不语，乱红飞过秋千去。', '欧阳修', '宋代', '写深闺少妇的春愁和离恨', '婉约词风，层层深入地刻画人物心理', '泪眼问花花不语，乱红飞过秋千去', '[\"玉勒\",\"游冶\",\"章台\"]', '庭院深深，不知有多深？杨柳依依，飞扬起片片烟雾，一重重帘幕不知有多少层。', '全词写景状物，疏俊委曲，虚实相融，用语自然，辞意深婉。', '/audio/dielianhua.mp3', '/image/songci7.jpg', 134, b'1', 'admin', '2025-10-24 11:26:12.221', 'admin', '2025-10-24 11:26:12.221');
INSERT INTO `poetry_learning_content` VALUES (8, '虞美人', '春花秋月何时了', 1, 3, 1, 5, 3, '春花秋月何时了？往事知多少。小楼昨夜又东风，故国不堪回首月明中。雕栏玉砌应犹在，只是朱颜改。问君能有几多愁？恰似一江春水向东流。', '李煜', '宋代', '李煜被俘后在汴京所作，表达亡国之痛', '以自然永恒与人生无常的对比，抒发深沉的故国之思', '问君能有几多愁？恰似一江春水向东流', '[\"故国\",\"雕栏\",\"朱颜\"]', '这年的时光什么时候才能了结，往事知道有多少！', '此词通过今昔交错对比，表现了一个亡国之君的无穷哀怨。', '/audio/yumeiren.mp3', '/image/songci8.jpg', 189, b'1', 'admin', '2025-10-24 11:26:12.221', 'admin', '2025-10-24 11:26:12.221');
INSERT INTO `poetry_learning_content` VALUES (9, '鹧鸪天', '彩袖殷勤捧玉钟', 1, 2, 1, 5, 2, '彩袖殷勤捧玉钟，当年拚却醉颜红。舞低杨柳楼心月，歌尽桃花扇底风。从别后，忆相逢，几回魂梦与君同。今宵剩把银釭照，犹恐相逢是梦中。', '晏几道', '宋代', '写与歌女久别重逢的喜悦和感慨', '工于言情，清丽婉转，情深意厚', '今宵剩把银釭照，犹恐相逢是梦中', '[\"舞低\",\"歌尽\",\"桃花\"]', '当年你殷勤劝酒频举玉盅，我开怀畅饮喝得酒醉脸通红。', '此词写情人久别重逢，通过回忆当年的欢乐和今日的惊喜，表现真挚的爱情。', '/audio/zhegutian.mp3', '/image/songci9.jpg', 123, b'1', 'admin', '2025-10-24 11:26:12.221', 'admin', '2025-10-24 11:26:12.221');
INSERT INTO `poetry_learning_content` VALUES (10, '踏莎行', '候馆梅残', 1, 2, 1, 5, 2, '候馆梅残，溪桥柳细。草薰风暖摇征辔。离愁渐远渐无穷，迢迢不断如春水。寸寸柔肠，盈盈粉泪。楼高莫近危阑倚。平芜尽处是春山，行人更在春山外。', '欧阳修', '宋代', '写早春南方行旅的离愁', '以春景写离情，情景交融', '离愁渐远渐无穷，迢迢不断如春水', '[\"候馆\",\"溪桥\",\"征辔\"]', '客舍前的梅花已经凋残，溪桥旁新生细柳轻垂，暖风吹送着春草的芳香。', '此词写离情，于柔情别绪中寓沉郁之情，在艺术上具有很高的成就。', '/audio/tasuo.mp3', '/image/songci10.jpg', 145, b'1', 'admin', '2025-10-24 11:26:12.221', 'admin', '2025-10-24 11:26:12.221');
INSERT INTO `poetry_learning_content` VALUES (11, '永遇乐·京口北固亭怀古', '千古江山', 1, 3, 1, 5, 3, '千古江山，英雄无觅，孙仲谋处。舞榭歌台，风流总被，雨打风吹去。斜阳草树，寻常巷陌，人道寄奴曾住。想当年，金戈铁马，气吞万里如虎。元嘉草草，封狼居胥，赢得仓皇北顾。四十三年，望中犹记，烽火扬州路。可堪回首，佛狸祠下，一片神鸦社鼓。凭谁问：廉颇老矣，尚能饭否？', '辛弃疾', '宋代', '辛弃疾在镇江知府任上登北固亭时所作', '借古讽今，抒发对南宋朝廷的不满和自己壮志未酬的悲愤', '想当年，金戈铁马，气吞万里如虎', '[\"孙仲谋\",\"寄奴\",\"佛狸\"]', '历经千古的江山，再也难找到像孙权那样的英雄。', '全词豪壮悲凉，义重情深，放射着爱国主义的思想光辉。', '/audio/yongyule.mp3', '/image/songci11.jpg', 176, b'1', 'admin', '2025-10-24 11:26:12.221', 'admin', '2025-10-24 11:26:12.221');
INSERT INTO `poetry_learning_content` VALUES (12, '钗头凤', '红酥手', 1, 3, 1, 5, 3, '红酥手，黄縢酒，满城春色宫墙柳。东风恶，欢情薄。一怀愁绪，几年离索。错、错、错。春如旧，人空瘦，泪痕红浥鲛绡透。桃花落，闲池阁。山盟虽在，锦书难托。莫、莫、莫！', '陆游', '宋代', '陆游与前妻唐婉在沈园重逢后所作', '表达对前妻的深切思念和无法相守的痛苦', '春如旧，人空瘦，泪痕红浥鲛绡透', '[\"东风\",\"欢情\",\"离索\"]', '你红润酥腻的手里，捧着盛上黄縢酒的杯子。满城荡漾着春天的景色，你却早已像宫墙中的绿柳那般遥不可及。', '此词描写了词人与原配唐氏的爱情悲剧，记述了词人与唐氏在沈园相遇的情景。', '/audio/chaitoufeng.mp3', '/image/songci12.jpg', 198, b'1', 'admin', '2025-10-24 11:26:12.221', 'admin', '2025-10-24 11:26:12.221');
INSERT INTO `poetry_learning_content` VALUES (13, '苏幕遮', '碧云天', 1, 3, 1, 5, 2, '碧云天，黄叶地，秋色连波，波上寒烟翠。山映斜阳天接水，芳草无情，更在斜阳外。黯乡魂，追旅思，夜夜除非，好梦留人睡。明月楼高休独倚，酒入愁肠，化作相思泪。', '范仲淹', '宋代', '写羁旅乡思之情', '上片写景，下片抒情，情景交融', '明月楼高休独倚，酒入愁肠，化作相思泪', '[\"芳草\",\"斜阳\",\"乡魂\"]', '白云满天，黄叶遍地。秋天的景色映进江上的碧波，水波上笼罩着寒烟一片苍翠。', '此词借景抒情，情景交融，以绚丽多彩的笔墨描绘碧云、黄叶、寒波、翠烟、芳草、斜阳、水天相接的江野辽阔苍茫的景色。', '/audio/sumuzhe.mp3', '/image/songci13.jpg', 154, b'1', 'admin', '2025-10-24 11:26:12.221', 'admin', '2025-10-24 11:26:12.221');
INSERT INTO `poetry_learning_content` VALUES (14, '江城子·密州出猎', '老夫聊发少年狂', 1, 3, 1, 5, 2, '老夫聊发少年狂，左牵黄，右擎苍。锦帽貂裘，千骑卷平冈。为报倾城随太守，亲射虎，看孙郎。酒酣胸胆尚开张，鬓微霜，又何妨！持节云中，何日遣冯唐？会挽雕弓如满月，西北望，射天狼。', '苏轼', '宋代', '苏轼在密州任知州时一次出猎所作', '表达作者强国抗敌的政治主张，抒写渴望报效朝廷的壮志豪情', '会挽雕弓如满月，西北望，射天狼', '[\"锦帽\",\"貂裘\",\"千骑\"]', '我姑且抒发一下少年的豪情壮志，左手牵着黄犬，右臂托起苍鹰。', '此词表达了强国抗敌的政治主张，抒写了渴望报效朝廷的壮志豪情，融叙事、言志、用典为一体。', '/audio/jiangchengzi.mp3', '/image/songci14.jpg', 167, b'1', 'admin', '2025-10-24 11:26:12.221', 'admin', '2025-10-24 11:26:12.221');
INSERT INTO `poetry_learning_content` VALUES (15, '卜算子·咏梅', '驿外断桥边', 1, 2, 1, 5, 2, '驿外断桥边，寂寞开无主。已是黄昏独自愁，更著风和雨。无意苦争春，一任群芳妒。零落成泥碾作尘，只有香如故。', '陆游', '宋代', '以梅花自喻，表达孤高雅洁的志趣', '托物言志，通过梅花表现自己虽历尽艰辛也不会趋炎附势', '零落成泥碾作尘，只有香如故', '[\"驿外\",\"断桥\",\"黄昏\"]', '驿站之外的断桥边，梅花孤单寂寞地绽开了花，无人过问。', '此词以梅花自况，咏梅的凄苦以泄胸中抑郁，感叹人生的失意坎坷；赞梅的精神又表达了青春无悔的信念。', '/audio/busuanzi.mp3', '/image/songci15.jpg', 143, b'1', 'admin', '2025-10-24 11:26:12.221', 'admin', '2025-10-24 11:26:12.221');
INSERT INTO `poetry_learning_content` VALUES (16, '之字的用法', '文言虚词学习', 3, 1, 2, 6, 1, '之字在文言文中有多种用法：1、代词 2、助词 3、动词', NULL, NULL, '文言文基础虚词教学', '之可以作为代词、助词、动词使用', '1、学而时习之 2、宋何罪之有 3、吾欲之南海', '[\"代词\",\"助词\",\"动词\"]', '之在文言文中的用法：1、代词\"它\" 2、助词\"的\" 3、动词\"到\"', '之是文言文中最常用的虚词之一，掌握其用法对理解文言文至关重要。', '/audio/zhi.mp3', '/image/xuci1.jpg', 89, b'1', 'admin', '2025-10-24 11:26:12.221', 'admin', '2025-10-24 11:26:12.221');
INSERT INTO `poetry_learning_content` VALUES (17, '而字的用法', '转折连词', 3, 1, 2, 6, 1, '而字主要用作连词，表示转折、并列、承接等关系', NULL, NULL, '文言文连词教学', '而可以表示转折、并列、修饰、承接等关系', '1、学而不思则罔 2、温故而知新 3、锲而不舍', '[\"转折\",\"并列\",\"承接\"]', '而主要作为连词使用，可以表示但是、并且、接着等意思', '而字是文言文中最重要的连词之一，能够连接词、短语和句子。', '/audio/er.mp3', '/image/xuci2.jpg', 76, b'1', 'admin', '2025-10-24 11:26:12.221', 'admin', '2025-10-24 11:26:12.221');
INSERT INTO `poetry_learning_content` VALUES (18, '其字的用法', '代词与副词', 3, 2, 2, 6, 2, '其字在文言文中主要用作代词和副词', NULL, NULL, '文言文代词教学', '其可以作为代词、副词、连词使用', '1、其一人专心致志 2、其如土石何 3、其真无马邪', '[\"代词\",\"副词\",\"连词\"]', '其可以表示他的、它的，或者表示反问、推测的语气', '其字用法灵活，需要根据上下文判断具体含义。', '/audio/qi.mp3', '/image/xuci3.jpg', 98, b'1', 'admin', '2025-10-24 11:26:12.221', 'admin', '2025-10-24 11:26:12.221');
INSERT INTO `poetry_learning_content` VALUES (19, '以字的用法', '介词与连词', 3, 2, 2, 6, 2, '以字主要用作介词和连词，表示凭借、原因、目的等', NULL, NULL, '文言文介词教学', '以可以表示凭借、原因、目的、时间等', '1、以刀劈狼首 2、以衾拥覆 3、以塞忠谏之路', '[\"介词\",\"连词\",\"动词\"]', '以可以表示用、因为、来等意思，用法多样', '以字是文言文中使用频率很高的虚词，需要重点掌握。', '/audio/yi.mp3', '/image/xuci4.jpg', 87, b'1', 'admin', '2025-10-24 11:26:12.221', 'admin', '2025-10-24 11:26:12.221');
INSERT INTO `poetry_learning_content` VALUES (20, '于字的用法', '介词用法', 3, 2, 2, 6, 2, '于字主要用作介词，表示时间、地点、对象等', NULL, NULL, '文言文介词教学', '于可以表示在、到、从、对、比等含义', '1、战于长勺 2、青取之于蓝 3、苛政猛于虎', '[\"时间\",\"地点\",\"比较\"]', '于可以表示在、从、对、比等介词含义', '于字是文言文中最基本的介词之一，用法相对固定。', '/audio/yu.mp3', '/image/xuci5.jpg', 92, b'1', 'admin', '2025-10-24 11:26:12.221', 'admin', '2025-10-24 11:26:12.221');
INSERT INTO `poetry_learning_content` VALUES (21, '静夜思', '床前明月光', 1, 1, 1, 4, 1, '床前明月光，疑是地上霜。举头望明月，低头思故乡。', '李白', '唐代', '李白旅居他乡时在一个月夜所作', '通过明月意象表达思乡之情', '举头望明月，低头思故乡', '[\"明月\",\"故乡\",\"霜\"]', '明亮的月光洒在窗户纸上，好像地上泛起了一层霜。我禁不住抬起头来，看那天窗外空中的一轮明月，不由得低头沉思，想起远方的家乡。', '这首诗写的是在寂静的月夜思念家乡的感受，以明白如话的语言描绘了月夜思乡的图景。', 'ttsmaker-file-2025-11-8-17-17-37.mp3', '1decd86741b64c378f5eb582323834d1-jingyesi.webp', 324, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (22, '望庐山瀑布', '日照香炉生紫烟', 1, 1, 1, 4, 2, '日照香炉生紫烟，遥看瀑布挂前川。飞流直下三千尺，疑是银河落九天。', '李白', '唐代', '李白游历庐山时被瀑布壮观景象所震撼而作', '运用夸张手法描绘庐山瀑布的雄伟壮观', '飞流直下三千尺，疑是银河落九天', '[\"香炉\",\"瀑布\",\"银河\"]', '香炉峰在阳光的照射下生起紫色烟霞，远远望见瀑布似白色绢绸悬挂在山前。高崖上飞腾直落的瀑布好像有几千尺，让人恍惚以为银河从天上泻落到人间。', '这首诗形象地描绘了庐山瀑布雄奇壮丽的景色，反映了诗人对祖国大好河山的无限热爱。', '/audio/pubu.mp3', '/image/tangshi2.jpg', 287, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (23, '早发白帝城', '朝辞白帝彩云间', 1, 2, 1, 4, 2, '朝辞白帝彩云间，千里江陵一日还。两岸猿声啼不住，轻舟已过万重山。', '李白', '唐代', '李白在流放途中遇赦返回时所作', '表达诗人遇赦后愉快的心情和江山的壮丽', '两岸猿声啼不住，轻舟已过万重山', '[\"白帝\",\"江陵\",\"猿声\"]', '清晨告别五彩云霞映照中的白帝城，千里之遥的江陵，一天就可以到达。两岸猿声还在耳边不停地回荡，轻快的小舟已驶过万重青山。', '全诗把诗人遇赦后愉快的心情和江山的壮丽多姿、顺水行舟的流畅轻快融为一体，运用夸张和奇想，写得流丽飘逸，惊世骇俗。', '/audio/baidicheng.mp3', '/image/tangshi3.jpg', 256, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (24, '黄鹤楼送孟浩然之广陵', '故人西辞黄鹤楼', 1, 2, 1, 4, 2, '故人西辞黄鹤楼，烟花三月下扬州。孤帆远影碧空尽，唯见长江天际流。', '李白', '唐代', '李白在黄鹤楼送别好友孟浩然时所作', '在离别诗中充满诗意和向往', '孤帆远影碧空尽，唯见长江天际流', '[\"黄鹤楼\",\"扬州\",\"孤帆\"]', '友人在黄鹤楼与我辞别，在柳絮如烟、繁花似锦的阳春三月去扬州远游。孤船帆影渐渐消失在碧空尽头，只看见滚滚长江向天际奔流。', '这首诗寓离情于写景之中，以绚丽斑驳的烟花春色和浩瀚无边的长江为背景，绘出了一幅意境开阔、情丝不绝、色彩明快的诗人送别画。', '/audio/huanghelou.mp3', '/image/tangshi4.jpg', 234, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (25, '行路难', '金樽清酒斗十千', 1, 3, 1, 4, 3, '金樽清酒斗十千，玉盘珍羞直万钱。停杯投箸不能食，拔剑四顾心茫然。欲渡黄河冰塞川，将登太行雪满山。闲来垂钓碧溪上，忽复乘舟梦日边。行路难，行路难，多歧路，今安在？长风破浪会有时，直挂云帆济沧海。', '李白', '唐代', '李白遭受谗毁而被排挤出长安时所作', '抒写仕途艰难和人生感慨', '长风破浪会有时，直挂云帆济沧海', '[\"金樽\",\"玉盘\",\"云帆\"]', '金杯中的美酒一斗价十千，玉盘里的菜肴珍贵值万钱。但心情愁烦使得我放下杯筷，不愿进餐。拔出宝剑环顾四周，心里一片茫然。', '这首诗抒写了诗人在政治道路上遭遇艰难后的感慨，反映了诗人在思想上既不愿同流合污又不愿独善一身的矛盾。', '/audio/xinglunan.mp3', '/image/tangshi5.jpg', 198, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (26, '春望', '国破山河在', 1, 3, 1, 4, 3, '国破山河在，城春草木深。感时花溅泪，恨别鸟惊心。烽火连三月，家书抵万金。白头搔更短，浑欲不胜簪。', '杜甫', '唐代', '安史之乱期间杜甫被困长安时所作', '表达诗人忧国忧民的情怀', '感时花溅泪，恨别鸟惊心', '[\"国破\",\"草木\",\"烽火\"]', '长安沦陷，国家破碎，只有山河依旧；春天来了，人烟稀少的长安城里草木茂密。感于战败的时局，看到花开而潸然泪下，内心惆怅怨恨，听到鸟鸣而心惊胆战。', '这首诗全篇情景交融，感情深沉，充分体现了杜甫沉郁顿挫的艺术风格，反映了诗人热爱国家、眷念家人的美好情操。', '/audio/chunwang.mp3', '/image/tangshi6.jpg', 267, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (27, '登高', '风急天高猿啸哀', 1, 3, 1, 4, 3, '风急天高猿啸哀，渚清沙白鸟飞回。无边落木萧萧下，不尽长江滚滚来。万里悲秋常作客，百年多病独登台。艰难苦恨繁霜鬓，潦倒新停浊酒杯。', '杜甫', '唐代', '杜甫晚年漂泊西南时期所作', '被誉为\"七律之冠\"，抒发身世飘零之感', '无边落木萧萧下，不尽长江滚滚来', '[\"猿啸\",\"落木\",\"长江\"]', '风急天高猿猴啼叫显得十分悲哀，水清沙白的河洲上有鸟儿在盘旋。无边无际的树木萧萧地飘下落叶，望不到头的长江水滚滚奔腾而来。', '此诗语言精练，通篇对偶，充分显示了杜甫晚年对诗歌语言声律的把握运用已达圆通之境，被后人推为古今七律第一。', '/audio/denggao.mp3', '/image/tangshi7.jpg', 289, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (28, '春夜喜雨', '好雨知时节', 1, 2, 1, 4, 2, '好雨知时节，当春乃发生。随风潜入夜，润物细无声。野径云俱黑，江船火独明。晓看红湿处，花重锦官城。', '杜甫', '唐代', '杜甫在成都草堂居住时所作', '描绘春夜降雨、润泽万物的美景', '随风潜入夜，润物细无声', '[\"好雨\",\"春风\",\"锦官城\"]', '好雨似乎会挑选时辰，降临在万物萌生之春。伴随和风，悄悄进入夜幕。细细密密，滋润大地万物。', '此诗运用拟人手法，以极大的喜悦之情细致地描绘了春雨的特点和成都夜雨的景象，热情地讴歌了来得及时、滋润万物的春雨。', '/audio/chunyexiyu.mp3', '/image/tangshi8.jpg', 245, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (29, '望岳', '岱宗夫如何', 1, 2, 1, 4, 2, '岱宗夫如何？齐鲁青未了。造化钟神秀，阴阳割昏晓。荡胸生曾云，决眦入归鸟。会当凌绝顶，一览众山小。', '杜甫', '唐代', '杜甫青年时期漫游齐赵时所作', '通过描绘泰山雄伟磅礴的气象抒发抱负', '会当凌绝顶，一览众山小', '[\"岱宗\",\"齐鲁\",\"绝顶\"]', '五岳之首泰山的景象怎么样？在齐鲁大地上，那青翠的山色没有尽头。大自然把神奇秀丽的景象全都汇聚其中，山南山北阴阳分界，晨昏迥然不同。', '这首诗通过描绘泰山雄伟磅礴的气象，热情赞美了泰山高大巍峨的气势和神奇秀丽的景色，流露出了对祖国山河的热爱之情。', '/audio/wangyue.mp3', '/image/tangshi9.jpg', 223, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (30, '绝句', '两个黄鹂鸣翠柳', 1, 1, 1, 4, 1, '两个黄鹂鸣翠柳，一行白鹭上青天。窗含西岭千秋雪，门泊东吴万里船。', '杜甫', '唐代', '杜甫在成都草堂闲居时所作', '描绘草堂周围明媚秀丽的春天景色', '窗含西岭千秋雪，门泊东吴万里船', '[\"黄鹂\",\"白鹭\",\"西岭\"]', '两只黄鹂在翠绿的柳树间婉转地歌唱，一队整齐的白鹭直冲向蔚蓝的天空。我坐在窗前，可以望见西岭上堆积着终年不化的积雪，门前停泊着自万里外的东吴远行而来的船只。', '这首诗描绘出四个独立的景色，营造出一幅生机勃勃的图画，诗人陶醉其中，望着来自东吴的船只，不觉勾起了乡愁，细致的内心活动自然地流露出来。', '/audio/jueju.mp3', '/image/tangshi10.jpg', 278, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (31, '赋得古原草送别', '离离原上草', 1, 2, 1, 4, 2, '离离原上草，一岁一枯荣。野火烧不尽，春风吹又生。远芳侵古道，晴翠接荒城。又送王孙去，萋萋满别情。', '白居易', '唐代', '白居易十六岁时为应试所作', '通过对古原上野草的描绘抒发送别之情', '野火烧不尽，春风吹又生', '[\"原上草\",\"春风\",\"晴翠\"]', '原野上长满茂盛的青草，每年秋冬枯黄春来草色浓。野火无法烧尽满地的野草，春风吹来大地又是绿茸茸。', '此诗通过对古原上野草的描绘，抒发送别友人时的依依惜别之情，它可以看成是一曲野草颂，进而是生命的颂歌。', '/audio/caosongbie.mp3', '/image/tangshi11.jpg', 256, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (32, '琵琶行', '浔阳江头夜送客', 1, 3, 1, 4, 3, '浔阳江头夜送客，枫叶荻花秋瑟瑟。主人下马客在船，举酒欲饮无管弦。醉不成欢惨将别，别时茫茫江浸月。忽闻水上琵琶声，主人忘归客不发。寻声暗问弹者谁？琵琶声停欲语迟。移船相近邀相见，添酒回灯重开宴。千呼万唤始出来，犹抱琵琶半遮面', '白居易', '唐代', '白居易被贬江州司马时所作', '长篇叙事诗，借琵琶女遭遇抒发自身感慨', '同是天涯沦落人，相逢何必曾相识', '[\"浔阳\",\"琵琶\",\"天涯\"]', '秋夜我到浔阳江头送一位归客，冷风吹着枫叶和芦花秋声瑟瑟。我和客人下马在船上饯别设宴，举起酒杯要饮却无助兴的音乐。', '这首诗通过描写琵琶女高超的弹奏技艺和她的凄凉身世，表达了诗人对她的深切同情，也抒发了诗人对自己无辜被贬的愤懑之情。', '/audio/pipaxing.mp3', '/image/tangshi12.jpg', 312, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (33, '钱塘湖春行', '孤山寺北贾亭西', 1, 2, 1, 4, 2, '孤山寺北贾亭西，水面初平云脚低。几处早莺争暖树，谁家新燕啄春泥。乱花渐欲迷人眼，浅草才能没马蹄。最爱湖东行不足，绿杨阴里白沙堤。', '白居易', '唐代', '白居易任杭州刺史时所作', '描绘早春西湖的美景和游春的乐趣', '乱花渐欲迷人眼，浅草才能没马蹄', '[\"孤山\",\"早莺\",\"新燕\"]', '行至孤山寺北，贾公亭西，暂且歇脚，举目远眺，水面平涨，白云低垂，秀色无边。几只黄莺，争先飞往向阳树木，谁家燕子，为筑新巢衔来春泥？', '此诗通过写西湖早春明媚风光的描绘，抒发了作者早春游湖的喜悦和对钱塘湖风景的喜爱，更表达了作者对于自然之美的热爱之情。', '/audio/qiantanghu.mp3', '/image/tangshi13.jpg', 234, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (34, '忆江南', '江南好', 1, 1, 1, 4, 1, '江南好，风景旧曾谙。日出江花红胜火，春来江水绿如蓝。能不忆江南？', '白居易', '唐代', '白居易晚年回忆江南美景所作', '抒发对江南春色的无限赞叹与怀念', '日出江花红胜火，春来江水绿如蓝', '[\"江南\",\"江花\",\"江水\"]', '江南的风景多么美好，如画的风景久已熟悉。春天到来时，太阳从江面升起，把江边的鲜花照得比火红，碧绿的江水绿得胜过蓝草。怎能叫人不怀念江南？', '此词写江南春色，首句\"江南好\"，以一个既浅切又圆活的\"好\"字，摄尽江南春色的种种佳处，而作者的赞颂之意与向往之情也尽寓其中。', '/audio/yijiangnan.mp3', '/image/tangshi14.jpg', 267, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (35, '暮江吟', '一道残阳铺水中', 1, 1, 1, 4, 1, '一道残阳铺水中，半江瑟瑟半江红。可怜九月初三夜，露似真珠月似弓。', '白居易', '唐代', '白居易赴杭州任刺史途中所作', '描绘傍晚和夜晚江边的美丽景色', '可怜九月初三夜，露似真珠月似弓', '[\"残阳\",\"瑟瑟\",\"真珠\"]', '一道残阳渐沉江中，半江碧绿半江艳红。最可爱的是那九月初三之夜，亮似珍珠朗朗新月形如弯弓。', '这首诗格调清新，自然可喜，通过\"露\"、\"月\"视觉形象的描写，创造出和谐、宁静的意境，用这样新颖巧妙的比喻来精心为大自然敷彩着色，描容绘形，令人叹绝。', '/audio/mujiangyin.mp3', '/image/tangshi15.jpg', 198, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (36, '相思', '红豆生南国', 1, 1, 1, 4, 1, '红豆生南国，春来发几枝。愿君多采撷，此物最相思。', '王维', '唐代', '借咏物而寄相思的诗作', '通过红豆表达对友人的思念之情', '愿君多采撷，此物最相思', '[\"红豆\",\"南国\",\"相思\"]', '红豆生长在阳光明媚的南方，每逢春天不知长多少新枝。希望思念的人儿多多采摘，因为它最能寄托相思之情。', '这是借咏物而寄相思的诗，全诗情调健美高雅，怀思饱满奔放，语言朴素无华，韵律和谐柔美，可谓绝句的上乘佳品。', '/audio/xiangsi.mp3', '/image/tangshi16.jpg', 289, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (37, '山居秋暝', '空山新雨后', 1, 3, 1, 4, 3, '空山新雨后，天气晚来秋。明月松间照，清泉石上流。', '王维', '唐代', '王维隐居辋川时所作', '描绘秋雨初晴后傍晚时分山村的旖旎风光', '明月松间照，清泉石上流', '[\"空山\",\"明月\",\"清泉\"]', '空旷的群山沐浴了一场新雨，夜晚降临使人感到已是初秋。皎皎明月从松隙间洒下清光，清清泉水在山石上淙淙淌流。', '这首诗为山水名篇，于诗情画意之中寄托着诗人高洁的情怀和对理想境界的追求，表现了诗人寄情山水田园并对隐居生活怡然自得的满足心情。', '/audio/shanjuqiuming.mp3', '/image/tangshi17.jpg', 245, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (38, '使至塞上', '单车欲问边', 1, 3, 1, 4, 3, '单车欲问边，属国过居延。征蓬出汉塞，归雁入胡天。大漠孤烟直，长河落日圆。萧关逢候骑，都护在燕然。', '王维', '唐代', '王维奉命赴边疆慰问将士途中所作', '描绘塞外奇特壮丽的风光', '大漠孤烟直，长河落日圆', '[\"征蓬\",\"归雁\",\"大漠\"]', '轻车简从将要去慰问边关，路经的属国已过居延。像随风而去的蓬草一样出临边塞，北归大雁正翱翔云天。', '此诗既反映了边塞生活，同时也表达了诗人由于被排挤而产生的孤独、寂寞、悲伤之情以及在大漠的雄浑景色中情感得到熏陶、净化、升华后产生的慷慨悲壮之情，显露出一种豁达情怀。', '/audio/saishang.mp3', '/image/tangshi18.jpg', 223, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (39, '鹿柴', '空山不见人', 1, 1, 1, 4, 1, '空山不见人，但闻人语响。返景入深林，复照青苔上。', '王维', '唐代', '王维隐居辋川时所作组诗之一', '描绘鹿柴附近的空山深林在傍晚时分的幽静景色', '返景入深林，复照青苔上', '[\"空山\",\"深林\",\"青苔\"]', '幽静的山谷里看不见人，只听到人说话的声音。落日的影晕映入了深林，又照在幽暗处的青苔上。', '这首诗创造了一种幽深而光明的象征性境界，表现了作者在深幽的修禅过程中的豁然开朗，语言清新自然，浑然天成。', '/audio/luzhai.mp3', '/image/tangshi19.jpg', 234, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (40, '竹里馆', '独坐幽篁里', 1, 2, 1, 4, 2, '独坐幽篁里，弹琴复长啸。深林人不知，明月来相照。', '王维', '唐代', '王维晚年隐居蓝田辋川时创作', '描绘诗人月下独坐、弹琴长啸的悠闲生活', '深林人不知，明月来相照', '[\"幽篁\",\"弹琴\",\"明月\"]', '独自闲坐在幽静竹林，一边弹琴一边高歌长啸。深深的山林中无人知晓，只有一轮明月静静与我相伴。', '此诗写隐者的闲适生活以及情趣，描绘了诗人月下独坐、弹琴长啸的悠闲生活，遣词造句简朴清丽，传达出诗人宁静、淡泊的心情。', '/audio/zhuliguan.mp3', '/image/tangshi20.jpg', 198, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (41, '登鹳雀楼', '白日依山尽', 1, 1, 1, 4, 1, '白日依山尽，黄河入海流。欲穷千里目，更上一层楼。', '王之涣', '唐代', '诗人登高望远抒怀之作', '通过登楼观景表达积极进取的精神', '欲穷千里目，更上一层楼', '[\"白日\",\"黄河\",\"千里目\"]', '夕阳依傍着山峦慢慢沉落，滔滔黄河朝着大海汹涌奔流。想要看到千里之外的风光，那就要再登上更高的一层城楼。', '此诗虽然只有二十字，却描绘出北国河山的磅礴气势和壮丽景象，气势磅礴、意境深远，千百年来一直激励着中华民族昂扬向上。', '/audio/guanquelou.mp3', '/image/tangshi21.jpg', 345, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (42, '凉州词', '黄河远上白云间', 1, 2, 1, 4, 2, '黄河远上白云间，一片孤城万仞山。羌笛何须怨杨柳，春风不度玉门关。', '王之涣', '唐代', '描写戍边士兵的怀乡情', '展现边塞地区荒凉辽阔的景象', '羌笛何须怨杨柳，春风不度玉门关', '[\"黄河\",\"孤城\",\"玉门关\"]', '黄河好像从白云间奔流而来，玉门关孤独地耸峙在高山中。何必用羌笛吹起那哀怨的杨柳曲去埋怨春光迟迟不来呢，原来玉门关一带春风是吹不到的啊！', '此诗虽极写戍边者不得还乡的怨情，但写得悲壮苍凉，没有衰飒颓唐的情调，表现出盛唐诗人广阔的心胸，是唐代边塞诗的代表作。', '/audio/liangzhouci.mp3', '/image/tangshi22.jpg', 267, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (43, '出塞', '秦时明月汉时关', 1, 3, 1, 4, 3, '秦时明月汉时关，万里长征人未还。但使龙城飞将在，不教胡马度阴山。', '王昌龄', '唐代', '反映边防将士保家卫国的英勇精神', '表达对良将的渴望和对和平的向往', '但使龙城飞将在，不教胡马度阴山', '[\"秦月\",\"汉关\",\"龙城\"]', '依旧是秦汉时期的明月和边关，守边御敌鏖战万里征人未回还。倘若龙城的飞将李广如今还在，绝不许匈奴南下牧马度过阴山。', '这首诗虽然只有短短四行，但是通过对边疆景物和征人心理的描绘，表现的内容是复杂的，既有对久戍士卒的浓厚同情和结束这种边防不顾局面的愿望。', '/audio/chusai.mp3', '/image/tangshi23.jpg', 289, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (44, '芙蓉楼送辛渐', '寒雨连江夜入吴', 1, 2, 1, 4, 2, '寒雨连江夜入吴，平明送客楚山孤。洛阳亲友如相问，一片冰心在玉壶。', '王昌龄', '唐代', '王昌龄被贬期间送别友人时所作', '表明自己光明磊落、清廉自守的品格', '洛阳亲友如相问，一片冰心在玉壶', '[\"寒雨\",\"楚山\",\"玉壶\"]', '冷雨洒满江天的夜晚我来到吴地，天明送走好友只留下楚山的孤影。到了洛阳，如果有亲友向您打听我的情况，就请转告他们，我的心依然像玉壶里的冰一样纯洁。', '这首诗即景生情，寓情于景，含蓄蕴藉，韵味无穷，令人自然地联想到诗人孤介傲岸、冰清玉洁的形象，使精巧的构思和深婉的用意融化在一片清空明澈的意境之中。', '/audio/furonglou.mp3', '/image/tangshi24.jpg', 234, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (45, '游子吟', '慈母手中线', 1, 1, 1, 4, 1, '慈母手中线，游子身上衣。临行密密缝，意恐迟迟归。谁言寸草心，报得三春晖。', '孟郊', '唐代', '孟郊居官溧阳时的作品', '歌颂母爱的伟大与无私', '谁言寸草心，报得三春晖', '[\"慈母\",\"游子\",\"春晖\"]', '慈祥的母亲手里把着针线，为即将远游的孩子赶制新衣。临行前一针针密密地缝缀，怕儿子回来得晚衣服破损。', '这是一首母爱的颂歌，通过回忆一个看似平常的临行前缝衣的场景，凸显并歌颂了母爱的伟大与无私，表达了诗人对母爱的感激以及对母亲深深的爱与尊敬。', '/audio/youziyin.mp3', '/image/tangshi25.jpg', 312, b'1', 'admin', '2025-10-24 11:30:16.152', 'admin', '2025-10-24 11:30:16.152');
INSERT INTO `poetry_learning_content` VALUES (46, '锦瑟', '锦瑟无端五十弦', 1, 3, 1, 4, 3, '锦瑟无端五十弦，一弦一柱思华年。庄生晓梦迷蝴蝶，望帝春心托杜鹃。沧海月明珠有泪，蓝田日暖玉生烟。此情可待成追忆，只是当时已惘然。', '李商隐', '唐代', '李商隐晚年回顾平生、自伤身世之作', '运用典故和象征手法表达人生感慨', '此情可待成追忆，只是当时已惘然', '[\"锦瑟\",\"华年\",\"杜鹃\"]', '精美的瑟为什么竟有五十根弦，一弦一柱都叫我追忆青春年华。庄周翩翩起舞睡梦中化为蝴蝶，望帝把自己的幽恨托身于杜鹃。', '这首诗历来注释不一，莫衷一是。或以为是悼亡之作，或以为是爱国之篇，或以为是自比文才之论，但以为是悼念亡妻最为贴切。', '/audio/jinse.mp3', '/image/tangshi26.jpg', 278, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (47, '夜雨寄北', '君问归期未有期', 1, 2, 1, 4, 2, '君问归期未有期，巴山夜雨涨秋池。何当共剪西窗烛，却话巴山夜雨时。', '李商隐', '唐代', '李商隐在巴蜀时寄给长安妻子的诗', '表达对妻子的思念和重逢的渴望', '何当共剪西窗烛，却话巴山夜雨时', '[\"归期\",\"巴山\",\"西窗\"]', '你问我回家的日期，归期难定，今晚巴山下着大雨，雨水已涨满秋池。什么时候我们才能一起秉烛长谈，相互倾诉今宵巴山夜雨中的思念之情。', '这首诗既描写了今日身处巴山倾听秋雨时的寂寥之苦，又想象了来日聚首之时的幸福欢乐，此时的痛苦与将来的喜悦交织一起。', '/audio/yeyujibei.mp3', '/image/tangshi27.jpg', 245, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (48, '无题', '相见时难别亦难', 1, 3, 1, 4, 3, '相见时难别亦难，东风无力百花残。春蚕到死丝方尽，蜡炬成灰泪始干。晓镜但愁云鬓改，夜吟应觉月光寒。蓬山此去无多路，青鸟殷勤为探看。', '李商隐', '唐代', '李商隐无题诗的代表作', '描写至死不渝的爱情', '春蚕到死丝方尽，蜡炬成灰泪始干', '[\"东风\",\"春蚕\",\"蜡炬\"]', '相见很难，离别更难，何况在这东风无力、百花凋谢的暮春时节。春蚕结茧到死时丝才吐完，蜡烛要烧成灰烬时像泪一样的蜡油才能滴干。', '这首诗以女性的口吻抒写爱情心理，在悲伤、痛苦之中，寓有灼热的渴望和坚忍的执着精神，感情境界深微绵邈，极为丰富。', '/audio/wuti1.mp3', '/image/tangshi28.jpg', 312, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (49, '登乐游原', '向晚意不适', 1, 2, 1, 4, 2, '向晚意不适，驱车登古原。夕阳无限好，只是近黄昏。', '李商隐', '唐代', '李商隐晚年登高抒怀之作', '借景抒情，感慨时光流逝', '夕阳无限好，只是近黄昏', '[\"向晚\",\"古原\",\"夕阳\"]', '傍晚时心情不快，驾着车登上古原。夕阳啊无限美好，只不过接近黄昏。', '这首诗反映了作者的伤感情绪，当诗人为排遣\"意不适\"的情怀而登上乐游原时，看到了一轮辉煌灿烂的黄昏斜阳，于是发乎感慨。', '/audio/leyouyuan.mp3', '/image/tangshi29.jpg', 234, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (50, '嫦娥', '云母屏风烛影深', 1, 3, 1, 4, 3, '云母屏风烛影深，长河渐落晓星沉。嫦娥应悔偷灵药，碧海青天夜夜心。', '李商隐', '唐代', '借嫦娥奔月的神话抒写内心孤寂', '寄托诗人复杂的人生感慨', '嫦娥应悔偷灵药，碧海青天夜夜心', '[\"云母\",\"长河\",\"嫦娥\"]', '云母屏风上烛影暗淡，银河渐渐斜落晨星也隐没低沉。嫦娥应该后悔偷取了长生不老药，如今空对碧海青天夜夜孤寂。', '此诗咏叹嫦娥在月中的孤寂情景，抒发诗人自伤之情，语言含蕴，情调感伤，进一步渲染了浓重的孤独氛围。', '/audio/chang_e.mp3', '/image/tangshi30.jpg', 198, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (51, '清明', '清明时节雨纷纷', 1, 1, 1, 4, 1, '清明时节雨纷纷，路上行人欲断魂。借问酒家何处有，牧童遥指杏花村。', '杜牧', '唐代', '描绘清明时节的景象和行人的心情', '展现清明特有的氛围和情感', '借问酒家何处有，牧童遥指杏花村', '[\"清明\",\"行人\",\"杏花村\"]', '江南清明时节细雨纷纷飘洒，路上羁旅行人个个落魄断魂。借问当地之人何处买酒浇愁？牧童笑而不答遥指杏花山村。', '这首诗描写清明时节的天气特征，抒发了孤身行路之人的情绪和希望，全诗色彩清淡，心境凄冷，余韵邈然，耐人寻味。', '/audio/qingming.mp3', '/image/tangshi31.jpg', 345, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (52, '江南春', '千里莺啼绿映红', 1, 1, 1, 4, 1, '千里莺啼绿映红，水村山郭酒旗风。南朝四百八十寺，多少楼台烟雨中。', '杜牧', '唐代', '描绘江南春日美景', '展现江南地区特有的秀丽风光', '南朝四百八十寺，多少楼台烟雨中', '[\"莺啼\",\"酒旗\",\"楼台\"]', '辽阔的江南到处莺歌燕舞绿树红花相映，水边村寨山麓城郭处处酒旗飘动。南朝遗留下的许多座古寺，如今有多少笼罩在这蒙胧烟雨之中。', '这首诗不仅描绘了明媚的江南春光，而且还再现了江南烟雨蒙蒙的楼台景色，使江南风光更加神奇迷离，别有一番情趣。', '/audio/jiangnanchun.mp3', '/image/tangshi32.jpg', 278, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (53, '山行', '远上寒山石径斜', 1, 1, 1, 4, 1, '远上寒山石径斜，白云生处有人家。停车坐爱枫林晚，霜叶红于二月花。', '杜牧', '唐代', '描绘秋日山行所见的景色', '赞美大自然中秋色的美丽', '停车坐爱枫林晚，霜叶红于二月花', '[\"寒山\",\"白云\",\"枫林\"]', '沿着弯弯曲曲的小路上山，在那生出白云的地方居然还有几户人家。停下马车是因为喜爱深秋枫林的晚景，枫叶秋霜染过，艳比二月春花。', '这首诗描绘的是秋之色，展现出一幅动人的山林秋色图，山路、人家、白云、红叶，构成一幅和谐统一的画面，表现了作者的高怀逸兴和豪荡思致。', '/audio/shanxing.mp3', '/image/tangshi33.jpg', 267, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (54, '泊秦淮', '烟笼寒水月笼沙', 1, 3, 1, 4, 3, '烟笼寒水月笼沙，夜泊秦淮近酒家。商女不知亡国恨，隔江犹唱后庭花。', '杜牧', '唐代', '夜泊秦淮河时的感慨', '借古讽今，表达对国事的忧虑', '商女不知亡国恨，隔江犹唱后庭花', '[\"秦淮\",\"商女\",\"后庭花\"]', '浩渺寒江之上弥漫着迷蒙的烟雾，皓月的清辉洒在白色沙渚之上。入夜，我将小舟泊在秦淮河畔，临近酒家。金陵歌女似乎不知何为亡国之恨黍离之悲，竟依然在对岸吟唱着淫靡之曲《玉树后庭花》。', '此诗是诗人夜泊秦淮时触景感怀之作，借陈后主因追求荒淫享乐终至亡国的历史，讽刺那些不从中汲取教训而醉生梦死的晚唐统治者。', '/audio/boqinhuai.mp3', '/image/tangshi34.jpg', 223, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (55, '秋夕', '银烛秋光冷画屏', 1, 2, 1, 4, 2, '银烛秋光冷画屏，轻罗小扇扑流萤。天阶夜色凉如水，卧看牵牛织女星。', '杜牧', '唐代', '描写古代宫女秋夜孤寂的生活', '反映深宫女子的不幸命运', '天阶夜色凉如水，卧看牵牛织女星', '[\"银烛\",\"流萤\",\"天阶\"]', '银烛的烛光映着冷清的画屏，手执绫罗小扇扑打萤火虫。夜色里的石阶清凉如冷水，静坐凝视天河两旁的牵牛织女星。', '此诗写失意宫女孤独的生活和凄凉的心境，反映了封建时代妇女的悲惨命运，从侧面写出了封建时代妇女的痛苦心情。', '/audio/qiuxi.mp3', '/image/tangshi35.jpg', 245, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (56, '春晓', '春眠不觉晓', 1, 1, 1, 4, 1, '春眠不觉晓，处处闻啼鸟。夜来风雨声，花落知多少。', '孟浩然', '唐代', '描绘春天早晨的景象', '表现诗人对大自然的热爱和怜惜之情', '夜来风雨声，花落知多少', '[\"春眠\",\"啼鸟\",\"风雨\"]', '春日里贪睡不知不觉天就亮了，到处可以听见小鸟的鸣叫声。回想昨夜的阵阵风雨声，不知吹落了多少娇美的春花。', '这首诗语言平易浅近，自然天成，一点也看不出人工雕琢的痕迹，而言浅意浓，景真情真，就像是从诗人心灵深处流出的一股泉水，晶莹透澈。', '/audio/chunxiao.mp3', '/image/tangshi36.jpg', 389, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (57, '过故人庄', '故人具鸡黍', 1, 2, 1, 4, 2, '故人具鸡黍，邀我至田家。绿树村边合，青山郭外斜。开轩面场圃，把酒话桑麻。待到重阳日，还来就菊花。', '孟浩然', '唐代', '诗人应邀到农家做客的经过', '描绘田园风光和友情的美好', '开轩面场圃，把酒话桑麻', '[\"鸡黍\",\"田家\",\"青山\"]', '老朋友准备丰盛的饭菜，邀请我到他田舍做客。翠绿的树林围绕着村落，一脉青山在城郭外隐隐横斜。', '此诗描写农家恬静闲适的生活情景，也写老朋友的情谊，通过写田园生活的风光，写出作者对这种生活的向往，全文十分押韵。', '/audio/guogurenzhuang.mp3', '/image/tangshi37.jpg', 234, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (58, '宿建德江', '移舟泊烟渚', 1, 2, 1, 4, 2, '移舟泊烟渚，日暮客愁新。野旷天低树，江清月近人。', '孟浩然', '唐代', '诗人漫游吴越时泊舟夜宿所作', '借景抒情，表达羁旅之思', '野旷天低树，江清月近人', '[\"烟渚\",\"客愁\",\"江清\"]', '把船停泊在烟雾弥漫的沙洲旁，日暮时分新愁又涌上客子心头。原野无边无际，远处的天空比近处的树林还要低；江水清清，明月仿似更与人相亲。', '此诗先写羁旅夜泊，再叙日暮添愁；然后写到宇宙广袤宁静，明月伴人更亲，一隐一现，虚实相间，两相映衬，互为补充，构成一个特殊的意境。', '/audio/sujiandegiang.mp3', '/image/tangshi38.jpg', 256, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (59, '望洞庭湖赠张丞相', '八月湖水平', 1, 3, 1, 4, 3, '八月湖水平，涵虚混太清。气蒸云梦泽，波撼岳阳城。欲济无舟楫，端居耻圣明。坐观垂钓者，徒有羡鱼情。', '孟浩然', '唐代', '孟浩然赠给宰相张九龄的干谒诗', '通过描绘洞庭湖景色表达求仕之意', '气蒸云梦泽，波撼岳阳城', '[\"洞庭\",\"云梦\",\"岳阳\"]', '八月洞庭湖水暴涨几与岸平，水天一色交相辉映迷离难辨。云梦大泽水气蒸腾白白茫茫，波涛汹涌似乎把岳阳城撼动。', '此诗是一首投赠之作，通过描述面临烟波浩淼的洞庭湖欲渡无舟的感叹以及临渊而羡鱼的情怀，曲折地表达了诗人希望张九龄予以援引之意。', '/audio/dongtinghu.mp3', '/image/tangshi39.jpg', 198, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (60, '与诸子登岘山', '人事有代谢', 1, 3, 1, 4, 3, '人事有代谢，往来成古今。江山留胜迹，我辈复登临。水落鱼梁浅，天寒梦泽深。羊公碑尚在，读罢泪沾襟。', '孟浩然', '唐代', '与友人登岘山怀古感今', '抒发对历史变迁和人生短暂的感慨', '水落鱼梁浅，天寒梦泽深', '[\"代谢\",\"胜迹\",\"鱼梁\"]', '人间的事情都有更替变化，来来往往的时日形成古今。江山各处保留的名胜古迹，而今我们又可以登攀亲临。', '此诗因作者求仕不遇心情苦闷而作，诗人登临岘山，凭吊羊公碑，怀古伤今，抒发感慨，想到自己空有抱负，不觉分外悲伤，泪湿衣襟。', '/audio/xianshan.mp3', '/image/tangshi40.jpg', 167, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (61, '枫桥夜泊', '月落乌啼霜满天', 1, 2, 1, 4, 2, '月落乌啼霜满天，江枫渔火对愁眠。姑苏城外寒山寺，夜半钟声到客船。', '张继', '唐代', '诗人夜泊枫桥时的所见所感', '描绘江南深秋夜景和旅人愁思', '姑苏城外寒山寺，夜半钟声到客船', '[\"乌啼\",\"江枫\",\"寒山寺\"]', '月亮已落下乌鸦啼叫寒气满天，江边枫树与船上渔火，难抵我独自傍愁而眠。姑苏城外那寒山古寺，半夜里敲响的钟声传到了我乘坐的客船。', '此诗精确而细腻地描述了一个客船夜泊者对江南深秋夜景的观察和感受，勾画了月落乌啼、霜天寒夜、江枫渔火、孤舟客子等景象，有景有情有声有色。', '/audio/fengqiaoyebo.mp3', '/image/tangshi41.jpg', 312, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (62, '滁州西涧', '独怜幽草涧边生', 1, 2, 1, 4, 2, '独怜幽草涧边生，上有黄鹂深树鸣。春潮带雨晚来急，野渡无人舟自横。', '韦应物', '唐代', '韦应物任滁州刺史时所作', '描绘西涧春景和野渡所见', '春潮带雨晚来急，野渡无人舟自横', '[\"幽草\",\"黄鹂\",\"野渡\"]', '唯独喜欢涧边幽谷里生长的野草，还有那树丛深处婉转啼鸣的黄鹂。傍晚时分，春潮上涨，春雨淅沥，西涧水势顿见湍急，荒野渡口无人，只有一只小船悠闲地横在水面。', '此诗写的虽然是平常的景物，但经诗人的点染，却成了一幅意境幽深的有韵之画，还蕴含了诗人一种不在其位、不得其用的无奈与忧伤情怀。', '/audio/chuzhouxijian.mp3', '/image/tangshi42.jpg', 245, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (63, '塞下曲', '月黑雁飞高', 1, 2, 1, 4, 2, '月黑雁飞高，单于夜遁逃。欲将轻骑逐，大雪满弓刀。', '卢纶', '唐代', '描写边疆将士雪夜追敌的英勇', '展现边防将士的豪迈气概', '欲将轻骑逐，大雪满弓刀', '[\"月黑\",\"单于\",\"大雪\"]', '死寂之夜，乌云遮月，天边惊起一群大雁。原来敌军首领趁着夜色悄悄逃跑。正想要率领轻骑一路追杀，纷纷扬扬的一场大雪，已经洒满了将士们的弓刀。', '这首诗写将军雪夜准备率兵追敌的壮举，气概豪迈，诗句虽然没有直接写激烈的战斗场面，但留给了读者广阔的想象空间。', '/audio/saixiaqu.mp3', '/image/tangshi43.jpg', 223, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (64, '游园不值', '应怜屐齿印苍苔', 1, 2, 1, 4, 2, '应怜屐齿印苍苔，小扣柴扉久不开。春色满园关不住，一枝红杏出墙来。', '叶绍翁', '宋代', '诗人游园未遇主人但见春色', '通过红杏出墙展现春天生机', '春色满园关不住，一枝红杏出墙来', '[\"屐齿\",\"柴扉\",\"红杏\"]', '也许是园主担心我的木屐踩坏他那爱惜的青苔，我轻轻地敲打柴门久久不开。满园子的春色是关不住的，开得正旺的红杏有一枝枝条伸到墙外来了。', '此诗先写诗人游园看花而进不了园门，感情上是从有所期待到失望遗憾；后看到一枝红杏伸出墙外，进而领略到园中的盎然春意，感情又由失望到意外之惊喜。', '/audio/youyuanbuzhi.mp3', '/image/tangshi44.jpg', 278, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (65, '题西林壁', '横看成岭侧成峰', 1, 2, 1, 4, 2, '横看成岭侧成峰，远近高低各不同。不识庐山真面目，只缘身在此山中。', '苏轼', '宋代', '苏轼游庐山时题写于西林寺壁', '通过观山感悟人生哲理', '不识庐山真面目，只缘身在此山中', '[\"横看\",\"侧峰\",\"庐山\"]', '从正面、侧面看庐山山岭连绵起伏、山峰耸立，从远处、近处、高处、低处看庐山，庐山呈现各种不同的样子。我之所以认不清庐山真正的面目，是因为我人身处在庐山之中。', '此诗描写庐山变化多姿的面貌，并借景说理，指出观察问题应客观全面，如果主观片面，就得不出正确的结论，启迪人们认识为人处事的一个哲理。', '/audio/tixilinbi.mp3', '/image/tangshi45.jpg', 267, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (66, '从军行', '青海长云暗雪山', 1, 3, 1, 4, 3, '青海长云暗雪山，孤城遥望玉门关。黄沙百战穿金甲，不破楼兰终不还。', '王昌龄', '唐代', '描写戍边将士的英勇和决心', '展现边塞将士的豪情壮志', '黄沙百战穿金甲，不破楼兰终不还', '[\"青海\",\"玉门关\",\"楼兰\"]', '青海湖上乌云密布，遮得连绵雪山一片黯淡。边塞古城，玉门雄关，远隔千里，遥遥相望。守边将士身经百战，铠甲磨穿，壮志不灭，不打败进犯之敌，誓不返回家乡。', '这首诗表现战士们为保卫祖国矢志不渝的崇高精神，通过对边塞战事场景的描绘，表现了戍边将士的豪情壮志。', '/audio/congjunxing.mp3', '/image/tangshi46.jpg', 234, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (67, '凉州词', '葡萄美酒夜光杯', 1, 2, 1, 4, 2, '葡萄美酒夜光杯，欲饮琵琶马上催。醉卧沙场君莫笑，古来征战几人回？', '王翰', '唐代', '描写边塞将士宴饮的场景', '表现将士们豪放旷达的胸怀', '醉卧沙场君莫笑，古来征战几人回', '[\"葡萄\",\"夜光杯\",\"沙场\"]', '酒筵上甘醇的葡萄美酒盛满在夜光杯之中，正要畅饮时，马上琵琶也声声响起，仿佛催人出征。如果醉卧在沙场上，也请你不要笑话，古来出外打仗的能有几人返回家乡？', '这首诗渲染了出征前盛大华贵的酒筵以及战士们痛快豪饮的场面，表现了战士们将生死置之度外的旷达、奔放的思想感情。', '/audio/liangzhouci2.mp3', '/image/tangshi47.jpg', 256, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (68, '逢雪宿芙蓉山主人', '日暮苍山远', 1, 2, 1, 4, 2, '日暮苍山远，天寒白屋贫。柴门闻犬吠，风雪夜归人。', '刘长卿', '唐代', '诗人雪夜投宿山村的经历', '描绘冬日山村的寂静和温馨', '柴门闻犬吠，风雪夜归人', '[\"苍山\",\"白屋\",\"柴门\"]', '暮色降临山色苍茫愈觉路途远，天寒冷茅草屋显得更贫困。柴门外忽传来犬吠声声，原来是有人冒着风雪归家门。', '这首诗用极其凝炼的诗笔，描画出一幅以旅客暮夜投宿、山家风雪人归为素材的寒山夜宿图，每句诗都构成一个独立的画面。', '/audio/fengxuesu.mp3', '/image/tangshi48.jpg', 198, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (69, '江雪', '千山鸟飞绝', 1, 2, 1, 4, 2, '千山鸟飞绝，万径人踪灭。孤舟蓑笠翁，独钓寒江雪。', '柳宗元', '唐代', '柳宗元被贬永州时所作', '借寒江独钓的渔翁抒发孤寂情怀', '孤舟蓑笠翁，独钓寒江雪', '[\"千山\",\"孤舟\",\"寒江\"]', '所有的山上，飞鸟的身影已经绝迹，所有道路都不见人的踪迹。江面孤舟上，一位披戴着蓑笠的老翁，独自在漫天风雪中垂钓。', '这首诗描绘了一幅幽静寒冷的画面：在下着大雪的江面上，一叶小舟，一个老渔翁，独自在寒冷的江心垂钓，抒发了诗人政治上失意的郁闷苦恼。', '/audio/jiangxue.mp3', '/image/tangshi49.jpg', 289, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (70, '寻隐者不遇', '松下问童子', 1, 1, 1, 4, 1, '松下问童子，言师采药去。只在此山中，云深不知处。', '贾岛', '唐代', '诗人寻访隐者未遇的经历', '通过问答展现隐者的高洁形象', '只在此山中，云深不知处', '[\"松下\",\"采药\",\"云深\"]', '苍松下，我询问隐者的童子他的师傅到哪里去了？他说，师傅已经采药去了。还指着高山说，就在这座山中，可是林深云密，我也不知道他到底在哪。', '这首诗的特点是寓问于答，用词简练，以白云比隐者的高洁，以苍松喻隐者的风骨，写寻访不遇，愈衬出钦慕高仰。', '/audio/xunyinzhe.mp3', '/image/tangshi50.jpg', 267, b'1', 'admin', '2025-10-24 11:33:17.025', 'admin', '2025-10-24 11:33:17.025');
INSERT INTO `poetry_learning_content` VALUES (71, '鹊桥仙', '纤云弄巧', 1, 3, 1, 5, 3, '纤云弄巧，飞星传恨，银汉迢迢暗度。金风玉露一相逢，便胜却人间无数。', '秦观', '宋代', '借牛郎织女故事歌颂坚贞爱情', '将抒情、写景、议论融为一体', '两情若是久长时，又岂在朝朝暮暮', '[\"纤云\",\"飞星\",\"银汉\"]', '纤薄的云彩在天空中变幻多端，天上的流星传递着相思的愁怨，遥远无垠的银河今夜我悄悄渡过。在秋风白露的七夕相会，就胜过尘世间那些长相厮守却貌合神离的夫妻。', '此词议论自由流畅，通俗易懂，却又显得婉约蕴藉，余味无穷，尤其是末二句，使词的思想境界升华到一个崭新的高度，成为词中警句。', '/audio/queqiaoxian.mp3', '/image/songci16.jpg', 278, b'1', 'admin', '2025-10-24 11:36:38.257', 'admin', '2025-10-24 11:36:38.257');
INSERT INTO `poetry_learning_content` VALUES (72, '踏莎行·郴州旅舍', '雾失楼台', 1, 3, 1, 5, 3, '雾失楼台，月迷津渡，桃源望断无寻处。可堪孤馆闭春寒，杜鹃声里斜阳暮。', '秦观', '宋代', '秦观被贬郴州时所作', '抒发贬谪之痛和思乡之情', '郴江幸自绕郴山，为谁流下潇湘去', '[\"楼台\",\"津渡\",\"桃源\"]', '雾失楼台，月迷津渡，桃源望断无寻处。可堪孤馆闭春寒，杜鹃声里斜阳暮。', '这首词以委婉的笔触，抒发了作者在政治上遭受打击后的凄苦失望的心情，流露出对现实政治的不满。', '/audio/tasuoxing2.mp3', '/image/songci17.jpg', 189, b'1', 'admin', '2025-10-24 11:36:38.257', 'admin', '2025-10-24 11:36:38.257');
INSERT INTO `poetry_learning_content` VALUES (73, '满庭芳', '山抹微云', 1, 3, 1, 5, 3, '山抹微云，天连衰草，画角声断谯门。暂停征棹，聊共引离尊。多少蓬莱旧事，空回首、烟霭纷纷。', '秦观', '宋代', '写与恋人离别的伤感', '融情入景，意境深远', '斜阳外，寒鸦万点，流水绕孤村', '[\"微云\",\"衰草\",\"画角\"]', '会稽山上，云朵淡淡的像是水墨画中轻抹上去的一半；越州城外，衰草连天，无穷无际。城门楼上的号角声，时断时续。在北归的客船上，与歌妓举杯共饮，聊以话别。', '此词虽写艳情，却能融入仕途不遇，前尘似梦的身世之感，而且词中写景、抒情汇成一气，错综变化，脍炙人口。', '/audio/mantingfang.mp3', '/image/songci18.jpg', 167, b'1', 'admin', '2025-10-24 11:36:38.257', 'admin', '2025-10-24 11:36:38.257');
INSERT INTO `poetry_learning_content` VALUES (74, '苏幕遮', '燎沉香', 1, 3, 1, 5, 3, '燎沉香，消溽暑。鸟雀呼晴，侵晓窥檐语。叶上初阳干宿雨，水面清圆，一一风荷举。', '周邦彦', '宋代', '描写夏日雨后荷塘景色', '语言工丽，格律精严', '小楫轻舟，梦入芙蓉浦', '[\"沉香\",\"鸟雀\",\"风荷\"]', '细焚沉香，来消除夏天闷热潮湿的暑气。鸟雀鸣叫呼唤着晴天，拂晓时分我偷偷听它们在屋檐下的说话。荷叶上初出的阳光晒干了昨夜的雨，水面上的荷花清润圆正，荷叶迎着晨风，每一片荷叶都挺出水面。', '这首词，上片写景，下片抒情，段落极为分明。一起写静境，焚香消暑，心静自然凉，但字面上看不出痕迹。', '/audio/sumuzhe2.mp3', '/image/songci19.jpg', 156, b'1', 'admin', '2025-10-24 11:36:38.257', 'admin', '2025-10-24 11:36:38.257');
INSERT INTO `poetry_learning_content` VALUES (75, '兰陵王·柳', '柳阴直', 1, 3, 1, 5, 3, '柳阴直，烟里丝丝弄碧。隋堤上、曾见几番，拂水飘绵送行色。登临望故国，谁识京华倦客？', '周邦彦', '宋代', '借咏柳抒写离别之情', '结构严谨，音律和谐', '沉思前事，似梦里，泪暗滴', '[\"柳阴\",\"隋堤\",\"京华\"]', '正午的柳荫直直地落下，雾霭中，丝丝柳枝随风摆动。在古老的隋堤上，曾经多少次看见柳絮飞舞，把匆匆离去的人相送。每次都登上高台向故乡瞭望，杭州远隔山水一重又一重。', '全词构思萦回曲折，似浅实深，有吐不尽的心事流荡其中，无论景语、情语，都很耐人寻味，体现了周邦彦词\"富艳精工\"的特色。', '/audio/lanlingwang.mp3', '/image/songci20.jpg', 134, b'1', 'admin', '2025-10-24 11:36:38.257', 'admin', '2025-10-24 11:36:38.257');
INSERT INTO `poetry_learning_content` VALUES (76, '浣溪沙', '一曲新词酒一杯', 1, 2, 1, 5, 2, '一曲新词酒一杯，去年天气旧亭台。夕阳西下几时回？无可奈何花落去，似曾相识燕归来。小园香径独徘徊。', '晏殊', '宋代', '抒发对时光流逝的感慨', '语言清丽，情致深婉', '无可奈何花落去，似曾相识燕归来', '[\"新词\",\"亭台\",\"夕阳\"]', '听一支新曲喝一杯美酒，还是去年的天气旧日的亭台，西落的夕阳何时再回来？', '此词虽含伤春惜时之意，却实为感慨抒怀之情，悼惜残春，感伤年华的飞逝，又暗寓怀人之意。', '/audio/huanxisha1.mp3', '/image/songci21.jpg', 245, b'1', 'admin', '2025-10-24 11:36:38.257', 'admin', '2025-10-24 11:36:38.257');
INSERT INTO `poetry_learning_content` VALUES (77, '蝶恋花', '槛菊愁烟兰泣露', 1, 3, 1, 5, 3, '槛菊愁烟兰泣露，罗幕轻寒，燕子双飞去。明月不谙离恨苦，斜光到晓穿朱户。', '晏殊', '宋代', '写深秋怀人的离愁别恨', '深婉中见含蓄，广远中有蕴涵', '昨夜西风凋碧树，独上高楼，望尽天涯路', '[\"槛菊\",\"罗幕\",\"明月\"]', '栏外的菊花笼罩着一层愁惨的烟雾，兰花沾露好似默默饮泣。罗幕闲垂，空气微寒，一双燕子飞去。明月不明白离别之苦，斜斜的银辉直到破晓还穿入朱户。', '此词写深秋怀人，是宋词的名篇之一，也是晏殊的代表作之一。上片描写苑中景物，运用移情于景的手法，注入主人公的感情，点出离恨；下片承离恨而来，通过高楼独望生动地表现出主人公望眼欲穿的神态。', '/audio/dielianhua2.mp3', '/image/songci22.jpg', 223, b'1', 'admin', '2025-10-24 11:36:38.257', 'admin', '2025-10-24 11:36:38.257');
INSERT INTO `poetry_learning_content` VALUES (78, '破阵子', '燕子来时新社', 1, 2, 1, 5, 2, '燕子来时新社，梨花落后清明。池上碧苔三四点，叶底黄鹂一两声，日长飞絮轻。巧笑东邻女伴，采桑径里逢迎。疑怪昨宵春梦好，元是今朝斗草赢，笑从双脸生。', '晏殊', '宋代', '描写清明时节田园风光', '笔调清新，生活气息浓厚', '疑怪昨宵春梦好，元是今朝斗草赢，笑从双脸生', '[\"燕子\",\"梨花\",\"黄鹂\"]', '燕子飞来正赶上社祭之时，梨花落去之后又迎来了清明。几片碧苔点缀着池中清水，树枝掩映下的黄鹂偶尔歌唱两声，白昼越来越长，随处可见柳絮飘飞。', '此词通过描写清明时节的一个生活片断，反映出少女身上显示的青春活力，充满着一种欢乐的气氛。全词纯用白描，笔调活泼，风格朴实，形象生动，展示了少女的纯洁心灵。', '/audio/pozhenzi.mp3', '/image/songci23.jpg', 198, b'1', 'admin', '2025-10-24 11:36:38.257', 'admin', '2025-10-24 11:36:38.257');
INSERT INTO `poetry_learning_content` VALUES (79, '临江仙', '梦后楼台高锁', 1, 3, 1, 5, 3, '梦后楼台高锁，酒醒帘幕低垂。去年春恨却来时。落花人独立，微雨燕双飞。', '晏几道', '宋代', '追忆曾经的恋情', '深情婉转，语言精丽', '当时明月在，曾照彩云归', '[\"楼台\",\"帘幕\",\"落花\"]', '深夜梦回楼台朱门紧锁，酒意消退但见帘幕重重低垂。去年的春恨涌上心头时，人在落花纷扬中幽幽独立，燕子在微风细雨中双双翱飞。', '此词写作者与恋人别后故地重游，引起对恋人的无限怀念，抒发对歌女小蘋的挚爱之情。上片描写人去楼空的寂寞景象，以及年年伤春伤别的凄凉怀抱。', '/audio/linjiangxian.mp3', '/image/songci24.jpg', 234, b'1', 'admin', '2025-10-24 11:36:38.257', 'admin', '2025-10-24 11:36:38.257');
INSERT INTO `poetry_learning_content` VALUES (80, '鹧鸪天', '彩袖殷勤捧玉钟', 1, 3, 1, 5, 3, '彩袖殷勤捧玉钟，当年拚却醉颜红。舞低杨柳楼心月，歌尽桃花扇底风。', '晏几道', '宋代', '写与歌女久别重逢的喜悦', '工于言情，清丽婉转', '今宵剩把银釭照，犹恐相逢是梦中', '[\"彩袖\",\"玉钟\",\"桃花扇\"]', '当年你殷勤劝酒频举玉盅，我开怀畅饮喝得酒醉脸通红。翩翩起舞直到楼顶月坠楼外树梢，尽兴唱歌累得无力把桃花扇摇动。', '此词写词人与一个女子久别重逢的情景，以相逢抒别恨。全词不过五十几个字，而能造成两种境界，互相补充配合，或实或虚，既有彩色的绚烂，又有声音的谐美，足见作者词艺之高妙。', '/audio/zhegutian2.mp3', '/image/songci25.jpg', 189, b'1', 'admin', '2025-10-24 11:36:38.257', 'admin', '2025-10-24 11:36:38.257');
INSERT INTO `poetry_learning_content` VALUES (81, '扬州慢', '淮左名都', 1, 3, 1, 5, 3, '淮左名都，竹西佳处，解鞍少驻初程。过春风十里，尽荠麦青青。自胡马窥江去后，废池乔木，犹厌言兵。', '姜夔', '宋代', '路过扬州感怀故国沦丧', '抒发黍离之悲，格调凄清', '念桥边红药，年年知为谁生', '[\"淮左\",\"竹西\",\"胡马\"]', '扬州是淮河东边著名的大都，在竹西亭美好的住处，解下马鞍稍作停留，这是最初的路程。经过春风吹遍了扬州十里，都是荠菜麦子一派青青。', '此词写词人因路过扬州，目睹了战争洗劫后扬州的萧条景象，抚今追昔，悲叹今日的荒凉，追忆昔日的繁华，发为吟咏，以寄托对扬州昔日繁华的怀念和对今日山河破碎的哀思。', '/audio/yangzhouman.mp3', '/image/songci26.jpg', 178, b'1', 'admin', '2025-10-24 11:36:38.257', 'admin', '2025-10-24 11:36:38.257');
INSERT INTO `poetry_learning_content` VALUES (82, '暗香', '旧时月色', 1, 3, 1, 5, 3, '旧时月色，算几番照我，梅边吹笛？唤起玉人，不管清寒与攀摘。何逊而今渐老，都忘却、春风词笔。', '姜夔', '宋代', '咏梅怀人之作', '托物言志，意境清空', '长记曾携手处，千树压、西湖寒碧', '[\"月色\",\"梅花\",\"西湖\"]', '昔日皎洁的月色，曾经多少次映照着我，对着梅花吹得玉笛声韵谐和。笛声唤起了美丽的佳人，跟我一道攀折梅花，不顾清冷寒瑟。而今我像何逊已渐渐衰老，往日春风般绚丽的辞采和文笔，全都已经忘记。', '此词咏梅怀人，将梅花与\"玉人\"融为一体，以梅花之冰清玉洁喻\"玉人\"之美，以梅花之随风飘落喻\"玉人\"之离去，思今念往，寄意深远。', '/audio/anxiang.mp3', '/image/songci27.jpg', 156, b'1', 'admin', '2025-10-24 11:36:38.257', 'admin', '2025-10-24 11:36:38.257');
INSERT INTO `poetry_learning_content` VALUES (83, '疏影', '苔枝缀玉', 1, 3, 1, 5, 3, '苔枝缀玉，有翠禽小小，枝上同宿。客里相逢，篱角黄昏，无言自倚修竹。昭君不惯胡沙远，但暗忆、江南江北。', '姜夔', '宋代', '与《暗香》为姊妹篇', '用典贴切，词境幽美', '等恁时、重觅幽香，已入小窗横幅', '[\"苔枝\",\"翠禽\",\"昭君\"]', '长满苔藓的梅枝上点点梅花如玉，娇小的翠鸟在枝上伴梅同宿。我在客旅他乡时见到她的倩影，像佳人在夕阳斜映篱笆的黄昏中，默默孤独，倚着修长的翠竹。', '此词与《暗香》是姊妹篇，都是咏梅花的。上片写梅花形神兼美，表现梅花高洁品格；下片写梅花凋落，表现对梅花的惋惜之情。全词用典巧妙，意境幽美。', '/audio/shuying.mp3', '/image/songci28.jpg', 145, b'1', 'admin', '2025-10-24 11:36:38.257', 'admin', '2025-10-24 11:36:38.257');
INSERT INTO `poetry_learning_content` VALUES (84, '卜算子', '我住长江头', 1, 2, 1, 5, 2, '我住长江头，君住长江尾。日日思君不见君，共饮长江水。此水几时休，此恨何时已。只愿君心似我心，定不负相思意。', '李之仪', '宋代', '写女子对情人的思念', '语言明白如话，感情真挚深沉', '只愿君心似我心，定不负相思意', '[\"长江头\",\"长江尾\",\"相思\"]', '我住在长江源头，君住在长江之尾。天天想念你却总是见不到你，却共同饮着长江之水。', '此词以长江水为抒情线索，语言明白如话，句式复叠回环，感情深沉真挚，深得民歌的神情风味，又具有文人词构思新巧，体现出灵秀隽永、玲珑晶莹的风神。', '/audio/busuanzi2.mp3', '/image/songci29.jpg', 267, b'1', 'admin', '2025-10-24 11:36:38.257', 'admin', '2025-10-24 11:36:38.257');
INSERT INTO `poetry_learning_content` VALUES (85, '青玉案', '凌波不过横塘路', 1, 3, 1, 5, 3, '凌波不过横塘路，但目送、芳尘去。锦瑟华年谁与度？月桥花院，琐窗朱户，只有春知处。', '贺铸', '宋代', '写偶遇佳人的怅惘之情', '辞藻工丽，即景抒情', '试问闲情都几许？一川烟草，满城风絮，梅子黄时雨', '[\"凌波\",\"横塘\",\"锦瑟\"]', '轻移莲步不再越过横塘路，只有用目力相送，她像芳尘一样飘去。正是青春年华时候，可什么人能与她一起欢度？是月台，是花榭，是雕饰的窗，是紧闭的朱户，这只有春天才会知道她的居处。', '此词通过对暮春景色的描写，抒发作者所感到的闲愁。上片写路遇佳人而不知所往的怅惘情景，也含蓄地流露其沉沦下僚、怀才不遇的感慨；下片写因思慕而引起的无限愁思。', '/audio/qingyuan2.mp3', '/image/songci30.jpg', 223, b'1', 'admin', '2025-10-24 11:36:38.257', 'admin', '2025-10-24 11:36:38.257');
INSERT INTO `poetry_learning_content` VALUES (86, '踏莎行', '情似游丝', 1, 3, 1, 5, 3, '情似游丝，人如飞絮。泪珠阁定空相觑。一溪烟柳万丝垂，无因系得兰舟住。', '周紫芝', '宋代', '写离别时的缠绵情意', '比喻新颖，抒情婉转', '雁过斜阳，草迷烟渚。如今已是愁无数', '[\"游丝\",\"飞絮\",\"烟柳\"]', '离情缭乱似漫空漂浮的游丝，离人漂泊如随风飞舞的柳絮。离别时凝定了泪眼空自相觑。整条河溪烟雾弥漫杨柳树万丝千缕，却无法将那木兰舟维系。', '此词上片写离别时的情景，情似游丝，泪眼相觑；下片写别后相思，雁过斜阳，草迷烟渚，已是愁无数。全词凄迷哀婉，愁思无限。', '/audio/tasuoxing3.mp3', '/image/songci31.jpg', 167, b'1', 'admin', '2025-10-24 11:36:38.257', 'admin', '2025-10-24 11:36:38.257');
INSERT INTO `poetry_learning_content` VALUES (87, '钗头凤', '世情薄', 1, 3, 1, 5, 3, '世情薄，人情恶，雨送黄昏花易落。晓风干，泪痕残。欲笺心事，独语斜阑。难，难，难！', '唐婉', '宋代', '唐婉回应陆游《钗头凤》之作', '抒发被迫分离的痛苦和无奈', '人成各，今非昨，病魂常似秋千索', '[\"世情\",\"人情\",\"泪痕\"]', '世事炎凉，黄昏中下着雨，打落片片桃花，这凄凉的情景中人的心也不禁忧伤。晨风吹干了昨晚的泪痕，当我想把心事写下来的时候，却不能够办到，只能倚着斜栏，心底里向着远方的你呼唤。', '此词描写了唐婉与陆游被迫分开后的种种思念之情，抒发了作者内心的凄苦寂寞。全词哀婉动人，情感复杂，表达了作者对于封建礼教的深恶痛绝。', '/audio/chaitoufeng2.mp3', '/image/songci32.jpg', 245, b'1', 'admin', '2025-10-24 11:36:38.257', 'admin', '2025-10-24 11:36:38.257');
INSERT INTO `poetry_learning_content` VALUES (88, '水龙吟', '似花还似非花', 1, 3, 1, 5, 3, '似花还似非花，也无人惜从教坠。抛家傍路，思量却是，无情有思。萦损柔肠，困酣娇眼，欲开还闭。', '苏轼', '宋代', '咏杨花寓身世之感', '咏物拟人，缠绵悱恻', '细看来，不是杨花，点点是离人泪', '[\"杨花\",\"柔肠\",\"离人泪\"]', '非常像花又好像不是花，无人怜惜任凭衰零坠地。把它抛离在家乡路旁，细细思量仿佛又是无情，实际上则饱含深情。受伤柔肠婉曲娇眼迷离，想要开放却又紧紧闭上。', '此词咏杨柳，上片主要写杨花的飘忽不定的际遇和不即不离的神态；下片与上片相呼应，主要是写柳絮的归宿，感情色彩更加浓厚。全词不仅写出了杨花的形神，而且采用拟人的艺术手法，把咏物与写人巧妙地结合起来。', '/audio/shuilongyin.mp3', '/image/songci33.jpg', 198, b'1', 'admin', '2025-10-24 11:36:38.257', 'admin', '2025-10-24 11:36:38.257');
INSERT INTO `poetry_learning_content` VALUES (89, '八声甘州', '对潇潇暮雨洒江天', 1, 3, 1, 5, 3, '对潇潇暮雨洒江天，一番洗清秋。渐霜风凄紧，关河冷落，残照当楼。是处红衰翠减，苒苒物华休。唯有长江水，无语东流。', '柳永', '宋代', '写羁旅行役之苦', '气象辽阔，声情激越', '想佳人妆楼颙望，误几回、天际识归舟', '[\"暮雨\",\"霜风\",\"长江\"]', '面对着潇潇暮雨从天空洒落在江面上，经过一番雨洗的秋景，分外寒凉清朗。凄凉的霜风一阵紧似一阵，关山江河一片冷清萧条，落日的余光照耀在高楼上。到处红花凋零翠叶枯落，一切美好的景物渐渐地衰残。', '此词抒写了作者漂泊江湖的愁思和仕途失意的悲慨。上片描绘了雨后清秋的傍晚，关河冷落夕阳斜照的凄凉之景；下片抒写词人久客他乡急切思念归家之情。', '/audio/bashengganzhou.mp3', '/image/songci34.jpg', 178, b'1', 'admin', '2025-10-24 11:36:38.257', 'admin', '2025-10-24 11:36:38.257');
INSERT INTO `poetry_learning_content` VALUES (90, '\"爱\"字解析', '文言实词学习', 3, 1, 2, 7, 1, '爱在文言文中有多种含义：1、喜爱 2、怜惜 3、吝啬', NULL, NULL, '文言文基础实词教学', '爱可以作为动词表示喜爱、怜惜，也可作形容词表示吝啬', '1、爱其子，择师而教之 2、国事至此，予不得爱身 3、百姓皆以王为爱也', '[\"喜爱\",\"怜惜\",\"吝啬\"]', '爱的含义：1、喜爱 2、怜惜 3、吝啬', '爱是文言文中使用频率很高的实词，需要根据上下文判断具体含义。', '/audio/ai.mp3', '/image/shici1.jpg', 145, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (91, '\"安\"字解析', '实词多义学习', 3, 1, 2, 7, 1, '安在文言文中含义丰富：1、安全 2、安逸 3、怎么', NULL, NULL, '文言文多义实词教学', '安可以作形容词、动词、疑问代词等', '1、风雨不动安如山 2、君子食无求饱，居无求安 3、燕雀安知鸿鹄之志哉', '[\"安全\",\"安逸\",\"怎么\"]', '安的含义：1、安全稳固 2、安逸舒适 3、怎么哪里', '安字用法灵活，既有实义又有虚化用法，需要重点掌握。', '/audio/an.mp3', '/image/shici2.jpg', 134, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (92, '\"被\"字解析', '实词用法详解', 3, 2, 2, 7, 2, '被在文言文中主要含义：1、被子 2、覆盖 3、遭受', NULL, NULL, '文言文实词多义教学', '被可以作为名词、动词、介词使用', '1、翡翠珠被 2、凝霜被野草 3、信而见疑，忠而被谤', '[\"被子\",\"覆盖\",\"遭受\"]', '被的含义：1、被子 2、覆盖 3、遭受', '被字从名词逐渐虚化为介词，体现了汉语词汇的发展演变。', '/audio/bei.mp3', '/image/shici3.jpg', 123, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (93, '\"倍\"字解析', '实词古今异义', 3, 2, 2, 7, 2, '倍在文言文中含义：1、加倍 2、背叛', NULL, NULL, '文言文古今异义词教学', '倍可以表示倍数，也可通\"背\"表示背叛', '1、事半功倍 2、愿伯具言臣之不敢倍德也', '[\"加倍\",\"背叛\"]', '倍的含义：1、加倍 2、背叛', '倍字的背叛义项在现代汉语中已消失，需要注意古今差异。', '/audio/bei2.mp3', '/image/shici4.jpg', 112, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (94, '\"本\"字解析', '实词本义引申', 3, 2, 2, 7, 2, '本在文言文中含义：1、草木根 2、根本 3、本来', NULL, NULL, '文言文本义引申义教学', '本从草木根的本义发展出多种引申义', '1、木水之有本原 2、君子务本 3、本在冀州之南', '[\"根本\",\"基础\",\"本来\"]', '本的含义：1、草木根 2、根本基础 3、本来原来', '本字体现了汉字从具体到抽象的引申规律，理解本义有助于掌握引申义。', '/audio/ben.mp3', '/image/shici5.jpg', 156, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (95, '\"鄙\"字解析', '实词古今差异', 3, 2, 2, 7, 2, '鄙在文言文中含义：1、边远地方 2、鄙陋 3、轻视', NULL, NULL, '文言文古今词义变化教学', '鄙从地理概念发展为道德评价', '1、蜀之鄙有二僧 2、肉食者鄙 3、过我而不假道，鄙我也', '[\"边远\",\"鄙陋\",\"轻视\"]', '鄙的含义：1、边远地方 2、见识浅陋 3、轻视看不起', '鄙字的词义经历了从具体到抽象，从中性到贬义的变化过程。', '/audio/bi.mp3', '/image/shici6.jpg', 134, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (96, '\"兵\"字解析', '军事类实词', 3, 2, 2, 7, 2, '兵在文言文中含义：1、兵器 2、士兵 3、战争', NULL, NULL, '文言文军事词汇教学', '兵可以指武器、军人、军事行动等', '1、收天下之兵 2、草木皆兵 3、兵者，国之大事', '[\"兵器\",\"士兵\",\"战争\"]', '兵的含义：1、武器 2、士兵军队 3、军事战争', '兵字体现了古代军事文化的特点，词义范围比现代汉语更广。', '/audio/bing.mp3', '/image/shici7.jpg', 167, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (97, '\"病\"字解析', '实词多义辨析', 3, 2, 2, 7, 2, '病在文言文中含义：1、疾病 2、困苦 3、缺点', NULL, NULL, '文言文多义词教学', '病可以表示生理疾病，也可引申为其他方面的毛病', '1、病万变，药亦万变 2、向吾不为斯役，则久已病矣 3、不如舜，不如周公，吾之病也', '[\"疾病\",\"困苦\",\"缺点\"]', '病的含义：1、疾病 2、困苦疲惫 3、缺点毛病', '病字的词义从具体疾病扩展到各种不良状态，体现了词义的泛化。', '/audio/bing2.mp3', '/image/shici8.jpg', 145, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (98, '\"察\"字解析', '观察类实词', 3, 2, 2, 7, 2, '察在文言文中含义：1、观察 2、明察 3、考察', NULL, NULL, '文言文认知动词教学', '察强调仔细观看和深入理解', '1、明足以察秋毫之末 2、小大之狱，虽不能察，必以情 3、察邻国之政', '[\"观察\",\"明察\",\"考察\"]', '察的含义：1、仔细观察 2、明察了解 3、考察调查', '察字体现了古代对观察认识的重视，词义与现代汉语基本一致。', '/audio/cha.mp3', '/image/shici9.jpg', 123, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (99, '\"朝\"字解析', '多音多义实词', 3, 2, 2, 7, 2, '朝在文言文中读音和含义：1、早晨 2、朝廷 3、朝拜', NULL, NULL, '文言文多音字教学', '朝根据读音不同有不同含义', '1、朝服衣冠 2、于是入朝见威王 3、皆朝于齐', '[\"早晨\",\"朝廷\",\"朝拜\"]', '朝的含义：读zhāo时指早晨，读cháo时指朝廷、朝拜', '朝是典型的多音多义词，需要根据上下文确定读音和含义。', '/audio/chao.mp3', '/image/shici10.jpg', 178, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (100, '\"诚\"字解析', '情态实词学习', 3, 3, 2, 7, 3, '诚在文言文中含义：1、真诚 2、确实 3、如果确实', NULL, NULL, '文言文情态副词教学', '诚既可以作形容词，也可作副词', '1、帝感其诚 2、此诚危急存亡之秋也 3、诚能见可欲', '[\"真诚\",\"确实\",\"如果\"]', '诚的含义：1、真诚诚实 2、确实的确 3、如果确实', '诚字从形容词发展为副词，体现了实词虚化的语言现象。', '/audio/cheng.mp3', '/image/shici11.jpg', 156, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (101, '\"除\"字解析', '实词多义详解', 3, 3, 2, 7, 3, '除在文言文中含义：1、台阶 2、清除 3、任命', NULL, NULL, '文言文多义词深度教学', '除的本义是台阶，发展出多种引申义', '1、扶辇下除 2、除恶务尽 3、予除右丞相兼枢密使', '[\"台阶\",\"清除\",\"任命\"]', '除的含义：1、台阶 2、清除去掉 3、拜官任职', '除字的任命义项比较特殊，需要重点记忆和理解。', '/audio/chu.mp3', '/image/shici12.jpg', 134, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (102, '\"辞\"字解析', '言语类实词', 3, 3, 2, 7, 3, '辞在文言文中含义：1、言辞 2、推辞 3、告别', NULL, NULL, '文言文言语动词教学', '辞与言语、礼仪密切相关', '1、其文约，其辞微 2、莫辞更坐弹一曲 3、辞楼下殿，辇来于秦', '[\"言辞\",\"推辞\",\"告别\"]', '辞的含义：1、言辞文辞 2、推辞拒绝 3、告别离开', '辞字反映了古代重视言辞表达和礼仪规范的文化特点。', '/audio/ci.mp3', '/image/shici13.jpg', 145, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (103, '\"从\"字解析', '多义实词辨析', 3, 3, 2, 7, 3, '从在文言文中含义：1、跟随 2、听从 3、由自', NULL, NULL, '文言文多义动词教学', '从的核心义是跟随，发展出多种相关含义', '1、一狼得骨止，一狼仍从 2、小惠未遍，民弗从也 3、从此道至吾军', '[\"跟随\",\"听从\",\"由自\"]', '从的含义：1、跟随 2、听从顺从 3、从由', '从字用法灵活，既有实义动词用法，也有介词用法。', '/audio/cong.mp3', '/image/shici14.jpg', 167, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (104, '\"殆\"字解析', '抽象实词学习', 3, 3, 2, 7, 3, '殆在文言文中含义：1、危险 2、大概 3、几乎', NULL, NULL, '文言文抽象义词教学', '殆从危险义虚化为表推测的副词', '1、知彼知己，百战不殆 2、此殆天所以资将军 3、扬州城下，进退不由，殆例送死', '[\"危险\",\"大概\",\"几乎\"]', '殆的含义：1、危险 2、大概可能 3、几乎差不多', '殆字的词义演变体现了从具体到抽象的虚化过程。', '/audio/dai.mp3', '/image/shici15.jpg', 123, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (105, '\"当\"字解析', '多功能实词', 3, 3, 2, 7, 3, '当在文言文中含义：1、对着 2、抵挡 3、应当', NULL, NULL, '文言文多功能词教学', '当可以作动词、介词、助动词等', '1、当窗理云鬓 2、非刘豫州莫可以当曹操者 3、当立者乃公子扶苏', '[\"对着\",\"抵挡\",\"应当\"]', '当的含义：1、对着面对 2、抵挡阻挡 3、应当应该', '当字功能多样，需要根据句子结构判断其词性和含义。', '/audio/dang.mp3', '/image/shici16.jpg', 178, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (106, '\"道\"字解析', '哲学类实词', 3, 3, 2, 7, 3, '道在文言文中含义：1、道路 2、道理 3、说', NULL, NULL, '文言文哲学词汇教学', '道是重要的哲学概念，含义丰富', '1、道阻且长 2、师者，所以传道受业解惑也 3、不足为外人道也', '[\"道路\",\"道理\",\"说\"]', '道的含义：1、道路 2、道理规律 3、说讲', '道字体现了中国古代哲学思想，词义从具体到抽象多层次。', '/audio/dao.mp3', '/image/shici17.jpg', 189, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (107, '\"得\"字解析', '常用实词详解', 3, 2, 2, 7, 2, '得在文言文中含义：1、得到 2、能够 3、合适', NULL, NULL, '文言文高频词教学', '得是使用频率很高的多义词', '1、求得其善者 2、不得其门而入 3、此言得之', '[\"得到\",\"能够\",\"合适\"]', '得的含义：1、获得得到 2、能够可以 3、得当合适', '得字从实义动词发展为能愿动词，体现了语法化过程。', '/audio/de.mp3', '/image/shici18.jpg', 156, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (108, '\"度\"字解析', '多音多义实词', 3, 3, 2, 7, 3, '度在文言文中读音和含义：1、量长短 2、限度 3、度过', NULL, NULL, '文言文多音多义词教学', '度根据读音和语境有不同含义', '1、先自度其足 2、生之有时而用之亡度 3、春风不度玉门关', '[\"量度\",\"限度\",\"度过\"]', '度的含义：读duó时指量度，读dù时指限度、度过', '度字的多音多义现象需要结合具体语境理解。', '/audio/du.mp3', '/image/shici19.jpg', 134, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (109, '\"非\"字解析', '否定类实词', 3, 2, 2, 7, 2, '非在文言文中含义：1、不对 2、不是 3、反对', NULL, NULL, '文言文否定词教学', '非是重要的否定词，用法多样', '1、是非之心 2、人非生而知之者 3、非汤武而薄周孔', '[\"不对\",\"不是\",\"反对\"]', '非的含义：1、错误不对 2、不是 3、非难反对', '非字作为否定词，在文言文中使用广泛，需要熟练掌握。', '/audio/fei.mp3', '/image/shici20.jpg', 167, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (110, '\"复\"字解析', '复杂实词学习', 3, 3, 2, 7, 3, '复在文言文中含义：1、回来 2、恢复 3、又', NULL, NULL, '文言文复杂词义教学', '复既有实义又有虚化用法', '1、昭王南征而不复 2、更若役，复若赋 3、复前行，欲穷其林', '[\"回来\",\"恢复\",\"又\"]', '复的含义：1、返回回来 2、恢复 3、又再', '复字的虚化用法在现代汉语中仍很常见，需要重点掌握。', '/audio/fu.mp3', '/image/shici21.jpg', 145, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (111, '\"负\"字解析', '多义实词深度', 3, 3, 2, 7, 3, '负在文言文中含义：1、背 2、依仗 3、辜负', NULL, NULL, '文言文多义词深度解析', '负的本义是背负，发展出多种引申义', '1、命夸娥氏二子负二山 2、秦贪，负其强 3、誓天不相负', '[\"背负\",\"依仗\",\"辜负\"]', '负的含义：1、背驮 2、依仗凭借 3、辜负违背', '负字的词义既有具体动作，也有抽象含义，理解时要注意语境。', '/audio/fu2.mp3', '/image/shici22.jpg', 123, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (112, '\"盖\"字解析', '虚化实词学习', 3, 3, 2, 7, 3, '盖在文言文中含义：1、盖子 2、超过 3、大概', NULL, NULL, '文言文实词虚化教学', '盖从具体名词发展为副词连词', '1、员径八尺，合盖隆起 2、力拔山兮气盖世 3、盖余所至，比好游者尚不能十一', '[\"盖子\",\"超过\",\"大概\"]', '盖的含义：1、盖子 2、超过压倒 3、大概', '盖字的副词用法比较特殊，需要重点记忆和理解。', '/audio/gai.mp3', '/image/shici23.jpg', 134, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (113, '\"故\"字解析', '多义实词综合', 3, 3, 2, 7, 3, '故在文言文中含义：1、旧 2、原因 3、所以', NULL, NULL, '文言文多义词综合教学', '故用法灵活，含义丰富', '1、温故而知新 2、既克，公问其故 3、故不为苟得也', '[\"旧的\",\"原因\",\"所以\"]', '故的含义：1、旧的老的 2、原因缘故 3、所以因此', '故字既有实词用法，也有虚词用法，需要根据上下文区分。', '/audio/gu.mp3', '/image/shici24.jpg', 156, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (114, '\"固\"字解析', '情态实词详解', 3, 3, 2, 7, 3, '固在文言文中含义：1、坚固 2、本来 3、坚决', NULL, NULL, '文言文情态词教学', '固从形容词发展为副词', '1、江山险固 2、固不如也 3、蔺相如固止之', '[\"坚固\",\"本来\",\"坚决\"]', '固的含义：1、坚固牢固 2、本来原来 3、坚决坚定', '固字的副词用法体现了实词的语法化现象。', '/audio/gu2.mp3', '/image/shici25.jpg', 145, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (115, '\"顾\"字解析', '动作实词学习', 3, 2, 2, 7, 2, '顾在文言文中含义：1、回头看 2、照顾 3、但是', NULL, NULL, '文言文动作动词教学', '顾的核心义是回头看，发展出多种引申义', '1、顾野有麦场 2、三岁贯女，莫我肯顾 3、顾吾念之', '[\"回头看\",\"照顾\",\"但是\"]', '顾的含义：1、回头看 2、照顾关心 3、但是不过', '顾字的词义从具体动作发展为抽象关系，体现了词义演变规律。', '/audio/gu3.mp3', '/image/shici26.jpg', 167, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (116, '\"归\"字解析', '趋向实词详解', 3, 2, 2, 7, 2, '归在文言文中含义：1、女子出嫁 2、返回 3、归属', NULL, NULL, '文言文趋向动词教学', '归的基本义是返回，有多个相关含义', '1、之子于归 2、将军百战死，壮士十年归 3、众士慕仰，若水之归海', '[\"出嫁\",\"返回\",\"归属\"]', '归的含义：1、女子出嫁 2、返回 3、归附归属', '归字反映了古代的社会文化和价值观念。', '/audio/gui.mp3', '/image/shici27.jpg', 134, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (117, '\"国\"字解析', '政治类实词', 3, 2, 2, 7, 2, '国在文言文中含义：1、国家 2、国都 3、封地', NULL, NULL, '文言文政治词汇教学', '国的概念在古代有特定含义', '1、国破山河在 2、去国怀乡 3、孟尝君就国于薛', '[\"国家\",\"国都\",\"封地\"]', '国的含义：1、国家 2、国都京城 3、诸侯封地', '国字的概念与现代有所不同，需要注意历史背景。', '/audio/guo.mp3', '/image/shici28.jpg', 178, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (118, '\"过\"字解析', '多义动词学习', 3, 2, 2, 7, 2, '过在文言文中含义：1、经过 2、超过 3、过错', NULL, NULL, '文言文多义动词教学', '过可以表示空间经过，也可表示程度超过', '1、过秦汉之故都 2、过犹不及 3、则知明而行无过矣', '[\"经过\",\"超过\",\"过错\"]', '过的含义：1、经过 2、超过过分 3、过错错误', '过字的词义既有中性描述，也有贬义评价。', '/audio/guo2.mp3', '/image/shici29.jpg', 156, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (119, '\"何\"字解析', '疑问实词详解', 3, 2, 2, 7, 2, '何在文言文中含义：1、什么 2、为什么 3、怎么', NULL, NULL, '文言文疑问词教学', '何是重要的疑问代词，用法灵活', '1、何事不语？ 2、肉食者谋之，又何间焉？ 3、徐公何能及君也？', '[\"什么\",\"为什么\",\"怎么\"]', '何的含义：1、什么 2、为什么 3、怎么', '何字在疑问句中位置灵活，需要熟悉各种疑问句式。', '/audio/he.mp3', '/image/shici30.jpg', 189, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (120, '\"恨\"字解析', '情感实词学习', 3, 2, 2, 7, 2, '恨在文言文中含义：1、遗憾 2、怨恨', NULL, NULL, '文言文情感词汇教学', '恨的感情强度在古代比现代弱', '1、未尝不叹息痛恨于桓灵也 2、天长地久有时尽，此恨绵绵无绝期', '[\"遗憾\",\"怨恨\"]', '恨的含义：1、遗憾后悔 2、怨恨', '恨字的感情色彩古今有差异，需要注意区分。', '/audio/hen.mp3', '/image/shici31.jpg', 134, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (121, '\"胡\"字解析', '外来实词学习', 3, 2, 2, 7, 2, '胡在文言文中含义：1、北方民族 2、为什么', NULL, NULL, '文言文外来词教学', '胡既指北方民族，也作疑问词', '1、胡人不敢南下而牧马 2、不稼不穑，胡取禾三百廛兮？', '[\"胡人\",\"为什么\"]', '胡的含义：1、古代北方民族 2、为什么何故', '胡字的疑问用法来自北方民族语言的影响。', '/audio/hu.mp3', '/image/shici32.jpg', 123, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (122, '\"患\"字解析', '心理实词详解', 3, 2, 2, 7, 2, '患在文言文中含义：1、忧虑 2、祸患', NULL, NULL, '文言文心理动词教学', '患可以作动词和名词', '1、不患寡而患不均 2、敌存而惧，敌去而舞，废备自盈，只益为愈', '[\"忧虑\",\"祸患\"]', '患的含义：1、忧虑担心 2、祸患灾难', '患字体现了古人居安思危的智慧。', '/audio/huan.mp3', '/image/shici33.jpg', 145, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (123, '\"或\"字解析', '不定实词学习', 3, 3, 2, 7, 3, '或在文言文中含义：1、有人 2、有时 3、或许', NULL, NULL, '文言文不定代词教学', '或表示不确定的人、时、可能性', '1、或以为死，或以为亡 2、或王命急宣 3、予尝求古仁人之心，或异二者之为', '[\"有人\",\"有时\",\"或许\"]', '或的含义：1、有人 2、有时 3、或许也许', '或字的不定指用法在现代汉语中已较少使用。', '/audio/huo.mp3', '/image/shici34.jpg', 156, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (124, '\"疾\"字解析', '多义实词综合', 3, 2, 2, 7, 2, '疾在文言文中含义：1、疾病 2、快速 3、痛恨', NULL, NULL, '文言文多义词综合教学', '疾可以表示疾病、速度、情感等', '1、疾在腠理 2、虽乘奔御风，不以疾也 3、君子疾夫舍曰欲之而必为之辞', '[\"疾病\",\"快速\",\"痛恨\"]', '疾的含义：1、疾病 2、快速 3、痛恨厌恶', '疾字的多义性反映了古人观察事物的多个角度。', '/audio/ji.mp3', '/image/shici35.jpg', 167, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (125, '\"及\"字解析', '关系实词学习', 3, 2, 2, 7, 2, '及在文言文中含义：1、赶上 2、等到 3、和', NULL, NULL, '文言文关系词教学', '及可以表示时间、空间、逻辑关系', '1、怀王悔，追张仪，不及 2、及反，市罢 3、太子及宾客知其事者', '[\"赶上\",\"等到\",\"和\"]', '及的含义：1、赶上达到 2、等到 3、和与', '及字的功能多样，需要根据语境判断具体含义。', '/audio/ji2.mp3', '/image/shici36.jpg', 178, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (126, '\"即\"字解析', '时间实词详解', 3, 3, 2, 7, 3, '即在文言文中含义：1、靠近 2、立即 3、就是', NULL, NULL, '文言文时间词教学', '即强调时间的紧接和空间的接近', '1、夜半，童自转，以缚即炉火烧绝之 2、太守即遣人随其往 3、梁父即楚将项燕', '[\"靠近\",\"立即\",\"就是\"]', '即的含义：1、靠近 2、立即马上 3、就是', '即字体现了时间和空间的密切联系。', '/audio/ji3.mp3', '/image/shici37.jpg', 145, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (127, '\"既\"字解析', '完成实词学习', 3, 3, 2, 7, 3, '既在文言文中含义：1、已经 2、既然 3、尽', NULL, NULL, '文言文完成体教学', '既表示动作的完成或状态的实现', '1、既克，公问其故 2、既来之，则安之 3、言未既，有笑于列者曰', '[\"已经\",\"既然\",\"尽\"]', '既的含义：1、已经 2、既然 3、完全尽', '既字是理解文言文时态的重要标志。', '/audio/ji4.mp3', '/image/shici38.jpg', 134, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (128, '\"假\"字解析', '多义实词深度', 3, 3, 2, 7, 3, '假在文言文中含义：1、借 2、凭借 3、虚假', NULL, NULL, '文言文多义词深度解析', '假既有积极意义也有消极意义', '1、假舆马者 2、假舟楫者 3、乃悟前狼假寐', '[\"借\",\"凭借\",\"虚假\"]', '假的含义：1、借 2、凭借利用 3、虚假假装', '假字的词义体现了事物的两面性。', '/audio/jia.mp3', '/image/shici39.jpg', 156, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (129, '\"间\"字解析', '空间实词学习', 3, 3, 2, 7, 3, '间在文言文中含义：1、中间 2、间隔 3、秘密', NULL, NULL, '文言文空间词教学', '间可以表示空间、时间、方式等', '1、悬泉瀑布，飞漱其间 2、间以河山 3、又间令吴广之次所旁丛祠中', '[\"中间\",\"间隔\",\"秘密\"]', '间的含义：1、中间 2、间隔 3、秘密地', '间字的多义性反映了古人对空间关系的深刻认识。', '/audio/jian.mp3', '/image/shici40.jpg', 167, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (130, '\"见\"字解析', '感知实词详解', 3, 2, 2, 7, 2, '见在文言文中含义：1、看见 2、拜见 3、被', NULL, NULL, '文言文感知动词教学', '见可以表示主动看，也可表示被动', '1、见藐小之物必细察其纹理 2、曹刿请见 3、信而见疑，忠而被谤', '[\"看见\",\"拜见\",\"被\"]', '见的含义：1、看见 2、拜见 3、被', '见字的被动用法需要特别注意，是文言文的重要语法现象。', '/audio/jian2.mp3', '/image/shici41.jpg', 189, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (131, '\"解\"字解析', '动作实词学习', 3, 2, 2, 7, 2, '解在文言文中含义：1、分解 2、解释 3、解除', NULL, NULL, '文言文动作动词教学', '解的核心义是分解，发展出多种引申义', '1、庖丁为文惠君解牛 2、师者，所以传道受业解惑也 3、解鞍少驻初程', '[\"分解\",\"解释\",\"解除\"]', '解的含义：1、分解解开 2、解释 3、解除消除', '解字体现了从具体动作到抽象思维的发展过程。', '/audio/jie.mp3', '/image/shici42.jpg', 156, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (132, '\"就\"字解析', '趋向实词详解', 3, 2, 2, 7, 2, '就在文言文中含义：1、接近 2、完成 3、担任', NULL, NULL, '文言文趋向动词教学', '就强调从远到近、从开始到完成的过程', '1、金就砺则利 2、自是指物作诗立就 3、陈力就列，不能者止', '[\"接近\",\"完成\",\"担任\"]', '就的含义：1、接近靠近 2、完成成就 3、就任担任', '就字的方向性很强，体现了古人的空间思维。', '/audio/jiu.mp3', '/image/shici43.jpg', 145, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (133, '\"举\"字解析', '多义实词综合', 3, 3, 2, 7, 3, '举在文言文中含义：1、举起 2、推荐 3、全', NULL, NULL, '文言文多义词综合教学', '举可以表示动作、社会行为、范围等', '1、举头望明月 2、傅说举于版筑之间 3、举家庆贺', '[\"举起\",\"推荐\",\"全\"]', '举的含义：1、举起抬起 2、推荐选拔 3、全整个', '举字的多义性反映了语言的丰富表现力。', '/audio/ju.mp3', '/image/shici44.jpg', 167, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (134, '\"绝\"字解析', '程度实词学习', 3, 2, 2, 7, 2, '绝在文言文中含义：1、断绝 2、极 3、横渡', NULL, NULL, '文言文程度词教学', '绝可以表示空间隔绝、程度极高', '1、往来而不绝者 2、以为妙绝 3、假舟楫者，非能水也，而绝江河', '[\"断绝\",\"极\",\"横渡\"]', '绝的含义：1、断绝 2、极最 3、横渡穿越', '绝字的程度义在现代汉语中仍很常用。', '/audio/jue.mp3', '/image/shici45.jpg', 178, b'1', 'admin', '2025-10-24 11:41:38.015', 'admin', '2025-10-24 11:41:38.015');
INSERT INTO `poetry_learning_content` VALUES (135, '背影', '朱自清经典散文', 3, 1, 3, 8, 2, '我与父亲不相见已二年余了，我最不能忘记的是他的背影。那年冬天，祖母死了，父亲的差使也交卸了，正是祸不单行的日子。我从北京到徐州，打算跟着父亲奔丧回家。到徐州见着父亲，看见满院狼藉的东西，又想起祖母，不禁簌簌地流下眼泪。父亲说：\"事已如此，不必难过，好在天无绝人之路！\"', '朱自清', '现代', '1917年作者北上读书，父亲送他到火车站', '通过描写父亲背影展现深沉的父爱', '我看见他戴着黑布小帽，穿着黑布大马褂，深青布棉袍，蹒跚地走到铁道边，慢慢探身下去，尚不大难。', '[\"背影\",\"狼藉\",\"蹒跚\"]', '文章通过回忆父亲送别时的背影，表达了作者对父亲深深的思念和感激之情。', '这篇散文以朴实细腻的笔触，通过一个普通的送别场景，展现了父爱的深沉与伟大，是中国现代散文的经典之作。', '/audio/beijing.mp3', '/image/yuedu1.jpg', 345, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (136, '从百草园到三味书屋', '鲁迅童年回忆', 3, 1, 3, 8, 2, '我家的后面有一个很大的园，相传叫作百草园。现在是早已并屋子一起卖给朱文公的子孙了，连那最末次的相见也已经隔了七八年，其中似乎确凿只有一些野草；但那时却是我的乐园。不必说碧绿的菜畦，光滑的石井栏，高大的皂荚树，紫红的桑椹；也不必说鸣蝉在树叶里长吟，肥胖的黄蜂伏在菜花上，轻捷的叫天子忽然从草间直窜向云霄里去了。', '鲁迅', '现代', '鲁迅回忆童年时期的生活经历', '通过对两个地方的对比展现童年乐趣', '单是周围的短短的泥墙根一带，就有无限趣味。油蛉在这里低唱，蟋蟀们在这里弹琴。', '[\"百草园\",\"三味书屋\",\"皂荚树\"]', '文章通过对比百草园的趣味盎然和三味书屋的严肃呆板，展现了作者对自由快乐童年的怀念。', '这篇散文以生动的笔触描绘了童年的美好时光，既有对自然的热爱，也有对封建教育制度的含蓄批判。', '/audio/caoyuan.mp3', '/image/yuedu2.jpg', 278, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (137, '小橘灯', '冰心温暖故事', 3, 1, 3, 8, 1, '这是十几年以前的事了。在一个春节前一天的下午，我到重庆郊外去看一位朋友。她住在那个乡村的乡公所楼上。走上一段阴暗的仄仄的楼梯，进入一间有一张方桌和几张竹凳、墙上装着一架电话的屋子，再进去就是我的朋友的房间，和外间只隔着一幅布帘。她不在家，窗前桌上留着一张条子，说是她临时有事出去，叫我等着她。', '冰心', '现代', '抗战时期作者在重庆的经历', '通过小橘灯象征希望和温暖', '我提着这灵巧的小橘灯，慢慢地在黑暗潮湿的山路上走着。这朦胧的橘红的光，实在照不了多远；但这小姑娘的镇定、勇敢、乐观的精神鼓舞了我，我似乎觉得眼前有无限光明！', '[\"小橘灯\",\"仄仄\",\"朦胧\"]', '文章通过一个小姑娘制作小橘灯的故事，展现了在艰难环境中人们的乐观精神和人间温情。', '这篇散文以细腻的笔触刻画了一个勇敢乐观的小姑娘形象，小橘灯成为温暖和希望的象征。', '/audio/xiaojudeng.mp3', '/image/yuedu3.jpg', 312, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (138, '荷塘月色', '朱自清写景名篇', 3, 2, 3, 8, 2, '这几天心里颇不宁静。今晚在院子里坐着乘凉，忽然想起日日走过的荷塘，在这满月的光里，总该另有一番样子吧。月亮渐渐地升高了，墙外马路上孩子们的欢笑，已经听不见了；妻在屋里拍着闰儿，迷迷糊糊地哼着眠歌。我悄悄地披了大衫，带上门出去。', '朱自清', '现代', '1927年作者在清华大学任教期间所作', '借景抒情，表达内心的苦闷和追求', '曲曲折折的荷塘上面，弥望的是田田的叶子。叶子出水很高，像亭亭的舞女的裙。层层的叶子中间，零星地点缀着些白花，有袅娜地开着的，有羞涩地打着朵儿的；正如一粒粒的明珠，又如碧天里的星星，又如刚出浴的美人。', '[\"荷塘\",\"月色\",\"田田\"]', '文章通过对荷塘月色的细腻描绘，抒发了作者对现实的不满和对美好生活的向往。', '这篇散文以精美的语言和独特的意境，展现了作者高超的写景抒情能力，是中国现代散文的典范之作。', '/audio/hetang.mp3', '/image/yuedu4.jpg', 389, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (139, '藤野先生', '鲁迅留学回忆', 3, 2, 3, 8, 2, '东京也无非是这样。上野的樱花烂熳的时节，望去确也像绯红的轻云，但花下也缺不了成群结队的\"清国留学生\"的速度班，头顶上盘着大辫子，顶得学生制帽的顶上高高耸起，形成一座富士山。也有解散辫子，盘得平的，除下帽来，油光可鉴，宛如小姑娘的发髻一般，还要将脖子扭几扭。实在标致极了。', '鲁迅', '现代', '鲁迅回忆在日本留学时的经历', '通过对藤野先生的回忆表达师生情谊', '他的性格，在我的眼里和心里是伟大的，虽然他的姓名并不为许多人所知道。他所改正的讲义，我曾经订成三厚本，收藏着的，将作为永久的纪念。', '[\"藤野\",\"樱花\",\"讲义\"]', '文章通过回忆藤野先生的严谨治学和无私关怀，表达了作者对老师的深深感激和怀念。', '这篇散文既展现了深厚的师生情谊，也反映了作者的爱国情怀，是鲁迅散文中的精品。', '/audio/tengye.mp3', '/image/yuedu5.jpg', 267, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (140, '济南的冬天', '老舍写景散文', 3, 1, 3, 8, 1, '对于一个在北平住惯的人，像我，冬天要是不刮风，便觉得是奇迹；济南的冬天是没有风声的。对于一个刚由伦敦回来的人，像我，冬天要能看得见日光，便觉得是怪事；济南的冬天是响晴的。自然，在热带的地方，日光是永远那么毒，响亮的天气，反有点叫人害怕。可是，在北中国的冬天，而能有温晴的天气，济南真得算个宝地。', '老舍', '现代', '老舍在济南任教时的生活体验', '生动描绘济南冬天独特的美景', '最妙的是下点小雪呀。看吧，山上的矮松越发的青黑，树尖上顶着一髻儿白花，好像日本看护妇。山尖全白了，给蓝天镶上一道银边。', '[\"济南\",\"响晴\",\"矮松\"]', '文章通过对比手法，突出了济南冬天温晴的特点，描绘了一幅幅美丽的冬日画卷。', '这篇散文语言生动形象，比喻新颖贴切，展现了老舍对自然景物的敏锐观察力和高超的表现力。', '/audio/jinan.mp3', '/image/yuedu6.jpg', 234, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (141, '风筝', '鲁迅童年故事', 3, 1, 3, 8, 2, '北京的冬季，地上还有积雪，灰黑色的秃树枝丫叉于晴朗的天空中，而远处有一二风筝浮动，在我是一种惊异和悲哀。故乡的风筝时节，是春二月，倘听到沙沙的风轮声，仰头便能看见一个淡墨色的蟹风筝或嫩蓝色的蜈蚣风筝。还有寂寞的瓦片风筝，没有风轮，又放得很低，伶仃地显出憔悴可怜模样。', '鲁迅', '现代', '鲁迅回忆童年时对弟弟放风筝的干涉', '表达对儿童天性的反思和忏悔', '但此时地上的杨柳已经发芽，早的山桃也多吐蕾，和孩子们的天上的点缀相照应，打成一片春日的温和。我现在在哪里呢？四面都还是严冬的肃杀，而久经诀别的故乡的久经逝去的春天，却就在这天空中荡漾了。', '[\"风筝\",\"肃杀\",\"伶仃\"]', '文章通过回忆童年时阻止弟弟放风筝的经历，表达了作者对扼杀儿童天性的深刻反省。', '这篇散文以深沉的情感和平实的语言，展现了作者严于律己的批判精神，具有深刻的教育意义。', '/audio/fengzheng.mp3', '/image/yuedu7.jpg', 289, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (142, '白杨礼赞', '茅盾托物言志', 3, 2, 3, 8, 2, '白杨树实在是不平凡的，我赞美白杨树！汽车在望不到边际的高原上奔驰，扑入你的视野的，是黄绿错综的一条大毯子。黄的是土，未开垦的荒地，几十万年前由伟大的自然力堆积成功的黄土高原的外壳；绿的呢，是人类劳力战胜自然的成果，是麦田。和风吹送，翻起了一轮一轮的绿波，——这时你会真心佩服昔人所造的两个字\"麦浪\"，若不是妙手偶得，便确是经过锤炼的语言的精华。', '茅盾', '现代', '1941年作者在西北旅行时的见闻', '通过赞美白杨树歌颂北方农民的精神', '那是力争上游的一种树，笔直的干，笔直的枝。它的干通常是大把高，像加过人工似的，一丈以内绝无旁枝。它所有的丫枝一律向上，而且紧紧靠拢，也像加过人工似的，成为一束，绝不旁逸斜出。', '[\"白杨\",\"礼赞\",\"锤炼\"]', '文章通过对白杨树的赞美，歌颂了北方农民坚强不屈、力求上进的精神品质。', '这篇散文运用象征手法，托物言志，语言铿锵有力，情感真挚热烈，是茅盾散文的代表作。', '/audio/baiyang.mp3', '/image/yuedu8.jpg', 256, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (143, '猫', '郑振铎动物故事', 3, 1, 3, 8, 1, '我家养了好几次猫，结局总是失踪或死亡。三妹是最喜欢猫的，她常在课后回家时，逗着猫玩。有一次，从隔壁要了一只新生的猫来。花白的毛，很活泼，常如带着泥土的白雪球似的，在廊前太阳光里滚来滚去。三妹常常的，取了一条红带，或一根绳子，在它面前来回的拖摇着，它便扑过来抢，又扑过去抢。', '郑振铎', '现代', '作者回忆家中养猫的经历', '通过养猫经历表达对生命的尊重', '我心里十分的难过，真的，我的良心受伤了，我没有判断明白，便妄下断语，冤苦了一只不能说话辩诉的动物。想到它的无抵抗的逃避，益使我感到我的暴怒、我的虐待，都是针，刺我良心的针！', '[\"猫\",\"白雪球\",\"妄下断语\"]', '文章通过三次养猫的不同经历，表达了作者对弱小生命的同情和尊重，以及深刻的自省意识。', '这篇散文以细腻的笔触描写了人与动物之间的关系，情感真挚，寓意深刻，具有很强的感染力。', '/audio/mao.mp3', '/image/yuedu9.jpg', 223, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (144, '落花生', '许地山人生哲理', 3, 1, 3, 8, 1, '我们家的后园有半亩空地。母亲说：\"让它荒着怪可惜的，你们那么爱吃花生，就开辟出来种花生吧。\"我们姐弟几个都很高兴，买种，翻地，播种，浇水，没过几个月，居然收获了。母亲说：\"今晚我们过一个收获节，请你们父亲也来尝尝我们的新花生，好不好？\"我们都说好。母亲把花生做成了好几样食品，还吩咐就在后园的茅亭里过这个节。', '许地山', '现代', '作者回忆童年时种花生的经历', '通过花生阐明做人道理', '父亲说：\"花生的好处很多，有一样最可贵：它的果实埋在地里，不像桃子、石榴、苹果那样，把鲜红嫩绿的果实高高地挂在枝头上，使人一见就生爱慕之心。你们看它矮矮地长在地上，等到成熟了，也不能立刻分辨出来它有没有果实，必须挖起来才知道。\"', '[\"落花生\",\"收获节\",\"爱慕\"]', '文章通过一家人种花生、吃花生、议花生的过程，阐明了做人要像花生一样朴实无华、默默奉献的道理。', '这篇散文语言朴实，寓意深刻，通过平常的生活场景揭示了深刻的人生哲理，具有很强的教育意义。', '/audio/luoshenghua.mp3', '/image/yuedu10.jpg', 278, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (145, '故乡', '鲁迅回乡见闻', 3, 2, 3, 8, 3, '我冒了严寒，回到相隔二千余里，别了二十余年的故乡去。时候既然是深冬；渐近故乡时，天气又阴晦了，冷风吹进船舱中，呜呜的响，从篷隙向外一望，苍黄的天底下，远近横着几个萧索的荒村，没有一些活气。我的心禁不住悲凉起来了。阿！这不是我二十年来时时记得的故乡？', '鲁迅', '现代', '1919年鲁迅回乡接母亲的经历', '通过故乡变化反映社会现实', '这时候，我的脑里忽然闪出一幅神异的图画来：深蓝的天空中挂着一轮金黄的圆月，下面是海边的沙地，都种着一望无际的碧绿的西瓜，其间有一个十一二岁的少年，项带银圈，手捏一柄钢叉，向一匹猹尽力的刺去，那猹却将身一扭，反从他的胯下逃走了。', '[\"故乡\",\"萧索\",\"猹\"]', '文章通过对比记忆中的故乡和现实中的故乡，反映了旧中国农村的衰败和农民的苦难生活。', '这篇小说以深沉的笔调描绘了故乡的变化，塑造了闰土这个典型形象，具有深刻的社会批判意义。', '/audio/guxiang.mp3', '/image/yuedu11.jpg', 312, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (146, '孔乙己', '鲁迅小说名篇', 3, 3, 3, 8, 3, '鲁镇的酒店的格局，是和别处不同的：都是当街一个曲尺形的大柜台，柜里面预备着热水，可以随时温酒。做工的人，傍午傍晚散了工，每每花四文铜钱，买一碗酒，——这是二十多年前的事，现在每碗要涨到十文，——靠柜外站着，热热的喝了休息；倘肯多花一文，便可以买一碟盐煮笋，或者茴香豆，做下酒物了，如果出到十几文，那就能买一样荤菜，但这些顾客，多是短衣帮，大抵没有这样阔绰。只有穿长衫的，才踱进店面隔壁的房子里，要酒要菜，慢慢地坐喝。', '鲁迅', '现代', '描写科举制度下的知识分子悲剧', '通过孔乙己的遭遇批判封建科举', '孔乙己是站着喝酒而穿长衫的唯一的人。他身材很高大；青白脸色，皱纹间时常夹些伤痕；一部乱蓬蓬的花白的胡子。穿的虽然是长衫，可是又脏又破，似乎十多年没有补，也没有洗。', '[\"孔乙己\",\"曲尺\",\"茴香豆\"]', '文章通过孔乙己这个人物形象，深刻揭露了封建科举制度对知识分子的毒害和社会的冷漠无情。', '这篇小说以精湛的艺术手法塑造了孔乙己这个典型形象，语言简练传神，是中国现代小说的经典之作。', '/audio/kongyiji.mp3', '/image/yuedu12.jpg', 356, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (147, '春', '朱自清写景抒情', 3, 2, 3, 8, 2, '盼望着，盼望着，东风来了，春天的脚步近了。一切都像刚睡醒的样子，欣欣然张开了眼。山朗润起来了，水涨起来了，太阳的脸红起来了。小草偷偷地从土里钻出来，嫩嫩的，绿绿的。园子里，田野里，瞧去，一大片一大片满是的。坐着，躺着，打两个滚，踢几脚球，赛几趟跑，捉几回迷藏。风轻悄悄的，草软绵绵的。', '朱自清', '现代', '作者对春天景象的细腻观察', '通过描绘春景表达对生命的热爱', '桃树、杏树、梨树，你不让我，我不让你，都开满了花赶趟儿。红的像火，粉的像霞，白的像雪。花里带着甜味儿；闭了眼，树上仿佛已经满是桃儿、杏儿、梨儿。花下成千成百的蜜蜂嗡嗡地闹着，大小的蝴蝶飞来飞去。', '[\"朗润\",\"赶趟儿\",\"嗡嗡\"]', '文章通过对春天各种景象的生动描绘，展现了春天的生机勃勃，表达了作者对大自然和生命的热爱。', '这篇散文语言优美，比喻新颖，描绘细腻，充满了诗情画意，是写景抒情散文的典范。', '/audio/chun.mp3', '/image/yuedu13.jpg', 423, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (148, '匆匆', '朱自清时间感悟', 3, 2, 3, 8, 2, '燕子去了，有再来的时候；杨柳枯了，有再青的时候；桃花谢了，有再开的时候。但是，聪明的，你告诉我，我们的日子为什么一去不复返呢？——是有人偷了他们罢：那是谁？又藏在何处呢？是他们自己逃走了罢：现在又到了哪里呢？', '朱自清', '现代', '作者对时间流逝的深刻思考', '通过具体意象表达对时光的珍惜', '去的尽管去了，来的尽管来着；去来的中间，又怎样地匆匆呢？早上我起来的时候，小屋里射进两三方斜斜的太阳。太阳他有脚啊，轻轻悄悄地挪移了；我也茫茫然跟着旋转。', '[\"匆匆\",\"挪移\",\"茫茫然\"]', '文章通过对时间匆匆流逝的细腻描写，表达了作者对时光易逝的感慨和对人生的思考。', '这篇散文以优美的语言和深刻的哲理，启迪人们珍惜时间、热爱生活，具有永恒的教育意义。', '/audio/congcong.mp3', '/image/yuedu14.jpg', 367, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (149, '阿长与山海经', '鲁迅童年回忆', 3, 2, 3, 8, 2, '长妈妈，已经说过，是一个一向带领着我的女工，说得阔气一点，就是我的保姆。我的母亲和许多别的人都这样称呼她，似乎略带些客气的意思。只有祖母叫她阿长。我平时叫她\"阿妈\"，连\"长\"字也不带；但到憎恶她的时候，——例如知道了谋死我那隐鼠的却是她的时候，就叫她阿长。', '鲁迅', '现代', '鲁迅回忆童年时的保姆长妈妈', '通过具体事例展现人物的善良品质', '哥儿，有画儿的\"三哼经\"，我给你买来了！我似乎遇着了一个霹雳，全体都震悚起来；赶紧去接过来，打开纸包，是四本小小的书，略略一翻，人面的兽，九头的蛇，……果然都在内。', '[\"阿长\",\"山海经\",\"震悚\"]', '文章通过几个生活片段，生动地刻画了长妈妈这个人物形象，表达了对她的怀念和感激之情。', '这篇散文以欲扬先抑的手法，通过具体事例展现人物的善良品质，情感真挚，人物形象鲜明。', '/audio/achang.mp3', '/image/yuedu15.jpg', 289, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (150, '范爱农', '鲁迅回忆友人', 3, 3, 3, 8, 3, '在东京的客店里，我们大抵一起来就看报。学生所看的多是《朝日新闻》和《读卖新闻》，专爱打听社会上琐事的就看《二六新闻》。一天早晨，辟头就看见一条从中国来的电报，大概是：\"安徽巡抚恩铭被Jo Shiki Rin刺杀，刺客就擒。\"大家一怔之后，便容光焕发地互相告语，并且研究这刺客是谁，汉字是怎样三个字。但只要是绍兴人，又不专看教科书的，却早已明白了。', '鲁迅', '现代', '鲁迅回忆友人范爱农的遭遇', '通过范爱农的命运反映时代悲剧', '这是一个高大身材，长头发，眼球白多黑少的人，看人总像在渺视。他蹲在席子上，我发言大抵就反对；我早觉得奇怪，注意着他的了，到这时才打听别人：说这话的是谁呢，有那么冷？', '[\"范爱农\",\"容光焕发\",\"渺视\"]', '文章通过回忆范爱农这个人物，展现了辛亥革命前后知识分子的命运，具有深刻的历史意义。', '这篇散文以深沉的情感回忆友人的遭遇，反映了时代的悲剧，是鲁迅回忆散文中的重要作品。', '/audio/fanainong.mp3', '/image/yuedu16.jpg', 234, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (151, '乌篷船', '周作人故乡风物', 3, 2, 3, 8, 2, '子荣君：接到手书，知道你要到我的故乡去，叫我给你一点什么指导。老实说，我的故乡，真正觉得可怀恋的地方，并不是那里；但是因为在那里生长，住过十多年，究竟知道一点情形，所以写这一封信告诉你。我所要告诉你的，并不是那里的风土人情，那是写不尽的，但是你到那里一看也就会明白的，不必啰唆地多讲。我要说的是一种很有趣的东西，这便是船。', '周作人', '现代', '作者向友人介绍故乡的乌篷船', '通过乌篷船展现江南水乡风情', '小船则真是一叶扁舟，你坐在船底席上，篷顶离你的头有两三寸，你的两手可以搁在左右的舷上，还把手都露出在外边。在这种船里仿佛是在水面上坐，靠近田岸去时泥土便和你的眼鼻接近，而且遇着风浪，或是坐得少不小心，就会船底朝天，发生危险，但是也颇有趣味，是水乡的一种特色。', '[\"乌篷船\",\"风土人情\",\"一叶扁舟\"]', '文章通过对乌篷船的详细介绍，展现了江南水乡的独特风情和作者对故乡的深厚感情。', '这篇散文以平和冲淡的笔调，通过对具体事物的描写展现地方风情，体现了周作人散文的独特风格。', '/audio/wupengchuan.mp3', '/image/yuedu17.jpg', 256, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (152, '想北平', '老舍思乡之作', 3, 2, 3, 8, 2, '设若让我写一本小说，以北平作背景，我不至于害怕，因为我可以检着我知道的写，而躲开我所不知道的。让我单摆浮搁的讲一套北平，我没办法。北平的地方那么大，事情那么多，我知道的真觉太少了，虽然我生在那里，一直到廿七岁才离开。以名胜说，我没到过陶然亭，这多可笑！以此类推，我所知道的那点只是\"我的北平\"，而我的北平大概等于牛的一毛。', '老舍', '现代', '老舍在青岛时思念北平所作', '通过具体细节表达对北平的深情', '面向着积水潭，背后是城墙，坐在石上看水中的小蝌蚪或苇叶上的嫩蜻蜓，我可以快乐的坐一天，心中完全安适，无所求也无可怕，像小儿安睡在摇篮里。是的，北平也有热闹的地方，但是它和太极拳相似，动中有静。', '[\"北平\",\"单摆浮搁\",\"安适\"]', '文章通过具体的景物和感受，表达了作者对北平深沉的爱和强烈的思乡之情。', '这篇散文以朴实真挚的语言，通过对北平日常生活的描写，展现了作者对故乡的深厚感情，感人至深。', '/audio/xiangbeiping.mp3', '/image/yuedu18.jpg', 278, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (153, '囚绿记', '陆蠡托物言志', 3, 3, 3, 8, 3, '这是去年夏间的事情。我住在北平的一家公寓里。我占据着高广不过一丈的小房间，砖铺的潮湿的地面，纸糊的墙壁和天花板，两扇木格子嵌玻璃的窗，窗上有很灵巧的纸卷帘，这在南方是少见的。窗是朝东的。北方的夏季天亮得快，早晨五点钟左右太阳便照进我的小屋，把可畏的光线射个满室，直到十一点半才退出，令人感到炎热。', '陆蠡', '现代', '抗战时期作者在北平的经历', '通过常春藤表达对自由的向往', '我天天望着窗口常春藤的生长。看它怎样伸开柔软的卷须，攀住一根缘引它的绳索，或一茎枯枝；看它怎样舒开折叠着的嫩叶，渐渐变青，渐渐变老，我细细观赏它纤细的脉络，嫩芽，我以揠苗助长的心情，巴不得它长得快，长得茂绿。', '[\"囚绿\",\"常春藤\",\"揠苗助长\"]', '文章通过\"囚绿\"的经历，表达了作者对自由的向往和对生命的尊重，具有深刻的象征意义。', '这篇散文以细腻的笔触描写了作者与常春藤的关系，托物言志，寓意深刻，是陆蠡的代表作。', '/audio/qiulvji.mp3', '/image/yuedu19.jpg', 245, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (154, '爱尔克的灯光', '巴金回乡感悟', 3, 3, 3, 8, 3, '傍晚，我靠着逐渐黯淡的最后的阳光的指引，走过十八年前的故居。这条街、这个建筑物开始在我的眼前隐藏起来，像在躲避一个久别的旧友。但是它们的改变了的面貌于我还是十分亲切。我认识它们，就像认识我自己。还是那样宽的街，宽的房屋。巍峨的门墙代替了太平缸和石狮子，那一对常常做我们坐骑的背脊光滑的雄狮也不知逃进了哪座荒山。', '巴金', '现代', '巴金重回故乡时的所见所感', '通过灯光意象表达对新生活的追求', '在这条被夜幕覆盖着的近代城市的静寂的街中，我仿佛看见了哈立希岛上的灯光。那应该是姐姐爱尔克点的灯罢。她用这灯光来给她的航海的兄弟照路，每夜每夜灯光亮在她的窗前，她一直到死都在等待那个出远门的兄弟回来。最后她带着失望进入坟墓。', '[\"爱尔克\",\"灯光\",\"故居\"]', '文章通过回忆姐姐爱尔克的故事和故居的变化，表达了作者对封建家庭的批判和对新生活的向往。', '这篇散文以\"灯光\"为线索，将现实与回忆交织在一起，情感深沉，思想深刻，是巴金散文的精品。', '/audio/aierskedeng.mp3', '/image/yuedu20.jpg', 267, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (155, '记念刘和珍君', '鲁迅纪念文章', 3, 3, 3, 8, 3, '中华民国十五年三月二十五日，就是国立北京女子师范大学为十八日在段祺瑞执政府前遇害的刘和珍杨德群两君开追悼会的那一天，我独在礼堂外徘徊，遇见程君，前来问我道，\"先生可曾为刘和珍写了一点什么没有？\"我说\"没有\"。她就正告我，\"先生还是写一点罢；刘和珍生前就很爱看先生的文章。\"', '鲁迅', '现代', '1926年三八惨案后鲁迅为遇害学生所作', '悼念遇害学生，批判反动统治', '真的猛士，敢于直面惨淡的人生，敢于正视淋漓的鲜血。这是怎样的哀痛者和幸福者？然而造化又常常为庸人设计，以时间的流驶，来洗涤旧迹，仅使留下淡红的血色和微漠的悲哀。', '[\"刘和珍\",\"猛士\",\"淋漓\"]', '文章通过悼念刘和珍等遇害学生，深刻揭露了反动政府的暴行，歌颂了爱国青年的英勇精神。', '这篇散文以悲愤的情感、犀利的笔触和深刻的思想，成为中国现代散文史上的不朽名篇。', '/audio/liuhezhen.mp3', '/image/yuedu21.jpg', 312, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (156, '为了忘却的记念', '鲁迅纪念左联五烈士', 3, 3, 3, 8, 3, '我早已想写一点文字，来记念几个青年的作家。这并非为了别的，只因为两年以来，悲愤总时时来袭击我的心，至今没有停止，我很想借此算是竦身一摇，将悲哀摆脱，给自己轻松一下，照直说，就是我倒要将他们忘却了。两年前的此时，即一九三一年的二月七日夜或八日晨，是我们的五个青年作家同时遇害的时候。当时上海的报章都不敢载这件事，或者也许是不愿，或不屑载这件事，只在《文艺新闻》上有一点隐约其辞的文章。', '鲁迅', '现代', '1933年为纪念左联五烈士而作', '纪念革命烈士，抒发悲愤之情', '不是年青的为年老的写记念，而在这三十年中，却使我目睹许多青年的血，层层淤积起来，将我埋得不能呼吸，我只能用这样的笔墨，写几句文章，算是从泥土中挖一个小孔，自己延口残喘，这是怎样的世界呢。夜正长，路也正长，我不如忘却，不说的好罢。但我知道，即使不是我，将来总会有记起他们，再说他们的时候的。……', '[\"忘却\",\"记念\",\"淤积\"]', '文章通过回忆与柔石等烈士的交往，抒发了对革命青年的深切怀念和对反动统治的强烈愤慨。', '这篇散文情感深沉，思想深刻，语言凝练，是鲁迅晚期散文的代表作，具有重要的文学和历史价值。', '/audio/wangque.mp3', '/image/yuedu22.jpg', 289, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (157, '包身工', '夏衍报告文学', 3, 3, 3, 8, 3, '已经是旧历四月中旬了，上午四点过一刻，晓星才从慢慢地推移着的淡云里面消去，蜂房般的格子铺里的生物已经在蠕动了。\"拆铺啦！起来！\"穿着一身和时节不相称的拷绸衫裤的男子，像生气似的呼喊，\"芦柴棒，去烧火！妈的，还躺着，猪猡！\"七尺阔、十二尺深的工房楼下，横七竖八地躺满了十六七个\"猪猡\"。跟着这种有威势的喊声，在充满了汗臭、粪臭和湿气的空气里面，她们很快地就像被搅动了的蜂窝一般骚动起来。', '夏衍', '现代', '1935年作者调查上海纺织女工状况', '揭露包身工制度的罪恶', '在这千万被饲养者中间，没有光，没有热，没有温情，没有希望……没有法律，没有人道。这儿有的是 twentieth century 的烂熟了的技术、机械、体制和对这种体制忠实服役的十六世纪封建制度下的奴隶！', '[\"包身工\",\"芦柴棒\",\"猪猡\"]', '文章通过真实细致的描写，揭露了包身工制度的残酷和罪恶，表达了对劳动人民的深切同情。', '这篇报告文学以真实生动的描写和深刻的社会分析，成为中国现代报告文学的典范之作。', '/audio/baoshengong.mp3', '/image/yuedu23.jpg', 256, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (158, '谁是最可爱的人', '魏巍朝鲜战场见闻', 3, 3, 3, 8, 3, '在朝鲜的每一天，我都被一些东西感动着；我的思想感情的潮水，在放纵奔流着；它使我想把一切东西，都告诉给我祖国的朋友们。但我最急于告诉你们的，是我思想感情的一段重要经历，这就是：我越来越深刻地感觉到谁是我们最可爱的人！谁是我们最可爱的人呢？我们的战士，我感到他们是最可爱的人。', '魏巍', '现代', '抗美援朝时期作者在朝鲜战场的见闻', '歌颂志愿军战士的英勇精神', '他们的品质是那样的纯洁和高尚，他们的意志是那样的坚韧和刚强，他们的气质是那样的淳朴和谦逊，他们的胸怀是那样的美丽和宽广！让我还是来说一段故事吧。', '[\"最可爱的人\",\"纯洁\",\"坚韧\"]', '文章通过几个典型事例，生动地展现了志愿军战士的英雄气概和崇高品质，歌颂了他们的爱国主义精神。', '这篇通讯以真挚的情感和生动的事例，成功地塑造了\"最可爱的人\"这一光辉形象，影响了几代中国人。', '/audio/zuikeaide.mp3', '/image/yuedu24.jpg', 334, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (159, '土地的誓言', '端木蕻良爱国散文', 3, 3, 3, 8, 3, '对于广大的关东原野，我心里怀着炽痛的热爱。我无时无刻不听见她呼唤我的名字，我无时无刻不听见她召唤我回去。我有时把手放在我的胸膛上，我知道我的心还是跳动的，我的心还在喷涌着热血，因为我常常感到它在泛滥着一种热情。当我躺在土地上的时候，当我仰望天上的星星，手里握着一把泥土的时候，或者当我回想起儿时的往事的时候，我想起那参天碧绿的白桦林，标直漂亮的白桦树在原野上呻吟；我看见奔流似的马群，深夜嗥鸣的蒙古狗，我听见皮鞭滚落在山涧里的脆响；我想起红布似的高粱，金黄的豆粒，黑色的土地，红玉的脸庞，黑玉的眼睛，斑斓的山雕，奔驰的鹿群，带着松香气味的煤块，带着赤色的足金；我想起幽远的车铃，晴天里马儿戴着串铃在溜直的大道上跑着，狐仙姑深夜的谰语，原野上怪诞的狂风……', '端木蕻良', '现代', '九一八事变后作者流亡关内时所作', '表达对东北故乡的深切思念', '土地，原野，我的家乡，你必须被解放！你必须站立！夜夜我听见马蹄奔驰的声音，草原的儿子在黎明的天边呼唤。这时我起来，找寻天空中北方的大熊，在它金色的光芒之下，乃是我的家乡。', '[\"炽痛\",\"泛滥\",\"谰语\"]', '文章以诗一般的语言，抒发了作者对故乡土地的深切热爱和誓死收复失地的坚强决心。', '这篇散文情感热烈奔放，语言富有诗意和感染力，是抗战时期爱国散文的代表作。', '/audio/tudide.mp3', '/image/yuedu25.jpg', 278, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (160, '西湖的雪景', '钟敬文写景散文', 3, 2, 3, 8, 2, '从来谈论西湖之胜景的，大抵注目于春夏两季；而各地游客，也多于此时翩然来临。——秋季游人已渐少，入冬后，则更形疏落了。这当中自然有所以然的道理。春夏之间，气温和暖，湖上风物，应时佳胜，或\"杂花生树，群莺乱飞\"，或\"浴晴鸥鹭争飞，拂袂荷风荐爽\"，都是要教人着迷的。', '钟敬文', '现代', '作者在冬日游览西湖的体验', '通过雪景展现西湖的别样美', '雪中的西湖，仿佛是一个沉睡的美人，静静地躺在白色的绒毯之下。平时的喧嚣不见了，游人的踪迹稀少了，连鸟儿也躲藏起来了。只有雪花在静静地飘落，落在湖面上，落在树枝上，落在亭台楼阁上，把整个世界都染成了白色。', '[\"西湖\",\"雪景\",\"翩然\"]', '文章通过对西湖雪景的细腻描绘，展现了西湖在冬季的静谧之美，表达了作者对自然美景的热爱。', '这篇散文以独特的视角描写西湖雪景，语言优美，意境深远，是写景散文中的佳作。', '/audio/xihuxue.mp3', '/image/yuedu26.jpg', 234, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (161, '故都的秋', '郁达夫写景抒情', 3, 3, 3, 8, 3, '秋天，无论在什么地方的秋天，总是好的；可是啊，北国的秋，却特别地来得清，来得静，来得悲凉。我的不适于南方的心境，每年要引起争议的，也不过是这北国的秋的深味，尤其是秋的深味，非要在北方，才感受得到底。南国之秋，当然是也有它的特异的地方的，比如廿四桥的明月，钱塘江的秋潮，普陀山的凉雾，荔枝湾的残荷等等，可是色彩不浓，回味不永。比起北国的秋来，正像是黄酒之与白干，稀饭之与馍馍，鲈鱼之与大蟹，黄犬之与骆驼。', '郁达夫', '现代', '1934年作者在北平体验秋天', '通过对比突出北国秋天的特点', '在北平即使不出门去罢，就是在皇城人海之中，租人家一椽破屋来住着，早晨起来，泡一碗浓茶，向院子一坐，你也能看得到很高很高的碧绿的天色，听得到青天下驯鸽的飞声。从槐树叶底，朝东细数着一丝一丝漏下来的日光，或在破壁腰中，静对着像喇叭似的牵牛花的蓝朵，自然而然地也能够感觉到十分的秋意。', '[\"故都\",\"悲凉\",\"一椽\"]', '文章通过对北国秋天各种景象的描写，突出了故都秋色的清、静、悲凉的特点，抒发了对故都的眷恋之情。', '这篇散文以细腻的观察和独特的感受，展现了北国秋天的独特魅力，是郁达夫散文的代表作。', '/audio/gududeqiu.mp3', '/image/yuedu27.jpg', 289, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (162, '雨中登泰山', '李健吾游记散文', 3, 2, 3, 8, 2, '从火车上遥望泰山，几十年来有好些次了，每次想起\"孔子登东山而小鲁，登泰山而小天下\"那句话来，就觉得过而不登，像是欠下悠久的文化传统一笔债似的。杜甫的愿望：\"会当凌绝顶，一览众山小\"，我也一样有，惜乎来去匆匆，每次都当面错过了。', '李健吾', '现代', '作者在雨中登泰山的经历', '通过登山经历展现坚持不懈的精神', '而今确实要登泰山了，偏偏天公不作美，下起雨来，淅淅沥沥，不像落在地上，倒像落在心里。天是灰的，心是沉的。我们约好了清晨出发，人齐了，雨却越下越大。等天晴吗？想着这渺茫的\"等\"字，先是憋闷。盼到十一点半钟，天色转白，我不由喊了一句：\"走吧！\"带动年轻人，挎起背包，兴致勃勃，朝岱宗坊出发了。', '[\"泰山\",\"淅淅沥沥\",\"岱宗坊\"]', '文章通过描写雨中登泰山的经历，展现了泰山雄伟壮丽的景色和作者不畏艰难的精神。', '这篇游记散文以生动的笔触描绘了雨中泰山的独特景色，情景交融，富有哲理，是游记散文的佳作。', '/audio/yuzhong.mp3', '/image/yuedu28.jpg', 256, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (163, '内蒙访古', '翦伯赞历史散文', 3, 3, 3, 8, 3, '今年夏天，我和历史学家范文澜、吕振羽同志等应乌兰夫同志的邀请，访问了内蒙古自治区。访问历时近两月，行程达一万五千余里。要想把这次访问的收获都写出来，也是不可能的，不过也可以写一写我个人的一些印象和感受。', '翦伯赞', '现代', '1961年作者访问内蒙古的经历', '通过访古探讨民族关系和历史发展', '现在的大青山，树木不多，但在汉代，这里却是一个\"草木茂盛，多禽兽\"的地方，古代的匈奴人曾经把这个地方当作自己的苑囿。一直到蒙古人来到阴山的时候，这里的自然条件还没有什么改变。', '[\"内蒙\",\"大青山\",\"苑囿\"]', '文章通过对内蒙古历史遗迹的考察，探讨了民族关系、历史发展等重大问题，具有深刻的历史眼光。', '这篇散文将历史考察与现实思考相结合，既有学术价值，又有文学价值，是历史散文的典范。', '/audio/neimeng.mp3', '/image/yuedu29.jpg', 223, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (164, '黄山记', '徐迟游记散文', 3, 3, 3, 8, 3, '大自然是崇高，卓越而美的。它煞费心机，创造世界。它创造了人间，还安排了一处胜境。它选中皖南山区。它是大手笔，用火山喷发的手法，迅速地，在周围一百二十公里，面积千余平方公里的一个浑圆的区域里，分布了这么多花岗岩的山峰。', '徐迟', '现代', '作者游览黄山的经历和感受', '通过黄山景色赞美大自然的神奇', '它巧妙地搭配了其中三十六大峰和三十六小峰。高峰下临深谷；幽潭傍依天柱。这些朱砂的，丹红的，紫褐色的群峰，前拥后簇，高矮参差。三个主峰，高风峻骨，鼎足而立，撑起青天。', '[\"黄山\",\"胜境\",\"峻骨\"]', '文章通过对黄山壮丽景色的描绘，赞美了大自然的神奇造化，表达了作者对祖国山河的热爱。', '这篇游记散文气势磅礴，语言华丽，想象丰富，是徐迟散文的代表作，也是当代游记散文的精品。', '/audio/huangshan.mp3', '/image/yuedu30.jpg', 245, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (165, '长江三日', '刘白羽游记散文', 3, 3, 3, 8, 3, '十一月十七日……雾笼罩着江面，气象森严。十二时，\"江津\"号启碇顺流而下了。在长江与嘉陵江汇合后，江面突然开阔，天穹顿觉低垂。浓浓的黄雾，渐渐把重庆隐去。一刻钟后，船又在两面碧森森的悬崖陡壁之间的狭窄的江面上行驶了。', '刘白羽', '现代', '作者乘船游览长江三日的经历', '通过长江景色抒发革命豪情', '你看那急速漂流的波涛一起一伏，真是\"众水会涪万，瞿塘争一门\"。而两三木船，却齐整的摇动着两排木桨，像鸟儿扇动着翅膀，正在逆流而上。我想到李白、杜甫在那遥远的年代，以一叶扁舟，搏浪急进，那该是多么雄伟的搏斗，会激发诗人多少瑰丽的诗思啊！', '[\"长江\",\"启碇\",\"瑰丽\"]', '文章通过描写长江三日的见闻，展现了长江的壮丽景色，抒发了作者的革命豪情和人生感悟。', '这篇游记散文将写景、抒情、议论融为一体，气势恢宏，意境深远，是刘白羽散文的代表作。', '/audio/changjiang.mp3', '/image/yuedu31.jpg', 267, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (166, '天山景物记', '碧野写景散文', 3, 2, 3, 8, 2, '朋友，你到过天山吗？天山是我们祖国西北边疆的一条大山脉，连绵几千里，横亘塔里木盆地和准噶尔盆地之间，把广阔的新疆分为南北两半。远望天山，美丽多姿，那长年积雪高插云霄的群峰，像集体起舞时的维吾尔族少女的珠冠，银光闪闪；那富于色彩的连绵不断的山峦，像孔雀开屏，艳丽迷人。', '碧野', '现代', '作者在天山地区的见闻', '描绘天山美丽的自然风光', '如果你愿意，我陪你进天山去看一看。雪峰·溪流·森林·野花七月间新疆的戈壁滩炎暑逼人，这时最理想的是骑马上天山。新疆北部的伊犁和南部的焉耆都出产良马，不论伊犁的哈萨克马或者焉耆的蒙古马，骑上它爬山就像走平川，又快又稳。', '[\"天山\",\"横亘\",\"珠冠\"]', '文章通过对天山各种景物的生动描绘，展现了天山美丽的自然风光和丰富的物产，表达了作者对祖国山河的热爱。', '这篇散文以优美的语言和细腻的描写，展现了天山的独特魅力，是当代写景散文的佳作。', '/audio/tianshan.mp3', '/image/yuedu32.jpg', 234, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (167, '画山绣水', '杨朔游记散文', 3, 2, 3, 8, 2, '自从唐人写了一句\"桂林山水甲天下\"的诗，多有人把它当作品评山水的论断。殊不知原诗只是出力烘衬桂林山水的妙处，并非要褒贬天下山水。本来天下山水各有各的特殊风致，桂林山水那种清奇峭拔的神态，自然是绝世少有的。', '杨朔', '现代', '作者游览桂林山水的感受', '通过山水描写展现生活哲理', '尤其是从桂林到阳朔，一百六十里漓江水路，满眼画山绣水，更是大自然的千古杰作。瞧瞧那漓水，碧绿碧绿的，绿得像最醇的青梅名酒，看一眼也叫人心醉。再瞧瞧那沿江攒聚的怪石奇峰，峰峰都是瘦骨嶙峋的，却又那样玲珑剔透，千奇百怪，有的像大象在江边饮水，有的像天马腾空欲飞，随着你的想象，可以变幻成各种各样神奇的物件。', '[\"画山绣水\",\"峭拔\",\"嶙峋\"]', '文章通过对桂林山水的描绘，不仅展现了自然美景，还通过船工的故事展现了劳动人民的智慧和生活哲理。', '这篇散文语言精美，意境深远，在写景中融入哲理思考，是杨朔散文的代表作。', '/audio/huashan.mp3', '/image/yuedu33.jpg', 256, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (168, '菜园小记', '吴伯箫回忆散文', 3, 2, 3, 8, 2, '种花好，种菜更好。花种得好，姹紫嫣红，满园芬芳，可以欣赏；菜种得好，嫩绿的茎叶，肥硕的块根，多浆的果实，却可以食用。俗话说：\"瓜菜半年粮。\"我想起在延安蓝家坪我们种的菜园来了。说是菜园，其实是果园。那园里桃树杏树很多，还有海棠。每年春二三月，粉红的桃杏花开罢，不久就开始绿叶成荫，果子藏在绿叶里头，一眼是看不见的；我说的是看见果子满园啦。', '吴伯箫', '现代', '作者回忆延安时期的菜园生活', '通过种菜经历表达革命乐观主义', '暮春，中午，踩着畦垅间苗或者锄草中耕，煦暖的阳光照得人浑身舒畅。新鲜的泥土气息，素淡的蔬菜清香，一阵阵沁人心脾。一会儿站起来，伸伸腰，用手背擦擦额头的汗，看看苗间得稀稠，中耕得深浅，草锄得是不是干净，那时候人是会感到劳动的愉快的。', '[\"菜园\",\"姹紫嫣红\",\"畦垅\"]', '文章通过回忆延安时期种菜的经历，展现了革命者的乐观主义精神和对劳动的热爱。', '这篇散文以朴实清新的语言，通过对劳动生活的描写，展现了深刻的人生哲理，是吴伯箫散文的精品。', '/audio/caiyuan.mp3', '/image/yuedu34.jpg', 223, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (169, '第二次考试', '何为叙事散文', 3, 2, 3, 8, 2, '著名的声乐专家苏林教授发现了一件奇怪的事情：在这次参加考试的二百多名考生中，有一个二十岁的女生陈伊玲，初试成绩十分优异，声乐、视唱、练耳和乐理都列入优等，尤其是她的音色美丽、音域宽广，令人赞叹。而复试时却使人大失所望。苏林教授一生桃李满天下，但这样年青而又有才华的学生却还是第一个，这样的事情也还是第一次碰到。', '何为', '现代', '通过考试故事展现人物品质', '运用悬念手法增强故事吸引力', '那次公开的考试是在一间古色古香的大厅里举行的。当陈伊玲镇静地站在考试委员会的几位声乐专家面前，唱完了冼星海的那支有名的《二月里来》时，专家们不由得互相递了递赞赏的眼色。按照规定，应试者还要唱一支外国歌曲，她唱的是意大利歌剧《蝴蝶夫人》中的咏叹调《有一个良辰佳日》。她那灿烂的音色和深沉的感情惊动了四座。', '[\"声乐\",\"音域\",\"咏叹调\"]', '文章通过陈伊玲两次考试的不同表现制造悬念，最后揭示真相，展现了人物美好的品质。', '这篇散文构思巧妙，情节曲折，人物形象鲜明，体现了何为散文的艺术特色。', '/audio/dierci.mp3', '/image/yuedu35.jpg', 245, b'1', 'admin', '2025-10-24 11:48:53.695', 'admin', '2025-10-24 11:48:53.695');
INSERT INTO `poetry_learning_content` VALUES (170, '中国的石拱桥', '茅以升科普文章', 3, 1, 3, 9, 2, '石拱桥的桥洞成弧形，就像虹。古代神话里说，雨后彩虹是\"人间天上的桥\"，通过彩虹就能上天。我国的诗人爱把拱桥比作虹，说拱桥是\"卧虹\"\"飞虹\"，把水上拱桥形容为\"长虹卧波\"。石拱桥在世界桥梁史上出现得比较早。这种桥不但形式优美，而且结构坚固，能几十年几百年甚至上千年雄跨在江河之上，在交通方面发挥作用。', '茅以升', '现代', '介绍中国石拱桥的历史和特点', '通过具体例子说明石拱桥的科学原理', '赵州桥横跨在洨河上，是世界著名的古代石拱桥，也是造成后一直使用到现在的最古的石桥。这座桥修建于公元605年左右，到现在已经一千三百多年了，还保持着原来的雄姿。', '[\"石拱桥\",\"弧形\",\"雄姿\"]', '文章通过赵州桥和卢沟桥等例子，详细说明了中国石拱桥的特点、历史和科学价值。', '这篇说明文语言准确生动，例子具体典型，是科普说明文的典范之作。', '/audio/shigongqiao.mp3', '/image/shuoming1.jpg', 234, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (171, '苏州园林', '叶圣陶建筑说明文', 3, 2, 3, 9, 2, '苏州园林据说有一百多处，我到过的不过十多处。其他地方的园林我也到过一些。倘若要我说说总的印象，我觉得苏州园林是我国各地园林的标本，各地园林或多或少都受到苏州园林的影响。因此，谁如果要鉴赏我国的园林，苏州园林就不该错过。设计者和匠师们因地制宜，自出心裁，修建成功的园林当然各个不同。可是苏州各个园林在不同之中有个共同点，似乎设计者和匠师们一致追求的是：务必使游览者无论站在哪个点上，眼前总是一幅完美的图画。', '叶圣陶', '现代', '介绍苏州园林的艺术特色', '通过具体特征说明园林设计理念', '他们讲究亭台轩榭的布局，讲究假山池沼的配合，讲究花草树木的映衬，讲究近景远景的层次。总之，一切都要为构成完美的图画而存在，决不容许有欠美伤美的败笔。', '[\"苏州园林\",\"自出心裁\",\"亭台轩榭\"]', '文章系统地介绍了苏州园林的整体特征和具体设计手法，展现了江南园林的艺术成就。', '这篇说明文结构清晰，语言优美，既有科学性又有艺术性，是建筑说明文的精品。', '/audio/suzhou.mp3', '/image/shuoming2.jpg', 267, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (172, '看云识天气', '科普说明文', 3, 1, 3, 9, 1, '天上的云，真是姿态万千，变化无常。它们有的像羽毛，轻轻地飘在空中；有的像鱼鳞，一片片整整齐齐地排列着；有的像羊群，来来去去；有的像一床大棉被，严严实实地盖住了天空；还有的像峰峦，像河流，像雄狮，像奔马……它们有时把天空点缀得很美丽，有时又把天空笼罩得很阴森。刚才还是白云朵朵，阳光灿烂；一霎间却又是乌云密布，大雨倾盆。云就像是天气的\"招牌\"，天上挂什么云，就将出现什么样的天气。', '佚名', '现代', '介绍云与天气的关系', '通过云的形态变化预测天气', '那最轻盈、站得最高的云，叫卷云。这种云很薄，阳光可以透过云层照到地面，房屋和树木的光与影依然很清晰。卷云丝丝缕缕地飘浮着，有时像一片白色的羽毛，有时像一块洁白的绫纱。如果卷云成群成行地排列在空中，好像微风吹过水面引起的鳞波，这就成了卷积云。', '[\"姿态万千\",\"变化无常\",\"卷云\"]', '文章通过介绍各种云的形态特征及其与天气变化的关系，教会读者通过观察云来预测天气。', '这篇说明文观察细致，描述生动，科学性强，是优秀的科普读物。', '/audio/kanyun.mp3', '/image/shuoming3.jpg', 289, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (173, '大自然的语言', '物候学说明文', 3, 2, 3, 9, 2, '立春过后，大地渐渐从沉睡中苏醒过来。冰雪融化，草木萌发，各种花次第开放。再过两个月，燕子翩然归来。不久，布谷鸟也来了。于是转入炎热的夏季，这是植物孕育果实的时期。到了秋天，果实成熟，植物的叶子渐渐变黄，在秋风中簌簌地落下来。北雁南飞，活跃在田间草际的昆虫也都销声匿迹。到处呈现一片衰草连天的景象，准备迎接风雪载途的寒冬。在地球上温带和亚热带区域里，年年如是，周而复始。', '竺可桢', '现代', '介绍物候学的基本知识', '通过自然现象说明物候规律', '这些自然现象，我国古代劳动人民称它为物候。物候知识在我国起源很早。古代流传下来的许多农谚就包含了丰富的物候知识。到了近代，利用物候知识来研究农业生产，已经发展为一门科学，就是物候学。物候学记录植物的生长荣枯，动物的养育往来，如桃花开、燕子来等自然现象，从而了解随着时节推移的气候变化和这种变化对动植物的影响。', '[\"物候\",\"周而复始\",\"农谚\"]', '文章系统地介绍了物候学的概念、研究内容和实际应用，强调了物候观测对农业生产的重要性。', '这篇说明文语言生动形象，科学性强，既有知识性又有实用性，是优秀的科普作品。', '/audio/ziran.mp3', '/image/shuoming4.jpg', 256, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (174, '奇妙的克隆', '生物技术说明文', 3, 2, 3, 9, 2, '一个细菌经过20分钟左右就可一分为二；一根葡萄枝切成十段就可能变成十株葡萄；仙人掌切成几块，每块落地就生根；一株草莓依靠它沿地\"爬走\"的匍匐茎，一年内就能长出数百株草莓苗……凡此种种，都是生物靠自身的一分为二或自身的一小部分的扩大来繁衍后代，这就是无性繁殖。无性繁殖的英文名称叫\"Clone\"，音译为\"克隆\"。实际上，英文的\"Clone\"起源于希腊文\"Klone\"，原意是用\"嫩枝\"或\"插条\"繁殖。', '谈家桢', '现代', '介绍克隆技术的原理和应用', '通过具体例子说明克隆技术', '克隆技术会给人类带来极大的好处。例如，英国PPL公司已培育出羊奶中含有治疗肺气肿的α-I抗胰蛋白酶的母羊。这种羊奶的售价是6000美元一升。一只母羊就好比一座制药厂。用什么办法能最有效、最方便地使这种羊扩大繁殖呢？最好的办法就是\"克隆\"。', '[\"克隆\",\"无性繁殖\",\"匍匐茎\"]', '文章详细介绍了克隆技术的原理、发展历程、应用前景和伦理问题，全面客观地说明了这一生物技术。', '这篇说明文概念准确，条理清晰，既介绍了科学知识，又探讨了科技伦理，具有重要的科普价值。', '/audio/kelong.mp3', '/image/shuoming5.jpg', 278, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (175, '恐龙无处不在', '科普说明文', 3, 1, 3, 9, 1, '不同科学领域之间是紧密相连的。在一个科学领域的新发现肯定会对其他领域产生影响。例如，在1986年1月，阿根廷南极研究所宣布在詹姆斯罗斯岛发现了一些化石骨骼。该岛是离南极海岸不远的一小片冰冻陆地，非常靠近南美的南端。这些骨头毫无疑问属于鸟臀目恐龙。在地球的其他大陆上也都发现有恐龙化石。这些古老的爬行动物在南极的出现，说明恐龙确实遍布于世界各地。', '阿西莫夫', '美国', '通过恐龙化石说明大陆漂移说', '运用具体证据支持科学理论', '如果把这个发现与南极大陆联系起来，这比仅考虑恐龙来说要重要得多。恐龙如何能在南极地区生存呢？恐龙实际上并不适应寒冷气候，但1986年在南极确实发现了这种古老的动物的化石。恐龙不可能在每一块大陆上独立生存，那么它们是如何越过大洋到另一个大陆上去的呢？这一问题的答案是：是大陆在漂移而不是恐龙自己在迁移。', '[\"恐龙\",\"化石\",\"大陆漂移\"]', '文章通过南极发现恐龙化石这一事实，有力地支持了大陆漂移学说，说明了各科学领域之间的密切联系。', '这篇说明文逻辑严密，证据充分，展现了科学发现的思维过程，是优秀的科普读物。', '/audio/konglong.mp3', '/image/shuoming6.jpg', 245, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (176, '生物入侵者', '生态学说明文', 3, 2, 3, 9, 2, '当你在路边草地或自家庭院里发现一两只从未见过的甲虫时，你肯定不会感到惊讶。但在生物学家和生态学家们看来，这或许不是件寻常小事。专家们把这种原本生活在异国他乡、通过非自然途径迁移到新的生态环境中的\"移民\"称为\"生物入侵者\"。它们不仅会破坏某个地区原有的生态系统，而且还可能给人类社会造成难以估量的经济损失。', '梅涛', '现代', '介绍生物入侵现象和危害', '通过具体案例说明生态保护重要性', '斑贝\"偷渡\"到北美大陆后，由于没有天敌的制约，在五大湖区内疯狂繁殖，挤占了本地贝类的生存空间，导致许多本地贝类灭绝。不仅如此，它们还堵塞管道，给美国和加拿大带来了巨大的经济损失。在欧洲，一种来自亚洲的龙虾状生物疯狂地侵蚀当地海岸，使海岸线上的堤坝和码头受到严重破坏。', '[\"生物入侵者\",\"生态系统\",\"斑贝\"]', '文章通过多个具体案例，详细说明了生物入侵的现象、危害和防治措施，强调了生态保护的重要性。', '这篇说明文案例典型，分析深入，具有很强的现实意义和警示作用。', '/audio/shengwu.mp3', '/image/shuoming7.jpg', 223, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (177, '你一定会听见的', '声学知识说明文', 3, 1, 3, 9, 1, '你听过蒲公英梳头的声音吗？蒲公英有一蓬金黄色的头发，当起风的时候，头发互相轻触着，像磨砂纸那样沙沙地一阵细响，转眼间，她的头发，全被风儿梳掉了！你听过蚂蚁小跑步的声音吗？那一天，蚂蚁们排列在红红的枫叶上准备做体操，\"噗\"，一粒小酸果从头顶落下，\"不好，炸弹来啦！\"顷刻间，它们全逃散了！你听过雪花飘落的声音吗？一个宁静的冬夜，一朵小小的雪花，从天上轻轻地、轻轻地飘下，飘啊飘，飘落在路边一盏孤灯的面颊上，微微地一阵暖意，小雪花满足而温柔地融化了……', '桂文亚', '现代', '介绍声音的多样性和重要性', '通过生动描述培养观察能力', '你善于用你的耳朵吗？你听见了世界的声音吗？你用心听了吗？你听见了什么？这里的几个声音游戏，你要不要试着玩玩看，也试着把感觉记录下来？轻轻松松嚼几片脆脆的饼干、几颗硬硬的糖果，感觉一下是什么声音？把玻璃纸揉成一团，然后聆听它缓缓舒展的声音。', '[\"蒲公英\",\"蚂蚁\",\"雪花\"]', '文章通过生动有趣的声音描述，引导读者关注生活中的各种声音，培养敏锐的观察力和感受力。', '这篇说明文语言优美，富有诗意，在传授知识的同时培养了读者的审美能力。', '/audio/shengyin.mp3', '/image/shuoming8.jpg', 267, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (178, '月亮——地球的妻子，姐妹，还是女儿', '天文知识说明文', 3, 2, 3, 9, 2, '古往今来，人们仰望天上的一轮明月，总会引起无穷的遐想。科学家们一直在思考：月球究竟是如何起源的？目前，关于月球的起源，大致有三种假设：月球若不是地球的妻子，那便是地球的姐妹，或者是地球的女儿。', '卞毓麟', '现代', '介绍月球起源的三种假说', '通过比喻说明复杂的科学理论', '第一种假说叫\"俘获说\"。月球原本是太阳系里的一颗小行星，在围绕太阳运行的过程中，接近地球，地球的引力就像慈母伸手拉住女儿一样，把月球拉了过来，使月球脱离原来的轨道而围绕地球旋转。有人认为，这个假说比较合理地解释了月球和地球在物质组成上的相似性和差异性。', '[\"月球\",\"俘获说\",\"引力\"]', '文章通过生动的比喻，系统地介绍了月球起源的三种主要假说，使复杂的科学理论变得通俗易懂。', '这篇说明文比喻恰当，语言生动，深入浅出地介绍了深奥的天文学知识，是优秀的科普作品。', '/audio/yueliang.mp3', '/image/shuoming9.jpg', 234, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (179, '花儿为什么这样红', '植物学说明文', 3, 2, 3, 9, 2, '花朵的红色是热情的色彩，它强烈，奔放，令人精神振奋。红紫烂漫的春天，活力充沛，生气蓬勃。花儿为什么这样红？人们一边赞叹，一边不免提出疑问，寻求科学的解释。花儿为什么这样红？首先有它的物质基础。不论是红花还是红叶，它们的细胞液里都含有由葡萄糖变成的花青素。当细胞液是酸性时，花青素呈红色，酸性愈强，颜色愈红。细胞液是碱性时，花青素呈蓝色，碱性较强，就成为蓝黑色，如墨菊、黑牡丹等。', '贾祖璋', '现代', '解释花朵颜色的科学原理', '从多个角度说明自然现象', '花儿为什么这样红？还需要用物理学原理来解释。太阳光经过三棱镜或水滴的折射，会分成红、橙、黄、绿、青、蓝、紫七种颜色。这七种颜色的光波长短不同，红光波长，紫光波短。酸性的花青素会把红色的长光波反射出来，送到我们的眼帘，我们便感觉到是鲜艳的红花。', '[\"花青素\",\"细胞液\",\"光波\"]', '文章从物质基础、物理学原理、生理需要、进化观点、自然选择、人工选择等角度，全面解释了花朵呈现红色的原因。', '这篇说明文角度全面，分析深入，语言生动，是科学小品的典范之作。', '/audio/huaer.mp3', '/image/shuoming10.jpg', 289, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (180, '中国建筑的特征', '梁思成建筑学文章', 3, 2, 3, 9, 3, '中国建筑体系是世界各民族数千年文化史中一个独特的建筑体系。它是中华民族数千年来世代经验的累积所创造的。这个体系分布到很广大的地区：西起葱岭，东至日本、朝鲜，南至越南、缅甸，北至黑龙江流域。这些地区的建筑和中国中心地区的建筑，或是同属于一个体系，或是大同小异，如弟兄之同属于一家的关系。', '梁思成', '现代', '系统介绍中国建筑的特点', '通过具体特征说明建筑体系', '中国建筑的基本特征可以概括为下列几点：一、个别建筑物的结构一般由三个主要部分构成：下部的台基，中间的房屋本身和上部翼状伸展的屋顶。二、在平面布置上，中国所称为\"一\"所\"房子\"是由若干座这种建筑物以及一些联系性的建筑物，如回廊、抱厦、厢、耳、过厅等等，围绕着一个或若干个庭院或天井建造而成的。', '[\"建筑体系\",\"台基\",\"庭院\"]', '文章系统地总结了中国建筑的九大特征，从结构、布局、装饰等方面全面介绍了中国传统建筑的特点。', '这篇说明文结构严谨，概念准确，是研究中国建筑的重要文献，也是优秀的科普读物。', '/audio/zhongguo.mp3', '/image/shuoming11.jpg', 256, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (181, '说\"屏\"', '陈从周传统文化说明文', 3, 2, 3, 9, 2, '屏，我们一般都称\"屏风\"，这是很富有诗意的名词。记得童年与家人在庭院纳凉，母亲总要背诵唐人\"银烛秋光冷画屏，轻罗小扇扑流萤\"的诗句，其情境真够令人销魂的了。后来每次读到诗词中咏屏的佳句，见到古画中的屏，便不禁心生向往之情。因为研究古代建筑，接触到这种似隔非隔、在空间中起着神秘作用的东西，更觉得它实在微妙。我们的先人，擅长在屏上做这种功能与美感相结合的文章，关键是在一个\"巧\"字上。', '陈从周', '现代', '介绍屏风的文化内涵和实用功能', '结合诗词说明传统文化', '屏可以分隔室内室外。过去的院子或天井中，为避免从门外直接望见厅室，必置一屏，上面有书有画，既起分隔作用，又是艺术点缀，而且可以挡风。而空间上还是流通的，如今称为\"流动空间\"。小时候厅上来了客人，就躲在屏后望一下。旧社会男女有别，双方不能见面，只得借助屏风了。', '[\"屏风\",\"似隔非隔\",\"流动空间\"]', '文章从屏风的实用功能、艺术价值和文化内涵等方面，全面介绍了这一传统家具的特点和意义。', '这篇说明文将知识性与文学性完美结合，既有科学准确的说明，又有诗情画意的描写。', '/audio/pingfeng.mp3', '/image/shuoming12.jpg', 223, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (182, '桥之美', '吴冠中艺术说明文', 3, 2, 3, 9, 2, '我之爱桥，并非着重于将桥作为大件工艺品来欣赏，也并非着眼于自李春的赵州桥以来的桥梁的发展，而是缘于桥在不同环境中的多种多样的形式作用。茅盾故乡乌镇的小河两岸都是密密的芦苇，真是密不透风，每当其间显现一座石桥时，仿佛发闷的苇丛做了一次深呼吸，透了一口舒畅的气。那拱桥的强劲的大弧线，或方桥的单纯的直线，都恰好与芦苇丛构成鲜明的对照。', '吴冠中', '现代', '从美学角度分析桥的价值', '通过具体例子说明美学原理', '早春天气，江南乡间石桥头细柳飘丝，那纤细的游丝拂着桥身坚硬的石块，即使碰不见晓风残月，也令画家销魂！湖水苍茫，水天一色，在一片单纯明亮的背景前突然出现一座长桥，卧龙一般，它有生命，而且往往有几百上千年的年龄。人们珍视长桥之美。颐和园里仿造的卢沟桥只17孔，苏州的宝带桥53孔之多，如果坐小船沿桥缓缓看一遍，你会感到像读了一篇史诗似的满足。', '[\"桥之美\",\"大弧线\",\"晓风残月\"]', '文章从艺术家的独特视角，分析了桥在不同环境中的美学价值，展现了桥与周围景物的和谐之美。', '这篇说明文视角独特，语言优美，在说明中融入了个人的艺术感受，富有感染力。', '/audio/qiaozhimei.mp3', '/image/shuoming13.jpg', 245, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (183, '故宫博物院', '黄传惕建筑说明文', 3, 2, 3, 9, 2, '在北京的中心，有一座城中之城，这就是紫禁城。现在人们叫它故宫，也叫故宫博物院。这是明清两代的皇宫，是我国现存的最大最完整的古代宫殿建筑群，有五百多年历史了。紫禁城的城墙十米多高，有四座城门：南面午门，北面神武门，东西面东华门、西华门。宫城呈长方形，占地72万平方米，有大小宫殿七十多座、房屋九千多间。城墙外是五十多米宽的护城河。', '黄传惕', '现代', '系统介绍故宫的建筑布局', '按照空间顺序说明建筑群', '从天安门往里走，沿着一条笔直的大道穿过端门，就到午门的前面。午门俗称五凤楼，是紫禁城的正门。走进午门，是一个宽广的庭院，弯弯的金水河像一条玉带横贯东西，河上是五座精美的汉白玉石桥。桥的北面是太和门，一对威武的铜狮守卫在门的两侧。', '[\"紫禁城\",\"午门\",\"金水河\"]', '文章按照空间顺序，详细介绍了故宫的整体布局、主要建筑及其功能，展现了这一伟大建筑群的艺术成就。', '这篇说明文条理清晰，描述准确，是了解故宫建筑的优秀入门读物。', '/audio/gugong.mp3', '/image/shuoming14.jpg', 312, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (184, '说园', '陈从周园林说明文', 3, 3, 3, 9, 3, '我国造园具有悠久的历史，在世界园林中树立着独特风格。学者们从各个方面分析我国园林，各抒高见。如今就我在接触园林中所见闻掇拾到的，提出\"园有静观、动观之分\"这个看法。所谓静观，就是园中予游者多驻足的观赏点；动观就是要有较长的游览线。二者说来，小园应以静观为主，动观为辅。庭院专主静观。大园则以动观为主，静观为辅。', '陈从周', '现代', '分析中国园林的艺术特点', '通过对比说明造园艺术', '大园如苏州拙政园，径缘池转，廊引人随，与\"日午画船桥下过，衣香人影太匆匆\"的瘦西湖相仿佛，妙在移步换影，这是动观。立意在先，文循意出。动静之分，要察看园林的大小性质而定。小园如苏州网师园，是园中小品，就像山水画的册页，尺幅窗，无心画，要做到含蓄不尽，让人留恋，宜静观。', '[\"造园\",\"静观\",\"动观\"]', '文章从静观与动观、疏与密、虚与实等多个角度，深入分析了中国园林的艺术特点和造园手法。', '这篇说明文分析深入，见解独到，是研究中国园林艺术的重要文献，具有很高的学术价值。', '/audio/shuoyuan.mp3', '/image/shuoming15.jpg', 234, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (185, '中国古代的书籍', '书籍发展史说明文', 3, 3, 3, 9, 3, '早在三千多年前，我国就已经有了文字。有了文字，就出现了最早的书籍。当时，人们把文字写在竹片或木片上，叫做竹简或木牍。这些竹片或木片用绳子编连起来，就成了一册书。但是，这种书很笨重，阅读、携带、保存都很不方便。古时候用\"学富五车\"形容一个人学问大，是因为书多得需要用车来拉。', '佚名', '现代', '介绍中国古代书籍的发展历程', '按照时间顺序说明书籍演变', '后来，人们把文字写在绸缎上，这种书叫帛书。它可以卷起来，一部书就是一卷绸缎，用木棒做轴，所以也叫卷轴。这种书比竹简轻便，但成本太高，不容易普遍采用。到了东汉，蔡伦总结了前人的经验，改进了造纸术。这种纸原料容易得到，又可以大量制造，价格又便宜，能满足多数人的需要，所以这种造纸方法就传承下来了。', '[\"竹简\",\"帛书\",\"卷轴\"]', '文章按照历史发展顺序，详细介绍了从竹简、帛书到雕版印刷、活字印刷的书籍演变过程。', '这篇说明文脉络清晰，史料翔实，生动地展现了中国书籍发展的历史画卷。', '/audio/guji.mp3', '/image/shuoming16.jpg', 267, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (186, '神奇的极光', '天文现象说明文', 3, 3, 3, 9, 3, '极光，是出现在地球南北两极附近地区高空的一种辉煌瑰丽的彩色光象。它的形状千姿百态，变幻无穷：有的像空中飘舞的彩带，有的像一团跳动的火焰，有的像巨大的帷幕，有的像轻盈的纱帐，有的像燃烧的朝霞，有的像飞泻的瀑布……长期以来，极光的成因机理未能得到满意的解释。在相当长一段时间内，人们一直认为极光可能是由以下三种原因形成的。', '曹冲', '现代', '解释极光形成的科学原理', '通过假设验证说明科学发现', '一种看法认为极光是地球外面燃起的大火，因为北极区临近地球的边缘，所以能看到这种大火。另一种看法认为，极光是红日西沉以后，透射反照出来的辉光。还有一种看法认为，极地冰雪丰富，它们在白天吸收阳光，贮存起来，到夜晚释放出来，便成了极光。随着科学技术的发展，这些观点的谬误性日益显露出来。', '[\"极光\",\"彩色光象\",\"成因机理\"]', '文章系统地介绍了极光的外观特征、历史记载、形成原理和观测方法，全面解释了这一天文现象。', '这篇说明文科学性强，解释深入，语言生动，是优秀的天文科普读物。', '/audio/jiguang.mp3', '/image/shuoming17.jpg', 245, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (187, '时间的脚印', '地质学说明文', 3, 3, 3, 9, 3, '时间一年一年地过去，我们仿佛并没有觉察到时间的流逝。然而，时间确实在流逝，而且留下了它的脚印。最能够记录时间脚印的，就是大自然中的岩石。岩石是怎样记录时间的呢？大自然中的各种物质都时时刻刻在运动着：这里在死亡，那里在生长；这里在建设，那里在破坏。就在我们读这篇文章的时候，地球上某些地方的岩石在被破坏，同时它们又被搬运到别的地方堆积起来，重新形成新的岩石。', '陶世龙', '现代', '介绍岩石记录地质历史的作用', '通过具体现象说明地质原理', '在北京故宫，我们还可以看到一种古老的计时装置：铜壶滴漏。水从一个铜壶缓缓地滴进另一个铜壶，时间就随着这滴答滴答的声音过去了。但是岩石的记录比铜壶滴漏更加精确，更加持久。岩石保存了远比上面所说得多得多的历史痕迹。有一种很粗糙的石头，叫做\"砾岩\"。你可以清楚地看到，砾岩中包含着从前的鹅卵石。这说明了岩石生成的地方，是当时陆地的边缘。', '[\"岩石\",\"砾岩\",\"铜壶滴漏\"]', '文章通过岩石的特征和变化，说明了地质历史的研究方法，展现了时间在地球上留下的痕迹。', '这篇说明文视角独特，逻辑严密，将抽象的时间概念通过具体的岩石特征表现出来，富有哲理。', '/audio/shijian.mp3', '/image/shuoming18.jpg', 223, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (188, '气候的威力', '气象学说明文', 3, 3, 3, 9, 3, '自从人类来到这个世界上，就一直面临着两种挑战：一是来自人类自身，一是来自大自然。实际上，人类正是在与这些挑战进行斗争的过程中不断前进的。特别是来自大自然的挑战，更为严峻。而在大自然的挑战中，气候的威力不能不说是举足轻重的。', '位梦华', '现代', '分析气候对人类社会的影响', '通过具体事例说明气候威力', '就拿南极来说吧，企鹅在这里生活了也许有几千万年，甚至上亿年，但它们顶多只是在海边徘徊，或者到内陆的冰原上溜达溜达，从来也没有深入到南极腹地。直到20世纪初，人类才首次到达南极点。但是，为了这短短的几分钟，人们却付出了惨重的代价。1911年，挪威的阿蒙森和英国的斯科特各率领一支探险队，开始了向南极点的进军。结果阿蒙森首先到达，顺利返回。而斯科特等五人却永远地留在了南极的冰原上。', '[\"气候\",\"南极\",\"探险队\"]', '文章通过南极探险等具体事例，说明了气候对人类活动的巨大影响，强调了认识自然规律的重要性。', '这篇说明文事例典型，分析深刻，既有科学性又有思想性，具有重要的警示意义。', '/audio/qihou.mp3', '/image/shuoming19.jpg', 256, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (189, '漫话小行星', '天文学说明文', 3, 3, 3, 9, 3, '在太阳系中，除了九大行星以外，还有成千上万颗我们肉眼看不到的小天体，它们像九大行星一样，沿着椭圆形的轨道不停地围绕太阳公转。与九大行星相比，它们好像是微不足道的碎石头。这些小天体就是小行星。大多数小行星是一些形状很不规则、表面粗糙、结构较松的石块，表层有含水矿物。它们的质量很小，按照天文学家的估计，所有小行星加在一起的质量也只有地球质量的万分之四。', '卞德培', '现代', '介绍小行星的特征和研究意义', '通过数据对比说明天文现象', '小行星的发现史是十分有趣的。1801年1月1日，意大利天文学家皮亚齐在火星和木星轨道之间发现了一个新天体，后来被命名为谷神星。在随后的几年中，人们又在火星和木星轨道之间发现了3个较大的天体。到19世纪末，被发现的小行星已有400多个。如今，天文学家们已经发现了数以万计的小行星。', '[\"小行星\",\"谷神星\",\"椭圆轨道\"]', '文章系统地介绍了小行星的发现历史、轨道特征、物理性质和科学研究价值，全面阐述了这一天文现象。', '这篇说明文数据准确，条理清晰，深入浅出地介绍了专业的天文学知识，是优秀的科普作品。', '/audio/xiaoxingxing.mp3', '/image/shuoming20.jpg', 234, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (190, '雨林的毁灭——世界性灾难', '生态学说明文', 3, 3, 3, 9, 3, '雨林，这个词语本身就让人联想到茂密而神秘的丛林。实际上，全球的热带雨林不仅是一个巨大的基因库，而且对全球的生态平衡起着举足轻重的作用。然而，这些宝贵的雨林正以惊人的速度从地球上消失。如果照现在的破坏速度发展下去，再过几十年，热带雨林可能会从地球上彻底消失。', '巴里·齐默尔曼', '英国', '分析雨林破坏的严重后果', '通过具体数据说明生态危机', '雨林的消失，意味着人类将永久丧失一大批物种。物种将濒临灭绝。很多物种现在就遭到威胁。橙色皮毛的猩猩（因其脸像人脸而被称为\"森林老人\"）过去在亚洲的任何地方都可以看到。由于其栖息地遭到极大破坏，这种在树上生活的惟一的一种大猩猩越来越少，只能在印度尼西亚找到。', '[\"雨林\",\"基因库\",\"物种灭绝\"]', '文章通过大量具体数据和案例，详细分析了雨林破坏对全球生态系统的严重影响，发出了保护环境的强烈呼吁。', '这篇说明文数据翔实，分析深刻，具有很强的说服力和警示作用，是优秀的环保科普读物。', '/audio/yulin.mp3', '/image/shuoming21.jpg', 278, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (191, '海洋是未来的粮仓', '资源学说明文', 3, 2, 3, 9, 2, '人口剧增，资源短缺，这是当今人类面临的最严重的环境问题之一。显然，能否妥善地解决这一问题，直接关系到人类未来的生死存亡。资源短缺的表现之一，是可耕土地资源不足，粮食生产的增长赶不上人口的增长。正是出于这样的考虑，许多人纷纷发出警告：地球将无法养活超过100亿的人口。然而，一些乐观的人士反对这种危言耸听的说法。他们认为，虽然陆地上可耕地的开发已近极限，但地球还有广阔的海洋可供开发，大海完全有可能成为人类未来的粮仓。', '佚名', '现代', '探讨海洋资源的开发潜力', '通过具体资源说明开发前景', '海洋中蕴藏着丰富的生物资源。仅以位于近海水域自然生长的海藻而言，年产量已相当于目前世界年产小麦总量的15倍以上。如果把这些藻类加工成食品，就可以为人们提供充足的蛋白质、多种维生素以及人体所需的矿物质。海洋中还有丰富的肉眼看不见的浮游生物。有人作过计算，在不破坏生态平衡的前提下，若能把它们捕捞出来，加工成食品，足可满足300亿人的需要。', '[\"海洋\",\"粮仓\",\"海藻\"]', '文章从海洋生物资源、化学资源和动力资源三个方面，全面探讨了海洋开发的巨大潜力和重要意义。', '这篇说明文观点新颖，论据充分，为解决人类面临的资源问题提供了新的思路，具有重要的现实意义。', '/audio/haiyang.mp3', '/image/shuoming22.jpg', 245, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (192, '走向虫子', '动物行为学说明文', 3, 2, 3, 9, 2, '那次，我盯着它，看它究竟要到哪里去。它急匆匆地，像有什么重要的事情，或者像被什么可怕的东西追赶着，爬得很快。我把它拨转过来，它犹豫一下，掉头朝相反的方向爬。我又把它拨转过来，它又重新选择方向。如此反复了好几次。这小小的生命，它要到哪里去？它知道自己的方向吗？我注意到，它其实并没有明确的方向，它只是在自己认定的路上不停地爬。', '刘亮程', '现代', '通过观察虫子探讨生命哲学', '通过具体观察引发深层思考', '蚂蚁是渺小的，它的身体那么小，它的力量那么微弱，它在大地上爬行，随时可能被踩死，被雨水冲走，被风吹走。但是，蚂蚁又是强大的。它们建造了复杂的地下宫殿，它们能够搬动比自身重几十倍的食物，它们有着严密的组织纪律。一只蚂蚁是弱小的，但是成千上万只蚂蚁组成的群体却是强大的。', '[\"蚂蚁\",\"渺小\",\"强大\"]', '文章通过对蚂蚁、蜘蛛等小虫子的细致观察，探讨了生命的顽强、自然的奥秘和人生的哲理。', '这篇说明文观察细致，思考深入，在科学观察中融入了哲学思考，富有深意。', '/audio/chongzi.mp3', '/image/shuoming23.jpg', 267, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (193, '沙尘暴敲响了警钟', '环境科学说明文', 3, 3, 3, 9, 3, '近年来，沙尘暴频频袭击我国北方地区。那滚滚的黄沙，不仅给人们的出行带来不便，更重要的是，它严重影响了人们的生活质量和身体健康。沙尘暴的肆虐，是大自然向人类发出的警告信号。那么，沙尘暴究竟是怎样形成的呢？', '李青松', '现代', '分析沙尘暴成因和防治措施', '通过现象分析环境问题本质', '沙尘暴的形成需要三个基本条件：一是强劲持久的大风，这是形成沙尘暴的动力条件；二是地面上的沙尘物质，这是形成沙尘暴的物质基础；三是不稳定的空气状态，这是重要的局地热力条件。我国西北地区深居内陆，远离海洋，降水稀少，气候干旱，植被稀疏，特别是由于人类不合理的垦荒、放牧、砍伐森林，使得土地沙漠化日益严重，为沙尘暴提供了充足的沙源。', '[\"沙尘暴\",\"沙漠化\",\"沙源\"]', '文章系统地分析了沙尘暴的形成条件、危害程度和防治措施，强调了生态环境保护的重要性。', '这篇说明文针对性强，分析透彻，提出了切实可行的防治建议，具有重要的现实意义。', '/audio/shachenbao.mp3', '/image/shuoming24.jpg', 289, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (194, '为什么母语让我们学得这么好', '语言学说明文', 3, 3, 3, 9, 3, '世界各地的人都学习自己民族的语言，也就是母语。几乎每个人都能熟练地掌握自己的母语，这是为什么呢？语言学家研究发现，母语学习有着独特的优势。首先，母语学习开始得最早。从婴儿时期开始，我们就沉浸在母语的环境中，不知不觉地学会了这种语言。', '佚名', '现代', '分析母语学习的优势', '通过对比说明语言习得规律', '其次，母语学习是在真实的语言环境中进行的。我们学习母语不是为了考试，而是为了实际生活的需要。我们要用母语表达自己的需求，理解别人的意思，与他人交流感情。这种真实的需求促使我们主动地、积极地学习母语。另外，母语学习是全方位的学习。我们不仅学习语言的发音、词汇和语法，还学习语言背后的文化、历史和思维方式。', '[\"母语\",\"语言环境\",\"思维方式\"]', '文章从多个角度分析了母语学习的优势，探讨了语言习得的规律，对语言教学有重要启示。', '这篇说明文分析深入，见解独到，对理解语言学习规律和改善语言教学方法有重要价值。', '/audio/muyu.mp3', '/image/shuoming25.jpg', 256, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (195, '数字时代如何保存记忆', '信息技术说明文', 3, 3, 3, 9, 3, '在数字时代，我们的记忆方式发生了革命性的变化。过去，人们用日记本、相册、信件来保存记忆；现在，我们更多地依赖数码照片、社交媒体、云存储来记录生活。但是，数字记忆真的可靠吗？研究表明，数字记忆面临着诸多挑战。', '佚名', '现代', '分析数字记忆的特点和风险', '通过对比说明信息技术问题', '数字记忆的第一个问题是载体寿命有限。光盘、硬盘、U盘等数字存储介质的寿命通常只有几年到几十年，远远低于纸张的保存时间。第二个问题是技术过时。今天能够读取的数据格式，几十年后可能因为软硬件的更新换代而无法读取。第三个问题是数据安全。数字数据容易受到病毒攻击、硬件损坏、人为删除等威胁。', '[\"数字记忆\",\"存储介质\",\"数据安全\"]', '文章系统地分析了数字记忆的优势和风险，提出了长期保存数字记忆的方法和建议。', '这篇说明文切合时代，分析全面，对数字时代的记忆保存有重要的指导意义。', '/audio/shuzi.mp3', '/image/shuoming26.jpg', 234, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (196, '蜜蜂的赞美', '生物学说明文', 3, 2, 3, 9, 2, '蜜蜂，这小小的昆虫，人们献给它多少赞美之词！它的酿蜜方式，常常令人惊叹不已。蜜蜂采蜜时的辛勤，从某一方面来说，可以用\"呕心沥血\"来形容。据说，一只蜜蜂要酿造一公斤蜂蜜，必须在100万朵花上采集原料。假如蜜蜂采蜜的花丛同蜂房的距离平均为一公里半，那么，蜜蜂采一公斤蜜，就得飞上45万公里，差不多等于绕地球赤道飞行11圈。', '秦牧', '现代', '介绍蜜蜂的生活习性和价值', '通过具体数据说明生物特性', '蜜蜂的复眼有6300个小眼，能够看到人眼看不到的紫外线。蜜蜂的嗅觉也很灵敏，能够分辨出40多种不同的气味。更令人惊奇的是蜜蜂的\"语言\"。侦察蜂发现蜜源后，会通过特定的舞蹈向同伴传达蜜源的方向和距离。圆形舞表示蜜源在近处，摆尾舞表示蜜源在远处，舞蹈的频率和角度准确地传达了具体信息。', '[\"蜜蜂\",\"酿蜜\",\"摆尾舞\"]', '文章通过详实的数据和生动的描述，全面介绍了蜜蜂的生物学特性、社会行为和生态价值。', '这篇说明文观察细致，描述生动，既有科学性又有文学性，是优秀的生物科普读物。', '/audio/mifeng.mp3', '/image/shuoming27.jpg', 278, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (197, '说\"茶\"', '茶文化说明文', 3, 2, 3, 9, 2, '中国是茶的故乡，是世界上最早发现茶树和利用茶叶的国家。据考证，中国人饮茶的历史可以追溯到神农时代，至少有4700多年的历史。直到现在，中国各族同胞还有民以茶代礼的风俗。茶，作为世界三大饮料之一，不仅是一种饮品，更是一种文化。', '佚名', '现代', '介绍茶的历史和文化', '从多个维度说明茶文化', '茶叶按照制作工艺的不同，可以分为绿茶、红茶、乌龙茶、白茶、黄茶、黑茶六大类。绿茶是不发酵茶，保留了鲜叶的天然物质；红茶是全发酵茶，茶多酚减少90%以上，产生了茶黄素、茶红素等新成分；乌龙茶是半发酵茶，兼具绿茶的清香和红茶的醇厚。不同的茶叶有不同的冲泡方法，水温、茶具、冲泡时间都有讲究。', '[\"茶文化\",\"六大茶类\",\"冲泡方法\"]', '文章从历史渊源、茶叶分类、制作工艺、冲泡方法、健康功效等方面，全面介绍了中国的茶文化。', '这篇说明文内容全面，条理清晰，既有知识性又有文化内涵，是了解中国茶文化的优秀读物。', '/audio/cha.mp3', '/image/shuoming28.jpg', 245, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (198, '大自然的文字', '自然观察说明文', 3, 1, 3, 9, 1, '大自然也有自己的文字。天上的每一颗星就是一个字，脚下的每一粒石子也是一个字。对于不认识大自然文字的人来说，所有的星体全是一样的。而有些人却认得许多星的名字，并且可以说出它跟别的星有什么分别。就像书里的话是用字母组成的一样，天上的星也组成星座。', '伊林', '苏联', '介绍自然现象的认识方法', '通过比喻说明自然观察', '从古以来，当水手们需要在海上寻找道路的时候，他们就去看那星星写成的书。你知道，在水面上船只是不会留痕迹的，那里也没有什么写着\"由此往北\"的有箭头的指路牌。但是水手们并不需要这样的指路牌。他们有带磷针的罗盘，磁针永远指着北边。即使他们没有罗盘，他们也照样迷不了路。他们朝天望望，在许多星座当中找到了小熊星座，在小熊星座当中找到了北极星。有北极星的那边就是北方。', '[\"大自然\",\"文字\",\"北极星\"]', '文章通过生动的比喻，介绍了通过星星、云朵、石头等自然现象认识世界的方法，培养了读者的观察能力。', '这篇说明文比喻恰当，语言生动，寓教于乐，是优秀的科普启蒙读物。', '/audio/zirandewenzi.mp3', '/image/shuoming29.jpg', 267, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (199, '细菌的启示', '微生物学说明文', 3, 3, 3, 9, 3, '细菌，这个听起来让人有些害怕的词语，实际上包含着远比我们想象中丰富得多的内容。在大多数人的印象中，细菌是致病的元凶，是应该被彻底消灭的敌人。然而，这种认识是片面的。实际上，细菌对于地球生态和人类生活有着不可替代的重要作用。', '佚名', '现代', '全面介绍细菌的生物学特性', '纠正对细菌的片面认识', '首先，细菌是地球上最早出现的生命形式之一。据科学家估计，细菌在地球上已经存在了35亿年之久。在这漫长的岁月里，细菌演化出了惊人的多样性。其次，细菌在生态系统中扮演着重要角色。它们是分解者，将动植物遗体分解成无机物，供植物重新利用。没有细菌，地球早就被生物遗体堆满了。', '[\"细菌\",\"生态系统\",\"分解者\"]', '文章从细菌的多样性、生态功能、与人类的关系等方面，全面客观地介绍了这一微生物群体。', '这篇说明文观点科学，论述全面，纠正了人们对细菌的片面认识，具有重要的科普价值。', '/audio/xijun.mp3', '/image/shuoming30.jpg', 234, b'1', 'admin', '2025-10-24 13:31:44.034', 'admin', '2025-10-24 13:31:44.034');
INSERT INTO `poetry_learning_content` VALUES (200, '谈骨气', '吴晗经典议论文', 3, 2, 3, 10, 2, '我们中国人是有骨气的。战国时代的孟子，有几句很好的话：\"富贵不能淫，贫贱不能移，威武不能屈，此之谓大丈夫。\"意思是说，高官厚禄收买不了，贫穷困苦折磨不了，强暴武力威胁不了，这就是所谓大丈夫。大丈夫的这种种行为，表现出了英雄气概，我们今天就叫做有骨气。', '吴晗', '现代', '1961年困难时期为鼓舞民族精神而作', '通过历史事例论证骨气的重要性', '我国经过了奴隶社会、封建社会的漫长时期，每个时代都有很多这样有骨气的人，我们就是这些有骨气的人的子孙，我们是有着优良革命传统的民族。当然，社会不同，阶级不同，骨气的具体含义也不同。这一点必须认识清楚。但是，就坚定不移地为当时的进步事业服务这一原则来说，我们祖先的许多有骨气的动人事迹，还有它积极的教育意义，是值得我们学习的。', '[\"骨气\",\"大丈夫\",\"英雄气概\"]', '文章通过具体的历史事例，论证了中国人是有骨气的，并阐述了骨气在不同时代的具体表现。', '这篇议论文观点鲜明，论据充分，论证有力，是议论文的典范之作，具有重要的教育意义。', '/audio/tanguqi.mp3', '/image/yilun1.jpg', 312, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (201, '怀疑与学问', '顾颉刚治学方法', 3, 3, 3, 10, 3, '\"学者先要会疑。\"——程颐\"在可疑而不疑者，不曾学；学则须疑。\"——张载学问的基础是事实和证据。事实和证据的来源有两种：一种是自己亲眼看见的，一种是听别人传说的。譬如在国难危急的时候，各地一定有许多口头的消息，说得如何凶险，那便是别人的传说，不一定可靠；要知道实际的情形，只有靠自己亲自去观察。做学问也是这样，最要紧最可靠的材料是自己亲见的事实根据；但这种证据有时候不能亲自看到，便只能靠别人的传说了。', '顾颉刚', '现代', '论述治学中怀疑精神的重要性', '通过引用和事例论证观点', '我们对于传说的话，不论信不信，都应当经过一番思考，不应当随随便便就信了。我们信它，因为它\"是\"；不信它，因为它\"非\"。这一番事前的思索，不随便轻信的态度，便是怀疑的精神。这是做一切学问的基本条件。我们听说中国古代有三皇、五帝，便要问问：这是谁说的话？最先见于何书？所见的书是何时何人著的？著者何以知道？我们又听说\"腐草为萤\"，也要问问：死了的植物如何会变成飞动的甲虫？有什么科学根据？我们若能这样追问，一切虚妄的学说便不攻自破了。', '[\"怀疑\",\"学问\",\"事实证据\"]', '文章论证了怀疑精神在治学中的重要作用，提出了\"怀疑—思索—辨别\"的治学方法。', '这篇议论文逻辑严密，论证有力，对培养科学精神和治学态度有重要指导意义。', '/audio/huaiyi.mp3', '/image/yilun2.jpg', 278, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (202, '发问的精神', '启发性议论文', 3, 2, 3, 10, 2, '我们日常所见、所闻、所接触的事物里，有很多的道理。大家因为时常见到、听到、接触到，都觉得那些事物平淡无奇，不足介意。其实这是一种损失。事物里的道理，不比课本的文字，教师的讲解，看了听了就可懂得。这种道理犹如封锁在秘库石室里的珍奇，我们要用一把钥匙去开启。这把钥匙就是发问的精神。', '佚名', '现代', '论述发问在求知中的重要性', '运用比喻和事例论证观点', '发问是思想的初步，研究的动机。一切知识的获得，大都从发问而来；新发明、新创造也常常由发问开端。能发问、勤发问的人，头脑自然会日益丰富，眼光自然会日益敏锐。别人不肯动脑筋的地方，他偏会想出惊人的见解；别人以为平常的事物，他偏会看出不平常的道理。这样的人，古今中外都有的是。', '[\"发问\",\"求知\",\"创造\"]', '文章通过具体事例和生动比喻，论证了发问精神在求知和创新中的重要作用。', '这篇议论文观点明确，论证生动，对激发学生的求知欲和探索精神有积极意义。', '/audio/fawen.mp3', '/image/yilun3.jpg', 245, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (203, '论求知', '培根经典论文', 3, 3, 3, 10, 3, '求知可以作为消遣，可以作为装饰，也可以增长才干。当你孤独寂寞时，阅读可以消遣。当你高谈阔论时，知识可供装饰。当你处世行事时，正确运用知识意味着力量。懂得事物因果的人是幸福的。有实际经验的人虽能够办理个别性的事务，但若要综观整体，运筹全局，却唯有掌握知识方能办到。', '培根', '英国', '论述求知的目的、方法和价值', '运用排比和对比增强说服力', '求知太慢会弛惰，为装潢而求知是自欺欺人，完全照书本条条办事会变成偏执的书呆子。求知可以改进人的天性，而实验又可以改进知识本身。人的天性犹如野生的花草，求知学习好比修剪移栽。实习尝试则可检验修正知识本身的真伪。狡诈者轻鄙学问，愚鲁者羡慕学问，唯聪明者善于运用学问。知识本身并没有告诉人怎样运用它，运用的方法乃在书本之外。这是一门技艺，不经实验就不能学到。', '[\"求知\",\"知识\",\"运用\"]', '文章系统地论述了求知的目的、方法、价值，强调了实践在求知过程中的重要性。', '这篇议论文思想深刻，论述精辟，语言凝练，是西方论说文的经典之作。', '/audio/lunqiuzhi.mp3', '/image/yilun4.jpg', 289, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (204, '俭以养德', '马铁丁道德论述', 3, 2, 3, 10, 2, '\"我的劳动所得，我愿怎么花就怎么花，你管得着吗？\"啊哟哟，同志，何必那么生气？我们并不是吝啬鬼，并不是那种临死的时候看见点两根灯草就闭不上眼睛的人。适当地改善自己的生活，有节制地满足自己合理的物质要求，岂但\"你管得着吗\"，而且是顺乎天理、合乎人情的。我们只是认为：无节制地信手乱花，即使是自己的劳动所得，也是有背于节约精神的。', '马铁丁', '现代', '论述节俭对品德修养的重要性', '通过说理和事例论证观点', '\"要富日子穷过\"的原则，适用于整个国家、每个集体、每个家庭，也适用于每个人。诸葛亮在《诫子书》中说：\"静以修身，俭以养德。\"节俭不仅是经济方面的事，而且牵联到思想品质。是故作惊人之谈吗？答曰：非也！一个人的脑子，容量总是有限的。这方面想得多，那方面就想得少了。脑子里过多地想着一顿佳肴、一件漂亮衣服、一架好无线电收音机之类，就不可能有更多的精力和时间去考虑工作和劳动的问题。', '[\"节俭\",\"养德\",\"思想品质\"]', '文章论证了节俭不仅是经济问题，更是关系到个人品德修养的重要问题。', '这篇议论文说理透彻，论证有力，对培养青少年的节俭美德有重要教育意义。', '/audio/jianyi.mp3', '/image/yilun5.jpg', 234, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (205, '谈读书', '朱光潜治学心得', 3, 3, 3, 10, 3, '书是读不尽的，就读尽也是无用，许多书都没有一读的价值。你多读一本没有价值的书，便丧失可读一本有价值的书的时间和精力，所以你须慎加选择。你自己自然不会选择，须去就教于批评家和专门学者。我不能告诉你必读的书，我能告诉你不必读的书。许多人曾抱定宗旨不读现代出版的新书。因为许多流行的新书只是迎合一时社会心理，实在毫无价值，经过时代淘汰而巍然独存的书才有永久性，才值得读一遍两遍以至于无数遍。', '朱光潜', '现代', '论述读书的方法和选择', '通过对比和说理阐述观点', '读书并不在多，最重要的是选得精，读得彻底。与其读十部无关轻重的书，不如以读十部书的时间和精力去读一部真正值得读的书；与其十部书都只能泛览一遍，不如取一部书精读十遍。\"旧书不厌百回读，熟读深思子自知\"，这两句诗值得每个读书人悬为座右铭。读书原为自己受用，多读不能算是荣誉，少读也不能算是羞耻。少读如果彻底，必能养成深思熟虑的习惯，涵泳优游，以至于变化气质；多读而不求甚解，则如驰骋十里洋场，虽珍奇满目，徒惹得心花意乱，空手而归。', '[\"读书\",\"选择\",\"精读\"]', '文章论述了读书的选择方法、精读的重要性以及读书与思考的关系。', '这篇议论文见解独到，说理透彻，对指导青少年读书学习有重要价值。', '/audio/tandushu.mp3', '/image/yilun6.jpg', 267, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (206, '失败是个未知数', '励志性议论文', 3, 1, 3, 10, 1, '你品尝过失败的滋味吗？测验不及格，升学考试不进分数段，比赛取不上名次……这都叫失败。失败的滋味当然是苦的。人的一生，不可能永远一帆风顺，不可避免要遭受这样或那样的失败，不过有的人栽跟头栽得多些，有的人栽得少些罢了。可以说，人生是不断栽跟头，而又不断爬起来前行的漫长过程。', '岑桑', '现代', '论述如何正确对待失败', '运用比喻和事例增强说服力', '失败的下一站是\"痛苦\"，但不是终点站，而是岔道口。这岔道口分岔出两条路：一条是心灰意冷、一蹶不振的路，这路通向彻底的失败，这时的失败才是真正的失败；另一条是汲取教训、奋起拼搏的路，这路通向再失败或失败的反面——成功，但只有踏上这条路，才有成功的希望。因此，一个人遭到了失败，并不意味着这就是最终的结果。问题在于：站在\"痛苦\"这个岔道口的时候，自己选择哪一条路。', '[\"失败\",\"痛苦\",\"成功\"]', '文章论证了失败不是终点而是新的起点，强调了在失败面前选择积极态度的重要性。', '这篇议论文观点积极，富有哲理，对培养学生面对挫折的勇气有重要教育意义。', '/audio/shibai.mp3', '/image/yilun7.jpg', 289, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (207, '起点之美', '刘心武人生感悟', 3, 2, 3, 10, 2, '到现场观看赛跑，多数人总愿选择离终点最近的位置，我却偏爱在起跑线附近观看。运动员在起点上的美往往被人忽略。其实，当运动员们在起点脱下外面的罩衣，露出紧凑而富有弹性的筋肉，先略事活动臂膊腿脚腰肢，再渐渐弹跳着、抖擞着，准备进入比赛，那神情，那体态，那气氛，就已非常之优雅。', '刘心武', '现代', '论述起点在人生中的意义', '通过赛跑比喻阐述人生哲理', '在人生的漫长道路上，起点往往太多，以至于我们忘记了我们出发的地方，忘记了起点之美。终点之美，属于优胜者；起点之美，属于每一个人。而自觉地进入起点并调动起自己的美来，也便是人生中的一种优胜。人的一生，可能平平淡淡，也可能轰轰烈烈，有过失败的考验，也有过成功的喜悦。爱与恨，悲与欢，……贯穿其间的是生命的过程。生命的过程，就是从一个起点走向另一个起点的过程。', '[\"起点\",\"人生\",\"过程\"]', '文章通过赛跑的比喻，论述了起点在人生中的重要意义，强调了重视过程的价值。', '这篇议论文视角独特，富有哲理，对帮助学生树立正确的人生观有积极意义。', '/audio/qidian.mp3', '/image/yilun8.jpg', 256, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (208, '才能来自勤奋学习', '成才规律论述', 3, 2, 3, 10, 2, '生而知之者是不存在的，\"天才\"也是不存在的。人们的才能虽有差别，但主要来自于勤奋学习。学习也是实践，不断的学习实践是人们获得才能的基础和源泉。没有学不会的东西，问题在于你肯不肯学，敢不敢学。自幼养成勤奋学习的习惯，就会比一般人早一些表现出有才能，人们却误认为是什么\"天才\"，捧之为\"神童\"。其实，\"天才\"和\"神童\"的才能主要也是后天获得的。当所谓的\"天才\"和\"神童\"一旦被人们发现后，捧场、社交等等因素阻止了他们继续勤奋学习，渐渐落后了，最后竟一事无成者，在历史上是屡见不鲜的。', '佚名', '现代', '论证才能与勤奋的关系', '通过事例和说理反驳天才论', '牛顿、爱因斯坦、爱迪生都不是\"神童\"。牛顿终身勤奋学习，很少在午夜两三点以前睡觉，常常通宵达旦工作。爱因斯坦读中学时成绩并不好，考了两次大学才被录取，学习也不出众，毕业后相当一段时间找不到工作，后来在瑞士伯尔尼专利局当了七年职员。就是在这七年里，爱因斯坦在艰苦的条件下顽强地学习、工作着，利用业余时间勾划出了相对论的理论基础。', '[\"才能\",\"勤奋\",\"学习\"]', '文章通过具体事例和严密说理，论证了才能主要来自勤奋学习的观点，驳斥了天才论。', '这篇议论文论据充分，论证有力，对激励学生勤奋学习有重要教育意义。', '/audio/caineng.mp3', '/image/yilun9.jpg', 278, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (209, '事业篇', '柳斌杰人生价值论述', 3, 3, 3, 10, 3, '自然造物，最美好的莫过于人的生命。然而它来去匆匆。唯其珍贵，我们就要探寻：究竟什么能够使它永葆青春？究竟什么能够使它迸发出灿烂的光华？是事业。精神贯注，事业必成；身体力行，事业必成；坚韧不拔，事业必成！古往今来，无数事实证明：事业是人生的常青之树。它植根于人民幸福的土壤中，汇合千千万万的人生，永远生气勃勃，枝繁叶茂。', '柳斌杰', '现代', '论述事业对人生价值的意义', '运用排比和比喻增强感染力', '谁建树了对人类有益的事业，谁就筑起了一座人生的纪念碑。和人类历史上的任何事业相比，共产主义事业是最宏伟的，它给人类创造彻底的、全面的、永恒的幸福。献身于共产主义事业的人们，英名和业绩将永远留在人民心中。我们为能赶上这样辉煌的时代而自豪。新一代青年，只要把自己的一生献给共产主义，那无疑就是在建造人生永存的金字塔。', '[\"事业\",\"人生\",\"价值\"]', '文章论述了事业对实现人生价值的重要意义，号召青年为崇高事业奋斗。', '这篇议论文思想深刻，富有激情，对帮助学生树立远大理想有重要教育意义。', '/audio/shiye.mp3', '/image/yilun10.jpg', 245, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (210, '理想的阶梯', '励志性议论文', 3, 2, 3, 10, 2, '青年最爱谈理想，青年最苦恼的是理想和现实常常有矛盾。有的青年虽有理想，但刻苦勤奋不足；有的也很想为理想努力，但不能抓紧一点一滴的时间；有的自以为条件差，岗位平凡，无用武之地，不能充分发挥主观能动作用。结果，常常在碌碌无为的苦闷中慨叹蹉跎。奋斗，是实现理想的阶梯。离开奋斗，理想就只能是幻想而已。有理想的青年，都应从眼前的现实起步，以非常艰苦的奋斗，作为通往理想境界的阶梯。', '佚名', '现代', '论述实现理想的途径', '通过具体方法指导实践', '理想的阶梯，属于刻苦勤奋的人。马克思为实现解放全人类的崇高理想奋斗一生。他积极投身于火热的工人运动，研读无数种著作，学会了欧洲好几个国家的语言。他不断在图书馆钻研，数十年如一日，座位下的地面竟然磨掉一层。化学家诺贝尔为减轻工地上挖土工人的繁重劳动，决心发明炸药。废寝忘食，四年里做了几百次试验。最后一次试验时，他聚精会神地盯着燃延的导火线。一声巨响，在旁的人们惊叫：\"诺贝尔完了！\"诺贝尔却从浓烟中跳出来，面孔乌黑，身上带着血，兴奋地狂呼：\"成功了！\"', '[\"理想\",\"奋斗\",\"勤奋\"]', '文章从刻苦勤奋、珍惜时间、迎难而上三个方面论述了实现理想的具体途径。', '这篇议论文层次清晰，论据典型，对指导学生实现人生理想有重要实践意义。', '/audio/lixiang.mp3', '/image/yilun11.jpg', 267, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (211, '论\"入迷\"', '茅盾治学态度论述', 3, 3, 3, 10, 3, '有多种多样的\"入迷\"。吉诃德先生看武侠小说把一份家产几乎看光，还嫌不够，还要出去行侠，终于把一条老命也赔上。这是\"入迷\"的一种。《红楼梦》上香菱学诗，弄得茶饭无心，梦里也做诗。这也是\"入迷\"。但据说香菱居然把诗做好了。乡间有伧夫读《封神榜》，搔头抓耳，心花大放，忽开窗俯瞰，窗下停有馄饨担，开了锅盖，热气蓬蓬直上。伧夫见了，遽大叫道：\"吾神驾祥云去也！\"跨窗而出，把馄饨担踹翻了。这又是一种的\"入迷\"，然而程度远在吉诃德先生之下。', '茅盾', '现代', '分析不同性质的入迷及其结果', '通过对比论证阐述观点', '吉诃德先生的\"入迷\"，结果是悲剧。乡间伧夫的\"入迷\"，结果是喜剧。香菱的\"入迷\"，结果不悲不喜，只成了一篇平凡的故事。就\"入迷\"而论，吉诃德先生实在是伟大的；你看他始终不动摇。乡间伧夫那一\"入迷\"，实在渺小得很，他只梦想那高不可攀的\"神道\"，却忘记了他那热气蓬蓬的馄饨担。至于香菱，她茶饭无心地读杜工部温飞卿的时候，她惟一目的是自己也做个诗人。使她着了\"迷\"的，不是杜工部他们的作品，而是她自己想做诗人这一念的\"虚荣\"。', '[\"入迷\",\"程度\",\"结果\"]', '文章通过对比分析，论述了不同性质、不同程度的入迷会带来不同的结果。', '这篇议论文分析深入，见解独到，对培养学生正确的学习态度有重要启示。', '/audio/rumi.mp3', '/image/yilun12.jpg', 234, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (212, '学问与智慧', '罗家伦教育论述', 3, 3, 3, 10, 3, '学问与智慧，有显然的区别。学问是知识的聚集，是一种滋养人生的原料，而智慧却是陶冶原料的熔炉。学问好比是铁，而智慧是炼钢的电火。学问是寸积铢累而来的，常是各有疆域独自为政的。它可吸收人生的兴趣，但是它本身却是人生的工具。智慧是一种透视，一种反想，一种远瞻；它是人生含蕴的一种放射性；它是从人生深处发出来的，同时它可以烛照人生的前途。', '罗家伦', '现代', '辨析学问与智慧的关系', '运用比喻说理阐述观点', '有人以为学问就是智慧，其实有学问的人，何曾都有智慧？世界上有不少学问渊博的人，可是食古不化，食今亦不化，不知融会贯通，举一不能反三，终身都跳不出书本的圈子，实在说不上\"智慧\"二字。这种人西洋便叫做\"有学问的笨伯\"，在中国便可称为\"两脚书橱\"或\"冬烘先生\"。反过来说，有智慧的人也不见得都有很好的学问。有一种人，读书虽然不多，但他对于人情事理，都很通达，凭借经验，运用心得，\"官知止而神欲行，依乎天理，批大郤，导大窾\"，这样的人，你能说他没有智慧吗？', '[\"学问\",\"智慧\",\"区别\"]', '文章辨析了学问与智慧的区别和联系，强调了智慧在治学中的重要性。', '这篇议论文思想深刻，辨析精微，对理解学问与智慧的关系有重要启发。', '/audio/xuewen.mp3', '/image/yilun13.jpg', 256, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (213, '说\"勤\"', '林家箴品德论述', 3, 2, 3, 10, 2, '中国有句俗话，叫做\"一勤天下无难事\"。唐朝大文学家韩愈也曾经说过：\"业精于勤\"。这就是说，学业方面的精深造诣来源于勤奋好学。勤，对好学上进的人来说，是一种美德。我们所说的勤，就是要人们善于珍惜时间，勤于学习，勤于思考，勤于探索，勤于实践，勤于总结。看古今中外，凡有建树者，在其历史的每一页上，无不用辛勤的汗水写着一个闪光的大字——\"勤\"。', '林家箴', '现代', '论述勤奋对成才的重要性', '通过中外事例论证观点', '勤出成果。马克思写《资本论》，辛勤劳动，艰苦奋斗了四十年，阅读了数量惊人的书籍和刊物，其中做过笔记的就有一千五百种以上；我国历史巨著《史记》的作者司马迁，从二十岁起就开始漫游生活，足迹遍及黄河、长江流域，汇集了大量的社会素材和历史素材，为《史记》的创作奠定了基础；德国伟大诗人、小说家和戏剧家歌德，前后花了五十八年的时间，搜集了大量的材料，写出了对世界文学和思想界产生很大影响的诗剧《浮士德》。', '[\"勤奋\",\"成才\",\"成果\"]', '文章从勤出成果和勤出智慧两个方面，论证了勤奋对成才的决定性作用。', '这篇议论文论据充分，论证有力，对激励学生勤奋学习有重要教育意义。', '/audio/shuoqin.mp3', '/image/yilun14.jpg', 278, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (214, '论友谊', '培根人际关系论述', 3, 3, 3, 10, 3, '古人曾说：喜欢孤独的人不是野兽便是神灵。没有比这句话更是把真理与谬误混合于一起的了。如果说，当一个人脱离了社会，甘愿遁入山林与野兽为侣，那么他是绝不可能成为神灵的。尽管有人这样做的目的，好像是要到社会之外去寻求一种更高尚的生活，就像古代的埃辟门笛斯、诺曼、埃辟格拉斯、阿波罗尼斯那样。', '培根', '英国', '论述友谊的价值和意义', '通过说理和事例阐述观点', '缺乏真正的朋友乃是最纯粹最可怜的孤独；没有友谊则斯世不过是一片荒野；我们还可以用这个意义来论\"孤独\"说，凡是天性不配交友的人其性情可说是来自禽兽而不是来自人类的。友谊的主要效用之一就在使人心中的愤懑抑郁之气得以宣泄弛放，这些不平之气是各种的情感都可以引起的。闭塞之症于人的身体最为凶险，这是我们知道的；在人的精神方面亦复如此：你可以服撒尔沙以通肝，服钢以通脾，服硫华以通肺，服海狸胶以通脑，然而除了一个真心的朋友之外没有一样药剂是可以通心的。', '[\"友谊\",\"孤独\",\"效用\"]', '文章从多个角度论述了友谊的价值和意义，强调了真诚友谊的重要性。', '这篇议论文思想深刻，论述全面，是西方论说友谊的经典之作。', '/audio/lunyouyi.mp3', '/image/yilun15.jpg', 245, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (215, '拿来主义', '鲁迅文化批判', 3, 3, 3, 10, 3, '中国一向是所谓\"闭关主义\"，自己不去，别人也不许来。自从给枪炮打破了大门之后，又碰了一串钉子，到现在，成了什么都是\"送去主义\"了。别的且不说罢，单是学艺上的东西，近来就先送一批古董到巴黎去展览，但终\"不知后事如何\"；还有几位\"大师\"们捧着几张古画和新画，在欧洲各国一路的挂过去，叫作\"发扬国光\"。听说不远还要送梅兰芳博士到苏联去，以催进\"象征主义\"，此后是顺便到欧洲传道。我在这里不想讨论梅博士演艺和象征主义的关系，总之，活人替代了古董，我敢说，也可以算得显出一点进步了。', '鲁迅', '现代', '批判对待文化遗产的错误态度', '运用比喻说理阐述文化立场', '但我们没有人根据了\"礼尚往来\"的仪节，说道：拿来！当然，能够只是送出去，也不算坏事情，一者见得丰富，二者见得大度。尼采就自诩过他是太阳，光热无穷，只是给与，不想取得。然而尼采究竟不是太阳，他发了疯。中国也不是，虽然有人说，掘起地下的煤来，就足够全世界几百年之用。但是，几百年之后呢？几百年之后，我们当然是化为魂灵，或上天堂，或落了地狱，但我们的子孙是在的，所以还应该给他们留下一点礼品。要不然，则当佳节大典之际，他们拿不出东西来，只好磕头贺喜，讨一点残羹冷炙做奖赏。', '[\"拿来主义\",\"闭关主义\",\"送去主义\"]', '文章批判了对待文化遗产的三种错误态度，主张运用脑髓、放出眼光、自己来拿。', '这篇议论文思想深刻，比喻精妙，是鲁迅杂文中的经典之作，具有重要的文化意义。', '/audio/nalai.mp3', '/image/yilun16.jpg', 312, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (216, '读书与革命', '鲁迅教育论述', 3, 3, 3, 10, 3, '现在我因为职务上的关系，不能不说几句话，可是有许多好的话，以前几位先生已经讲完了，我再没有什么话可讲了。我想中山大学，并不是今天开学的日子才起始的，三十年前已经有了。中山先生一生致力革命，宣传，运动，失败了又起来，失败了又起来，这就是他的讲义。他用这样的讲义教给学生，后来大家发表的成绩，即是现在的中华民国。中山先生给后人的遗嘱上说：\"革命尚未成功，同志仍须努力。\"这中山大学就是\"努力\"的一部分。', '鲁迅', '现代', '论述读书与革命的关系', '在中山大学开学典礼上的演讲', '为革命起见，要有\"革命人\"，\"革命文学\"倒无须急急，革命人做出东西来，才是革命文学。所以，我想：革命，倒是与文章有关系的。革命时代的文学和平时的文学不同，革命来了，文学就变换色彩。但大革命可以变换文学的色彩，小革命却不，因为不算什么革命，所以不能变换文学的色彩。在此地是听惯了\"革命\"了，江苏浙江谈到革命二字，听的人都很害怕，讲的人也很危险。其实\"革命\"是并不稀奇的，惟其有了它，社会才会改革，人类才会进步，能从原虫到人类，从野蛮到文明，就因为没有一刻不在革命。', '[\"读书\",\"革命\",\"文学\"]', '文章论述了读书与革命的关系，强调了革命实践的重要性。', '这篇议论文观点鲜明，论述深刻，体现了鲁迅的革命思想和教育理念。', '/audio/dushu.mp3', '/image/yilun17.jpg', 289, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (217, '人生的境界', '冯友兰哲学论述', 3, 3, 3, 10, 3, '哲学的任务是什么？我曾提出，按照中国哲学的传统，它的任务不是增加关于实际的积极的知识，而是提高人的精神境界。在这里更清楚地解释一下这个话的意思，似乎是恰当的。我在《新原人》一书中曾说，人与其他动物的不同，在于人做某事时，他了解他在做什么，并且自觉地在做。正是这种觉解，使他正在做的事对于他有了意义。他做各种事，有各种意义，各种意义合成一个整体，就构成他的人生境界。', '冯友兰', '现代', '论述人生境界的层次', '运用哲学概念分析人生问题', '每个人各有自己的人生境界，与其他任何个人的都不完全相同。若是不管这些个人的差异，我们可以把各种不同的人生境界划分为四个等级。从最低的说起，它们是：自然境界，功利境界，道德境界，天地境界。一个人做事，可能只是顺着他的本能或其社会的风俗习惯。就像小孩和原始人那样，他做他所做的事，而并无觉解，或不甚觉解。这样，他所做的事，对于他就没有意义，或很少意义。他的人生境界，就是我所说的自然境界。', '[\"人生\",\"境界\",\"哲学\"]', '文章系统地论述了人生境界的四个层次，阐述了哲学在提升人生境界中的作用。', '这篇议论文思想深刻，分析精辟，是冯友兰哲学思想的精华，具有重要的哲学价值。', '/audio/rensheng.mp3', '/image/yilun18.jpg', 267, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (218, '美与物理学', '杨振宁科学美学论述', 3, 3, 3, 10, 3, '物理学的发展，经历了几个重要的阶段。从牛顿力学到麦克斯韦电磁理论，到爱因斯坦的相对论，再到量子力学，每一个阶段都展现了物理学的美。这种美，不是日常生活中所说的美，而是一种更深层次的美，是理论结构的美，是自然规律的美。狄拉克在1963年的《科学美国人》中写道：\"使一个方程具有美感比使它去符合实验更重要。\"', '杨振宁', '现代', '论述物理学中的美学价值', '结合物理学史阐述科学美学', '牛顿的运动方程、麦克斯韦方程、爱因斯坦的狭义与广义相对论方程、狄拉克方程、海森伯方程和其他五六个方程是物理学理论架构的骨干。它们提炼了几个世纪的实验工作与唯象理论的精髓，达到了科学研究的最高境界。它们以极度浓缩的数学语言写出了物理世界的基本结构，可以说它们是造物者的诗篇。这些方程还有一方面与诗有共同点：它们的内涵往往随着物理学的发展而产生新的、当初所完全没有想到的意义。', '[\"物理学\",\"美\",\"方程\"]', '文章论述了物理学理论中的美学价值，探讨了科学与艺术的内在联系。', '这篇议论文视角独特，思想深刻，对理解科学中的美学价值有重要启发。', '/audio/meiyu.mp3', '/image/yilun19.jpg', 234, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (219, '传统文化与现代文明', '文化发展论述', 3, 3, 3, 10, 3, '中国传统文化源远流长，博大精深，是中华民族的瑰宝。在现代文明飞速发展的今天，如何对待传统文化，是一个值得深思的问题。全盘否定传统文化，无异于割断民族的精神命脉；盲目固守传统，又可能阻碍社会进步。正确的态度应该是：批判地继承，创新地发展。', '佚名', '现代', '论述传统文化与现代文明的关系', '运用辩证观点分析文化问题', '传统文化中既有精华，也有糟粕。比如，儒家思想中的\"仁者爱人\"、\"己所不欲，勿施于人\"等观念，体现了宝贵的人文精神，在今天仍然具有重要价值。而\"三纲五常\"中的某些封建礼教，则应该予以批判和抛弃。对待传统文化，我们要取其精华，去其糟粕，使之与现代社会相适应，与现代文明相协调。同时，我们还要以开放的心态吸收外来文化的优秀成果，在交流互鉴中发展中国特色社会主义文化。', '[\"传统文化\",\"现代文明\",\"批判继承\"]', '文章运用辩证观点分析了传统文化与现代文明的关系，提出了批判继承、创新发展的主张。', '这篇议论文观点辩证，论述全面，对理解文化传承与创新有重要价值。', '/audio/chuantong.mp3', '/image/yilun20.jpg', 256, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (220, '论创造', '创造精神论述', 3, 3, 3, 10, 3, '创造是人类进步的阶梯，是文明发展的动力。从石器时代的原始工具到今天的智能科技，从结绳记事的原始方法到现代的信息技术，人类文明的每一个进步都离不开创造。什么是创造？创造就是打破常规，创造就是推陈出新，创造就是在无人走过的地方开辟道路。', '佚名', '现代', '论述创造的意义和方法', '通过事例和说理阐述创造精神', '创造需要勇气。哥白尼提出日心说，冒着被教会迫害的危险；布鲁诺坚持真理，宁愿被烧死在鲜花广场；伽利略面对宗教裁判所的审判，仍然坚持\"可是地球仍在转动\"。创造需要智慧。牛顿从苹果落地发现万有引力，瓦特从水壶盖被蒸汽顶开发明蒸汽机，爱因斯坦从追光悖论提出相对论。创造更需要坚持。爱迪生发明电灯，试验了1600多种材料；居里夫人发现镭，在简陋的工棚里处理了8吨铀矿渣。', '[\"创造\",\"勇气\",\"智慧\"]', '文章从创造的意义、创造需要的品质、创造的方法等方面论述了创造精神的重要性。', '这篇议论文论述全面，富有激情，对培养学生的创新精神有重要教育意义。', '/audio/lunchuangzao.mp3', '/image/yilun21.jpg', 278, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (221, '青春与理想', '青年修养论述', 3, 2, 3, 10, 2, '青春是人生最宝贵的时期，理想是青春最绚丽的色彩。没有理想的青春，就像没有太阳的早晨；没有行动的理想，就像没有翅膀的鸟儿。青年时期是树立理想的关键时期，也是实现理想的黄金时期。在这个时期树立什么样的理想，选择什么样的道路，往往决定一个人一生的方向和成就。', '佚名', '现代', '论述青春与理想的关系', '运用比喻和事例激励青年', '纵观历史，大凡有成就者，无不在青年时期就树立了远大理想。马克思在中学毕业时就在作文中写道：\"如果我们选择了最能为人类福利而劳动的职业，那么，重担就不能把我们压倒，因为这是为大家而献身。\"周恩来少年时就立志\"为中华之崛起而读书\"。他们之所以能够成就伟大事业，正是因为他们在青年时期就确立了崇高的理想，并为之奋斗终身。', '[\"青春\",\"理想\",\"奋斗\"]', '文章论述了青春与理想的密切关系，强调了青年树立远大理想的重要性。', '这篇议论文富有激情，催人奋进，对帮助青年树立正确理想有重要教育意义。', '/audio/qingchun.mp3', '/image/yilun22.jpg', 289, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (222, '自由与约束', '哲学关系论述', 3, 3, 3, 10, 3, '自由是人类永恒的追求，但自由从来都不是绝对的、无限制的。卢梭说过：\"人生而自由，却无往不在枷锁之中。\"这句话深刻地揭示了自由与约束的辩证关系。真正的自由，是在认识必然性的基础上获得的自由，是在遵守规则的前提下享有的自由。', '佚名', '现代', '论述自由与约束的辩证关系', '运用哲学观点分析社会问题', '交通规则约束了驾驶员随意行驶的自由，却保障了所有人安全通行的自由；法律约束了人们为所欲为的自由，却保障了每个人正当权益的自由；道德规范约束了人们放纵欲望的自由，却保障了社会和谐有序的自由。可见，必要的约束不是对自由的否定，而是对自由的保障。没有约束的自由是野蛮的自由，最终会导致所有人的不自由。', '[\"自由\",\"约束\",\"辩证关系\"]', '文章运用辩证观点分析了自由与约束的关系，强调了规则和纪律对保障自由的重要性。', '这篇议论文思想深刻，论述辩证，对帮助学生理解自由的真谛有重要价值。', '/audio/ziyou.mp3', '/image/yilun23.jpg', 256, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (223, '论责任', '道德修养论述', 3, 2, 3, 10, 2, '人生在世，总要承担各种各样的责任。对家庭的责任，对工作的责任，对社会的责任，对国家的责任……责任，是人生的重要内涵，也是人格的试金石。一个没有责任感的人，不可能获得别人的信任和尊重；一个没有责任感的民族，不可能屹立于世界民族之林。', '佚名', '现代', '论述责任的意义和层次', '通过事例和说理阐述责任意识', '责任有不同的层次。最基本的是对自己的责任，就是要珍惜生命，提升素质，实现人生价值。高一个层次的是对家庭的责任，就是要孝敬父母，关爱子女，维护家庭和谐。再高一个层次的是对社会的责任，就是要遵守公德，热心公益，促进社会进步。最高层次的是对国家和民族的责任，就是要热爱祖国，维护统一，为实现民族复兴贡献力量。', '[\"责任\",\"层次\",\"人生价值\"]', '文章论述了责任的意义、层次和具体要求，强调了培养责任意识的重要性。', '这篇议论文层次清晰，论述深刻，对培养学生的责任感有重要教育意义。', '/audio/lunzeren.mp3', '/image/yilun24.jpg', 267, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (224, '幸福在哪里', '人生哲学论述', 3, 2, 3, 10, 2, '幸福，是人们永恒追求的目标。但是，幸福在哪里？不同的人有不同的答案。有人认为幸福在财富的积累中，有人认为幸福在权力的拥有中，有人认为幸福在享乐的满足中。然而，这些真的是幸福的真谛吗？', '佚名', '现代', '探讨幸福的真谛', '通过对比分析阐述幸福观', '古希腊哲学家德谟克利特说：\"幸福不在于占有畜群，也不在于占有黄金，它的居处是在我们的灵魂之中。\"幸福更多的是一种主观感受，一种精神体验。当我们通过努力实现了一个目标时，我们会感到幸福；当我们帮助别人解决了困难时，我们会感到幸福；当我们与家人朋友共享美好时光时，我们会感到幸福。幸福就在奋斗的过程中，在奉献的行动中，在珍惜的感悟中。', '[\"幸福\",\"精神体验\",\"主观感受\"]', '文章通过对比分析，探讨了幸福的真谛，强调了精神追求在获得幸福中的重要性。', '这篇议论文富有哲理，启人深思，对帮助学生树立正确的幸福观有重要价值。', '/audio/xingfu.mp3', '/image/yilun25.jpg', 278, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (225, '说\"嫉妒\"', '心理分析论述', 3, 3, 3, 10, 3, '嫉妒，是一种普遍存在的社会心理现象。从某种意义上说，没有嫉妒，就没有竞争；但过度的嫉妒，又会成为人际关系的毒药，个人心灵的枷锁。如何认识嫉妒，对待嫉妒，是人生的重要课题。', '佚名', '现代', '分析嫉妒心理及其应对', '运用心理学知识分析社会现象', '嫉妒的产生，往往源于比较。当看到别人在某方面比自己强时，有些人会产生羡慕之情，进而转化为奋发向上的动力；有些人则会产生嫉妒之心，进而转化为诋毁破坏的行为。前者是积极的嫉妒，可以促进个人进步；后者是消极的嫉妒，只会导致自我毁灭。古人说：\"临渊羡鱼，不如退而结网。\"与其嫉妒别人的成就，不如努力提升自己。', '[\"嫉妒\",\"心理\",\"竞争\"]', '文章分析了嫉妒心理的成因、表现和影响，提出了正确对待嫉妒的方法。', '这篇议论文分析深入，见解独到，对帮助学生克服嫉妒心理有重要指导意义。', '/audio/jidu.mp3', '/image/yilun26.jpg', 245, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (226, '论宽容', '品德修养论述', 3, 2, 3, 10, 2, '宽容，是一种美德，一种境界，一种力量。林则徐有句名言：\"海纳百川，有容乃大。\"一个人有了宽容的胸怀，才能成就大事业；一个社会有了宽容的氛围，才能实现大发展。', '佚名', '现代', '论述宽容的意义和价值', '通过历史事例阐述宽容精神', '宽容不是无原则的迁就，而是在坚持原则的基础上对人的理解和包容。宽容也不是软弱的表现，而是内心强大的象征。历史上，唐太宗宽容魏征的直谏，开创了贞观之治；蔺相如宽容廉颇的挑衅，成就了将相和的佳话。相反，周瑜因不能宽容诸葛亮的才能，最终气郁而亡。可见，宽容不仅有利于他人，更有利于自己。', '[\"宽容\",\"美德\",\"胸怀\"]', '文章论述了宽容的内涵、意义和实践要求，强调了宽容在个人修养和社会和谐中的重要性。', '这篇议论文观点明确，论据充分，对培养学生的宽容品质有重要教育意义。', '/audio/kuanrong.mp3', '/image/yilun27.jpg', 256, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (227, '谈\"忍\"', '处世哲学论述', 3, 3, 3, 10, 3, '中国传统文化中，\"忍\"是一个重要的处世哲学。从\"小不忍则乱大谋\"到\"忍一时风平浪静，退一步海阔天空\"，都体现了古人对\"忍\"的重视。但是，忍并不是无原则的退让，也不是懦弱的表现，而是一种智慧，一种策略，一种修养。', '佚名', '现代', '分析\"忍\"的智慧', '运用辩证观点阐述处世哲学', '忍有不同的境界。最低层次的是\"忍受\"，这是被动的、痛苦的忍；较高层次的是\"忍耐\"，这是主动的、有目的的忍；最高层次的是\"忍让\"，这是自觉的、有修养的忍。越王勾践卧薪尝胆是忍，韩信受胯下之辱是忍，司马迁忍辱负重著《史记》也是忍。他们的忍，都不是懦弱，而是为了更大的目标做出的暂时让步，体现了远见和智慧。', '[\"忍\",\"智慧\",\"处世哲学\"]', '文章分析了\"忍\"的不同境界和智慧，阐述了\"忍\"在人生中的重要意义。', '这篇议论文分析深入，富有哲理，对帮助学生理解处世智慧有重要价值。', '/audio/tanren.mp3', '/image/yilun28.jpg', 234, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (228, '论\"机遇\"', '成功因素论述', 3, 2, 3, 10, 2, '机遇，在人生中扮演着重要角色。有人说：\"机遇只偏爱有准备的头脑。\"有人说：\"弱者等待机遇，强者创造机遇。\"如何看待机遇，把握机遇，是每个人都面临的课题。', '佚名', '现代', '论述机遇与准备的关系', '通过事例论证成功规律', '机遇确实重要。诸葛亮如果没有遇到刘备，可能终老隆中；刘邦如果没有遇到秦末乱世，可能终老沛县。但是，机遇只对那些有准备的人才有意义。诸葛亮在隆中苦读十年，积累了丰富的知识；刘邦在基层为吏多年，积累了社会经验。当机遇来临时，他们才能抓住机遇，成就大业。所以，与其抱怨没有机遇，不如努力做好准备。当你的能力达到一定程度时，机遇自然会来敲门。', '[\"机遇\",\"准备\",\"成功\"]', '文章论述了机遇与准备的关系，强调了个人努力在把握机遇中的重要性。', '这篇议论文观点辩证，论述有力，对激励学生勤奋学习有重要教育意义。', '/audio/jiyu.mp3', '/image/yilun29.jpg', 267, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (229, '说\"平凡\"', '价值观念论述', 3, 2, 3, 10, 2, '在这个崇尚英雄、追求卓越的时代，我们往往忽略了平凡的价值。其实，伟大寓于平凡，崇高源于普通。社会的正常运转，靠的是千千万万普通人在平凡岗位上的默默奉献。', '佚名', '现代', '论述平凡的价值和意义', '通过具体事例阐述人生哲理', '雷锋是一名普通士兵，在平凡岗位上做出了不平凡的业绩；时传祥是一名普通掏粪工，用一人脏换来万家净；张秉贵是一名普通售货员，用\"一团火\"精神温暖了无数顾客。他们的工作岗位都很平凡，但他们的人格都很伟大。平凡不是平庸，而是在平凡中追求卓越；平凡不是无为，而是在平凡中创造价值。只要我们用心去做，任何平凡的工作都能做出不平凡的成就。', '[\"平凡\",\"价值\",\"奉献\"]', '文章论述了平凡的价值和意义，强调了在平凡岗位上创造不平凡业绩的可能性。', '这篇议论文视角独特，富有哲理，对帮助学生树立正确的价值观有重要教育意义。', '/audio/pingfan.mp3', '/image/yilun30.jpg', 245, b'1', 'admin', '2025-10-24 13:39:15.061', 'admin', '2025-10-24 13:39:15.061');
INSERT INTO `poetry_learning_content` VALUES (230, '《春晓》赏析', '孟浩然诗歌鉴赏', 3, 1, 3, 11, 1, '春眠不觉晓，处处闻啼鸟。夜来风雨声，花落知多少。', '孟浩然', '唐代', '孟浩然隐居鹿门山时所作，描绘春天早晨的景象', '通过简练语言表现对春天的热爱和怜惜', '诗歌语言平易浅近，自然天成，一点也看不出人工雕琢的痕迹。', '[\"春眠\",\"啼鸟\",\"花落\"]', '这首小诗仅仅二十个字，却写得清新自然、意味深长，把诗人对春天的热爱和怜惜之情表达得淋漓尽致。', '《春晓》是孟浩然的代表作之一，以其清新自然的风格和深刻的生活感悟，成为唐诗中流传最广的作品之一。', '/audio/chunxiao_shangxi.mp3', '/image/shige1.jpg', 345, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (231, '《静夜思》赏析', '李白诗歌鉴赏', 3, 1, 3, 11, 1, '床前明月光，疑是地上霜。举头望明月，低头思故乡。', '李白', '唐代', '李白旅居他乡时在一个月夜所作，表达思乡之情', '通过明月意象抒发游子思乡之情', '诗歌运用对比手法，通过\"举头\"和\"低头\"的动作描写，生动表现了诗人的思乡之情。', '[\"明月光\",\"地上霜\",\"思故乡\"]', '这首诗以明白如话的语言描绘了月夜思乡的图景，抒发了深切的思乡之情，引起了千古游子的共鸣。', '《静夜思》是李白最著名的诗作之一，以其质朴的语言和真挚的情感，成为中国人思乡的经典表达。', '/audio/jingyesi_shangxi.mp3', '/image/shige2.jpg', 389, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (232, '《悯农》赏析', '李绅诗歌鉴赏', 3, 1, 3, 11, 1, '锄禾日当午，汗滴禾下土。谁知盘中餐，粒粒皆辛苦。', '李绅', '唐代', '李绅目睹农民劳作艰辛而作，表达对农民的同情', '通过具体场景展现劳动人民的辛苦', '诗歌以白描手法真实地再现了农民在烈日下劳作的艰辛场景。', '[\"锄禾\",\"汗滴\",\"辛苦\"]', '这首诗通过描绘农民劳作的艰辛，表达了诗人对劳动人民的深切同情，具有深刻的教育意义。', '《悯农》是唐代新乐府运动的代表作，以其深刻的社会内容和真挚的情感，成为千古传诵的名篇。', '/audio/minnong_shangxi.mp3', '/image/shige3.jpg', 312, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (233, '《登鹳雀楼》赏析', '王之涣诗歌鉴赏', 3, 1, 3, 11, 1, '白日依山尽，黄河入海流。欲穷千里目，更上一层楼。', '王之涣', '唐代', '诗人登高望远抒怀之作，表达积极进取的精神', '通过壮丽景色抒发人生感悟', '诗歌前两句写景壮阔，后两句寓理于景，表达了不断进取的人生态度。', '[\"白日\",\"黄河\",\"千里目\"]', '这首诗描绘了北国河山的壮丽景色，并通过登高望远的感受，表达了积极进取的人生哲理。', '《登鹳雀楼》是盛唐诗歌的代表作，以其雄浑的气势和深刻的哲理，成为激励人们奋发向上的经典诗篇。', '/audio/guanquelou_shangxi.mp3', '/image/shige4.jpg', 367, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (234, '《望庐山瀑布》赏析', '李白诗歌鉴赏', 3, 1, 3, 11, 2, '日照香炉生紫烟，遥看瀑布挂前川。飞流直下三千尺，疑是银河落九天。', '李白', '唐代', '李白游历庐山时被瀑布壮观景象所震撼而作', '运用夸张手法描绘自然景观的雄伟', '诗人运用大胆的想象和极度的夸张，将庐山瀑布描绘得神奇壮丽。', '[\"香炉\",\"瀑布\",\"银河\"]', '这首诗以浪漫主义的笔触，描绘了庐山瀑布的雄伟壮观，展现了诗人豪放飘逸的诗风。', '《望庐山瀑布》充分体现了李白诗歌的浪漫主义特色，是唐代山水诗中的杰作。', '/audio/pubu_shangxi.mp3', '/image/shige5.jpg', 334, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (235, '《相思》赏析', '王维诗歌鉴赏', 3, 1, 3, 11, 1, '红豆生南国，春来发几枝。愿君多采撷，此物最相思。', '王维', '唐代', '借咏物而寄相思的诗作，表达对友人的思念', '托物言情，含蓄隽永', '诗歌以红豆起兴，借物抒情，语言朴素而感情深沉。', '[\"红豆\",\"南国\",\"相思\"]', '这首诗借咏红豆表达相思之情，语言简练含蓄，情感真挚动人，是王维抒情诗的代表作。', '《相思》以其深婉含蓄的艺术风格和真挚动人的情感，成为唐诗中表达相思之情的名篇。', '/audio/xiangsi_shangxi.mp3', '/image/shige6.jpg', 298, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (236, '《游子吟》赏析', '孟郊诗歌鉴赏', 3, 1, 3, 11, 1, '慈母手中线，游子身上衣。临行密密缝，意恐迟迟归。谁言寸草心，报得三春晖。', '孟郊', '唐代', '孟郊居官溧阳时回忆母亲关爱而作', '通过细节描写歌颂母爱的伟大', '诗歌通过母亲为游子缝衣的细节，表现了深沉的母爱。', '[\"慈母\",\"游子\",\"三春晖\"]', '这首诗通过平凡的生活场景，歌颂了母爱的伟大无私，表达了子女对母亲的感恩之情。', '《游子吟》以其真挚的情感和深刻的哲理，成为歌颂母爱的千古绝唱。', '/audio/youziyin_shangxi.mp3', '/image/shige7.jpg', 356, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (237, '《鹿柴》赏析', '王维诗歌鉴赏', 3, 1, 3, 11, 1, '空山不见人，但闻人语响。返景入深林，复照青苔上。', '王维', '唐代', '王维隐居辋川时所作组诗之一', '以动衬静，营造幽深意境', '诗歌以声音反衬寂静，以光亮反衬幽暗，创造了独特的艺术境界。', '[\"空山\",\"人语\",\"青苔\"]', '这首诗以精炼的语言描绘了鹿柴附近的幽静景色，体现了王维诗歌\"诗中有画\"的艺术特色。', '《鹿柴》是王维山水田园诗的代表作，以其空灵幽静的意境和精湛的艺术手法闻名于世。', '/audio/luzhai_shangxi.mp3', '/image/shige8.jpg', 278, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (238, '《江雪》赏析', '柳宗元诗歌鉴赏', 3, 1, 3, 11, 2, '千山鸟飞绝，万径人踪灭。孤舟蓑笠翁，独钓寒江雪。', '柳宗元', '唐代', '柳宗元被贬永州时所作，抒发孤寂情怀', '以冰雪世界衬托孤高品格', '诗歌通过极端寂静的冰雪世界，衬托出渔翁孤高坚毅的形象。', '[\"千山\",\"孤舟\",\"寒江雪\"]', '这首诗描绘了一幅寒江独钓图，在极端寂静的环境中突出了渔翁的孤高形象，寄托了诗人的理想人格。', '《江雪》以其独特的艺术境界和深刻的思想内涵，成为唐诗中托物言志的典范之作。', '/audio/jiangxue_shangxi.mp3', '/image/shige9.jpg', 312, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (239, '《春夜喜雨》赏析', '杜甫诗歌鉴赏', 3, 1, 3, 11, 2, '好雨知时节，当春乃发生。随风潜入夜，润物细无声。', '杜甫', '唐代', '杜甫在成都草堂居住时所作', '运用拟人手法赞美春雨', '诗人将春雨人格化，赋予其人的情感和意志，生动表现了春雨的特点。', '[\"好雨\",\"发生\",\"润物\"]', '这首诗以喜悦的心情描绘了春夜降雨的景象，赞美了春雨滋润万物、无私奉献的品格。', '《春夜喜雨》体现了杜甫诗歌关心民生疾苦的特点，是唐代咏物诗中的精品。', '/audio/chunyexiyu_shangxi.mp3', '/image/shige10.jpg', 289, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (240, '《望岳》赏析', '杜甫诗歌鉴赏', 3, 2, 3, 11, 2, '岱宗夫如何？齐鲁青未了。造化钟神秀，阴阳割昏晓。荡胸生曾云，决眦入归鸟。会当凌绝顶，一览众山小。', '杜甫', '唐代', '杜甫青年时期漫游齐赵时所作', '通过描绘泰山抒发人生抱负', '诗歌以问答形式开篇，层层推进，最后以哲理句收束，结构严谨。', '[\"岱宗\",\"造化\",\"绝顶\"]', '这首诗通过描绘泰山的雄伟壮观，抒发了诗人勇攀高峰的豪情壮志和远大抱负。', '《望岳》是杜甫早期诗歌的代表作，以其雄浑的气势和豪迈的情怀，展现了盛唐气象。', '/audio/wangyue_shangxi.mp3', '/image/shige11.jpg', 323, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (241, '《黄鹤楼送孟浩然之广陵》赏析', '李白诗歌鉴赏', 3, 2, 3, 11, 2, '故人西辞黄鹤楼，烟花三月下扬州。孤帆远影碧空尽，唯见长江天际流。', '李白', '唐代', '李白在黄鹤楼送别好友孟浩然时所作', '借景抒情，意境开阔', '诗歌以绚丽春景为背景，通过孤帆远影的意象，抒发了深沉的离别之情。', '[\"黄鹤楼\",\"烟花\",\"孤帆\"]', '这首诗将离别之情融入壮丽的自然景色中，情景交融，意境开阔，是送别诗中的佳作。', '《黄鹤楼送孟浩然之广陵》以其诗情画意的描写和豪放飘逸的风格，成为李白送别诗的代表作。', '/audio/huanghelou_shangxi.mp3', '/image/shige12.jpg', 298, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (242, '《枫桥夜泊》赏析', '张继诗歌鉴赏', 3, 2, 3, 11, 2, '月落乌啼霜满天，江枫渔火对愁眠。姑苏城外寒山寺，夜半钟声到客船。', '张继', '唐代', '诗人夜泊枫桥时的所见所感', '以景写情，营造愁绪氛围', '诗歌通过月落、乌啼、霜天、江枫、渔火等意象，营造出浓郁的愁绪氛围。', '[\"月落\",\"江枫\",\"夜半钟声\"]', '这首诗精确描绘了江南深秋夜景，将旅人的愁思与景物描写完美结合，创造了独特的艺术境界。', '《枫桥夜泊》以其精湛的艺术手法和深刻的审美意境，成为唐诗中描写旅愁的名篇。', '/audio/fengqiaoyebo_shangxi.mp3', '/image/shige13.jpg', 334, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (243, '《钱塘湖春行》赏析', '白居易诗歌鉴赏', 3, 2, 3, 11, 2, '孤山寺北贾亭西，水面初平云脚低。几处早莺争暖树，谁家新燕啄春泥。乱花渐欲迷人眼，浅草才能没马蹄。最爱湖东行不足，绿杨阴里白沙堤。', '白居易', '唐代', '白居易任杭州刺史时所作', '通过细腻观察描绘早春美景', '诗人以游踪为线索，移步换景，生动展现了西湖早春的生机勃勃。', '[\"早莺\",\"新燕\",\"乱花\"]', '这首诗以细腻的笔触描绘了西湖早春的美景，表达了诗人对自然的热爱和游春的喜悦心情。', '《钱塘湖春行》体现了白居易诗歌通俗流畅、写景生动的艺术特色，是山水诗中的佳作。', '/audio/qiantanghu_shangxi.mp3', '/image/shige14.jpg', 278, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (244, '《夜雨寄北》赏析', '李商隐诗歌鉴赏', 3, 2, 3, 11, 2, '君问归期未有期，巴山夜雨涨秋池。何当共剪西窗烛，却话巴山夜雨时。', '李商隐', '唐代', '李商隐在巴蜀时寄给长安妻子的诗', '虚实结合，情感深婉', '诗歌将现实与想象交织，通过重复\"巴山夜雨\"营造回环往复的艺术效果。', '[\"归期\",\"夜雨\",\"西窗烛\"]', '这首诗以质朴的语言表达了诗人对妻子的深切思念，虚实相生，含蓄深婉，感人至深。', '《夜雨寄北》是李商隐爱情诗的代表作，以其真挚的情感和独特的艺术手法广为传诵。', '/audio/yeyujibei_shangxi.mp3', '/image/shige15.jpg', 312, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (245, '《泊秦淮》赏析', '杜牧诗歌鉴赏', 3, 2, 3, 11, 2, '烟笼寒水月笼沙，夜泊秦淮近酒家。商女不知亡国恨，隔江犹唱后庭花。', '杜牧', '唐代', '杜牧夜泊秦淮河时的感慨', '借古讽今，寓意深刻', '诗歌前两句写景凄迷，后两句借陈后主故事抒发对时局的忧虑。', '[\"烟笼\",\"商女\",\"后庭花\"]', '这首诗通过夜泊秦淮的见闻，借古讽今，表达了诗人对国事的深沉忧虑，具有深刻的历史洞察力。', '《泊秦淮》是杜牧咏史抒怀诗的代表作，以其精警的语言和深刻的思想内容著称。', '/audio/boqinhuai_shangxi.mp3', '/image/shige16.jpg', 289, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (246, '《清明》赏析', '杜牧诗歌鉴赏', 3, 2, 3, 11, 1, '清明时节雨纷纷，路上行人欲断魂。借问酒家何处有，牧童遥指杏花村。', '杜牧', '唐代', '描绘清明时节的景象和行人的心情', '情景交融，含蓄隽永', '诗歌以细雨纷飞为背景，通过行人问路的细节，含蓄地表现了清明特有的哀愁。', '[\"清明\",\"断魂\",\"杏花村\"]', '这首诗以白描手法描绘了清明时节的景象，情景交融，意境深远，是杜牧诗歌中的名篇。', '《清明》以其清新自然的语言和深远的意境，成为描写清明节的经典诗作。', '/audio/qingming_shangxi.mp3', '/image/shige17.jpg', 356, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (247, '《题西林壁》赏析', '苏轼诗歌鉴赏', 3, 2, 3, 11, 2, '横看成岭侧成峰，远近高低各不同。不识庐山真面目，只缘身在此山中。', '苏轼', '宋代', '苏轼游庐山时题写于西林寺壁', '借景说理，哲理深刻', '诗歌前两句写景，后两句说理，通过观山感悟人生哲理。', '[\"横看\",\"侧峰\",\"真面目\"]', '这首诗通过描绘庐山的不同面貌，阐明了\"当局者迷，旁观者清\"的深刻哲理。', '《题西林壁》是苏轼哲理诗的代表作，以其生动的形象和深刻的哲理影响深远。', '/audio/tixilinbi_shangxi.mp3', '/image/shige18.jpg', 323, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (248, '《游园不值》赏析', '叶绍翁诗歌鉴赏', 3, 2, 3, 11, 2, '应怜屐齿印苍苔，小扣柴扉久不开。春色满园关不住，一枝红杏出墙来。', '叶绍翁', '宋代', '诗人游园未遇主人但见春色', '以小见大，生机盎然', '诗歌通过\"红杏出墙\"的细节，表现了春天旺盛的生命力。', '[\"屐齿\",\"柴扉\",\"红杏\"]', '这首诗通过游园不值的经历，以红杏出墙的意象，表现了春天不可阻挡的生机，构思巧妙。', '《游园不值》以其新颖的构思和生动的意象，成为宋诗中描写春光的名篇。', '/audio/youyuanbuzhi_shangxi.mp3', '/image/shige19.jpg', 298, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (249, '《江南春》赏析', '杜牧诗歌鉴赏', 3, 2, 3, 11, 2, '千里莺啼绿映红，水村山郭酒旗风。南朝四百八十寺，多少楼台烟雨中。', '杜牧', '唐代', '描绘江南春日美景', '视野开阔，色彩明丽', '诗歌前两句写江南春日的明丽景色，后两句写烟雨迷蒙中的佛寺，形成鲜明对比。', '[\"莺啼\",\"酒旗\",\"烟雨\"]', '这首诗以广阔的视野描绘了江南春天的美景，既有明丽的色彩，又有朦胧的意境，展现了江南春色的多样性。', '《江南春》是杜牧写景诗的代表作，以其精湛的艺术手法展现了江南春色的独特魅力。', '/audio/jiangnanchun_shangxi.mp3', '/image/shige20.jpg', 278, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (250, '《春望》赏析', '杜甫诗歌鉴赏', 3, 3, 3, 11, 3, '国破山河在，城春草木深。感时花溅泪，恨别鸟惊心。烽火连三月，家书抵万金。白头搔更短，浑欲不胜簪。', '杜甫', '唐代', '安史之乱期间杜甫被困长安时所作', '忧国忧民，沉郁顿挫', '诗歌以乐景写哀情，通过春日美景反衬国破家亡的悲痛。', '[\"国破\",\"感时\",\"家书\"]', '这首诗通过春日长安的凄凉景象，抒发了诗人忧国忧民、念家悲己的沉痛感情。', '《春望》是杜甫沉郁顿挫诗风的代表作，以其深刻的社会内容和真挚的情感震撼人心。', '/audio/chunwang_shangxi.mp3', '/image/shige21.jpg', 345, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (251, '《登高》赏析', '杜甫诗歌鉴赏', 3, 3, 3, 11, 3, '风急天高猿啸哀，渚清沙白鸟飞回。无边落木萧萧下，不尽长江滚滚来。万里悲秋常作客，百年多病独登台。艰难苦恨繁霜鬓，潦倒新停浊酒杯。', '杜甫', '唐代', '杜甫晚年漂泊西南时期所作', '情景交融，格律精严', '诗歌前四句写景，后四句抒情，对仗工整，格律严谨。', '[\"风急\",\"落木\",\"百年\"]', '这首诗通过秋日登高所见所感，抒发了诗人长年漂泊、老病孤愁的复杂感情。', '《登高》被后人推为古今七律第一，充分展现了杜甫诗歌沉郁顿挫的艺术风格。', '/audio/denggao_shangxi.mp3', '/image/shige22.jpg', 367, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (252, '《锦瑟》赏析', '李商隐诗歌鉴赏', 3, 3, 3, 11, 3, '锦瑟无端五十弦，一弦一柱思华年。庄生晓梦迷蝴蝶，望帝春心托杜鹃。沧海月明珠有泪，蓝田日暖玉生烟。此情可待成追忆，只是当时已惘然。', '李商隐', '唐代', '李商隐晚年回顾平生、自伤身世之作', '意象朦胧，情感哀婉', '诗歌运用典故和象征手法，意象朦胧，情感哀婉，寓意深远。', '[\"锦瑟\",\"华年\",\"惘然\"]', '这首诗以锦瑟起兴，通过一系列典故和意象，抒发了诗人对人生和爱情的复杂感悟。', '《锦瑟》是李商隐无题诗的代表作，以其朦胧的意境和深刻的人生感悟著称。', '/audio/jinse_shangxi.mp3', '/image/shige23.jpg', 312, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (253, '《声声慢》赏析', '李清照诗歌鉴赏', 3, 3, 3, 11, 3, '寻寻觅觅，冷冷清清，凄凄惨惨戚戚。乍暖还寒时候，最难将息。三杯两盏淡酒，怎敌他、晚来风急？雁过也，正伤心，却是旧时相识。', '李清照', '宋代', '李清照晚年作品，反映国破家亡的悲惨境遇', '叠字运用，愁绪层层递进', '开篇七组叠字，由浅入深，层层递进地表现了词人孤寂愁苦的心境。', '[\"寻觅\",\"冷清\",\"凄惨\"]', '这首词通过描写残秋所见所感，抒发了词人孤寂落寞、悲凉愁苦的心绪，是李清照晚年词作的代表。', '《声声慢》以其精湛的艺术手法和深刻的情感体验，成为宋词中表达愁情的巅峰之作。', '/audio/shengshengman_shangxi.mp3', '/image/shige24.jpg', 334, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (254, '《水调歌头》赏析', '苏轼诗歌鉴赏', 3, 3, 3, 11, 3, '明月几时有？把酒问青天。不知天上宫阙，今夕是何年。我欲乘风归去，又恐琼楼玉宇，高处不胜寒。起舞弄清影，何似在人间。', '苏轼', '宋代', '写于中秋之夜，表达对弟弟苏辙的思念之情', '豪放飘逸，哲理深刻', '词人把酒问月，既有浪漫的想象，又有对人生的理性思考。', '[\"明月\",\"琼楼\",\"清影\"]', '这首词通过对明月的追问和对人生的思考，表现了词人豁达的胸襟和深刻的人生感悟。', '《水调歌头》是苏轼豪放词的代表作，以其高超的艺术成就和深刻的思想内容流传千古。', '/audio/shuidiaogetou_shangxi.mp3', '/image/shige25.jpg', 389, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (255, '《念奴娇·赤壁怀古》赏析', '苏轼诗歌鉴赏', 3, 3, 3, 11, 3, '大江东去，浪淘尽，千古风流人物。故垒西边，人道是，三国周郎赤壁。乱石穿空，惊涛拍岸，卷起千堆雪。江山如画，一时多少豪杰。', '苏轼', '宋代', '苏轼因乌台诗案被贬黄州时所作', '怀古抒怀，气势磅礴', '词人通过对赤壁景色的描绘和对历史人物的追忆，抒发了自己的人生感慨。', '[\"大江\",\"赤壁\",\"豪杰\"]', '这首词以雄浑的笔触描绘了赤壁的壮丽景色，通过对周瑜的追慕，抒发了词人壮志未酬的感慨。', '《念奴娇·赤壁怀古》是苏轼豪放词的代表作，以其恢宏的气势和深刻的历史感闻名于世。', '/audio/niannujiao_shangxi.mp3', '/image/shige26.jpg', 356, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (256, '《雨霖铃》赏析', '柳永诗歌鉴赏', 3, 3, 3, 11, 3, '寒蝉凄切，对长亭晚，骤雨初歇。都门帐饮无绪，留恋处，兰舟催发。执手相看泪眼，竟无语凝噎。念去去，千里烟波，暮霭沉沉楚天阔。', '柳永', '宋代', '写离京南下时与恋人的惜别之情', '情景交融，婉约缠绵', '词人将离别之情与秋景描写相结合，创造了凄清感人的艺术境界。', '[\"寒蝉\",\"长亭\",\"凝噎\"]', '这首词通过对离别场景的细腻描写，抒发了词人与恋人分别时的痛苦心情，是柳永婉约词的代表作。', '《雨霖铃》以其真挚的情感和精湛的艺术手法，成为宋词中描写离情别绪的典范之作。', '/audio/yulinling_shangxi.mp3', '/image/shige27.jpg', 323, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (257, '《钗头凤》赏析', '陆游诗歌鉴赏', 3, 3, 3, 11, 3, '红酥手，黄縢酒，满城春色宫墙柳。东风恶，欢情薄。一怀愁绪，几年离索。错、错、错。', '陆游', '宋代', '陆游与前妻唐婉在沈园重逢后所作', '情感真挚，悔恨交加', '词人通过往昔美好与今日痛苦的对比，抒发了对前妻的思念和悔恨之情。', '[\"红酥手\",\"黄縢酒\",\"离索\"]', '这首词表达了词人对前妻唐婉的深切思念和无法相守的痛苦，情感真挚动人。', '《钗头凤》是陆游爱情词的代表作，以其真挚的情感和深刻的人生体验感动了无数读者。', '/audio/chaitoufeng_shangxi.mp3', '/image/shige28.jpg', 298, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (258, '《永遇乐·京口北固亭怀古》赏析', '辛弃疾诗歌鉴赏', 3, 3, 3, 11, 3, '千古江山，英雄无觅，孙仲谋处。舞榭歌台，风流总被，雨打风吹去。斜阳草树，寻常巷陌，人道寄奴曾住。想当年，金戈铁马，气吞万里如虎。', '辛弃疾', '宋代', '辛弃疾在镇江知府任上登北固亭时所作', '借古讽今，慷慨悲壮', '词人通过对历史人物的追忆，抒发了对南宋朝廷的不满和自己壮志未酬的悲愤。', '[\"江山\",\"英雄\",\"金戈铁马\"]', '这首词通过对历史兴亡的感慨，表达了词人强烈的爱国情怀和报国无门的悲愤。', '《永遇乐》是辛弃疾豪放词的代表作，以其深沉的历史感和慷慨悲壮的情怀著称。', '/audio/yongyule_shangxi.mp3', '/image/shige29.jpg', 312, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (259, '《青玉案·元夕》赏析', '辛弃疾诗歌鉴赏', 3, 3, 3, 11, 3, '东风夜放花千树。更吹落、星如雨。宝马雕车香满路。凤箫声动，玉壶光转，一夜鱼龙舞。', '辛弃疾', '宋代', '描写元宵节的热闹场景和寻找意中人的心境', '对比手法，寄托理想', '词人极力渲染元宵节的热闹场面，以此反衬出意中人的孤高淡泊。', '[\"花千树\",\"星如雨\",\"鱼龙舞\"]', '这首词通过元宵佳节的热闹场面，反衬出一个孤高淡泊的形象，寄托了词人的理想追求。', '《青玉案·元夕》是辛弃疾词中的精品，以其绚丽的描写和深刻的寓意广为传诵。', '/audio/qingyuan_shangxi.mp3', '/image/shige30.jpg', 345, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (260, '《虞美人》赏析', '李煜诗歌鉴赏', 3, 3, 3, 11, 3, '春花秋月何时了？往事知多少。小楼昨夜又东风，故国不堪回首月明中。雕栏玉砌应犹在，只是朱颜改。问君能有几多愁？恰似一江春水向东流。', '李煜', '宋代', '李煜被俘后在汴京所作，表达亡国之痛', '对比手法，愁思如潮', '词人通过自然永恒与人生无常的对比，抒发了深沉的故国之思和亡国之痛。', '[\"春花秋月\",\"故国\",\"春水\"]', '这首词通过今昔交错对比，表现了一个亡国之君的无穷哀怨，情感真挚动人。', '《虞美人》是李煜词的代表作，以其真挚的情感和精湛的艺术手法，成为宋词中的绝唱。', '/audio/yumeiren_shangxi.mp3', '/image/shige31.jpg', 367, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (261, '《鹊桥仙》赏析', '秦观诗歌鉴赏', 3, 3, 3, 11, 3, '纤云弄巧，飞星传恨，银汉迢迢暗度。金风玉露一相逢，便胜却人间无数。柔情似水，佳期如梦，忍顾鹊桥归路。两情若是久长时，又岂在朝朝暮暮。', '秦观', '宋代', '借牛郎织女故事歌颂坚贞爱情', '立意新颖，哲理深刻', '词人一反传统七夕词的哀婉格调，提出了崭新的爱情观。', '[\"纤云\",\"金风玉露\",\"朝朝暮暮\"]', '这首词借牛郎织女的故事，歌颂了真挚坚贞的爱情，立意新颖，境界高远。', '《鹊桥仙》是秦观婉约词的代表作，以其深刻的哲理和优美的语言流传千古。', '/audio/queqiaoxian_shangxi.mp3', '/image/shige32.jpg', 334, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (262, '《满江红》赏析', '岳飞诗歌鉴赏', 3, 3, 3, 11, 3, '怒发冲冠，凭栏处、潇潇雨歇。抬望眼，仰天长啸，壮怀激烈。三十功名尘与土，八千里路云和月。莫等闲、白了少年头，空悲切。', '岳飞', '宋代', '表达抗金救国的坚定意志和必胜信念', '慷慨激昂，爱国情深', '词人以磅礴的气势表达了收复失地的坚强决心和爱国情怀。', '[\"怒发\",\"壮怀\",\"悲切\"]', '这首词表现了岳飞精忠报国的英雄之志，洋溢着爱国主义激情和浩然正气。', '《满江红》是岳飞词的代表作，以其慷慨激昂的情感和崇高的爱国精神激励着世代中国人。', '/audio/manjianghong_shangxi.mp3', '/image/shige33.jpg', 389, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (263, '《天净沙·秋思》赏析', '马致远诗歌鉴赏', 3, 2, 3, 11, 2, '枯藤老树昏鸦，小桥流水人家，古道西风瘦马。夕阳西下，断肠人在天涯。', '马致远', '元代', '抒发游子思乡之情', '意象叠加，情景交融', '诗人通过一系列秋天意象的叠加，营造出浓郁的思乡氛围。', '[\"枯藤\",\"小桥\",\"断肠人\"]', '这首小令以精炼的语言描绘了秋天萧瑟的景象，抒发了游子思乡的断肠之情。', '《天净沙·秋思》是元散曲中的杰作，以其精湛的艺术手法和深刻的情感体验著称。', '/audio/tianjingsha_shangxi.mp3', '/image/shige34.jpg', 356, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (264, '《己亥杂诗》赏析', '龚自珍诗歌鉴赏', 3, 3, 3, 11, 3, '九州生气恃风雷，万马齐喑究可哀。我劝天公重抖擞，不拘一格降人才。', '龚自珍', '清代', '表达对社会变革的渴望', '寓意深刻，呼唤变革', '诗人以风雷喻社会变革，表达了对人才辈出的热切期待。', '[\"九州\",\"风雷\",\"降人才\"]', '这首诗表达了诗人对当时社会死气沉沉的不满和对变革的热切期待，具有深刻的社会意义。', '《己亥杂诗》是龚自珍的代表作，以其深刻的社会洞察力和强烈的变革意识影响深远。', '/audio/jihaizashi_shangxi.mp3', '/image/shige35.jpg', 312, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (265, '《再别康桥》赏析', '徐志摩诗歌鉴赏', 3, 3, 3, 11, 3, '轻轻的我走了，正如我轻轻的来；我轻轻的招手，作别西天的云彩。那河畔的金柳，是夕阳中的新娘；波光里的艳影，在我的心头荡漾。', '徐志摩', '现代', '徐志摩第二次欧游归来时所作', '意境优美，情感细腻', '诗人以细腻的笔触描绘了康桥的景色，抒发了对康桥的深深眷恋。', '[\"轻轻\",\"金柳\",\"艳影\"]', '这首诗以优美的语言和和谐的韵律，表达了诗人对康桥的眷恋之情，是现代诗的经典之作。', '《再别康桥》是徐志摩的代表作，以其优美的意境和真挚的情感成为现代诗歌的典范。', '/audio/zaibiekangqiao_shangxi.mp3', '/image/shige36.jpg', 378, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (266, '《雨巷》赏析', '戴望舒诗歌鉴赏', 3, 3, 3, 11, 3, '撑着油纸伞，独自彷徨在悠长、悠长又寂寥的雨巷，我希望逢着一个丁香一样地结着愁怨的姑娘。', '戴望舒', '现代', '表达大革命失败后知识分子的迷茫', '象征手法，意境朦胧', '诗人通过雨巷、丁香姑娘等意象，营造出朦胧忧郁的意境。', '[\"油纸伞\",\"雨巷\",\"丁香\"]', '这首诗运用象征手法，通过雨巷中期待丁香姑娘的意象，表现了诗人的孤独和期待。', '《雨巷》是戴望舒的代表作，以其优美的意象和朦胧的意境被称为现代诗的里程碑。', '/audio/yuxiang_shangxi.mp3', '/image/shige37.jpg', 345, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (267, '《乡愁》赏析', '余光中诗歌鉴赏', 3, 2, 3, 11, 2, '小时候，乡愁是一枚小小的邮票，我在这头，母亲在那头。长大后，乡愁是一张窄窄的船票，我在这头，新娘在那头。', '余光中', '现代', '表达对故乡和亲人的思念', '比喻新颖，情感真挚', '诗人通过邮票、船票、坟墓、海峡等比喻，将抽象的乡愁具体化。', '[\"乡愁\",\"邮票\",\"船票\"]', '这首诗通过新颖的比喻和递进的结构，抒发了诗人对故乡和亲人的深切思念。', '《乡愁》是余光中的代表作，以其真挚的情感和独特的艺术手法成为现代诗歌的名篇。', '/audio/xiangchou_shangxi.mp3', '/image/shige38.jpg', 412, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (268, '《致橡树》赏析', '舒婷诗歌鉴赏', 3, 3, 3, 11, 3, '我如果爱你——绝不像攀援的凌霄花，借你的高枝炫耀自己；我如果爱你——绝不学痴情的鸟儿，为绿荫重复单调的歌曲；', '舒婷', '现代', '表达独立平等的爱情观', '意象鲜明，思想深刻', '诗人通过凌霄花、鸟儿、泉源等意象，批判了依附式的爱情，倡导独立平等。', '[\"凌霄花\",\"鸟儿\",\"橡树\"]', '这首诗通过鲜明的意象和对比手法，表达了现代女性追求独立平等爱情的观念。', '《致橡树》是舒婷的代表作，以其深刻的思想和独特的艺术表现成为新时期诗歌的经典。', '/audio/zhixiangshu_shangxi.mp3', '/image/shige39.jpg', 367, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (269, '《面朝大海，春暖花开》赏析', '海子诗歌鉴赏', 3, 3, 3, 11, 3, '从明天起，做一个幸福的人喂马，劈柴，周游世界从明天起，关心粮食和蔬菜我有一所房子，面朝大海，春暖花开', '海子', '现代', '表达对简单幸福生活的向往', '语言质朴，意境深远', '诗人以质朴的语言描绘了理想中的幸福生活，但字里行间透露着深沉的忧伤。', '[\"幸福\",\"大海\",\"春暖花开\"]', '这首诗以明朗的语言表达了诗人对幸福生活的向往，但背后隐藏着深刻的孤独和绝望。', '《面朝大海，春暖花开》是海子的代表作，以其质朴的语言和复杂的情感内涵广为传诵。', '/audio/mianchaodahai_shangxi.mp3', '/image/shige40.jpg', 445, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (270, '《沁园春·雪》赏析', '毛泽东诗歌鉴赏', 3, 3, 3, 11, 3, '北国风光，千里冰封，万里雪飘。望长城内外，惟余莽莽；大河上下，顿失滔滔。山舞银蛇，原驰蜡象，欲与天公试比高。须晴日，看红装素裹，分外妖娆。', '毛泽东', '现代', '1936年毛泽东在陕北所作', '气势磅礴，立意高远', '诗人以雄浑的笔触描绘了北国雪景，抒发了改造中国的豪情壮志。', '[\"北国\",\"雪飘\",\"妖娆\"]', '这首词以壮丽的北国雪景为背景，评说历史人物，表达了诗人改造中国的伟大抱负。', '《沁园春·雪》是毛泽东词的代表作，以其恢宏的气势和深刻的思想内容闻名于世。', '/audio/qinyuanchun_shangxi.mp3', '/image/shige41.jpg', 423, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (271, '《七律·长征》赏析', '毛泽东诗歌鉴赏', 3, 3, 3, 11, 3, '红军不怕远征难，万水千山只等闲。五岭逶迤腾细浪，乌蒙磅礴走泥丸。金沙水拍云崖暖，大渡桥横铁索寒。更喜岷山千里雪，三军过后尽开颜。', '毛泽东', '现代', '1935年长征胜利后所作', '革命乐观主义精神', '诗人以豪迈的笔调歌颂了红军长征的伟大壮举，表现了革命乐观主义精神。', '[\"远征\",\"万水千山\",\"三军\"]', '这首诗以磅礴的气势描绘了长征的艰难历程，歌颂了红军战士的英雄气概和革命精神。', '《七律·长征》是毛泽东诗歌的代表作，以其豪迈的风格和革命乐观主义精神激励着人们。', '/audio/changzheng_shangxi.mp3', '/image/shige42.jpg', 389, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (272, '《天上的街市》赏析', '郭沫若诗歌鉴赏', 3, 2, 3, 11, 2, '远远的街灯明了，好像闪着无数的明星。天上的明星现了，好像点着无数的街灯。我想那缥缈的空中，定然有美丽的街市。', '郭沫若', '现代', '表达对美好生活的向往', '想象丰富，意境优美', '诗人通过街灯和明星的联想，创造了一个美丽的天上街市，表达了对理想世界的向往。', '[\"街灯\",\"明星\",\"街市\"]', '这首诗以丰富的想象描绘了天上的街市，表达了诗人对美好生活的向往和追求。', '《天上的街市》是郭沫若早期诗歌的代表作，以其丰富的想象和优美的意境广为传诵。', '/audio/tianshang_shangxi.mp3', '/image/shige43.jpg', 356, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (273, '《我爱这土地》赏析', '艾青诗歌鉴赏', 3, 3, 3, 11, 3, '假如我是一只鸟，我也应该用嘶哑的喉咙歌唱：这被暴风雨所打击着的土地，这永远汹涌着我们的悲愤的河流，这无止息地吹刮着的激怒的风，和那来自林间的无比温柔的黎明……', '艾青', '现代', '抗日战争时期表达爱国情怀', '意象鲜明，情感深沉', '诗人以鸟自喻，通过一系列意象表达了对祖国深沉的爱。', '[\"鸟\",\"土地\",\"黎明\"]', '这首诗以深沉的情感表达了诗人对祖国的热爱，即使在最黑暗的时刻也不放弃希望。', '《我爱这土地》是艾青的代表作，以其真挚的爱国情怀和独特的艺术表现成为现代诗歌的经典。', '/audio/woaizhetudi_shangxi.mp3', '/image/shige44.jpg', 378, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (274, '《断章》赏析', '卞之琳诗歌鉴赏', 3, 3, 3, 11, 3, '你站在桥上看风景，看风景人在楼上看你。明月装饰了你的窗子，你装饰了别人的梦。', '卞之琳', '现代', '表达相对主义的哲学思考', '意境深远，哲理深刻', '诗歌通过看与被看的关系，揭示了事物相对性的哲理。', '[\"风景\",\"明月\",\"梦\"]', '这首小诗通过简洁的语言和深远的意境，表达了深刻的相对主义哲学思想。', '《断章》是卞之琳的代表作，以其精炼的语言和深刻的哲理成为现代诗歌的名篇。', '/audio/duanzhang_shangxi.mp3', '/image/shige45.jpg', 334, b'1', 'admin', '2025-10-24 13:48:43.191', 'admin', '2025-10-24 13:48:43.191');
INSERT INTO `poetry_learning_content` VALUES (275, '\"所\"字解析', '文言虚词进阶', 3, 3, 2, 6, 3, '所在文言文中用法：1、处所 2、助词 3、约数', NULL, NULL, '文言文虚词深度教学', '所可以作名词、助词、数词等', '1、得其船，便扶向路，处处志之 2、衣食所安，弗敢专也 3、从弟子女十人所', '[\"处所\",\"助词\",\"约数\"]', '所的含义：1、地方处所 2、构成所字结构 3、表示大约的数量', '所字的助词用法比较复杂，需要重点掌握所字结构的语法功能。', '/audio/suo.mp3', '/image/xuci6.jpg', 156, b'1', 'admin', '2025-10-24 14:02:26.767', 'admin', '2025-10-24 14:02:26.767');
INSERT INTO `poetry_learning_content` VALUES (276, '\"为\"字解析', '多音虚词学习', 3, 3, 2, 6, 3, '为在文言文中读音和含义：1、做 2、为了 3、被', NULL, NULL, '文言文多音虚词教学', '为根据读音和语境有不同含义', '1、为坛而盟 2、为宫室之美 3、为天下笑', '[\"做\",\"为了\",\"被\"]', '为的含义：读wéi时指做、作为，读wèi时指为了、替', '为字的被动用法和介词用法需要特别注意，是文言文的重要语法现象。', '/audio/wei.mp3', '/image/xuci7.jpg', 167, b'1', 'admin', '2025-10-24 14:02:26.767', 'admin', '2025-10-24 14:02:26.767');
INSERT INTO `poetry_learning_content` VALUES (277, '\"焉\"字解析', '复合虚词学习', 3, 3, 2, 6, 3, '焉在文言文中用法：1、代词 2、语气词 3、兼词', NULL, NULL, '文言文复合虚词教学', '焉功能多样，可作代词、语气词等', '1、且焉置土石 2、寒暑易节，始一反焉 3、积土成山，风雨兴焉', '[\"代词\",\"语气词\",\"兼词\"]', '焉的含义：1、哪里怎么 2、语气词 3、于此在这里', '焉字的兼词用法比较特殊，需要理解其语法功能。', '/audio/yan.mp3', '/image/xuci8.jpg', 145, b'1', 'admin', '2025-10-24 14:02:26.767', 'admin', '2025-10-24 14:02:26.767');
INSERT INTO `poetry_learning_content` VALUES (278, '\"也\"字解析', '语气虚词学习', 3, 2, 2, 6, 2, '也在文言文中主要用作语气助词', NULL, NULL, '文言文语气词教学', '也用于句末表判断、陈述等语气', '1、陈胜者，阳城人也 2、小信未孚，神弗福也 3、不足为外人道也', '[\"判断\",\"陈述\",\"祈使\"]', '也的用法：主要用于句末，表示判断、陈述、祈使等语气', '也字是文言文中最常见的语气词，需要熟悉其各种用法。', '/audio/ye.mp3', '/image/xuci9.jpg', 178, b'1', 'admin', '2025-10-24 14:02:26.767', 'admin', '2025-10-24 14:02:26.767');
INSERT INTO `poetry_learning_content` VALUES (279, '\"以\"字深度解析', '介词连词学习', 3, 3, 2, 6, 3, '以在文言文中用法复杂：1、介词 2、连词 3、动词', NULL, NULL, '文言文多功能词深度教学', '以可以表示工具、原因、目的等', '1、以刀劈狼首 2、以衾拥覆 3、以塞忠谏之路', '[\"工具\",\"方式\",\"目的\"]', '以的含义：1、用拿 2、因为 3、来用来', '以字的用法非常灵活，需要根据上下文判断具体含义。', '/audio/yi2.mp3', '/image/xuci10.jpg', 189, b'1', 'admin', '2025-10-24 14:02:26.767', 'admin', '2025-10-24 14:02:26.767');
INSERT INTO `poetry_learning_content` VALUES (280, '\"因\"字解析', '介词连词进阶', 3, 3, 2, 6, 3, '因在文言文中含义：1、依靠 2、于是 3、通过', NULL, NULL, '文言文介词连词教学', '因可以作介词、连词、动词等', '1、因势利导 2、因击沛公于坐 3、因宾客至蔺相如门谢罪', '[\"依靠\",\"于是\",\"通过\"]', '因的含义：1、依靠凭借 2、于是就 3、通过经由', '因字的连词用法比较特殊，需要重点掌握。', '/audio/yin.mp3', '/image/xuci11.jpg', 156, b'1', 'admin', '2025-10-24 14:02:26.767', 'admin', '2025-10-24 14:02:26.767');
INSERT INTO `poetry_learning_content` VALUES (281, '\"于\"字深度解析', '介词用法进阶', 3, 3, 2, 6, 3, '于在文言文中用法：1、在 2、到 3、比', NULL, NULL, '文言文介词深度教学', '于可以表示时间、地点、比较等', '1、战于长勺 2、青取之于蓝 3、苛政猛于虎', '[\"在\",\"从\",\"比\"]', '于的含义：1、在 2、从 3、比', '于字是文言文中最基本的介词之一，用法相对固定但重要。', '/audio/yu2.mp3', '/image/xuci12.jpg', 167, b'1', 'admin', '2025-10-24 14:02:26.767', 'admin', '2025-10-24 14:02:26.767');
INSERT INTO `poetry_learning_content` VALUES (282, '\"与\"字解析', '连词介词学习', 3, 3, 2, 6, 3, '与在文言文中读音和含义：1、和 2、给予 3、参与', NULL, NULL, '文言文多音词教学', '与根据读音和语境有不同含义', '1、吾与汝毕力平险 2、与斗卮酒 3、蹇叔之子与师', '[\"和\",\"给予\",\"参与\"]', '与的含义：读yǔ时指和、给，读yù时指参与', '与字的介词用法和连词用法需要仔细区分。', '/audio/yu3.mp3', '/image/xuci13.jpg', 145, b'1', 'admin', '2025-10-24 14:02:26.767', 'admin', '2025-10-24 14:02:26.767');
INSERT INTO `poetry_learning_content` VALUES (283, '\"则\"字解析', '连词副词学习', 3, 3, 2, 6, 3, '则在文言文中用法：1、就 2、那么 3、却', NULL, NULL, '文言文连词教学', '则主要用于连接分句，表示顺承、转折等', '1、学而不思则罔 2、为之，则难者亦易矣 3、至则无可用', '[\"就\",\"那么\",\"却\"]', '则的用法：1、表示顺承 2、表示假设 3、表示转折', '则字是文言文中重要的连词，需要掌握其各种连接关系。', '/audio/ze.mp3', '/image/xuci14.jpg', 156, b'1', 'admin', '2025-10-24 14:02:26.767', 'admin', '2025-10-24 14:02:26.767');
INSERT INTO `poetry_learning_content` VALUES (284, '\"者\"字解析', '助词代词学习', 3, 3, 2, 6, 3, '者在文言文中用法：1、的人 2、的原因 3、语气词', NULL, NULL, '文言文特殊助词教学', '者主要用于构成者字结构', '1、有好事者船载以入 2、吾妻之美我者，私我也 3、陈胜者，阳城人也', '[\"的人\",\"的原因\",\"语气词\"]', '者的用法：1、构成名词性短语 2、表示原因 3、表提顿语气', '者字的语法功能特殊，是构成文言文特殊句式的重要词汇。', '/audio/zhe.mp3', '/image/xuci15.jpg', 167, b'1', 'admin', '2025-10-24 14:02:26.767', 'admin', '2025-10-24 14:02:26.767');
INSERT INTO `poetry_learning_content` VALUES (285, '\"知\"字解析', '认知实词学习', 3, 3, 2, 7, 3, '知在文言文中含义：1、知道 2、智慧 3、交好', NULL, NULL, '文言文认知动词教学', '知可以表示认知、智慧、交往等', '1、人非生而知之者 2、孰为汝多知乎 3、绝宾客之知', '[\"知道\",\"智慧\",\"交好\"]', '知的含义：1、知道了解 2、智慧聪明 3、知己交情', '知字的智慧义项在现代汉语中已较少使用，需要注意。', '/audio/zhi.mp3', '/image/shici46.jpg', 178, b'1', 'admin', '2025-10-24 14:02:26.767', 'admin', '2025-10-24 14:02:26.767');
INSERT INTO `poetry_learning_content` VALUES (286, '\"致\"字解析', '动作实词进阶', 3, 3, 2, 7, 3, '致在文言文中含义：1、送达 2、招致 3、情趣', NULL, NULL, '文言文动作动词教学', '致可以表示送达、导致、表达等', '1、远方莫不致其珍 2、以致天下之士 3、闲情逸致', '[\"送达\",\"招致\",\"情趣\"]', '致的含义：1、送达表达 2、招来导致 3、情趣兴致', '致字的词义从具体动作发展为抽象概念，体现了词义演变。', '/audio/zhi2.mp3', '/image/shici47.jpg', 156, b'1', 'admin', '2025-10-24 14:02:26.767', 'admin', '2025-10-24 14:02:26.767');
INSERT INTO `poetry_learning_content` VALUES (287, '\"质\"字解析', '多义实词深度', 3, 3, 2, 7, 3, '质在文言文中含义：1、抵押 2、本质 3、质问', NULL, NULL, '文言文多义词深度解析', '质可以表示抵押、本质、询问等', '1、必以长安君为质 2、其质非不美也 3、援疑质理', '[\"抵押\",\"本质\",\"质问\"]', '质的含义：1、抵押人质 2、本质质地 3、询问质疑', '质字的多义性反映了古人从具体到抽象的思维发展。', '/audio/zhi3.mp3', '/image/shici48.jpg', 145, b'1', 'admin', '2025-10-24 14:02:26.767', 'admin', '2025-10-24 14:02:26.767');
INSERT INTO `poetry_learning_content` VALUES (288, '\"治\"字解析', '政治实词学习', 3, 3, 2, 7, 3, '治在文言文中含义：1、治理 2、太平 3、医治', NULL, NULL, '文言文政治词汇教学', '治可以表示治理、太平、治疗等', '1、治国无法则乱 2、长治久安 3、不治将恐深', '[\"治理\",\"太平\",\"医治\"]', '治的含义：1、管理治理 2、太平有序 3、治疗研究', '治字体现了古代政治文化和医学观念。', '/audio/zhi4.mp3', '/image/shici49.jpg', 167, b'1', 'admin', '2025-10-24 14:02:26.767', 'admin', '2025-10-24 14:02:26.767');
INSERT INTO `poetry_learning_content` VALUES (289, '\"诸\"字解析', '特殊实词学习', 3, 3, 2, 7, 3, '诸在文言文中用法：1、众多 2、之于 3、之乎', NULL, NULL, '文言文特殊词汇教学', '诸可以作形容词、兼词等', '1、诸位先生 2、投诸渤海之尾 3、有诸', '[\"众多\",\"之于\",\"之乎\"]', '诸的用法：1、众多各位 2、之于的合音 3、之乎的合音', '诸字的兼词用法比较特殊，需要重点记忆。', '/audio/zhu.mp3', '/image/shici50.jpg', 134, b'1', 'admin', '2025-10-24 14:02:26.767', 'admin', '2025-10-24 14:02:26.767');
INSERT INTO `poetry_learning_content` VALUES (290, '\"贼\"字解析', '古今异义实词', 3, 3, 2, 7, 3, '贼在文言文中含义：1、伤害 2、强盗 3、叛臣', NULL, NULL, '文言文古今异义词教学', '贼的词义古今有较大变化', '1、贼仁者谓之贼 2、忍能对面为盗贼 3、操虽托名汉相，其实汉贼也', '[\"伤害\",\"强盗\",\"叛臣\"]', '贼的含义：1、伤害破坏 2、强盗 3、叛国者', '贼字的词义从动词发展为名词，感情色彩也有所变化。', '/audio/zei.mp3', '/image/shici51.jpg', 156, b'1', 'admin', '2025-10-24 14:02:26.767', 'admin', '2025-10-24 14:02:26.767');
INSERT INTO `poetry_learning_content` VALUES (291, '\"族\"字解析', '社会实词学习', 3, 3, 2, 7, 3, '族在文言文中含义：1、家族 2、灭族 3、种类', NULL, NULL, '文言文社会词汇教学', '族可以表示家族、灭族、种类等', '1、士大夫之族 2、族秦者秦也 3、万物百族', '[\"家族\",\"灭族\",\"种类\"]', '族的含义：1、家族氏族 2、灭族 3、种类品类', '族字反映了古代的宗法制度和社会结构。', '/audio/zu.mp3', '/image/shici52.jpg', 145, b'1', 'admin', '2025-10-24 14:02:26.767', 'admin', '2025-10-24 14:02:26.767');
INSERT INTO `poetry_learning_content` VALUES (292, '\"卒\"字解析', '多义实词综合', 3, 3, 2, 7, 3, '卒在文言文中含义：1、士兵 2、终于 3、死亡', NULL, NULL, '文言文多义词综合教学', '卒可以表示士兵、最终、死亡等', '1、项燕为楚将，数有功，爱士卒 2、卒廷见相如 3、年六十二，永和四年卒', '[\"士兵\",\"终于\",\"死亡\"]', '卒的含义：1、士兵 2、最终终于 3、死亡', '卒字的副词用法体现了实词的语法化过程。', '/audio/zu2.mp3', '/image/shici53.jpg', 167, b'1', 'admin', '2025-10-24 14:02:26.767', 'admin', '2025-10-24 14:02:26.767');
INSERT INTO `poetry_learning_content` VALUES (293, '\"走\"字解析', '古今异义深度', 3, 3, 2, 7, 3, '走在文言文中含义：1、跑 2、逃跑 3、趋向', NULL, NULL, '文言文古今异义词深度教学', '走的古义与现代义差别很大', '1、扁鹊望桓侯而还走 2、弃甲曳兵而走 3、骊山北构而西折，直走咸阳', '[\"跑\",\"逃跑\",\"趋向\"]', '走的含义：1、跑 2、逃跑 3、奔向趋向', '走字的古今义差别很大，需要特别注意。', '/audio/zou.mp3', '/image/shici54.jpg', 178, b'1', 'admin', '2025-10-24 14:02:26.767', 'admin', '2025-10-24 14:02:26.767');
INSERT INTO `poetry_learning_content` VALUES (294, '\"左\"字解析', '方位实词学习', 3, 2, 2, 7, 2, '左在文言文中含义：1、左边 2、降职 3、不正', NULL, NULL, '文言文方位词教学', '左可以表示方位、贬职、邪僻等', '1、左右流之 2、予左迁九江郡司马 3、旁门左道', '[\"左边\",\"降职\",\"不正\"]', '左的含义：1、左边 2、贬职 3、邪僻不正', '左字的贬职义项反映了古代的尊右观念。', '/audio/zuo.mp3', '/image/shici55.jpg', 156, b'1', 'admin', '2025-10-24 14:02:26.767', 'admin', '2025-10-24 14:02:26.767');
INSERT INTO `poetry_learning_content` VALUES (295, '唐诗发展概述', '文学史知识点', 3, 2, 1, 4, 2, '唐诗的发展经历了初唐、盛唐、中唐、晚唐四个时期。初唐诗歌承袭南朝诗风，逐渐摆脱齐梁浮艳习气；盛唐诗歌题材广阔，风格多样，出现了李白、杜甫等伟大诗人；中唐诗歌更加注重反映社会现实；晚唐诗歌则倾向于形式技巧的追求。', NULL, NULL, '唐代文学发展脉络', '系统介绍唐诗发展历程', '李白、杜甫、王维、孟浩然、白居易、李商隐、杜牧', '[\"初唐\",\"盛唐\",\"中唐\",\"晚唐\"]', '唐诗是中国古典诗歌的巅峰，在不同时期呈现出不同的艺术特色。', '了解唐诗的发展脉络有助于更好地理解和欣赏唐代诗歌作品。', '/audio/tangshifazhan.mp3', '/image/zhishi1.jpg', 234, b'1', 'admin', '2025-10-24 14:02:26.767', 'admin', '2025-10-24 14:02:26.767');
INSERT INTO `poetry_learning_content` VALUES (296, '比喻修辞', '修辞手法知识点', 3, 2, 3, 11, 2, '比喻是用相似的事物来打比方，包括明喻、暗喻、借喻三种类型。明喻用\"像、如、似\"等词，暗喻用\"是、成为\"等词，借喻直接借用喻体代替本体。', NULL, NULL, '比喻修辞手法讲解', '分析比喻的类型和作用', '问君能有几多愁？恰似一江春水向东流。（明喻）', '[\"明喻\",\"暗喻\",\"借喻\",\"喻体\",\"本体\"]', '比喻可以使抽象的事物具体化，深奥的道理浅显化，增强语言的形象性。', '比喻是文学作品中最常用的修辞手法，需要熟练掌握其运用。', '/audio/biyu.mp3', '/image/xiuci1.jpg', 289, b'1', 'admin', '2025-10-24 14:05:26.157', 'admin', '2025-10-24 14:05:26.157');
INSERT INTO `poetry_learning_content` VALUES (297, '对偶修辞', '修辞手法知识点', 3, 2, 3, 11, 2, '对偶是用字数相等、结构相似、意义相关的两个语句并列在一起。严格的对偶还要求平仄相对、词性相对。对偶可以使语言整齐匀称，增强节奏感。', NULL, NULL, '对偶修辞手法讲解', '分析对偶的特点和作用', '两个黄鹂鸣翠柳，一行白鹭上青天。（杜甫《绝句》）', '[\"字数相等\",\"结构相似\",\"意义相关\",\"平仄相对\"]', '对偶是汉语特有的修辞手法，体现了汉语的对称美和音乐美。', '对偶在诗词和对联中运用最为广泛，是重要的修辞手段。', '/audio/duiou.mp3', '/image/xiuci2.jpg', 256, b'1', 'admin', '2025-10-24 14:05:26.157', 'admin', '2025-10-24 14:05:26.157');
INSERT INTO `poetry_learning_content` VALUES (298, '夸张修辞', '修辞手法知识点', 3, 2, 3, 11, 2, '夸张是为了突出事物的特征，故意言过其实的修辞手法。分为扩大夸张、缩小夸张、超前夸张三种类型。夸张可以增强语言的感染力和表现力。', NULL, NULL, '夸张修辞手法讲解', '分析夸张的类型和作用', '飞流直下三千尺，疑是银河落九天。（李白《望庐山瀑布》）', '[\"扩大夸张\",\"缩小夸张\",\"超前夸张\",\"言过其实\"]', '夸张手法可以突出事物的本质特征，表达强烈的情感，创造奇特的意境。', '夸张要建立在真实的基础上，做到夸而有节，饰而不诬。', '/audio/kuazhang.mp3', '/image/xiuci3.jpg', 278, b'1', 'admin', '2025-10-24 14:05:26.157', 'admin', '2025-10-24 14:05:26.157');
INSERT INTO `poetry_learning_content` VALUES (299, '排比修辞', '修辞手法知识点', 3, 2, 3, 11, 2, '排比是把三个或三个以上结构相似、语气一致、意思相关的句子或词组排列在一起。排比可以增强语言气势，深化思想内容，加强表达效果。', NULL, NULL, '排比修辞手法讲解', '分析排比的特点和作用', '山朗润起来了，水涨起来了，太阳的脸红起来了。（朱自清《春》）', '[\"结构相似\",\"语气一致\",\"意思相关\",\"增强气势\"]', '排比可以使语言节奏鲜明，条理清晰，感情充沛，说理透彻。', '排比是增强语言表现力的重要手段，在演讲和散文中运用广泛。', '/audio/paibi.mp3', '/image/xiuci4.jpg', 245, b'1', 'admin', '2025-10-24 14:05:26.157', 'admin', '2025-10-24 14:05:26.157');
INSERT INTO `poetry_learning_content` VALUES (300, '借代修辞', '修辞手法知识点', 3, 3, 3, 11, 3, '借代是不直接说出要说的人或事物，而是借用与它密切相关的人或事物来代替。常见类型有：部分代整体，具体代抽象，特征代本体等。', NULL, NULL, '借代修辞手法讲解', '分析借代的类型和作用', '朱门酒肉臭，路有冻死骨。（杜甫《自京赴奉先县咏怀五百字》）', '[\"部分代整体\",\"具体代抽象\",\"特征代本体\",\"专名代泛称\"]', '借代可以突出事物的特征，使语言简练生动，引发读者联想。', '借代与借喻不同，借代重在相关性，借喻重在相似性。', '/audio/jiedai.mp3', '/image/xiuci5.jpg', 234, b'1', 'admin', '2025-10-24 14:05:26.157', 'admin', '2025-10-24 14:05:26.157');
INSERT INTO `poetry_learning_content` VALUES (301, '记叙文写作指导', '写作方法知识点', 3, 2, 3, 8, 2, '记叙文写作要交代清楚六要素，合理安排记叙顺序，注意详略得当。写人时要抓住特征，写事时要突出重点，写景时要情景交融。还要注意运用各种描写方法。', NULL, NULL, '记叙文写作方法指导', '讲解记叙文写作要点', '《背影》的细节描写，《藤野先生》的人物刻画', '[\"六要素\",\"记叙顺序\",\"详略得当\",\"描写方法\"]', '记叙文写作要注重真实性和感染力，通过具体事例表现主题。', '掌握记叙文写作方法可以提高叙事能力和文章感染力。', '/audio/jixuxiezuo.mp3', '/image/xiezuo1.jpg', 312, b'1', 'admin', '2025-10-24 14:05:26.157', 'admin', '2025-10-24 14:05:26.157');
INSERT INTO `poetry_learning_content` VALUES (302, '议论文写作指导', '写作方法知识点', 3, 3, 3, 10, 3, '议论文写作要论点明确，论据充分，论证严密。开头要提出中心论点，主体部分要合理安排论证结构，结尾要总结全文。论证时要善于运用各种论证方法。', NULL, NULL, '议论文写作方法指导', '讲解议论文写作要点', '《谈骨气》的论点确立，《怀疑与学问》的论证展开', '[\"论点明确\",\"论据充分\",\"论证严密\",\"论证方法\"]', '议论文写作要注重逻辑性和说服力，做到以理服人。', '掌握议论文写作方法可以培养逻辑思维和说理能力。', '/audio/yilunxiezuo.mp3', '/image/xiezuo2.jpg', 289, b'1', 'admin', '2025-10-24 14:05:26.157', 'admin', '2025-10-24 14:05:26.157');
INSERT INTO `poetry_learning_content` VALUES (303, '说明文写作指导', '写作方法知识点', 3, 2, 3, 9, 2, '说明文写作要抓住事物特征，合理安排说明顺序，恰当运用说明方法。语言要准确、简明、通俗。根据说明对象的不同，可以采用不同的说明方式。', NULL, NULL, '说明文写作方法指导', '讲解说明文写作要点', '《中国石拱桥》的特征说明，《看云识天气》的方法介绍', '[\"事物特征\",\"说明顺序\",\"说明方法\",\"语言准确\"]', '说明文写作要注重科学性和条理性，做到说明清楚、易懂。', '掌握说明文写作方法可以提高观察能力和表达能力。', '/audio/shuomingxiezuo.mp3', '/image/xiezuo3.jpg', 267, b'1', 'admin', '2025-10-24 14:05:26.157', 'admin', '2025-10-24 14:05:26.157');
INSERT INTO `poetry_learning_content` VALUES (304, '诗歌写作基础', '写作方法知识点', 3, 3, 3, 11, 3, '诗歌写作要注重意象的创造，情感的抒发，语言的凝练。要讲究节奏和韵律，善于运用各种修辞手法。现代诗可以更加自由，但仍需注重意境和内涵。', NULL, NULL, '诗歌创作方法指导', '讲解诗歌写作要点', '《再别康桥》的意境营造，《致橡树》的意象运用', '[\"意象创造\",\"情感抒发\",\"语言凝练\",\"节奏韵律\"]', '诗歌写作是情感的艺术表达，需要丰富的想象力和语言功力。', '诗歌创作可以抒发情感，陶冶性情，提高文学素养。', '/audio/shigexiezuo.mp3', '/image/xiezuo4.jpg', 245, b'1', 'admin', '2025-10-24 14:05:26.157', 'admin', '2025-10-24 14:05:26.157');
INSERT INTO `poetry_learning_content` VALUES (305, '作文修改方法', '写作方法知识点', 3, 2, 3, 8, 2, '作文修改要从内容、结构、语言三个方面入手。内容上要看主题是否明确，材料是否充实；结构上要看层次是否清晰，过渡是否自然；语言上要看表达是否准确，文字是否流畅。', NULL, NULL, '作文修改技巧指导', '讲解作文修改方法', '主题深化、材料增删、结构调整、语言润色', '[\"内容修改\",\"结构修改\",\"语言修改\",\"主题明确\"]', '好文章是改出来的，修改是写作的重要环节。', '掌握作文修改方法可以不断提高写作水平，写出更好的文章。', '/audio/zuowenxiugai.mp3', '/image/xiezuo5.jpg', 278, b'1', 'admin', '2025-10-24 14:05:26.157', 'admin', '2025-10-24 14:05:26.157');
INSERT INTO `poetry_learning_content` VALUES (306, '古诗文默写技巧', '学习方法知识点', 3, 2, 1, 4, 2, '古诗文默写要理解记忆，分类整理，反复巩固。可以先理解诗文内容和艺术特色，然后按照题材、作者、时代等分类记忆，最后通过默写检测掌握情况。', NULL, NULL, '古诗文记忆方法指导', '介绍古诗文默写技巧', '按题材分类：山水诗、边塞诗、咏物诗等；按作者分类：李白、杜甫、苏轼等', '[\"理解记忆\",\"分类整理\",\"反复巩固\",\"默写检测\"]', '科学的记忆方法可以提高古诗文学习的效率和效果。', '掌握古诗文默写技巧有助于积累文学素养，提高语文成绩。', '/audio/moxiejiqiao.mp3', '/image/zonghe1.jpg', 323, b'1', 'admin', '2025-10-24 14:05:26.157', 'admin', '2025-10-24 14:05:26.157');
INSERT INTO `poetry_learning_content` VALUES (307, '文言文阅读策略', '学习方法知识点', 3, 3, 2, 7, 3, '文言文阅读要注重积累常用实词、虚词和特殊句式。阅读时可以采取以下步骤：通读全文，把握大意；细读难点，理解词句；分析结构，理清思路；领会主旨，评价赏析。', NULL, NULL, '文言文阅读方法指导', '讲解文言文阅读策略', '《岳阳楼记》的阅读实践，《师说》的文言知识点梳理', '[\"词汇积累\",\"句式掌握\",\"文意理解\",\"主旨把握\"]', '系统的阅读策略可以有效提高文言文阅读能力和应试水平。', '文言文阅读需要长期积累和科学方法的结合。', '/audio/wenyanydcl.mp3', '/image/zonghe2.jpg', 289, b'1', 'admin', '2025-10-24 14:05:26.157', 'admin', '2025-10-24 14:05:26.157');
INSERT INTO `poetry_learning_content` VALUES (308, '文学鉴赏能力培养', '学习方法知识点', 3, 3, 3, 11, 3, '文学鉴赏能力的培养需要多读、多思、多写。要多读经典作品，提高审美感受力；要多思考作品内涵，培养批判思维能力；要多写读书笔记，提升表达能力。', NULL, NULL, '文学鉴赏方法指导', '介绍鉴赏能力培养途径', '经典作品细读，比较阅读，创作实践', '[\"多读经典\",\"多思考\",\"多写作\",\"审美感受\"]', '文学鉴赏能力是语文素养的重要组成部分，需要长期培养。', '提高文学鉴赏能力可以丰富精神世界，提升人文素养。', '/audio/wenxuejsnl.mp3', '/image/zonghe3.jpg', 267, b'1', 'admin', '2025-10-24 14:05:26.157', 'admin', '2025-10-24 14:05:26.157');
INSERT INTO `poetry_learning_content` VALUES (309, '语文学习方法总结', '学习方法知识点', 3, 2, 3, 8, 2, '语文学习要注重积累、思考、运用三个环节。积累包括字词、诗文、文化常识等；思考包括理解、分析、评价等；运用包括表达、写作、鉴赏等。三者要有机结合。', NULL, NULL, '语文学习方法指导', '总结语文学习有效方法', '每日阅读，每周练笔，每月总结', '[\"积累\",\"思考\",\"运用\",\"读写结合\"]', '科学的学习方法可以提高语文学习的效率和兴趣。', '掌握正确的学习方法可以让语文学习事半功倍。', '/audio/yuwenxxff.mp3', '/image/zonghe4.jpg', 312, b'1', 'admin', '2025-10-24 14:05:26.157', 'admin', '2025-10-24 14:05:26.157');

-- ----------------------------
-- Table structure for poetry_quiz_questions
-- ----------------------------
DROP TABLE IF EXISTS `poetry_quiz_questions`;
CREATE TABLE `poetry_quiz_questions`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '题目唯一ID',
  `content_id` bigint NOT NULL COMMENT '关联的学习内容ID（逻辑关联）',
  `question_type` tinyint UNSIGNED NOT NULL COMMENT '题型编码：single_choice 单选, multi_choice 多选, fill_blank 填空, order_sort 排序, true_false 判断',
  `question_stem` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '题目主干/问题正文',
  `question_data` json NULL COMMENT '题目动态数据（选项、可接受答案、排序项等）',
  `correct_answer` json NOT NULL COMMENT '标准答案',
  `explanation` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '题目解析',
  `difficulty` tinyint NULL DEFAULT 2 COMMENT '难度等级（1-5）',
  `hints` json NULL COMMENT '提示信息（JSON数组）',
  `is_enable` bit(1) NULL DEFAULT b'1' COMMENT '是否启用（0/1）',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_content_id`(`content_id` ASC) USING BTREE,
  INDEX `idx_type_difficulty`(`question_type` ASC, `difficulty` ASC) USING BTREE,
  INDEX `idx_enable_content`(`is_enable` ASC, `content_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 695 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '练习题题库表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of poetry_quiz_questions
-- ----------------------------
INSERT INTO `poetry_quiz_questions` VALUES (341, 2, 3, '大江东去，浪淘尽，千古风流人物。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"千古风流人物。\"]}', '{\"text\": \"千古风流人物。\"}', '这是《念奴娇·赤壁怀古》的原文，通过填空，加深对诗词的记忆。', 1, '[\"千古风流人物。\"]', b'1', 'SYSTEM', '2025-11-06 15:45:48.707', 'SYSTEM', '2025-11-06 15:45:48.707');
INSERT INTO `poetry_quiz_questions` VALUES (342, 2, 3, '大江浩浩荡荡向东流去，__________，千古风流人物。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"浪淘尽\"]}', '{\"text\": \"浪淘尽\"}', '通过填空，加深对诗词中关键信息的记忆。', 2, '[\"浪淘尽\"]', b'1', 'SYSTEM', '2025-11-06 15:45:48.726', 'SYSTEM', '2025-11-06 15:45:48.726');
INSERT INTO `poetry_quiz_questions` VALUES (343, 2, 5, '苏轼的《念奴娇·赤壁怀古》通过怀古抒发了对英雄人物的向往和自己壮志未酬的感慨。', '{}', '{\"text\": \"true\"}', '通过判断题，加深对诗词主题的理解。', 3, '[\"主题：怀古抒情\"]', b'1', 'SYSTEM', '2025-11-06 15:45:48.730', 'SYSTEM', '2025-11-06 15:45:48.730');
INSERT INTO `poetry_quiz_questions` VALUES (344, 2, 4, '请将以下句子按照原文顺序排序：大江浩浩荡荡向东流去，浪淘尽，千古风流人物。', '{\"sentenceList\": [\"大江浩浩荡荡向东流去\", \"浪淘尽\", \"千古风流人物\"]}', '{\"text\": \"大江浩浩荡荡向东流去，浪淘尽，千古风流人物。\"}', '通过排序题，加深对诗词结构的理解。', 4, '[\"顺序：大江浩浩荡荡向东流去，浪淘尽，千古风流人物。\"]', b'1', 'SYSTEM', '2025-11-06 15:45:48.733', 'SYSTEM', '2025-11-06 15:45:48.733');
INSERT INTO `poetry_quiz_questions` VALUES (345, 2, 1, '《念奴娇·赤壁怀古》的作者是？', '{}', '{\"text\": \"苏轼\"}', '通过单选题，加深对诗词作者的记忆。', 5, '[\"作者：苏轼\"]', b'1', 'SYSTEM', '2025-11-06 15:45:48.736', 'SYSTEM', '2025-11-06 15:45:48.736');
INSERT INTO `poetry_quiz_questions` VALUES (346, 3, 3, '寻寻觅觅，冷冷清清，凄凄惨惨戚戚。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"寻寻觅觅，冷冷清清，凄凄惨惨戚戚。\"]}', '{\"text\": \"寻寻觅觅，冷冷清清，凄凄惨惨戚戚。\"}', '这是《声声慢》的原文，通过叠字手法，层层递进地表达愁苦心情。', 1, '[\"叠字手法\", \"层层递进\"]', b'1', 'SYSTEM', '2025-11-06 15:45:53.874', 'SYSTEM', '2025-11-06 15:45:53.874');
INSERT INTO `poetry_quiz_questions` VALUES (347, 3, 3, '寻寻觅觅，冷冷清清，凄凄惨惨戚戚。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"寻寻觅觅，冷冷清清，凄凄惨惨戚戚。\"]}', '{\"text\": \"寻寻觅觅，冷冷清清，凄凄惨惨戚戚。\"}', '这是《声声慢》的原文，通过叠字手法，层层递进地表达愁苦心情。', 2, '[\"叠字手法\", \"层层递进\"]', b'1', 'SYSTEM', '2025-11-06 15:45:53.879', 'SYSTEM', '2025-11-06 15:45:53.879');
INSERT INTO `poetry_quiz_questions` VALUES (348, 3, 5, '《声声慢》的作者是李清照。', '{}', '{\"text\": \"true\"}', '《声声慢》的作者确实是宋代的李清照。', 3, '[\"宋代\", \"女词人\"]', b'1', 'SYSTEM', '2025-11-06 15:45:53.883', 'SYSTEM', '2025-11-06 15:45:53.883');
INSERT INTO `poetry_quiz_questions` VALUES (349, 3, 5, '《声声慢》的原文是\'寻寻觅觅，冷冷清清，凄凄惨惨戚戚。\'', '{}', '{\"text\": \"true\"}', '《声声慢》的原文确实是\'寻寻觅觅，冷冷清清，凄凄惨惨戚戚。\'。', 4, '[\"原文\", \"叠字手法\"]', b'1', 'SYSTEM', '2025-11-06 15:45:53.887', 'SYSTEM', '2025-11-06 15:45:53.887');
INSERT INTO `poetry_quiz_questions` VALUES (350, 3, 4, '请将以下词语按照《声声慢》的原文顺序排列：寻寻觅觅，冷冷清清，凄凄惨惨戚戚。', '{\"words\": [\"寻寻觅觅\", \"冷冷清清\", \"凄凄惨惨戚戚\"]}', '{\"text\": \"寻寻觅觅，冷冷清清，凄凄惨惨戚戚。\"}', '《声声慢》的原文是\'寻寻觅觅，冷冷清清，凄凄惨惨戚戚。\'，按照原文顺序排列。', 5, '[\"原文\", \"叠字手法\"]', b'1', 'SYSTEM', '2025-11-06 15:45:53.889', 'SYSTEM', '2025-11-06 15:45:53.889');
INSERT INTO `poetry_quiz_questions` VALUES (351, 4, 3, '寒蝉凄切，对长亭晚，骤雨初歇。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"寒蝉凄切，对长亭晚，骤雨初歇。\"]}', '{\"text\": \"寒蝉凄切，对长亭晚，骤雨初歇。\"}', '这是《雨霖铃》的首句，描述了离别时的场景。', 1, '[\"首句\"]', b'1', 'SYSTEM', '2025-11-06 15:45:58.428', 'SYSTEM', '2025-11-06 15:45:58.428');
INSERT INTO `poetry_quiz_questions` VALUES (352, 4, 3, '寒蝉凄切，对长亭晚，骤雨初歇。__________，__________。', '{\"blankCount\": 2, \"acceptableAnswers\": [\"寒蝉凄切，对长亭晚，骤雨初歇。秋风紧，离人泪。\"]}', '{\"text\": \"秋风紧，离人泪。\"}', '这是《雨霖铃》的下一句，描述了离别时的场景。', 2, '[\"下一句\"]', b'1', 'SYSTEM', '2025-11-06 15:45:58.431', 'SYSTEM', '2025-11-06 15:45:58.431');
INSERT INTO `poetry_quiz_questions` VALUES (353, 4, 5, '《雨霖铃》是柳永的代表作，属于婉约派。', '{}', '{\"text\": \"true\"}', '《雨霖铃》是柳永的代表作，属于婉约派。', 3, '[\"柳永，婉约派\"]', b'1', 'SYSTEM', '2025-11-06 15:45:58.434', 'SYSTEM', '2025-11-06 15:45:58.434');
INSERT INTO `poetry_quiz_questions` VALUES (354, 4, 4, '请将以下句子按照原文顺序排序：\n1. 寒蝉凄切，对长亭晚，骤雨初歇。\n2. 秋风紧，离人泪。\n3. 今宵酒醒何处？杨柳岸，晓风残月。', '{}', '{\"text\": \"1, 2, 3\"}', '这是《雨霖铃》的原文顺序。', 4, '[\"原文顺序\"]', b'1', 'SYSTEM', '2025-11-06 15:45:58.437', 'SYSTEM', '2025-11-06 15:45:58.437');
INSERT INTO `poetry_quiz_questions` VALUES (355, 4, 1, '《雨霖铃》的作者是？', '{}', '{\"text\": \"柳永\"}', '《雨霖铃》的作者是柳永。', 5, '[\"作者\"]', b'1', 'SYSTEM', '2025-11-06 15:45:58.440', 'SYSTEM', '2025-11-06 15:45:58.440');
INSERT INTO `poetry_quiz_questions` VALUES (356, 5, 3, '怒发冲冠，凭栏处、潇潇雨歇。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"我欲乘风归去。\"]}', '{\"text\": \"我欲乘风归去。\"}', '这是《水调歌头》中的一句，表达了作者对自由的向往。', 1, '[\"出自《水调歌头》\"]', b'1', 'SYSTEM', '2025-11-06 15:46:02.936', 'SYSTEM', '2025-11-06 15:46:02.936');
INSERT INTO `poetry_quiz_questions` VALUES (357, 5, 3, '怒发冲冠，凭栏处、潇潇雨歇。__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"抬望眼，仰天长啸，壮怀激烈。\"]}', '{\"text\": \"抬望眼，仰天长啸，壮怀激烈。\"}', '这是《满江红》中的一句，表达了作者的豪情壮志。', 2, '[\"出自《满江红》\"]', b'1', 'SYSTEM', '2025-11-06 15:46:02.939', 'SYSTEM', '2025-11-06 15:46:02.939');
INSERT INTO `poetry_quiz_questions` VALUES (358, 5, 5, '《满江红》的作者是岳飞。', '{}', '{\"text\": \"true\"}', '《满江红》的作者确实是岳飞。', 3, '[]', b'1', 'SYSTEM', '2025-11-06 15:46:02.942', 'SYSTEM', '2025-11-06 15:46:02.942');
INSERT INTO `poetry_quiz_questions` VALUES (359, 5, 4, '请将以下诗句按照作者的朝代顺序排列：\n1. 《静夜思》\n2. 《水调歌头》\n3. 《满江红》', '{}', '{\"text\": \"1, 2, 3\"}', '《静夜思》是唐代李白的作品，《水调歌头》是宋代苏轼的作品，《满江红》是宋代岳飞的作品。', 4, '[]', b'1', 'SYSTEM', '2025-11-06 15:46:02.944', 'SYSTEM', '2025-11-06 15:46:02.944');
INSERT INTO `poetry_quiz_questions` VALUES (360, 5, 1, '《满江红》的风格属于哪一种？\nA. 婉约派\nB. 豪放派\nC. 现代派', '{}', '{\"text\": \"B\"}', '《满江红》属于豪放派，具有气势磅礴、慷慨激昂的特点。', 5, '[\"豪放派\"]', b'1', 'SYSTEM', '2025-11-06 15:46:02.947', 'SYSTEM', '2025-11-06 15:46:02.947');
INSERT INTO `poetry_quiz_questions` VALUES (361, 6, 3, '东风夜放花千树。更吹落、星如雨。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"花千树。\"]}', '{\"text\": \"花千树。\"}', '这是《青玉案·元夕》的第二句，描述了元宵节的热闹景象。', 1, '[\"第二句\"]', b'1', 'SYSTEM', '2025-11-06 15:46:06.961', 'SYSTEM', '2025-11-06 15:46:06.961');
INSERT INTO `poetry_quiz_questions` VALUES (362, 6, 1, '《青玉案·元夕》的作者是？', '{}', '{\"text\": \"辛弃疾\"}', '辛弃疾是宋代著名的词人，这首词是他创作的。', 2, '[\"宋代词人\"]', b'1', 'SYSTEM', '2025-11-06 15:46:06.965', 'SYSTEM', '2025-11-06 15:46:06.965');
INSERT INTO `poetry_quiz_questions` VALUES (363, 6, 5, '这首词的背景是元宵节。', '{}', '{\"text\": \"true\"}', '词中多次提到元宵节的景象，如\'元夕\'、\'花千树\'等。', 3, '[\"背景\"]', b'1', 'SYSTEM', '2025-11-06 15:46:06.968', 'SYSTEM', '2025-11-06 15:46:06.968');
INSERT INTO `poetry_quiz_questions` VALUES (364, 6, 4, '请将以下句子按原文顺序排序：\n1. 更吹落、星如雨。\n2. 东风夜放花千树。', '{}', '{\"text\": \"2, 1\"}', '根据原文顺序，正确排序为：东风夜放花千树。更吹落、星如雨。', 4, '[\"原文顺序\"]', b'1', 'SYSTEM', '2025-11-06 15:46:06.970', 'SYSTEM', '2025-11-06 15:46:06.970');
INSERT INTO `poetry_quiz_questions` VALUES (365, 6, 2, '这首词表达了作者怎样的情感？\nA. 思乡\nB. 热爱元宵节\nC. 对理想追求的寄托\nD. 对自然美景的赞美', '{}', '{\"text\": \"C\"}', '词中描绘了元宵节的热闹景象，寄托了作者的理想追求。', 5, '[\"寄托理想追求\"]', b'1', 'SYSTEM', '2025-11-06 15:46:06.972', 'SYSTEM', '2025-11-06 15:46:06.972');
INSERT INTO `poetry_quiz_questions` VALUES (366, 7, 3, '庭院深深深几许，杨柳堆烟，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"帘幕无重数\"]}', '{\"text\": \"帘幕无重数\"}', '这是《蝶恋花》的原文，通过描写庭院的深邃和杨柳的烟雾，表达出一种婉约的意境。', 1, '[\"《蝶恋花》\", \"杨柳堆烟\"]', b'1', 'SYSTEM', '2025-11-06 15:46:11.923', 'SYSTEM', '2025-11-06 15:46:11.923');
INSERT INTO `poetry_quiz_questions` VALUES (367, 7, 3, '庭院深深深几许，杨柳堆烟，帘幕无重数。__________，小楼昨夜又东风。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"玉楼人醉映垂杨\"]}', '{\"text\": \"玉楼人醉映垂杨\"}', '这是《蝶恋花》的下阕，通过描写玉楼中的人醉映垂杨，表达出一种怀旧之情。', 2, '[\"《蝶恋花》下阕\", \"小楼昨夜又东风\"]', b'1', 'SYSTEM', '2025-11-06 15:46:11.926', 'SYSTEM', '2025-11-06 15:46:11.926');
INSERT INTO `poetry_quiz_questions` VALUES (368, 7, 5, '《蝶恋花》的作者是欧阳修。', '{}', '{\"text\": \"true\"}', '《蝶恋花》的作者确实是宋代的欧阳修。', 3, '[\"宋代\", \"婉约词风\"]', b'1', 'SYSTEM', '2025-11-06 15:46:11.930', 'SYSTEM', '2025-11-06 15:46:11.930');
INSERT INTO `poetry_quiz_questions` VALUES (369, 7, 4, '请将以下词语按照《蝶恋花》的词牌结构排序：庭院、深深、深几许、杨柳、堆烟、帘幕、无重数、玉楼、人醉、映垂杨。', '{}', '{\"order\": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]}', '《蝶恋花》的词牌结构为：上阕5句30字，下阕6句36字。根据词牌结构，词语应按顺序排列。', 4, '[\"词牌结构\", \"上阕5句30字\", \"下阕6句36字\"]', b'1', 'SYSTEM', '2025-11-06 15:46:11.933', 'SYSTEM', '2025-11-06 15:46:11.933');
INSERT INTO `poetry_quiz_questions` VALUES (370, 7, 5, '《蝶恋花》的词风是豪放派。', '{}', '{\"text\": \"false\"}', '《蝶恋花》的词风是婉约派，而非豪放派。', 5, '[\"婉约词风\", \"宋代\"]', b'1', 'SYSTEM', '2025-11-06 15:46:11.936', 'SYSTEM', '2025-11-06 15:46:11.936');
INSERT INTO `poetry_quiz_questions` VALUES (371, 8, 3, '春花秋月何时了？往事知多少。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"何时了\"]}', '{\"text\": \"何时了\"}', '这是《虞美人》的首句，通过\"何时了\"的疑问，表达对时光流逝的感慨。', 1, '[\"首句\"]', b'1', 'SYSTEM', '2025-11-06 15:46:16.150', 'SYSTEM', '2025-11-06 15:46:16.150');
INSERT INTO `poetry_quiz_questions` VALUES (372, 8, 3, '春花秋月何时了？往事知多少。小楼昨夜又东风，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"故国不堪回首月明中\"]}', '{\"text\": \"故国不堪回首月明中\"}', '这是《虞美人》的第二句，通过\"故国\"的提及，表达对故国的怀念。', 2, '[\"第二句\"]', b'1', 'SYSTEM', '2025-11-06 15:46:16.156', 'SYSTEM', '2025-11-06 15:46:16.156');
INSERT INTO `poetry_quiz_questions` VALUES (373, 8, 5, '《虞美人》的作者是李煜。', '{}', '{\"text\": \"true\"}', '《虞美人》的作者确实是李煜。', 3, '[\"作者\"]', b'1', 'SYSTEM', '2025-11-06 15:46:16.158', 'SYSTEM', '2025-11-06 15:46:16.158');
INSERT INTO `poetry_quiz_questions` VALUES (374, 8, 4, '请将以下诗句按照原文顺序排序：\n1. 春花秋月何时了？\n2. 何时了，往事知多少。\n3. 小楼昨夜又东风，故国不堪回首月明中。', '{}', '{\"text\": \"1, 2, 3\"}', '这是《虞美人》的原文顺序，通过排序来检验对诗句顺序的掌握。', 4, '[\"原文顺序\"]', b'1', 'SYSTEM', '2025-11-06 15:46:16.161', 'SYSTEM', '2025-11-06 15:46:16.161');
INSERT INTO `poetry_quiz_questions` VALUES (375, 8, 5, '《虞美人》的主旨是表达对自然永恒的感慨。', '{}', '{\"text\": \"false\"}', '《虞美人》的主旨是表达对人生无常的感慨，而非自然永恒。', 5, '[\"主旨\"]', b'1', 'SYSTEM', '2025-11-06 15:46:16.163', 'SYSTEM', '2025-11-06 15:46:16.163');
INSERT INTO `poetry_quiz_questions` VALUES (376, 10, 3, '客舍前的梅花已经凋残，溪桥旁新生细柳轻垂，暖风吹送着春草的芳香。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"草薰风暖摇征辔。\"]}', '{\"text\": \"草薰风暖摇征辔。\"}', '这是《踏莎行》的原文，通过描述春景来表达离情。', 1, '[\"春景描写\", \"离情表达\"]', b'1', 'SYSTEM', '2025-11-06 15:46:27.753', 'SYSTEM', '2025-11-06 15:46:27.753');
INSERT INTO `poetry_quiz_questions` VALUES (377, 10, 3, '候馆梅残，溪桥柳细。草薰风暖摇征辔。__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"客舍前的梅花已经凋残，溪桥旁新生细柳轻垂，暖风吹送着春草的芳香。\"]}', '{\"text\": \"客舍前的梅花已经凋残，溪桥旁新生细柳轻垂，暖风吹送着春草的芳香。\"}', '这是《踏莎行》的原文，通过描述春景来表达离情。', 2, '[\"春景描写\", \"离情表达\"]', b'1', 'SYSTEM', '2025-11-06 15:46:27.758', 'SYSTEM', '2025-11-06 15:46:27.758');
INSERT INTO `poetry_quiz_questions` VALUES (378, 10, 5, '《踏莎行》的作者是欧阳修。', '{}', '{\"text\": \"true\"}', '《踏莎行》的作者确实是欧阳修。', 3, '[\"作者信息\"]', b'1', 'SYSTEM', '2025-11-06 15:46:27.761', 'SYSTEM', '2025-11-06 15:46:27.761');
INSERT INTO `poetry_quiz_questions` VALUES (379, 10, 5, '《踏莎行》的朝代是唐代。', '{}', '{\"text\": \"false\"}', '《踏莎行》的朝代是宋代，不是唐代。', 4, '[\"朝代信息\"]', b'1', 'SYSTEM', '2025-11-06 15:46:27.764', 'SYSTEM', '2025-11-06 15:46:27.764');
INSERT INTO `poetry_quiz_questions` VALUES (380, 10, 4, '请将以下诗句按照原文顺序排列：\n1. 溪桥柳细\n2. 草薰风暖摇征辔\n3. 客舍前的梅花已经凋残\n4. 暖风吹送着春草的芳香。', '{}', '{\"text\": \"3124\"}', '这是《踏莎行》的原文，按照原文顺序排列。', 5, '[\"原文顺序\", \"诗句排列\"]', b'1', 'SYSTEM', '2025-11-06 15:46:27.766', 'SYSTEM', '2025-11-06 15:46:27.766');
INSERT INTO `poetry_quiz_questions` VALUES (381, 1, 3, '明月几时有？把酒问青天。不知天上宫阙，今夕是何年。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"不知天上宫阙，今夕是何年。\"]}', '{\"text\": \"不知天上宫阙，今夕是何年。\"}', '这是《水调歌头》的第二句，通过\"把酒问青天\"的提问，表达对宇宙和人生的思考。', 1, '[\"第二句\"]', b'1', 'SYSTEM', '2025-11-06 15:49:02.064', 'SYSTEM', '2025-11-06 15:49:02.064');
INSERT INTO `poetry_quiz_questions` VALUES (382, 1, 3, '明月几时有？把酒问青天。不知天上宫阙，今夕是何年。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"不知天上宫阙，今夕是何年。\"]}', '{\"text\": \"不知天上宫阙，今夕是何年。\"}', '这是《水调歌头》的第二句，通过\"把酒问青天\"的提问，表达对宇宙和人生的思考。', 1, '[\"第二句\"]', b'1', 'SYSTEM', '2025-11-06 15:49:02.081', 'SYSTEM', '2025-11-06 15:49:02.081');
INSERT INTO `poetry_quiz_questions` VALUES (383, 1, 3, '明月几时有？把酒问青天。不知天上宫阙，今夕是何年。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"不知天上宫阙，今夕是何年。\"]}', '{\"text\": \"不知天上宫阙，今夕是何年。\"}', '这是《水调歌头》的第二句，通过\"把酒问青天\"的提问，表达对宇宙和人生的思考。', 1, '[\"第二句\"]', b'1', 'SYSTEM', '2025-11-06 15:49:02.085', 'SYSTEM', '2025-11-06 15:49:02.085');
INSERT INTO `poetry_quiz_questions` VALUES (384, 1, 3, '明月几时有？把酒问青天。不知天上宫阙，今夕是何年。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"不知天上宫阙，今夕是何年。\"]}', '{\"text\": \"不知天上宫阙，今夕是何年。\"}', '这是《水调歌头》的第二句，通过\"把酒问青天\"的提问，表达对宇宙和人生的思考。', 1, '[\"第二句\"]', b'1', 'SYSTEM', '2025-11-06 15:49:02.088', 'SYSTEM', '2025-11-06 15:49:02.088');
INSERT INTO `poetry_quiz_questions` VALUES (385, 1, 3, '明月几时有？把酒问青天。不知天上宫阙，今夕是何年。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"不知天上宫阙，今夕是何年。\"]}', '{\"text\": \"不知天上宫阙，今夕是何年。\"}', '这是《水调歌头》的第二句，通过\"把酒问青天\"的提问，表达对宇宙和人生的思考。', 1, '[\"第二句\"]', b'1', 'SYSTEM', '2025-11-06 15:49:02.090', 'SYSTEM', '2025-11-06 15:49:02.090');
INSERT INTO `poetry_quiz_questions` VALUES (386, 2, 3, '大江东去，浪淘尽，千古风流人物。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"千古风流人物。\"]}', '{\"text\": \"千古风流人物。\"}', '这是《念奴娇·赤壁怀古》的原文，通过填空，加深对诗词的记忆。', 1, '[\"千古风流人物。\"]', b'1', 'SYSTEM', '2025-11-06 15:49:06.506', 'SYSTEM', '2025-11-06 15:49:06.506');
INSERT INTO `poetry_quiz_questions` VALUES (387, 2, 3, '大江浩浩荡荡向东流去，__________，千古风流人物。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"浪淘尽\"]}', '{\"text\": \"浪淘尽\"}', '通过填空，加深对诗词中关键信息的记忆。', 2, '[\"浪淘尽\"]', b'1', 'SYSTEM', '2025-11-06 15:49:06.510', 'SYSTEM', '2025-11-06 15:49:06.510');
INSERT INTO `poetry_quiz_questions` VALUES (388, 2, 5, '苏轼的《念奴娇·赤壁怀古》通过怀古抒发了对英雄人物的向往和自己壮志未酬的感慨。', '{}', '{\"text\": \"true\"}', '通过判断题，加深对诗词主题的理解。', 3, '[\"主题：怀古抒情\"]', b'1', 'SYSTEM', '2025-11-06 15:49:06.513', 'SYSTEM', '2025-11-06 15:49:06.513');
INSERT INTO `poetry_quiz_questions` VALUES (389, 2, 4, '请将以下句子按照原文顺序排序：大江浩浩荡荡向东流去，浪淘尽，千古风流人物。', '{\"sentenceList\": [\"大江浩浩荡荡向东流去\", \"浪淘尽\", \"千古风流人物\"]}', '{\"text\": \"大江浩浩荡荡向东流去，浪淘尽，千古风流人物。\"}', '通过排序题，加深对诗词结构的理解。', 4, '[\"顺序：大江浩浩荡荡向东流去，浪淘尽，千古风流人物。\"]', b'1', 'SYSTEM', '2025-11-06 15:49:06.516', 'SYSTEM', '2025-11-06 15:49:06.516');
INSERT INTO `poetry_quiz_questions` VALUES (390, 2, 1, '《念奴娇·赤壁怀古》的作者是？', '{}', '{\"text\": \"苏轼\"}', '通过单选题，加深对诗词作者的记忆。', 5, '[\"作者：苏轼\"]', b'1', 'SYSTEM', '2025-11-06 15:49:06.518', 'SYSTEM', '2025-11-06 15:49:06.518');
INSERT INTO `poetry_quiz_questions` VALUES (391, 11, 3, '千古江山，英雄无觅，孙仲谋处。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"处\"]}', '{\"text\": \"处\"}', '这是《永遇乐·京口北固亭怀古》的原文，根据上下文，此处应填入“处”字。', 1, '[\"原文中，此处应填入一个字\"]', b'1', 'SYSTEM', '2025-11-06 15:51:06.983', 'SYSTEM', '2025-11-06 15:51:06.983');
INSERT INTO `poetry_quiz_questions` VALUES (392, 11, 1, '《永遇乐·京口北固亭怀古》的作者是？', '{}', '{\"text\": \"辛弃疾\"}', '《永遇乐·京口北固亭怀古》的作者是辛弃疾。', 2, '[\"宋代词人\"]', b'1', 'SYSTEM', '2025-11-06 15:51:06.987', 'SYSTEM', '2025-11-06 15:51:06.987');
INSERT INTO `poetry_quiz_questions` VALUES (393, 11, 5, '《永遇乐·京口北固亭怀古》借古讽今，抒发了对南宋朝廷的不满和自己壮志未酬的悲愤。', '{}', '{\"text\": \"true\"}', '《永遇乐·京口北固亭怀古》确实借古讽今，表达了对南宋朝廷的不满和自己壮志未酬的悲愤。', 3, '[\"主旨\"]', b'1', 'SYSTEM', '2025-11-06 15:51:06.991', 'SYSTEM', '2025-11-06 15:51:06.991');
INSERT INTO `poetry_quiz_questions` VALUES (394, 11, 4, '请将以下诗句按照原文顺序排序：\n1. 千古江山，英雄无觅，孙仲谋处。\n2. 何处望神州？满眼风光北固楼。\n3. 佛狸祠下，一片神鸦社鼓。', '{}', '{\"text\": \"213\"}', '根据《永遇乐·京口北固亭怀古》的原文顺序，诗句应为：何处望神州？满眼风光北固楼。千古江山，英雄无觅，孙仲谋处。佛狸祠下，一片神鸦社鼓。', 4, '[\"原文顺序\"]', b'1', 'SYSTEM', '2025-11-06 15:51:06.994', 'SYSTEM', '2025-11-06 15:51:06.994');
INSERT INTO `poetry_quiz_questions` VALUES (395, 11, 2, '《永遇乐·京口北固亭怀古》中，以下哪些诗句体现了作者对历史人物的怀念？\nA. 千古江山，英雄无觅，孙仲谋处。\nB. 何处望神州？满眼风光北固楼。\nC. 佛狸祠下，一片神鸦社鼓。', '{}', '{\"text\": \"A\"}', '诗句A体现了作者对历史人物孙权的怀念，而诗句B和C则更多地表达了对历史的感慨和对现实的忧虑。', 5, '[\"怀念历史人物\"]', b'1', 'SYSTEM', '2025-11-06 15:51:06.996', 'SYSTEM', '2025-11-06 15:51:06.996');
INSERT INTO `poetry_quiz_questions` VALUES (396, 12, 3, '床前明月光，疑是地上霜。举头望明月，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"低头思故乡\", \"低头思故乡。\"]}', '{\"text\": \"低头思故乡\"}', '这是《静夜思》的最后一句，通过\"低头\"与\"举头\"的对比，表达思乡之情。', 1, '[\"首字：低\"]', b'1', 'SYSTEM', '2025-11-06 15:51:11.653', 'SYSTEM', '2025-11-06 15:51:11.653');
INSERT INTO `poetry_quiz_questions` VALUES (397, 12, 3, '红酥手，黄縢酒，满城春色宫墙柳。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"你却早已像宫墙中的绿柳那般遥不可及。\"]}', '{\"text\": \"你却早已像宫墙中的绿柳那般遥不可及。\"}', '这是《钗头凤》的最后两句，表达了对前妻的深切思念和无法相守的痛苦。', 2, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 15:51:11.658', 'SYSTEM', '2025-11-06 15:51:11.658');
INSERT INTO `poetry_quiz_questions` VALUES (398, 12, 5, '《钗头凤》的作者是陆游。', '{}', '{\"text\": \"true\"}', '《钗头凤》的作者确实是陆游。', 3, '[\"作者：陆游\"]', b'1', 'SYSTEM', '2025-11-06 15:51:11.662', 'SYSTEM', '2025-11-06 15:51:11.662');
INSERT INTO `poetry_quiz_questions` VALUES (399, 12, 4, '请将以下诗句按照原文顺序排序：\n1. 红酥手，黄縢酒，满城春色宫墙柳。\n2. 你却早已像宫墙中的绿柳那般遥不可及。\n3. 举头望明月，__________。', '{}', '{\"text\": \"1, 3, 2\"}', '根据原文顺序，正确的排序应该是1、3、2。', 4, '[\"原文顺序\"]', b'1', 'SYSTEM', '2025-11-06 15:51:11.664', 'SYSTEM', '2025-11-06 15:51:11.664');
INSERT INTO `poetry_quiz_questions` VALUES (400, 12, 1, '《钗头凤》表达了诗人怎样的情感？\nA. 思乡之情\nB. 对前妻的深切思念\nC. 对春天的赞美\nD. 对生活的热爱', '{}', '{\"text\": \"B\"}', '《钗头凤》表达了诗人对前妻的深切思念和无法相守的痛苦。', 5, '[\"情感：思念\"]', b'1', 'SYSTEM', '2025-11-06 15:51:11.667', 'SYSTEM', '2025-11-06 15:51:11.667');
INSERT INTO `poetry_quiz_questions` VALUES (401, 13, 3, '碧云天，黄叶地，秋色连波，波上寒烟翠。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"翠\"]}', '{\"text\": \"翠\"}', '根据原文，填空处应为\'翠\'，描述了水波上笼罩着的寒烟的颜色。', 1, '[\"颜色：翠\"]', b'1', 'SYSTEM', '2025-11-06 15:51:15.965', 'SYSTEM', '2025-11-06 15:51:15.965');
INSERT INTO `poetry_quiz_questions` VALUES (402, 13, 3, '秋色连波，波上寒烟翠。__________，长亭外，古道边。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"长亭外，古道边\"]}', '{\"text\": \"长亭外，古道边\"}', '根据下一句的结构，填空处应为\'长亭外，古道边\'，描述了秋天的景色。', 2, '[\"下一句：长亭外，古道边\"]', b'1', 'SYSTEM', '2025-11-06 15:51:15.968', 'SYSTEM', '2025-11-06 15:51:15.968');
INSERT INTO `poetry_quiz_questions` VALUES (403, 13, 5, '上片写景，下片抒情，情景交融。', '{}', '{\"text\": \"true\"}', '根据解析，上片写景，下片抒情，情景交融。', 3, '[\"上片：写景；下片：抒情\"]', b'1', 'SYSTEM', '2025-11-06 15:51:15.971', 'SYSTEM', '2025-11-06 15:51:15.971');
INSERT INTO `poetry_quiz_questions` VALUES (404, 13, 4, '请将以下词语按照原文顺序排列：碧云天，黄叶地，秋色连波，波上寒烟翠。', '{}', '{\"text\": \"碧云天，黄叶地，秋色连波，波上寒烟翠\"}', '根据原文顺序，词语应为\'碧云天，黄叶地，秋色连波，波上寒烟翠\'。', 4, '[\"原文顺序\"]', b'1', 'SYSTEM', '2025-11-06 15:51:15.973', 'SYSTEM', '2025-11-06 15:51:15.973');
INSERT INTO `poetry_quiz_questions` VALUES (405, 13, 1, '这首词的作者是？', '{}', '{\"text\": \"范仲淹\"}', '这首词的作者是范仲淹。', 5, '[\"作者：范仲淹\"]', b'1', 'SYSTEM', '2025-11-06 15:51:15.976', 'SYSTEM', '2025-11-06 15:51:15.976');
INSERT INTO `poetry_quiz_questions` VALUES (406, 14, 3, '老夫聊发少年狂，左牵黄，右擎苍。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"少年狂。\"]}', '{\"text\": \"少年狂。\"}', '这是《江城子·密州出猎》的第二句，通过\"左牵\"与\"右擎\"的对比，表达出猎的豪情。', 1, '[\"第二句\"]', b'1', 'SYSTEM', '2025-11-06 15:51:22.431', 'SYSTEM', '2025-11-06 15:51:22.431');
INSERT INTO `poetry_quiz_questions` VALUES (407, 14, 3, '老夫聊发少年狂，左牵黄，右擎苍。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"少年狂。\"]}', '{\"text\": \"少年狂。\"}', '这是《江城子·密州出猎》的第二句，通过\"左牵\"与\"右擎\"的对比，表达出猎的豪情。', 1, '[\"第二句\"]', b'1', 'SYSTEM', '2025-11-06 15:51:22.434', 'SYSTEM', '2025-11-06 15:51:22.434');
INSERT INTO `poetry_quiz_questions` VALUES (408, 14, 5, '《江城子·密州出猎》的作者是苏轼。', '{}', '{\"text\": \"true\"}', '《江城子·密州出猎》的作者确实是宋代的苏轼。', 2, '[\"作者：苏轼\"]', b'1', 'SYSTEM', '2025-11-06 15:51:22.437', 'SYSTEM', '2025-11-06 15:51:22.437');
INSERT INTO `poetry_quiz_questions` VALUES (409, 14, 3, '《江城子·密州出猎》的上阙主要表达了作者的__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"豪情壮志\"]}', '{\"text\": \"豪情壮志\"}', '《江城子·密州出猎》的上阙主要表达了作者的豪情壮志。', 3, '[\"上阙\"]', b'1', 'SYSTEM', '2025-11-06 15:51:22.439', 'SYSTEM', '2025-11-06 15:51:22.439');
INSERT INTO `poetry_quiz_questions` VALUES (410, 14, 5, '《江城子·密州出猎》的下阙表达了作者的强国抗敌的政治主张。', '{}', '{\"text\": \"true\"}', '《江城子·密州出猎》的下阙表达了作者的强国抗敌的政治主张。', 4, '[\"下阙\"]', b'1', 'SYSTEM', '2025-11-06 15:51:22.442', 'SYSTEM', '2025-11-06 15:51:22.442');
INSERT INTO `poetry_quiz_questions` VALUES (411, 15, 3, '驿外断桥边，寂寞开无主。已是黄昏独自愁，更著风和雨。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"寂寞开无主。\"]}', '{\"text\": \"寂寞开无主。\"}', '这是《卜算子·咏梅》的第二句，通过描述梅花的处境，表达作者的孤独与坚持。', 1, '[\"第二句\"]', b'1', 'SYSTEM', '2025-11-06 15:51:26.757', 'SYSTEM', '2025-11-06 15:51:26.757');
INSERT INTO `poetry_quiz_questions` VALUES (412, 15, 3, '驿外断桥边，寂寞开无主。已是黄昏独自愁，更著风和雨。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"已是黄昏独自愁。\"]}', '{\"text\": \"已是黄昏独自愁。\"}', '这是《卜算子·咏梅》的第三句，通过描述黄昏时分的景象，表达作者的孤独与忧愁。', 2, '[\"第三句\"]', b'1', 'SYSTEM', '2025-11-06 15:51:26.761', 'SYSTEM', '2025-11-06 15:51:26.761');
INSERT INTO `poetry_quiz_questions` VALUES (413, 15, 3, '驿外断桥边，寂寞开无主。已是黄昏独自愁，更著风和雨。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"更著风和雨。\"]}', '{\"text\": \"更著风和雨。\"}', '这是《卜算子·咏梅》的第四句，通过描述风雨交加的环境，表达作者的孤独与坚持。', 3, '[\"第四句\"]', b'1', 'SYSTEM', '2025-11-06 15:51:26.764', 'SYSTEM', '2025-11-06 15:51:26.764');
INSERT INTO `poetry_quiz_questions` VALUES (414, 15, 5, '《卜算子·咏梅》的作者是陆游。', '{}', '{\"text\": \"true\"}', '《卜算子·咏梅》的作者确实是陆游。', 4, '[\"作者\"]', b'1', 'SYSTEM', '2025-11-06 15:51:26.767', 'SYSTEM', '2025-11-06 15:51:26.767');
INSERT INTO `poetry_quiz_questions` VALUES (415, 15, 5, '《卜算子·咏梅》的题目是《卜算子》。', '{}', '{\"text\": \"true\"}', '《卜算子·咏梅》的题目确实是《卜算子》。', 5, '[\"题目\"]', b'1', 'SYSTEM', '2025-11-06 15:51:26.769', 'SYSTEM', '2025-11-06 15:51:26.769');
INSERT INTO `poetry_quiz_questions` VALUES (416, 16, 3, '之字在文言文中有多种用法：1、代词 2、助词 3、动词', '{\"blankCount\": 3, \"acceptableAnswers\": [\"代词\", \"助词\", \"动词\"]}', '{\"text\": \"代词\"}', '之字在文言文中可以作为代词、助词、动词使用。', 1, '[\"文言文中之字的用法\"]', b'1', 'SYSTEM', '2025-11-06 15:51:32.210', 'SYSTEM', '2025-11-06 15:51:32.210');
INSERT INTO `poetry_quiz_questions` VALUES (417, 16, 3, '之在文言文中的用法：1、代词\"它\" 2、助词\"的\" 3、动词\"到\"', '{\"blankCount\": 3, \"acceptableAnswers\": [\"代词\", \"助词\", \"动词\"]}', '{\"text\": \"代词\"}', '之在文言文中可以作为代词、助词、动词使用。', 1, '[\"文言文中之字的用法\"]', b'1', 'SYSTEM', '2025-11-06 15:51:32.214', 'SYSTEM', '2025-11-06 15:51:32.214');
INSERT INTO `poetry_quiz_questions` VALUES (418, 16, 1, '下列句子中，哪个选项中的\'之\'字用作代词？', '{\"choices\": [\"1. 无之而有之\", \"2. 之子于归\", \"3. 之子于归，宜其室家\"]}', '{\"text\": \"1. 无之而有之\"}', '在\'无之而有之\'中，\'之\'字用作代词，指代\'无\'和\'有\'。', 2, '[\"代词的用法\"]', b'1', 'SYSTEM', '2025-11-06 15:51:32.217', 'SYSTEM', '2025-11-06 15:51:32.217');
INSERT INTO `poetry_quiz_questions` VALUES (419, 16, 1, '下列句子中，哪个选项中的\'之\'字用作助词？', '{\"choices\": [\"1. 无之而有之\", \"2. 之子于归\", \"3. 之子于归，宜其室家\"]}', '{\"text\": \"2. 之子于归\"}', '在\'之子于归\'中，\'之\'字用作助词，相当于\'这\'。', 2, '[\"助词的用法\"]', b'1', 'SYSTEM', '2025-11-06 15:51:32.219', 'SYSTEM', '2025-11-06 15:51:32.219');
INSERT INTO `poetry_quiz_questions` VALUES (420, 16, 1, '下列句子中，哪个选项中的\'之\'字用作动词？', '{\"choices\": [\"1. 无之而有之\", \"2. 之子于归\", \"3. 之子于归，宜其室家\"]}', '{\"text\": \"3. 之子于归，宜其室家\"}', '在\'之子于归，宜其室家\'中，\'之\'字用作动词，表示\'到\'的意思。', 3, '[\"动词的用法\"]', b'1', 'SYSTEM', '2025-11-06 15:51:32.221', 'SYSTEM', '2025-11-06 15:51:32.221');
INSERT INTO `poetry_quiz_questions` VALUES (421, 17, 3, '床前明月光，疑是地上霜。举头望明月，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"低头思故乡\", \"低头思故乡。\"]}', '{\"text\": \"低头思故乡\"}', '这是《静夜思》的最后一句，通过\"低头\"与\"举头\"的对比，表达思乡之情。', 1, '[\"首字：低\"]', b'1', 'SYSTEM', '2025-11-06 15:51:38.615', 'SYSTEM', '2025-11-06 15:51:38.615');
INSERT INTO `poetry_quiz_questions` VALUES (422, 17, 1, '下列句子中，哪个选项中的\'而\'字表示转折关系？', '{\"choices\": [\"我吃了一顿饭，而他没吃。\", \"我吃了一顿饭，而他吃了两顿。\", \"我吃了一顿饭，而他吃了三顿。\"]}', '{\"text\": \"我吃了一顿饭，而他没吃。\"}', '在选项A中，\'而\'字表示转折关系，即虽然我吃了一顿饭，但他的情况与我不同。', 2, '[\"转折关系\"]', b'1', 'SYSTEM', '2025-11-06 15:51:38.619', 'SYSTEM', '2025-11-06 15:51:38.619');
INSERT INTO `poetry_quiz_questions` VALUES (423, 17, 2, '下列句子中，哪些选项中的\'而\'字表示并列关系？', '{\"choices\": [\"我吃了一顿饭，而他没吃。\", \"我吃了一顿饭，而他吃了两顿。\", \"我吃了一顿饭，而他吃了三顿。\", \"我吃了一顿饭，而他吃了两顿饭。\"]}', '{\"text\": [\"我吃了一顿饭，而他吃了两顿。\", \"我吃了一顿饭，而他吃了三顿。\", \"我吃了一顿饭，而他吃了两顿饭。\"]}', '在选项B、C和D中，\'而\'字表示并列关系，即我和他都吃了饭，但数量不同。', 3, '[\"并列关系\"]', b'1', 'SYSTEM', '2025-11-06 15:51:38.622', 'SYSTEM', '2025-11-06 15:51:38.622');
INSERT INTO `poetry_quiz_questions` VALUES (424, 17, 5, '下列句子中，\'而\'字表示修饰关系的是哪个选项？', '{\"choices\": [\"我吃了一顿饭，而他没吃。\", \"我吃了一顿饭，而他吃了两顿。\", \"我吃了一顿饭，而他吃了三顿。\", \"我吃了一顿饭，而他吃了两顿饭。\"]}', '{\"text\": \"我吃了一顿饭，而他吃了两顿。\"}', '在选项B中，\'而\'字表示修饰关系，即他吃两顿饭，修饰了他吃饭的数量。', 4, '[\"修饰关系\"]', b'1', 'SYSTEM', '2025-11-06 15:51:38.625', 'SYSTEM', '2025-11-06 15:51:38.625');
INSERT INTO `poetry_quiz_questions` VALUES (425, 17, 4, '请将下列句子按照\'而\'字的用法进行排序：\n1. 我吃了一顿饭，而他没吃。\n2. 我吃了一顿饭，而他吃了两顿。\n3. 我吃了一顿饭，而他吃了三顿。\n4. 我吃了一顿饭，而他吃了两顿饭。', '{\"choices\": [\"1\", \"2\", \"3\", \"4\"]}', '{\"text\": \"1, 2, 3, 4\"}', '根据\'而\'字的用法，这些句子可以按照转折、并列、修饰、并列的顺序进行排序。', 5, '[\"排序\"]', b'1', 'SYSTEM', '2025-11-06 15:51:38.627', 'SYSTEM', '2025-11-06 15:51:38.627');
INSERT INTO `poetry_quiz_questions` VALUES (426, 18, 3, '其可以表示他的、它的，或者表示反问、推测的语气。例如：__________，明月几时有？', '{\"blankCount\": 1, \"acceptableAnswers\": [\"不知天上宫阙\"]}', '{\"text\": \"不知天上宫阙\"}', '这是《水调歌头》中的名句，通过反问的语气表达对月亮的思念。', 2, '[\"反问语气\"]', b'1', 'SYSTEM', '2025-11-06 15:51:43.201', 'SYSTEM', '2025-11-06 15:51:43.201');
INSERT INTO `poetry_quiz_questions` VALUES (427, 18, 3, '其可以作为代词、副词、连词使用。例如：__________，明月几时有？', '{\"blankCount\": 1, \"acceptableAnswers\": [\"不知天上宫阙\"]}', '{\"text\": \"不知天上宫阙\"}', '这是《水调歌头》中的名句，通过反问的语气表达对月亮的思念。', 2, '[\"反问语气\"]', b'1', 'SYSTEM', '2025-11-06 15:51:43.206', 'SYSTEM', '2025-11-06 15:51:43.206');
INSERT INTO `poetry_quiz_questions` VALUES (428, 18, 5, '其可以表示他的、它的，或者表示反问、推测的语气。例如：__________，明月几时有？', '{\"trueFalse\": true}', '{\"text\": \"不知天上宫阙\"}', '这是《水调歌头》中的名句，通过反问的语气表达对月亮的思念。', 3, '[\"反问语气\"]', b'1', 'SYSTEM', '2025-11-06 15:51:43.209', 'SYSTEM', '2025-11-06 15:51:43.209');
INSERT INTO `poetry_quiz_questions` VALUES (429, 18, 5, '其可以作为代词、副词、连词使用。例如：__________，明月几时有？', '{\"trueFalse\": true}', '{\"text\": \"不知天上宫阙\"}', '这是《水调歌头》中的名句，通过反问的语气表达对月亮的思念。', 3, '[\"反问语气\"]', b'1', 'SYSTEM', '2025-11-06 15:51:43.212', 'SYSTEM', '2025-11-06 15:51:43.212');
INSERT INTO `poetry_quiz_questions` VALUES (430, 18, 5, '其可以表示他的、它的，或者表示反问、推测的语气。例如：__________，明月几时有？', '{\"trueFalse\": true}', '{\"text\": \"不知天上宫阙\"}', '这是《水调歌头》中的名句，通过反问的语气表达对月亮的思念。', 4, '[\"反问语气\"]', b'1', 'SYSTEM', '2025-11-06 15:51:43.215', 'SYSTEM', '2025-11-06 15:51:43.215');
INSERT INTO `poetry_quiz_questions` VALUES (431, 19, 3, '床前明月光，疑是地上霜。举头望明月，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"低头思故乡\", \"低头思故乡。\"]}', '{\"text\": \"低头思故乡\"}', '这是《静夜思》的最后一句，通过\"低头\"与\"举头\"的对比，表达思乡之情。', 1, '[\"首字：低\"]', b'1', 'SYSTEM', '2025-11-06 15:51:47.543', 'SYSTEM', '2025-11-06 15:51:47.543');
INSERT INTO `poetry_quiz_questions` VALUES (432, 19, 3, '以__________，则小人之过也必多。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"不教\"]}', '{\"text\": \"不教\"}', '出自《论语》，意思是不教育孩子，那么孩子犯的错误就会很多。', 2, '[\"出自《论语》\"]', b'1', 'SYSTEM', '2025-11-06 15:51:47.547', 'SYSTEM', '2025-11-06 15:51:47.547');
INSERT INTO `poetry_quiz_questions` VALUES (433, 19, 3, '以__________，则小人之过也必多。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"不教\"]}', '{\"text\": \"不教\"}', '出自《论语》，意思是不教育孩子，那么孩子犯的错误就会很多。', 2, '[\"出自《论语》\"]', b'1', 'SYSTEM', '2025-11-06 15:51:47.549', 'SYSTEM', '2025-11-06 15:51:47.549');
INSERT INTO `poetry_quiz_questions` VALUES (434, 19, 3, '以__________，则小人之过也必多。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"不教\"]}', '{\"text\": \"不教\"}', '出自《论语》，意思是不教育孩子，那么孩子犯的错误就会很多。', 2, '[\"出自《论语》\"]', b'1', 'SYSTEM', '2025-11-06 15:51:47.552', 'SYSTEM', '2025-11-06 15:51:47.552');
INSERT INTO `poetry_quiz_questions` VALUES (435, 19, 3, '以__________，则小人之过也必多。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"不教\"]}', '{\"text\": \"不教\"}', '出自《论语》，意思是不教育孩子，那么孩子犯的错误就会很多。', 2, '[\"出自《论语》\"]', b'1', 'SYSTEM', '2025-11-06 15:51:47.554', 'SYSTEM', '2025-11-06 15:51:47.554');
INSERT INTO `poetry_quiz_questions` VALUES (436, 20, 3, '床前明月光，疑是地上霜。举头望明月，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"低头思故乡\", \"低头思故乡。\"]}', '{\"text\": \"低头思故乡\"}', '这是《静夜思》的最后一句，通过\"低头\"与\"举头\"的对比，表达思乡之情。', 1, '[\"首字：低\"]', b'1', 'SYSTEM', '2025-11-06 15:51:54.101', 'SYSTEM', '2025-11-06 15:51:54.101');
INSERT INTO `poetry_quiz_questions` VALUES (437, 20, 3, '举头望明月，低头思故乡。床前明月光，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"疑是地上霜\", \"疑是地上霜。\"]}', '{\"text\": \"疑是地上霜\"}', '这是《静夜思》的第二句，通过\"明月光\"与\"地上霜\"的对比，表达月光如霜的意境。', 2, '[\"第二句：疑\"]', b'1', 'SYSTEM', '2025-11-06 15:51:54.104', 'SYSTEM', '2025-11-06 15:51:54.104');
INSERT INTO `poetry_quiz_questions` VALUES (438, 20, 1, '下列句子中，哪个使用了\"于\"字表示时间？', '{\"choices\": [\"A. 春眠不觉晓，处处闻啼鸟。B. 举头望明月，低头思故乡。C. 明月松间照，清泉石上流。D. 月落乌啼霜满天，江枫渔火对愁眠。\"]}', '{\"text\": \"D\"}', '选项D中的\"月落乌啼霜满天\"使用了\"于\"字表示时间，即\"月落\"。', 3, '[\"表示时间\"]', b'1', 'SYSTEM', '2025-11-06 15:51:54.106', 'SYSTEM', '2025-11-06 15:51:54.106');
INSERT INTO `poetry_quiz_questions` VALUES (439, 20, 2, '下列句子中，哪些使用了\"于\"字表示对象？', '{\"choices\": [\"A. 春眠不觉晓，处处闻啼鸟。B. 举头望明月，低头思故乡。C. 明月松间照，清泉石上流。D. 月落乌啼霜满天，江枫渔火对愁眠。E. 独坐幽篁里，弹琴复长啸。\"]}', '{\"text\": [\"C\", \"E\"]}', '选项C中的\"明月松间照，清泉石上流\"使用了\"于\"字表示对象，即\"明月照松间\"和\"清泉流石上\"。选项E中的\"独坐幽篁里，弹琴复长啸\"使用了\"于\"字表示对象，即\"独坐幽篁里\"。', 4, '[\"表示对象\"]', b'1', 'SYSTEM', '2025-11-06 15:51:54.109', 'SYSTEM', '2025-11-06 15:51:54.109');
INSERT INTO `poetry_quiz_questions` VALUES (440, 20, 5, '下列句子中，哪个使用了\"于\"字表示地点？', '{\"choices\": [\"A. 春眠不觉晓，处处闻啼鸟。B. 举头望明月，低头思故乡。C. 明月松间照，清泉石上流。D. 月落乌啼霜满天，江枫渔火对愁眠。E. 独坐幽篁里，弹琴复长啸。\"]}', '{\"text\": \"D\"}', '选项D中的\"月落乌啼霜满天，江枫渔火对愁眠\"使用了\"于\"字表示地点，即\"月落乌啼霜满天\"。', 5, '[\"表示地点\"]', b'1', 'SYSTEM', '2025-11-06 15:51:54.111', 'SYSTEM', '2025-11-06 15:51:54.111');
INSERT INTO `poetry_quiz_questions` VALUES (441, 21, 3, '床前明月光，疑是地上霜。举头望明月，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"低头思故乡\", \"低头思故乡。\"]}', '{\"text\": \"低头思故乡\"}', '这是《静夜思》的最后一句，通过\"低头\"与\"举头\"的对比，表达思乡之情。', 1, '[\"首字：低\"]', b'1', 'SYSTEM', '2025-11-06 15:52:17.384', 'SYSTEM', '2025-11-06 15:52:17.384');
INSERT INTO `poetry_quiz_questions` VALUES (442, 21, 3, '__________，疑是地上霜。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"床前明月光\"]}', '{\"text\": \"床前明月光\"}', '这是《静夜思》的第二句，描述了月光洒在窗户上的景象。', 2, '[\"首字：床\"]', b'1', 'SYSTEM', '2025-11-06 15:52:17.388', 'SYSTEM', '2025-11-06 15:52:17.388');
INSERT INTO `poetry_quiz_questions` VALUES (443, 21, 5, '这首诗表达了作者对家乡的思念之情。', '{}', '{\"text\": \"true\"}', '通过明月意象表达思乡之情。', 3, '[\"主题：思乡\"]', b'1', 'SYSTEM', '2025-11-06 15:52:17.390', 'SYSTEM', '2025-11-06 15:52:17.390');
INSERT INTO `poetry_quiz_questions` VALUES (444, 21, 4, '请将以下诗句按照原文顺序排序：\n1. 举头望明月\n2. 疑是地上霜\n3. 床前明月光\n4. 低头思故乡', '{\"items\": [\"床前明月光\", \"疑是地上霜\", \"举头望明月\", \"低头思故乡\"]}', '{\"order\": \"3, 2, 1, 4\"}', '根据原文顺序排列诗句。', 4, '[\"顺序：原文\"]', b'1', 'SYSTEM', '2025-11-06 15:52:17.393', 'SYSTEM', '2025-11-06 15:52:17.393');
INSERT INTO `poetry_quiz_questions` VALUES (445, 21, 5, '这首诗的作者是唐代诗人李白。', '{}', '{\"text\": \"true\"}', '《静夜思》的作者是唐代诗人李白。', 5, '[\"作者：李白\"]', b'1', 'SYSTEM', '2025-11-06 15:52:17.395', 'SYSTEM', '2025-11-06 15:52:17.395');
INSERT INTO `poetry_quiz_questions` VALUES (446, 22, 3, '日照香炉生紫烟，遥看瀑布挂前川。飞流直下三千尺，疑是银河落九天。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"疑是银河落九天。\"]}', '{\"text\": \"疑是银河落九天。\"}', '这是《望庐山瀑布》的最后一句，通过夸张手法描绘庐山瀑布的雄伟壮观。', 1, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 15:52:22.168', 'SYSTEM', '2025-11-06 15:52:22.168');
INSERT INTO `poetry_quiz_questions` VALUES (447, 22, 3, '香炉峰在阳光的照射下生起紫色烟霞，远远望见瀑布似白色绢绸悬挂在山前。高崖上飞腾直落的瀑布好像有几千尺，让人恍惚以为银河从天上泻落到人间。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"高崖上飞腾直落的瀑布好像有几千尺。\"]}', '{\"text\": \"高崖上飞腾直落的瀑布好像有几千尺。\"}', '这是《望庐山瀑布》的第三句，通过夸张手法描绘庐山瀑布的雄伟壮观。', 2, '[\"第三句\"]', b'1', 'SYSTEM', '2025-11-06 15:52:22.172', 'SYSTEM', '2025-11-06 15:52:22.172');
INSERT INTO `poetry_quiz_questions` VALUES (448, 22, 5, '《望庐山瀑布》的作者是李白。', '{}', '{\"text\": \"true\"}', '《望庐山瀑布》的作者确实是唐代诗人李白。', 3, '[\"作者是李白\"]', b'1', 'SYSTEM', '2025-11-06 15:52:22.174', 'SYSTEM', '2025-11-06 15:52:22.174');
INSERT INTO `poetry_quiz_questions` VALUES (449, 22, 4, '请将以下诗句按照原文顺序排序：日照香炉生紫烟，遥看瀑布挂前川。飞流直下三千尺，疑是银河落九天。', '{}', '{\"text\": \"日照香炉生紫烟，遥看瀑布挂前川。飞流直下三千尺，疑是银河落九天。\"}', '这是《望庐山瀑布》的原文，按照原文顺序排列。', 4, '[\"原文顺序\"]', b'1', 'SYSTEM', '2025-11-06 15:52:22.177', 'SYSTEM', '2025-11-06 15:52:22.177');
INSERT INTO `poetry_quiz_questions` VALUES (450, 22, 1, '《望庐山瀑布》中，作者运用了哪种修辞手法来描绘瀑布的壮观？', '{}', '{\"text\": \"夸张\"}', '《望庐山瀑布》中，作者通过夸张手法描绘了瀑布的雄伟壮观。', 5, '[\"夸张\"]', b'1', 'SYSTEM', '2025-11-06 15:52:22.179', 'SYSTEM', '2025-11-06 15:52:22.179');
INSERT INTO `poetry_quiz_questions` VALUES (451, 23, 3, '两岸猿声啼不住，轻舟已过_________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"万重山\"]}', '{\"text\": \"万重山\"}', '这是《早发白帝城》的最后一句，通过“两岸猿声”与“轻舟已过”的对比，表达诗人轻松愉快的心情。', 1, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 15:52:26.606', 'SYSTEM', '2025-11-06 15:52:26.606');
INSERT INTO `poetry_quiz_questions` VALUES (452, 23, 3, '朝辞白帝彩云间，_________江陵一日还。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"千里\"]}', '{\"text\": \"千里\"}', '这是《早发白帝城》的第二句，通过“朝辞白帝”与“千里江陵”的对比，表达诗人轻松愉快的心情。', 2, '[\"第二句\"]', b'1', 'SYSTEM', '2025-11-06 15:52:26.609', 'SYSTEM', '2025-11-06 15:52:26.609');
INSERT INTO `poetry_quiz_questions` VALUES (453, 23, 5, '《早发白帝城》的作者是李白。', '{}', '{\"text\": \"true\"}', '《早发白帝城》的作者确实是唐代诗人李白。', 3, '[\"作者是李白\"]', b'1', 'SYSTEM', '2025-11-06 15:52:26.612', 'SYSTEM', '2025-11-06 15:52:26.612');
INSERT INTO `poetry_quiz_questions` VALUES (454, 23, 5, '《早发白帝城》表达了诗人遇赦后愉快的心情。', '{}', '{\"text\": \"true\"}', '《早发白帝城》通过“轻舟已过万重山”等诗句，表达了诗人遇赦后愉快的心情。', 4, '[\"愉快的心情\"]', b'1', 'SYSTEM', '2025-11-06 15:52:26.615', 'SYSTEM', '2025-11-06 15:52:26.615');
INSERT INTO `poetry_quiz_questions` VALUES (455, 23, 4, '请按照时间顺序排列以下事件：\n1. 朝辞白帝\n2. 轻舟已过万重山\n3. 千里江陵一日还\n4. 两岸猿声啼不住', '{}', '{\"order\": [1, 4, 2, 3]}', '《早发白帝城》的诗句按照时间顺序排列为：朝辞白帝，两岸猿声啼不住，轻舟已过万重山，千里江陵一日还。', 5, '[\"时间顺序\"]', b'1', 'SYSTEM', '2025-11-06 15:52:26.617', 'SYSTEM', '2025-11-06 15:52:26.617');
INSERT INTO `poetry_quiz_questions` VALUES (456, 24, 3, '孤帆远影碧空尽，唯见长江天际流。故人西辞黄鹤楼，烟花三月下扬州。李白的《黄鹤楼送孟浩然之广陵》中，表达了离别之情的诗句是：', '{\"blankCount\": 1, \"acceptableAnswers\": [\"孤帆远影碧空尽，唯见长江天际流。\"]}', '{\"text\": \"孤帆远影碧空尽，唯见长江天际流。\"}', '这是《黄鹤楼送孟浩然之广陵》的后两句，表达了离别之情。', 1, '[\"离别之情\"]', b'1', 'SYSTEM', '2025-11-06 15:52:32.243', 'SYSTEM', '2025-11-06 15:52:32.243');
INSERT INTO `poetry_quiz_questions` VALUES (457, 24, 3, '《黄鹤楼送孟浩然之广陵》中，描写友人离去后诗人所见的诗句是：', '{\"blankCount\": 1, \"acceptableAnswers\": [\"孤帆远影碧空尽，唯见长江天际流。\"]}', '{\"text\": \"孤帆远影碧空尽，唯见长江天际流。\"}', '这是《黄鹤楼送孟浩然之广陵》的后两句，表达了离别之情。', 2, '[\"离别后所见\"]', b'1', 'SYSTEM', '2025-11-06 15:52:32.247', 'SYSTEM', '2025-11-06 15:52:32.247');
INSERT INTO `poetry_quiz_questions` VALUES (458, 24, 5, '《黄鹤楼送孟浩然之广陵》的作者是李白。', '{}', '{\"text\": \"true\"}', '《黄鹤楼送孟浩然之广陵》的作者确实是唐代诗人李白。', 3, '[\"作者\"]', b'1', 'SYSTEM', '2025-11-06 15:52:32.250', 'SYSTEM', '2025-11-06 15:52:32.250');
INSERT INTO `poetry_quiz_questions` VALUES (459, 24, 5, '《黄鹤楼送孟浩然之广陵》中，\'烟花三月下扬州\'一句描绘了春天的美景。', '{}', '{\"text\": \"true\"}', '《黄鹤楼送孟浩然之广陵》中，\'烟花三月下扬州\'一句描绘了春天的美景。', 4, '[\"春天的美景\"]', b'1', 'SYSTEM', '2025-11-06 15:52:32.253', 'SYSTEM', '2025-11-06 15:52:32.253');
INSERT INTO `poetry_quiz_questions` VALUES (460, 24, 4, '请按照时间顺序排列以下事件：\n1. 孟浩然离开黄鹤楼\n2. 李白与孟浩然在黄鹤楼告别\n3. 孟浩然乘坐孤帆远去\n4. 李白目送孟浩然远去\n5. 孟浩然到达扬州\n请用数字表示顺序：', '{}', '{\"text\": \"2, 1, 4, 3, 5\"}', '根据《黄鹤楼送孟浩然之广陵》的诗意，事件的顺序应该是：李白与孟浩然在黄鹤楼告别，孟浩然离开黄鹤楼，李白目送孟浩然远去，孟浩然乘坐孤帆远去，孟浩然到达扬州。', 5, '[\"时间顺序\"]', b'1', 'SYSTEM', '2025-11-06 15:52:32.255', 'SYSTEM', '2025-11-06 15:52:32.255');
INSERT INTO `poetry_quiz_questions` VALUES (461, 25, 3, '金樽清酒斗十千，玉盘珍羞直万钱。停杯投箸不能食，拔剑四顾心茫然。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"心茫然。\"]}', '{\"text\": \"心茫然。\"}', '这是《行路难》的最后两句，通过\"拔剑\"与\"心茫然\"的对比，表达作者的迷茫与无奈。', 1, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 15:52:37.080', 'SYSTEM', '2025-11-06 15:52:37.080');
INSERT INTO `poetry_quiz_questions` VALUES (462, 25, 3, '金樽清酒斗十千，玉盘珍羞直万钱。停杯投箸不能食，拔剑四顾心茫然。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"拔剑四顾心茫然。\"]}', '{\"text\": \"拔剑四顾心茫然。\"}', '这是《行路难》的最后两句，通过\"拔剑\"与\"心茫然\"的对比，表达作者的迷茫与无奈。', 1, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 15:52:37.084', 'SYSTEM', '2025-11-06 15:52:37.084');
INSERT INTO `poetry_quiz_questions` VALUES (463, 25, 5, '金樽清酒斗十千，玉盘珍羞直万钱。停杯投箸不能食，拔剑四顾心茫然。', '{}', '{\"text\": \"True\"}', '这首诗的前两句描绘了宴席的奢华，后两句则表达了作者的失落与迷茫。', 2, '[\"奢华宴席\", \"失落与迷茫\"]', b'1', 'SYSTEM', '2025-11-06 15:52:37.086', 'SYSTEM', '2025-11-06 15:52:37.086');
INSERT INTO `poetry_quiz_questions` VALUES (464, 25, 4, '金樽清酒斗十千，玉盘珍羞直万钱。停杯投箸不能食，拔剑四顾心茫然。', '{}', '{\"text\": \"金樽清酒斗十千，玉盘珍羞直万钱。停杯投箸不能食，拔剑四顾心茫然。\"}', '请将诗句按照原文顺序排列。', 3, '[\"原文顺序\"]', b'1', 'SYSTEM', '2025-11-06 15:52:37.088', 'SYSTEM', '2025-11-06 15:52:37.088');
INSERT INTO `poetry_quiz_questions` VALUES (465, 25, 5, '金樽清酒斗十千，玉盘珍羞直万钱。停杯投箸不能食，拔剑四顾心茫然。', '{}', '{\"text\": \"False\"}', '这首诗的前两句描绘了宴席的奢华，后两句则表达了作者的失落与迷茫。', 4, '[\"奢华宴席\", \"失落与迷茫\"]', b'1', 'SYSTEM', '2025-11-06 15:52:37.091', 'SYSTEM', '2025-11-06 15:52:37.091');
INSERT INTO `poetry_quiz_questions` VALUES (466, 26, 3, '国破山河在，城春草木深。感时花溅泪，恨别鸟惊心。__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"国破山河在，城春草木深。感时花溅泪，恨别鸟惊心。\"]}', '{\"text\": \"国破山河在，城春草木深。感时花溅泪，恨别鸟惊心。\"}', '这是《春望》的原文，通过填空的方式加深对诗歌内容的理解。', 1, '[\"首句：国破山河在\"]', b'1', 'SYSTEM', '2025-11-06 15:52:42.253', 'SYSTEM', '2025-11-06 15:52:42.253');
INSERT INTO `poetry_quiz_questions` VALUES (467, 26, 3, '感时花溅泪，恨别鸟惊心。__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"感时花溅泪，恨别鸟惊心。\"]}', '{\"text\": \"感时花溅泪，恨别鸟惊心。\"}', '这是《春望》的第二句，通过填空的方式加深对诗歌内容的理解。', 2, '[\"第二句：感时花溅泪，恨别鸟惊心。\"]', b'1', 'SYSTEM', '2025-11-06 15:52:42.257', 'SYSTEM', '2025-11-06 15:52:42.257');
INSERT INTO `poetry_quiz_questions` VALUES (468, 26, 5, '《春望》的作者是杜甫。', '{}', '{\"text\": \"true\"}', '《春望》的作者确实是唐代诗人杜甫。', 3, '[\"作者：杜甫\"]', b'1', 'SYSTEM', '2025-11-06 15:52:42.259', 'SYSTEM', '2025-11-06 15:52:42.259');
INSERT INTO `poetry_quiz_questions` VALUES (469, 26, 4, '请将以下诗句按照原文顺序排序：\n1. 感时花溅泪，恨别鸟惊心。\n2. 国破山河在，城春草木深。\n3. 低头思故乡。', '{}', '{\"text\": \"2, 1, 3\"}', '这是《春望》的诗句顺序，通过排序的方式加深对诗歌内容的理解。', 4, '[\"顺序：国破山河在，城春草木深。感时花溅泪，恨别鸟惊心。低头思故乡。\"]', b'1', 'SYSTEM', '2025-11-06 15:52:42.262', 'SYSTEM', '2025-11-06 15:52:42.262');
INSERT INTO `poetry_quiz_questions` VALUES (470, 26, 1, '《春望》表达了诗人怎样的情怀？\nA. 思乡之情\nB. 忧国忧民的情怀\nC. 悲伤之情\nD. 愤怒之情', '{}', '{\"text\": \"B\"}', '《春望》表达了诗人忧国忧民的情怀，通过分析诗句内容可以得出答案。', 5, '[\"情怀：忧国忧民\"]', b'1', 'SYSTEM', '2025-11-06 15:52:42.264', 'SYSTEM', '2025-11-06 15:52:42.264');
INSERT INTO `poetry_quiz_questions` VALUES (471, 27, 3, '无边落木萧萧下，不尽长江滚滚来。__________，不尽长江滚滚来。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"无边落木萧萧下\"]}', '{\"text\": \"无边落木萧萧下\"}', '这是《登高》的第二句，通过\"无边\"与\"不尽\"的对比，表达诗人对自然景象的感慨。', 1, '[\"第二句\"]', b'1', 'SYSTEM', '2025-11-06 15:52:46.784', 'SYSTEM', '2025-11-06 15:52:46.784');
INSERT INTO `poetry_quiz_questions` VALUES (472, 27, 3, '风急天高猿啸哀，渚清沙白鸟飞回。__________，不尽长江滚滚来。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"无边落木萧萧下\"]}', '{\"text\": \"无边落木萧萧下\"}', '这是《登高》的第二句，通过\"无边\"与\"不尽\"的对比，表达诗人对自然景象的感慨。', 1, '[\"第二句\"]', b'1', 'SYSTEM', '2025-11-06 15:52:46.788', 'SYSTEM', '2025-11-06 15:52:46.788');
INSERT INTO `poetry_quiz_questions` VALUES (473, 27, 5, '这首诗的作者是杜甫。', '{}', '{\"text\": \"true\"}', '《登高》的作者确实是唐代诗人杜甫。', 2, '[\"唐代诗人\"]', b'1', 'SYSTEM', '2025-11-06 15:52:46.791', 'SYSTEM', '2025-11-06 15:52:46.791');
INSERT INTO `poetry_quiz_questions` VALUES (474, 27, 5, '这首诗的标题是《静夜思》。', '{}', '{\"text\": \"false\"}', '这首诗的标题是《登高》，不是《静夜思》。', 2, '[\"唐代诗人\"]', b'1', 'SYSTEM', '2025-11-06 15:52:46.792', 'SYSTEM', '2025-11-06 15:52:46.792');
INSERT INTO `poetry_quiz_questions` VALUES (475, 27, 4, '请将以下诗句按照原文顺序排序：\n1. 无边落木萧萧下\n2. 风急天高猿啸哀\n3. 不尽长江滚滚来\n4. 渚清沙白鸟飞回', '{}', '{\"text\": \"2, 4, 1, 3\"}', '根据原文顺序，诗句的正确排序应该是：风急天高猿啸哀，渚清沙白鸟飞回，无边落木萧萧下，不尽长江滚滚来。', 4, '[\"原文顺序\"]', b'1', 'SYSTEM', '2025-11-06 15:52:46.794', 'SYSTEM', '2025-11-06 15:52:46.794');
INSERT INTO `poetry_quiz_questions` VALUES (476, 28, 3, '好雨知时节，当春乃发生。随风潜入夜，润物细无声。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"好雨知时节，当春乃发生。随风潜入夜，润物细无声。\"]}', '{\"text\": \"润物细无声\"}', '这是《春夜喜雨》的第四句，描绘了春夜降雨、润泽万物的美景。', 1, '[\"润物细无声\"]', b'1', 'SYSTEM', '2025-11-06 15:52:51.014', 'SYSTEM', '2025-11-06 15:52:51.014');
INSERT INTO `poetry_quiz_questions` VALUES (477, 28, 3, '随风潜入夜，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"润物细无声\"]}', '{\"text\": \"润物细无声\"}', '这是《春夜喜雨》的第四句，描绘了春夜降雨、润泽万物的美景。', 2, '[\"润物细无声\"]', b'1', 'SYSTEM', '2025-11-06 15:52:51.018', 'SYSTEM', '2025-11-06 15:52:51.018');
INSERT INTO `poetry_quiz_questions` VALUES (478, 28, 5, '春夜喜雨是唐代诗人杜甫的作品。', '{}', '{\"text\": \"true\"}', '春夜喜雨是唐代诗人杜甫的作品。', 3, '[]', b'1', 'SYSTEM', '2025-11-06 15:52:51.020', 'SYSTEM', '2025-11-06 15:52:51.020');
INSERT INTO `poetry_quiz_questions` VALUES (479, 28, 4, '请将以下诗句按照原文顺序排序：\n1. 好雨知时节\n2. 当春乃发生\n3. 随风潜入夜\n4. 润物细无声。', '{}', '{\"text\": \"1, 2, 3, 4\"}', '这是《春夜喜雨》的原文顺序，描述了春夜降雨、润泽万物的美景。', 4, '[]', b'1', 'SYSTEM', '2025-11-06 15:52:51.022', 'SYSTEM', '2025-11-06 15:52:51.022');
INSERT INTO `poetry_quiz_questions` VALUES (480, 28, 5, '《春夜喜雨》描绘了春夜降雨、润泽万物的美景。', '{}', '{\"text\": \"true\"}', '《春夜喜雨》描绘了春夜降雨、润泽万物的美景。', 5, '[]', b'1', 'SYSTEM', '2025-11-06 15:52:51.024', 'SYSTEM', '2025-11-06 15:52:51.024');
INSERT INTO `poetry_quiz_questions` VALUES (481, 29, 3, '岱宗夫如何？齐鲁青未了。造化钟神秀，阴阳割昏晓。__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"阴阳割昏晓。\"]}', '{\"text\": \"阴阳割昏晓。\"}', '这是《望岳》的第四句，通过描绘泰山雄伟磅礴的气象抒发抱负。', 1, '[\"首字：阳\"]', b'1', 'SYSTEM', '2025-11-06 15:52:55.333', 'SYSTEM', '2025-11-06 15:52:55.333');
INSERT INTO `poetry_quiz_questions` VALUES (482, 29, 3, '岱宗夫如何？齐鲁青未了。造化钟神秀，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"阴阳割昏晓。\"]}', '{\"text\": \"阴阳割昏晓。\"}', '这是《望岳》的第三句，通过描绘泰山雄伟磅礴的气象抒发抱负。', 2, '[\"次字：钟\"]', b'1', 'SYSTEM', '2025-11-06 15:52:55.336', 'SYSTEM', '2025-11-06 15:52:55.336');
INSERT INTO `poetry_quiz_questions` VALUES (483, 29, 5, '《望岳》的作者是杜甫。', '{}', '{\"text\": \"true\"}', '《望岳》的作者确实是唐代诗人杜甫。', 3, '[]', b'1', 'SYSTEM', '2025-11-06 15:52:55.339', 'SYSTEM', '2025-11-06 15:52:55.339');
INSERT INTO `poetry_quiz_questions` VALUES (484, 29, 5, '《望岳》描绘了泰山的雄伟磅礴的气象。', '{}', '{\"text\": \"true\"}', '《望岳》通过描绘泰山的雄伟磅礴的气象抒发抱负。', 4, '[]', b'1', 'SYSTEM', '2025-11-06 15:52:55.342', 'SYSTEM', '2025-11-06 15:52:55.342');
INSERT INTO `poetry_quiz_questions` VALUES (485, 29, 4, '请将《望岳》的诗句按顺序排列：\n1. 齐鲁青未了\n2. 造化钟神秀\n3. 阴阳割昏晓\n4. 岱宗夫如何？', '{}', '{\"text\": \"4, 1, 2, 3\"}', '《望岳》的诗句顺序为：岱宗夫如何？齐鲁青未了。造化钟神秀，阴阳割昏晓。', 5, '[]', b'1', 'SYSTEM', '2025-11-06 15:52:55.345', 'SYSTEM', '2025-11-06 15:52:55.345');
INSERT INTO `poetry_quiz_questions` VALUES (486, 30, 3, '两个黄鹂鸣翠柳，一行白鹭上青天。窗含西岭千秋雪，门泊东吴万里船。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"窗含西岭千秋雪\"]}', '{\"text\": \"窗含西岭千秋雪\"}', '这是《绝句》的第四句，描绘了草堂周围明媚秀丽的春天景色。', 1, '[\"第四句\"]', b'1', 'SYSTEM', '2025-11-06 15:53:00.592', 'SYSTEM', '2025-11-06 15:53:00.592');
INSERT INTO `poetry_quiz_questions` VALUES (487, 30, 3, '两个黄鹂鸣翠柳，一行白鹭上青天。窗含西岭千秋雪，门泊东吴万里船。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"门泊东吴万里船\"]}', '{\"text\": \"门泊东吴万里船\"}', '这是《绝句》的第四句，描绘了草堂周围明媚秀丽的春天景色。', 1, '[\"第四句\"]', b'1', 'SYSTEM', '2025-11-06 15:53:00.595', 'SYSTEM', '2025-11-06 15:53:00.595');
INSERT INTO `poetry_quiz_questions` VALUES (488, 30, 1, '《绝句》描绘了草堂周围明媚秀丽的春天景色，以下哪一句不是出自《绝句》？', '{\"choices\": [\"两个黄鹂鸣翠柳\", \"一行白鹭上青天\", \"窗含西岭千秋雪\", \"门泊东吴万里船\"], \"choiceCount\": 4}', '{\"text\": \"窗含西岭千秋雪\"}', '窗含西岭千秋雪是《绝句》的第四句，描绘了草堂周围明媚秀丽的春天景色。', 2, '[\"不是《绝句》的诗句\"]', b'1', 'SYSTEM', '2025-11-06 15:53:00.598', 'SYSTEM', '2025-11-06 15:53:00.598');
INSERT INTO `poetry_quiz_questions` VALUES (489, 30, 5, '《绝句》描绘了草堂周围明媚秀丽的春天景色。', '{}', '{\"text\": \"true\"}', '《绝句》描绘了草堂周围明媚秀丽的春天景色。', 3, '[\"描绘了春天景色\"]', b'1', 'SYSTEM', '2025-11-06 15:53:00.603', 'SYSTEM', '2025-11-06 15:53:00.603');
INSERT INTO `poetry_quiz_questions` VALUES (490, 30, 4, '请将以下诗句按照《绝句》的顺序排列：\n1. 两个黄鹂鸣翠柳\n2. 一行白鹭上青天\n3. 窗含西岭千秋雪\n4. 门泊东吴万里船', '{\"choices\": [\"1\", \"2\", \"3\", \"4\"]}', '{\"text\": \"1234\"}', '《绝句》的诗句顺序为：两个黄鹂鸣翠柳，一行白鹭上青天，窗含西岭千秋雪，门泊东吴万里船。', 4, '[\"诗句顺序\"]', b'1', 'SYSTEM', '2025-11-06 15:53:00.606', 'SYSTEM', '2025-11-06 15:53:00.606');
INSERT INTO `poetry_quiz_questions` VALUES (491, 31, 3, '离离原上草，一岁一枯荣。野火烧不尽，春风吹又生。__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"离离原上草，一岁一枯荣。野火烧不尽，春风吹又生。\"]}', '{\"text\": \"离离原上草，一岁一枯荣。野火烧不尽，春风吹又生。\"}', '这是《赋得古原草送别》的后半部分，通过描写古原上野草的生长与枯荣，表达送别之情。', 1, '[\"首句：离离原上草\"]', b'1', 'SYSTEM', '2025-11-06 15:53:13.101', 'SYSTEM', '2025-11-06 15:53:13.101');
INSERT INTO `poetry_quiz_questions` VALUES (492, 31, 3, '离离原上草，一岁一枯荣。野火烧不尽，春风吹又生。离离原上草，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"一岁一枯荣。野火烧不尽，春风吹又生。\"]}', '{\"text\": \"一岁一枯荣。野火烧不尽，春风吹又生。\"}', '这是《赋得古原草送别》的后半部分，通过描写古原上野草的生长与枯荣，表达送别之情。', 2, '[\"第二句：一岁一枯荣\"]', b'1', 'SYSTEM', '2025-11-06 15:53:13.104', 'SYSTEM', '2025-11-06 15:53:13.104');
INSERT INTO `poetry_quiz_questions` VALUES (493, 31, 5, '离离原上草，一岁一枯荣。野火烧不尽，春风吹又生。这首诗的作者是白居易。', '{}', '{\"text\": \"true\"}', '这首诗的作者确实是唐代诗人白居易。', 3, '[\"作者：白居易\"]', b'1', 'SYSTEM', '2025-11-06 15:53:13.106', 'SYSTEM', '2025-11-06 15:53:13.106');
INSERT INTO `poetry_quiz_questions` VALUES (494, 31, 5, '离离原上草，一岁一枯荣。野火烧不尽，春风吹又生。这首诗的背景是送别友人。', '{}', '{\"text\": \"true\"}', '这首诗通过描写古原上野草的生长与枯荣，表达送别之情。', 4, '[\"背景：送别\"]', b'1', 'SYSTEM', '2025-11-06 15:53:13.109', 'SYSTEM', '2025-11-06 15:53:13.109');
INSERT INTO `poetry_quiz_questions` VALUES (495, 31, 4, '离离原上草，一岁一枯荣。野火烧不尽，春风吹又生。请将诗句按照正确的顺序排列。', '{}', '{\"text\": \"离离原上草，一岁一枯荣。野火烧不尽，春风吹又生。\"}', '这首诗的正确顺序是：离离原上草，一岁一枯荣。野火烧不尽，春风吹又生。', 5, '[\"顺序：离离原上草，一岁一枯荣。野火烧不尽，春风吹又生。\"]', b'1', 'SYSTEM', '2025-11-06 15:53:13.111', 'SYSTEM', '2025-11-06 15:53:13.111');
INSERT INTO `poetry_quiz_questions` VALUES (496, 32, 3, '床前明月光，疑是地上霜。举头望明月，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"低头思故乡\", \"低头思故乡。\"]}', '{\"text\": \"低头思故乡\"}', '这是《静夜思》的最后一句，通过\"低头\"与\"举头\"的对比，表达思乡之情。', 1, '[\"首字：低\"]', b'1', 'SYSTEM', '2025-11-06 15:53:17.446', 'SYSTEM', '2025-11-06 15:53:17.446');
INSERT INTO `poetry_quiz_questions` VALUES (497, 32, 3, '白居易的《琵琶行》中，\'主人下马客在船\'的下一句是：__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"举酒欲饮无管弦\", \"举酒欲饮无管弦。\"]}', '{\"text\": \"举酒欲饮无管弦\"}', '这是《琵琶行》中的第二句，描述了送别宴席的场景。', 2, '[\"第二句\"]', b'1', 'SYSTEM', '2025-11-06 15:53:17.450', 'SYSTEM', '2025-11-06 15:53:17.450');
INSERT INTO `poetry_quiz_questions` VALUES (498, 32, 5, '《琵琶行》的作者是白居易。', '{}', '{\"text\": \"true\"}', '《琵琶行》的作者确实是唐代诗人白居易。', 3, '[\"作者：白居易\"]', b'1', 'SYSTEM', '2025-11-06 15:53:17.453', 'SYSTEM', '2025-11-06 15:53:17.453');
INSERT INTO `poetry_quiz_questions` VALUES (499, 32, 4, '请将以下诗句按照原文顺序排序：\n1. 举酒欲饮无管弦\n2. 无管弦\n3. 无管弦。', '{}', '{\"text\": \"1, 2, 3\"}', '这是《琵琶行》中的诗句，按照原文顺序排列。', 4, '[\"原文顺序\"]', b'1', 'SYSTEM', '2025-11-06 15:53:17.455', 'SYSTEM', '2025-11-06 15:53:17.455');
INSERT INTO `poetry_quiz_questions` VALUES (500, 32, 5, '《琵琶行》是一首长篇叙事诗。', '{}', '{\"text\": \"true\"}', '《琵琶行》确实是一首长篇叙事诗，借琵琶女的遭遇抒发自身感慨。', 5, '[\"长篇叙事诗\"]', b'1', 'SYSTEM', '2025-11-06 15:53:17.458', 'SYSTEM', '2025-11-06 15:53:17.458');
INSERT INTO `poetry_quiz_questions` VALUES (501, 34, 3, '江南好，风景旧曾谙。日出江花红胜火，春来江水绿如蓝。能不忆江南？', '{\"blankCount\": 1, \"acceptableAnswers\": [\"日出江花红胜火，春来江水绿如蓝。能不忆江南？\"]}', '{\"text\": \"日出江花红胜火，春来江水绿如蓝。能不忆江南？\"}', '这是《忆江南》的后半部分，通过描述江南的美丽景色，表达对江南的怀念。', 1, '[\"首句：江南好\"]', b'1', 'SYSTEM', '2025-11-06 15:53:27.493', 'SYSTEM', '2025-11-06 15:53:27.493');
INSERT INTO `poetry_quiz_questions` VALUES (502, 34, 3, '江南好，风景旧曾谙。日出江花红胜火，春来江水绿如蓝。能不忆江南？', '{\"blankCount\": 1, \"acceptableAnswers\": [\"日出江花红胜火，春来江水绿如蓝。能不忆江南？\"]}', '{\"text\": \"日出江花红胜火，春来江水绿如蓝。能不忆江南？\"}', '这是《忆江南》的后半部分，通过描述江南的美丽景色，表达对江南的怀念。', 1, '[\"首句：江南好\"]', b'1', 'SYSTEM', '2025-11-06 15:53:27.496', 'SYSTEM', '2025-11-06 15:53:27.496');
INSERT INTO `poetry_quiz_questions` VALUES (503, 34, 5, '《忆江南》的作者是白居易。', '{}', '{\"text\": \"true\"}', '《忆江南》的作者确实是唐代诗人白居易。', 2, '[\"作者：白居易\"]', b'1', 'SYSTEM', '2025-11-06 15:53:27.499', 'SYSTEM', '2025-11-06 15:53:27.499');
INSERT INTO `poetry_quiz_questions` VALUES (504, 34, 5, '《忆江南》的首句是\'日出江花红胜火，春来江水绿如蓝。\'', '{}', '{\"text\": \"false\"}', '《忆江南》的首句是\'江南好，风景旧曾谙。\'', 3, '[\"首句：江南好\"]', b'1', 'SYSTEM', '2025-11-06 15:53:27.502', 'SYSTEM', '2025-11-06 15:53:27.502');
INSERT INTO `poetry_quiz_questions` VALUES (505, 34, 4, '请将以下诗句按照正确的顺序排列：\n1. 日出江花红胜火，春来江水绿如蓝。\n2. 江南好，风景旧曾谙。\n3. 能不忆江南？', '{}', '{\"text\": \"2, 1, 3\"}', '这是《忆江南》的完整诗句，按照首句、中间句、尾句的顺序排列。', 4, '[\"首句：江南好\"]', b'1', 'SYSTEM', '2025-11-06 15:53:27.505', 'SYSTEM', '2025-11-06 15:53:27.505');
INSERT INTO `poetry_quiz_questions` VALUES (506, 35, 3, '一道残阳铺水中，半江瑟瑟半江红。可怜九月初三夜，露似真珠月似弓。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"月似弓。\"]}', '{\"text\": \"月似弓。\"}', '这是《暮江吟》的最后一句，通过\"月似弓\"的比喻，描绘了夜晚江边的美丽景色。', 1, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 15:53:32.273', 'SYSTEM', '2025-11-06 15:53:32.273');
INSERT INTO `poetry_quiz_questions` VALUES (507, 35, 3, '一道残阳铺水中，半江瑟瑟半江红。可怜九月初三夜，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"露似真珠月似弓。\"]}', '{\"text\": \"露似真珠月似弓。\"}', '这是《暮江吟》的倒数第二句，通过\"露似真珠\"和\"月似弓\"的比喻，描绘了夜晚江边的美丽景色。', 2, '[\"倒数第二句\"]', b'1', 'SYSTEM', '2025-11-06 15:53:32.278', 'SYSTEM', '2025-11-06 15:53:32.278');
INSERT INTO `poetry_quiz_questions` VALUES (508, 35, 5, '《暮江吟》描绘了夜晚江边的美丽景色。', '{}', '{\"text\": \"true\"}', '《暮江吟》确实描绘了夜晚江边的美丽景色，通过\"露似真珠月似弓\"的比喻，展现了夜晚的美丽。', 3, '[\"描绘了夜晚江边的美丽景色\"]', b'1', 'SYSTEM', '2025-11-06 15:53:32.282', 'SYSTEM', '2025-11-06 15:53:32.282');
INSERT INTO `poetry_quiz_questions` VALUES (509, 35, 1, '《暮江吟》的作者是？', '{}', '{\"text\": \"白居易\"}', '《暮江吟》的作者是唐代诗人白居易。', 4, '[\"唐代诗人\"]', b'1', 'SYSTEM', '2025-11-06 15:53:32.287', 'SYSTEM', '2025-11-06 15:53:32.287');
INSERT INTO `poetry_quiz_questions` VALUES (510, 35, 4, '请将以下诗句按照原文顺序排序：\n1. 一道残阳铺水中\n2. 半江瑟瑟半江红\n3. 可怜九月初三夜\n4. 露似真珠月似弓。', '{}', '{\"text\": \"1234\"}', '《暮江吟》的原文顺序为：一道残阳铺水中，半江瑟瑟半江红。可怜九月初三夜，露似真珠月似弓。', 5, '[\"原文顺序\"]', b'1', 'SYSTEM', '2025-11-06 15:53:32.289', 'SYSTEM', '2025-11-06 15:53:32.289');
INSERT INTO `poetry_quiz_questions` VALUES (511, 36, 3, '红豆生南国，春来发几枝。愿君多采撷，此物最相思。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"相思\"]}', '{\"text\": \"相思\"}', '通过红豆表达对友人的思念之情。', 1, '[\"表达思念\"]', b'1', 'SYSTEM', '2025-11-06 15:53:36.311', 'SYSTEM', '2025-11-06 15:53:36.311');
INSERT INTO `poetry_quiz_questions` VALUES (512, 36, 3, '红豆生南国，春来发几枝。愿君多采撷，此物最相思。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"相思\"]}', '{\"text\": \"相思\"}', '通过红豆表达对友人的思念之情。', 1, '[\"表达思念\"]', b'1', 'SYSTEM', '2025-11-06 15:53:36.314', 'SYSTEM', '2025-11-06 15:53:36.314');
INSERT INTO `poetry_quiz_questions` VALUES (513, 36, 5, '红豆生长在阳光明媚的南方，每逢春天不知长多少新枝。希望思念的人儿多多采摘，因为它最能寄托相思之情。', '{}', '{\"text\": \"正确\"}', '解析正确，通过红豆表达对友人的思念之情。', 2, '[\"解析正确\"]', b'1', 'SYSTEM', '2025-11-06 15:53:36.316', 'SYSTEM', '2025-11-06 15:53:36.316');
INSERT INTO `poetry_quiz_questions` VALUES (514, 36, 5, '红豆生长在阳光明媚的南方，每逢春天不知长多少新枝。希望思念的人儿多多采摘，因为它最能寄托相思之情。', '{}', '{\"text\": \"正确\"}', '解析正确，通过红豆表达对友人的思念之情。', 2, '[\"解析正确\"]', b'1', 'SYSTEM', '2025-11-06 15:53:36.318', 'SYSTEM', '2025-11-06 15:53:36.318');
INSERT INTO `poetry_quiz_questions` VALUES (515, 36, 4, '红豆生长在阳光明媚的南方，每逢春天不知长多少新枝。希望思念的人儿多多采摘，因为它最能寄托相思之情。', '{}', '{\"text\": \"阳光明媚的南方\"}', '解析正确，通过红豆表达对友人的思念之情。', 3, '[\"阳光明媚的南方\"]', b'1', 'SYSTEM', '2025-11-06 15:53:36.321', 'SYSTEM', '2025-11-06 15:53:36.321');
INSERT INTO `poetry_quiz_questions` VALUES (516, 37, 3, '空山新雨后，天气晚来秋。明月松间照，清泉石上流。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"明月松间照。\"]}', '{\"text\": \"明月松间照。\"}', '这是《山居秋暝》的第二句，通过描绘明月照松间，表达秋夜的宁静。', 1, '[\"首字：明\"]', b'1', 'SYSTEM', '2025-11-06 15:53:41.127', 'SYSTEM', '2025-11-06 15:53:41.127');
INSERT INTO `poetry_quiz_questions` VALUES (517, 37, 3, '空山新雨后，天气晚来秋。明月松间照，清泉石上流。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"清泉石上流。\"]}', '{\"text\": \"清泉石上流。\"}', '这是《山居秋暝》的第三句，通过描绘清泉流过石上，表达山间景色的清幽。', 1, '[\"首字：清\"]', b'1', 'SYSTEM', '2025-11-06 15:53:41.130', 'SYSTEM', '2025-11-06 15:53:41.130');
INSERT INTO `poetry_quiz_questions` VALUES (518, 37, 5, '这首诗描绘了秋雨初晴后山村的景色。', '{}', '{\"text\": \"true\"}', '根据原文描述，这首诗描绘了秋雨初晴后山村的景色。', 3, '[\"描述：秋雨初晴后山村的景色\"]', b'1', 'SYSTEM', '2025-11-06 15:53:41.133', 'SYSTEM', '2025-11-06 15:53:41.133');
INSERT INTO `poetry_quiz_questions` VALUES (519, 37, 4, '请将以下诗句按照原文顺序排列：\n1. 明月松间照\n2. 清泉石上流\n3. 空山新雨后\n4. 天气晚来秋。', '{}', '{\"text\": \"3, 4, 1, 2\"}', '根据原文顺序，诗句应为：空山新雨后，天气晚来秋。明月松间照，清泉石上流。', 3, '[\"顺序：原文顺序\"]', b'1', 'SYSTEM', '2025-11-06 15:53:41.135', 'SYSTEM', '2025-11-06 15:53:41.135');
INSERT INTO `poetry_quiz_questions` VALUES (520, 37, 1, '这首诗表达了诗人怎样的情感？\nA. 思乡\nB. 悲秋\nC. 闲适\nD. 思念', '{}', '{\"text\": \"C\"}', '这首诗描绘了秋雨初晴后山村的景色，表达了诗人闲适的心情。', 5, '[\"情感：闲适\"]', b'1', 'SYSTEM', '2025-11-06 15:53:41.137', 'SYSTEM', '2025-11-06 15:53:41.137');
INSERT INTO `poetry_quiz_questions` VALUES (521, 38, 3, '单车欲问边，属国过居延。征蓬出汉塞，归雁入胡天。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"轻车简从将要去慰问边关\"]}', '{\"text\": \"轻车简从将要去慰问边关\"}', '这是《使至塞上》的首句，描述了诗人轻车简从去慰问边关的情景。', 1, '[\"首句\"]', b'1', 'SYSTEM', '2025-11-06 15:53:47.962', 'SYSTEM', '2025-11-06 15:53:47.962');
INSERT INTO `poetry_quiz_questions` VALUES (522, 38, 3, '征蓬出汉塞，归雁入胡天。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"像随风而去的蓬草一样出临边塞\"]}', '{\"text\": \"像随风而去的蓬草一样出临边塞\"}', '这是《使至塞上》的第二句，用比喻手法描绘了诗人出塞的情景。', 2, '[\"比喻手法\"]', b'1', 'SYSTEM', '2025-11-06 15:53:47.965', 'SYSTEM', '2025-11-06 15:53:47.965');
INSERT INTO `poetry_quiz_questions` VALUES (523, 38, 5, '《使至塞上》描绘了塞外奇特壮丽的风光。', '{}', '{\"text\": \"true\"}', '这首诗描绘了塞外奇特壮丽的风光，表达了诗人对边塞风光的赞美。', 3, '[\"描绘了塞外奇特壮丽的风光\"]', b'1', 'SYSTEM', '2025-11-06 15:53:47.967', 'SYSTEM', '2025-11-06 15:53:47.967');
INSERT INTO `poetry_quiz_questions` VALUES (524, 38, 4, '请将以下诗句按照原文顺序排序：\n1. 归雁入胡天\n2. 征蓬出汉塞\n3. 轻车简从将要去慰问边关\n4. 属国过居延', '{}', '{\"text\": \"3, 4, 2, 1\"}', '这是《使至塞上》的诗句顺序，按照原文顺序排列。', 4, '[\"原文顺序\"]', b'1', 'SYSTEM', '2025-11-06 15:53:47.969', 'SYSTEM', '2025-11-06 15:53:47.969');
INSERT INTO `poetry_quiz_questions` VALUES (525, 38, 1, '《使至塞上》的作者是？', '{}', '{\"text\": \"王维\"}', '《使至塞上》的作者是唐代诗人王维。', 5, '[\"唐代诗人\"]', b'1', 'SYSTEM', '2025-11-06 15:53:47.971', 'SYSTEM', '2025-11-06 15:53:47.971');
INSERT INTO `poetry_quiz_questions` VALUES (526, 39, 3, '空山不见人，但闻人语响。返景入深林，复照青苔上。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"但闻人语响。\"]}', '{\"text\": \"但闻人语响。\"}', '这是《鹿柴》的第二句，描述了山谷中只有人语声的景象。', 1, '[\"第二句\"]', b'1', 'SYSTEM', '2025-11-06 15:53:52.627', 'SYSTEM', '2025-11-06 15:53:52.627');
INSERT INTO `poetry_quiz_questions` VALUES (527, 39, 3, '空山不见人，但闻人语响。返景入深林，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"复照青苔上。\"]}', '{\"text\": \"复照青苔上。\"}', '这是《鹿柴》的最后一句，描述了落日的影晕照在青苔上的景象。', 1, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 15:53:52.630', 'SYSTEM', '2025-11-06 15:53:52.630');
INSERT INTO `poetry_quiz_questions` VALUES (528, 39, 5, '《鹿柴》描绘了鹿柴附近的空山深林在傍晚时分的幽静景色。', '{}', '{\"text\": \"true\"}', '解析中已经说明了这首诗描绘了鹿柴附近的空山深林在傍晚时分的幽静景色。', 2, '[\"描述内容\"]', b'1', 'SYSTEM', '2025-11-06 15:53:52.633', 'SYSTEM', '2025-11-06 15:53:52.633');
INSERT INTO `poetry_quiz_questions` VALUES (529, 39, 1, '《鹿柴》的作者是？', '{}', '{\"text\": \"王维\"}', '王维是唐代著名的诗人，这首诗的作者就是王维。', 3, '[\"唐代诗人\"]', b'1', 'SYSTEM', '2025-11-06 15:53:52.635', 'SYSTEM', '2025-11-06 15:53:52.635');
INSERT INTO `poetry_quiz_questions` VALUES (530, 39, 4, '请将以下诗句按照原文顺序排序：\n1. 空山不见人\n2. 但闻人语响\n3. 返景入深林\n4. 复照青苔上。', '{}', '{\"text\": \"1, 2, 3, 4\"}', '根据原文顺序，诗句的正确排序应该是1、2、3、4。', 4, '[\"原文顺序\"]', b'1', 'SYSTEM', '2025-11-06 15:53:52.637', 'SYSTEM', '2025-11-06 15:53:52.637');
INSERT INTO `poetry_quiz_questions` VALUES (531, 40, 3, '独坐幽篁里，弹琴复长啸。深林人不知，明月来相照。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"独坐幽篁里，弹琴复长啸。深林人不知，明月来相照。\"]}', '{\"text\": \"独坐幽篁里，弹琴复长啸。深林人不知，明月来相照。\"}', '这是《竹里馆》的原文，通过填空的方式检验对整首诗的记忆。', 1, '[\"首句：独坐幽篁里\"]', b'1', 'SYSTEM', '2025-11-06 15:53:58.017', 'SYSTEM', '2025-11-06 15:53:58.017');
INSERT INTO `poetry_quiz_questions` VALUES (532, 40, 1, '《竹里馆》中，诗人通过什么方式表达自己的情感？', '{\"options\": [\"弹琴\", \"长啸\", \"明月\"], \"optionCount\": 3}', '{\"text\": \"弹琴\"}', '通过弹琴和长啸，诗人表达了自己在幽静的竹林中享受自然、陶醉于音乐的悠闲生活。', 2, '[\"诗人通过弹琴和长啸表达情感\"]', b'1', 'SYSTEM', '2025-11-06 15:53:58.021', 'SYSTEM', '2025-11-06 15:53:58.021');
INSERT INTO `poetry_quiz_questions` VALUES (533, 40, 5, '《竹里馆》中，诗人独自一人在幽静的竹林中，周围没有人，因此他感到孤独。', '{}', '{\"text\": \"false\"}', '虽然诗人独自一人，但明月相伴，他并不感到孤独。', 3, '[\"诗人并不感到孤独\"]', b'1', 'SYSTEM', '2025-11-06 15:53:58.024', 'SYSTEM', '2025-11-06 15:53:58.024');
INSERT INTO `poetry_quiz_questions` VALUES (534, 40, 4, '请按照《竹里馆》的顺序排列以下诗句：\n1. 独坐幽篁里\n2. 弹琴复长啸\n3. 深林人不知\n4. 明月来相照', '{\"options\": [\"1\", \"2\", \"3\", \"4\"], \"optionCount\": 4}', '{\"text\": \"1234\"}', '根据《竹里馆》的原文顺序排列诗句。', 4, '[\"按照原文顺序排列诗句\"]', b'1', 'SYSTEM', '2025-11-06 15:53:58.026', 'SYSTEM', '2025-11-06 15:53:58.026');
INSERT INTO `poetry_quiz_questions` VALUES (535, 40, 2, '《竹里馆》中，诗人通过哪些方式来表现自己的情感？\nA. 弹琴\nB. 长啸\nC. 明月\nD. 独坐', '{\"options\": [\"A\", \"B\", \"C\", \"D\"], \"optionCount\": 4}', '{\"text\": \"ABCD\"}', '诗人通过弹琴、长啸、明月和独坐来表现自己的情感。', 5, '[\"诗人通过多种方式表达情感\"]', b'1', 'SYSTEM', '2025-11-06 15:53:58.028', 'SYSTEM', '2025-11-06 15:53:58.028');
INSERT INTO `poetry_quiz_questions` VALUES (536, 33, 3, '几处早莺争暖树，谁家新燕啄春泥。__________，浅草才能没马蹄。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"乱花渐欲迷人眼\"]}', '{\"text\": \"乱花渐欲迷人眼\"}', '这是《钱塘湖春行》中的诗句，描绘了早春西湖的美景。', 1, '[\"乱花\"]', b'1', 'SYSTEM', '2025-11-06 16:00:39.063', 'SYSTEM', '2025-11-06 16:00:39.063');
INSERT INTO `poetry_quiz_questions` VALUES (537, 33, 3, '水面初平云脚低，__________，几处早莺争暖树。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"孤山寺北贾亭西\"]}', '{\"text\": \"孤山寺北贾亭西\"}', '这是《钱塘湖春行》中的诗句，描绘了早春西湖的美景。', 1, '[\"孤山\"]', b'1', 'SYSTEM', '2025-11-06 16:00:39.079', 'SYSTEM', '2025-11-06 16:00:39.079');
INSERT INTO `poetry_quiz_questions` VALUES (538, 33, 5, '这首诗描绘了早春西湖的美景。', '{}', '{\"text\": \"true\"}', '根据原文和译文，这首诗确实描绘了早春西湖的美景。', 3, '[\"描绘美景\"]', b'1', 'SYSTEM', '2025-11-06 16:00:39.082', 'SYSTEM', '2025-11-06 16:00:39.082');
INSERT INTO `poetry_quiz_questions` VALUES (539, 33, 4, '请将以下诗句按照原文顺序排序：\n1. 孤山寺北贾亭西\n2. 几处早莺争暖树\n3. 水面初平云脚低\n4. 谁家新燕啄春泥\n5. 乱花渐欲迷人眼\n6. 浅草才能没马蹄', '{}', '[\"1\", \"3\", \"2\", \"4\", \"5\", \"6\"]', '根据原文顺序，正确排序为：1. 孤山寺北贾亭西，2. 水面初平云脚低，3. 几处早莺争暖树，4. 谁家新燕啄春泥，5. 乱花渐欲迷人眼，6. 浅草才能没马蹄。', 4, '[\"原文顺序\"]', b'1', 'SYSTEM', '2025-11-06 16:00:39.086', 'SYSTEM', '2025-11-06 16:00:39.086');
INSERT INTO `poetry_quiz_questions` VALUES (540, 33, 1, '这首诗的作者是？', '{}', '{\"text\": \"白居易\"}', '这首诗的作者是唐代诗人白居易。', 5, '[\"唐代诗人\"]', b'1', 'SYSTEM', '2025-11-06 16:00:39.088', 'SYSTEM', '2025-11-06 16:00:39.088');
INSERT INTO `poetry_quiz_questions` VALUES (541, 41, 3, '白日依山尽，黄河入海流。欲穷千里目，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"更上一层楼。\"]}', '{\"text\": \"更上一层楼。\"}', '这是《登鹳雀楼》的最后一句，通过登高望远，表达积极进取的精神。', 1, '[\"首字：欲\"]', b'1', 'SYSTEM', '2025-11-06 16:02:03.491', 'SYSTEM', '2025-11-06 16:02:03.491');
INSERT INTO `poetry_quiz_questions` VALUES (542, 41, 3, '床前明月光，疑是地上霜。举头望明月，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"低头思故乡。\"]}', '{\"text\": \"低头思故乡。\"}', '这是《静夜思》的最后一句，通过\"低头\"与\"举头\"的对比，表达思乡之情。', 1, '[\"首字：低\"]', b'1', 'SYSTEM', '2025-11-06 16:02:03.495', 'SYSTEM', '2025-11-06 16:02:03.495');
INSERT INTO `poetry_quiz_questions` VALUES (543, 41, 1, '《登鹳雀楼》的作者是？', '{}', '{\"text\": \"王之涣\"}', '《登鹳雀楼》的作者是唐代诗人王之涣。', 2, '[\"唐代诗人\"]', b'1', 'SYSTEM', '2025-11-06 16:02:03.498', 'SYSTEM', '2025-11-06 16:02:03.498');
INSERT INTO `poetry_quiz_questions` VALUES (544, 41, 5, '《登鹳雀楼》的诗句“白日依山尽，黄河入海流”描绘了夕阳西下的景象。', '{}', '{\"text\": \"true\"}', '《登鹳雀楼》的诗句“白日依山尽，黄河入海流”描绘了夕阳西下的景象。', 3, '[\"夕阳西下\"]', b'1', 'SYSTEM', '2025-11-06 16:02:03.501', 'SYSTEM', '2025-11-06 16:02:03.501');
INSERT INTO `poetry_quiz_questions` VALUES (545, 41, 4, '请将以下诗句按照正确的顺序排列：\n1. 白日依山尽\n2. 欲穷千里目\n3. 黄河入海流\n4. 更上一层楼。', '{}', '{\"text\": \"1, 3, 2, 4\"}', '《登鹳雀楼》的诗句按照正确的顺序排列应该是：白日依山尽，黄河入海流，欲穷千里目，更上一层楼。', 4, '[\"诗句顺序\"]', b'1', 'SYSTEM', '2025-11-06 16:02:03.505', 'SYSTEM', '2025-11-06 16:02:03.505');
INSERT INTO `poetry_quiz_questions` VALUES (546, 42, 3, '黄河远上白云间，一片孤城万仞山。羌笛何须怨杨柳，春风不度玉门关。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"玉门关\"]}', '{\"text\": \"玉门关\"}', '根据原文，最后一句提到玉门关，填空处应为玉门关。', 1, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 16:02:07.818', 'SYSTEM', '2025-11-06 16:02:07.818');
INSERT INTO `poetry_quiz_questions` VALUES (547, 42, 3, '黄河远上白云间，__________，一片孤城万仞山。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"一片孤城万仞山\"]}', '{\"text\": \"一片孤城万仞山\"}', '根据原文，第二句应填入一片孤城万仞山。', 2, '[\"第二句\"]', b'1', 'SYSTEM', '2025-11-06 16:02:07.821', 'SYSTEM', '2025-11-06 16:02:07.821');
INSERT INTO `poetry_quiz_questions` VALUES (548, 42, 5, '这首诗描绘了边塞地区荒凉辽阔的景象。', '{}', '{\"text\": \"true\"}', '根据解析，这首诗确实展现了边塞地区荒凉辽阔的景象。', 3, '[\"描绘景象\"]', b'1', 'SYSTEM', '2025-11-06 16:02:07.824', 'SYSTEM', '2025-11-06 16:02:07.824');
INSERT INTO `poetry_quiz_questions` VALUES (549, 42, 4, '请将以下诗句按照原文顺序排序：\n1. 羌笛何须怨杨柳\n2. 一片孤城万仞山\n3. 黄河远上白云间\n4. 春风不度玉门关', '{}', '{\"order\": [3, 1, 2, 4]}', '根据原文顺序，诗句应为黄河远上白云间，羌笛何须怨杨柳，一片孤城万仞山，春风不度玉门关。', 4, '[\"原文顺序\"]', b'1', 'SYSTEM', '2025-11-06 16:02:07.826', 'SYSTEM', '2025-11-06 16:02:07.826');
INSERT INTO `poetry_quiz_questions` VALUES (550, 42, 1, '这首诗的作者是？', '{}', '{\"text\": \"王之涣\"}', '根据学习内容，这首诗的作者是王之涣。', 5, '[\"作者\"]', b'1', 'SYSTEM', '2025-11-06 16:02:07.829', 'SYSTEM', '2025-11-06 16:02:07.829');
INSERT INTO `poetry_quiz_questions` VALUES (551, 43, 3, '床前明月光，疑是地上霜。举头望明月，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"低头思故乡\", \"低头思故乡。\"]}', '{\"text\": \"低头思故乡\"}', '这是《静夜思》的最后一句，通过\"低头\"与\"举头\"的对比，表达思乡之情。', 1, '[\"首字：低\"]', b'1', 'SYSTEM', '2025-11-06 16:02:12.311', 'SYSTEM', '2025-11-06 16:02:12.311');
INSERT INTO `poetry_quiz_questions` VALUES (552, 43, 3, '但使龙城飞将在，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"不教胡马度阴山\"]}', '{\"text\": \"不教胡马度阴山\"}', '出自《出塞》，表达对良将的渴望和对和平的向往。', 2, '[\"尾字：山\"]', b'1', 'SYSTEM', '2025-11-06 16:02:12.314', 'SYSTEM', '2025-11-06 16:02:12.314');
INSERT INTO `poetry_quiz_questions` VALUES (553, 43, 5, '《出塞》的作者是王昌龄。', '{}', '{\"text\": \"true\"}', '《出塞》的作者确实是王昌龄。', 3, '[]', b'1', 'SYSTEM', '2025-11-06 16:02:12.317', 'SYSTEM', '2025-11-06 16:02:12.317');
INSERT INTO `poetry_quiz_questions` VALUES (554, 43, 4, '请将以下诗句按照原文顺序排序：\n1. 但使龙城飞将在\n2. 万里长征人未还\n3. 秦时明月汉时关\n4. 不教胡马度阴山', '{}', '{\"text\": \"3214\"}', '根据原文顺序，正确排序为：秦时明月汉时关，万里长征人未还，但使龙城飞将在，不教胡马度阴山。', 4, '[]', b'1', 'SYSTEM', '2025-11-06 16:02:12.320', 'SYSTEM', '2025-11-06 16:02:12.320');
INSERT INTO `poetry_quiz_questions` VALUES (555, 43, 1, '《出塞》表达了诗人怎样的情感？\nA. 思乡\nB. 对和平的向往\nC. 对战争的厌恶\nD. 对良将的渴望', '{}', '{\"text\": \"D\"}', '《出塞》表达了诗人对良将的渴望和对和平的向往。', 5, '[\"主旨：渴望良将，向往和平\"]', b'1', 'SYSTEM', '2025-11-06 16:02:12.322', 'SYSTEM', '2025-11-06 16:02:12.322');
INSERT INTO `poetry_quiz_questions` VALUES (556, 44, 3, '床前明月光，疑是地上霜。举头望明月，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"低头思故乡\", \"低头思故乡。\"]}', '{\"text\": \"低头思故乡\"}', '这是《静夜思》的最后一句，通过\"低头\"与\"举头\"的对比，表达思乡之情。', 1, '[\"首字：低\"]', b'1', 'SYSTEM', '2025-11-06 16:02:16.559', 'SYSTEM', '2025-11-06 16:02:16.559');
INSERT INTO `poetry_quiz_questions` VALUES (557, 44, 3, '洛阳亲友如相问，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"一片冰心在玉壶\"]}', '{\"text\": \"一片冰心在玉壶\"}', '这是《芙蓉楼送辛渐》的最后一句，表明自己光明磊落、清廉自守的品格。', 2, '[\"首字：一\"]', b'1', 'SYSTEM', '2025-11-06 16:02:16.563', 'SYSTEM', '2025-11-06 16:02:16.563');
INSERT INTO `poetry_quiz_questions` VALUES (558, 44, 5, '《芙蓉楼送辛渐》的作者是王维。', '{}', '{\"text\": \"false\"}', '《芙蓉楼送辛渐》的作者是王昌龄，不是王维。', 3, '[\"作者：王昌龄\"]', b'1', 'SYSTEM', '2025-11-06 16:02:16.566', 'SYSTEM', '2025-11-06 16:02:16.566');
INSERT INTO `poetry_quiz_questions` VALUES (559, 44, 4, '请将以下诗句按照正确的顺序排列：\n1. 平明送客楚山孤\n2. 寒雨连江夜入吴\n3. 洛阳亲友如相问\n4. 一片冰心在玉壶', '{}', '{\"text\": \"2134\"}', '这是《芙蓉楼送辛渐》的诗句顺序，根据原文内容排列。', 4, '[\"顺序：2134\"]', b'1', 'SYSTEM', '2025-11-06 16:02:16.568', 'SYSTEM', '2025-11-06 16:02:16.568');
INSERT INTO `poetry_quiz_questions` VALUES (560, 44, 5, '《静夜思》的作者是李白。', '{}', '{\"text\": \"true\"}', '《静夜思》的作者确实是李白。', 5, '[\"作者：李白\"]', b'1', 'SYSTEM', '2025-11-06 16:02:16.571', 'SYSTEM', '2025-11-06 16:02:16.571');
INSERT INTO `poetry_quiz_questions` VALUES (561, 45, 3, '慈母手中线，游子身上衣。临行密密缝，意恐迟迟归。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"恐迟迟归。\"]}', '{\"text\": \"恐迟迟归。\"}', '这是《游子吟》的最后一句，通过\"密密缝\"与\"迟迟归\"的对比，表达母亲对儿子的关爱。', 1, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 16:02:20.959', 'SYSTEM', '2025-11-06 16:02:20.959');
INSERT INTO `poetry_quiz_questions` VALUES (562, 45, 3, '慈母手中线，游子身上衣。临行密密缝，意恐迟迟归。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"恐迟迟归。\"]}', '{\"text\": \"恐迟迟归。\"}', '这是《游子吟》的最后一句，通过\"密密缝\"与\"迟迟归\"的对比，表达母亲对儿子的关爱。', 1, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 16:02:20.962', 'SYSTEM', '2025-11-06 16:02:20.962');
INSERT INTO `poetry_quiz_questions` VALUES (563, 45, 5, '慈母手中线，游子身上衣。临行密密缝，意恐迟迟归。', '{}', '{\"text\": \"TRUE\"}', '这首诗歌颂了母爱的伟大与无私。', 2, '[\"主旨\"]', b'1', 'SYSTEM', '2025-11-06 16:02:20.965', 'SYSTEM', '2025-11-06 16:02:20.965');
INSERT INTO `poetry_quiz_questions` VALUES (564, 45, 4, '慈母手中线，游子身上衣。临行密密缝，意恐迟迟归。', '{\"order\": [\"慈母手中线\", \"游子身上衣\", \"临行密密缝\", \"意恐迟迟归\"]}', '{\"order\": [\"慈母手中线\", \"游子身上衣\", \"临行密密缝\", \"意恐迟迟归\"]}', '这首诗的顺序是按照诗句的顺序排列的。', 3, '[\"顺序\"]', b'1', 'SYSTEM', '2025-11-06 16:02:20.967', 'SYSTEM', '2025-11-06 16:02:20.967');
INSERT INTO `poetry_quiz_questions` VALUES (565, 45, 5, '慈母手中线，游子身上衣。临行密密缝，意恐迟迟归。', '{}', '{\"text\": \"TRUE\"}', '这首诗歌颂了母爱的伟大与无私。', 3, '[\"主旨\"]', b'1', 'SYSTEM', '2025-11-06 16:02:20.969', 'SYSTEM', '2025-11-06 16:02:20.969');
INSERT INTO `poetry_quiz_questions` VALUES (566, 46, 3, '锦瑟无端五十弦，一弦一柱思华年。庄生晓梦迷蝴蝶，望帝春心托杜鹃。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"思华年。\"]}', '{\"text\": \"思华年。\"}', '这是《锦瑟》的第二句，通过\"一弦一柱\"与\"思华年\"的对比，表达对青春年华的怀念。', 1, '[\"第二句\"]', b'1', 'SYSTEM', '2025-11-06 16:02:25.759', 'SYSTEM', '2025-11-06 16:02:25.759');
INSERT INTO `poetry_quiz_questions` VALUES (567, 46, 3, '锦瑟无端五十弦，一弦一柱思华年。庄生晓梦迷蝴蝶，望帝春心托杜鹃。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"思华年。\"]}', '{\"text\": \"思华年。\"}', '这是《锦瑟》的第二句，通过\"一弦一柱\"与\"思华年\"的对比，表达对青春年华的怀念。', 1, '[\"第二句\"]', b'1', 'SYSTEM', '2025-11-06 16:02:25.763', 'SYSTEM', '2025-11-06 16:02:25.763');
INSERT INTO `poetry_quiz_questions` VALUES (568, 46, 5, '锦瑟无端五十弦，一弦一柱思华年。庄生晓梦迷蝴蝶，望帝春心托杜鹃。', '{}', '{\"text\": \"True\"}', '这首诗运用了典故和象征手法表达人生感慨。', 3, '[\"典故\", \"象征手法\"]', b'1', 'SYSTEM', '2025-11-06 16:02:25.766', 'SYSTEM', '2025-11-06 16:02:25.766');
INSERT INTO `poetry_quiz_questions` VALUES (569, 46, 4, '锦瑟无端五十弦，一弦一柱思华年。庄生晓梦迷蝴蝶，望帝春心托杜鹃。', '{\"options\": [\"思华年。\", \"庄生晓梦迷蝴蝶。\", \"望帝春心托杜鹃。\", \"一弦一柱思华年。\"]}', '{\"text\": \"思华年。\"}', '这首诗的四句按照原文顺序排列。', 4, '[\"原文顺序\"]', b'1', 'SYSTEM', '2025-11-06 16:02:25.768', 'SYSTEM', '2025-11-06 16:02:25.768');
INSERT INTO `poetry_quiz_questions` VALUES (570, 46, 5, '锦瑟无端五十弦，一弦一柱思华年。庄生晓梦迷蝴蝶，望帝春心托杜鹃。', '{}', '{\"text\": \"True\"}', '这首诗运用了典故和象征手法表达人生感慨。', 5, '[\"典故\", \"象征手法\"]', b'1', 'SYSTEM', '2025-11-06 16:02:25.771', 'SYSTEM', '2025-11-06 16:02:25.771');
INSERT INTO `poetry_quiz_questions` VALUES (571, 47, 3, '君问归期未有期，巴山夜雨涨秋池。何当共剪西窗烛，却话巴山夜雨时。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"却话巴山夜雨时。\"]}', '{\"text\": \"却话巴山夜雨时。\"}', '这是《夜雨寄北》的最后一句，表达了诗人对妻子的思念和重逢的渴望。', 1, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 16:02:30.357', 'SYSTEM', '2025-11-06 16:02:30.357');
INSERT INTO `poetry_quiz_questions` VALUES (572, 47, 3, '君问归期未有期，巴山夜雨涨秋池。何当共剪西窗烛，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"却话巴山夜雨时。\"]}', '{\"text\": \"却话巴山夜雨时。\"}', '这是《夜雨寄北》的最后一句，表达了诗人对妻子的思念和重逢的渴望。', 2, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 16:02:30.361', 'SYSTEM', '2025-11-06 16:02:30.361');
INSERT INTO `poetry_quiz_questions` VALUES (573, 47, 5, '这首诗表达了诗人对妻子的思念和重逢的渴望。', '{}', '{\"text\": \"true\"}', '通过诗句内容可知，诗人表达了对妻子的思念和重逢的渴望。', 3, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 16:02:30.365', 'SYSTEM', '2025-11-06 16:02:30.365');
INSERT INTO `poetry_quiz_questions` VALUES (574, 47, 5, '这首诗的题目是《静夜思》。', '{}', '{\"text\": \"false\"}', '这首诗的题目是《夜雨寄北》，不是《静夜思》。', 4, '[\"题目\"]', b'1', 'SYSTEM', '2025-11-06 16:02:30.367', 'SYSTEM', '2025-11-06 16:02:30.367');
INSERT INTO `poetry_quiz_questions` VALUES (575, 47, 4, '请将以下诗句按照正确的顺序排列：\n1. 君问归期未有期\n2. 巴山夜雨涨秋池\n3. 何当共剪西窗烛\n4. 却话巴山夜雨时。', '{}', '{\"text\": \"1234\"}', '这首诗的正确顺序是：君问归期未有期，巴山夜雨涨秋池，何当共剪西窗烛，却话巴山夜雨时。', 5, '[\"正确顺序\"]', b'1', 'SYSTEM', '2025-11-06 16:02:30.370', 'SYSTEM', '2025-11-06 16:02:30.370');
INSERT INTO `poetry_quiz_questions` VALUES (576, 49, 3, '夕阳无限好，只是近________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"黄昏\"]}', '{\"text\": \"黄昏\"}', '这是《登乐游原》的最后一句，表达了对美好时光的感慨。', 1, '[\"首字：昏\"]', b'1', 'SYSTEM', '2025-11-06 16:02:54.256', 'SYSTEM', '2025-11-06 16:02:54.256');
INSERT INTO `poetry_quiz_questions` VALUES (577, 49, 3, '向晚意不适，驱车登古________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"原\"]}', '{\"text\": \"原\"}', '这是《登乐游原》的第二句，描述了作者登高望远的情景。', 1, '[\"首字：原\"]', b'1', 'SYSTEM', '2025-11-06 16:02:54.259', 'SYSTEM', '2025-11-06 16:02:54.259');
INSERT INTO `poetry_quiz_questions` VALUES (578, 49, 5, '这首诗表达了作者对美好时光的感慨。', '{}', '{\"text\": \"true\"}', '通过诗句中的“夕阳无限好，只是近黄昏”可以推断出作者对美好时光的感慨。', 3, '[\"主旨：感慨时光流逝\"]', b'1', 'SYSTEM', '2025-11-06 16:02:54.262', 'SYSTEM', '2025-11-06 16:02:54.262');
INSERT INTO `poetry_quiz_questions` VALUES (579, 49, 4, '请将以下诗句按照原文顺序排序：\n1. 向晚意不适\n2. 驱车登古原\n3. 夕阳无限好\n4. 只是近黄昏', '{}', '{\"order\": [1, 2, 3, 4]}', '根据原文顺序，诗句应为：向晚意不适，驱车登古原。夕阳无限好，只是近黄昏。', 4, '[\"顺序：原文\"]', b'1', 'SYSTEM', '2025-11-06 16:02:54.264', 'SYSTEM', '2025-11-06 16:02:54.264');
INSERT INTO `poetry_quiz_questions` VALUES (580, 49, 1, '这首诗的作者是？\nA. 李商隐\nB. 杜甫\nC. 白居易\nD. 李白', '{}', '{\"text\": \"A\"}', '这首诗的作者是唐代诗人李商隐。', 5, '[\"作者：李商隐\"]', b'1', 'SYSTEM', '2025-11-06 16:02:54.267', 'SYSTEM', '2025-11-06 16:02:54.267');
INSERT INTO `poetry_quiz_questions` VALUES (581, 50, 3, '云母屏风烛影深，长河渐落晓星沉。嫦娥应悔偷灵药，碧海青天夜夜心。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"碧海青天夜夜心。\"]}', '{\"text\": \"碧海青天夜夜心。\"}', '这是《嫦娥》的最后一句，通过\"悔偷灵药\"与\"夜夜心\"的对比，表达诗人复杂的人生感慨。', 1, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 16:02:59.297', 'SYSTEM', '2025-11-06 16:02:59.297');
INSERT INTO `poetry_quiz_questions` VALUES (582, 50, 3, '云母屏风烛影深，__________，嫦娥应悔偷灵药，碧海青天夜夜心。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"长河渐落晓星沉。\"]}', '{\"text\": \"长河渐落晓星沉。\"}', '这是《嫦娥》的第二句，通过\"烛影深\"与\"长河渐落晓星沉\"的对比，表达诗人复杂的人生感慨。', 2, '[\"第二句\"]', b'1', 'SYSTEM', '2025-11-06 16:02:59.301', 'SYSTEM', '2025-11-06 16:02:59.301');
INSERT INTO `poetry_quiz_questions` VALUES (583, 50, 5, '这首诗的作者是李商隐。', '{}', '{\"text\": \"true\"}', '这首诗的作者确实是唐代诗人李商隐。', 3, '[]', b'1', 'SYSTEM', '2025-11-06 16:02:59.305', 'SYSTEM', '2025-11-06 16:02:59.305');
INSERT INTO `poetry_quiz_questions` VALUES (584, 50, 4, '请将以下诗句按照原文顺序排序：\n1. 长河渐落晓星沉。\n2. 云母屏风烛影深。\n3. 嫦娥应悔偷灵药。\n4. 碧海青天夜夜心。', '{}', '{\"order\": [2, 1, 3, 4]}', '这首诗的原文顺序是：云母屏风烛影深，长河渐落晓星沉，嫦娥应悔偷灵药，碧海青天夜夜心。', 4, '[]', b'1', 'SYSTEM', '2025-11-06 16:02:59.307', 'SYSTEM', '2025-11-06 16:02:59.307');
INSERT INTO `poetry_quiz_questions` VALUES (585, 50, 1, '这首诗表达了诗人怎样的情感？\nA. 思乡之情\nB. 复杂的人生感慨\nC. 对爱情的向往\nD. 对自然的热爱', '{}', '{\"text\": \"B\"}', '这首诗通过描写嫦娥的孤独和悔恨，表达了诗人复杂的人生感慨。', 5, '[\"诗人复杂的人生感慨\"]', b'1', 'SYSTEM', '2025-11-06 16:02:59.310', 'SYSTEM', '2025-11-06 16:02:59.310');
INSERT INTO `poetry_quiz_questions` VALUES (586, 48, 3, '相见时难别亦难，东风无力百花残。春蚕到死丝方尽，蜡炬成灰泪始干。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"泪始干\"]}', '{\"text\": \"泪始干\"}', '这是《无题》的最后一句，通过\"泪始干\"与前句的对比，表达至死不渝的爱情。', 1, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 16:08:06.180', 'SYSTEM', '2025-11-06 16:08:06.180');
INSERT INTO `poetry_quiz_questions` VALUES (587, 48, 5, '春蚕到死丝方尽，蜡炬成灰泪始干。这句诗表达了什么情感？', '{}', '{\"text\": \"TRUE\"}', '这句诗表达了至死不渝的爱情。', 3, '[\"至死不渝的爱情\"]', b'1', 'SYSTEM', '2025-11-06 16:08:06.195', 'SYSTEM', '2025-11-06 16:08:06.195');
INSERT INTO `poetry_quiz_questions` VALUES (588, 48, 3, '相见时难别亦难，东风无力百花残。春蚕到死丝方尽，蜡炬成灰泪始干。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"泪始干\"]}', '{\"text\": \"泪始干\"}', '这是《无题》的最后一句，通过\"泪始干\"与前句的对比，表达至死不渝的爱情。', 1, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 16:09:34.634', 'SYSTEM', '2025-11-06 16:09:34.634');
INSERT INTO `poetry_quiz_questions` VALUES (589, 48, 5, '春蚕到死丝方尽，蜡炬成灰泪始干。这句诗表达了什么情感？', '{}', '{\"text\": \"TRUE\"}', '这句诗表达了至死不渝的爱情。', 3, '[\"至死不渝的爱情\"]', b'1', 'SYSTEM', '2025-11-06 16:09:34.638', 'SYSTEM', '2025-11-06 16:09:34.638');
INSERT INTO `poetry_quiz_questions` VALUES (590, 41, 3, '白日依山尽，黄河入海流。欲穷千里目，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"更上一层楼。\"]}', '{\"text\": \"更上一层楼。\"}', '这是《登鹳雀楼》的最后一句，通过登高望远，表达积极进取的精神。', 1, '[\"首字：欲\"]', b'1', 'SYSTEM', '2025-11-06 16:10:30.870', 'SYSTEM', '2025-11-06 16:10:30.870');
INSERT INTO `poetry_quiz_questions` VALUES (591, 41, 3, '床前明月光，疑是地上霜。举头望明月，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"低头思故乡。\"]}', '{\"text\": \"低头思故乡。\"}', '这是《静夜思》的最后一句，通过\"低头\"与\"举头\"的对比，表达思乡之情。', 1, '[\"首字：低\"]', b'1', 'SYSTEM', '2025-11-06 16:10:30.875', 'SYSTEM', '2025-11-06 16:10:30.875');
INSERT INTO `poetry_quiz_questions` VALUES (592, 41, 1, '《登鹳雀楼》的作者是？', '{}', '{\"text\": \"王之涣\"}', '《登鹳雀楼》的作者是唐代诗人王之涣。', 2, '[\"唐代诗人\"]', b'1', 'SYSTEM', '2025-11-06 16:10:30.880', 'SYSTEM', '2025-11-06 16:10:30.880');
INSERT INTO `poetry_quiz_questions` VALUES (593, 41, 5, '《登鹳雀楼》的诗句“白日依山尽，黄河入海流”描绘了夕阳西下的景象。', '{}', '{\"text\": \"true\"}', '《登鹳雀楼》的诗句“白日依山尽，黄河入海流”描绘了夕阳西下的景象。', 3, '[\"夕阳西下\"]', b'1', 'SYSTEM', '2025-11-06 16:10:30.884', 'SYSTEM', '2025-11-06 16:10:30.884');
INSERT INTO `poetry_quiz_questions` VALUES (594, 41, 4, '请将以下诗句按照正确的顺序排列：\n1. 白日依山尽\n2. 欲穷千里目\n3. 黄河入海流\n4. 更上一层楼。', '{}', '{\"text\": \"1, 3, 2, 4\"}', '《登鹳雀楼》的诗句按照正确的顺序排列应该是：白日依山尽，黄河入海流，欲穷千里目，更上一层楼。', 4, '[\"诗句顺序\"]', b'1', 'SYSTEM', '2025-11-06 16:10:30.889', 'SYSTEM', '2025-11-06 16:10:30.889');
INSERT INTO `poetry_quiz_questions` VALUES (595, 42, 3, '黄河远上白云间，一片孤城万仞山。羌笛何须怨杨柳，春风不度玉门关。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"玉门关\"]}', '{\"text\": \"玉门关\"}', '根据原文，最后一句提到玉门关，填空处应为玉门关。', 1, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 16:10:35.241', 'SYSTEM', '2025-11-06 16:10:35.241');
INSERT INTO `poetry_quiz_questions` VALUES (596, 42, 3, '黄河远上白云间，__________，一片孤城万仞山。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"一片孤城万仞山\"]}', '{\"text\": \"一片孤城万仞山\"}', '根据原文，第二句应填入一片孤城万仞山。', 2, '[\"第二句\"]', b'1', 'SYSTEM', '2025-11-06 16:10:35.245', 'SYSTEM', '2025-11-06 16:10:35.245');
INSERT INTO `poetry_quiz_questions` VALUES (597, 42, 5, '这首诗描绘了边塞地区荒凉辽阔的景象。', '{}', '{\"text\": \"true\"}', '根据解析，这首诗确实展现了边塞地区荒凉辽阔的景象。', 3, '[\"描绘景象\"]', b'1', 'SYSTEM', '2025-11-06 16:10:35.248', 'SYSTEM', '2025-11-06 16:10:35.248');
INSERT INTO `poetry_quiz_questions` VALUES (598, 42, 4, '请将以下诗句按照原文顺序排序：\n1. 羌笛何须怨杨柳\n2. 一片孤城万仞山\n3. 黄河远上白云间\n4. 春风不度玉门关', '{}', '{\"order\": [3, 1, 2, 4]}', '根据原文顺序，诗句应为黄河远上白云间，羌笛何须怨杨柳，一片孤城万仞山，春风不度玉门关。', 4, '[\"原文顺序\"]', b'1', 'SYSTEM', '2025-11-06 16:10:35.251', 'SYSTEM', '2025-11-06 16:10:35.251');
INSERT INTO `poetry_quiz_questions` VALUES (599, 42, 1, '这首诗的作者是？', '{}', '{\"text\": \"王之涣\"}', '根据学习内容，这首诗的作者是王之涣。', 5, '[\"作者\"]', b'1', 'SYSTEM', '2025-11-06 16:10:35.254', 'SYSTEM', '2025-11-06 16:10:35.254');
INSERT INTO `poetry_quiz_questions` VALUES (600, 43, 3, '床前明月光，疑是地上霜。举头望明月，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"低头思故乡\", \"低头思故乡。\"]}', '{\"text\": \"低头思故乡\"}', '这是《静夜思》的最后一句，通过\"低头\"与\"举头\"的对比，表达思乡之情。', 1, '[\"首字：低\"]', b'1', 'SYSTEM', '2025-11-06 16:10:39.727', 'SYSTEM', '2025-11-06 16:10:39.727');
INSERT INTO `poetry_quiz_questions` VALUES (601, 43, 3, '但使龙城飞将在，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"不教胡马度阴山\"]}', '{\"text\": \"不教胡马度阴山\"}', '出自《出塞》，表达对良将的渴望和对和平的向往。', 2, '[\"尾字：山\"]', b'1', 'SYSTEM', '2025-11-06 16:10:39.731', 'SYSTEM', '2025-11-06 16:10:39.731');
INSERT INTO `poetry_quiz_questions` VALUES (602, 43, 5, '《出塞》的作者是王昌龄。', '{}', '{\"text\": \"true\"}', '《出塞》的作者确实是王昌龄。', 3, '[]', b'1', 'SYSTEM', '2025-11-06 16:10:39.734', 'SYSTEM', '2025-11-06 16:10:39.734');
INSERT INTO `poetry_quiz_questions` VALUES (603, 43, 4, '请将以下诗句按照原文顺序排序：\n1. 但使龙城飞将在\n2. 万里长征人未还\n3. 秦时明月汉时关\n4. 不教胡马度阴山', '{}', '{\"text\": \"3214\"}', '根据原文顺序，正确排序为：秦时明月汉时关，万里长征人未还，但使龙城飞将在，不教胡马度阴山。', 4, '[]', b'1', 'SYSTEM', '2025-11-06 16:10:39.737', 'SYSTEM', '2025-11-06 16:10:39.737');
INSERT INTO `poetry_quiz_questions` VALUES (604, 43, 1, '《出塞》表达了诗人怎样的情感？\nA. 思乡\nB. 对和平的向往\nC. 对战争的厌恶\nD. 对良将的渴望', '{}', '{\"text\": \"D\"}', '《出塞》表达了诗人对良将的渴望和对和平的向往。', 5, '[\"主旨：渴望良将，向往和平\"]', b'1', 'SYSTEM', '2025-11-06 16:10:39.740', 'SYSTEM', '2025-11-06 16:10:39.740');
INSERT INTO `poetry_quiz_questions` VALUES (605, 44, 3, '床前明月光，疑是地上霜。举头望明月，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"低头思故乡\", \"低头思故乡。\"]}', '{\"text\": \"低头思故乡\"}', '这是《静夜思》的最后一句，通过\"低头\"与\"举头\"的对比，表达思乡之情。', 1, '[\"首字：低\"]', b'1', 'SYSTEM', '2025-11-06 16:10:43.961', 'SYSTEM', '2025-11-06 16:10:43.961');
INSERT INTO `poetry_quiz_questions` VALUES (606, 44, 3, '洛阳亲友如相问，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"一片冰心在玉壶\"]}', '{\"text\": \"一片冰心在玉壶\"}', '这是《芙蓉楼送辛渐》的最后一句，表明自己光明磊落、清廉自守的品格。', 2, '[\"首字：一\"]', b'1', 'SYSTEM', '2025-11-06 16:10:43.966', 'SYSTEM', '2025-11-06 16:10:43.966');
INSERT INTO `poetry_quiz_questions` VALUES (607, 44, 5, '《芙蓉楼送辛渐》的作者是王维。', '{}', '{\"text\": \"false\"}', '《芙蓉楼送辛渐》的作者是王昌龄，不是王维。', 3, '[\"作者：王昌龄\"]', b'1', 'SYSTEM', '2025-11-06 16:10:43.970', 'SYSTEM', '2025-11-06 16:10:43.970');
INSERT INTO `poetry_quiz_questions` VALUES (608, 44, 4, '请将以下诗句按照正确的顺序排列：\n1. 平明送客楚山孤\n2. 寒雨连江夜入吴\n3. 洛阳亲友如相问\n4. 一片冰心在玉壶', '{}', '{\"text\": \"2134\"}', '这是《芙蓉楼送辛渐》的诗句顺序，根据原文内容排列。', 4, '[\"顺序：2134\"]', b'1', 'SYSTEM', '2025-11-06 16:10:43.972', 'SYSTEM', '2025-11-06 16:10:43.972');
INSERT INTO `poetry_quiz_questions` VALUES (609, 44, 5, '《静夜思》的作者是李白。', '{}', '{\"text\": \"true\"}', '《静夜思》的作者确实是李白。', 5, '[\"作者：李白\"]', b'1', 'SYSTEM', '2025-11-06 16:10:43.974', 'SYSTEM', '2025-11-06 16:10:43.974');
INSERT INTO `poetry_quiz_questions` VALUES (610, 45, 3, '慈母手中线，游子身上衣。临行密密缝，意恐迟迟归。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"恐迟迟归。\"]}', '{\"text\": \"恐迟迟归。\"}', '这是《游子吟》的最后一句，通过\"密密缝\"与\"迟迟归\"的对比，表达母亲对儿子的关爱。', 1, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 16:10:48.365', 'SYSTEM', '2025-11-06 16:10:48.365');
INSERT INTO `poetry_quiz_questions` VALUES (611, 45, 5, '慈母手中线，游子身上衣。临行密密缝，意恐迟迟归。', '{}', '{\"text\": \"TRUE\"}', '这首诗歌颂了母爱的伟大与无私。', 2, '[\"主旨\"]', b'1', 'SYSTEM', '2025-11-06 16:10:48.369', 'SYSTEM', '2025-11-06 16:10:48.369');
INSERT INTO `poetry_quiz_questions` VALUES (612, 45, 4, '慈母手中线，游子身上衣。临行密密缝，意恐迟迟归。', '{\"order\": [\"慈母手中线\", \"游子身上衣\", \"临行密密缝\", \"意恐迟迟归\"]}', '{\"order\": [\"慈母手中线\", \"游子身上衣\", \"临行密密缝\", \"意恐迟迟归\"]}', '这首诗的顺序是按照诗句的顺序排列的。', 3, '[\"顺序\"]', b'1', 'SYSTEM', '2025-11-06 16:10:48.371', 'SYSTEM', '2025-11-06 16:10:48.371');
INSERT INTO `poetry_quiz_questions` VALUES (613, 46, 3, '锦瑟无端五十弦，一弦一柱思华年。庄生晓梦迷蝴蝶，望帝春心托杜鹃。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"思华年。\"]}', '{\"text\": \"思华年。\"}', '这是《锦瑟》的第二句，通过\"一弦一柱\"与\"思华年\"的对比，表达对青春年华的怀念。', 1, '[\"第二句\"]', b'1', 'SYSTEM', '2025-11-06 16:10:53.151', 'SYSTEM', '2025-11-06 16:10:53.151');
INSERT INTO `poetry_quiz_questions` VALUES (614, 46, 5, '锦瑟无端五十弦，一弦一柱思华年。庄生晓梦迷蝴蝶，望帝春心托杜鹃。', '{}', '{\"text\": \"True\"}', '这首诗运用了典故和象征手法表达人生感慨。', 3, '[\"典故\", \"象征手法\"]', b'1', 'SYSTEM', '2025-11-06 16:10:53.154', 'SYSTEM', '2025-11-06 16:10:53.154');
INSERT INTO `poetry_quiz_questions` VALUES (615, 46, 4, '锦瑟无端五十弦，一弦一柱思华年。庄生晓梦迷蝴蝶，望帝春心托杜鹃。', '{\"options\": [\"思华年。\", \"庄生晓梦迷蝴蝶。\", \"望帝春心托杜鹃。\", \"一弦一柱思华年。\"]}', '{\"text\": \"思华年。\"}', '这首诗的四句按照原文顺序排列。', 4, '[\"原文顺序\"]', b'1', 'SYSTEM', '2025-11-06 16:10:53.157', 'SYSTEM', '2025-11-06 16:10:53.157');
INSERT INTO `poetry_quiz_questions` VALUES (616, 47, 3, '君问归期未有期，巴山夜雨涨秋池。何当共剪西窗烛，却话巴山夜雨时。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"却话巴山夜雨时。\"]}', '{\"text\": \"却话巴山夜雨时。\"}', '这是《夜雨寄北》的最后一句，表达了诗人对妻子的思念和重逢的渴望。', 1, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 16:10:57.936', 'SYSTEM', '2025-11-06 16:10:57.936');
INSERT INTO `poetry_quiz_questions` VALUES (617, 47, 3, '君问归期未有期，巴山夜雨涨秋池。何当共剪西窗烛，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"却话巴山夜雨时。\"]}', '{\"text\": \"却话巴山夜雨时。\"}', '这是《夜雨寄北》的最后一句，表达了诗人对妻子的思念和重逢的渴望。', 2, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 16:10:57.940', 'SYSTEM', '2025-11-06 16:10:57.940');
INSERT INTO `poetry_quiz_questions` VALUES (618, 47, 5, '这首诗表达了诗人对妻子的思念和重逢的渴望。', '{}', '{\"text\": \"true\"}', '通过诗句内容可知，诗人表达了对妻子的思念和重逢的渴望。', 3, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 16:10:57.943', 'SYSTEM', '2025-11-06 16:10:57.943');
INSERT INTO `poetry_quiz_questions` VALUES (619, 47, 5, '这首诗的题目是《静夜思》。', '{}', '{\"text\": \"false\"}', '这首诗的题目是《夜雨寄北》，不是《静夜思》。', 4, '[\"题目\"]', b'1', 'SYSTEM', '2025-11-06 16:10:57.945', 'SYSTEM', '2025-11-06 16:10:57.945');
INSERT INTO `poetry_quiz_questions` VALUES (620, 47, 4, '请将以下诗句按照正确的顺序排列：\n1. 君问归期未有期\n2. 巴山夜雨涨秋池\n3. 何当共剪西窗烛\n4. 却话巴山夜雨时。', '{}', '{\"text\": \"1234\"}', '这首诗的正确顺序是：君问归期未有期，巴山夜雨涨秋池，何当共剪西窗烛，却话巴山夜雨时。', 5, '[\"正确顺序\"]', b'1', 'SYSTEM', '2025-11-06 16:10:57.947', 'SYSTEM', '2025-11-06 16:10:57.947');
INSERT INTO `poetry_quiz_questions` VALUES (621, 49, 3, '夕阳无限好，只是近________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"黄昏\"]}', '{\"text\": \"黄昏\"}', '这是《登乐游原》的最后一句，表达了对美好时光的感慨。', 1, '[\"首字：昏\"]', b'1', 'SYSTEM', '2025-11-06 16:11:22.708', 'SYSTEM', '2025-11-06 16:11:22.708');
INSERT INTO `poetry_quiz_questions` VALUES (622, 49, 3, '向晚意不适，驱车登古________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"原\"]}', '{\"text\": \"原\"}', '这是《登乐游原》的第二句，描述了作者登高望远的情景。', 1, '[\"首字：原\"]', b'1', 'SYSTEM', '2025-11-06 16:11:22.712', 'SYSTEM', '2025-11-06 16:11:22.712');
INSERT INTO `poetry_quiz_questions` VALUES (623, 49, 5, '这首诗表达了作者对美好时光的感慨。', '{}', '{\"text\": \"true\"}', '通过诗句中的“夕阳无限好，只是近黄昏”可以推断出作者对美好时光的感慨。', 3, '[\"主旨：感慨时光流逝\"]', b'1', 'SYSTEM', '2025-11-06 16:11:22.715', 'SYSTEM', '2025-11-06 16:11:22.715');
INSERT INTO `poetry_quiz_questions` VALUES (624, 49, 4, '请将以下诗句按照原文顺序排序：\n1. 向晚意不适\n2. 驱车登古原\n3. 夕阳无限好\n4. 只是近黄昏', '{}', '{\"order\": [1, 2, 3, 4]}', '根据原文顺序，诗句应为：向晚意不适，驱车登古原。夕阳无限好，只是近黄昏。', 4, '[\"顺序：原文\"]', b'1', 'SYSTEM', '2025-11-06 16:11:22.718', 'SYSTEM', '2025-11-06 16:11:22.718');
INSERT INTO `poetry_quiz_questions` VALUES (625, 49, 1, '这首诗的作者是？\nA. 李商隐\nB. 杜甫\nC. 白居易\nD. 李白', '{}', '{\"text\": \"A\"}', '这首诗的作者是唐代诗人李商隐。', 5, '[\"作者：李商隐\"]', b'1', 'SYSTEM', '2025-11-06 16:11:22.720', 'SYSTEM', '2025-11-06 16:11:22.720');
INSERT INTO `poetry_quiz_questions` VALUES (626, 50, 3, '云母屏风烛影深，长河渐落晓星沉。嫦娥应悔偷灵药，碧海青天夜夜心。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"碧海青天夜夜心。\"]}', '{\"text\": \"碧海青天夜夜心。\"}', '这是《嫦娥》的最后一句，通过\"悔偷灵药\"与\"夜夜心\"的对比，表达诗人复杂的人生感慨。', 1, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 16:11:27.854', 'SYSTEM', '2025-11-06 16:11:27.854');
INSERT INTO `poetry_quiz_questions` VALUES (627, 50, 3, '云母屏风烛影深，__________，嫦娥应悔偷灵药，碧海青天夜夜心。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"长河渐落晓星沉。\"]}', '{\"text\": \"长河渐落晓星沉。\"}', '这是《嫦娥》的第二句，通过\"烛影深\"与\"长河渐落晓星沉\"的对比，表达诗人复杂的人生感慨。', 2, '[\"第二句\"]', b'1', 'SYSTEM', '2025-11-06 16:11:27.859', 'SYSTEM', '2025-11-06 16:11:27.859');
INSERT INTO `poetry_quiz_questions` VALUES (628, 50, 5, '这首诗的作者是李商隐。', '{}', '{\"text\": \"true\"}', '这首诗的作者确实是唐代诗人李商隐。', 3, '[]', b'1', 'SYSTEM', '2025-11-06 16:11:27.863', 'SYSTEM', '2025-11-06 16:11:27.863');
INSERT INTO `poetry_quiz_questions` VALUES (629, 50, 4, '请将以下诗句按照原文顺序排序：\n1. 长河渐落晓星沉。\n2. 云母屏风烛影深。\n3. 嫦娥应悔偷灵药。\n4. 碧海青天夜夜心。', '{}', '{\"order\": [2, 1, 3, 4]}', '这首诗的原文顺序是：云母屏风烛影深，长河渐落晓星沉，嫦娥应悔偷灵药，碧海青天夜夜心。', 4, '[]', b'1', 'SYSTEM', '2025-11-06 16:11:27.865', 'SYSTEM', '2025-11-06 16:11:27.865');
INSERT INTO `poetry_quiz_questions` VALUES (630, 50, 1, '这首诗表达了诗人怎样的情感？\nA. 思乡之情\nB. 复杂的人生感慨\nC. 对爱情的向往\nD. 对自然的热爱', '{}', '{\"text\": \"B\"}', '这首诗通过描写嫦娥的孤独和悔恨，表达了诗人复杂的人生感慨。', 5, '[\"诗人复杂的人生感慨\"]', b'1', 'SYSTEM', '2025-11-06 16:11:27.867', 'SYSTEM', '2025-11-06 16:11:27.867');
INSERT INTO `poetry_quiz_questions` VALUES (631, 51, 3, '清明时节雨纷纷，路上行人欲断魂。借问酒家何处有，牧童遥指杏花村。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"杏花村\"]}', '{\"text\": \"杏花村\"}', '这是《清明》的最后一句，描述了清明时节的景象。', 1, '[\"地点：杏花村\"]', b'1', 'SYSTEM', '2025-11-06 16:12:14.387', 'SYSTEM', '2025-11-06 16:12:14.387');
INSERT INTO `poetry_quiz_questions` VALUES (632, 51, 5, '清明时节雨纷纷，路上行人欲断魂。借问酒家何处有，牧童遥指杏花村。', '{}', '{\"text\": \"TRUE\"}', '这首诗描绘了清明时节的景象，表达了诗人对家乡的思念。', 2, '[\"情感：思念\"]', b'1', 'SYSTEM', '2025-11-06 16:12:14.391', 'SYSTEM', '2025-11-06 16:12:14.391');
INSERT INTO `poetry_quiz_questions` VALUES (633, 51, 4, '清明时节雨纷纷，路上行人欲断魂。借问酒家何处有，牧童遥指杏花村。', '{}', '{\"text\": \"清明时节雨纷纷，路上行人欲断魂。借问酒家何处有，牧童遥指杏花村。\"}', '这首诗描绘了清明时节的景象，表达了诗人对家乡的思念。', 4, '[\"顺序：诗的顺序\"]', b'1', 'SYSTEM', '2025-11-06 16:12:14.393', 'SYSTEM', '2025-11-06 16:12:14.393');
INSERT INTO `poetry_quiz_questions` VALUES (634, 52, 3, '千里莺啼绿映红，水村山郭酒旗风。南朝四百八十寺，多少楼台烟雨中。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"多少楼台烟雨中。\"]}', '{\"text\": \"多少楼台烟雨中。\"}', '这是《江南春》的最后一句，描述了江南的美丽景色。', 1, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 16:12:18.887', 'SYSTEM', '2025-11-06 16:12:18.887');
INSERT INTO `poetry_quiz_questions` VALUES (635, 52, 3, '千里莺啼绿映红，水村山郭酒旗风。南朝四百八十寺，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"多少楼台烟雨中。\"]}', '{\"text\": \"多少楼台烟雨中。\"}', '这是《江南春》的最后一句，描述了江南的美丽景色。', 2, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 16:12:18.891', 'SYSTEM', '2025-11-06 16:12:18.891');
INSERT INTO `poetry_quiz_questions` VALUES (636, 52, 1, '《江南春》中，描述了江南美丽景色的诗句是？', '{}', '{\"text\": \"千里莺啼绿映红，水村山郭酒旗风。\"}', '这首诗的前两句描述了江南的美丽景色。', 3, '[\"前两句\"]', b'1', 'SYSTEM', '2025-11-06 16:12:18.894', 'SYSTEM', '2025-11-06 16:12:18.894');
INSERT INTO `poetry_quiz_questions` VALUES (637, 52, 5, '《江南春》中，\'南朝四百八十寺\'描述了江南的美丽景色。', '{}', '{\"text\": \"false\"}', '这句诗描述的是南朝时期遗留下来的寺庙数量，而不是景色。', 4, '[\"描述的是寺庙数量\"]', b'1', 'SYSTEM', '2025-11-06 16:12:18.896', 'SYSTEM', '2025-11-06 16:12:18.896');
INSERT INTO `poetry_quiz_questions` VALUES (638, 52, 4, '请将以下诗句按照出现的顺序排列：\n1. 千里莺啼绿映红\n2. 水村山郭酒旗风\n3. 南朝四百八十寺\n4. 多少楼台烟雨中。', '{}', '{\"order\": [1, 2, 3, 4]}', '这是《江南春》的诗句顺序，描述了江南的美丽景色。', 5, '[\"诗句顺序\"]', b'1', 'SYSTEM', '2025-11-06 16:12:18.899', 'SYSTEM', '2025-11-06 16:12:18.899');
INSERT INTO `poetry_quiz_questions` VALUES (639, 53, 3, '停车坐爱枫林晚，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"霜叶红于二月花\"]}', '{\"text\": \"霜叶红于二月花\"}', '这是《山行》的最后一句，表达了诗人对深秋枫林晚景的喜爱。', 1, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 16:12:23.250', 'SYSTEM', '2025-11-06 16:12:23.250');
INSERT INTO `poetry_quiz_questions` VALUES (640, 53, 3, '远上寒山石径斜，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"白云生处有人家\"]}', '{\"text\": \"白云生处有人家\"}', '这是《山行》的第二句，描述了山间景色。', 2, '[\"第二句\"]', b'1', 'SYSTEM', '2025-11-06 16:12:23.254', 'SYSTEM', '2025-11-06 16:12:23.254');
INSERT INTO `poetry_quiz_questions` VALUES (641, 53, 5, '《山行》的作者是杜牧。', '{}', '{\"text\": \"true\"}', '《山行》的作者确实是唐代诗人杜牧。', 3, '[\"作者是杜牧\"]', b'1', 'SYSTEM', '2025-11-06 16:12:23.257', 'SYSTEM', '2025-11-06 16:12:23.257');
INSERT INTO `poetry_quiz_questions` VALUES (642, 53, 4, '请将以下诗句按照原文顺序排序：\n1. 远上寒山石径斜\n2. 停车坐爱枫林晚\n3. 白云生处有人家\n4. 霜叶红于二月花', '{}', '{\"order\": [1, 3, 2, 4]}', '这是《山行》的诗句顺序，需要按照原文顺序排列。', 4, '[\"原文顺序\"]', b'1', 'SYSTEM', '2025-11-06 16:12:23.259', 'SYSTEM', '2025-11-06 16:12:23.259');
INSERT INTO `poetry_quiz_questions` VALUES (643, 53, 1, '《山行》这首诗描绘了什么季节的景色？\nA. 春天\nB. 夏天\nC. 秋天\nD. 冬天', '{}', '{\"text\": \"C. 秋天\"}', '《山行》描绘了深秋枫林晚景，因此是秋天的景色。', 5, '[\"枫林晚景\"]', b'1', 'SYSTEM', '2025-11-06 16:12:23.262', 'SYSTEM', '2025-11-06 16:12:23.262');
INSERT INTO `poetry_quiz_questions` VALUES (644, 54, 3, '烟笼寒水月笼沙，夜泊秦淮近酒家。商女不知亡国恨，隔江犹唱后庭花。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"后庭花\"]}', '{\"text\": \"后庭花\"}', '这是《泊秦淮》的最后一句，通过\"后庭花\"的典故，表达对国事的忧虑。', 1, '[\"典故：后庭花\"]', b'1', 'SYSTEM', '2025-11-06 16:12:27.793', 'SYSTEM', '2025-11-06 16:12:27.793');
INSERT INTO `poetry_quiz_questions` VALUES (645, 54, 1, '《泊秦淮》的作者是？', '{}', '{\"text\": \"杜牧\"}', '《泊秦淮》的作者是唐代诗人杜牧。', 2, '[\"唐代诗人\"]', b'1', 'SYSTEM', '2025-11-06 16:12:27.797', 'SYSTEM', '2025-11-06 16:12:27.797');
INSERT INTO `poetry_quiz_questions` VALUES (646, 54, 5, '《泊秦淮》的主旨是表达对国事的忧虑。', '{}', '{\"text\": \"true\"}', '《泊秦淮》通过描写秦淮河畔的景象和歌女的演唱，表达了对国事的忧虑。', 3, '[\"主旨：忧虑\"]', b'1', 'SYSTEM', '2025-11-06 16:12:27.800', 'SYSTEM', '2025-11-06 16:12:27.800');
INSERT INTO `poetry_quiz_questions` VALUES (647, 54, 4, '请将以下诗句按照原文顺序排序：\n1. 烟笼寒水月笼沙\n2. 夜泊秦淮近酒家\n3. 商女不知亡国恨\n4. 隔江犹唱后庭花', '{}', '[\"1\", \"2\", \"3\", \"4\"]', '这是《泊秦淮》的原文诗句，按照原文顺序排序。', 4, '[\"原文顺序\"]', b'1', 'SYSTEM', '2025-11-06 16:12:27.803', 'SYSTEM', '2025-11-06 16:12:27.803');
INSERT INTO `poetry_quiz_questions` VALUES (648, 54, 2, '以下哪些诗句出自《泊秦淮》？\nA. 烟笼寒水月笼沙\nB. 夜泊秦淮近酒家\nC. 商女不知亡国恨\nD. 隔江犹唱后庭花', '{}', '[\"A\", \"B\", \"C\", \"D\"]', '这些诗句都出自唐代诗人杜牧的《泊秦淮》。', 5, '[\"唐代诗人杜牧\"]', b'1', 'SYSTEM', '2025-11-06 16:12:27.805', 'SYSTEM', '2025-11-06 16:12:27.805');
INSERT INTO `poetry_quiz_questions` VALUES (649, 55, 3, '银烛秋光冷画屏，轻罗小扇扑流萤。天阶夜色凉如水，卧看牵牛织女星。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"静夜思\"]}', '{\"text\": \"静夜思\"}', '这是《秋夕》的最后一句，通过描写宫女的生活，表达其孤独和寂寞。', 1, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 16:12:32.106', 'SYSTEM', '2025-11-06 16:12:32.106');
INSERT INTO `poetry_quiz_questions` VALUES (650, 55, 1, '《秋夕》中描写宫女生活的诗句是？', '{}', '{\"text\": \"银烛秋光冷画屏，轻罗小扇扑流萤。\"}', '这首诗通过描写宫女的生活，表达其孤独和寂寞。', 2, '[\"描写宫女生活\"]', b'1', 'SYSTEM', '2025-11-06 16:12:32.111', 'SYSTEM', '2025-11-06 16:12:32.111');
INSERT INTO `poetry_quiz_questions` VALUES (651, 55, 2, '《秋夕》中表现宫女孤独寂寞的诗句有哪些？', '{}', '{\"text\": \"银烛秋光冷画屏，轻罗小扇扑流萤。\"}', '这首诗通过描写宫女的生活，表达其孤独和寂寞。', 3, '[\"描写宫女生活\"]', b'1', 'SYSTEM', '2025-11-06 16:12:32.113', 'SYSTEM', '2025-11-06 16:12:32.113');
INSERT INTO `poetry_quiz_questions` VALUES (652, 55, 5, '《秋夕》中描写宫女生活的诗句是：银烛秋光冷画屏，轻罗小扇扑流萤。', '{}', '{\"text\": \"true\"}', '这首诗通过描写宫女的生活，表达其孤独和寂寞。', 5, '[\"描写宫女生活\"]', b'1', 'SYSTEM', '2025-11-06 16:12:32.115', 'SYSTEM', '2025-11-06 16:12:32.115');
INSERT INTO `poetry_quiz_questions` VALUES (653, 56, 3, '春眠不觉晓，处处闻啼鸟。夜来风雨声，花落知多少。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"多少\"]}', '{\"text\": \"多少\"}', '这是《春晓》的最后一句，通过\"多少\"的疑问，表达对春花被风吹落的怜惜之情。', 1, '[\"首字：多\"]', b'1', 'SYSTEM', '2025-11-06 16:12:37.168', 'SYSTEM', '2025-11-06 16:12:37.168');
INSERT INTO `poetry_quiz_questions` VALUES (654, 56, 1, '《春晓》中，诗人通过描写什么来表达对大自然的热爱和怜惜之情？', '{\"choices\": [\"鸟鸣声\", \"风雨声\", \"花落声\", \"春眠声\"], \"choiceCount\": 4}', '{\"text\": \"风雨声\"}', '《春晓》中，诗人通过描写风雨声来表达对大自然的热爱和怜惜之情。', 2, '[\"表达对大自然的热爱和怜惜\"]', b'1', 'SYSTEM', '2025-11-06 16:12:37.171', 'SYSTEM', '2025-11-06 16:12:37.171');
INSERT INTO `poetry_quiz_questions` VALUES (655, 56, 5, '《春晓》中，诗人通过描写鸟鸣声来表达对大自然的热爱和怜惜之情。', '{}', '{\"text\": \"false\"}', '《春晓》中，诗人通过描写风雨声来表达对大自然的热爱和怜惜之情，而不是鸟鸣声。', 3, '[\"表达对大自然的热爱和怜惜\"]', b'1', 'SYSTEM', '2025-11-06 16:12:37.174', 'SYSTEM', '2025-11-06 16:12:37.174');
INSERT INTO `poetry_quiz_questions` VALUES (656, 56, 4, '请将以下诗句按照原文顺序排序：\n1. 处处闻啼鸟\n2. 春眠不觉晓\n3. 夜来风雨声\n4. 花落知多少', '{}', '{\"order\": [2, 1, 3, 4]}', '《春晓》的原文顺序为：春眠不觉晓，处处闻啼鸟，夜来风雨声，花落知多少。', 4, '[\"原文顺序\"]', b'1', 'SYSTEM', '2025-11-06 16:12:37.177', 'SYSTEM', '2025-11-06 16:12:37.177');
INSERT INTO `poetry_quiz_questions` VALUES (657, 56, 2, '《春晓》中，诗人通过描写哪些自然景象来表达对大自然的热爱和怜惜之情？', '{\"choices\": [\"鸟鸣声\", \"风雨声\", \"花落声\", \"春眠声\"], \"choiceCount\": 4}', '{\"text\": [\"风雨声\", \"花落声\"]}', '《春晓》中，诗人通过描写风雨声和花落声来表达对大自然的热爱和怜惜之情。', 5, '[\"表达对大自然的热爱和怜惜\"]', b'1', 'SYSTEM', '2025-11-06 16:12:37.179', 'SYSTEM', '2025-11-06 16:12:37.179');
INSERT INTO `poetry_quiz_questions` VALUES (658, 57, 3, '故人具鸡黍，邀我至田家。绿树村边合，青山郭外斜。__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"翠绿的树林围绕着村落\"]}', '{\"text\": \"翠绿的树林围绕着村落\"}', '根据原文，填空处描述的是田园风光中的景象。', 1, '[\"原文描述的景象\"]', b'1', 'SYSTEM', '2025-11-06 16:12:41.463', 'SYSTEM', '2025-11-06 16:12:41.463');
INSERT INTO `poetry_quiz_questions` VALUES (659, 57, 3, '故人具鸡黍，邀我至田家。__________，青山郭外斜。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"绿树村边合\"]}', '{\"text\": \"绿树村边合\"}', '根据原文，填空处描述的是田园风光中的景象。', 2, '[\"原文描述的景象\"]', b'1', 'SYSTEM', '2025-11-06 16:12:41.468', 'SYSTEM', '2025-11-06 16:12:41.468');
INSERT INTO `poetry_quiz_questions` VALUES (660, 57, 5, '这首诗描绘了田园风光和友情的美好。', '{}', '{\"text\": \"true\"}', '根据原文和解析，这首诗确实描绘了田园风光和友情的美好。', 3, '[\"描绘田园风光和友情的美好\"]', b'1', 'SYSTEM', '2025-11-06 16:12:41.471', 'SYSTEM', '2025-11-06 16:12:41.471');
INSERT INTO `poetry_quiz_questions` VALUES (661, 57, 4, '请将以下诗句按照原文顺序排序：\n1. 绿树村边合\n2. 邀我至田家\n3. 故人具鸡黍\n4. 青山郭外斜', '{}', '{\"order\": [3, 2, 1, 4]}', '根据原文顺序，诗句的正确排序为：故人具鸡黍，邀我至田家。绿树村边合，青山郭外斜。', 4, '[\"原文诗句顺序\"]', b'1', 'SYSTEM', '2025-11-06 16:12:41.473', 'SYSTEM', '2025-11-06 16:12:41.473');
INSERT INTO `poetry_quiz_questions` VALUES (662, 57, 1, '这首诗的作者是？', '{}', '{\"text\": \"孟浩然\"}', '根据学习内容信息，这首诗的作者是唐代诗人孟浩然。', 5, '[\"唐代诗人\"]', b'1', 'SYSTEM', '2025-11-06 16:12:41.475', 'SYSTEM', '2025-11-06 16:12:41.475');
INSERT INTO `poetry_quiz_questions` VALUES (663, 58, 3, '野旷天低树，江清月近人。日暮客愁新，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"移舟泊烟渚\"]}', '{\"text\": \"移舟泊烟渚\"}', '这是《宿建德江》的第二句，通过描绘江边的景象，表达羁旅之思。', 1, '[\"首字：移\"]', b'1', 'SYSTEM', '2025-11-06 16:12:46.316', 'SYSTEM', '2025-11-06 16:12:46.316');
INSERT INTO `poetry_quiz_questions` VALUES (664, 58, 3, '野旷天低树，江清月近人。__________，日暮客愁新。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"移舟泊烟渚\"]}', '{\"text\": \"移舟泊烟渚\"}', '这是《宿建德江》的首句，通过描绘江边的景象，表达羁旅之思。', 1, '[\"首字：移\"]', b'1', 'SYSTEM', '2025-11-06 16:12:46.320', 'SYSTEM', '2025-11-06 16:12:46.320');
INSERT INTO `poetry_quiz_questions` VALUES (665, 58, 5, '野旷天低树，江清月近人。日暮客愁新，移舟泊烟渚。这首诗表达了诗人对家乡的思念之情。', '{}', '{\"text\": \"true\"}', '这首诗通过描绘江边的景象，表达羁旅之思，表达了诗人对家乡的思念之情。', 2, '[\"主题：思乡\"]', b'1', 'SYSTEM', '2025-11-06 16:12:46.322', 'SYSTEM', '2025-11-06 16:12:46.322');
INSERT INTO `poetry_quiz_questions` VALUES (666, 58, 4, '请将以下诗句按照原文顺序排序：\n1. 移舟泊烟渚\n2. 日暮客愁新\n3. 野旷天低树\n4. 江清月近人', '{}', '{\"order\": [1, 2, 3, 4]}', '这是《宿建德江》的原文顺序，通过排序可以加深对诗歌结构的理解。', 3, '[\"顺序：原文\"]', b'1', 'SYSTEM', '2025-11-06 16:12:46.325', 'SYSTEM', '2025-11-06 16:12:46.325');
INSERT INTO `poetry_quiz_questions` VALUES (667, 58, 5, '野旷天低树，江清月近人。日暮客愁新，移舟泊烟渚。这首诗描绘了诗人夜晚在江边的所见所感。', '{}', '{\"text\": \"true\"}', '这首诗描绘了诗人夜晚在江边的所见所感，通过描绘江边的景象，表达羁旅之思。', 4, '[\"主题：夜景\"]', b'1', 'SYSTEM', '2025-11-06 16:12:46.327', 'SYSTEM', '2025-11-06 16:12:46.327');
INSERT INTO `poetry_quiz_questions` VALUES (668, 59, 3, '八月湖水平，涵虚混太清。气蒸云梦泽，波撼岳阳城。', '{\"blankCount\": 4, \"acceptableAnswers\": [\"八月湖水平\", \"涵虚混太清\", \"气蒸云梦泽\", \"波撼岳阳城\"]}', '{\"text\": \"八月湖水平\"}', '这是《望洞庭湖赠张丞相》的首句，描绘了洞庭湖的壮丽景色。', 1, '[\"首句\"]', b'1', 'SYSTEM', '2025-11-06 16:12:51.093', 'SYSTEM', '2025-11-06 16:12:51.093');
INSERT INTO `poetry_quiz_questions` VALUES (669, 59, 3, '气蒸云梦泽，波撼岳阳城。欲济无舟楫，__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"端居耻圣明\"]}', '{\"text\": \"端居耻圣明\"}', '这是《望洞庭湖赠张丞相》的第五句，表达了诗人希望得到张丞相的引荐。', 2, '[\"欲济无舟楫\"]', b'1', 'SYSTEM', '2025-11-06 16:12:51.096', 'SYSTEM', '2025-11-06 16:12:51.096');
INSERT INTO `poetry_quiz_questions` VALUES (670, 59, 5, '《望洞庭湖赠张丞相》的作者是孟浩然。', '{}', '{\"text\": \"true\"}', '《望洞庭湖赠张丞相》的作者确实是唐代诗人孟浩然。', 3, '[\"作者：孟浩然\"]', b'1', 'SYSTEM', '2025-11-06 16:12:51.099', 'SYSTEM', '2025-11-06 16:12:51.099');
INSERT INTO `poetry_quiz_questions` VALUES (671, 59, 4, '请将以下诗句按照原文顺序排序：\n1. 气蒸云梦泽\n2. 波撼岳阳城\n3. 八月湖水平\n4. 涵虚混太清', '{}', '{\"order\": [3, 1, 4, 2]}', '这是《望洞庭湖赠张丞相》的诗句顺序，按照原文顺序排列。', 4, '[\"诗句顺序\"]', b'1', 'SYSTEM', '2025-11-06 16:12:51.101', 'SYSTEM', '2025-11-06 16:12:51.101');
INSERT INTO `poetry_quiz_questions` VALUES (672, 59, 5, '《望洞庭湖赠张丞相》表达了诗人希望得到张丞相的引荐。', '{}', '{\"text\": \"true\"}', '《望洞庭湖赠张丞相》通过描绘洞庭湖的壮丽景色，表达了诗人希望得到张丞相的引荐。', 5, '[\"希望得到张丞相的引荐\"]', b'1', 'SYSTEM', '2025-11-06 16:12:51.103', 'SYSTEM', '2025-11-06 16:12:51.103');
INSERT INTO `poetry_quiz_questions` VALUES (673, 60, 3, '人事有代谢，往来成古今。江山留胜迹，我辈复登临。__________。', '{\"blankCount\": 1, \"acceptableAnswers\": [\"我辈复登临。\"]}', '{\"text\": \"我辈复登临。\"}', '这是《与诸子登岘山》的最后一句，通过\"我辈\"与\"人事\"的对比，表达对历史变迁和人生短暂的感慨。', 1, '[\"最后一句\"]', b'1', 'SYSTEM', '2025-11-06 16:12:55.413', 'SYSTEM', '2025-11-06 16:12:55.413');
INSERT INTO `poetry_quiz_questions` VALUES (674, 60, 5, '这首诗表达了对历史变迁和人生短暂的感慨。', '{}', '{\"text\": \"true\"}', '通过\"人事有代谢，往来成古今\"的诗句，可以感受到诗人对历史变迁和人生短暂的感慨。', 3, '[\"主旨\"]', b'1', 'SYSTEM', '2025-11-06 16:12:55.417', 'SYSTEM', '2025-11-06 16:12:55.417');

-- ----------------------------
-- Table structure for poetry_user
-- ----------------------------
DROP TABLE IF EXISTS `poetry_user`;
CREATE TABLE `poetry_user`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `openid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '微信openid',
  `unionid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信unionid',
  `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `avatar_url` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像URL',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `grade_level` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '当前年级 1:小学, 2:初中 3:高中',
  `study_goal_daily` int UNSIGNED NULL DEFAULT 10 COMMENT '每日学习目标数量',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `openid`(`openid` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户基本信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of poetry_user
-- ----------------------------
INSERT INTO `poetry_user` VALUES (1, 'oVQpB1147dmXNpwdkZpQH_ny4jYI', NULL, NULL, NULL, '13611988536', 1, 50, 'SYSTEM', '2025-10-25 08:14:30.914', 'SYSTEM', '2025-12-16 12:39:13.234');

-- ----------------------------
-- Table structure for poetry_user_learning_record
-- ----------------------------
DROP TABLE IF EXISTS `poetry_user_learning_record`;
CREATE TABLE `poetry_user_learning_record`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint UNSIGNED NOT NULL COMMENT '用户id',
  `sub_category_id` bigint UNSIGNED NOT NULL COMMENT '子分类id',
  `content_id` bigint UNSIGNED NOT NULL COMMENT '学习内容id',
  `first_studied_at` datetime NOT NULL COMMENT '首次学习时间',
  `last_reviewed_at` datetime NULL DEFAULT NULL COMMENT '最后复习时间',
  `next_review_at` datetime NOT NULL COMMENT '下次复习时间',
  `review_count` int UNSIGNED NULL DEFAULT 0 COMMENT '已复习次数',
  `memory_strength` decimal(3, 2) NULL DEFAULT 1.00 COMMENT '记忆强度(0-1)',
  `easiness_factor` decimal(4, 2) NULL DEFAULT 2.50 COMMENT '难度系数(1.3-2.5)',
  `learning_phase` tinyint UNSIGNED NULL DEFAULT 1 COMMENT '学习阶段 1:新学 2:正在掌握 3:待巩固 4:已掌握',
  `is_remembered` bit(1) NULL DEFAULT b'1' COMMENT '是否记住',
  `study_duration` int UNSIGNED NULL DEFAULT 0 COMMENT '学习时长(秒)',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_content`(`user_id` ASC, `content_id` ASC) USING BTREE,
  INDEX `idx_next_review`(`next_review_at` ASC) USING BTREE,
  INDEX `idx_user_phase`(`user_id` ASC, `learning_phase` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户学习记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of poetry_user_learning_record
-- ----------------------------
INSERT INTO `poetry_user_learning_record` VALUES (19, 1, 4, 21, '2025-11-29 06:50:20', '2025-11-29 06:50:20', '2025-12-02 06:50:20', 1, 0.72, 2.50, 3, b'1', 48959, 'SYSTEM', '2025-11-29 06:50:19.583', 'SYSTEM', '2025-11-29 06:50:19.583');

-- ----------------------------
-- Table structure for poetry_user_learning_stats
-- ----------------------------
DROP TABLE IF EXISTS `poetry_user_learning_stats`;
CREATE TABLE `poetry_user_learning_stats`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint UNSIGNED NOT NULL COMMENT '用户id',
  `total_study_days` int NULL DEFAULT 0 COMMENT '总学习天数',
  `total_items_learned` int NULL DEFAULT 0 COMMENT '总学习内容数量',
  `total_study_minutes` int NULL DEFAULT 0 COMMENT '总学习时长(分钟)',
  `current_streak` int NULL DEFAULT 0 COMMENT '当前连续学习天数',
  `longest_streak` int NULL DEFAULT 0 COMMENT '最长连续学习天数',
  `last_study_date` date NULL DEFAULT NULL COMMENT '最后学习日期',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户学习统计表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of poetry_user_learning_stats
-- ----------------------------

-- ----------------------------
-- Table structure for poetry_user_login_log
-- ----------------------------
DROP TABLE IF EXISTS `poetry_user_login_log`;
CREATE TABLE `poetry_user_login_log`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint UNSIGNED NOT NULL COMMENT '用户ID',
  `openid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户openid',
  `login_type` tinyint NOT NULL DEFAULT 1 COMMENT '登录类型：1-小程序登录，2-公众号登录，3-APP登录',
  `ip_address` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录IP地址',
  `session_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信session_key',
  `login_status` bit(1) NOT NULL DEFAULT b'1' COMMENT '登录状态：1-成功，0-失败',
  `login_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_openid`(`openid` ASC) USING BTREE,
  INDEX `idx_login_time`(`login_time` ASC) USING BTREE,
  INDEX `idx_ip_address`(`ip_address` ASC) USING BTREE,
  INDEX `idx_login_status`(`login_status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 86 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户登录日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of poetry_user_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for poetry_user_study_note
-- ----------------------------
DROP TABLE IF EXISTS `poetry_user_study_note`;
CREATE TABLE `poetry_user_study_note`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint UNSIGNED NOT NULL COMMENT '用户id',
  `content_id` bigint UNSIGNED NOT NULL COMMENT '学习内容id',
  `note_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '笔记内容',
  `tags` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '笔记标签',
  `is_disclosure` bit(1) NULL DEFAULT b'0' COMMENT '是否公开',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_content`(`user_id` ASC, `content_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '学习笔记表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of poetry_user_study_note
-- ----------------------------
INSERT INTO `poetry_user_study_note` VALUES (1, 1, 34, '我的笔记', '九爷真尿性', b'1', 'SYSTEM', '2025-10-25 14:30:40.346', 'SYSTEM', '2025-10-25 14:30:40.346');
INSERT INTO `poetry_user_study_note` VALUES (2, 1, 21, '有感而发', '本来应该从从容容游刃有余', b'1', 'SYSTEM', '2025-10-25 15:06:39.659', 'SYSTEM', '2025-10-25 15:06:39.659');
INSERT INTO `poetry_user_study_note` VALUES (4, 1, 21, '1', NULL, NULL, 'SYSTEM', '2025-11-29 06:24:13.401', 'SYSTEM', '2025-11-29 06:24:13.401');
INSERT INTO `poetry_user_study_note` VALUES (5, 1, 21, '2', NULL, NULL, 'SYSTEM', '2025-11-29 06:24:24.884', 'SYSTEM', '2025-11-29 06:24:24.884');
INSERT INTO `poetry_user_study_note` VALUES (6, 1, 21, '我去问问全额33', '12 22 33', NULL, 'SYSTEM', '2025-11-29 06:49:13.323', 'SYSTEM', '2025-11-29 06:49:13.323');

-- ----------------------------
-- Table structure for poetry_user_study_setting
-- ----------------------------
DROP TABLE IF EXISTS `poetry_user_study_setting`;
CREATE TABLE `poetry_user_study_setting`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint UNSIGNED NOT NULL COMMENT '用户ID',
  `grade_id` bigint UNSIGNED NOT NULL COMMENT '年级ID',
  `sub_category_id` bigint NULL DEFAULT NULL COMMENT '子分类ID',
  `daily_new_items` int UNSIGNED NULL DEFAULT 5 COMMENT '每日新学数量',
  `daily_review_items` int UNSIGNED NULL DEFAULT 10 COMMENT '每日复习数量',
  `study_reminder_time` time NULL DEFAULT NULL COMMENT '学习提醒时间',
  `is_enable_dark_mode` bit(1) NULL DEFAULT b'1' COMMENT '是否开启护眼模式',
  `study_session_minutes` int UNSIGNED NULL DEFAULT 25 COMMENT '单次学习时长(分钟)',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_id_grade_id_sub_category_id`(`user_id` ASC, `grade_id` ASC, `sub_category_id` ASC) USING BTREE COMMENT '同一个用户在同一个年级只能有同一个子分类'
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户学习设置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of poetry_user_study_setting
-- ----------------------------
INSERT INTO `poetry_user_study_setting` VALUES (16, 1, 1, 4, 10, 3, '09:00:00', b'0', 2, 'SYSTEM', '2025-11-29 05:05:14.475', 'SYSTEM', '2025-11-29 05:05:14.475');

-- ----------------------------
-- Table structure for pre_admin
-- ----------------------------
DROP TABLE IF EXISTS `pre_admin`;
CREATE TABLE `pre_admin`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `salt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pre_admin
-- ----------------------------

-- ----------------------------
-- Table structure for pre_category
-- ----------------------------
DROP TABLE IF EXISTS `pre_category`;
CREATE TABLE `pre_category`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `sort` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pre_category
-- ----------------------------
INSERT INTO `pre_category` VALUES (1, '⭐️点收藏福利【6款】', 1);
INSERT INTO `pre_category` VALUES (2, '🔥 热销', 2);
INSERT INTO `pre_category` VALUES (3, '👛 优惠', 3);
INSERT INTO `pre_category` VALUES (4, '🔥 人气菜品 【15款】', 4);
INSERT INTO `pre_category` VALUES (5, '流量套餐', 5);
INSERT INTO `pre_category` VALUES (6, '国 🍢 肉肉 【26款】', 6);
INSERT INTO `pre_category` VALUES (7, '麻 🍄 菌菇 【8款】', 7);
INSERT INTO `pre_category` VALUES (8, '好 蘸料 【11款】', 8);
INSERT INTO `pre_category` VALUES (9, '味 🧋 饮品 【款】', 9);
INSERT INTO `pre_category` VALUES (10, '⭐️ 🥢 必选', 10);

-- ----------------------------
-- Table structure for pre_food
-- ----------------------------
DROP TABLE IF EXISTS `pre_food`;
CREATE TABLE `pre_food`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `category_id` int UNSIGNED NOT NULL DEFAULT 0,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `price` decimal(10, 2) UNSIGNED NOT NULL DEFAULT 0.00,
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `status` tinyint UNSIGNED NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL,
  `delete_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pre_food
-- ----------------------------
INSERT INTO `pre_food` VALUES (1, 1, '新客福利 点亮 收藏门店 新客立减2元', 0.00, 'images/1.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (2, 1, '收藏福利 点亮收藏送3元现金券', 0.00, 'images/2.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (3, 1, '宠粉福利 点亮 蔬菜6选1(多点不送)', 0.01, 'images/3.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (4, 1, '宠粉福利 点亮 荤菜4选1(多点不送)', 0.20, 'images/4.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (5, 1, '特惠福利 点亮 仅限1瓶饮料(多点不送)', 2.28, 'images/5.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (6, 2, '经典骨汤', 2.98, 'images/6.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (7, 2, '【慈爸爸力荐】横扫饥饿单人餐', 27.90, 'images/7.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (8, 2, '【赠*冰红茶】七荤八素加主食麻辣烫套餐', 32.88, 'images/8.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (9, 2, '方便面(1个）', 4.98, 'images/9.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (10, 2, '兰花干(1个）', 4.98, 'images/10.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (11, 2, '火锅油条(1个)', 4.98, 'images/11.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (12, 2, '午餐肉(2片)', 5.98, 'images/12.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (13, 2, '大里脊(1个)', 6.18, 'images/13.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (14, 2, '撒尿牛丸(2个)', 5.98, 'images/14.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (15, 2, '醇香麻辣烫(无汤)', 3.98, 'images/15.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (16, 3, '【优惠福利】点亮⭐️ 仅限1瓶饮料(多点不送)', 2.28, 'images/16.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (17, 3, '【慈爸爸力荐】横扫饥饿单人餐', 27.90, 'images/17.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (18, 3, '【冰雪节】福气满满单人套餐', 24.90, 'images/18.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (19, 3, '【手感火热】单人专属套餐直营', 27.90, 'images/19.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (20, 3, '超级A麻辣烫套餐', 31.88, 'images/20.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (21, 3, '【赠*冰红茶】七荤八素加主食麻辣烫套餐', 32.88, 'images/21.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (22, 3, '超级B麻辣烫套餐', 35.88, 'images/22.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (23, 3, '【赠*冰红茶】大片里脊麻辣烫套餐加主食', 31.88, 'images/23.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (24, 3, '【赠*冰红茶】人气低卡麻辣烫套餐加粉丝', 31.88, 'images/24.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (25, 3, '【童心未泯】单人餐', 24.90, 'images/25.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (26, 3, '【欢乐无限】双人餐', 38.90, 'images/26.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (27, 4, '土豆(3-4片)', 3.98, 'images/27.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (28, 4, '腐竹(3-4个)', 4.58, 'images/28.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (29, 4, '方便面(1个)', 4.98, 'images/29.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (30, 4, '日本豆腐(1个)', 4.98, 'images/30.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (31, 4, '自己煎的蛋(1个)', 5.68, 'images/31.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (32, 4, '奥尔良鸡肉片(2-3片)', 5.98, 'images/32.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (33, 4, '生菜(1份)', 3.98, 'images/33.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (34, 4, '火锅川粉(1份)', 4.98, 'images/34.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (35, 4, '金针菇(1份)', 4.98, 'images/35.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (36, 4, '鱼豆腐(2个)', 4.98, 'images/36.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (37, 4, '刀削面(1包)', 5.98, 'images/37.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (38, 4, '大里脊(1个)', 6.18, 'images/38.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (39, 4, '娃娃菜(1份)', 4.98, 'images/39.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (40, 5, '龙虾饼(1个)', 5.98, 'images/40.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (41, 5, '骨肉相连', 5.98, 'images/41.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (42, 5, '红油五花肉片(3片)', 5.88, 'images/42.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (43, 5, '掌中宝(1份)', 5.98, 'images/43.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (44, 5, '玉米香肠(1个)', 5.98, 'images/44.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (45, 5, '干猪皮(2片)', 5.98, 'images/45.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (46, 5, '臻品纯肉肠(1个)', 5.98, 'images/46.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (47, 5, '麻辣肉片(1份)', 5.98, 'images/47.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (48, 5, '虾滑(1根) ', 9.80, 'images/48.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (49, 5, '腊肠(3-4块)', 5.98, 'images/49.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (50, 6, '红糖酥饼', 10.00, 'images/50.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (51, 7, '耗油青菜小炒/大份', 12.00, 'images/51.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (52, 7, '耗油青菜小炒/小份', 10.00, 'images/52.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (53, 7, '鸡蛋木耳小炒/大份', 12.00, 'images/53.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (54, 7, '鸡蛋木耳小炒/小份', 10.00, 'images/54.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (55, 7, '可乐鸡翅', 11.00, 'images/55.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (56, 7, '油菜沙拉/大份', 11.00, 'images/56.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (57, 7, '油菜沙拉/小份', 9.00, 'images/57.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (58, 8, '美式咖啡/中杯', 8.00, 'images/58.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (59, 8, '香柠咖啡/大杯', 14.00, 'images/59.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (60, 8, '拿铁咖啡/大杯', 16.00, 'images/60.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (61, 8, '拿铁咖啡/中杯', 13.00, 'images/61.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (62, 8, '卡布奇诺/大杯', 16.00, 'images/62.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (63, 8, '卡布奇诺/中杯', 13.00, 'images/63.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (64, 8, '摩卡咖啡/大杯', 19.00, 'images/64.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (65, 8, '摩卡咖啡/中杯', 16.00, 'images/65.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (66, 8, '珍珠拿铁/大杯', 16.00, 'images/66.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (67, 8, '香草拿铁/大杯', 19.00, 'images/67.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (68, 8, '香草拿铁/中杯', 16.00, 'images/68.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (69, 8, '法式奶霜咖啡/大杯', 15.00, 'images/69.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (70, 8, '海盐焦糖拿铁/大杯', 18.00, 'images/70.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (71, 8, '海盐焦糖拿铁/中杯', 15.00, 'images/71.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (72, 8, '香柠咖啡', 14.00, 'images/72.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (73, 9, '法式奶霜草莓果茶（大杯）', 15.00, 'images/73.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (74, 9, '鲜百香双响炮/大杯', 13.00, 'images/74.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (75, 9, '柠檬霸/大杯', 13.00, 'images/75.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (76, 9, '金桔柠檬汁/中杯', 11.00, 'images/76.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (77, 9, '葡萄柚绿茶/中杯', 11.00, 'images/77.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (78, 9, '草莓果茶/中杯', 10.00, 'images/78.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (79, 9, '鲜柠檬红茶/中杯', 10.00, 'images/79.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (80, 9, '鲜柠檬绿茶/中杯', 10.00, 'images/80.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (81, 9, '鲜百香绿茶/中杯', 10.00, 'images/81.webp', 1, '2024-05-29 20:30:07', NULL, NULL);

-- ----------------------------
-- Table structure for pre_order
-- ----------------------------
DROP TABLE IF EXISTS `pre_order`;
CREATE TABLE `pre_order`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `pickup_no` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0',
  `price` decimal(10, 2) UNSIGNED NOT NULL DEFAULT 0.00,
  `promotion` decimal(10, 2) UNSIGNED NOT NULL DEFAULT 0.00,
  `number` int UNSIGNED NOT NULL DEFAULT 0,
  `is_paid` bit(1) NOT NULL DEFAULT b'0',
  `is_taken` bit(1) NOT NULL DEFAULT b'0',
  `comment` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` tinyint UNSIGNED NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `pay_time` datetime NULL DEFAULT NULL,
  `taken_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pre_order
-- ----------------------------
INSERT INTO `pre_order` VALUES (1, 'WD100320240531223729', 'A01', '1', 12.00, 3.00, 4, b'1', b'0', '不要辣，多放香菜', 3, '2024-05-30 01:10:35', '2024-05-30 01:10:39', '2024-05-30 01:10:43');
INSERT INTO `pre_order` VALUES (2, 'WD100420240531223729', 'A02', '1', 13.00, 2.00, 3, b'1', b'1', '放门把手', 3, '2024-05-30 01:12:20', '2024-05-30 01:11:44', '2024-05-30 01:11:46');
INSERT INTO `pre_order` VALUES (3, 'WD100520240531223729', 'A03', '1', 14.00, 0.00, 1, b'1', b'1', '放门把手', 3, '2024-05-30 01:12:15', '2024-05-30 01:12:24', '2024-05-30 01:12:28');
INSERT INTO `pre_order` VALUES (4, 'WD100620240531223729', 'A04', '1', 15.00, 0.00, 1, b'1', b'1', '放门把手', 3, '2024-05-30 01:13:37', '2024-05-30 01:12:44', NULL);
INSERT INTO `pre_order` VALUES (5, 'WD100720240531223729', 'A05', '1', 16.00, 0.00, 2, b'1', b'1', '放门把手', 3, '2024-05-30 01:13:37', '2024-05-30 01:13:43', NULL);
INSERT INTO `pre_order` VALUES (6, 'WD100820240531223729', 'A06', '1', 17.00, 0.00, 1, b'1', b'1', '放门把手', 3, '2024-05-30 01:13:37', '2024-05-30 01:13:46', NULL);
INSERT INTO `pre_order` VALUES (7, 'WD13', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 12.45, 0.00, 15, b'0', b'0', NULL, 1, '2024-06-01 19:03:56', NULL, NULL);
INSERT INTO `pre_order` VALUES (8, 'WD0', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 10.96, 0.00, 2, b'0', b'0', NULL, 1, '2024-06-01 19:12:45', NULL, NULL);
INSERT INTO `pre_order` VALUES (9, 'WD5', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 2.49, 0.00, 4, b'0', b'0', NULL, 1, '2024-06-01 19:16:06', NULL, NULL);
INSERT INTO `pre_order` VALUES (10, 'WD14', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 2.49, 0.00, 4, b'0', b'0', NULL, 1, '2024-06-01 19:25:11', NULL, NULL);
INSERT INTO `pre_order` VALUES (11, 'WD17', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 2.49, 0.00, 3, b'0', b'0', '我第一次下单', 1, '2024-06-01 20:11:39', NULL, NULL);
INSERT INTO `pre_order` VALUES (12, 'WD13', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 7.47, 0.00, 4, b'0', b'0', NULL, 1, '2024-06-01 20:15:10', NULL, NULL);
INSERT INTO `pre_order` VALUES (13, 'WD13', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 0.01, 0.00, 1, b'0', b'0', NULL, 1, '2024-06-01 20:20:06', NULL, NULL);
INSERT INTO `pre_order` VALUES (14, 'WD6', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 0.21, 0.00, 3, b'0', b'0', 'gyuguyguyguyy', 2, '2024-06-01 20:23:13', NULL, NULL);
INSERT INTO `pre_order` VALUES (15, 'WD13', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 0.01, 0.00, 1, b'0', b'0', '111111', 2, '2024-06-01 20:24:26', NULL, NULL);
INSERT INTO `pre_order` VALUES (16, 'WD10', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 0.01, 0.00, 3, b'0', b'0', '我不吃辣，嘻嘻嘻', 2, '2024-06-01 20:25:37', NULL, NULL);

-- ----------------------------
-- Table structure for pre_order_food
-- ----------------------------
DROP TABLE IF EXISTS `pre_order_food`;
CREATE TABLE `pre_order_food`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_id` int UNSIGNED NOT NULL DEFAULT 0,
  `food_id` int UNSIGNED NOT NULL DEFAULT 0,
  `number` int UNSIGNED NOT NULL DEFAULT 0,
  `price` decimal(10, 2) NOT NULL DEFAULT 0.00,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pre_order_food
-- ----------------------------
INSERT INTO `pre_order_food` VALUES (1, 1, 1, 1, 100.00);
INSERT INTO `pre_order_food` VALUES (2, 1, 2, 1, 10.00);
INSERT INTO `pre_order_food` VALUES (3, 2, 3, 1, 11.00);
INSERT INTO `pre_order_food` VALUES (4, 3, 3, 1, 111.00);
INSERT INTO `pre_order_food` VALUES (5, 4, 4, 4, 444.00);
INSERT INTO `pre_order_food` VALUES (6, 5, 5, 1, 111.00);
INSERT INTO `pre_order_food` VALUES (7, 7, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (8, 7, 4, 1, 0.20);
INSERT INTO `pre_order_food` VALUES (9, 7, 5, 1, 2.28);
INSERT INTO `pre_order_food` VALUES (10, 7, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (11, 7, 4, 1, 0.20);
INSERT INTO `pre_order_food` VALUES (12, 7, 5, 1, 2.28);
INSERT INTO `pre_order_food` VALUES (13, 7, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (14, 7, 4, 1, 0.20);
INSERT INTO `pre_order_food` VALUES (15, 7, 5, 1, 2.28);
INSERT INTO `pre_order_food` VALUES (16, 7, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (17, 7, 4, 1, 0.20);
INSERT INTO `pre_order_food` VALUES (18, 7, 5, 1, 2.28);
INSERT INTO `pre_order_food` VALUES (19, 7, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (20, 7, 4, 1, 0.20);
INSERT INTO `pre_order_food` VALUES (21, 7, 5, 1, 2.28);
INSERT INTO `pre_order_food` VALUES (22, 8, 11, 1, 4.98);
INSERT INTO `pre_order_food` VALUES (23, 8, 12, 1, 5.98);
INSERT INTO `pre_order_food` VALUES (24, 9, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (25, 9, 4, 2, 0.20);
INSERT INTO `pre_order_food` VALUES (26, 9, 5, 1, 2.28);
INSERT INTO `pre_order_food` VALUES (27, 10, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (28, 10, 4, 1, 0.20);
INSERT INTO `pre_order_food` VALUES (29, 10, 5, 2, 2.28);
INSERT INTO `pre_order_food` VALUES (30, 11, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (31, 11, 4, 1, 0.20);
INSERT INTO `pre_order_food` VALUES (32, 11, 5, 1, 2.28);
INSERT INTO `pre_order_food` VALUES (33, 12, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (34, 12, 4, 1, 0.20);
INSERT INTO `pre_order_food` VALUES (35, 12, 5, 1, 2.28);
INSERT INTO `pre_order_food` VALUES (36, 12, 11, 1, 4.98);
INSERT INTO `pre_order_food` VALUES (37, 13, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (38, 14, 1, 1, 0.00);
INSERT INTO `pre_order_food` VALUES (39, 14, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (40, 14, 4, 1, 0.20);
INSERT INTO `pre_order_food` VALUES (41, 15, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (42, 16, 1, 1, 0.00);
INSERT INTO `pre_order_food` VALUES (43, 16, 2, 1, 0.00);
INSERT INTO `pre_order_food` VALUES (44, 16, 3, 1, 0.01);

-- ----------------------------
-- Table structure for pre_setting
-- ----------------------------
DROP TABLE IF EXISTS `pre_setting`;
CREATE TABLE `pre_setting`  (
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pre_setting
-- ----------------------------
INSERT INTO `pre_setting` VALUES ('appid', '');
INSERT INTO `pre_setting` VALUES ('appsecret', '');
INSERT INTO `pre_setting` VALUES ('img_ad', '/static/uploads/default/image_ad.png');
INSERT INTO `pre_setting` VALUES ('img_category', '[\"\\/static\\/uploads\\/default\\/bottom_1.png\",\"\\/static\\/uploads\\/default\\/bottom_2.png\",\"\\/static\\/uploads\\/default\\/bottom_3.png\",\"\\/static\\/uploads\\/default\\/bottom_1.png\"]');
INSERT INTO `pre_setting` VALUES ('img_swiper', '[\"\\/static\\/uploads\\/default\\/banner_1.png\",\"\\/static\\/uploads\\/default\\/banner_2.png\",\"\\/static\\/uploads\\/default\\/banner_3.png\"]');
INSERT INTO `pre_setting` VALUES ('promotion', '[{\"k\":50,\"v\":10}]');

-- ----------------------------
-- Table structure for pre_user
-- ----------------------------
DROP TABLE IF EXISTS `pre_user`;
CREATE TABLE `pre_user`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `price` decimal(10, 2) UNSIGNED NOT NULL DEFAULT 0.00,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pre_user
-- ----------------------------

-- ----------------------------
-- Table structure for prompt_common_format
-- ----------------------------
DROP TABLE IF EXISTS `prompt_common_format`;
CREATE TABLE `prompt_common_format`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '格式ID',
  `format_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '格式名称',
  `format_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '格式内容',
  `format_type` tinyint UNSIGNED NOT NULL COMMENT '格式化类型 1学习内容 2 测试题目',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '格式说明',
  `is_active` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否启用',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '通用格式说明表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of prompt_common_format
-- ----------------------------
INSERT INTO `prompt_common_format` VALUES (1, 'DEFAULT_JSON_FORMAT', '\n请返回JSON数组格式，每个题目对象包含以下字段：\n[\n  {\n    \"questionType\": \"SINGLE_CHOICE | MULTI_CHOICE | FILL_BLANK | ORDER_SORT | TRUE_FALSE\",\n    \"questionStem\": \"题目主干\",\n    \"questionData\": {\"根据题型动态变化\"},\n    \"correctAnswer\": {\"标准答案\"},\n    \"explanation\": \"题目解析\",\n    \"difficulty\": 1-5,\n    \"hints\": [\"提示1\", \"提示2\"]\n  }\n]\n\n只返回JSON数组，不要其他文字说明。', 2, '默认的JSON输出格式说明', b'1', 'SYSTEM', '2025-11-08 18:26:53.855', 'SYSTEM', '2025-11-16 21:29:48.334');
INSERT INTO `prompt_common_format` VALUES (2, 'LEARNING_CONTENT_JSON_FORMAT', '\n请返回JSON对象格式，学习内容包含以下字段：\n{\n  \"title\": \"学习内容标题\",\n  \"subtitle\": \"副标题或简要说明\",\n  \"contentType\": 1-3, // 1:诗词 2:文言文 3:现代文\n  \"gradeId\": 1-12, // 年级ID\n  \"categoryId\": 1-3, // 分类ID\n  \"subCategoryId\": 4-11, // 子分类ID\n  \"difficulty\": 1-5, // 难度等级\n  \"originalText\": \"原文内容\",\n  \"author\": \"作者姓名\",\n  \"dynasty\": \"朝代或时期\",\n  \"background\": \"创作背景\",\n  \"explanation\": \"内容解释\",\n  \"usageExamples\": \"用法示例\",\n  \"annotations\": [\"注释1\", \"注释2\"],\n  \"translation\": \"翻译或现代文解释\",\n  \"appreciation\": \"赏析评价\",\n  \"learningObjectives\": [\"学习目标1\", \"学习目标2\"],\n  \"keyPoints\": [\"重点1\", \"重点2\"],\n  \"extendedKnowledge\": \"拓展知识\",\n  \"audioScript\": \"音频脚本\",\n  \"imageDescription\": \"图片描述\"\n}\n\n只返回JSON对象，不要其他文字说明。', 1, '学习内容JSON输出格式', b'1', 'SYSTEM', '2025-11-16 21:30:00.000', 'SYSTEM', '2025-11-16 21:30:00.000');

-- ----------------------------
-- Table structure for prompt_template
-- ----------------------------
DROP TABLE IF EXISTS `prompt_template`;
CREATE TABLE `prompt_template`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '模板ID',
  `template_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板名称',
  `template_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板编码（唯一标识）',
  `template_type` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '提示词模板类型',
  `sub_category_id` bigint NULL DEFAULT NULL COMMENT '关联的子分类ID（为空表示通用模板）',
  `template_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '提示词模板内容',
  `variable_definitions` json NULL COMMENT '变量定义（JSON格式）',
  `example_output` json NULL COMMENT '示例输出（JSON格式）',
  `difficulty_settings` json NULL COMMENT '难度设置（JSON格式）',
  `question_types` json NULL COMMENT '支持的题型（JSON数组）',
  `version` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1.0' COMMENT '模板版本',
  `is_active` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否启用',
  `sort_order` int UNSIGNED NULL DEFAULT 0 COMMENT '排序权重（越大越靠前）',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `template_code`(`template_code` ASC) USING BTREE,
  UNIQUE INDEX `uk_template_code`(`template_code` ASC) USING BTREE,
  INDEX `idx_category`(`sub_category_id` ASC) USING BTREE,
  INDEX `idx_active`(`is_active` ASC, `sort_order` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 279 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'AI提示词模板表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of prompt_template
-- ----------------------------
INSERT INTO `prompt_template` VALUES (1, '唐诗学习内容生成模板', 'TANG_POETRY_LEARNING_V1', 1, 4, '你是一名语文老师，为${gradeLevel}学生生成${name}的唐诗学习内容。\n\n**内容要求：**\n- 随机选择适合${gradeLevel}的唐诗\n- 避免重复选择同一首诗\n- 轮流选择不同诗人、不同题材\n\n**学习内容结构：**\n1. **诗人简介**：生平事迹和文学地位\n2. **创作背景**：时代背景和个人境遇  \n3. **诗句解析**：原文、翻译、词汇解释\n4. **艺术手法**：对仗、押韵、修辞技巧\n5. **主题思想**：核心思想和情感\n6. **背诵指导**：背诵技巧和重点\n\n**JSON格式：**\n{\n  \"title\": \"学习内容标题\",\n  \"poet\": \"诗人姓名\",\n  \"poemName\": \"诗名\",\n  \"dynasty\": \"朝代\",\n  \"originalText\": \"原文内容\",\n  \"explanation\": \"详细解析\",\n  \"translation\": \"翻译\",\n  \"appreciation\": \"赏析评价\",\n  \"background\": \"背景知识\",\n  \"difficulty\": 2,\n  \"keyPoints\": [\"关键知识点1\", \"关键知识点2\"],\n  \"learningTips\": [\"学习技巧1\", \"学习技巧2\"]\n}\n\n**注意事项：**\n- 只返回纯JSON对象\n- 不要代码块标记或前缀说明\n- 确保JSON语法正确\n- 所有字段必须包含', '{\"name\": \"内容分类名称\", \"gradeLevel\": \"适用年级\", \"commonFormat\": \"输出格式要求\"}', NULL, '{\"contentDepth\": \"全面\", \"languageLevel\": \"通俗易懂\", \"targetAudience\": \"中小学生\"}', '[\"POET_INTRO\", \"POEM_ANALYSIS\", \"ART_TECHNIQUES\", \"THEME_DISCUSSION\"]', '1.1', b'1', 90, NULL, '2025-11-08 19:24:37.342', NULL, '2025-11-25 22:58:38.489');
INSERT INTO `prompt_template` VALUES (2, '宋词学习内容生成模板', 'SONG_CI_LEARNING_V1', 1, 5, '你是一名博学的语文老师，精通宋词元曲。请为${gradeLevel}学生生成一份适合学习的${name}解析内容。\r\n\r\n**内容多样性要求：**\r\n1. 每次从你庞大的宋词库中随机选择一首适合${gradeLevel}学生的经典宋词\r\n2. 避免连续选择同一首词作\r\n3. 轮流选择不同词人、不同风格的作品\r\n4. 充分发挥你的文学素养和随机选择能力\r\n\r\n**可选词人范围：**\r\n苏轼、辛弃疾、李清照、柳永、陆游、欧阳修、晏殊、秦观、周邦彦、姜夔等\r\n\r\n**可选词牌范围：**\r\n水调歌头、念奴娇、满江红、声声慢、雨霖铃、鹊桥仙、青玉案、蝶恋花、浣溪沙等\r\n\r\n**学习内容结构：**\r\n1. **作者介绍**：介绍作者生平、创作风格和文学地位\r\n2. **创作背景**：分析写作的历史背景和创作动机\r\n3. **词牌介绍**：说明词牌名的由来和格律特点\r\n4. **原文解析**：提供完整的宋词原文，并逐句解析诗词含义和意象\r\n5. **艺术特色**：分析修辞手法、表现技巧和语言特点\r\n6. **思想情感**：阐述表达的情感主题和思想内涵\r\n7. **名句赏析**：对重点名句进行深入解读\r\n8. **拓展知识**：介绍相关历史典故和文化背景\r\n\r\n**严格的JSON格式要求：**\r\n必须严格按照以下JSON格式返回，不要有任何额外的文字说明、代码块标记或前缀：\r\n\r\n{\r\n  \"title\": \"学习内容标题\",\r\n  \"subtitle\": \"学习内容副标题\", \r\n  \"contentType\": 1,\r\n  \"originalText\": \"原文内容\",\r\n  \"author\": \"作者\",\r\n  \"dynasty\": \"朝代\",\r\n  \"explanation\": \"详细的知识讲解和解析\",\r\n  \"usageExamples\": \"用法示例和例句\",\r\n  \"annotations\": \"注释和说明\", \r\n  \"translation\": \"翻译\",\r\n  \"appreciation\": \"赏析和评价\",\r\n  \"background\": \"背景知识和历史context\",\r\n  \"difficulty\": 2,\r\n  \"keyPoints\": [\"关键知识点1\", \"关键知识点2\"],\r\n  \"learningTips\": [\"学习技巧1\", \"学习技巧2\"]\r\n}\r\n\r\n**重要注意事项：**\r\n- 必须只返回纯JSON对象，不要任何其他文字\r\n- 不要使用```json或```等代码块标记  \r\n- 不要有\"以下是\"、\"结果如下\"等前缀说明\r\n- JSON必须完整且可直接解析\r\n- 所有字段都必须包含，即使为空也要保留字段名\r\n- 数组字段必须使用方括号[]\r\n- 字符串必须使用双引号\"\"\r\n- 确保JSON语法正确，属性间用逗号分隔', '{\"name\": \"内容分类名称\", \"gradeLevel\": \"适用年级\", \"commonFormat\": \"输出格式要求\"}', NULL, '{\"contentDepth\": \"详细\", \"languageLevel\": \"适中\", \"targetAudience\": \"学生\"}', '[\"AUTHOR_INTRO\", \"BACKGROUND\", \"TEXT_ANALYSIS\", \"ART_FEATURES\"]', '1.1', b'1', 95, NULL, '2025-11-08 19:24:37.337', NULL, '2025-11-16 23:21:57.389');
INSERT INTO `prompt_template` VALUES (3, '文言虚词学习内容模板', 'CLASSICAL_FUNCTION_WORD_LEARNING_V1', 1, 6, '你是一名资深的文言文教师，精通古代汉语语法。请为${gradeLevel}学生生成关于${name}的学习内容。\r\n\r\n**内容多样性要求：**\r\n1. 每次从常用文言虚词中随机选择一个适合${gradeLevel}学生学习的虚词\r\n2. 避免连续选择同一个虚词\r\n3. 轮流选择不同功能类别的虚词（连词、介词、助词、语气词等）\r\n4. 充分发挥你的语言学知识和随机选择能力\r\n\r\n**可选虚词范围：**\r\n之、乎、者、也、矣、焉、哉、而、以、于、其、所、为、与、乃等\r\n\r\n**学习内容结构：**\r\n1. **基本含义**：解释该虚词的核心意义和用法\r\n2. **语法功能**：说明在句子中的语法作用（连词、介词、助词等）\r\n3. **常见用法**：列举2-3种常见用法，每种用法提供例句\r\n4. **辨析对比**：与相近虚词的区别和联系\r\n5. **使用技巧**：使用时的注意事项和常见错误\r\n6. **实战应用**：提供经典文言文中的使用实例\r\n7. **记忆口诀**：编写便于记忆的口诀或方法\r\n8. **练习提示**：学习后的自我检测方法\r\n\r\n**严格的JSON格式要求：**\r\n必须严格按照以下JSON格式返回，不要有任何额外的文字说明、代码块标记或前缀：\r\n\r\n{\r\n  \"title\": \"学习内容标题\",\r\n  \"subtitle\": \"学习内容副标题\", \r\n  \"contentType\": 3,\r\n  \"originalText\": \"原文内容\",\r\n  \"author\": \"作者\",\r\n  \"dynasty\": \"朝代\",\r\n  \"explanation\": \"详细的知识讲解和解析\",\r\n  \"usageExamples\": \"用法示例和例句\",\r\n  \"annotations\": \"注释和说明\", \r\n  \"translation\": \"翻译\",\r\n  \"appreciation\": \"赏析和评价\",\r\n  \"background\": \"背景知识和历史context\",\r\n  \"difficulty\": 2,\r\n  \"keyPoints\": [\"关键知识点1\", \"关键知识点2\"],\r\n  \"learningTips\": [\"学习技巧1\", \"学习技巧2\"]\r\n}\r\n\r\n**重要注意事项：**\r\n- 必须只返回纯JSON对象，不要任何其他文字\r\n- 不要使用```json或```等代码块标记  \r\n- 不要有\"以下是\"、\"结果如下\"等前缀说明\r\n- JSON必须完整且可直接解析\r\n- 所有字段都必须包含，即使为空也要保留字段名\r\n- 数组字段必须使用方括号[]\r\n- 字符串必须使用双引号\"\"\r\n- 确保JSON语法正确，属性间用逗号分隔', '{\"name\": \"内容分类名称\", \"gradeLevel\": \"学生年级\", \"commonFormat\": \"输出格式要求\"}', NULL, '{\"contentDepth\": \"基础\", \"languageLevel\": \"简单明了\", \"targetAudience\": \"文言文初学者\"}', '[\"MEANING_EXPLAIN\", \"USAGE_EXAMPLES\", \"GRAMMAR_FUNCTION\", \"PRACTICE_TIPS\"]', '1.1', b'1', 85, NULL, '2025-11-08 19:24:37.345', NULL, '2025-11-16 23:21:57.393');
INSERT INTO `prompt_template` VALUES (4, '文言实词学习内容模板', 'CLASSICAL_CONTENT_WORD_LEARNING_V1', 1, 7, '你是一名资深的文言文教师，精通古代汉语词汇。请为${gradeLevel}学生生成关于${name}的学习内容。\r\n\r\n**内容多样性要求：**\r\n1. 每次从常用文言实词中随机选择一个适合${gradeLevel}学生学习的实词\r\n2. 避免连续选择同一个实词\r\n3. 轮流选择不同词性的实词（名词、动词、形容词等）\r\n4. 充分发挥你的词汇学知识和随机选择能力\r\n\r\n**可选实词范围：**\r\n爱、安、被、本、鄙、兵、病、察、朝、乘、除、传、辞、从、道、得、度、发、方、复、负、故、顾、观、归、过、恨、后、胡、患、或、疾、及、即、既、假、间、见、解、进、尽、就、举、绝、堪、克、类、怜、临、弥、名、末、莫、乃、内、判、期、奇、迁、请、穷、求、取、去、全、任、入、善、少、舍、涉、生、胜、师、施、实、食、使、始、士、世、势、事、视、是、适、书、孰、属、数、遂、说、私、素、汤、涕、通、徒、图、退、亡、王、望、微、闻、相、谢、信、兴、行、幸、修、徐、许、阳、要、宜、遗、贻、易、阴、引、逾、狱、再、造、知、直、止、致、质、治、诸、贼、族、卒、走、左等\r\n\r\n**学习内容结构：**\r\n1. **本义解析**：解释词语的最初含义和字形演变\r\n2. **引申义**：介绍从本义发展出的其他含义\r\n3. **古今异义**：与现代汉语的差异对比\r\n4. **词类活用**：分析名词动用、形容词动用等现象\r\n5. **经典例句**：选取经典文言文中的使用实例\r\n6. **语境分析**：在不同语境中的含义变化\r\n7. **记忆方法**：提供有效的记忆技巧和方法\r\n8. **应用练习**：指导在实际阅读中如何识别和理解\r\n\r\n**严格的JSON格式要求：**\r\n必须严格按照以下JSON格式返回，不要有任何额外的文字说明、代码块标记或前缀：\r\n\r\n{\r\n  \"title\": \"学习内容标题\",\r\n  \"subtitle\": \"学习内容副标题\", \r\n  \"contentType\": 3,\r\n  \"originalText\": \"原文内容\",\r\n  \"author\": \"作者\",\r\n  \"dynasty\": \"朝代\",\r\n  \"explanation\": \"详细的知识讲解和解析\",\r\n  \"usageExamples\": \"用法示例和例句\",\r\n  \"annotations\": \"注释和说明\", \r\n  \"translation\": \"翻译\",\r\n  \"appreciation\": \"赏析和评价\",\r\n  \"background\": \"背景知识和历史context\",\r\n  \"difficulty\": 2,\r\n  \"keyPoints\": [\"关键知识点1\", \"关键知识点2\"],\r\n  \"learningTips\": [\"学习技巧1\", \"学习技巧2\"]\r\n}\r\n\r\n**重要注意事项：**\r\n- 必须只返回纯JSON对象，不要任何其他文字\r\n- 不要使用```json或```等代码块标记  \r\n- 不要有\"以下是\"、\"结果如下\"等前缀说明\r\n- JSON必须完整且可直接解析\r\n- 所有字段都必须包含，即使为空也要保留字段名\r\n- 数组字段必须使用方括号[]\r\n- 字符串必须使用双引号\"\"\r\n- 确保JSON语法正确，属性间用逗号分隔', '{\"name\": \"内容分类名称\", \"gradeLevel\": \"学习阶段\", \"commonFormat\": \"输出格式要求\"}', NULL, '{\"contentDepth\": \"深入\", \"languageLevel\": \"专业准确\", \"targetAudience\": \"文言文学习者\"}', '[\"BASIC_MEANING\", \"EXTENDED_MEANINGS\", \"MODERN_CONTRAST\", \"PRACTICAL_USAGE\"]', '1.1', b'1', 80, NULL, '2025-11-08 19:24:37.349', NULL, '2025-11-16 23:21:57.402');
INSERT INTO `prompt_template` VALUES (5, '记叙文学习内容生成模板', 'NARRATIVE_LEARNING_V1', 1, 8, '你是一名经验丰富的语文教师，精通各类文体教学。请为${gradeLevel}学生生成${name}学习材料。\r\n\r\n**内容多样性要求：**\r\n1. 每次选择或创作一篇适合${gradeLevel}学生的不同主题的记叙文\r\n2. 避免连续选择相同题材的内容\r\n3. 轮流选择不同生活场景、人物故事、成长经历等题材\r\n4. 充分发挥你的创作能力和教学经验\r\n\r\n**可选题材范围：**\r\n童年回忆、校园生活、家庭故事、友情经历、成长感悟、旅行见闻、社会观察等\r\n\r\n**学习内容结构：**\r\n1. **文章概述**：简要介绍文章的主要内容和情节\r\n2. **人物分析**：分析主要人物的性格特点和形象塑造\r\n3. **情节梳理**：梳理故事发展的脉络和关键情节\r\n4. **环境描写**：分析环境描写的作用和意义\r\n5. **主题思想**：阐述文章表达的核心思想和深层含义\r\n6. **写作特色**：分析语言风格、表现手法和结构特点\r\n7. **重点语句**：解读关键句子的理解和赏析\r\n8. **阅读指导**：提供阅读方法和思考问题的角度\r\n\r\n**严格的JSON格式要求：**\r\n必须严格按照以下JSON格式返回，不要有任何额外的文字说明、代码块标记或前缀：\r\n\r\n{\r\n  \"title\": \"学习内容标题\",\r\n  \"subtitle\": \"学习内容副标题\", \r\n  \"contentType\": 3,\r\n  \"originalText\": \"原文内容\",\r\n  \"author\": \"作者\",\r\n  \"dynasty\": \"朝代\",\r\n  \"explanation\": \"详细的知识讲解和解析\",\r\n  \"usageExamples\": \"用法示例和例句\",\r\n  \"annotations\": \"注释和说明\", \r\n  \"translation\": \"翻译\",\r\n  \"appreciation\": \"赏析和评价\",\r\n  \"background\": \"背景知识和历史context\",\r\n  \"difficulty\": 2,\r\n  \"keyPoints\": [\"关键知识点1\", \"关键知识点2\"],\r\n  \"learningTips\": [\"学习技巧1\", \"学习技巧2\"]\r\n}\r\n\r\n**重要注意事项：**\r\n- 必须只返回纯JSON对象，不要任何其他文字\r\n- 不要使用```json或```等代码块标记  \r\n- 不要有\"以下是\"、\"结果如下\"等前缀说明\r\n- JSON必须完整且可直接解析\r\n- 所有字段都必须包含，即使为空也要保留字段名\r\n- 数组字段必须使用方括号[]\r\n- 字符串必须使用双引号\"\"\r\n- 确保JSON语法正确，属性间用逗号分隔', '{\"name\": \"内容分类名称\", \"gradeLevel\": \"学生年级\", \"commonFormat\": \"输出格式要求\"}', NULL, '{\"contentDepth\": \"详细\", \"languageLevel\": \"生动形象\", \"targetAudience\": \"中小学生\"}', '[\"OVERVIEW\", \"CHARACTER_ANALYSIS\", \"PLOT_ANALYSIS\", \"WRITING_FEATURES\"]', '1.1', b'1', 68, NULL, '2025-11-16 18:52:33.000', NULL, '2025-11-16 23:21:57.406');
INSERT INTO `prompt_template` VALUES (6, '说明文学习内容生成模板', 'EXPOSITORY_LEARNING_V1', 1, 9, '你是一名专业的科普教育专家，精通科学知识传播。请为${gradeLevel}学生生成${name}学习材料。\r\n\r\n**内容多样性要求：**\r\n1. 每次选择或创作一篇适合${gradeLevel}学生的不同科学主题的说明文\r\n2. 避免连续选择相同领域的内容\r\n3. 轮流选择自然科学、生活常识、科技知识、人文地理等不同领域\r\n4. 充分发挥你的科学素养和教学创意\r\n\r\n**可选主题范围：**\r\n动植物知识、天文地理、物理化学、人体健康、环境保护、科技发明、传统文化等\r\n\r\n**学习内容结构：**\r\n1. **说明对象**：明确文章说明的事物或事理\r\n2. **对象特征**：分析说明对象的主要特点和性质\r\n3. **说明顺序**：分析文章采用的说明顺序（时间、空间、逻辑）\r\n4. **说明方法**：识别运用的说明方法（举例子、列数字、作比较等）\r\n5. **结构分析**：分析文章的组织结构和段落关系\r\n6. **语言特点**：分析说明文语言的准确性和科学性\r\n7. **知识拓展**：提供相关的科学知识或背景信息\r\n8. **学习方法**：指导阅读说明文的技巧和注意事项\r\n\r\n**严格的JSON格式要求：**\r\n必须严格按照以下JSON格式返回，不要有任何额外的文字说明、代码块标记或前缀：\r\n\r\n{\r\n  \"title\": \"学习内容标题\",\r\n  \"subtitle\": \"学习内容副标题\", \r\n  \"contentType\": 3,\r\n  \"originalText\": \"原文内容\",\r\n  \"author\": \"作者\",\r\n  \"dynasty\": \"朝代\",\r\n  \"explanation\": \"详细的知识讲解和解析\",\r\n  \"usageExamples\": \"用法示例和例句\",\r\n  \"annotations\": \"注释和说明\", \r\n  \"translation\": \"翻译\",\r\n  \"appreciation\": \"赏析和评价\",\r\n  \"background\": \"背景知识和历史context\",\r\n  \"difficulty\": 2,\r\n  \"keyPoints\": [\"关键知识点1\", \"关键知识点2\"],\r\n  \"learningTips\": [\"学习技巧1\", \"学习技巧2\"]\r\n}\r\n\r\n**重要注意事项：**\r\n- 必须只返回纯JSON对象，不要任何其他文字\r\n- 不要使用```json或```等代码块标记  \r\n- 不要有\"以下是\"、\"结果如下\"等前缀说明\r\n- JSON必须完整且可直接解析\r\n- 所有字段都必须包含，即使为空也要保留字段名\r\n- 数组字段必须使用方括号[]\r\n- 字符串必须使用双引号\"\"\r\n- 确保JSON语法正确，属性间用逗号分隔', '{\"name\": \"内容分类名称\", \"gradeLevel\": \"学生年级\", \"commonFormat\": \"输出格式要求\"}', NULL, '{\"contentDepth\": \"系统\", \"languageLevel\": \"准确清晰\", \"targetAudience\": \"中小学生\"}', '[\"OBJECT_INTRODUCTION\", \"FEATURE_ANALYSIS\", \"METHOD_IDENTIFICATION\", \"STRUCTURE_ANALYSIS\"]', '1.1', b'1', 73, NULL, '2025-11-16 18:52:47.000', NULL, '2025-11-16 23:21:57.409');
INSERT INTO `prompt_template` VALUES (7, '议论文学习内容生成模板', 'ARGUMENTATIVE_LEARNING_V1', 1, 10, '你是一名资深的思辨教育专家，精通逻辑思维训练。请为${gradeLevel}学生生成${name}学习材料。\r\n\r\n**内容多样性要求：**\r\n1. 每次选择或创作一篇适合${gradeLevel}学生的不同议题的议论文\r\n2. 避免连续选择相同主题的内容\r\n3. 轮流选择社会热点、人生哲理、学习方法、品德修养等不同议题\r\n4. 充分发挥你的思辨能力和教学智慧\r\n\r\n**可选议题范围：**\r\n学习方法、品德修养、社会责任、环境保护、科技发展、文化传承、人生价值等\r\n\r\n**学习内容结构：**\r\n1. **论点分析**：找出文章的中心论点和分论点\r\n2. **论据梳理**：分析使用的论据类型（事实、数据、理论等）\r\n3. **论证方法**：识别运用的论证方法（举例、对比、因果等）\r\n4. **论证结构**：分析文章的论证层次和逻辑关系\r\n5. **语言特色**：分析议论文语言的逻辑性和说服力\r\n6. **思想价值**：阐述文章的思想内涵和现实意义\r\n7. **写作借鉴**：学习议论文的写作技巧\r\n8. **思辨训练**：提供相关的思考和讨论问题\r\n\r\n**严格的JSON格式要求：**\r\n必须严格按照以下JSON格式返回，不要有任何额外的文字说明、代码块标记或前缀：\r\n\r\n{\r\n  \"title\": \"学习内容标题\",\r\n  \"subtitle\": \"学习内容副标题\", \r\n  \"contentType\": 3,\r\n  \"originalText\": \"原文内容\",\r\n  \"author\": \"作者\",\r\n  \"dynasty\": \"朝代\",\r\n  \"explanation\": \"详细的知识讲解和解析\",\r\n  \"usageExamples\": \"用法示例和例句\",\r\n  \"annotations\": \"注释和说明\", \r\n  \"translation\": \"翻译\",\r\n  \"appreciation\": \"赏析和评价\",\r\n  \"background\": \"背景知识和历史context\",\r\n  \"difficulty\": 2,\r\n  \"keyPoints\": [\"关键知识点1\", \"关键知识点2\"],\r\n  \"learningTips\": [\"学习技巧1\", \"学习技巧2\"]\r\n}\r\n\r\n**重要注意事项：**\r\n- 必须只返回纯JSON对象，不要任何其他文字\r\n- 不要使用```json或```等代码块标记  \r\n- 不要有\"以下是\"、\"结果如下\"等前缀说明\r\n- JSON必须完整且可直接解析\r\n- 所有字段都必须包含，即使为空也要保留字段名\r\n- 数组字段必须使用方括号[]\r\n- 字符串必须使用双引号\"\"\r\n- 确保JSON语法正确，属性间用逗号分隔', '{\"name\": \"内容分类名称\", \"gradeLevel\": \"学生年级\", \"commonFormat\": \"输出格式要求\"}', NULL, '{\"contentDepth\": \"深入\", \"languageLevel\": \"逻辑严密\", \"targetAudience\": \"中学生\"}', '[\"THESIS_ANALYSIS\", \"EVIDENCE_ANALYSIS\", \"ARGUMENT_METHODS\", \"CRITICAL_THINKING\"]', '1.1', b'1', 78, NULL, '2025-11-16 18:53:04.000', NULL, '2025-11-16 23:21:57.412');
INSERT INTO `prompt_template` VALUES (8, '诗词鉴赏学习内容模板', 'POETRY_APPRECIATION_LEARNING_V1', 1, 11, '你是一名资深的文学鉴赏专家，精通诗词美学。请为${gradeLevel}学生生成${name}学习内容。\r\n\r\n**内容多样性要求：**\r\n1. 每次从你丰富的诗词库中随机选择一首适合${gradeLevel}学生鉴赏的经典诗词\r\n2. 避免连续选择同一首诗词\r\n3. 轮流选择不同朝代、不同风格、不同题材的诗词作品\r\n4. 充分发挥你的文学鉴赏能力和美学素养\r\n\r\n**可选诗词范围：**\r\n唐诗、宋词、元曲、古诗十九首、乐府诗、近体诗等各个时期的经典作品\r\n\r\n**可选诗人词人：**\r\n李白、杜甫、苏轼、李清照、白居易、王维、李商隐、杜牧、辛弃疾等\r\n\r\n**学习内容结构：**\r\n1. **整体感知**：分析诗歌的整体意境和情感基调\r\n2. **语言品味**：赏析关键词语的妙用和语言特色\r\n3. **意象分析**：分析诗歌中意象的构成和象征意义\r\n4. **手法鉴赏**：鉴赏运用的艺术手法和表现技巧\r\n5. **情感体验**：体会诗人表达的情感内涵和读者感受\r\n6. **文化内涵**：分析诗歌反映的文化背景和思想价值\r\n7. **比较阅读**：与同类题材诗歌的对比分析\r\n8. **创作启发**：从鉴赏中获得的写作启示\r\n\r\n**严格的JSON格式要求：**\r\n必须严格按照以下JSON格式返回，不要有任何额外的文字说明、代码块标记或前缀：\r\n\r\n{\r\n  \"title\": \"学习内容标题\",\r\n  \"subtitle\": \"学习内容副标题\", \r\n  \"contentType\": 1,\r\n  \"originalText\": \"原文内容\",\r\n  \"author\": \"作者\",\r\n  \"dynasty\": \"朝代\",\r\n  \"explanation\": \"详细的知识讲解和解析\",\r\n  \"usageExamples\": \"用法示例和例句\",\r\n  \"annotations\": \"注释和说明\", \r\n  \"translation\": \"翻译\",\r\n  \"appreciation\": \"赏析和评价\",\r\n  \"background\": \"背景知识和历史context\",\r\n  \"difficulty\": 2,\r\n  \"keyPoints\": [\"关键知识点1\", \"关键知识点2\"],\r\n  \"learningTips\": [\"学习技巧1\", \"学习技巧2\"]\r\n}\r\n\r\n**重要注意事项：**\r\n- 必须只返回纯JSON对象，不要任何其他文字\r\n- 不要使用```json或```等代码块标记  \r\n- 不要有\"以下是\"、\"结果如下\"等前缀说明\r\n- JSON必须完整且可直接解析\r\n- 所有字段都必须包含，即使为空也要保留字段名\r\n- 数组字段必须使用方括号[]\r\n- 字符串必须使用双引号\"\"\r\n- 确保JSON语法正确，属性间用逗号分隔', '{\"name\": \"内容分类名称\", \"gradeLevel\": \"鉴赏水平\", \"commonFormat\": \"输出格式要求\"}', NULL, '{\"contentDepth\": \"鉴赏级\", \"languageLevel\": \"优美生动\", \"targetAudience\": \"文学爱好者\"}', '[\"OVERALL_IMPRESSION\", \"LANGUAGE_APPRECIATION\", \"IMAGE_ANALYSIS\", \"CULTURAL_VALUE\"]', '1.1', b'1', 88, NULL, '2025-11-08 19:24:37.352', NULL, '2025-11-16 23:21:57.416');
INSERT INTO `prompt_template` VALUES (9, '通用学习内容生成模板', 'GENERAL_LEARNING_V1', 1, NULL, '你是一名全科教育专家，精通各学科知识教学。请为${gradeLevel}学生生成适合的${name}学习内容。\r\n\r\n**内容多样性要求：**\r\n1. 每次根据${gradeLevel}学生的特点选择不同的学习主题\r\n2. 避免连续选择相同领域的内容\r\n3. 轮流选择语文、数学、英语、科学、社会等不同学科知识\r\n4. 充分发挥你的跨学科教学能力和创意\r\n\r\n**可选学科范围：**\r\n语言文学、数学逻辑、外语学习、自然科学、社会科学、艺术修养、生活技能等\r\n\r\n**学习内容结构：**\r\n1. **知识概述**：简要介绍学习内容的核心要点\r\n2. **详细解析**：对重要概念和内容进行深入讲解\r\n3. **实例说明**：通过具体例子帮助理解\r\n4. **学习方法**：提供有效的学习策略和技巧\r\n5. **常见问题**：解答学生可能遇到的疑问\r\n6. **拓展知识**：提供相关的延伸阅读和知识链接\r\n7. **总结回顾**：归纳关键知识点\r\n8. **自我检测**：提供学习效果的自我评估方法\r\n\r\n**严格的JSON格式要求：**\r\n必须严格按照以下JSON格式返回，不要有任何额外的文字说明、代码块标记或前缀：\r\n\r\n{\r\n  \"title\": \"学习内容标题\",\r\n  \"subtitle\": \"学习内容副标题\", \r\n  \"contentType\": 3,\r\n  \"originalText\": \"原文内容\",\r\n  \"author\": \"作者\",\r\n  \"dynasty\": \"朝代\",\r\n  \"explanation\": \"详细的知识讲解和解析\",\r\n  \"usageExamples\": \"用法示例和例句\",\r\n  \"annotations\": \"注释和说明\", \r\n  \"translation\": \"翻译\",\r\n  \"appreciation\": \"赏析和评价\",\r\n  \"background\": \"背景知识和历史context\",\r\n  \"difficulty\": 2,\r\n  \"keyPoints\": [\"关键知识点1\", \"关键知识点2\"],\r\n  \"learningTips\": [\"学习技巧1\", \"学习技巧2\"]\r\n}\r\n\r\n**重要注意事项：**\r\n- 必须只返回纯JSON对象，不要任何其他文字\r\n- 不要使用```json或```等代码块标记  \r\n- 不要有\"以下是\"、\"结果如下\"等前缀说明\r\n- JSON必须完整且可直接解析\r\n- 所有字段都必须包含，即使为空也要保留字段名\r\n- 数组字段必须使用方括号[]\r\n- 字符串必须使用双引号\"\"\r\n- 确保JSON语法正确，属性间用逗号分隔', '{\"name\": \"内容分类名称\", \"gradeLevel\": \"目标学生群体\", \"commonFormat\": \"输出格式要求\"}', NULL, '{\"contentDepth\": \"适中\", \"languageLevel\": \"通俗易懂\", \"targetAudience\": \"普通学习者\"}', '[\"KNOWLEDGE_OVERVIEW\", \"DETAILED_EXPLANATION\", \"LEARNING_METHODS\", \"SELF_ASSESSMENT\"]', '1.1', b'1', 10, NULL, '2025-11-08 19:24:37.359', NULL, '2025-11-16 23:21:57.419');
INSERT INTO `prompt_template` VALUES (11, '唐诗学习提示词模板', 'POETRY_TANG_V1', 2, 4, '请根据以下唐诗内容，生成5道练习题。题目要考察唐诗的记忆、理解和赏析能力。\n\n唐诗信息：\n标题：${title}\n作者：${author}\n朝代：${dynasty}\n原文：\n${originalText}\n译文：\n${translation}\n赏析：\n${appreciation}\n\n请生成5道题目，题型可以包括：填空、选择、排序、判断等。\n考察重点：名句默写、作者信息、诗歌意象、修辞手法、情感主旨等。\n${commonFormat}', '{\"variables\": {\"title\": \"诗词标题\", \"author\": \"作者（可选）\", \"dynasty\": \"朝代（可选）\", \"translation\": \"译文（可选）\", \"appreciation\": \"赏析（可选）\", \"commonFormat\": \"通用JSON格式说明\", \"originalText\": \"诗词原文\"}}', '[{\"difficulty\": 1, \"explanation\": \"考察名句默写能力\", \"questionStem\": \"床前明月光，疑是地上霜。举头望明月，__________。\", \"questionType\": \"FILL_BLANK\"}]', '{\"easyCount\": 2, \"hardCount\": 1, \"mediumCount\": 2, \"difficultyRange\": [1, 5]}', '[\"SINGLE_CHOICE\", \"FILL_BLANK\", \"ORDER_SORT\", \"TRUE_FALSE\"]', '1.0', b'1', 100, NULL, '2025-11-08 18:18:46.026', NULL, '2025-11-16 20:37:40.895');
INSERT INTO `prompt_template` VALUES (12, '宋词学习提示词模板', 'POETRY_SONG_V1', 2, 5, '请根据以下宋词内容，生成5道练习题。重点考察宋词的格律、意境和情感表达。\n\n宋词信息：\n标题：${title}\n作者：${author}\n词牌名：${ciPaiName}\n原文：\n${originalText}\n译文：\n${translation}\n赏析：\n${appreciation}\n\n请生成5道题目，重点考察：\n1. 词牌格律特点\n2. 意象意境分析\n3. 情感表达方式\n4. 名句赏析理解\n${commonFormat}', '{\"variables\": {\"title\": \"词作标题\", \"author\": \"作者\", \"ciPaiName\": \"词牌名（可选）\", \"translation\": \"译文（可选）\", \"appreciation\": \"赏析（可选）\", \"originalText\": \"宋词原文\"}}', NULL, '{\"easyCount\": 1, \"hardCount\": 1, \"mediumCount\": 3, \"difficultyRange\": [2, 5]}', '[\"SINGLE_CHOICE\", \"FILL_BLANK\", \"TRUE_FALSE\", \"MULTI_CHOICE\"]', '1.0', b'1', 95, NULL, '2025-11-08 18:18:46.031', NULL, '2025-11-16 20:37:42.796');
INSERT INTO `prompt_template` VALUES (13, '虚词语法提示词模板', 'GRAMMAR_FUNCTION_WORD_V1', 2, 6, '请根据以下虚词语法知识点，生成5道练习题。重点考察虚词的用法和语法功能。\n\n知识点信息：\n标题：${title}\n解释：\n${explanation}\n用法示例：\n${usageExamples}\n相关例句：\n${originalText}\n\n请生成5道题目，题型建议：\n1. 选择题：考察虚词的含义和语法功能\n2. 填空题：在句子中正确使用虚词\n3. 判断题：用法正误判断\n4. 例句分析题\n${commonFormat}', NULL, NULL, '{\"easyCount\": 2, \"hardCount\": 1, \"mediumCount\": 2, \"difficultyRange\": [1, 4]}', '[\"SINGLE_CHOICE\", \"FILL_BLANK\", \"TRUE_FALSE\"]', '1.0', b'1', 85, NULL, '2025-11-08 18:18:46.044', NULL, '2025-11-16 20:37:45.322');
INSERT INTO `prompt_template` VALUES (14, '实词语法提示词模板', 'GRAMMAR_CONTENT_WORD_V1', 2, 7, '请根据以下实词语法知识点，生成5道练习题。重点考察实词的含义和用法。\n\n知识点信息：\n标题：${title}\n解释：\n${explanation}\n用法示例：\n${usageExamples}\n相关例句：\n${originalText}\n\n请生成5道题目，题型建议：\n1. 选择题：考察实词的含义和用法\n2. 填空题：在句子中正确使用实词\n3. 判断题：用法正误判断\n4. 近义词辨析题\n${commonFormat}', NULL, NULL, '{\"easyCount\": 2, \"hardCount\": 1, \"mediumCount\": 2, \"difficultyRange\": [1, 4]}', '[\"SINGLE_CHOICE\", \"FILL_BLANK\", \"TRUE_FALSE\", \"MULTI_CHOICE\"]', '1.0', b'1', 80, NULL, '2025-11-08 18:18:46.051', NULL, '2025-11-16 20:37:47.963');
INSERT INTO `prompt_template` VALUES (15, '议论文阅读提示词模板', 'ARGUMENTATIVE_V1', 2, 8, '请根据以下议论文内容，生成5道阅读理解题。重点考察论点论据和论证方法。\n\n议论文信息：\n标题：${title}\n作者：${author}\n原文：\n${originalText}\n中心论点：\n${explanation}\n\n请生成5道题目，重点考察：\n1. 中心论点的把握\n2. 论据的类型和作用\n3. 论证方法分析\n4. 文章结构理解\n5. 作者观点态度\n${commonFormat}', NULL, NULL, '{\"easyCount\": 1, \"hardCount\": 1, \"mediumCount\": 3, \"difficultyRange\": [2, 5]}', '[\"SINGLE_CHOICE\", \"MULTI_CHOICE\", \"TRUE_FALSE\", \"FILL_BLANK\"]', '1.0', b'1', 75, NULL, '2025-11-08 18:18:46.057', NULL, '2025-11-16 20:37:50.843');
INSERT INTO `prompt_template` VALUES (16, '说明文阅读提示词模板', 'EXPOSITORY_V1', 2, 9, '请根据以下说明文内容，生成5道阅读理解题。重点考察说明对象和说明方法。\n\n说明文信息：\n标题：${title}\n原文：\n${originalText}\n\n请生成5道题目，重点考察：\n1. 说明对象的特征\n2. 说明顺序的理解\n3. 说明方法的识别\n4. 关键信息的提取\n5. 文章结构的把握\n${commonFormat}', NULL, NULL, '{\"easyCount\": 2, \"hardCount\": 1, \"mediumCount\": 2, \"difficultyRange\": [1, 5]}', '[\"SINGLE_CHOICE\", \"TRUE_FALSE\", \"FILL_BLANK\"]', '1.0', b'1', 70, NULL, '2025-11-08 18:18:46.063', NULL, '2025-11-16 20:37:52.828');
INSERT INTO `prompt_template` VALUES (17, '记叙文阅读提示词模板', 'NARRATIVE_V1', 2, 10, '请根据以下记叙文内容，生成5道阅读理解题。重点考察情节人物和主题思想。\n\n记叙文信息：\n标题：${title}\n作者：${author}\n原文：\n${originalText}\n\n请生成5道题目，重点考察：\n1. 情节发展的理解\n2. 人物形象分析\n3. 环境描写的作用\n4. 主题思想的把握\n5. 写作手法分析\n${commonFormat}', NULL, NULL, '{\"easyCount\": 1, \"hardCount\": 1, \"mediumCount\": 3, \"difficultyRange\": [2, 5]}', '[\"SINGLE_CHOICE\", \"MULTI_CHOICE\", \"TRUE_FALSE\"]', '1.0', b'1', 65, NULL, '2025-11-08 18:18:46.066', NULL, '2025-11-16 20:37:54.894');
INSERT INTO `prompt_template` VALUES (18, '通用学习提示词模板', 'GENERAL_V1', 2, NULL, '请根据以下学习内容，生成5道练习题。题目难度要合理分配。\n\n学习内容：\n标题：${title}\n内容：\n${originalText}\n解析：\n${explanation}\n\n请生成5道题目，考察对内容的理解和掌握。\n${commonFormat}', NULL, NULL, '{\"easyCount\": 2, \"hardCount\": 1, \"mediumCount\": 2, \"difficultyRange\": [1, 5]}', '[\"SINGLE_CHOICE\", \"FILL_BLANK\", \"TRUE_FALSE\"]', '1.0', b'1', 10, NULL, '2025-11-08 18:18:46.069', NULL, '2025-11-16 20:37:58.302');
INSERT INTO `prompt_template` VALUES (19, '诗词赏析测试题目模板', 'POETRY_APPRECIATION_QUESTIONS_V1', 2, 11, '请根据以下诗词赏析内容，生成5道测试题目，重点考察学生的诗词鉴赏能力和文学素养。\n\n诗词信息：\n标题：${title}\n作者：${author}\n朝代：${dynasty}\n原文：\n${originalText}\n赏析要点：\n${appreciation}\n\n请生成5道题目，重点考察：\n1. 诗歌意象的理解和分析\n2. 艺术手法的识别和赏析\n3. 情感主旨的把握\n4. 语言特色的品味\n5. 文化内涵的理解\n\n题目类型建议：\n- 选择题：考察对诗歌细节的理解\n- 简答题：考察综合分析能力\n- 判断题：考察基本概念\n- 填空题：考察关键诗句和术语\n${commonFormat}', '{\"variables\": {\"title\": \"诗词标题\", \"author\": \"作者\", \"dynasty\": \"朝代\", \"appreciation\": \"赏析内容\", \"commonFormat\": \"通用JSON格式说明\", \"originalText\": \"诗词原文\"}}', '[{\"difficulty\": 3, \"explanation\": \"考察对诗歌意象的理解\", \"questionStem\": \"这首诗中\\\"明月\\\"意象表达了诗人怎样的情感？\", \"questionType\": \"SHORT_ANSWER\"}]', '{\"easyCount\": 1, \"hardCount\": 2, \"mediumCount\": 2, \"difficultyRange\": [2, 5]}', '[\"SINGLE_CHOICE\", \"SHORT_ANSWER\", \"TRUE_FALSE\", \"FILL_BLANK\"]', '1.0', b'1', 87, NULL, '2025-11-16 18:52:22.000', NULL, '2025-11-16 20:38:02.284');

-- ----------------------------
-- Table structure for user_interaction_log
-- ----------------------------
DROP TABLE IF EXISTS `user_interaction_log`;
CREATE TABLE `user_interaction_log`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint UNSIGNED NOT NULL COMMENT '用户id',
  `resource_type` tinyint UNSIGNED NOT NULL COMMENT '资源类型',
  `resource_id` bigint UNSIGNED NOT NULL COMMENT '资源id',
  `interaction_type` tinyint UNSIGNED NOT NULL COMMENT '互动类型',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_favorite`(`user_id` ASC, `resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_interaction_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
