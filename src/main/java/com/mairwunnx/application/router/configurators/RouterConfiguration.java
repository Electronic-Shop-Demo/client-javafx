package com.mairwunnx.application.router.configurators;

import org.jetbrains.annotations.Nullable;

import java.util.ResourceBundle;

public interface RouterConfiguration {
    boolean isAutomapping();

    boolean isAutosize();

    boolean isImplicitDefaults();

    @Nullable ResourceBundle getCurrentBundle();
}
