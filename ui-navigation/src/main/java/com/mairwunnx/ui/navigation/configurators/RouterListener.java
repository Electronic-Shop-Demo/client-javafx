package com.mairwunnx.ui.navigation.configurators;

import com.mairwunnx.ui.navigation.Router;
import com.mairwunnx.ui.navigation.contracts.ListeningEvent;
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
