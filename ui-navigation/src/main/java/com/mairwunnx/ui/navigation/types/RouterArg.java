package com.mairwunnx.ui.navigation.types;

import org.jetbrains.annotations.NotNull;

public record RouterArg<T>(@NotNull String key, @NotNull T value) {
}
