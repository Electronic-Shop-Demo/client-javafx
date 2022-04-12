package com.mairwunnx.ui.commons;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

public final class ImageUtils {
    public static void clip(@NotNull final ImageView imageView, final double radii, final double size) {
        final var clip = new Rectangle(size, size);
        final var parameters = new SnapshotParameters();

        clip.setArcWidth(radii);
        clip.setArcHeight(radii);
        imageView.setClip(clip);
        parameters.setFill(Color.TRANSPARENT);

        final var writableImage = imageView.snapshot(parameters, null);

        imageView.setClip(null);
        imageView.setImage(writableImage);
    }
}
