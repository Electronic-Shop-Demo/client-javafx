package com.mairwunnx.ui.navigation.builders;

import com.mairwunnx.ui.navigation.impl.RouterMapperImpl;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface RouterMapperBuilder {
    void apply(@NotNull RouterMapperImpl mapper);
}
