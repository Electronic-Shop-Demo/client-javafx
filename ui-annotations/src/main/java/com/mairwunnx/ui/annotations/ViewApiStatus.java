package com.mairwunnx.ui.annotations;

import com.mairwunnx.ui.annotations.types.ViewApiStatusVariant;

import java.lang.annotation.*;

/**
 * Mark any view class with this annotation with specified
 * api status variant.
 *
 * @since 1.0.0.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface ViewApiStatus {
    /**
     * @return view api status variant.
     * @since 1.0.0.
     */
    ViewApiStatusVariant value();
}
