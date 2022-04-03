package com.mairwunnx.application.application.router.types;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record RouterGraphEntry<T>(
    @NotNull String key,
    @NotNull String layout,
    @Nullable Size size,
    @NotNull String title,
    @Nullable RouterArg<T> arg
) {
}
