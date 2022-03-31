package com.mairwunnx.application.router.impl;

import com.mairwunnx.application.router.configurators.RouterConfiguration;
import com.mairwunnx.application.router.configurators.RouterMapper;
import com.mairwunnx.application.router.contracts.Mapper;
import com.mairwunnx.application.router.types.RouterEntry;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@ApiStatus.Internal
final class MapperDefaultImpl implements Mapper {
    private static volatile MapperDefaultImpl instance;

    public static MapperDefaultImpl getInstance() {
        var localInstance = instance;
        if (localInstance == null) {
            synchronized (MapperDefaultImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    localInstance = new MapperDefaultImpl();
                    instance = localInstance;
                }
            }
        }
        return localInstance;
    }

    @Override
    public void use(
        @NotNull final RouterConfiguration configuration,
        @NotNull final RouterMapper mapper,
        @NotNull final List<RouterEntry> entries,
        @NotNull final Class<?> callerClass
    ) {
        final var mapped = mapper.getMapped().parallelStream().map(entry -> {
            if (entry.implicitDefaults()) {
                if (!configuration.isImplicitDefaults()) {
                    throw new IllegalStateException("Using implicit defaults without implicitDefaults enabled");
                }
            }

            if (entry.size() == null) {
                if (!configuration.isAutosize()) {
                    throw new IllegalStateException("Using autosize without autosize enabled");
                }
            }

            return new RouterEntry(
                entry.key() == null ? keyFromLayout(entry.layout()) : entry.key(),
                entry.layout(),
                entry.title() == null ? titleFromLayout(entry.layout()) : entry.title(),
                entry.size()
            );
        }).toList();

        System.out.println("mapped = " + mapped);
        entries.addAll(mapped);
    }

    @NotNull
    private String keyFromLayout(@NotNull final String layout) {
        return layout.substring(0, layout.length() - 5);
    }

    @NotNull
    private String titleFromLayout(@NotNull final String layout) {
        var isFirstCapitalized = false;
        var isSpaceInserted = false;
        final StringBuilder title = new StringBuilder();

        for (final char c : layout.toCharArray()) {
            if (!isFirstCapitalized) {
                if (Character.isLowerCase(c)) {
                    title.append(Character.toUpperCase(c));
                }
                isFirstCapitalized = true;
                continue;
            }

            if (c == '.') break;

            if (c == '-' || c == '_') {
                title.append(" ");
                isSpaceInserted = true;
                continue;
            }

            if (Character.isUpperCase(c)) title.append(" ");

            if (isSpaceInserted) {
                title.append(Character.toUpperCase(c));
                isSpaceInserted = false;
            } else {
                title.append(c);
            }
        }

        return title.toString();
    }
}
