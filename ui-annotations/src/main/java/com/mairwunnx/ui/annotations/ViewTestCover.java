package com.mairwunnx.ui.annotations;

import com.mairwunnx.ui.annotations.types.ViewTestCoverVariant;

import java.lang.annotation.*;

/**
 * Mark any view, which have tests with this annotation.
 *
 * @since 1.0.0.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface ViewTestCover {
    /**
     * @return view test status variant.
     * @since 1.0.0.
     */
    ViewTestCoverVariant value();
}
