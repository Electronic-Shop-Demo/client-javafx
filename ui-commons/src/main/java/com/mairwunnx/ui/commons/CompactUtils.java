package com.mairwunnx.ui.commons;

import javafx.scene.Node;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Compact utils for compactable views.
 *
 * @since 1.0.0.
 */
public final class CompactUtils {
    /**
     * Compact view css class suffix.
     *
     * @since 1.0.0.
     */
    public static final String COMPACT_CSS_CLASS_SUFFIX = "-compact";

    /**
     * Does switch to compact specified views.
     *
     * @param isCompact compact or not compact?
     * @param node      target node to switch to compact.
     * @since 1.0.0.
     */
    public static void switchToCompact(final boolean isCompact, @NotNull final Node... node) {
        for (final Node currentNode : node) {
            final var clazz = currentNode.getStyleClass().get(1);

            if (isCompact) {
                currentNode.getStyleClass().removeAll(clazz);
                currentNode.getStyleClass().addAll(clazz + COMPACT_CSS_CLASS_SUFFIX);
            } else {
                currentNode.getStyleClass().removeAll(clazz);
                currentNode.getStyleClass().addAll(StringUtils.replace(clazz, COMPACT_CSS_CLASS_SUFFIX, ""));
            }
        }
    }
}
