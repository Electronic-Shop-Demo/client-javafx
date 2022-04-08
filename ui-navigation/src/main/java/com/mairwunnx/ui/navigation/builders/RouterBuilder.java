package com.mairwunnx.ui.navigation.builders;

import com.mairwunnx.ui.navigation.impl.RouterBuilderImpl;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface RouterBuilder {
    void apply(@NotNull RouterBuilderImpl router);
}
