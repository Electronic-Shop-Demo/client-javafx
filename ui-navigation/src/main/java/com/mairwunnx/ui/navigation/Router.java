package com.mairwunnx.ui.navigation;

import com.mairwunnx.ui.navigation.configurators.RouterConfiguration;
import com.mairwunnx.ui.navigation.graph.RouterGraph;
import com.mairwunnx.ui.navigation.types.RouterArg;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ResourceBundle;

/**
 * JavaFX router contract, this is all methods for interacting
 * with router.
 *
 * @since 1.0.0.
 */
@SuppressWarnings("unused")
public interface Router {
    /**
     * Does ensure stage for router. You must call it before
     * any interactions with router in case if you have not
     * passed stage in configuration.
     *
     * @param stage non-nullable stage you want to ensure.
     * @since 1.0.0.
     */
    void ensureStage(@NotNull Stage stage);

    /**
     * Does ensure bundle for router. You can call this at
     * any time, before scene switching for example etc.
     * <p>
     * You also can provide this in router configuration.
     *
     * @param bundle non-nullable bundle you want to ensure.
     * @since 1.0.0.
     */
    void ensureBundle(@NotNull ResourceBundle bundle);

    /**
     * Does ensure stylesheet for router. You can call this at
     * any time, before scene switching for example etc.
     *
     * @param path non-nullable path to stylesheet.
     * @since 1.0.0.
     */
    void ensureStylesheet(@NotNull String path);

    /**
     * Might return null if stage are not passed to router.
     *
     * @return nullable current stage in this router.
     * @since 1.0.0.
     */
    @Nullable Stage getStage();

    /**
     * @return nullable current scene in this router.
     * @since 1.0.0.
     */
    @Nullable Scene getCurrentScene();

    /**
     * @return nullable current resource bundle.
     * @since 1.0.0.
     */
    @Nullable ResourceBundle getCurrentBundle();

    /**
     * @return nullable router configuration.
     * @since 1.0.0.
     */
    @Nullable RouterConfiguration getConfiguration();

    /**
     * @return nullable router navigation graph.
     * @since 1.0.0.
     */
    @NotNull RouterGraph getGraph();

    /**
     * Does request router shutdown, disposes all used resources
     * in router, also calls in current controller method
     * {@link com.mairwunnx.ui.navigation.controller.RouterController#onExit(Router)}
     * <p/>
     * You should call this in {@link Application#stop()} method.
     *
     * @since 1.0.0.
     */
    void shutdown();

    /**
     * Does navigate you to new scene with specified key.
     * <p/>
     * With this method will use default resource bundle and stylesheet.
     *
     * @param key key of layout you want to navigate.
     * @since 1.0.0.
     */
    void navigate(@NotNull String key);

    /**
     * Does navigate you to new scene with specified key and specified bundle.
     * <p/>
     * With this method will use passed resource bundle.
     *
     * @param key    key of layout you want to navigate.
     * @param bundle specified bundle with which you want to navigate.
     * @since 1.0.0.
     */
    void navigate(@NotNull String key, @NotNull ResourceBundle bundle);

    /**
     * Does navigate you to new scene with specified key and default bundle.
     * <p/>
     * With this method will use default resource bundle and stylesheet.
     * <p/>
     * You can receive argument in controller with method
     * {@link com.mairwunnx.ui.navigation.controller.RouterController#getArgumentFrom(RouterArg, String)}
     *
     * @param key key of layout you want to navigate.
     * @param arg argument for navigation you want to receive in other controller.
     * @param <T> type of router navigation object.
     * @since 1.0.0.
     */
    <T> void navigate(@NotNull String key, @NotNull RouterArg<T> arg);

    /**
     * Does navigate you to new scene with specified key and specified bundle and argument.
     * <p/>
     * With this method will use passed resource bundle and stylesheet.
     * <p/>
     * You can receive argument in controller with method
     * {@link com.mairwunnx.ui.navigation.controller.RouterController#getArgumentFrom(RouterArg, String)}
     *
     * @param key    key of layout you want to navigate.
     * @param arg    argument for navigation you want to receive in other controller.
     * @param bundle specified bundle with which you want to navigate.
     * @param <T>    type of router navigation object.
     * @since 1.0.0.
     */
    <T> void navigate(@NotNull String key, @NotNull RouterArg<T> arg, @NotNull ResourceBundle bundle);

