package com.mairwunnx.ui.annotations;

import java.lang.annotation.*;

/**
 * Mark any class with this annotation, which contains an issue.
 * <p/>
 * You must pass the id of issue and value with link to issue.
 *
 * @since 1.0.0.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Repeatable(value = Issues.class)
public @interface Issue {
    /**
     * @return id of issue.
     * @since 1.0.0.
     */
    long id();

    /**
     * @return link to issue.
     * @since 1.0.0.
     */
    String value();
}

@Documented
@Target(ElementType.TYPE)
@interface Issues {
    Issue[] value();
}
