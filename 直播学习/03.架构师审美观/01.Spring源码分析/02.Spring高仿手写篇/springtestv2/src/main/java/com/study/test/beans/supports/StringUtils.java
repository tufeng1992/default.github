package com.study.test.beans.supports;

import com.sun.istack.internal.Nullable;

public class StringUtils {

    public static String trimAllWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        } else {
            int len = str.length();
            StringBuilder sb = new StringBuilder(str.length());

            for(int i = 0; i < len; ++i) {
                char c = str.charAt(i);
                if (!Character.isWhitespace(c)) {
                    sb.append(c);
                }
            }

            return sb.toString();
        }
    }

    public static boolean hasLength(@Nullable String str) {
        return str != null && !str.isEmpty();
    }


    public static boolean isBlank(String str) {
        return null == str || "".equals(str);
    }
}
