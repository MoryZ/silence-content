/**
 * 代码生成器核心接口定义
 *
 * <p>本包定义了代码生成框架的核心 SPI（Service Provider Interface），
 * 是整个代码生成系统的入口点。
 *
 * <h3>主要接口：</h3>
 * <ul>
 *   <li>{@link com.old.silence.content.code.generator.api.CodeGenerator} - 代码生成的核心接口</li>
 * </ul>
 *
 * <h3>职责：</h3>
 * <ul>
 *   <li>定义代码生成的规范和契约</li>
 *   <li>提供模板渲染、文件生成等核心能力</li>
 *   <li>支持多种代码生成器的实现（如 FreeMarker, Velocity 等）</li>
 * </ul>
 *
 * @author moryzang
 */
package com.old.silence.content.code.generator.api;
