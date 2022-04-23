package com.mairwunnx.ui.annotations.types;

/**
 * View api test cover status enumeration class.
 *
 * @since 1.0.0.
 */
public enum ViewTestCoverVariant {
    /**
     * Full test covered view class. (Full means all setters, which do influence on view, for example
     * some setters, {@code setEnabled(false)} must be tested with UI tests (check opacity for example)).
     *
     * @since 1.0.0.
     */
    FULL,

    /**
     * Test in progress for this view.
     *
     * @since 1.0.0.
     */
    IN_DEV,

    /**
     * View has no tests. :(
     *
     * @since 1.0.0.
     */
    NONE
}
