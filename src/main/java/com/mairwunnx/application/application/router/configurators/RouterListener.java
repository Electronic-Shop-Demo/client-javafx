package com.mairwunnx.application.application.router.configurators;

import com.mairwunnx.application.application.router.Router;
import com.mairwunnx.application.application.router.contracts.ListeningEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ResourceBundle;
import java.util.function.BiConsumer;

public interface RouterListener {
    @Nullable ListeningEvent getOnNavigationRequested();

    @Nullable ListeningEvent getOnNavigationPerformed();

    @Nullable ListeningEvent getOnBackRequested();

    @Nullable ListeningEvent getOnBackPerformed();

    @Nullable BiConsumer<Router, ResourceBundle> getOnResourceBundleChanged();
}
