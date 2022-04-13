package com.mairwunnx.application;

import com.google.inject.Inject;
import com.mairwunnx.application.application.Application;
import com.mairwunnx.ui.di.qualifiers.StartupArgs;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.SystemUtils;
import org.jetbrains.annotations.NotNull;

@Log4j2
public final class PostInit {
    @Inject
    @StartupArgs
    @NotNull
    private String[] args;

    public void init() {
        try {
            log.info(
                "Starting application on the Java Virtual Machine {} {}, on the OS {} {} {}",
                SystemUtils.JAVA_VERSION,
                SystemUtils.JAVA_VENDOR,
                SystemUtils.OS_NAME,
                SystemUtils.OS_ARCH,
                SystemUtils.OS_VERSION
            );
            Application.launch(Application.class, args);
        } catch (final RuntimeException ex) {
            log.error("An error occurred while application running", ex);
        }
    }
}
