package com.mairwunnx.ui.commons;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static com.mairwunnx.ui.commons.FlowUtils.noop;

/**
 * Interaction utils for views.
 *
 * @since 1.0.0.
 */
public final class InteractionUtils {
    /**
     * Sets event on user interact with specified action represented
     * as {@link Consumer}.
     * <p/>
     * Does add events for keyboard (enter and space) and mouse click.
     *
     * @param node     target node for subscribing events.
     * @param consumer consumer as action.
     * @param <T>      generic type of node.
     * @since 1.0.0.
     */
    public static <T extends Node> void setOnUserInteract(@NotNull final T node, @NotNull final Consumer<T> consumer) {
        node.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                consumer.accept(node);
            }
        });

        node.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case SPACE, ENTER -> consumer.accept(node);
                default -> noop();
            }
        });
    }
}
