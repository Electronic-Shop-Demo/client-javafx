package com.mairwunnx.application.application.types;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import static com.mairwunnx.application.application.preferences.impl.PreferencesImpl.ARG_PREFERENCE_KEYVALUE_SEPARATOR;

public final class PreferenceArgument {
    @NotNull
    public static String[] toPreference(final @NotNull String arg) {
        return StringUtils.split(arg, ARG_PREFERENCE_KEYVALUE_SEPARATOR);
    }
}
