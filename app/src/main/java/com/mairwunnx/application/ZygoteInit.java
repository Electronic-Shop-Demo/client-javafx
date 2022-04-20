package com.mairwunnx.application;

import com.mairwunnx.application.application.EnvironmentVariables;
import com.mairwunnx.application.application.di.GuiceInjector;
import org.jetbrains.annotations.Nullable;

public final class ZygoteInit {
    public static void main(@Nullable final String[] args) {
        if (EnvironmentVariables.isDebugEnabled) {
            System.setProperty("log4j2.configurationFile", "log4j2-debug.xml");
        }

        GuiceInjector.ensureModules(args == null ? new String[]{} : args);
    }
}
