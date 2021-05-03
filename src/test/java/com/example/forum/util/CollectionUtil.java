package com.example.forum.util;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class CollectionUtil {
    private CollectionUtil() {
        // util
    }

    public static <T> T getExactlyOne(T[] items, Predicate<T> predicate) {
        return getExactlyOne(Arrays.stream(items), predicate);
    }

    public static <T> T getExactlyOne(Stream<T> stream, Predicate<T> predicate) {
        return stream.filter(predicate)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Expected exactly 1 element, but found none"));
    }

}
