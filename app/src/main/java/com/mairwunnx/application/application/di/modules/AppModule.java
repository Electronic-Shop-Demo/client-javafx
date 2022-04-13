package com.mairwunnx.application.application.di.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mairwunnx.application.application.Application;
import com.mairwunnx.application.application.components.HttpClientComponent;
import com.mairwunnx.ui.di.qualifiers.CompactNumberFormatter;
import com.mairwunnx.ui.di.qualifiers.StartupArgs;
import com.mairwunnx.ui.lib.managers.BadgeManager;
import com.mairwunnx.ui.lib.managers.BadgeManagerImpl;
import com.mairwunnx.ui.preferences.PreferenceType;
import com.mairwunnx.ui.preferences.Preferences;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.ResourceBundle;

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
        bind(BadgeManager.class).to(BadgeManagerImpl.class);
        /*bind(ListenerManager.class).to(ListenerManagerImpl.class).asEagerSingleton();*/
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
    static ResourceBundle provideResourceBundle() {
        return Application.getCurrentResourceBundle();
    }

    @Singleton
    @Provides
    static Preferences providePreferences() {
        return Preferences.load(Path.of("settings", "config.xml"), PreferenceType.XML);
    }

    @Provides
    @CompactNumberFormatter
    static NumberFormat provideCompactNumberInstance() {
        return NumberFormat.getCompactNumberInstance(provideResourceBundle().getLocale(), NumberFormat.Style.SHORT);
    }
}
