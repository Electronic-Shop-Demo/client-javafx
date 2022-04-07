package com.mairwunnx.application.application.router.impl;

import com.mairwunnx.application.application.router.configurators.RouterImplementation;
import com.mairwunnx.application.application.router.contracts.AutoMapper;
import com.mairwunnx.application.application.router.contracts.Mapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public class RouterImplementationImpl implements RouterImplementation {
    @Nullable
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private AutoMapper automapper;

    @Nullable
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private Mapper mapper;

    @NotNull
    public RouterImplementationImpl setAutomappingImpl(final @NotNull AutoMapper automappingImpl) {
        if (automapper != null) throw new IllegalStateException("Auto mapper implementation already assigned");
        automapper = automappingImpl;
        return this;
    }

    @NotNull
    public RouterImplementationImpl setMappingImpl(final @NotNull Mapper mappingImpl) {
        if (mapper != null) throw new IllegalStateException("Mapper implementation already assigned");
        mapper = mappingImpl;
        return this;
    }
}
