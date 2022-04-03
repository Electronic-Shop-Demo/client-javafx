package com.mairwunnx.application.application.router.builders;

import com.mairwunnx.application.application.router.impl.RouterImplementationImpl;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface RouterImplementationBuilder {
    void apply(@NotNull RouterImplementationImpl implementation);
}
