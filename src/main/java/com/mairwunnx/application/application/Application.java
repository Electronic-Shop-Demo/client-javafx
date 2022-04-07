package com.mairwunnx.application.application;

import com.mairwunnx.application.application.di.GuiceInjector;
import com.mairwunnx.application.application.preferences.Preferences;
import com.mairwunnx.application.application.preferences.impl.PreferencesImpl.PredefinedSettings;
import com.mairwunnx.application.application.router.Router;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.ResourceBundle;

@Log4j2
public final class Application extends javafx.application.Application {
    private static final String STYLESHEET = "/com/mairwunnx/application/styles/application.css";
    private static final String BUNDLE = "/com/mairwunnx/application/bundles/strings";

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private static ResourceBundle currentResourceBundle;

    private Router router = null;
    private Preferences preferences = null;
    private MainRouterHandler mainRouterHandler = null;
    private StageConfigurator stageConfigurator = null;

    public Application() {
        log.info("Instantiating {} JavaFX application class", Application.class.getName());
    }

    @Override
    public void init() {
        log.info("Initializing the JavaFX application");
        injectPreferences();
        initializeProperties();
        initializeRouter();
        postInit();
    }

    @Override
    public void start(final @NotNull Stage stage) {
        if (router != null) {
            loadResourceBundle();
            stageConfigurator.loadStageProperties(getCurrentResourceBundle(), preferences, stage);
            router.ensureBundle(getCurrentResourceBundle());
            router.ensureStage(stage);
            router.ensureStylesheet(STYLESHEET);
            router.navigate(ApplicationRouter.Routes.HOME.toString());
        }
    }

    @Override
    public void stop() {
        saveApplicationConfiguration();
        shutdownRouter();
    }

    private void injectPreferences() {
        preferences = GuiceInjector.getInjector().getInstance(Preferences.class);
    }

    private void initializeProperties() {
        try {
            System.setProperty("prism.lcdtext", "false");
            log.debug("Property prism.lcdtext set to false");
            System.setProperty("prism.text", "t2k");
            log.debug("Property prism.text set to t2k");
        } catch (final SecurityException ex) {
            log.error("Security error, has no able to change system properties", ex);
        }
    }

    private void initializeRouter() {
        router = new ApplicationRouter()
            .setOnNavigationPerformedInterceptor((k, s, a, e) -> mainRouterHandler.interceptSceneChanging(s))
            .setOnResourceBundleInterceptor((router, bundle) -> setCurrentResourceBundle(bundle))
            .buildRouter();
        log.info("Application router created");
    }

    private void postInit() {
        mainRouterHandler = new MainRouterHandler(router);
        stageConfigurator = new StageConfigurator();
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

    private void saveApplicationConfiguration() {
        if (router.getStage() != null) {
            stageConfigurator.saveStageProperties(router.getStage(), preferences);
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
