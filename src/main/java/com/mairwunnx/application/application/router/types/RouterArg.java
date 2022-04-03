package com.mairwunnx.application.application.router.types;

import org.jetbrains.annotations.NotNull;

public record RouterArg<T>(@NotNull String key, @NotNull T value) {
}
