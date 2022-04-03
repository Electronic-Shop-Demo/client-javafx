package com.mairwunnx.application.application.router.builders;

import com.mairwunnx.application.application.router.impl.RouterConfigurationImpl;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface RouterConfigurationBuilder {
    void apply(@NotNull RouterConfigurationImpl config);
}
