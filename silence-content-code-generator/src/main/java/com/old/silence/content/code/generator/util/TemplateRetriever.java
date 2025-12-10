package com.old.silence.content.code.generator.util;

import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.domain.enums.codegen.ModuleType;

/**
 * 模板检索器，用于为不同层级的代码生成提供模板参考文本。
 */
public interface TemplateRetriever {

    /**
     * 根据代码层级和上下文信息检索适用的模板提示。
     *
     * @param moduleType     代码层级
     * @param tableInfo 表信息
     * @param apiDoc    接口文档
     * @return 模板提示文本，可为空字符串
     */
    String retrieveHints(ModuleType moduleType, TableInfo tableInfo, ApiDocument apiDoc);
}
