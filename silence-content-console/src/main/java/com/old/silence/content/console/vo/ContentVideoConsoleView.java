package com.old.silence.content.console.vo;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigInteger;

/**
 * @author moryzang
 */
public interface ContentVideoConsoleView {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    BigInteger getId();

    String getDescription();

    Integer getDuration();

    Integer getWidth();

    Integer getHeight();

    String getVerticalCoverImageReference();

    String getScriptFilename();

    String getScriptFileKey();

    String getVideoName();

    ContentConsoleView getContent();

}
