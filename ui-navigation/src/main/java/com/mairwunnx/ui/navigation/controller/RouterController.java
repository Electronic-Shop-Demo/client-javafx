package com.mairwunnx.ui.navigation.controller;

import com.mairwunnx.ui.navigation.Router;
import com.mairwunnx.ui.navigation.types.RouterArg;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"unchecked", "unused"})
public interface RouterController {
    @Nullable
    default <T> T getArgumentFromOrNull(@Nullable final RouterArg<?> arg, @Nullable final String key) {
        if (arg != null) {
            return arg.key().equals(key) ? (T) arg.value() : null;
        }

        return null;
    }

    @NotNull
    default <T> T getArgumentFrom(@Nullable final RouterArg<?> arg, @Nullable final String key) {
        if (arg != null) {
            if (arg.key().equals(key)) return (T) arg.value();
            throw new NullPointerException();
        }

        throw new NullPointerException();
    }

    <T> void onShow(@NotNull final Router router, @Nullable final RouterArg<T> arg);

    default void onExit(@NotNull final Router router) {
    }
}