    /**
     * Does navigate you to new scene with specified key and default bundle and clears
     * back stack. i.e. {@link Router#back()} will do no effect.
     * <p/>
     * With this method will use default resource bundle and stylesheet.
     *
     * @param key key of layout you want to navigate.
     * @since 1.0.0.
     */
    void navigateClearBackStack(@NotNull String key);

    /**
     * Does navigate you to new scene with specified key and specified bundle and clears
     * back stack. i.e. {@link Router#back()} will do no effect.
     * <p/>
     * With this method will use specified resource bundle.
     *
     * @param key    key of layout you want to navigate.
     * @param bundle specified bundle with which you want to navigate.
     * @since 1.0.0.
     */
    void navigateClearBackStack(@NotNull String key, @NotNull ResourceBundle bundle);

    /**
     * Does navigate you to new scene with specified key and default bundle
     * and argument, and clears back stack. i.e. {@link Router#back()} will do no effect.
     * <p/>
     * With this method will use default resource bundle.
     * <p/>
     * You can receive argument in controller with method
     * {@link com.mairwunnx.ui.navigation.controller.RouterController#getArgumentFrom(RouterArg, String)}
     *
     * @param key key of layout you want to navigate.
     * @param arg argument for navigation you want to receive in other controller.
     * @param <T> type of router navigation object.
     * @since 1.0.0.
     */
    <T> void navigateClearBackStack(@NotNull String key, @NotNull RouterArg<T> arg);

    /**
     * Does navigate you to new scene with specified key and specified bundle
     * and argument, and clears back stack. i.e. {@link Router#back()} will do no effect.
     * <p/>
     * With this method will use specified resource bundle.
     * <p/>
     * You can receive argument in controller with method
     * {@link com.mairwunnx.ui.navigation.controller.RouterController#getArgumentFrom(RouterArg, String)}
     *
     * @param key    key of layout you want to navigate.
     * @param arg    argument for navigation you want to receive in other controller.
     * @param bundle specified bundle with which you want to navigate.
     * @param <T>    type of router navigation object.
     * @since 1.0.0.
     */
    <T> void navigateClearBackStack(@NotNull String key, @NotNull RouterArg<T> arg, @NotNull ResourceBundle bundle);

    /**
     * Does navigate you to back by router back stack.
     * <p/>
     * Pay attention, back will create new instance of controller and
     * will inflate layout again, there is no caching.
     *
     * @since 1.0.0.
     */
    void back();

    /**
     * Does navigate you to back by router back stack.
     * <p/>
     * Pay attention, back will create new instance of controller and
     * will inflate layout again, there is no caching.
     * <p/>
     * You can receive argument in controller with method
     * {@link com.mairwunnx.ui.navigation.controller.RouterController#getArgumentFrom(RouterArg, String)}
     *
     * @param arg argument for navigation you want to receive in other controller.
     * @param <T> type of router navigation object.
     * @since 1.0.0.
     */
    <T> void back(@NotNull RouterArg<T> arg);

    /**
     * Does reload current route, creates again controller and inflate view.
     *
     * @since 1.0.0.
     */
    void reload();

    /**
     * Does reload current route, creates again controller and inflate view
     * with specified bundle.
     *
     * @param bundle specified bundle with which you want to navigate.
     * @since 1.0.0.
     */
    void reload(@NotNull ResourceBundle bundle);

    /**
     * Does reload current route, creates again controller and inflate view.
     * <p/>
     * You can receive argument in controller with method
     * {@link com.mairwunnx.ui.navigation.controller.RouterController#getArgumentFrom(RouterArg, String)}
     *
     * @param arg argument for navigation you want to receive in other controller.
     * @param <T> type of router navigation object.
     * @since 1.0.0.
     */
    <T> void reload(@NotNull RouterArg<T> arg);

    /**
     * Does reload current route, creates again controller and inflate view
     * with specified bundle.
     * <p/>
     * You can receive argument in controller with method
     * {@link com.mairwunnx.ui.navigation.controller.RouterController#getArgumentFrom(RouterArg, String)}
     *
     * @param arg    argument for navigation you want to receive in other controller.
     * @param bundle specified bundle with which you want to navigate.
     * @param <T>    type of router navigation object.
     * @since 1.0.0.
     */
    <T> void reload(@NotNull RouterArg<T> arg, @NotNull ResourceBundle bundle);
}
