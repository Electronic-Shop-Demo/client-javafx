package com.mairwunnx.application.application.router.builders;

import com.mairwunnx.application.application.router.impl.RouterMapperImpl;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface RouterMapperBuilder {
    void apply(@NotNull RouterMapperImpl mapper);
}
