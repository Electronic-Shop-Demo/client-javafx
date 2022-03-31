package com.mairwunnx.application.router.impl;

import com.mairwunnx.application.router.builders.RouterConfigurationBuilder;
import com.mairwunnx.application.router.builders.RouterImplementationBuilder;
import com.mairwunnx.application.router.builders.RouterListenerBuilder;
import com.mairwunnx.application.router.builders.RouterMapperBuilder;
import com.mairwunnx.application.router.configurators.RouterConfiguration;
import com.mairwunnx.application.router.configurators.RouterImplementation;
import com.mairwunnx.application.router.configurators.RouterListener;
import com.mairwunnx.application.router.configurators.RouterMapper;
import com.mairwunnx.application.router.types.RouterEntry;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Internal
public final class RouterBuilderImpl {
    @Nullable
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private RouterImplementation implementation = null;

    @Nullable
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private RouterConfiguration configuration = null;

    @Nullable
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private RouterMapper mapper = null;

    @Nullable
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private RouterListener listener = null;

    @Nullable
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private Stage stage = null;

    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private boolean isInitialized;

    @NotNull
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private List<RouterEntry> routerEntries = new ArrayList<>();

    private final StackWalker walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    public RouterBuilderImpl implementation(final @NotNull RouterImplementationBuilder implementation) {
        requireIsNotInitialized();
        final var routerImplValue = new RouterImplementationImpl();
        setImplementation(routerImplValue);
        implementation.apply(routerImplValue);
        return this;
    }

    public RouterBuilderImpl configure(final @NotNull RouterConfigurationBuilder configuration) {
        requireIsNotInitialized();
        final var configurationImplValue = new RouterConfigurationImpl();
        setConfiguration(configurationImplValue);
        configuration.apply(configurationImplValue);
        configurationImplValue.ensureInitialize();
        return this;
    }

    public RouterBuilderImpl map(final @NotNull RouterMapperBuilder mapper) {
        requireIsNotInitialized();
        final var mapperImplValue = new RouterMapperImpl();
        setMapper(mapperImplValue);
        mapper.apply(mapperImplValue);
        return this;
    }

    public RouterBuilderImpl listening(final @NotNull RouterListenerBuilder listener) {
        requireIsNotInitialized();
        final var listenerImplValue = new RouterListenerImpl();
        setListener(listenerImplValue);
        listener.apply(listenerImplValue);
        return this;
    }

    public RouterBuilderImpl withStage(final @NotNull Stage stage) {
        requireIsNotInitialized();
        setStage(stage);
        return this;
    }

    public void ensureInitialize() {
        if (isInitialized) {
            throw new IllegalStateException("Router builder already initialized");
        }

        if (getConfiguration() == null) {
            throw new IllegalStateException(
                "Router must be configured, use `configure(config -> config)` to pass configure"
            );
        }

        isInitialized = true;

        final var callerClass = walker.getCallerClass();

        if (getMapper() == null) {
            if (!getConfiguration().isAutomapping()) {
                throw new IllegalStateException("Using automapping without automapping enabled");
            }

            if (getImplementation() != null) {
                if (getImplementation().getAutomapper() != null) {
                    getImplementation().getAutomapper().use(getConfiguration(), routerEntries, callerClass);
                } else {
                    AutomapperDefaultImpl.getInstance().use(getConfiguration(), routerEntries, callerClass);
                }
            } else {
                AutomapperDefaultImpl.getInstance().use(getConfiguration(), routerEntries, callerClass);
            }
        } else {
            if (getImplementation() != null) {
                if (getImplementation().getMapper() != null) {
                    getImplementation().getMapper().use(getConfiguration(), getMapper(), routerEntries, callerClass);
                } else {
                    MapperDefaultImpl.getInstance().use(getConfiguration(), getMapper(), routerEntries, callerClass);
                }
            } else {
                MapperDefaultImpl.getInstance().use(getConfiguration(), getMapper(), routerEntries, callerClass);
            }
        }
    }

    private void requireIsNotInitialized() {
        if (isInitialized) throw new IllegalStateException("Router builder already initialized!");
    }
}
