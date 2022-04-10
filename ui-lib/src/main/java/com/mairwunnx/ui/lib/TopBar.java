package com.mairwunnx.ui.lib;

import com.google.inject.Inject;
import com.mairwunnx.ui.annotations.Issue;
import com.mairwunnx.ui.annotations.LocalizationStatus;
import com.mairwunnx.ui.annotations.ViewApiStatus;
import com.mairwunnx.ui.annotations.types.LocalizationStatusVariant;
import com.mairwunnx.ui.annotations.types.ViewApiStatusVariant;
import com.mairwunnx.ui.lib.exceptions.ViewInitializationException;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.controlsfx.control.textfield.CustomTextField;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.mairwunnx.ui.commons.CompactUtils.switchToCompact;
import static com.mairwunnx.ui.commons.InteractionUtils.setOnUserInteract;

/**
 * Top bar layout implementation class.
 *
 * @see JfxView
 * @see JfxCompactable
 * @since 1.0.0
 */
@Issue(id = 1, value = "https://github.com/Electronic-Shop-Demo/client-javafx/issues/1")
@ViewApiStatus(ViewApiStatusVariant.IN_DEV)
@LocalizationStatus(LocalizationStatusVariant.DONE)
public final class TopBar extends AnchorPane implements JfxView, JfxCompactable {
    private static final double WIDE_MODE_WIDTH_THRESHOLD = 1_500.0;
    private static final int WIDE_MORE_CONSTRAINT_MULTIPLIER = 6;
    private static final double DEFAULT_MODE_CONSTRAINT = 0.0;

    private static final Insets SIGN_IN_COMPACT_INSETS = new Insets(0, 32, 0, 8);
    private static final Insets SIGN_IN_DEFAULT_INSETS = new Insets(0, 16, 0, 8);

    @FXML private AnchorPane root;
    @FXML private GridPane rootGrid;
    @FXML private Pane topbarBackgroundPane;
    @FXML private Button backButton;
    @FXML private CustomTextField search;
    @FXML private Button favorite;
    @FXML private Button cart;
    @FXML private Button locationButton;
    @FXML private Button signin;

    /**
     * On back requested callback. Calls when back arrow button is clicked
     * with mouse or space or enter while focused.
     */
    @Getter(value = AccessLevel.PRIVATE, onMethod_ = {@Nullable})
    @Setter(onParam_ = {@NotNull})
    private Runnable onBackRequested;

    /**
     * On location requested dialog callback. Calls when location button is clicked
     * with mouse or space or enter while focused.
     * <p>
     * By idea must display in-app fullscreen dialog with selecting city if city was
     * detected not correctly.
     */
    @Getter(value = AccessLevel.PRIVATE, onMethod_ = {@Nullable})
    @Setter(onParam_ = {@NotNull})
    private Supplier<String> onLocationDialogRequested;

    /**
     * On search changed callback. Calls when text in search text field
     * is changed.
     * <p>
     * By idea for every typed character must be shown in-app fullscreen dialog
     * with selecting preview product.
     */
    @Getter(value = AccessLevel.PRIVATE, onMethod_ = {@Nullable})
    @Setter(onParam_ = {@NotNull})
    private Consumer<String> onSearchChanged;

    /**
     * On search requested callback. Calls when search requested by user, by
     * clicking on search icon or enter while focused in text field.
     * <p>
     * By idea must navigate to other page with displaying that thing user
     * has requested.
     */
    @Getter(value = AccessLevel.PRIVATE, onMethod_ = {@Nullable})
    @Setter(onParam_ = {@NotNull})
    private Consumer<String> onSearchRequested;

    /**
     * On favorite requested callback. Calls when user clicked on
     * favorite button.
     * <p>
     * By idea must display new page with favorite list.
     */
    @Getter(value = AccessLevel.PRIVATE, onMethod_ = {@Nullable})
    @Setter(onParam_ = {@NotNull})
    private Runnable onFavoriteClicked;

