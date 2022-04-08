package com.mairwunnx.ui.preferences.impl;

import com.mairwunnx.ui.preferences.PreferenceType;
import com.mairwunnx.ui.preferences.Preferences;
import com.mairwunnx.ui.preferences.internal.SupplierConsumer;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

@ApiStatus.Internal
public final class PreferencesImpl implements Preferences {
    @Nullable private final Path configurationPath;
    @Nullable private final PreferenceType preferenceType;
    @NotNull private final PreferenceLoader preferenceLoader;
    @Nullable private Properties properties;

    public PreferencesImpl(
        @Nullable final Path configurationPath,
        @Nullable final Properties properties,
        @Nullable final PreferenceType preferenceType,
        @NotNull final PreferenceLoader preferenceLoader
    ) {
        this.configurationPath = configurationPath;
        this.properties = properties;
        this.preferenceType = preferenceType;
        this.preferenceLoader = preferenceLoader;
    }

    private <T> T withProperties(@NotNull final SupplierConsumer<T, @NotNull Properties> supplier) {
        if (properties != null) {
            return supplier.acceptAndReturn(properties);
        } else {
            throw new IllegalStateException("Properties must be initialized!");
        }
    }

    @Override
    public @NotNull String getString(@NotNull final String key) {
        return withProperties(localProperties -> {
            final var val = localProperties.getProperty(key);
            if (val == null) throw new NullPointerException("Property value of key %s was null".formatted(key));
            return val;
        });
    }

    @Override
    public @Nullable String getStringOrNull(@NotNull final String key) {
        return withProperties(localProperties -> localProperties.getProperty(key));
    }

    @Override
    public @NotNull String getStringOrDefault(@NotNull final String key, @NotNull final String defaultVal) {
        return withProperties(localProperties -> {
            final var val = localProperties.getProperty(key);
            if (val == null) return defaultVal;
            return val;
        });
    }

    @Override
    public int getInt(@NotNull final String key) {
        return withProperties(localProperties -> NumberUtils.toInt(localProperties.getProperty(key)));
    }

    @Override
    public int getIntOrDefault(@NotNull final String key, final int defaultVal) {
        return withProperties(localProperties -> {
            final var val = localProperties.getProperty(key);
            if (val == null) return defaultVal;
            return NumberUtils.toInt(val);
        });
    }

    @Override
    public long getLong(@NotNull final String key) {
        return withProperties(localProperties -> NumberUtils.toLong(localProperties.getProperty(key)));
    }

    @Override
    public long getLongOrDefault(@NotNull final String key, final long defaultVal) {
        return withProperties(localProperties -> {
            final var val = localProperties.getProperty(key);
            if (val == null) return defaultVal;
            return NumberUtils.toLong(val);
        });
    }

    @Override
    public boolean getBoolean(@NotNull final String key) {
        return withProperties(localProperties -> BooleanUtils.toBoolean(localProperties.getProperty(key)));
    }

    @Override
    public boolean getBooleanOrDefault(@NotNull final String key, final boolean defaultVal) {
        return withProperties(localProperties -> {
            final var val = localProperties.getProperty(key);
            if (val == null) return defaultVal;
            return BooleanUtils.toBoolean(val);
        });
    }

    private static final String STRING_ARRAY_SEPARATOR = "○";

    @Override
    public @NotNull List<String> getList(@NotNull final String key) {
        return withProperties(localProperties -> {
            final var val = localProperties.getProperty(key);
            if (val == null) throw new NullPointerException("Property value of key %s was null".formatted(key));
            return List.of(StringUtils.splitByWholeSeparator(localProperties.getProperty(key), STRING_ARRAY_SEPARATOR));
        });
    }

    @Override
    public @Nullable List<String> getListOrNull(@NotNull final String key) {
        return withProperties(localProperties -> {
            final var val = localProperties.getProperty(key);
            if (val == null) return null;
            return List.of(StringUtils.splitByWholeSeparator(localProperties.getProperty(key), STRING_ARRAY_SEPARATOR));
        });
    }

    @Override
    public @NotNull List<String> getListOrDefault(@NotNull final String key, @NotNull final List<String> defaultVal) {
        return withProperties(localProperties -> {
            final var val = localProperties.getProperty(key);
            if (val == null) return defaultVal;
            return List.of(StringUtils.splitByWholeSeparator(localProperties.getProperty(key), STRING_ARRAY_SEPARATOR));
        });
    }

    @Override
    public @NotNull Preferences setString(@NotNull final String key, @Nullable final String value) {
        withProperties(localProperties -> localProperties.put(key, value));
        return this;
    }

    @Override
    public @NotNull Preferences setInt(@NotNull final String key, final int value) {
        withProperties(localProperties -> localProperties.put(key, String.valueOf(value)));
        return this;
    }

    @Override
    public @NotNull Preferences setBoolean(@NotNull final String key, final boolean value) {
        withProperties(localProperties -> localProperties.put(key, String.valueOf(value)));
        return this;
    }

    @Override
    public @NotNull Preferences setList(@NotNull final String key, @Nullable final List<String> value) {
        withProperties(localProperties -> localProperties.put(key, StringUtils.join(value, STRING_ARRAY_SEPARATOR)));
        return this;
    }

    @Override
    public @NotNull CompletableFuture<Void> commitAsync() {
        return CompletableFuture.runAsync(this::savePreferences);
    }

    @Override
    public void commitSync() {
        savePreferences();
    }

    @Override
    public void reload() {
        if (configurationPath != null) {
            if (preferenceType != null) {
                properties = preferenceLoader.loadProperties();
            }
        }
    }

    private void savePreferences() {
        if (configurationPath != null) {
            try (final var os = Files.newOutputStream(configurationPath)) {
                if (properties != null) {
                    if (preferenceType != null) {
                        switch (preferenceType) {
                            case XML -> properties.storeToXML(os, null);
                            case PROPERTIES -> properties.store(os, null);
                            default -> throw new IllegalStateException("Unexpected value: " + preferenceType);
                        }
                    }
                }
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
