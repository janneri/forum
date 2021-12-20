package com.example.forum.util;

import com.example.forum.exception.InvalidJsonException;

public final class InputValidationUtil {
    private InputValidationUtil() {
        // util
    }

    public static void requireTextLength(String text, int minLength) {
        if (text == null) {
            throw new InvalidJsonException("Required field missing");
        }

        if (text.length() < minLength) {
            throw new InvalidJsonException("Input value too short");
        }
    }
}

