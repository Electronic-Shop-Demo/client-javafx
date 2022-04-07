package com.mairwunnx.application.application.router.impl;

import com.mairwunnx.application.application.router.Router;
import com.mairwunnx.application.application.router.configurators.RouterListener;
import com.mairwunnx.application.application.router.contracts.ListeningEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ResourceBundle;
import java.util.function.BiConsumer;

@SuppressWarnings("unused")
@ApiStatus.Internal
public final class RouterListenerImpl implements RouterListener {
    @Nullable
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private ListeningEvent onNavigationRequested;

    @Nullable
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private ListeningEvent onNavigationPerformed;

    @Nullable
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private ListeningEvent onBackRequested;

    @Nullable
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private ListeningEvent onBackPerformed;

    @Nullable
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private BiConsumer<Router, ResourceBundle> onResourceBundleChanged;

    public void onNavigationRequested(@NotNull final ListeningEvent event) {
        setOnNavigationRequested(event);
    }

    public void onNavigationPerformed(@NotNull final ListeningEvent event) {
        setOnNavigationPerformed(event);
    }

    public void onBackRequested(@NotNull final ListeningEvent event) {
        setOnBackRequested(null);
    }

    public void onBackPerformed(@NotNull final ListeningEvent event) {
        setOnBackPerformed(event);
    }

    public void onResourceBundleChanged(@NotNull final BiConsumer<Router, ResourceBundle> onBundleChanged) {
        setOnResourceBundleChanged(onBundleChanged);
    }
}
