package com.mairwunnx.application.application.router.impl;

import com.mairwunnx.application.application.router.Router;
import com.mairwunnx.application.application.router.configurators.RouterConfiguration;
import com.mairwunnx.application.application.router.configurators.RouterListener;
import com.mairwunnx.application.application.router.configurators.RouterMapper;
import com.mairwunnx.application.application.router.controller.RouterController;
import com.mairwunnx.application.application.router.exceptions.RouteNotDefinedException;
import com.mairwunnx.application.application.router.graph.RouterGraph;
import com.mairwunnx.application.application.router.graph.RouterGraphImpl;
import com.mairwunnx.application.application.router.internal.EventAction;
import com.mairwunnx.application.application.router.types.RouterArg;
import com.mairwunnx.application.application.router.types.RouterEntry;
import com.mairwunnx.application.application.router.types.RouterEvent;
import com.mairwunnx.application.application.router.types.RouterGraphEntry;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@SuppressWarnings("DuplicatedCode")
@ApiStatus.Internal
public final class RouterImpl implements Router {
    private boolean isStageInitialized;

    @Nullable
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private Scene currentScene;

    @Nullable
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private ResourceBundle currentBundle;

    @Nullable
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private String currentStylesheet;

    @Nullable
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private Stage stage;

    @Nullable
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private RouterConfiguration configuration;

    @Nullable
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private RouterMapper mapper;

    @Nullable
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private RouterListener listener;

    @NotNull
    @Setter(value = AccessLevel.PRIVATE)
    private RouterGraphImpl graph;

    @NotNull
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private List<RouterEntry> routerEntries;

    @Nullable
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private RouterController currentController;

    private final StackWalker walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    public RouterImpl(
        @Nullable final Stage stage,
        @Nullable final RouterConfiguration configuration,
        @Nullable final RouterMapper mapper,
        @Nullable final RouterListener listener,
        @NotNull final List<RouterEntry> routerEntries
    ) {
        if (stage != null) {
            isStageInitialized = true;
            setStage(stage);
        }

        setConfiguration(configuration);
        setMapper(mapper);
        setListener(listener);
        setRouterEntries(routerEntries);

        if (getConfiguration() != null) {
            if (getConfiguration().getCurrentBundle() != null) {
                setCurrentBundle(getConfiguration().getCurrentBundle());
            }
        }

        setGraph(new RouterGraphImpl());
    }

    @NotNull
    public RouterGraph getGraph() {
        return graph;
    }

    @Override
    public void ensureStage(@NotNull final Stage stage) {
        requireIsStageNotInitialized();
        isStageInitialized = true;
        setStage(stage);
    }

    @Override
    public void ensureBundle(@NotNull final ResourceBundle bundle) {
        setCurrentBundle(bundle);
    }

    @Override
    public void ensureStylesheet(@NotNull final String path) {
        if (getStage() == null) {
            throw new IllegalStateException(
                "Can't ensure stylesheets while stage is not provided, provide stage via ensureStage or configuration"
            );
        }

        setCurrentStylesheet(path);
    }

    @Override
    public void shutdown() {
        if (getCurrentController() != null) {
            getCurrentController().onExit(this);
        }

        setCurrentScene(null);
        setCurrentBundle(null);
        setCurrentController(null);
        setConfiguration(null);
        setMapper(null);
        setListener(null);
        setStage(null);
    }

    @Override
    public void navigate(@NotNull final String key) {
        checkStageNotNull();

        if (fireOnNavigationRequestedEventWithArg(key, null)) return;

        final var entry = getRouterEntryByKeyOrThrowException(key);
        final var callerClass = walker.getCallerClass();
        final FXMLLoader fxmlLoader = getFXMLLoader(callerClass, entry, null);
        final Scene scene = getLoadedScene(entry, fxmlLoader);

        graph.push(new RouterGraphEntry<>(entry.key(), entry.layout(), entry.size(), entry.title(), null));
        currentScene = scene;

        applyStageAndScene(fxmlLoader, scene, entry, null, callerClass);
        fireOnNavigationPerformedEventWithArg(key, null);
    }

