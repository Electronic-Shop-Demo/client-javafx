package com.mairwunnx.application.router.types;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public record RouterMappedEntry(
    @Nullable String key,
    @NotNull String layout,
    @Nullable String title,
    @Nullable Size size,
    boolean implicitDefaults
) {
}
