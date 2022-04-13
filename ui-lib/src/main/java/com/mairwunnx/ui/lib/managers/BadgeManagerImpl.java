package com.mairwunnx.ui.lib.managers;

import com.google.inject.Inject;
import com.mairwunnx.ui.di.qualifiers.CompactNumberFormatter;
import javafx.scene.Node;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.NumberFormat;
import java.util.ResourceBundle;

public final class BadgeManagerImpl implements BadgeManager {
    @Inject private ResourceBundle resourceBundle;

    @Inject
    @CompactNumberFormatter
    private NumberFormat formatter;

    @Nullable private Node badge;
    @Nullable private Label badgeText;
    private int count;

    @Override
    public @NotNull BadgeManager use(@NotNull final Node badge, @NotNull final Label badgeText) {
        this.badge = badge;
        this.badgeText = badgeText;
        return this;
    }

    @Override
    public @NotNull BadgeManager withCount(final int count) {
        this.count = count;
        return this;
    }

    @Override
    public void apply() {
        if (badge != null && badgeText != null) {
            final var displayBadge = count > 0;
            if (displayBadge) badgeText.setText(format(count));
            badge.setVisible(displayBadge);
            badge.setManaged(displayBadge);
        }

        badge = null;
        badgeText = null;
        count = -1;
    }

    @NotNull
    private String format(final int value) {
        return value > 99 && value < 1000 ? resourceBundle.getString("overOneHundred") : formatter.format(value);
    }
}
