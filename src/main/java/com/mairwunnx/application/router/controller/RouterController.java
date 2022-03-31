package com.mairwunnx.application.router.controller;

import com.mairwunnx.application.router.types.RouterArg;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    <T> void onShow(@NotNull Stage stage, @NotNull Scene scene, @Nullable RouterArg<T> arg);

    void onExit(@NotNull Stage stage, @NotNull Scene scene);
}
