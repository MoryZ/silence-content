package com.old.silence.content.api.assembler;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import com.old.silence.content.api.config.SilenceMapStructSpringConfig;
import com.old.silence.content.api.dto.CodeFileSpecCommand;
import com.old.silence.content.domain.model.codegen.CodeFileSpec;

/**
 * 代码文件规格数据转换器
 * 
 * 负责 Command → Entity、Entity → View 的数据转换
 *
 * @author moryzang
 */
@Mapper(uses = SilenceMapStructSpringConfig.class)
public interface CodeFileSpecMapper extends Converter<CodeFileSpecCommand, CodeFileSpec> {

}
