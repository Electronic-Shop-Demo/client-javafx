package com.mairwunnx.application.router.configurators;

import com.mairwunnx.application.router.contracts.AutoMapper;
import com.mairwunnx.application.router.contracts.Mapper;
import org.jetbrains.annotations.Nullable;

public interface RouterImplementation {
    @Nullable AutoMapper getAutomapper();

    @Nullable Mapper getMapper();
}
