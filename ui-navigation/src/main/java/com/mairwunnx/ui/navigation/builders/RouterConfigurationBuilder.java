package com.mairwunnx.ui.navigation.builders;

import com.mairwunnx.ui.navigation.impl.RouterConfigurationImpl;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface RouterConfigurationBuilder {
    void apply(@NotNull RouterConfigurationImpl config);
}
