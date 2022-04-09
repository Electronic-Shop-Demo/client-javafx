package com.mairwunnx.ui.navigation.impl;

import com.mairwunnx.ui.navigation.configurators.RouterConfiguration;
import javafx.util.BuilderFactory;
import javafx.util.Callback;
import lombok.Getter;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ResourceBundle;

@SuppressWarnings({"UnusedReturnValue", "unused"})
@ApiStatus.Internal
public final class RouterConfigurationImpl implements RouterConfiguration {
    private boolean isInitialized;
    @Getter
    private boolean automapping;
    @Getter
    private boolean autosize;
    @Getter
    private boolean implicitDefaults;
    @Nullable
    @Getter
    private ResourceBundle currentBundle;

    @Nullable
    @Getter
    private Callback<Class<?>, Object> controllerFactory;

    @Nullable
    @Getter
    private BuilderFactory builderFactory;

    @NotNull
    public RouterConfigurationImpl automapping(final boolean value) {
        requireIsNotInitialized();
        automapping = value;
        return this;
    }

    @NotNull
    public RouterConfigurationImpl autosize(final boolean value) {
        requireIsNotInitialized();
        autosize = value;
        return this;
    }

    @NotNull
    public RouterConfigurationImpl implicitDefaults(final boolean value) {
        requireIsNotInitialized();
        implicitDefaults = value;
        return this;
    }

    @NotNull
    public RouterConfigurationImpl withBundle(final ResourceBundle bundle) {
        requireIsNotInitialized();
        currentBundle = bundle;
        return this;
    }

    @NotNull
    public RouterConfigurationImpl controllerFactory(final Callback<Class<?>, Object> factory) {
        requireIsNotInitialized();
        controllerFactory = factory;
        return this;
    }

    @NotNull
    public RouterConfigurationImpl builderFactory(final BuilderFactory factory) {
        requireIsNotInitialized();
        builderFactory = factory;
        return this;
    }

    public void ensureInitialize() {
        requireIsNotInitialized();
        isInitialized = true;
    }

    private void requireIsNotInitialized() {
        if (isInitialized) throw new IllegalStateException("Router configuration already initialized!");
    }
}
