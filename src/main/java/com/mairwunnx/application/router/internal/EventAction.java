package com.mairwunnx.application.router.internal;

import com.mairwunnx.application.router.types.RouterArg;
import com.mairwunnx.application.router.types.RouterEvent;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@FunctionalInterface
public interface EventAction {
    <T> void action(final String key, final RouterArg<T> arg, final RouterEvent requestedEvent);
}
