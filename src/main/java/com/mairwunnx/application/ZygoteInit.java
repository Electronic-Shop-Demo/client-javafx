package com.mairwunnx.application;

public final class ZygoteInit {
    public static void main(final String[] args) {
        try {
            Application.launch(args);
        } catch (final RuntimeException ex) {

        }
    }
}
