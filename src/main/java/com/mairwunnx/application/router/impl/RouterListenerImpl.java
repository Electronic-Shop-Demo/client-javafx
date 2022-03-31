package com.mairwunnx.application.router.impl;

import com.mairwunnx.application.router.configurators.RouterListener;
import com.mairwunnx.application.router.contracts.ListeningEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

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

    public void onNavigationRequested(@Nullable final ListeningEvent event) {
        setOnNavigationRequested(event);
    }

    public void onNavigationPerformed(@Nullable final ListeningEvent event) {
        setOnNavigationPerformed(event);
    }

    public void onBackRequested(@Nullable final ListeningEvent event) {
        setOnBackRequested(null);
    }

    public void onBackPerformed(@Nullable final ListeningEvent event) {
        setOnBackPerformed(event);
    }
}
