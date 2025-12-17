package com.old.silence.content.util;

/**
 * @author moryzang
 */
public final class ContentCodeUtils {

    private ContentCodeUtils() {
        throw new AssertionError();
    }

    public static String generateCode() {
        return String.valueOf(BuildPrimaryIdUtils.getPrimaryId());
    }
}
