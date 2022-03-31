package com.mairwunnx.application.router.contracts;

import com.mairwunnx.application.router.types.RouterArg;
import com.mairwunnx.application.router.types.RouterEvent;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ListeningEvent {
    void action(@Nullable String key, @NotNull Stage stage, @Nullable RouterArg<?> arg, @NotNull RouterEvent e);
}
