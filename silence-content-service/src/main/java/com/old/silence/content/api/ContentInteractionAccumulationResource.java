package com.old.silence.content.api;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.dto.ContentInteractionAccumulationCommand;
import com.old.silence.content.domain.model.ContentInteractionAccumulation;
import com.old.silence.content.domain.repository.ContentInteractionAccumulationRepository;

import java.math.BigInteger;
import java.util.List;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * 互动累计资源控制器
 */
@Validated
@RestController
@RequestMapping("/api/v1")
public class ContentInteractionAccumulationResource implements ContentInteractionAccumulationService {

    private final ContentInteractionAccumulationRepository contentInteractionAccumulationRepository;

    public ContentInteractionAccumulationResource(ContentInteractionAccumulationRepository contentInteractionAccumulationRepository) {
        this.contentInteractionAccumulationRepository = contentInteractionAccumulationRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchUpsert(List<ContentInteractionAccumulationCommand> commands) {
        int total = 0;
        for (ContentInteractionAccumulationCommand command : commands) {
            ContentInteractionAccumulation accumulation = new ContentInteractionAccumulation();
            accumulation.setResourceId(command.getResourceId());
            accumulation.setResourceType(command.getResourceType());
            accumulation.setType(command.getInteractionType());
            accumulation.setAccumulation(command.getAccumulation());
            total += validateAndUpsert(accumulation);
        }
        return total;
    }

    private int validateAndUpsert(ContentInteractionAccumulation accumulation) {
        return validateModifyingResult(contentInteractionAccumulationRepository.upsertAccumulationByResourceIdAndResourceTypeAndType(
                accumulation.getAccumulation(), accumulation.getResourceId(), accumulation.getResourceType(), accumulation.getType()));
    }
}