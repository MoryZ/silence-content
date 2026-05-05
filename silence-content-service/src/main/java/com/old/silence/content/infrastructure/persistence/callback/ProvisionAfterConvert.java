package com.old.silence.content.infrastructure.persistence.callback;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.relational.core.mapping.event.AfterConvertCallback;
import org.springframework.stereotype.Component;
import com.old.silence.content.domain.model.Provision;
import com.old.silence.content.file.factory.FileStorageFactory;

/**
 * @author moryzang
 */
@Component
public class ProvisionAfterConvert implements AfterConvertCallback<Provision> {


    private final FileStorageFactory fileStorageFactory;

    public ProvisionAfterConvert(FileStorageFactory fileStorageFactory) {
        this.fileStorageFactory = fileStorageFactory;
    }

    @Override
    public Provision onAfterConvert(Provision provision) {
        var storageTemplate = fileStorageFactory.getStorageTemplate();

        if (StringUtils.isNotBlank(provision.getUrl())) {
            var presignedObjectUrl = storageTemplate.getPreviewUrl(provision.getUrl());
            provision.setUrl(presignedObjectUrl);
        }
        return provision;
    }
}
