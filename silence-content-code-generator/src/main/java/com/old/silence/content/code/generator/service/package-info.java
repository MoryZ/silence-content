/**
 * 代码生成核心服务层
 *
 * <p>本包包含代码生成的所有业务逻辑服务，是应用层和基础设施层之间的纽带。
 *
 * <h3>核心服务：</h3>
 * <ul>
 *   <li>{@link com.old.silence.content.code.generator.service.RefactoredCodeGeneratorService} - 元数据驱动的代码生成服务</li>
 *   <li>{@link com.old.silence.content.code.generator.service.StepService} - 步骤拆解服务</li>
 *   <li>{@link com.old.silence.content.code.generator.service.TemplateCodeGenerationStrategy} - 模板生成策略</li>
 *   <li>{@link com.old.silence.content.code.generator.service.ApiDocumentGeneratorService} - API 文档生成</li>
 * </ul>
 *
 * <h3>设计特点：</h3>
 * <ul>
 *   <li>使用 {@link com.old.silence.content.code.generator.registry.CodeFileSpecRegistry} 存储元数据</li>
 *   <li>支持元数据驱动的代码生成（消除硬编码）</li>
 *   <li>统一的生成和预览逻辑</li>
 *   <li>支持多模块批量处理</li>
 * </ul>
 *
 * <h3>架构演进：</h3>
 * <ul>
 *   <li>{@link com.old.silence.content.code.generator.service.SpringCodeGeneratorService} (已废弃) - 使用 RefactoredCodeGeneratorService 替代</li>
 * </ul>
 *
 * @author moryzang
 */
package com.old.silence.content.code.generator.service;
