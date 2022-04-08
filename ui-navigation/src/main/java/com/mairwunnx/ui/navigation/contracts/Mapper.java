package com.mairwunnx.ui.navigation.contracts;

import com.mairwunnx.ui.navigation.configurators.RouterConfiguration;
import com.mairwunnx.ui.navigation.configurators.RouterMapper;
import com.mairwunnx.ui.navigation.types.RouterEntry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Mapper {
    void use(
        @NotNull RouterConfiguration configuration,
        @NotNull RouterMapper mapper,
        @NotNull List<RouterEntry> entries,
        @NotNull Class<?> callerClass
    );
}
