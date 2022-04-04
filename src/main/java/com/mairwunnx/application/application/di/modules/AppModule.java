package com.mairwunnx.application.application.di.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mairwunnx.application.application.components.HttpClientComponent;
import com.mairwunnx.application.application.di.qulifiers.StartupArgs;
import org.jetbrains.annotations.NotNull;

public final class AppModule extends AbstractModule {
    @NotNull
    private static String[] args;

    public AppModule(@NotNull final String[] args) {
        if (args != null) {
            AppModule.args = args;
        }
    }

    @Provides
    @StartupArgs
    static String[] provideArgs() {
        return args;
    }

    @Singleton
    @Provides
    static HttpClientComponent provideHttpClientComponent() {
        return new HttpClientComponent();
    }
}
