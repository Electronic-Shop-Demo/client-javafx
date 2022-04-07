package com.mairwunnx.application.application.views;

import com.mairwunnx.application.application.Application;
import com.mairwunnx.application.application.contracts.JfxCompactable;
import com.mairwunnx.application.application.contracts.JfxView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.function.Consumer;

import static com.mairwunnx.application.application.compact.CompactUtils.switchToCompact;
import static com.mairwunnx.application.application.interaction.InteractionUtils.setOnUserInteract;

public final class TopBar extends AnchorPane implements JfxView, JfxCompactable {
    public static final double WIDE_MODE_WIDTH_THRESHOLD = 1_500.0;
    public static final int WIDE_MORE_CONSTRAINT_MULTIPLIER = 6;
    public static final double DEFAULT_MODE_CONSTRAINT = 0.0;

    @FXML private AnchorPane root;
    @FXML private GridPane rootGrid;
    @FXML private Pane topbarBackgroundPane;
    @FXML private Button backButton;
    @FXML private TextField search;
    @FXML private Button favorite;
    @FXML private Button cart;
    @FXML private Button locationButton;
    @FXML private Button signin;

    @Getter(value = AccessLevel.PRIVATE, onMethod_ = {@Nullable})
    @Setter(onParam_ = {@NotNull})
    private Runnable onBackRequested;

    @Getter(value = AccessLevel.PRIVATE, onMethod_ = {@Nullable})
    @Setter(onParam_ = {@NotNull})
    private Consumer<String> onSearchChanged;

    @Getter(value = AccessLevel.PRIVATE, onMethod_ = {@Nullable})
    @Setter(onParam_ = {@NotNull})
    private Runnable onFavoriteClicked;

    @Getter(value = AccessLevel.PRIVATE, onMethod_ = {@Nullable})
    @Setter(onParam_ = {@NotNull})
    private Runnable onCartClicked;

    @Getter(value = AccessLevel.PRIVATE, onMethod_ = {@Nullable})
    @Setter(onParam_ = {@NotNull})
    private Runnable onLoginOrRegisterClicked;

    @Getter
    private boolean isCompactModeEnabled;

    @Getter
    private final HashMap<Node, Object> compactSavedParams = new HashMap<>();

    private String compactSignInText;
    private double minCompactSize;

    public TopBar() {
        initialize();
    }

    @Override
    public @NotNull String layoutPath() {
        return "/com/mairwunnx/application/layouts/shared/topbar.fxml";
    }

    @Override
    public void setCompactMode(final boolean isEnabled) {
        isCompactModeEnabled = isEnabled;
        if (isEnabled) {
            getCompactSavedParams().put(cart, cart.getText());
            getCompactSavedParams().put(favorite, favorite.getText());
            getCompactSavedParams().put(signin, signin.getText());
            getCompactSavedParams().put(locationButton, locationButton.getText());
            cart.setText(null);
            favorite.setText(null);
            locationButton.setText(null);
            signin.setText(compactSignInText);
        } else {
            cart.setText((String) getCompactSavedParams().get(cart));
            favorite.setText((String) getCompactSavedParams().get(favorite));
            signin.setText((String) getCompactSavedParams().get(signin));
            locationButton.setText((String) getCompactSavedParams().get(locationButton));
            getCompactSavedParams().clear();
        }

        switchToCompact(isEnabled, cart, favorite, locationButton);
    }

    private void initialize() {
        extractBundleValues();
        expose();
        updateWidthConstraints(root.getWidth());
        setCompactMode(root.getWidth() <= minCompactSize);
        subscribeOnRootWidthChanged();
        subscribeOnBackButtonClicked();
        subscribeOnSearchTextChanged();
        subscribeOnFavoriteClicked();
        subscribeOnCartClicked();
        subscribeOnLoginOrRegisterRequested();
    }

    private void extractBundleValues() {
        compactSignInText = Application.getCurrentResourceBundle().getString("compactButtonSignInText");
        minCompactSize = Double.parseDouble(
            Application.getCurrentResourceBundle().getString("layoutTopbarMinCompactSize")
        );
    }

    private void subscribeOnRootWidthChanged() {
        root.widthProperty().addListener((o, oldValue, newValue) -> handleWideScreen(oldValue, newValue));
        root.widthProperty().addListener((o, oldValue, newValue) -> handleSmallScreen(oldValue, newValue));
    }

    private void handleWideScreen(@NotNull final Number oldValue, @NotNull final Number newValue) {
        final var oldDoubleValue = oldValue.doubleValue();
        final var newDoubleValue = newValue.doubleValue();

        if (oldDoubleValue != newDoubleValue) {
            if (newDoubleValue > WIDE_MODE_WIDTH_THRESHOLD) {
                updateWidthConstraints(newDoubleValue);
            } else {
                updateWidthConstraints(DEFAULT_MODE_CONSTRAINT);
            }
        }
    }

    private void handleSmallScreen(@NotNull final Number oldValue, @NotNull final Number newValue) {
        final var oldDoubleValue = oldValue.doubleValue();
        final var newDoubleValue = newValue.doubleValue();

        if (oldDoubleValue != newDoubleValue) {
            if (minCompactSize > newDoubleValue) {
                if (!isCompactModeEnabled()) {
                    setCompactMode(true);
                }
            } else {
                if (isCompactModeEnabled()) {
                    setCompactMode(false);
                }
            }
        }
    }

    private void updateWidthConstraints(final double width) {
        if (width > WIDE_MODE_WIDTH_THRESHOLD) {
            if (!topbarBackgroundPane.isVisible()) {
                topbarBackgroundPane.setManaged(true);
                topbarBackgroundPane.setVisible(true);
            }
        } else {
            if (topbarBackgroundPane.isVisible()) {
                topbarBackgroundPane.setManaged(false);
                topbarBackgroundPane.setVisible(false);
            }
        }

        if (width == DEFAULT_MODE_CONSTRAINT) {
            AnchorPane.setLeftAnchor(rootGrid, DEFAULT_MODE_CONSTRAINT);
            AnchorPane.setRightAnchor(rootGrid, DEFAULT_MODE_CONSTRAINT);
        } else {
            final var calculatedMargins = width / WIDE_MORE_CONSTRAINT_MULTIPLIER;
            AnchorPane.setLeftAnchor(rootGrid, calculatedMargins);
            AnchorPane.setRightAnchor(rootGrid, calculatedMargins);
        }
    }

    private void subscribeOnBackButtonClicked() {
        setOnUserInteract(backButton, button -> {
            if (getOnBackRequested() != null) {
                getOnBackRequested().run();
            }
        });
    }

    private void subscribeOnSearchTextChanged() {
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                if (getOnSearchChanged() != null) {
                    getOnSearchChanged().accept(newValue);
                }
            }
        });
    }

    private void subscribeOnFavoriteClicked() {
        setOnUserInteract(favorite, button -> {
            if (getOnFavoriteClicked() != null) {
                getOnFavoriteClicked().run();
            }
        });
    }

    private void subscribeOnCartClicked() {
        setOnUserInteract(cart, button -> {
            if (getOnCartClicked() != null) {
                getOnCartClicked().run();
            }
        });
    }

    private void subscribeOnLoginOrRegisterRequested() {
        setOnUserInteract(signin, button -> {
            if (getOnLoginOrRegisterClicked() != null) {
                getOnLoginOrRegisterClicked().run();
            }
        });
    }
}
