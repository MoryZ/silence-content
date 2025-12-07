package com.old.silence.content.code.generator.util;

import org.springframework.stereotype.Component;

import com.old.silence.content.code.generator.dto.CodeGenModuleConfig;
import com.old.silence.content.code.generator.model.ApiDocument;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.strategy.CodeGenerationStrategy.CodeLayer;

/**
 * 提示词构建器
 * 根据表信息、API文档和配置构建大模型提示词
 *
 * @author moryzang
 */
@Component
public class PromptBuilder {

    private final TemplateRetriever templateRetriever;

    public PromptBuilder(TemplateRetriever templateRetriever) {
        this.templateRetriever = templateRetriever;
    }

    /**
     * 构建代码生成提示词
     *
     * @param tableInfo 表信息
     * @param apiDoc    API文档
     * @param config    配置
     * @param layer     代码层级
     * @return 提示词
     */
    public String buildPrompt(TableInfo tableInfo, ApiDocument apiDoc,
                              CodeGenModuleConfig config, CodeLayer layer) {
        StringBuilder prompt = new StringBuilder();

        // 1. 角色定义
        prompt.append("你是一个经验丰富的Java开发工程师，擅长DDD、分层架构与Spring Boot项目开发。\n\n");

        // 2. 任务描述
        prompt.append("## 任务\n");
        prompt.append("根据提供的表结构和API文档，生成").append(getLayerDescription(layer)).append("代码。请严格遵循分层职责与模板约定。\n\n");

        // 3. 表结构信息
        prompt.append("## 表结构信息\n");
        prompt.append("- 表名: ").append(tableInfo.getTableName()).append("\n");
        if (tableInfo.getComment() != null) {
            prompt.append("- 表注释: ").append(tableInfo.getComment()).append("\n");
        }
        prompt.append("- 字段列表:\n");
        tableInfo.getColumnInfos().forEach(column -> {
            prompt.append("  - ").append(column.getFieldName())
                    .append(" (").append(column.getFieldType()).append(")");
            if (column.getComment() != null) {
                prompt.append(" - ").append(column.getComment());
            }
            prompt.append("\n");
        });
        prompt.append("\n");

        // 4. API文档信息
        if (apiDoc != null && apiDoc.getEndpoints() != null) {
            prompt.append("## API接口文档\n");
            apiDoc.getEndpoints().forEach((name, endpoint) -> {
                prompt.append("- ").append(name).append(": ")
                        .append(endpoint.getMethod()).append(" ").append(endpoint.getPath())
                        .append("\n");
            });
            prompt.append("\n");
        }

        // 5. 代码要求
        prompt.append("## 通用代码要求\n");
        prompt.append("- 包名: ").append(config.getBasePackage()).append("，保持模块化路径\n");
        prompt.append("- 遵循Spring Boot最佳实践、SOLID原则以及KISS原则\n");
        prompt.append("- 所有Java文件需包含必要的类注释、方法注释与字段说明\n");
        prompt.append("- 所有REST接口统一使用驼峰风格的路径与@Validated校验\n");
        prompt.append("- MapStruct转换器请使用@Mapper(componentModel = \"spring\")\n");
        prompt.append("- BigInteger/Long 字段序列化时通过@JsonFormat(shape = JsonFormat.Shape.STRING)避免精度丢失\n");
        prompt.append("- 方法内不能出现 System.out.println，统一使用日志（如log.debug/log.info）\n");
        prompt.append("- 各层之间仅通过领域接口交互，不跨层访问\n");
        prompt.append("\n## 代码风格规范（必须严格遵守）\n");
        prompt.append("### Import语句顺序\n");
        prompt.append("严格按照以下顺序排列import语句，每组之间用空行分隔，同组内按字母顺序排序：\n");
        prompt.append("1. import static ...\n");
        prompt.append("2. import java.*\n");
        prompt.append("3. import javax.*\n");
        prompt.append("4. import jakarta.*\n");
        prompt.append("5. import org.*\n");
        prompt.append("6. import com.*\n");
        prompt.append("7. 其他包名\n\n");
        prompt.append("### 接口方法顺序（Controller/Resource类）\n");
        prompt.append("严格按照以下顺序排列方法，同类型方法按方法名字母顺序排序：\n");
        prompt.append("1. @GetMapping 方法\n");
        prompt.append("2. @GetJsonMapping 方法\n");
        prompt.append("3. @PostMapping 方法\n");
        prompt.append("4. @PostJsonMapping 方法\n");
        prompt.append("5. @PutMapping 方法\n");
        prompt.append("6. @PutJsonMapping 方法\n");
        prompt.append("7. @DeleteMapping 方法\n\n");
        prompt.append("### Repository方法顺序\n");
        prompt.append("严格按照以下顺序排列方法，同类型方法按方法名字母顺序排序：\n");
        prompt.append("1. find/query 开头的方法（查询方法）\n");
        prompt.append("2. create/bulkCreate 方法（创建方法）\n");
        prompt.append("3. update/bulkUpdate 方法（更新方法）\n");
        prompt.append("4. delete/bulkDelete 方法（删除方法）\n\n");

        // 6. 层级特定要求
        prompt.append(getLayerSpecificRequirements(layer));

        // 7. 模板参考
        String templateHints = templateRetriever.retrieveHints(layer, tableInfo, apiDoc);
        if (templateHints != null && !templateHints.isBlank()) {
            prompt.append("\n## 模板参考\n");
            prompt.append(templateHints).append("\n");
        }

        // 8. 输出格式
        prompt.append("## 输出格式\n");
        prompt.append("请仅输出完整的Java代码（单个文件），不要包含额外说明或Markdown。\n");

        return prompt.toString();
    }

