package com.mairwunnx.application.application.controllers;

import com.mairwunnx.ui.lib.TopBar;
import com.mairwunnx.ui.navigation.Router;
import com.mairwunnx.ui.navigation.controller.RouterController;
import com.mairwunnx.ui.navigation.types.RouterArg;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import lombok.AccessLevel;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Log4j2
public final class HomeController extends Controller implements RouterController {
    @FXML
    @lombok.Getter(AccessLevel.PROTECTED)
    private AnchorPane root;

    @FXML
    private TopBar topbar;

    @Override
    public <T> void onShow(@NotNull final Router router, @Nullable final RouterArg<T> arg) {
        super.onShow(router, arg);

        topbar.setBackAvailable(router.getGraph().getPages().size() > 1);

        root.setOnMousePressed(e -> root.requestFocus());
        topbar.setOnLocationDialogRequested(() -> {
            System.out.println("");
            return "";
        });
        topbar.setOnBackRequested(() -> log.debug("top bar back button clicked"));
        topbar.setOnSearchChanged(log::debug);
    }
}
