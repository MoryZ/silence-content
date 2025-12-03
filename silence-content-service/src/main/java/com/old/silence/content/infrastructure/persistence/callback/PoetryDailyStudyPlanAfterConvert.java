package com.old.silence.content.infrastructure.persistence.callback;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.relational.core.mapping.event.AfterConvertCallback;
import org.springframework.stereotype.Component;
import com.old.silence.content.domain.model.PoetryDailyStudyPlan;
import com.old.silence.content.file.factory.FileStorageFactory;

/**
 * @author moryzang
 */
@Component
public class PoetryDailyStudyPlanAfterConvert implements AfterConvertCallback<PoetryDailyStudyPlan> {


    private final FileStorageFactory fileStorageFactory;

    public PoetryDailyStudyPlanAfterConvert(FileStorageFactory fileStorageFactory) {
        this.fileStorageFactory = fileStorageFactory;
    }

    @Override
    public PoetryDailyStudyPlan onAfterConvert(PoetryDailyStudyPlan poetryDailyStudyPlan) {
        var storageTemplate = fileStorageFactory.getStorageTemplate();
        if (poetryDailyStudyPlan.getPoetryCategory() != null) {
            var icon = poetryDailyStudyPlan.getPoetryCategory().getIcon();
            if (StringUtils.isNotBlank(icon)) {

                var presignedObjectUrl = storageTemplate.getPreviewUrl(icon);
                poetryDailyStudyPlan.getPoetryCategory().setIcon(presignedObjectUrl);
            }
        }
        return poetryDailyStudyPlan;
    }
}
