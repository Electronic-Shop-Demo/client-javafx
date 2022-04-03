package com.mairwunnx.application;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.SystemUtils;

@Log4j2
public final class ZygoteInit {
    public static void main(final String[] args) {
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
