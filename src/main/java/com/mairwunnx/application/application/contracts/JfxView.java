package com.mairwunnx.application.application.contracts;

import com.mairwunnx.application.application.Application;
import com.mairwunnx.application.application.views.TopBar;
import javafx.fxml.FXMLLoader;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public sealed interface JfxView permits TopBar {
    @NotNull String layoutPath();

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

    default void onExposeFailure(final IOException e) {
        throw new RuntimeException(e);
    }
}
