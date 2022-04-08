package com.mairwunnx.ui.navigation.contracts;

import com.mairwunnx.ui.navigation.types.RouterArg;
import com.mairwunnx.ui.navigation.types.RouterEvent;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ListeningEvent {
    void action(@Nullable String key, @NotNull Stage stage, @Nullable RouterArg<?> arg, @NotNull RouterEvent e);
}
