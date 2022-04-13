package com.mairwunnx.application.application.controllers;

import com.mairwunnx.ui.lib.TopBar;
import com.mairwunnx.ui.navigation.Router;
import com.mairwunnx.ui.navigation.controller.RouterController;
import com.mairwunnx.ui.navigation.types.RouterArg;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    @FXML private Button switchBack;
    @FXML private Button switchLogin;
    @FXML private Button avatar;
    @FXML private Button decrementBadge;
    @FXML private Button incrementBadge;
    @FXML private Button setLocation;
    @FXML private Button requestLocation;
    @FXML private TopBar topbar;

    private boolean isLoggedIn;
    private int counter;

    @Override
    public <T> void onShow(@NotNull final Router router, @Nullable final RouterArg<T> arg) {
        super.onShow(router, arg);
        final var topBarApi = topbar.getApi();

        switchBack.setOnMouseClicked(event -> topBarApi.setBackAvailable(!topBarApi.isBackAvailable()));
        switchLogin.setOnMouseClicked(event -> {
            isLoggedIn = !isLoggedIn;
            topBarApi.setIsLoggedIn(isLoggedIn);
        });
        avatar.setOnMouseClicked(event -> topBarApi.setAvatarImage("https://www.w3schools.com/howto/img_avatar.png"));
        decrementBadge.setOnMouseClicked(event -> {
            counter -= 10;
            topBarApi.setFavoriteCount(counter);
        });
        incrementBadge.setOnMouseClicked(event -> {
            counter += 10;
            topBarApi.setFavoriteCount(counter);
        });
        setLocation.setOnMouseClicked(event -> topBarApi.setLocation("Moscow"));
        requestLocation.setOnMouseClicked(event -> topBarApi.setOnLocationDialogRequested(() -> "Tokyo"));

        topbar.getApi().setBackAvailable(router.getGraph().getPages().size() > 1);

        root.setOnMousePressed(e -> root.requestFocus());
        topbar.setOnBackRequested(() -> log.debug("top bar back button clicked"));
        topbar.setOnSearchChanged(log::debug);
    }

    @Override
    public void onExit(@NotNull final Router router) {
        System.out.println("HomeController.onExit");
        topbar.onViewDestroy();
    }
}
