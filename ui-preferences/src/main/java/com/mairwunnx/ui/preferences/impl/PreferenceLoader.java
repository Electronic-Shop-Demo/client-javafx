package com.mairwunnx.ui.preferences.impl;

import com.mairwunnx.ui.preferences.PreferenceType;
import com.mairwunnx.ui.preferences.Preferences;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

@ApiStatus.Internal
public class PreferenceLoader {
    @Nullable private Path configurationPath;
    @Nullable private PreferenceType preferenceType;
    @Nullable private Properties properties;

    public @NotNull Preferences load(@NotNull final Path path, @NotNull final PreferenceType type) {
        configurationPath = path;
        preferenceType = type;
        loadProperties();
        return new PreferencesImpl(configurationPath, properties, preferenceType, this);
    }

    public @NotNull Properties loadProperties() {
        properties = new Properties();
        ensureDirectories();
        ensurePreferences();
        return properties;
    }

    private void ensureDirectories() {
        if (configurationPath != null) {
            try {
                Files.createDirectories(configurationPath.getParent());
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void ensurePreferences() {
        if (configurationPath != null) {
            if (Files.exists(configurationPath)) {
                try (final var is = Files.newInputStream(configurationPath)) {
                    if (properties != null) {
                        if (preferenceType != null) {
                            switch (preferenceType) {
                                case XML -> properties.loadFromXML(is);
                                case PROPERTIES -> properties.load(is);
                                default -> throw new IllegalStateException("Unexpected value: " + preferenceType);
                            }
                        }
                    }
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                if (preferenceType != null) {
                    try (final var os = Files.newOutputStream(configurationPath)) {
                        if (properties != null) {
                            switch (preferenceType) {
                                case XML -> properties.storeToXML(os, null);
                                case PROPERTIES -> properties.store(os, null);
                                default -> throw new IllegalStateException("Unexpected value: " + preferenceType);
                            }
                        }
                    } catch (final IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
