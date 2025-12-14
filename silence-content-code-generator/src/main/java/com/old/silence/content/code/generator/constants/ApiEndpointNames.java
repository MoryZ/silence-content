package com.old.silence.content.code.generator.constants;

/**
 * API端点名称常量
 * 
 * <p>定义所有标准的CRUD操作端点名称，确保在生成和检查端点时使用一致的字符串。
 * 这避免了硬编码字符串带来的维护问题。
 * 
 * @author moryzang
 */
public final class ApiEndpointNames {
    
    private ApiEndpointNames() {
        throw new AssertionError("常量类不能实例化");
    }
    
    /**
     * 分页查询端点
     */
    public static final String PAGINATED_QUERY = "分页查询";
    
    /**
     * 创建端点
     */
    public static final String CREATE = "创建";
    
    /**
     * 更新端点
     */
    public static final String UPDATE = "更新";
    
    /**
     * 根据主键查询端点
     */
    public static final String QUERY_BY_KEY = "根据主键查询";
    
    /**
     * 删除端点
     */
    public static final String DELETE = "删除";
}
