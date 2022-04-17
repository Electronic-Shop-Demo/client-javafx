package com.mairwunnx.application.application.di;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.mairwunnx.application.PostInit;
import com.mairwunnx.application.application.EnvironmentVariables;
import com.mairwunnx.application.application.di.modules.AppModule;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

public final class GuiceInjector {
    @NotNull
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private static Injector injector;

    public static void ensureModules(@NotNull final String[] args) {
        setInjector(Guice.createInjector(getStage(), getModules(args)));
        getInjector().getInstance(PostInit.class).init();
    }

    private static @NotNull Stage getStage() {
        return EnvironmentVariables.isDebugEnabled ? Stage.DEVELOPMENT : Stage.PRODUCTION;
    }

    private static @NotNull AbstractModule[] getModules(@NotNull final String[] args) {
        return new AbstractModule[]{new AppModule(args)};
    }
}
