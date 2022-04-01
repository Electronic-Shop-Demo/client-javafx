package com.mairwunnx.application;

import com.mairwunnx.application.router.Router;
import com.mairwunnx.application.router.RouterFX;
import javafx.application.Application;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class EntryPoint extends Application {
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
        router.ensureStage(stage);
        router.navigate("hello-view");

        /*router.navigate("hello-view", new RouterArg<>("testInt", 1));
        router.navigate("hello-view", new RouterArg<>("testInt", 1));
        router.navigateClearBackStack("hello-view");
        router.navigateClearBackStack("hello-view", new RouterArg<>("testString", "sdsad"));
        router.back();
        router.getGraph().clear();
        router.getGraph().getLast();
        router.getGraph().getFirst();
        router.getGraph().getPages();*/
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        router = null;
    }

    public static void main(final String[] args) {
        launch();
    }
}