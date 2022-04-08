package com.mairwunnx.ui.navigation.contracts;

import com.mairwunnx.ui.navigation.configurators.RouterConfiguration;
import com.mairwunnx.ui.navigation.types.RouterEntry;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@ApiStatus.Experimental
public interface AutoMapper {
    void use(
        @NotNull RouterConfiguration configuration,
        @NotNull List<RouterEntry> entries,
        @NotNull Class<?> callerClass
    );
}
