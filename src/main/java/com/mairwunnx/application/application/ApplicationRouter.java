package com.mairwunnx.application.application;

import com.mairwunnx.application.application.di.GuiceInjector;
import com.mairwunnx.application.application.router.Router;
import com.mairwunnx.application.application.router.RouterFX;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

@Log4j2
public final class ApplicationRouter {
    public Router buildRouter() {
        return RouterFX.build(router -> {
            router
                .configure(config -> {
                    config.autosize(true);
                    config.implicitDefaults(true);
                    config.controllerFactory(type -> GuiceInjector.getInjector().getInstance(type));
                })
                .map(mapper -> {
                    for (final Routes value : Routes.values()) {
                        mapper.withLayout(value.getLayout()).apply();
                    }
                })
                .listening(listener -> {
                    listener.onNavigationPerformed((key, stage, arg, e) -> {
                        log.info("Navigation performed to {} with arg {}", key, arg);
                    });

                    listener.onBackPerformed((key, stage, arg, e) -> {
                        log.info("Navigation performed to back ({}) with arg {}", key, arg);
                    });
                })
                .ensureInitialize();
        });
    }

    public enum Routes {
        MAIN("hello-view.fxml"),
        TEST("hello-test.fxml");

        @NotNull
        @Getter
        private final String key;
        @NotNull
        @Getter
        private final String layout;

        Routes(@NotNull final String layout) {
            this.key = StringUtils.removeEnd(layout, ".fxml");
            this.layout = layout;
        }

        @NotNull
        @Override
        public String toString() {
            return getKey();
        }
    }
}
