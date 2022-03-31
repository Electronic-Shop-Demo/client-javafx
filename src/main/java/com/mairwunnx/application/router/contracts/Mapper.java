package com.mairwunnx.application.router.contracts;

import com.mairwunnx.application.router.configurators.RouterConfiguration;
import com.mairwunnx.application.router.configurators.RouterMapper;
import com.mairwunnx.application.router.types.RouterEntry;
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
