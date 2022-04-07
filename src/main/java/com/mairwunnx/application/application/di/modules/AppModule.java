package com.mairwunnx.application.application.di.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mairwunnx.application.application.components.HttpClientComponent;
import com.mairwunnx.application.application.di.qulifiers.StartupArgs;
import com.mairwunnx.application.application.preferences.Preferences;
import com.mairwunnx.application.application.preferences.impl.PreferencesImpl;
import org.jetbrains.annotations.NotNull;

public final class AppModule extends AbstractModule {
    @NotNull
    private static String[] args;

    public AppModule(@NotNull final String[] args) {
        if (args != null) {
            AppModule.args = args;
        }
    }

    @Override
    protected void configure() {
        bind(Preferences.class).to(PreferencesImpl.class).asEagerSingleton();
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

    @Singleton
    @Provides
    static PreferencesImpl providePreferences() {
        return new PreferencesImpl(args);
    }
}
