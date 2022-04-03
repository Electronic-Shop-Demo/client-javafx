package com.mairwunnx.application.application.router.configurators;

import javafx.util.Callback;
import org.jetbrains.annotations.Nullable;

import java.util.ResourceBundle;

public interface RouterConfiguration {
    boolean isAutomapping();

    boolean isAutosize();

    boolean isImplicitDefaults();

    @Nullable ResourceBundle getCurrentBundle();

    @Nullable Callback<Class<?>, Object> getControllerFactory();
}
