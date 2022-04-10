package com.mairwunnx.ui.annotations.types;

/**
 * View api status enumeration class.
 *
 * @since 1.0.0.
 */
public enum ViewApiStatusVariant {
    /**
     * Stable view api status, mean API will do not contain
     * breaking changes and its final variant of api, but can be
     * additions (new methods, etc).
     *
     * @since 1.0.0.
     */
    STABLE,

    /**
     * Unstable view api status, mean API can be changed without
     * backward compatibility, can be removed some methods or
     * renamed.
     *
     * @since 1.0.0.
     */
    UNSTABLE,

    /**
     * In dev view api status, mean API in development, it's like
     * a {@link ViewApiStatusVariant#UNSTABLE} but more correctly in
     * case if view not done for using.
     */
    IN_DEV
}
