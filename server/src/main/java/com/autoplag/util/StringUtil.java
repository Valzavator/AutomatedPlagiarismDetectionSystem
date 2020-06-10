package com.autoplag.util;

public class StringUtil {
    public static String processWhitespace(String toProcess) {
        return toProcess.trim()
                .replaceAll("\\s+", " ");
    }
}
