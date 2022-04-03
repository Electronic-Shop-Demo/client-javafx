package com.mairwunnx.application.application.router;

import com.mairwunnx.application.application.router.builders.RouterBuilder;
import com.mairwunnx.application.application.router.impl.RouterBuilderImpl;
import com.mairwunnx.application.application.router.impl.RouterImpl;
import org.jetbrains.annotations.NotNull;

public interface RouterFX {
    static @NotNull Router build(final @NotNull RouterBuilder router) {
        final var builder = new RouterBuilderImpl();
        router.apply(builder);
        return new RouterImpl(
            builder.getStage(),
            builder.getConfiguration(),
            builder.getMapper(),
            builder.getListener(),
            builder.getRouterEntries()
        );
    }
}