    @Override
    public void navigate(@NotNull final String key, @NotNull final ResourceBundle bundle) {
        checkStageNotNull();

        if (fireOnNavigationRequestedEventWithArg(key, null)) return;

        final var entry = getRouterEntryByKeyOrThrowException(key);
        final var callerClass = walker.getCallerClass();
        final FXMLLoader fxmlLoader = getFXMLLoader(callerClass, entry, bundle);
        final Scene scene = getLoadedScene(entry, fxmlLoader);

        graph.push(new RouterGraphEntry<>(entry.key(), entry.layout(), entry.size(), entry.title(), null));
        currentScene = scene;

        applyStageAndScene(fxmlLoader, scene, entry, null, callerClass);
        fireOnNavigationPerformedEventWithArg(key, null);
    }

    @Override
    public <T> void navigate(@NotNull final String key, @NotNull final RouterArg<T> arg) {
        checkStageNotNull();

        if (fireOnNavigationRequestedEventWithArg(key, arg)) return;

        final var entry = getRouterEntryByKeyOrThrowException(key);
        final var callerClass = walker.getCallerClass();
        System.out.println("entry.key() = " + entry.key());
        System.out.println("entry.layout() = " + entry.layout());
        final FXMLLoader fxmlLoader = getFXMLLoader(callerClass, entry, null);
        final Scene scene = getLoadedScene(entry, fxmlLoader);

        graph.push(new RouterGraphEntry<>(entry.key(), entry.layout(), entry.size(), entry.title(), arg));
        currentScene = scene;

        applyStageAndScene(fxmlLoader, scene, entry, arg, callerClass);
        fireOnNavigationPerformedEventWithArg(key, arg);
    }

    @Override
    public <T> void navigate(
        @NotNull final String key,
        @NotNull final RouterArg<T> arg,
        @NotNull final ResourceBundle bundle
    ) {
        checkStageNotNull();

        if (fireOnNavigationRequestedEventWithArg(key, arg)) return;

        final var entry = getRouterEntryByKeyOrThrowException(key);
        final var callerClass = walker.getCallerClass();
        final FXMLLoader fxmlLoader = getFXMLLoader(callerClass, entry, bundle);
        final Scene scene = getLoadedScene(entry, fxmlLoader);

        graph.push(new RouterGraphEntry<>(entry.key(), entry.layout(), entry.size(), entry.title(), arg));
        currentScene = scene;

        applyStageAndScene(fxmlLoader, scene, entry, arg, callerClass);
        fireOnNavigationPerformedEventWithArg(key, arg);
    }

    @Override
    public void navigateClearBackStack(@NotNull final String key) {
        checkStageNotNull();

        if (fireOnNavigationRequestedEventWithArg(key, null)) return;

        final var entry = getRouterEntryByKeyOrThrowException(key);
        final var callerClass = walker.getCallerClass();
        final FXMLLoader fxmlLoader = getFXMLLoader(callerClass, entry, null);
        final Scene scene = getLoadedScene(entry, fxmlLoader);

        graph.clear();
        graph.push(new RouterGraphEntry<>(entry.key(), entry.layout(), entry.size(), entry.title(), null));
        currentScene = scene;

        applyStageAndScene(fxmlLoader, scene, entry, null, callerClass);
        fireOnNavigationPerformedEventWithArg(key, null);
    }

    @Override
    public void navigateClearBackStack(@NotNull final String key, @NotNull final ResourceBundle bundle) {
        checkStageNotNull();

        if (fireOnNavigationRequestedEventWithArg(key, null)) return;

        final var entry = getRouterEntryByKeyOrThrowException(key);
        final var callerClass = walker.getCallerClass();
        final FXMLLoader fxmlLoader = getFXMLLoader(callerClass, entry, bundle);
        final Scene scene = getLoadedScene(entry, fxmlLoader);

        graph.clear();
        graph.push(new RouterGraphEntry<>(entry.key(), entry.layout(), entry.size(), entry.title(), null));
        currentScene = scene;

        applyStageAndScene(fxmlLoader, scene, entry, null, callerClass);
        fireOnNavigationPerformedEventWithArg(key, null);
    }

