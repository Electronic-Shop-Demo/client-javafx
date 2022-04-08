package com.mairwunnx.application.application.contracts;

import javafx.scene.Node;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * Interface for all JavaFX controls can be compact, depends
 * on stage width and height.
 *
 * @since 1.0.0
 */
public interface JfxCompactable {
    /**
     * @return true if compact mode for this view is enabled otherwise else.
     */
    boolean isCompactModeEnabled();

    /**
     * Does enable compact mode for current view.
     *
     * @param isEnabled true to turn on compact mode otherwise false.
     */
    void setCompactMode(final boolean isEnabled);

    /**
     * @return hash map structure with saved params before compact
     * mode enabled.
     */
    @NotNull HashMap<Node, Object> getCompactSavedParams();
}
