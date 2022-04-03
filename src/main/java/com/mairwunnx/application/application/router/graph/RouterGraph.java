package com.mairwunnx.application.application.router.graph;

import com.mairwunnx.application.application.router.types.RouterGraphEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface RouterGraph {
    @Nullable <T> RouterGraphEntry<T> getLast();

    @Nullable <T> RouterGraphEntry<T> getFirst();

    @NotNull List<RouterGraphEntry<?>> getPages();

    void clear();
}
