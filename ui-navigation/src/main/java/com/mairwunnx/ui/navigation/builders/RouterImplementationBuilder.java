package com.mairwunnx.ui.navigation.builders;

import com.mairwunnx.ui.navigation.impl.RouterImplementationImpl;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface RouterImplementationBuilder {
    void apply(@NotNull RouterImplementationImpl implementation);
}
