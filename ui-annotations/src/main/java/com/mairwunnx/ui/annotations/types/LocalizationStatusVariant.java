package com.mairwunnx.ui.annotations.types;

/**
 * Localization status enumeration class.
 *
 * @since 1.0.0.
 */
public enum LocalizationStatusVariant {
    /**
     * Done variant means localization done for all languages.
     *
     * @since 1.0.0.
     */
    DONE,

    /**
     * Incomplete variant means localization incomplete for some
     * languages or some strings not moved to bundle now.
     *
     * @since 1.0.0.
     */
    INCOMPLETE
}
