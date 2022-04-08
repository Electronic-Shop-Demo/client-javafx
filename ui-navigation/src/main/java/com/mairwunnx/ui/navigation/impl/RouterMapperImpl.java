package com.mairwunnx.ui.navigation.impl;

import com.mairwunnx.ui.navigation.configurators.RouterMapper;
import com.mairwunnx.ui.navigation.types.RouterMappedEntry;
import com.mairwunnx.ui.navigation.types.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
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
