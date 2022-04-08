package com.mairwunnx.ui.navigation.impl;

import com.mairwunnx.ui.navigation.configurators.RouterConfiguration;
import com.mairwunnx.ui.navigation.contracts.AutoMapper;
import com.mairwunnx.ui.navigation.types.RouterEntry;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@ApiStatus.Internal
@ApiStatus.Experimental
final class AutomapperDefaultImpl implements AutoMapper {
    private static volatile AutomapperDefaultImpl instance;

    private AutomapperDefaultImpl() {
    }

    public static AutomapperDefaultImpl getInstance() {
        var localInstance = instance;
        if (localInstance == null) {
            synchronized (AutomapperDefaultImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    localInstance = new AutomapperDefaultImpl();
                    instance = localInstance;
                }
            }
        }
        return localInstance;
    }

    @Override
    public void use(
        @NotNull final RouterConfiguration configuration,
        @NotNull final List<RouterEntry> entries,
        @NotNull final Class<?> callerClass
    ) {
        var clazzName = callerClass.getName();
        final var index = clazzName.lastIndexOf(".");
        clazzName = clazzName.substring(index + 1) + ".class";

        final var path = callerClass.getResource(clazzName);

        if (path != null) {
            final var normalPath = path.toString().split("!")[0];

            try (
                final var zipfs =
                    FileSystems.newFileSystem(
                        Paths.get(normalPath.replace("jar:file:///", ""))
                    )
            ) {
                try (final var walkerStream = Files.walk(zipfs.getPath("/"))) {
                    final var mapped = walkerStream
                        .map(Path::toString)
                        .filter(filePath -> {
                            final var names = filePath.split("\\.");
                            final var extension = names[names.length - 1];
                            return extension.equals("fxml");
                        })
                        .map(s -> toRelativeName(s, callerClass))
                        .map(r -> {
                            final var splitted = r.split("/");
                            final var fileName = splitted[splitted.length - 1];
                            return new RouterEntry(
                                keyFromLayout(fileName),
                                fileName,
                                titleFromLayout(fileName),
                                null
                            );
                        }).toList();

                    entries.addAll(mapped);
                }
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @NotNull
    private String toRelativeName(@NotNull final String name, @NotNull final Class<?> aClass) {
        var newname = name.replace(aClass.getPackageName().replace(".", "/"), "");
        newname = newname.substring(2);
        return newname;
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
