package com.old.silence.content.util;

import java.security.SecureRandom;

/**
 * @author MurrayZhang
 * @Description
 */
public final class ContentCodeUtils {

    private ContentCodeUtils() {
        throw new AssertionError();
    }

    public static String generateCode(int length) {
        return String.valueOf(new SecureRandom().nextInt(length));
    }
}
