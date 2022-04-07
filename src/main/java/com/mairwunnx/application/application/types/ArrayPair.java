package com.mairwunnx.application.application.types;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jetbrains.annotations.NotNull;

public final class ArrayPair {
    @NotNull
    public static <T> ImmutablePair<T, T> of(@NotNull final T[] array) {
        return ImmutablePair.of(array[0], array[1]);
    }
}
