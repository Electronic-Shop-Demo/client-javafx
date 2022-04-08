package com.mairwunnx.ui.navigation.types;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record RouterEntry(
    @NotNull String key,
    @NotNull String layout,
    @NotNull String title,
    @Nullable Size size
) {
}
