package com.mairwunnx.ui.navigation.graph;

import com.mairwunnx.ui.navigation.types.RouterGraphEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("unused")
public interface RouterGraph {
    @Nullable <T> RouterGraphEntry<T> getLast();

    @Nullable <T> RouterGraphEntry<T> getFirst();

    @NotNull List<RouterGraphEntry<?>> getPages();

    void clear();
}
