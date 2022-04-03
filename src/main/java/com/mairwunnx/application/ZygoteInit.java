package com.mairwunnx.application;

import com.mairwunnx.application.application.di.GuiceInjector;

public final class ZygoteInit {
    public static void main(final String[] args) {
        GuiceInjector.ensureModules(args);
    }
}
