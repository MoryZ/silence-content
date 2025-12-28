package com.old.silence.content.code.generator.support;

import org.apache.commons.lang3.StringUtils;
import com.old.silence.content.code.generator.model.TableInfo;
import com.old.silence.content.code.generator.util.NameConverterUtils;
public class FileOutputService {
    public String fileName(TableInfo tableInfo, String suffix) {
        return NameConverterUtils.toCamelCase(tableInfo.getTableName(), true)
                + (StringUtils.isNotBlank(suffix) ? suffix : "")
                + ".java";
    }
}
