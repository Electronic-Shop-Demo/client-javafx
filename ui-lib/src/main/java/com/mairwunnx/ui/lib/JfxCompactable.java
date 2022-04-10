package com.mairwunnx.ui.lib;

import javafx.scene.Node;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * Interface for all JavaFX controls can be compact, depends
 * on stage width and height.
 *
 * @since 1.0.0.
 */
public interface JfxCompactable {
    /**
     * @return true if compact mode for this view is enabled otherwise else.
     * @since 1.0.0.
     */
    boolean isCompactModeEnabled();

    /**
     * Does enable compact mode for current view.
     *
     * @param isEnabled true to turn on compact mode otherwise false.
     * @since 1.0.0.
     */
    void setCompactModeEnabled(final boolean isEnabled);

    /**
     * @return hash map structure with saved params before compact
     * mode enabled.
     * @since 1.0.0.
     */
    @NotNull HashMap<Node, Object> getCompactSavedParams();
}
