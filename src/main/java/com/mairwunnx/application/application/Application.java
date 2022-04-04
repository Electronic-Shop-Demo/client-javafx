package com.mairwunnx.application.application;

import com.mairwunnx.application.application.router.Router;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Log4j2
public final class Application extends javafx.application.Application {
    private static final String STYLESHEET = "/com/mairwunnx/application/styles/application.css";

    @Nullable
    private Router router = null;

    @Override
    public void init() {
        log.info("Initializing the JavaFX application");
        initializeProperties();
        initializeRouter();
    }

    @Override
    public void start(final @NotNull Stage stage) {
        if (router != null) {
            router.ensureStage(stage);
            router.ensureStylesheet(STYLESHEET);
            router.navigate(ApplicationRouter.Routes.HOME.toString());
        }
    }

    @Override
    public void stop() {
        if (router != null) {
            router.shutdown();
            router = null;
        } else {
            log.warn("Router shutdown call skipped, router is null");
        }
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
        router = new ApplicationRouter().buildRouter();
        log.info("Application router created");
    }
}
