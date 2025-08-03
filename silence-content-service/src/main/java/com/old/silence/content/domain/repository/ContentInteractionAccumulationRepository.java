package com.old.silence.content.domain.repository;

import com.old.silence.content.domain.model.ContentInteractionAccumulation;

import java.util.List;


/**
 * @author moryzang
 */
public interface ContentInteractionAccumulationRepository {

    int bulkCreate(List<ContentInteractionAccumulation> contentInteractionAccumulations);
}
