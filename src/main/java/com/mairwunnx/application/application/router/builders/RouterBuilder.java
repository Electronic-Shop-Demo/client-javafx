package com.mairwunnx.application.application.router.builders;

import com.mairwunnx.application.application.router.impl.RouterBuilderImpl;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface RouterBuilder {
    void apply(@NotNull RouterBuilderImpl router);
}
