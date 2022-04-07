package com.mairwunnx.application.application.preferences.impl;

import com.mairwunnx.application.application.preferences.Preferences;
import com.mairwunnx.application.application.types.ArrayPair;
import com.mairwunnx.application.application.types.PreferenceArgument;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

@Log4j2
public final class PreferencesImpl implements Preferences {
    public static final String ARG_PREFERENCE_KEYVALUE_SEPARATOR = "=";

    private final String configurationFile = "config.xml";
    private final Path configurationPath = Path.of("settings", configurationFile);
    private final Properties properties = new Properties();
    private final String[] args;

    public PreferencesImpl(@NotNull final String[] args) {
        log.debug("Loading configuration from {}", configurationPath);
        this.args = args;
        ensureDirectories();
        loadPreferences();
        loadPreferencesFromArg();
    }

    private void ensureDirectories() {
        try {
            Files.createDirectories(configurationPath.getParent());
        } catch (final IOException e) {
            log.error(
                new ParameterizedMessage(
                    "An error occurred while creating directories for path {}", configurationPath
                ), e
            );
        }
    }

    private void loadPreferences() {
        if (Files.exists(configurationPath)) {
            try (final var is = Files.newInputStream(configurationPath)) {
                properties.loadFromXML(is);
            } catch (final InvalidPropertiesFormatException e) {
                log.error("An error occurred while parsing XML configuration file, incorrect format", e);
            } catch (final IOException e) {
                log.error("An error occurred while loading XML configuration file", e);
            }
        } else {
            log.warn("Configuration file {} is not exist, used default settings", configurationFile);
            commit();
        }
    }

    private void loadPreferencesFromArg() {
        Arrays.stream(args)
            .map(PreferenceArgument::toPreference)
            .map(ArrayPair::of)
            .forEach(pair -> {
                log.info("Loaded property with key {}, with value {}", pair.getKey(), pair.getValue());
                properties.put(pair.getKey(), pair.getValue());
            });
    }

    private void savePreferences() {
        log.debug("Preparing to saving settings to {}", configurationPath);
        try (final var os = Files.newOutputStream(configurationPath)) {
            properties.storeToXML(os, "Application configuration");
            log.debug("Settings saved to {}", configurationPath);
        } catch (final IOException e) {
            log.error(
                "An error occurred while saving settings, YOUR CONFIGURATION WILL NOT SAVED!", e
            );
        }
    }

    @Nullable
    @Override
    public String getStringOrDefault(@NotNull final String key, @Nullable final String defaultValue) {
        return (String) properties.getOrDefault(key, defaultValue);
    }

    @Override
    public int getIntOrDefault(@NotNull final String key, final int defaultValue) {
        return NumberUtils.toInt(getStringOrDefault(key, (String) null), defaultValue);
    }

    @Override
    public boolean getBooleanOrFalse(@NotNull final String key) {
        return BooleanUtils.toBoolean(getStringOrDefault(key, (String) null));
    }

    @NotNull
    @Override
    public Preferences setString(@NotNull final String key, @NotNull final String value) {
        properties.put(key, value);
        return this;
    }

    @NotNull
    @Override
    public Preferences setInt(@NotNull final String key, final int value) {
        properties.put(key, String.valueOf(value));
        return this;
    }

    @NotNull
    @Override
    public Preferences setBoolean(@NotNull final String key, final boolean value) {
        properties.put(key, String.valueOf(value));
        return this;
    }

    @NotNull
    @Override
    public CompletableFuture<Void> commit() {
        return CompletableFuture.runAsync(this::savePreferences);
    }

    public void commitSynchronously() {
        savePreferences();
    }

    public enum PredefinedSettings {
        SIZE_WIDTH("window-width", -1),
        SIZE_HEIGHT("window-height", -1),
        WINDOW_X("window-x", -1),
        WINDOW_Y("window-y", -1),
        WINDOW_IS_MAXIMIZED("window-is-maximized", false),
        LOCALE("locale", "null");

        @NotNull
        @Getter
        @Setter(value = AccessLevel.PRIVATE)
        private String key;

        @NotNull
        @Getter
        @Setter(value = AccessLevel.PRIVATE)
        private Object value;

        PredefinedSettings(final String key, final Object value) {
            setKey(key);
            setValue(value);
        }
    }
}
