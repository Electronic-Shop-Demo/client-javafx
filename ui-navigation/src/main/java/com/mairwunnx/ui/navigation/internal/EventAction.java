package com.mairwunnx.ui.navigation.internal;

import com.mairwunnx.ui.navigation.types.RouterArg;
import com.mairwunnx.ui.navigation.types.RouterEvent;

@FunctionalInterface
public interface EventAction {
    <T> void action(final String key, final RouterArg<T> arg, final RouterEvent requestedEvent);
}
