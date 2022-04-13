package com.mairwunnx.application.application.controllers;/*
package com.mairwunnx.application.application.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.mairwunnx.application.application.components.HttpClientComponent;
import com.mairwunnx.dto.response.products.ProductResponse;
import com.mairwunnx.ui.navigation.Router;
import com.mairwunnx.ui.navigation.types.RouterArg;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public final class HelloController extends Controller implements Initializable {
    private Router router;

    @FXML
    private Label welcomeText;

    @FXML
    private FlowPane products;

    @Inject
    private HttpClientComponent httpClientComponent;

    private final ObservableList<ProductResponse> observableList = FXCollections.observableArrayList();

    @FXML
    private void onBackButtonClick() {
        router.back();
    }

    @FXML
    private void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");

        final var request =
            HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://192.168.0.104:8080/products"))
                .build();

        final var mapper = new ObjectMapper();

        httpClientComponent
            .getHttpClient()
            .sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .handle((response, throwable) -> {
                if (throwable != null) {
                    Platform.runLater(() -> welcomeText.setText("Error! Connection error."));
                }
                return response;
            })
            .thenApply(response -> {
                handleStatus(response.statusCode());
                return response;
            })
            .thenApply(HttpResponse::body)
            .thenApply(s -> {
                try {
                    final List<ProductResponse> responses = Arrays.asList(mapper.readValue(s, ProductResponse[].class));
                    System.out.println("responses = " + responses);
                    return responses;
                } catch (final JsonProcessingException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            })
            .thenAccept(observableList::setAll);

        welcomeText.setText("WOW!");
    }

    private void handleStatus(final int status) {
        System.out.println("status = " + status);
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        observableList.addListener((ListChangeListener<? super ProductResponse>) c -> {
            final var productResponses = c.getList();
            final List<Node> cells = new ArrayList<>();
            productResponses.forEach(productResponse -> {
                final var controller = new ProductCellController();
                Platform.runLater(() -> {
                    controller.setInfo(productResponse);
                    cells.add(controller.getParent());
                });
            });

            Platform.runLater(() -> products.getChildren().addAll(cells));
        });
    }

    @Override
    protected @NotNull Node getRoot() {
        return products;
    }

    @Override
    public <T> void onShow(@NotNull final Router router, @Nullable final RouterArg<T> arg) {
        this.router = router;
    }
}
*/
