package com.mairwunnx.application.application.controllers;

import com.mairwunnx.dto.response.products.ProductResponse;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.random.RandomGenerator;

public class ProductCellController {
    private static final RandomGenerator RANDOM_GENERATOR = RandomGenerator.of("L32X64MixRandom");

    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private Label price;

    @FXML
    private VBox parent;

    @FXML
    private ImageView image;

    @FXML
    private Label warning;

    public ProductCellController() {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("product-cell.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(final ProductResponse product) {
        title.setText(product.title());
        description.setText(product.description());
        price.setText(product.price() + "₽");

        if (product.description().isEmpty()) {
            description.setVisible(false);
            warning.setManaged(false);
        }

        if (product.count() <= 100) { // todo move in mapper layer
            if (RANDOM_GENERATOR.nextInt(10) == 7) { // todo: remove
                warning.setText("Осталось немного");
                warning.setVisible(true);
                warning.setManaged(true);
            }
        }

        final var clip = new Rectangle(300, image.getImage().getHeight());
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        image.setClip(clip);

        final var parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        final var writableImage = image.snapshot(parameters, null);

        image.setClip(null);

        image.setImage(writableImage);

    }

    public VBox getParent() {
        return parent;
    }
}
