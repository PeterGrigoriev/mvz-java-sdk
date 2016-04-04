package com.movilizer.reply.converter;

import org.apache.commons.lang.BooleanUtils;

import static com.movilizer.util.string.StringUtils.isNullOrEmpty;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class BooleanHelper {
    public static boolean toBoolean(String string) {
        if (isNullOrEmpty(string)) {
            return false;
        }
        string = string.toLowerCase();
        return string.startsWith("o") || !string.startsWith("n") && BooleanUtils.toBoolean(string);
    }

}
