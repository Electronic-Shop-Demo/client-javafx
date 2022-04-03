package com.mairwunnx.application.application.router.configurators;

import com.mairwunnx.application.application.router.types.RouterMappedEntry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface RouterMapper {
    @NotNull List<RouterMappedEntry> getMapped();
}
