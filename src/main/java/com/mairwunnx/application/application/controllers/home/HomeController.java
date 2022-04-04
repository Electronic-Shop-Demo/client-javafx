package com.mairwunnx.application.application.controllers.home;

import com.mairwunnx.application.application.router.Router;
import com.mairwunnx.application.application.router.controller.RouterController;
import com.mairwunnx.application.application.router.types.RouterArg;
import com.mairwunnx.application.application.views.TopBar;
import javafx.fxml.FXML;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Log4j2
public class HomeController implements RouterController {
    private Router router;

    @FXML
    private TopBar topbar;

    @Override
    public <T> void onShow(@NotNull final Router router, @Nullable final RouterArg<T> arg) {
        this.router = router;
        topbar.setOnBackRequested(() -> log.debug("top bar back button clicked"));
        topbar.setOnSearchChanged(log::debug);
    }
}
