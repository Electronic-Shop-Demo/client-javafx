package com.mairwunnx.application.router.builders;

import com.mairwunnx.application.router.impl.RouterMapperImpl;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface RouterMapperBuilder {
    void apply(@NotNull RouterMapperImpl mapper);
}
