package com.mairwunnx.ui.navigation;

import com.mairwunnx.ui.navigation.builders.RouterBuilder;
import com.mairwunnx.ui.navigation.impl.RouterBuilderImpl;
import com.mairwunnx.ui.navigation.impl.RouterImpl;
import org.jetbrains.annotations.NotNull;

/**
 * JavaFX router builder interface, use this for building router
 * beautifully.
 *
 * @since 1.0.0.
 */
@SuppressWarnings("unused")
public interface RouterFX {
    /**
     * Does build new router instance.
     *
     * @param router non-nullable router builder interface.
     * @return new router non-nullable {@link Router} instance.
     * @since 1.0.0.
     */
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
