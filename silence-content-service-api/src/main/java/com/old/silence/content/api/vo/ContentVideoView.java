package com.old.silence.content.api.vo;

import org.springframework.data.web.ProjectedPayload;

import java.math.BigInteger;

/**
 * @author moryzang
 */
@ProjectedPayload
public interface ContentVideoView {

    BigInteger getId();

    String getDescription();

    Integer getDuration();

    Integer getWidth();

    Integer getHeight();

    String getVerticalCoverImageReference();

    String getScriptFilename();

    String getScriptFileKey();

    String getVideoName();

    ContentView getContent();
}
