package com.mairwunnx.ui.preferences.internal;

import org.jetbrains.annotations.ApiStatus;

@FunctionalInterface
@ApiStatus.Internal
public interface SupplierConsumer<R, T> {
    R acceptAndReturn(T t);
}
