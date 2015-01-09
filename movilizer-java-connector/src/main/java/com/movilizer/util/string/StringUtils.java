package com.movilizer.util.string;

import com.google.common.primitives.Ints;
import org.apache.commons.lang.StringEscapeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.movilizer.util.dbc.Ensure.ensure;
import static com.movilizer.util.dbc.Ensure.ensureNotNullOrEmpty;
import static java.text.MessageFormat.format;

/**
 * @author Peter.Grigoriev@movilizer.com
 */
public class StringUtils {

    public static String formatNoLocale(String pattern, Object... arguments) {
        List<String> argumentsAsStrings = new ArrayList<String>(arguments.length);
        for (Object argument : arguments) {
            argumentsAsStrings.add(String.valueOf(argument));
        }
        return format(pattern, argumentsAsStrings.toArray());
    }

    public static String phoneToDeviceAddress(String phone) {
        ensureNotNullOrEmpty(phone, "phone");
        if (phone.startsWith("+")) {
            return phone;
        }
        ensure(phone.startsWith("00"), "phone must either start with '+' or '00'");
        return formatNoLocale("+{0}", phone.substring(2));
    }

    public static String emailToDeviceAddress(String email) {
        ensureNotNullOrEmpty(email, "email");
        if (email.startsWith("@")) {
            return email;
        }
        return "@" + email;
    }


    public static String joinOmitEmpties(String separator, Object... values) {
        StringBuilder builder = new StringBuilder();


        boolean firstOne = true;
        for (Object value : values) {
            if (value == null) {
                continue;
            }

            String strValue = String.valueOf(value).trim();
            if ("".equals(strValue)) {
                continue;
            }

            if (!firstOne) {
                builder.append(separator);
            }


            builder.append(strValue);

            firstOne = false;
        }
        return builder.toString();
    }

    public static String removePrefix(String string, String prefix) {
        if (isNullOrEmpty(string) || isNullOrEmpty(prefix)) {
            return string;
        }
        if (string.startsWith(prefix)) {
            return string.substring(prefix.length());
        }
        return string;

    }

    public static String removeSuffix(String string, String suffix) {
        if (isNullOrEmpty(string) || isNullOrEmpty(suffix)) {
            return string;
        }
        if (string.endsWith(suffix)) {
            return string.substring(0, string.length() - suffix.length());
        }
        return string;
    }

    public static String removeSuffixes(String string, String... suffixes) {
        if (isNullOrEmpty(string)) {
            return string;
        }

        for (String suffix : suffixes) {
            if (isNullOrEmpty(suffix)) {
                return string;
            }
            if (string.endsWith(suffix)) {
                return removeSuffix(string, suffix);
            }
        }
        return string;
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static String replaceXMLPredefinedEntities(String s) {
        return StringEscapeUtils.escapeXml(s);
    }


    public static String addLeading(char leading, String string, int desiredLength) {
        if (string == null) {
            string = "";
        }
        if (string.length() >= desiredLength) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int charactersToAdd = desiredLength - string.length();
        for (int i = 0; i < charactersToAdd; i++) {
            stringBuilder.append(leading);
        }
        stringBuilder.append(string);
        return stringBuilder.toString();
    }

    public static String addLeadingZeroes(String str, int desiredLength) {
        return addLeading('0', str, desiredLength);
    }

    public static Map<String, Integer> arrayToIndexMap(String[] strings) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < strings.length; i++) {
            String meterEventAttribute = strings[i];
            map.put(meterEventAttribute, i);
        }
        return map;
    }

    public static String dump(Iterable iterable) {
        if (iterable == null) {
            return "null";
        }
        StringBuilder builder = new StringBuilder("{");
        boolean first = true;
        for (Object o : iterable) {
            if (!first) {
                builder.append(", ");
            }
            first = false;
            builder.append(o);
        }
        builder.append('}');
        return builder.toString();
    }

    public static boolean canParseInt(String value) {
        return null != value && Ints.tryParse(value) != null;
    }


    public static boolean startsWith(String string, String prefix) {
        return isNullOrEmpty(prefix) || (!isNullOrEmpty(string) && string.startsWith(prefix));
    }
}
