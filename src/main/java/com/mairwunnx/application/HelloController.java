package com.mairwunnx.application;

import com.mairwunnx.application.router.Router;
import com.mairwunnx.application.router.controller.RouterController;
import com.mairwunnx.application.router.types.RouterArg;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements RouterController, Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onBackButtonClick() {
        router.back();
    }

    @FXML
    protected void onHelloButtonClick() {
        if (router != null) {
            router.navigate("hello-test", new RouterArg<>("router", router));
        }
        /*welcomeText.setText("Welcome to JavaFX Application!");*/
    }

    private Router router;

    @Override
    public <T> void onShow(@NotNull final Stage stage, @NotNull final Scene scene, @Nullable final RouterArg<T> arg) {
        final Router ss = getArgumentFromOrNull(arg, "router");
        router = ss;
    }

    @Override
    public void onExit(@NotNull final Stage stage, @NotNull final Scene scene) {

    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

    }
}