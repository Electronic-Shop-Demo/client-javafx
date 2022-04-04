package com.mairwunnx.application;

import com.mairwunnx.application.application.di.GuiceInjector;
import org.jetbrains.annotations.Nullable;

public final class ZygoteInit {
    public static void main(@Nullable final String[] args) {
        GuiceInjector.ensureModules(args == null ? new String[]{} : args);
    }
}
