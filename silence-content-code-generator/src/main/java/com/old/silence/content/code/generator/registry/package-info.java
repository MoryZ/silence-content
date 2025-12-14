/**
 * 代码文件规格注册表
 *
 * <p>本包实现了集中式的元数据管理，消除了代码生成中的硬编码。
 *
 * <h3>核心组件：</h3>
 * <ul>
 *   <li>{@link com.old.silence.content.code.generator.registry.CodeFileSpecRegistry} - 代码文件规格注册表</li>
 * </ul>
 *
 * <h3>功能说明：</h3>
 * <ul>
 *   <li>集中存储所有文件生成规格的元数据</li>
 *   <li>支持按模块类型查询规格</li>
 *   <li>支持按生成条件（ALWAYS/HAS_ENDPOINT/HAS_ANY_ENDPOINT）过滤</li>
 *   <li>提供动态组装生成规则的能力</li>
 * </ul>
 *
 * <h3>数据源：</h3>
 * <ul>
 *   <li>数据库表：cg_code_file_spec</li>
 *   <li>应用启动时加载到内存</li>
 *   <li>支持运行时更新</li>
 * </ul>
 *
 * <h3>设计优势：</h3>
 * <ul>
 *   <li>无需修改代码即可调整生成规则</li>
 *   <li>易于扩展新的文件类型</li>
 *   <li>支持多模块、多场景的代码生成</li>
 * </ul>
 *
 * @author moryzang
 */
package com.old.silence.content.code.generator.registry;
