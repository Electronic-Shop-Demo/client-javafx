package com.mairwunnx.application.application.router;

import com.mairwunnx.application.application.router.configurators.RouterConfiguration;
import com.mairwunnx.application.application.router.graph.RouterGraph;
import com.mairwunnx.application.application.router.types.RouterArg;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ResourceBundle;

public interface Router {
    void ensureStage(@NotNull Stage stage);

    void ensureBundle(@NotNull ResourceBundle bundle);

    void ensureStylesheet(@NotNull String path);

    @Nullable Stage getStage();

    @Nullable Scene getCurrentScene();

    @Nullable ResourceBundle getCurrentBundle();

    @Nullable RouterConfiguration getConfiguration();

    @NotNull RouterGraph getGraph();

    void shutdown();

    void navigate(@NotNull String key);

    void navigate(@NotNull String key, @NotNull ResourceBundle bundle);

    <T> void navigate(@NotNull String key, @NotNull RouterArg<T> arg);

    <T> void navigate(@NotNull String key, @NotNull RouterArg<T> arg, @NotNull ResourceBundle bundle);

    void navigateClearBackStack(@NotNull String key);

    void navigateClearBackStack(@NotNull String key, @NotNull ResourceBundle bundle);

    <T> void navigateClearBackStack(@NotNull String key, @NotNull RouterArg<T> arg);

    <T> void navigateClearBackStack(@NotNull String key, @NotNull RouterArg<T> arg, @NotNull ResourceBundle bundle);

    void back();

    <T> void back(@NotNull RouterArg<T> arg);

    void reload();

    void reload(@NotNull ResourceBundle bundle);

    <T> void reload(@NotNull RouterArg<T> arg);

    <T> void reload(@NotNull RouterArg<T> arg, @NotNull ResourceBundle bundle);
}