    /**
     * 获取层级描述
     */
    private String getLayerDescription(CodeLayer layer) {
        return switch (layer) {
            case CONSOLE -> "Console层（管理后台）";
            case SERVICE -> "Service层（业务逻辑）";
            case SERVICE_API -> "Service-API层（接口定义）";
            case ENUM -> "枚举类";
        };
    }

    /**
     * 获取层级特定要求
     */
    private String getLayerSpecificRequirements(CodeLayer layer) {
        return switch (layer) {
            case CONSOLE -> """
                    ## Console层特定要求
                    - 主要用于后台管理系统，仅聚焦CRUD。Resource中使用@RestController与@RequestMapping，调用 service-api 层的FeignClient；仅当流程复杂时，抽取ConsoleService封装流程逻辑
                    - ConsoleResource 方法包含分页、详情、创建、更新、删除；分页返回PageResponse；所有入参使用@Validated
                    - ConsoleCommand 用于接收新增/修改请求：字符串字段使用@NotBlank，设置@Size(max=长度)；数字、枚举、布尔字段使用@NotNull；集合字段使用@NotEmpty；需要校验消息时补充message
                    - ConsoleQuery 承载分页查询条件：包含pageNo、pageSize、sort等基础字段；对于可空过滤条件可使用JavaDoc说明含义；避免复杂逻辑
                    - ConsoleView 作为投影类：所有Long/BigInteger字段使用@JsonFormat(shape = JsonFormat.Shape.STRING)，其余字段保持与模板一致
                    - CommandMapper、ConsoleQueryMapper 使用MapStruct，将Console层对象与service-api层DTO互转；保持@Mapping说明字段映射规则
                    - 内部如需调用ConsoleService时，ConsoleService使用@Service注解，聚合领域逻辑后再调用FeignClient
                    - 参考模板：consoleResource.ftl、consoleCommand.ftl、consoleQuery.ftl、consoleView.ftl、commandMapper.ftl、queryMapper.ftl，输出结构需与模板保持一致
                    """;
            case SERVICE_API -> """
                    ## Service-API层特定要求
                    - Service接口使用@RestController或@RequestMapping仅声明接口规范；Client使用@FeignClient指向Service接口；保持接口与实现一一对应
                    - Command/Query DTO 校验规则与Console层一致：字符串@NotBlank + @Size，数字/布尔/枚举@NotNull，集合@NotEmpty；分页Query包含pageNo/pageSize/sort
                    - View 投影对象：对Long/BigInteger字段添加@JsonFormat(shape = JsonFormat.Shape.STRING)；必要时补充注释说明字段含义
                    - FeignClient方法签名、路径、请求体、返回体必须与Service接口一致，保持@Validated和@RequestBody/@PathVariable等注解同步
                    - DTO 类通过JavaDoc注释说明字段含义；必要时添加@Builder或@AllArgsConstructor/NoArgsConstructor
                    - 参考模板：service.ftl、client.ftl、command.ftl、query.ftl、view.ftl
                    """;
            case SERVICE -> """
                    ## Service层特定要求
                    - Resource 实现 service-api 层接口：使用@RestController；方法中调用Repository或DomainService；处理事务时使用@Transactional
                    - Mapper 使用MapStruct：负责在Command/Query/View与领域模型之间转换，所有字段映射明确声明；日期类型、枚举类型需处理好转换
                    - DomainService 仅在业务复杂时创建，使用@Service注解，聚合多个Repository或领域操作
                    - Repository 接口定义仓储操作，禁止泄露MyBatis细节；MyBatisRepository 实现类放在infrastructure层，实现接口并调用Dao
                    - Dao 使用MyBatis Mapper或XML；遵循命名规范，如 insert/selectById/update/delete；避免业务逻辑
                    - Model 领域实体：
                      * 数据库字段以 is_ 开头（如 is_enabled），Java 字段命名去掉 is 前缀（enabled），并使用 @Column(name = "is_enabled") 标注数据库列名；布尔使用 Boolean/boolean
                      * 数据库唯一（unique）字段，生成 @Column(updatable = false) 以保证不可更新（遵循不可变自然键约束）；如需要可同时在表层面保留唯一索引
                      * Long/BigInteger 字段如用于对外交互，序列化使用 @JsonFormat(shape = JsonFormat.Shape.STRING)
                      * 关系映射：根据外键信息生成关联
                        - 多对一：@ManyToOne + @JoinColumn(name = "xxx_id")
                        - 一对多：@OneToMany(mappedBy = "parent")，集合类型使用 List/Set，注意懒加载与级联
                        - 一对一：@OneToOne + @JoinColumn(name = "xxx_id") 或 mappedBy；按外键所在方确定拥有方
                      * 对关联字段添加合适的 fetch/cascade 策略（默认 LAZY，必要时设置 CascadeType.MERGE/PERSIST）
                      * 必要时添加@Builder、静态工厂方法；保持不可变性或最小可变性
                    - 所有层之间使用依赖注入；Resource -> DomainService/Repository -> MyBatisRepository -> Dao 的依赖方向不可逆
                    - 参考模板：resource.ftl、mapper.ftl、repository.ftl、repository-impl.ftl、dao.ftl、model.ftl
                    """;
            case ENUM -> """
                    ## 枚举层特定要求
                    - 为表中枚举/字典字段生成Java枚举，名称语义化如 StatusEnum；若字段名以 _type/_status 结尾，可转换为 UpperCamelEnum
                    - 枚举实现 DescribedEnumValue<T> 接口：
                      * tinyint/bit → Byte
                      * int/integer → Integer
                      * varchar/char/text → String
                      * 其他类型默认使用 String，并在必要时标注注释
                    - 枚举常量定义 value (T 类型) 与 description 字段；构造函数接收 value、description；value 字段命名为 value，不加 is 前缀
                    - 提供静态 fromValue(T value) 方法，使用 Arrays.stream 查找，未匹配抛出 IllegalArgumentException；必要时保留 @JsonCreator/@JsonValue
                    - description 默认使用字段注释或中文含义；如无注释则给出 TODO 占位
                    - 参考模板：enum.ftl
                    """;
        };
    }
}

