package com.mairwunnx.ui.navigation.builders;

import com.mairwunnx.ui.navigation.impl.RouterListenerImpl;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface RouterListenerBuilder {
    void apply(@NotNull RouterListenerImpl listener);
}
