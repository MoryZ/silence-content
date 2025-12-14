/**
 * 模板引擎
 *
 * <p>本包实现了模板渲染的核心逻辑，支持多种模板引擎。
 *
 * <h3>核心接口：</h3>
 * <ul>
 *   <li>{@link com.old.silence.content.code.generator.engine.TemplateEngine} - 模板引擎接口</li>
 * </ul>
 *
 * <h3>支持的模板引擎：</h3>
 * <ul>
 *   <li>FreeMarker（默认）</li>
 *   <li>Velocity</li>
 * </ul>
 *
 * <h3>职责：</h3>
 * <ul>
 *   <li>加载模板文件</li>
 *   <li>渲染模板生成代码</li>
 *   <li>处理模板变量和表达式</li>
 * </ul>
 *
 * @author moryzang
 */
package com.old.silence.content.code.generator.engine;
