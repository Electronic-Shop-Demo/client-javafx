package com.mairwunnx.application.application.contracts;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Repeatable(value = Issues.class)
public @interface Issue {
    long id();

    String value();
}

@Documented
@Target(ElementType.TYPE)
@interface Issues {
    Issue[] value();
}
