package com.mairwunnx.ui.preferences;

import com.mairwunnx.ui.preferences.impl.PreferenceLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Preferences contract, there is all method for safety interactions
 * with preferences.
 *
 * @since 1.0.0.
 */
@SuppressWarnings({"UnusedReturnValue", "unused"})
public interface Preferences {
    /**
     * Does loading configuration from file.
     *
     * @param path path to configuration file to load.
     * @param type preference type to load.
     * @since 1.0.0.
     */
    static Preferences load(@NotNull final Path path, @NotNull final PreferenceType type) {
        return new PreferenceLoader().load(path, type);
    }

    /**
     * @param key key of preference.
     * @return value of preference as string (force non-nullable)
     * from preference or throw {@link NullPointerException}.
     * @since 1.0.0.
     */
    @NotNull String getString(@NotNull final String key);

    /**
     * @param key key of preference.
     * @return value of preference as string or null.
     * @since 1.0.0.
     */
    @Nullable String getStringOrNull(@NotNull final String key);

    /**
     * @param key        key of preference.
     * @param defaultVal default value in case if preference does
     *                   not exist.
     * @return not nullable value of preference as string or
     * {@code defaultVal} value.
     * @since 1.0.0.
     */
    @NotNull String getStringOrDefault(@NotNull final String key, @NotNull String defaultVal);

    /**
     * @param key key of preference.
     * @return value of preference as int
     * from preference or return default primitive value.
     * @since 1.0.0.
     */
    int getInt(@NotNull final String key);

    /**
     * @param key key of preference.
     * @return value of preference as int or {@code defaultVal} value.
     * @since 1.0.0.
     */
    int getIntOrDefault(@NotNull final String key, final int defaultVal);

    /**
     * @param key key of preference.
     * @return value of preference as long
     * from preference or return default primitive value.
     * @since 1.0.0.
     */
    long getLong(@NotNull final String key);

    /**
     * @param key key of preference.
     * @return value of preference as long or {@code defaultVal} value.
     * @since 1.0.0.
     */
    long getLongOrDefault(@NotNull final String key, final long defaultVal);

    /**
     * @param key key of preference.
     * @return value of preference as boolean
     * from preference or return default primitive value.
     * @since 1.0.0.
     */
    boolean getBoolean(@NotNull final String key);

    /**
     * @param key key of preference.
     * @return value of preference as boolean or {@code defaultVal} value.
     * @since 1.0.0.
     */
    boolean getBooleanOrDefault(@NotNull final String key, final boolean defaultVal);

    /**
     * @param key key of preference.
     * @return value of preference as {@code List<String>} (force non-nullable)
     * from preference or throw {@link NullPointerException}.
     * @since 1.0.0.
     */
    @NotNull List<String> getList(@NotNull final String key);

    /**
     * @param key key of preference.
     * @return value of preference as {@code List<String>} or null.
     * @since 1.0.0.
     */
    @Nullable List<String> getListOrNull(@NotNull final String key);

    /**
     * @param key        key of preference.
     * @param defaultVal default value in case if preference does
     *                   not exist.
     * @return not nullable value of preference as {@code List<String>} or
     * {@code defaultVal} value.
     * @since 1.0.0.
     */
    @NotNull List<String> getListOrDefault(@NotNull final String key, @NotNull final List<String> defaultVal);

    /**
     * Does set string property with specified value by key.
     *
     * @param key   key of preference.
     * @param value nullable new string value.
     * @return {@link Preferences} current instance for method chaining.
     * @since 1.0.0.
     */
    @NotNull Preferences setString(@NotNull final String key, @Nullable final String value);

    /**
     * Does set int property with specified value by key.
     *
     * @param key   key of preference.
     * @param value new int value.
     * @return {@link Preferences} current instance for method chaining.
     * @since 1.0.0.
     */
    @NotNull Preferences setInt(@NotNull final String key, final int value);

    /**
     * Does set boolean property with specified value by key.
     *
     * @param key   key of preference.
     * @param value new boolean value.
     * @return {@link Preferences} current instance for method chaining.
     * @since 1.0.0.
     */
    @NotNull Preferences setBoolean(@NotNull final String key, final boolean value);

    /**
     * Does set {@code List<String>} property with specified value by key.
     *
     * @param key   key of preference.
     * @param value nullable new List value.
     * @return {@link Preferences} current instance for method chaining.
     * @since 1.0.0.
     */
    @NotNull Preferences setList(@NotNull final String key, @Nullable final List<String> value);

    /**
     * Does commit changes asynchronously.
     *
     * @return non-nullable completable future of async operation.
     * @since 1.0.0.
     */
    @NotNull CompletableFuture<Void> commitAsync();

    /**
     * Does commit changes synchronously.
     *
     * @since 1.0.0.
     */
    void commitSync();

    /**
     * Does reload configuration file from disk.
     *
     * @since 1.0.0.
     */
    void reload();
}
