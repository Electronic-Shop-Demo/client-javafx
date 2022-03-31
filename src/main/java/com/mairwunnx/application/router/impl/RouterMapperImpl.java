package com.mairwunnx.application.router.impl;

import com.mairwunnx.application.router.configurators.RouterMapper;
import com.mairwunnx.application.router.types.RouterMappedEntry;
import com.mairwunnx.application.router.types.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Internal
public final class RouterMapperImpl implements RouterMapper {
    @NotNull
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private List<RouterMappedEntry> mapped = new ArrayList<>();

    private String key;
    private String layout;
    private String title;
    private Size size;

    public RouterMapperImpl withInferredKey() {
        this.key = null;
        return this;
    }

    public RouterMapperImpl withLayout(final String layout) {
        this.layout = layout;
        return this;
    }

    public RouterMapperImpl withTitle(final String title) {
        this.title = title;
        return this;
    }

    public RouterMapperImpl withInferredTitle() {
        this.title = null;
        return this;
    }

    public RouterMapperImpl withInitSize(final int width, final int height) {
        this.size = new Size(width, height);
        return this;
    }

    public RouterMapperImpl withKey(final String key) {
        this.key = key;
        return this;
    }

    public RouterMapperImpl apply() {
        mapped.add(new RouterMappedEntry(key, layout, title, size, key == null && title == null));
        key = null;
        layout = null;
        title = null;
        size = null;
        return this;
    }
}
