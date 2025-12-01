package com.old.silence.content.console.vo;


import java.math.BigInteger;

/**
 * @author moryzang
 */
public interface ContentContentTagConsoleView {

    BigInteger getId();

    BigInteger getContentId();

    BigInteger getTagId();

    ContentTagConsoleView getContentTag();
}
