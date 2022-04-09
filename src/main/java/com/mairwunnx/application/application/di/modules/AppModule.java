package com.mairwunnx.application.application.di.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mairwunnx.application.application.Application;
import com.mairwunnx.application.application.components.HttpClientComponent;
import com.mairwunnx.application.application.di.qulifiers.StartupArgs;
import com.mairwunnx.application.application.views.TopBar;
import com.mairwunnx.ui.preferences.PreferenceType;
import com.mairwunnx.ui.preferences.Preferences;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

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
        bind(Preferences.class)
            .toInstance(Preferences.load(Path.of("settings", "config.xml"), PreferenceType.XML));
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

    @Provides
    static TopBar provideTopBar() {
        return new TopBar(Application.getCurrentResourceBundle());
    }
}