    /**
     * On cart requested callback. Calls when user clicked on cart button.
     * <p>
     * By idea must display new page with cart.
     */
    @Getter(value = AccessLevel.PRIVATE, onMethod_ = {@Nullable})
    @Setter(onParam_ = {@NotNull})
    private Runnable onCartClicked;

    /**
     * On login requested callback. Calls when user clicked on login or
     * register button.
     * <p>
     * By idea must display new page with login\register page.
     */
    @Getter(value = AccessLevel.PRIVATE, onMethod_ = {@Nullable})
    @Setter(onParam_ = {@NotNull})
    private Runnable onLoginOrRegisterClicked;

    @Getter
    @ApiStatus.Internal
    private boolean isCompactModeEnabled;

    @Getter
    @ApiStatus.Internal
    private final HashMap<Node, Object> compactSavedParams = new HashMap<>();

    private String compactSignInText;
    private double minCompactSize;

    private final ImageView searchImageButton = new ImageView();

    @Inject
    private ResourceBundle bundle;

    @Override
    public @NotNull String layoutPath() {
        return "/topbar.fxml";
    }

    @Override
    public @NotNull ResourceBundle resourceBundle() {
        return bundle;
    }

    @Override
    public void setCompactModeEnabled(final boolean isEnabled) {
        isCompactModeEnabled = isEnabled;

        if (isEnabled) {
            getCompactSavedParams().put(cart, cart.getText());
            getCompactSavedParams().put(favorite, favorite.getText());
            getCompactSavedParams().put(signin, signin.getText());
            getCompactSavedParams().put(locationButton, locationButton.getText());
        }

        cart.setText(isEnabled ? null : (String) getCompactSavedParams().get(cart));
        favorite.setText(isEnabled ? null : (String) getCompactSavedParams().get(favorite));
        locationButton.setText(isEnabled ? null : (String) getCompactSavedParams().get(locationButton));
        signin.setText(isEnabled ? compactSignInText : (String) getCompactSavedParams().get(signin));
        GridPane.setMargin(signin, isEnabled ? SIGN_IN_COMPACT_INSETS : SIGN_IN_DEFAULT_INSETS);

        switchToCompact(isEnabled, cart, favorite, locationButton);
        if (!isEnabled) getCompactSavedParams().clear();
    }

    @NotNull
    @Override
    public TopBar init() {
        extractBundleValues();
        expose();
        installSearchButtonToSearchField();
        updateWidthConstraints(root.getWidth());
        setCompactModeEnabled(root.getWidth() <= minCompactSize);
        subscribeOnRootWidthChanged();
        subscribeOnBackButtonClicked();
        subscribeOnSearchTextChanged();
        subscribeOnFavoriteClicked();
        subscribeOnCartClicked();
        subscribeOnLoginOrRegisterRequested();
        return this;
    }

    private void extractBundleValues() {
        compactSignInText = bundle.getString("compactButtonSignInText");
        minCompactSize = Double.parseDouble(
            bundle.getString("layoutTopbarMinCompactSize")
        );
    }

    private void installSearchButtonToSearchField() {
        final var resource =
            getClass().getResource("/assets/round_search_black_18dp.jpg");

        String path;
        try {
            path = resource != null ? resource.toURI().toString() : null;
        } catch (final URISyntaxException e) {
            path = null;
            throw new ViewInitializationException("An error occurred while resource translating to URI", e);
        }

        if (path != null) {
            searchImageButton.setImage(new Image(path));
            searchImageButton.setCursor(Cursor.HAND);
            searchImageButton.setOnMouseClicked(event -> {
                // todo: call on search requested
            });
            search.setRight(searchImageButton);
        } else {
            throw new ViewInitializationException("Search image was not loaded correctly, path == null");
        }
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
                    setCompactModeEnabled(true);
                }
            } else {
                if (isCompactModeEnabled()) {
                    setCompactModeEnabled(false);
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