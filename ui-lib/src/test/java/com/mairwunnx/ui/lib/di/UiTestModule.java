package com.mairwunnx.ui.lib.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mairwunnx.ui.di.qualifiers.CompactNumberFormatter;
import com.mairwunnx.ui.lib.base.UiLibTest;
import com.mairwunnx.ui.lib.managers.BadgeManager;
import com.mairwunnx.ui.lib.managers.BadgeManagerImpl;

import java.text.NumberFormat;
import java.util.ResourceBundle;

public class UiTestModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(BadgeManager.class).to(BadgeManagerImpl.class);
    }

    @Provides
    static ResourceBundle provideResourceBundle() {
        return UiLibTest.resourceBundle;
    }

    @Provides
    @CompactNumberFormatter
    static NumberFormat provideCompactNumberInstance() {
        return NumberFormat.getCompactNumberInstance(provideResourceBundle().getLocale(), NumberFormat.Style.SHORT);
    }
}
