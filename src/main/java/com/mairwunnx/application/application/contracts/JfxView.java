package com.mairwunnx.application.application.contracts;

import com.mairwunnx.application.application.Application;
import com.mairwunnx.application.application.views.TopBar;
import javafx.fxml.FXMLLoader;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Contract for all JavaFX custom views can be exposed.
 */
public sealed interface JfxView permits TopBar {
    /**
     * Simply supplier for receiving layout path for this view.
     *
     * @return layout path for this view.
     */
    @NotNull String layoutPath();

    /**
     * Does expose (inflate) layout for this root. Also sets controller
     * as instance to this.
     *
     * In case if {@link FXMLLoader} will throw exception that exception will
     * delegate to {@link JfxView#onExposeFailure(IOException)}
     */
    default void expose() {
        final FXMLLoader fxmlLoader = new FXMLLoader(
            getClass().getResource(layoutPath()), Application.getCurrentResourceBundle()
        );
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
     *
     * By default, does throw RuntimeException to top level.
     *
     * @param e exception, which was thrown.
     */
    default void onExposeFailure(final IOException e) {
        throw new RuntimeException(e);
    }
}
