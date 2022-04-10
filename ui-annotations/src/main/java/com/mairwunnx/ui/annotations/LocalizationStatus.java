package com.mairwunnx.ui.annotations;

import com.mairwunnx.ui.annotations.types.LocalizationStatusVariant;

import java.lang.annotation.*;

/**
 * Mark any view class with this annotation with specified
 * localization status variant.
 *
 * @since 1.0.0.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface LocalizationStatus {
    /**
     * @return view localization status variant.
     * @since 1.0.0.
     */
    LocalizationStatusVariant value();
}
