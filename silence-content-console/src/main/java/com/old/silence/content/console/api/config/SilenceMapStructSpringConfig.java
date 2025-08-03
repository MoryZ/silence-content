package com.old.silence.content.console.api.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.extensions.spring.SpringMapperConfig;
import com.old.silence.core.mapstruct.MapStructSpringConfig;

/**
 * @author moryzang
 * @Description
 */
@SpringMapperConfig
@MapperConfig(uses = {MapStructSpringConfig.class, ConversionServiceAdapter.class})
public class SilenceMapStructSpringConfig {
}