    @Override
    public <T> void navigateClearBackStack(@NotNull final String key, @NotNull final RouterArg<T> arg) {
        checkStageNotNull();

        if (fireOnNavigationRequestedEventWithArg(key, arg)) return;

        final var entry = getRouterEntryByKeyOrThrowException(key);
        final var callerClass = walker.getCallerClass();
        final FXMLLoader fxmlLoader = getFXMLLoader(callerClass, entry, null);
        final Scene scene = getLoadedScene(entry, fxmlLoader);

        graph.clear();
        graph.push(new RouterGraphEntry<>(entry.key(), entry.layout(), entry.size(), entry.title(), arg));
        currentScene = scene;

        applyStageAndScene(fxmlLoader, scene, entry, arg, callerClass);
        fireOnNavigationPerformedEventWithArg(key, arg);
    }

    @Override
    public <T> void navigateClearBackStack(
        @NotNull final String key,
        @NotNull final RouterArg<T> arg,
        @NotNull final ResourceBundle bundle
    ) {
        checkStageNotNull();

        if (fireOnNavigationRequestedEventWithArg(key, arg)) return;

        final var entry = getRouterEntryByKeyOrThrowException(key);
        final var callerClass = walker.getCallerClass();
        final FXMLLoader fxmlLoader = getFXMLLoader(callerClass, entry, bundle);
        final Scene scene = getLoadedScene(entry, fxmlLoader);

        graph.clear();
        graph.push(new RouterGraphEntry<>(entry.key(), entry.layout(), entry.size(), entry.title(), arg));
        currentScene = scene;

        applyStageAndScene(fxmlLoader, scene, entry, arg, callerClass);
        fireOnNavigationPerformedEventWithArg(key, arg);
    }

    @Override
    public void back() {
        checkStageNotNull();

        if (fireOnBackRequestedEventWithArg(null)) return;

        graph.removeLast();
        final var last = graph.getLast();

        if (last != null) {
            final var entry = getRouterEntryByKeyOrThrowException(last.key());
            final var callerClass = walker.getCallerClass();
            final FXMLLoader fxmlLoader = getFXMLLoader(callerClass, entry, null);
            final Scene scene = getLoadedScene(entry, fxmlLoader);

            currentScene = scene;

            applyStageAndScene(fxmlLoader, scene, entry, last.arg(), callerClass);
            fireOnBackPerformedEventWithArg(null);
        }
    }

    @Override
    public <T> void back(@NotNull final RouterArg<T> arg) {
        checkStageNotNull();

        if (fireOnBackRequestedEventWithArg(arg)) return;

        graph.removeLast();
        final var last = graph.getLast();

        if (last != null) {
            final var entry = getRouterEntryByKeyOrThrowException(last.key());
            final var callerClass = walker.getCallerClass();
            final FXMLLoader fxmlLoader = getFXMLLoader(callerClass, entry, null);
            final Scene scene = getLoadedScene(entry, fxmlLoader);

            currentScene = scene;

            applyStageAndScene(fxmlLoader, scene, entry, arg, callerClass);
            fireOnBackPerformedEventWithArg(arg);
        }
    }

    @Override
    public void reload() {
        checkStageNotNull();

        final var last = graph.getLast();

        if (last != null) {
            final var entry = getRouterEntryByKeyOrThrowException(last.key());
            final var callerClass = walker.getCallerClass();
            final FXMLLoader fxmlLoader = getFXMLLoader(callerClass, entry, null);
            final Scene scene = getLoadedScene(entry, fxmlLoader);

            currentScene = scene;

            applyStageAndScene(fxmlLoader, scene, entry, last.arg(), callerClass);
        }
    }

