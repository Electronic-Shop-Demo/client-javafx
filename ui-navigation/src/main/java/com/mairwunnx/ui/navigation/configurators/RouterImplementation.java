package com.mairwunnx.ui.navigation.configurators;

import com.mairwunnx.ui.navigation.contracts.AutoMapper;
import com.mairwunnx.ui.navigation.contracts.Mapper;
import org.jetbrains.annotations.Nullable;

public interface RouterImplementation {
    @Nullable AutoMapper getAutomapper();

    @Nullable Mapper getMapper();
}
