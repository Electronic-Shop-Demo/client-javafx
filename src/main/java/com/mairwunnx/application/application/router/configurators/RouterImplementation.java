package com.mairwunnx.application.application.router.configurators;

import com.mairwunnx.application.application.router.contracts.AutoMapper;
import com.mairwunnx.application.application.router.contracts.Mapper;
import org.jetbrains.annotations.Nullable;

public interface RouterImplementation {
    @Nullable AutoMapper getAutomapper();

    @Nullable Mapper getMapper();
}
