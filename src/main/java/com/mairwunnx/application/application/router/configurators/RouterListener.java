package com.mairwunnx.application.application.router.configurators;

import com.mairwunnx.application.application.router.contracts.ListeningEvent;
import org.jetbrains.annotations.Nullable;

public interface RouterListener {
    @Nullable ListeningEvent getOnNavigationRequested();

    @Nullable ListeningEvent getOnNavigationPerformed();

    @Nullable ListeningEvent getOnBackRequested();

    @Nullable ListeningEvent getOnBackPerformed();
}
