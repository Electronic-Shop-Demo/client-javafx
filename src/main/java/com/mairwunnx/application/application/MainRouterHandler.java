package com.mairwunnx.application.application;

import com.mairwunnx.ui.navigation.Router;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("ClassCanBeRecord")
public final class MainRouterHandler {
    private final Router router;

    public MainRouterHandler(@NotNull final Router router) {
        this.router = router;
    }

    public void interceptSceneChanging(@NotNull final Stage stage) {
        stage.setMinWidth(stage.getScene().getRoot().minWidth(stage.getHeight()));
        initializeSceneAccelerators(stage);
        initializeSceneFocusHandlers(stage);
    }

    private void initializeSceneAccelerators(@NotNull final Stage stage) {
        stage.getScene().getAccelerators().put(
            new KeyCodeCombination(KeyCode.LEFT, KeyCombination.ALT_DOWN),
            router::back
        );
    }

    private void initializeSceneFocusHandlers(@NotNull final Stage stage) {
        stage.getScene().getAccelerators().put(
            new KeyCodeCombination(KeyCode.ESCAPE),
            () -> {
                if (stage.getScene().getFocusOwner() != null) {
                    stage.getScene().getRoot().requestFocus();
                }
            }
        );
    }
}
