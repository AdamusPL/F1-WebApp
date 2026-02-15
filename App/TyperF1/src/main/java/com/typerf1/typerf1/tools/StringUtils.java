package com.typerf1.typerf1.tools;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtils {

    // Helper method to process a single string
    public static String stripAccents(String input) {
        if (input == null) {
            return null;
        }

        // 1. Decompose the unicode characters (e.g., "é" becomes "e" + "´")
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);

        // 2. Remove the accent marks (non-spacing marks) using Regex
        Pattern pattern = Pattern.compile("\\p{M}");
        return pattern.matcher(normalized).replaceAll("");
    }
}