package com.mairwunnx.application.application;

import com.mairwunnx.ui.preferences.Preferences;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ResourceBundle;

@Log4j2
public final class StageConfigurator {
    public void loadStageProperties(
        @NotNull final ResourceBundle bundle,
        @Nullable final Preferences preferences,
        @NotNull final Stage stage
    ) {
        if (preferences != null) {
            final var sizeW = preferences.getIntOrDefault(
                ApplicationPreferences.SIZE_WIDTH,
                Integer.parseInt(bundle.getString("defaultWindowWidth"))
            );
            final var sizeH = preferences.getIntOrDefault(
                ApplicationPreferences.SIZE_HEIGHT,
                Integer.parseInt(bundle.getString("defaultWindowHeight"))
            );

            final var winX = preferences.getIntOrDefault(ApplicationPreferences.WINDOW_X, -1);
            final var winY = preferences.getIntOrDefault(ApplicationPreferences.WINDOW_Y, -1);
            final var isMaximized = preferences.getBoolean(ApplicationPreferences.WINDOW_IS_MAXIMIZED);

            if (isMaximized) {
                stage.setMaximized(true);
            } else {
                if (winX != -1 && winY != -1) {
                    stage.setX(winX);
                    stage.setY(winY);
                }

                stage.setWidth(sizeW);
                stage.setHeight(sizeH);
            }
        } else {
            log.error("Must be not happen! But preferences is null, instance of preferences not injected.");
            stage.setWidth(Integer.parseInt(bundle.getString("defaultWindowWidth")));
            stage.setHeight(Integer.parseInt(bundle.getString("defaultWindowHeight")));
        }
    }

    public void saveStageProperties(@NotNull final Stage stage, @Nullable final Preferences preferences) {
        if (preferences != null) {
            preferences
                .setInt(ApplicationPreferences.WINDOW_X, (int) stage.getX())
                .setInt(ApplicationPreferences.WINDOW_Y, (int) stage.getY())
                .setInt(ApplicationPreferences.SIZE_WIDTH, (int) stage.getWidth())
                .setInt(ApplicationPreferences.SIZE_HEIGHT, (int) stage.getHeight())
                .setBoolean(ApplicationPreferences.WINDOW_IS_MAXIMIZED, stage.isMaximized())
                .commitSync();
        } else {
            log.warn("Preferences saving call skipped, preferences is null");
        }
    }
}
