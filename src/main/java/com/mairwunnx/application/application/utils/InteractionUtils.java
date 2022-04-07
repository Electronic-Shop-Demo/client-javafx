package com.mairwunnx.application.application.utils;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class InteractionUtils {
    public static <T extends Node> void setOnUserInteract(
        @NotNull final T node,
        @NotNull final Consumer<T> consumer
    ) {
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

    private static void noop() {
    }
}
