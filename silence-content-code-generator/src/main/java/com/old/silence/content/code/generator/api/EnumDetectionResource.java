package com.old.silence.content.code.generator.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.old.silence.content.code.generator.dto.EnumDetectionRequest;
import com.old.silence.content.code.generator.dto.EnumDetectionResponse;
import com.old.silence.content.code.generator.executor.JdbcSQLAnalyzer;
import com.old.silence.content.code.generator.executor.SQLAnalyzer;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.service.EnumDetectionService;

import java.util.ArrayList;
import java.util.List;

/**
 * 枚举识别资源接口
 * 提供枚举字段智能识别功能
 *
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1/enum")
public class EnumDetectionResource {

    private static final Logger log = LoggerFactory.getLogger(EnumDetectionResource.class);

    private final EnumDetectionService enumDetectionService;

    public EnumDetectionResource(EnumDetectionService enumDetectionService) {
        this.enumDetectionService = enumDetectionService;
    }

    /**
     * 检测表中的枚举字段
     *
     * @param request 检测请求
     * @return 枚举建议列表
     */
    @PostMapping("/detect")
    public ResponseEntity<?> detectEnums(@RequestBody EnumDetectionRequest request) {
        if (request.getDbUrl() == null || request.getDbUrl().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("数据库连接URL不能为空");
        }

        try (SQLAnalyzer analyzer = new JdbcSQLAnalyzer()) {
            List<EnumDetectionResponse.EnumSuggestion> allSuggestions = new ArrayList<>();

            if (request.getTableName() != null && !request.getTableName().trim().isEmpty()) {
                // 检测单个表
                TableInfo tableInfo = analyzer.analyzeTable(request.getTableName());
                if (tableInfo == null) {
                    return ResponseEntity.badRequest()
                            .body("表 " + request.getTableName() + " 不存在或无法访问");
                }
                
                EnumDetectionResponse response = enumDetectionService.detectEnums(tableInfo);
                allSuggestions.addAll(response.getSuggestions());
                
            } else {
                // 检测所有表
                analyzer.getTablesWithComments().keySet().forEach(tableName -> {
                    try {
                        TableInfo tableInfo = analyzer.analyzeTable(tableName);
                        if (tableInfo != null) {
                            EnumDetectionResponse response = enumDetectionService.detectEnums(tableInfo);
                            allSuggestions.addAll(response.getSuggestions());
                        }
                    } catch (Exception e) {
                        log.warn("检测表 {} 的枚举字段失败: {}", tableName, e.getMessage());
                    }
                });
            }

            EnumDetectionResponse result = new EnumDetectionResponse();
            result.setSuggestions(allSuggestions);
            
            log.info("枚举检测完成，共发现 {} 个潜在枚举字段", allSuggestions.size());
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            log.error("枚举检测失败", e);
            return ResponseEntity.internalServerError()
                    .body("枚举检测失败: " + e.getMessage());
        }
    }
}
