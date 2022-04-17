package com.mairwunnx.ui.lib.managers;

import javafx.scene.Node;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;

/**
 * Badge manager for current badge implementation stage.
 *
 * @since 1.0.0.
 */
public interface BadgeManager {
    /**
     * Provides all needed nodes to use this manager.
     *
     * @param badge     badge panel element.
     * @param badgeText badge label (counter label).
     * @return current instance for method chaining.
     * @since 1.0.0.
     */
    @NotNull BadgeManager use(@NotNull final Node badge, @NotNull final Label badgeText);

    /**
     * Provides badge count as int.
     *
     * @param count badge counter value.
     * @return current instance for method chaining.
     * @since 1.0.0.
     */
    @NotNull BadgeManager withCount(final int count);

    /**
     * Applies changes and reset current instance state.
     *
     * @since 1.0.0.
     */
    void apply();
}
