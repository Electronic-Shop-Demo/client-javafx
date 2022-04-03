package com.mairwunnx.application.router.builders;

import com.mairwunnx.application.router.impl.RouterListenerImpl;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface RouterListenerBuilder {
    void apply(@NotNull RouterListenerImpl listener);
}
