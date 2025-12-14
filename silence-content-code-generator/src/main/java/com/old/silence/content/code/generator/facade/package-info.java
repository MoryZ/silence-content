/**
 * 门面层（Facade）
 *
 * <p>本包提供代码生成器的门面接口，简化客户端调用。
 *
 * <h3>核心门面：</h3>
 * <ul>
 *   <li>{@link com.old.silence.content.code.generator.facade.CodeGeneratorFacade} - 代码生成器门面</li>
 * </ul>
 *
 * <h3>职责：</h3>
 * <ul>
 *   <li>提供简化的代码生成器获取接口</li>
 *   <li>隐藏代码生成器的创建细节</li>
 *   <li>支持多种代码生成器实现的切换（FreeMarker, Velocity 等）</li>
 * </ul>
 *
 * <h3>使用示例：</h3>
 * <pre>
 * CodeGenerator codeGenerator = CodeGeneratorFacade.ofDefault();
 * </pre>
 *
 * @author moryzang
 */
package com.old.silence.content.code.generator.facade;
