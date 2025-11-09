package com.old.silence.content.infrastructure.persistence.callback;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.relational.core.mapping.event.AfterConvertCallback;
import org.springframework.stereotype.Component;
import com.old.silence.autoconfigure.minio.MinioTemplate;
import com.old.silence.content.domain.model.PoetryDailyStudyPlan;

/**
 * @author moryzang
 */
@Component
public class PoetryDailyStudyPlanAfterConvert implements AfterConvertCallback<PoetryDailyStudyPlan> {


    private final MinioTemplate minioTemplate;

    public PoetryDailyStudyPlanAfterConvert(MinioTemplate cosTemplate) {
        this.minioTemplate = cosTemplate;
    }

    @Override
    public PoetryDailyStudyPlan onAfterConvert(PoetryDailyStudyPlan poetryDailyStudyPlan) {
        if (poetryDailyStudyPlan.getPoetryCategory() != null) {
            var icon = poetryDailyStudyPlan.getPoetryCategory().getIcon();
            if (StringUtils.isNotBlank(icon)) {

                var fileKey = StringUtils.substringBefore(icon, "-");
                var filename = StringUtils.substringAfter(icon, "-");
                var presignedObjectUrl = minioTemplate.getInternetUrl(fileKey, filename);
                poetryDailyStudyPlan.getPoetryCategory().setIcon(presignedObjectUrl);
            }
        }

        return poetryDailyStudyPlan;
    }
}
