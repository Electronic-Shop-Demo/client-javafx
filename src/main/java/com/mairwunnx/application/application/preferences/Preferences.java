package com.mairwunnx.application.application.preferences;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public interface Preferences {
    @Nullable
    String getStringOrDefault(@NotNull final String key, @Nullable final String defaultValue);

    int getIntOrDefault(@NotNull final String key, final int defaultValue);

    boolean getBooleanOrFalse(@NotNull final String key);

    @NotNull
    Preferences setString(@NotNull final String key, @NotNull final String value);

    @NotNull
    Preferences setInt(@NotNull final String key, final int value);

    @NotNull
    Preferences setBoolean(@NotNull final String key, final boolean value);

    CompletableFuture<Void> commit();

    void commitSynchronously();
}
