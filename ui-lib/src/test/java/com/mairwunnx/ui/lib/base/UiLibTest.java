package com.mairwunnx.ui.lib.base;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.mairwunnx.ui.lib.JfxView;
import com.mairwunnx.ui.lib.di.UiTestModule;
import javafx.util.BuilderFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.ResourceBundle;

public final class UiLibTest {
    public static final String STYLESHEET = "/application/styles/application.css";
    public static ResourceBundle resourceBundle = ResourceBundle.getBundle("application/bundles/strings");
    private static BuilderFactory builderFactory;

    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PRIVATE)
    private static Injector injector;

    public static void initialize() {
        initializeDi();
        initializeProperties();
        initializeBuilderFactory();
    }

    @SuppressWarnings("unchecked")
    public static <T> T constructView(final Class<?> type) {
        return (T) builderFactory.getBuilder(type).build();
    }

    private static void initializeDi() {
        setInjector(Guice.createInjector(Stage.DEVELOPMENT, getModules()));
    }

    private static void initializeProperties() {
        System.setProperty("prism.lcdtext", "false");
        System.setProperty("prism.text", "t2k");
    }

    private static void initializeBuilderFactory() {
        builderFactory = type -> {
            if (JfxView.class.isAssignableFrom(type)) {
                return () -> ((JfxView) getInjector().getInstance(type)).onViewCreate();
            } else {
                return null;
            }
        };
    }

    private static @NotNull AbstractModule[] getModules() {
        return new AbstractModule[]{new UiTestModule()};
    }
}
