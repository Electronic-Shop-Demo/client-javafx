package com.mairwunnx.application.application;

import com.mairwunnx.application.application.contracts.JfxView;
import com.mairwunnx.application.application.di.GuiceInjector;
import com.mairwunnx.ui.navigation.Router;
import com.mairwunnx.ui.navigation.RouterFX;
import com.mairwunnx.ui.navigation.contracts.ListeningEvent;
import javafx.util.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Log4j2
public final class ApplicationRouter {
    @Nullable private ListeningEvent onNavigationPerformed;
    @Nullable private BiConsumer<Router, ResourceBundle> onResourceBundleChanged;

    public ApplicationRouter setOnNavigationPerformedInterceptor(final ListeningEvent onNavigationPerformed) {
        this.onNavigationPerformed = onNavigationPerformed;
        return this;
    }

    public ApplicationRouter setOnResourceBundleInterceptor(
        final BiConsumer<Router, ResourceBundle> onResourceBundleChanged
    ) {
        this.onResourceBundleChanged = onResourceBundleChanged;
        return this;
    }

    @NotNull
    public Router buildRouter() {
        return RouterFX.build(router -> router
            .configure(config -> {
                config.autosize(true);
                config.implicitDefaults(true);
                config.controllerFactory(type -> GuiceInjector.getInjector().getInstance(type));
                config.builderFactory(type -> (Builder<JfxView>) () ->
                    (JfxView) GuiceInjector.getInjector().getInstance(type)
                );
            })
            .map(mapper -> {
                for (final Routes value : Routes.values()) {
                    mapper
                        .withKey(value.getKey())
                        .withTitle(value.getTitle())
                        .withLayout(value.getLayout())
                        .apply();
                }
            })
            .listening(listener -> {
                listener.onNavigationPerformed((key, stage, arg, e) -> {
                    log.debug("Navigation performed to {} with arg {}", key, arg);

                    if (onNavigationPerformed != null) {
                        onNavigationPerformed.action(key, stage, arg, e);
                    }
                });

                listener.onBackPerformed((key, stage, arg, e) ->
                    log.debug("Navigation performed to back ({}) with arg {}", key, arg)
                );

                listener.onResourceBundleChanged((lambdaRouterVal, bundle) -> {
                    if (onResourceBundleChanged != null) {
                        onResourceBundleChanged.accept(lambdaRouterVal, bundle);
                    }
                });
            })
            .ensureInitialize());
    }

    public enum Routes {
        HOME("home", "/com/mairwunnx/application/layouts/home/home.fxml");

        @NotNull
        @Getter
        private final String key;
        @NotNull
        @Getter
        private final String layout;

        @NotNull
        @Getter
        private final String title;

        Routes(@NotNull final String key, @NotNull final String layout) {
            this.key = key;
            this.layout = layout;
            this.title =
                Arrays
                    .stream(StringUtils.split(key, "-"))
                    .map(StringUtils::capitalize)
                    .collect(Collectors.joining(" "));
        }

        @NotNull
        @Override
        public String toString() {
            return getKey();
        }
    }
}
