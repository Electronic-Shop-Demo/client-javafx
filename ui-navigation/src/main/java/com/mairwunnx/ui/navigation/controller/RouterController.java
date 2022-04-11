package com.mairwunnx.ui.navigation.controller;

import com.mairwunnx.ui.navigation.Router;
import com.mairwunnx.ui.navigation.types.RouterArg;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Router controller behavior contract.
 * All controllers must implement this interface.
 *
 * @since 1.0.0.
 */
@SuppressWarnings("unused")
public interface RouterController {
    /**
     * @param arg argument object received from {@link RouterController#onShow(Router, RouterArg)}
     * @param key key of argument.
     * @param <T> generic type of argument value.
     * @return value of argument received by specified key parameter or null.
     * @since 1.0.0.
     */
    @Nullable <T> T getArgumentFromOrNull(@Nullable final RouterArg<?> arg, @Nullable final String key);

    /**
     * @param arg argument object received from {@link RouterController#onShow(Router, RouterArg)}
     * @param key key of argument.
     * @param <T> generic type of argument value.
     * @return value of argument received by specified key parameter or throw {@link NullPointerException}.
     * @since 1.0.0.
     */
    @NotNull <T> T getArgumentFrom(@Nullable final RouterArg<?> arg, @Nullable final String key);

    /**
     * On show controller lifecycle method.
     * <p/>
     * Calls after navigate calling (and after initialize from JFX called).
     *
     * @param router {@link Router} class instance.
     * @param arg    {@link RouterArg} router argument class instance.
     * @param <T>    generic of argument value type.
     * @since 1.0.0.
     */
    <T> void onShow(@NotNull final Router router, @Nullable final RouterArg<T> arg);

    /**
     * On exit controller lifecycle method.
     * <p/>
     * Calls before application shutdown when router shutdown method called.
     *
     * @param router {@link Router} class instance.
     * @see Router#shutdown().
     * @since 1.0.0.
     */
    void onExit(@NotNull final Router router);
}
