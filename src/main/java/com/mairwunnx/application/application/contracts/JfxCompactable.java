package com.mairwunnx.application.application.contracts;

import javafx.scene.Node;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public interface JfxCompactable {
    boolean isCompactModeEnabled();

    void setCompactMode(final boolean isEnabled);

    @NotNull HashMap<Node, Object> getCompactSavedParams();
}
