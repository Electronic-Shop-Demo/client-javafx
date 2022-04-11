package com.mairwunnx.application.application.controllers;

import com.mairwunnx.ui.navigation.Router;
import com.mairwunnx.ui.navigation.controller.RouterController;
import com.mairwunnx.ui.navigation.types.RouterArg;
import javafx.scene.Node;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Router controller base contract implementation
 * All controllers must extend this abstract class.
 *
 * @see RouterController
 * @since 1.0.0.
 */
@SuppressWarnings("unchecked")
public abstract sealed class Controller implements RouterController permits HomeController {
    /**
     * Current stage router instance.
     *
     * @see Router
     * @since 1.0.0.
     */
    @Getter
    @Setter(AccessLevel.PROTECTED)
    @Nullable
    private Router router;

    /**
     * Root node of markup. Need for doing some stuff for this view.
     * For example, request focus on root when scene shown.
     *
     * @since 1.0.0.
     */
    @NotNull
    protected abstract Node getRoot();

    @Override
    public <T> @Nullable T getArgumentFromOrNull(@Nullable final RouterArg<?> arg, @Nullable final String key) {
        if (arg != null) {
            return arg.key().equals(key) ? (T) arg.value() : null;
        }

        return null;
    }

    @Override
    public <T> @NotNull T getArgumentFrom(@Nullable final RouterArg<?> arg, @Nullable final String key) {
        if (arg != null) {
            if (arg.key().equals(key)) return (T) arg.value();
            throw new NullPointerException();
        }

        throw new NullPointerException();
    }

    /**
     * On show controller lifecycle method.
     * <p/>
     * Calls after navigate calling (and after initialize from JFX called).
     * <p/>
     * Note: Super method must be called before your code.
     *
     * @param router {@link Router} class instance.
     * @param arg    {@link RouterArg} router argument class instance.
     * @param <T>    generic of argument value type.
     * @since 1.0.0.
     */
    @Override
    public <T> void onShow(@NotNull final Router router, @Nullable final RouterArg<T> arg) {
        setRouter(router);
        getRoot().requestFocus();
    }

    @Override
    public void onExit(@NotNull final Router router) {
    }
}
