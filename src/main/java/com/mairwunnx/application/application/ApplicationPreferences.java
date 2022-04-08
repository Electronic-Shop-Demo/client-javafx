package com.mairwunnx.application.application;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

public enum ApplicationPreferences {
    SIZE_WIDTH("window-width"),
    SIZE_HEIGHT("window-height"),
    WINDOW_X("window-x"),
    WINDOW_Y("window-y"),
    WINDOW_IS_MAXIMIZED("window-is-maximized"),
    LOCALE("locale");

    @NotNull
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private String key;

    ApplicationPreferences(final String key) {
        setKey(key);
    }
}
