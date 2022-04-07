package com.mairwunnx.application.application;

import com.mairwunnx.application.application.di.GuiceInjector;
import com.mairwunnx.application.application.preferences.Preferences;
import com.mairwunnx.application.application.preferences.impl.PreferencesImpl.PredefinedSettings;
import com.mairwunnx.application.application.router.Router;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.ResourceBundle;

@Log4j2
public final class Application extends javafx.application.Application {
    private static final String STYLESHEET = "/com/mairwunnx/application/styles/application.css";
    private static final String BUNDLE = "/com/mairwunnx/application/bundles/strings";

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private static ResourceBundle currentResourceBundle;

    @Nullable
    private Router router = null;

    @Nullable
    private Preferences preferences = null;

    @Override
    public void init() {
        log.info("Initializing the JavaFX application");
        injectPreferences();
        initializeProperties();
        initializeRouter();
    }

    @Override
    public void start(final @NotNull Stage stage) {
        if (router != null) {
            loadResourceBundle();
            loadStageConfiguration(stage);
            router.ensureBundle(getCurrentResourceBundle());
            router.ensureStage(stage);
            router.ensureStylesheet(STYLESHEET);
            router.navigate(ApplicationRouter.Routes.HOME.toString());
        }
    }

    @Override
    public void stop() {
        saveStageProperties();
        shutdownRouter();
    }

    private void injectPreferences() {
        preferences = GuiceInjector.getInjector().getInstance(Preferences.class);
    }

    private void initializeProperties() {
        try {
            System.setProperty("prism.lcdtext", "false");
            System.setProperty("prism.text", "t2k");
        } catch (final SecurityException ex) {
            log.error("Security error, has no able to change system properties", ex);
        }
    }

    private void initializeRouter() {
        router = new ApplicationRouter()
            .setOnNavigationPerformedInterceptor((key, stage, arg, e) -> interceptSceneChanging(stage))
            .setOnResourceBundleInterceptor((router, bundle) -> setCurrentResourceBundle(bundle))
            .buildRouter();

        log.info("Application router created");
    }

    private void loadResourceBundle() {
        if (preferences != null) {
            final var lang = preferences.getStringOrDefault(PredefinedSettings.LOCALE.getKey(), null);
            if (lang == null) {
                setCurrentResourceBundle(ResourceBundle.getBundle(BUNDLE));
            } else {
                log.info("Loading bundle with requested language code {}", lang);
                setCurrentResourceBundle(ResourceBundle.getBundle(BUNDLE, Locale.forLanguageTag(lang)));
            }
        } else {
            log.error("Must be not happen! But preferences is null, instance of preferences not injected.");
            setCurrentResourceBundle(ResourceBundle.getBundle(BUNDLE));
        }
    }

    private void loadStageConfiguration(@NotNull final Stage stage) {
        if (preferences != null) {
            final var sizeW = preferences.getIntOrDefault(
                PredefinedSettings.SIZE_WIDTH.getKey(),
                Integer.parseInt(getCurrentResourceBundle().getString("defaultWindowWidth"))
            );
            final var sizeH = preferences.getIntOrDefault(
                PredefinedSettings.SIZE_HEIGHT.getKey(),
                Integer.parseInt(getCurrentResourceBundle().getString("defaultWindowHeight"))
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
            stage.setWidth(Integer.parseInt(getCurrentResourceBundle().getString("defaultWindowWidth")));
            stage.setHeight(Integer.parseInt(getCurrentResourceBundle().getString("defaultWindowHeight")));
        }
    }

    private void interceptSceneChanging(@NotNull final Stage stage) {
        stage.setMinWidth(stage.getScene().getRoot().minWidth(stage.getHeight()));
        initializeSceneAccelerators(stage);
        initializeSceneFocusHandlers(stage);
    }

    private void initializeSceneAccelerators(@NotNull final Stage stage) {
        stage.getScene().getAccelerators().put(
            new KeyCodeCombination(KeyCode.LEFT, KeyCombination.ALT_DOWN),
            () -> {
                if (router != null) router.back();
            }
        );
    }

    private void initializeSceneFocusHandlers(@NotNull final Stage stage) {
        stage.getScene().getAccelerators().put(
            new KeyCodeCombination(KeyCode.ESCAPE),
            () -> {
                if (stage.getScene().getFocusOwner() != null) {
                    stage.getScene().getRoot().requestFocus();
                }
            }
        );
    }

    private void saveStageProperties() {
        if (preferences != null) {
            if (router != null) {
                final var stage = router.getStage();
                if (stage != null) {
                    preferences
                        .setInt(PredefinedSettings.WINDOW_X.getKey(), (int) router.getStage().getX())
                        .setInt(PredefinedSettings.WINDOW_Y.getKey(), (int) router.getStage().getY())
                        .setInt(PredefinedSettings.SIZE_WIDTH.getKey(), (int) router.getStage().getWidth())
                        .setInt(PredefinedSettings.SIZE_HEIGHT.getKey(), (int) router.getStage().getHeight())
                        .setBoolean(PredefinedSettings.WINDOW_IS_MAXIMIZED.getKey(), router.getStage().isMaximized())
                        .commitSynchronously();
                }
            }
        } else {
            log.warn("Preferences saving call skipped, preferences is null");
        }
    }

    private void shutdownRouter() {
        if (router != null) {
            router.shutdown();
            router = null;
        } else {
            log.warn("Router shutdown call skipped, router is null");
        }
    }
}
