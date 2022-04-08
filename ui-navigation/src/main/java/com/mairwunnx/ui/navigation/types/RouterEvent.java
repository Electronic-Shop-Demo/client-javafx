package com.mairwunnx.ui.navigation.types;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("unused")
public class RouterEvent {
    @Getter
    private final boolean isCancellationAllowed;

    public RouterEvent() {
        isCancellationAllowed = true;
    }

    public RouterEvent(final boolean cancellationAllowed) {
        isCancellationAllowed = cancellationAllowed;
    }

    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    private boolean isCanceled;

    public void cancel() {
        if (isCancellationAllowed()) {
            throw new IllegalStateException("Event can not be canceled");
        }
        if (isCanceled()) {
            throw new IllegalStateException("Event already canceled");
        }
        setCanceled(true);
    }
}
