package com.mairwunnx.ui.lib;

import javafx.fxml.FXMLLoader;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Contract for all JavaFX custom views can be exposed.
 *
 * @since 1.0.0.
 */
public sealed interface JfxView permits TopBar {
    /**
     * Base initialization process, calling after instantiating view class.
     *
     * @return {@link JfxView} class instance.
     */
    @NotNull JfxView init();

    /**
     * Simply supplier for receiving layout path for this view.
     *
     * @return layout path for this view.
     */
    @NotNull String layoutPath();

    /**
     * Simply supplier for receiving resource bundle for this view.
     *
     * @return non-nullable resource bundle.
     */
    @NotNull ResourceBundle resourceBundle();

    /**
     * Does expose (inflate) layout for this root. Also sets controller
     * as instance to this.
     * <p>
     * In case if {@link FXMLLoader} will throw exception that exception will
     * delegate to {@link JfxView#onExposeFailure(IOException)}
     */
    default void expose() {
        final var fxmlLoader = new FXMLLoader(getClass().getResource(layoutPath()), resourceBundle());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (final IOException e) {
            onExposeFailure(e);
        }
    }

    /**
     * On expose failure callback. When exception will throw by
     * {@link FXMLLoader}, this method will call.
     * <p>
     * By default, does throw RuntimeException to top level.
     *
     * @param e exception, which was thrown.
     */
    default void onExposeFailure(final IOException e) {
        throw new RuntimeException(e);
    }
}
