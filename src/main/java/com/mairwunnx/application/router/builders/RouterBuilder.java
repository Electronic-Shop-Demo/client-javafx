package com.mairwunnx.application.router.builders;

import com.mairwunnx.application.router.impl.RouterBuilderImpl;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface RouterBuilder {
    void apply(@NotNull RouterBuilderImpl router);
}
