package com.mairwunnx.application.application.controllers.home;

import com.mairwunnx.application.application.views.TopBar;
import com.mairwunnx.ui.navigation.Router;
import com.mairwunnx.ui.navigation.controller.RouterController;
import com.mairwunnx.ui.navigation.types.RouterArg;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Log4j2
public class HomeController implements RouterController {
    @FXML
    private TopBar topbar;

    @FXML
    private AnchorPane root;

    @Override
    public <T> void onShow(@NotNull final Router router, @Nullable final RouterArg<T> arg) {
        root.requestFocus();
        root.setOnMousePressed(e -> root.requestFocus());
        topbar.setOnBackRequested(() -> log.debug("top bar back button clicked"));
        topbar.setOnSearchChanged(log::debug);
    }
}
