package com.mairwunnx.application;

import com.mairwunnx.application.router.Router;
import com.mairwunnx.application.router.RouterFX;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class Application extends javafx.application.Application {
    private Router router;

    @Override
    public void init() throws Exception {
        super.init();

        System.setProperty("prism.lcdtext", "false");
        System.setProperty("prism.text", "t2k");

        router = RouterFX.build(router -> {
            router
                .configure(config -> config.automapping(true).autosize(true).implicitDefaults(true))
                .map(mapper -> {
                    mapper.withKey("hello-view").withLayout("hello-view.fxml").withTitle("Hello View").apply();
                    mapper.withLayout("hello-test.fxml").apply();
                })
                .ensureInitialize();
        });
    }

    @Override
    public void start(final @NotNull Stage stage) {
        stage.setOnCloseRequest(e -> Platform.exit());
        router.ensureStage(stage);
        router.navigate("hello-view");
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        router.shutdown();
        router = null;
    }
}