    @Override
    public void reload(@NotNull final ResourceBundle bundle) {
        checkStageNotNull();

        final var last = graph.getLast();

        if (last != null) {
            final var entry = getRouterEntryByKeyOrThrowException(last.key());
            final var callerClass = walker.getCallerClass();
            final FXMLLoader fxmlLoader = getFXMLLoader(callerClass, entry, bundle);
            final Scene scene = getLoadedScene(entry, fxmlLoader);

            currentScene = scene;

            applyStageAndScene(fxmlLoader, scene, entry, last.arg(), callerClass);
        }
    }

    @Override
    public <T> void reload(@NotNull final RouterArg<T> arg) {
        checkStageNotNull();

        final var last = graph.getLast();

        if (last != null) {
            final var entry = getRouterEntryByKeyOrThrowException(last.key());
            final var callerClass = walker.getCallerClass();
            final FXMLLoader fxmlLoader = getFXMLLoader(callerClass, entry, null);
            final Scene scene = getLoadedScene(entry, fxmlLoader);

            currentScene = scene;

            applyStageAndScene(fxmlLoader, scene, entry, arg, callerClass);
        }
    }

    @Override
    public <T> void reload(@NotNull final RouterArg<T> arg, @NotNull final ResourceBundle bundle) {
        checkStageNotNull();

        final var last = graph.getLast();

        if (last != null) {
            final var entry = getRouterEntryByKeyOrThrowException(last.key());
            final var callerClass = walker.getCallerClass();
            final FXMLLoader fxmlLoader = getFXMLLoader(callerClass, entry, bundle);
            final Scene scene = getLoadedScene(entry, fxmlLoader);

            currentScene = scene;

            applyStageAndScene(fxmlLoader, scene, entry, arg, callerClass);
        }
    }

    private void checkStageNotNull() {
        if (getStage() == null) {
            throw new IllegalStateException(
                "Stage must be assigned! Use ensureStage() or provide stage in configuration"
            );
        }
    }

    @NotNull
    private RouterEntry getRouterEntryByKeyOrThrowException(@NotNull final String key) {
        final var entry = getRouterEntries()
            .stream()
            .filter(routerEntry -> routerEntry.key().equals(key))
            .findFirst()
            .orElse(null);

        if (entry == null) {
            throw new RouteNotDefinedException("Route " + key + " is not defined.");
        }

        return entry;
    }

    @NotNull
    private FXMLLoader getFXMLLoader(
        @NotNull final Class<?> callerClass,
        @NotNull final RouterEntry entry,
        @Nullable final ResourceBundle bundle
    ) {
        final FXMLLoader loader;

        if (bundle != null) {
            loader = new FXMLLoader(callerClass.getResource(entry.layout()), bundle);
        } else if (getCurrentBundle() != null) {
            loader = new FXMLLoader(callerClass.getResource(entry.layout()), getCurrentBundle());
        } else {
            loader = new FXMLLoader(callerClass.getResource(entry.layout()));
        }

        if (getConfiguration() != null && getConfiguration().getControllerFactory() != null) {
            loader.setControllerFactory(getConfiguration().getControllerFactory());
        }

        return loader;
    }

