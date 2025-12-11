package com.old.silence.content.code.generator.runner;

import com.old.silence.content.code.generator.api.CodeGenerator;
import com.old.silence.content.code.generator.model.ColumnInfo;
import com.old.silence.content.code.generator.model.TableInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;

@Component
@Profile("dev")
public class CodeGeneratorSanityRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(CodeGeneratorSanityRunner.class);

    private final CodeGenerator codeGenerator;

    public CodeGeneratorSanityRunner(CodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
    }

    @Override
    public void run(String... args) throws Exception {
        // 构造一个最小的 TableInfo 进行渲染验证
        TableInfo table = new TableInfo();
        table.setTableName("demo_table");
        table.setPrimaryKeys(new ArrayList<>() {{ add("id"); }});
        table.setColumnInfos(new ArrayList<>() {{
            ColumnInfo id = new ColumnInfo();
            id.setOriginalName("id");
            id.setFieldName("id");
            id.setFieldType("BIGINT");
            id.setNullable(false);
            add(id);

            ColumnInfo name = new ColumnInfo();
            name.setOriginalName("name");
            name.setFieldName("name");
            name.setType("VARCHAR");
            name.setNullable(false);
            add(name);
        }});

        // 使用 classpath 模板回退（如果DB没有该模板）
        String content = codeGenerator.renderTemplate(table,
                "com.old.silence.content", ".domain.model", "model.ftl");
        log.info("Sanity 渲染成功，长度: {}", content.length());
    }
}
