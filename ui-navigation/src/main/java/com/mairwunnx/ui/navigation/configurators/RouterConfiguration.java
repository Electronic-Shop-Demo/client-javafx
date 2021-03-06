package com.mairwunnx.ui.navigation.configurators;

import javafx.util.BuilderFactory;
import javafx.util.Callback;
import org.jetbrains.annotations.Nullable;

import java.util.ResourceBundle;

public interface RouterConfiguration {
    boolean isAutomapping();

    boolean isAutosize();

    boolean isImplicitDefaults();

    @Nullable ResourceBundle getCurrentBundle();

    @Nullable Callback<Class<?>, Object> getControllerFactory();

    @Nullable BuilderFactory getBuilderFactory();
}