    @NotNull
    private Scene getLoadedScene(@NotNull final RouterEntry entry, @NotNull final FXMLLoader fxmlLoader) {
        try {
            if (entry.size() != null) {
                return new Scene(fxmlLoader.load(), entry.size().width(), entry.size().height());
            } else {
                return new Scene(fxmlLoader.load());
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private RouterController prevController;

    private <T> void applyStageAndScene(
        @NotNull final FXMLLoader fxmlLoader,
        @NotNull final Scene scene,
        @NotNull final RouterEntry entry,
        @Nullable final RouterArg<T> arg,
        @NotNull final Class<?> callerClass
    ) {
        if (getStage() != null) {
            getStage().setScene(scene);

            if (getCurrentStylesheet() != null) {
                final var resource = callerClass.getResource(getCurrentStylesheet());
                if (resource != null) {
                    final var externalForm = resource.toExternalForm();

                    if (scene.getStylesheets().contains(externalForm)) {
                        scene.getStylesheets().removeAll(externalForm);
                    }
                    scene.getStylesheets().addAll(externalForm);
                }
            }

            final var controller = fxmlLoader.getController();
            if (controller instanceof RouterController routerController) {
                if (getCurrentScene() != null) {
                    if (prevController != null) {
                        prevController.onExit(this);
                    }

                    prevController = routerController;
                    setCurrentController(routerController);
                    routerController.onShow(this, arg);
                }
            }

            getStage().setTitle(entry.title());

            if (!getStage().isShowing()) {
                getStage().show();
            }
        }
    }

    private <T> boolean fireOnBackRequestedEventWithArg(@Nullable final RouterArg<T> arg) {
        return fireSpecifiedEventWithArg("", arg, new EventAction() {
            @Override
            public <R> void action(final String key, final RouterArg<R> arg, final RouterEvent requestedEvent) {
                fireOnBackRequested(arg, requestedEvent);
            }
        });
    }

    private <T> void fireOnBackPerformedEventWithArg(@Nullable final RouterArg<T> arg) {
        fireSpecifiedEventWithArg("", arg, new EventAction() {
            @Override
            public <R> void action(final String key, final RouterArg<R> arg, final RouterEvent requestedEvent) {
                fireOnBackPerformed(arg, requestedEvent);
            }
        });
    }

    private <T> boolean fireOnNavigationRequestedEventWithArg(
        @NotNull final String key,
        @Nullable final RouterArg<T> arg
    ) {
        return fireSpecifiedEventWithArg(key, arg, this::fireOnNavigationRequested);
    }

    private <T> void fireOnNavigationPerformedEventWithArg(
        @NotNull final String key,
        @Nullable final RouterArg<T> arg
    ) {
        fireSpecifiedEventWithArg(key, arg, this::fireOnNavigationPerformed);
    }

    private <T> boolean fireSpecifiedEventWithArg(
        @NotNull final String key,
        @Nullable final RouterArg<T> arg,
        @NotNull final EventAction eventAction
    ) {
        final var requestedEvent = new RouterEvent();
        eventAction.action(key, arg, requestedEvent);
        return requestedEvent.isCanceled();
    }

    private <T> void fireOnNavigationRequested(
        @NotNull final String key,
        @Nullable final RouterArg<T> arg,
        @NotNull final RouterEvent event
    ) {
        if (getListener() != null) {
            if (getListener().getOnNavigationRequested() != null) {
                getListener().getOnNavigationRequested().action(
                    key, Objects.requireNonNull(getStage()), arg, event
                );
            }
        }
    }

    private <T> void fireOnNavigationPerformed(
        @NotNull final String key,
        @Nullable final RouterArg<T> arg,
        @NotNull final RouterEvent event
    ) {
        if (getListener() != null) {
            if (getListener().getOnNavigationPerformed() != null) {
                getListener().getOnNavigationPerformed().action(
                    key, Objects.requireNonNull(getStage()), arg, event
                );
            }
        }
    }

    private <T> void fireOnBackRequested(
        @Nullable final RouterArg<T> arg,
        @NotNull final RouterEvent event
    ) {
        if (getListener() != null) {
            if (getListener().getOnBackRequested() != null) {
                getListener().getOnBackRequested().action(
                    (getGraph().getLast() != null) ? getGraph().getLast().key() : null,
                    Objects.requireNonNull(getStage()),
                    arg,
                    event
                );
            }
        }
    }

    private <T> void fireOnBackPerformed(
        @Nullable final RouterArg<T> arg,
        @NotNull final RouterEvent event
    ) {
        if (getListener() != null) {
            if (getListener().getOnBackPerformed() != null) {
                getListener().getOnBackPerformed().action(
                    (getGraph().getLast() != null) ? getGraph().getLast().key() : null,
                    Objects.requireNonNull(getStage()),
                    arg,
                    event
                );
            }
        }
    }

    private void requireIsStageNotInitialized() {
        if (isStageInitialized) throw new IllegalStateException("Router stage already initialized!");
    }
}
