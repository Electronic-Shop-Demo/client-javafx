package com.mairwunnx.ui.navigation.configurators;

import com.mairwunnx.ui.navigation.types.RouterMappedEntry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface RouterMapper {
    @NotNull List<RouterMappedEntry> getMapped();
}
