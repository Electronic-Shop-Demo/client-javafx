package com.mairwunnx.application.router.contracts;

import com.mairwunnx.application.router.configurators.RouterConfiguration;
import com.mairwunnx.application.router.types.RouterEntry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface AutoMapper {
    void use(
        @NotNull RouterConfiguration configuration,
        @NotNull List<RouterEntry> entries,
        @NotNull Class<?> callerClass
    );
}
