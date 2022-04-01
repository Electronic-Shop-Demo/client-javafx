package com.mairwunnx.application;

import com.mairwunnx.application.router.Router;
import com.mairwunnx.application.router.controller.RouterController;
import com.mairwunnx.application.router.types.RouterArg;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements RouterController, Initializable {
    private Router router;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onBackButtonClick() {
        router.back();
    }

    @FXML
    protected void onHelloButtonClick() {
        router.navigate("hello-test");
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
    }

    @Override
    public <T> void onShow(@NotNull final Router router, @Nullable final RouterArg<T> arg) {
        this.router = router;
    }

    @Override
    public void onExit(@NotNull final Router router) {

    }
}