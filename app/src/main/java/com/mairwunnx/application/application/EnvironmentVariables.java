package com.mairwunnx.application.application;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.SystemUtils;

public final class EnvironmentVariables {
    public static final String DEBUG = "com.mairwunnx.application.debug";
    public static final boolean isDebugEnabled = BooleanUtils.toBoolean(SystemUtils.getEnvironmentVariable(DEBUG, "false"));
}
