package com.mairwunnx.application.application.router.builders;

import com.mairwunnx.application.application.router.impl.RouterListenerImpl;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface RouterListenerBuilder {
    void apply(@NotNull RouterListenerImpl listener);
}
