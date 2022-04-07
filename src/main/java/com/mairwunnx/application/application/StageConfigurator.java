package com.mairwunnx.application.application;

import com.mairwunnx.application.application.preferences.Preferences;
import com.mairwunnx.application.application.preferences.impl.PreferencesImpl.PredefinedSettings;
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
                PredefinedSettings.SIZE_WIDTH.getKey(),
                Integer.parseInt(bundle.getString("defaultWindowWidth"))
            );
            final var sizeH = preferences.getIntOrDefault(
                PredefinedSettings.SIZE_HEIGHT.getKey(),
                Integer.parseInt(bundle.getString("defaultWindowHeight"))
            );

            final var winX = preferences.getIntOrDefault(PredefinedSettings.WINDOW_X.getKey(), -1);
            final var winY = preferences.getIntOrDefault(PredefinedSettings.WINDOW_Y.getKey(), -1);
            final var isMaximized = preferences.getBooleanOrFalse(PredefinedSettings.WINDOW_IS_MAXIMIZED.getKey());

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
                .setInt(PredefinedSettings.WINDOW_X.getKey(), (int) stage.getX())
                .setInt(PredefinedSettings.WINDOW_Y.getKey(), (int) stage.getY())
                .setInt(PredefinedSettings.SIZE_WIDTH.getKey(), (int) stage.getWidth())
                .setInt(PredefinedSettings.SIZE_HEIGHT.getKey(), (int) stage.getHeight())
                .setBoolean(PredefinedSettings.WINDOW_IS_MAXIMIZED.getKey(), stage.isMaximized())
                .commitSynchronously();
        } else {
            log.warn("Preferences saving call skipped, preferences is null");
        }
    }
}
