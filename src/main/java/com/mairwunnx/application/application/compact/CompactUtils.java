package com.mairwunnx.application.application.compact;

import javafx.scene.Node;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public final class CompactUtils {
    public static final String COMPACT_CSS_CLASS_SUFFIX = "-compact";

    public static void switchToCompact(
        final boolean isCompact,
        @NotNull final Node... node
    ) {
        for (final Node currentNode : node) {
            final var clazz = currentNode.getStyleClass().get(1);

            if (isCompact) {
                currentNode.getStyleClass().removeAll(clazz);
                currentNode.getStyleClass().addAll(clazz + COMPACT_CSS_CLASS_SUFFIX);
            } else {
                currentNode.getStyleClass().removeAll(clazz);
                currentNode.getStyleClass().addAll(StringUtils.replace(clazz, "-compact", ""));
            }
        }
    }
}
