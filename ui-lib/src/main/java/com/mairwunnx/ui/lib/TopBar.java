package com.mairwunnx.ui.lib;

import com.google.inject.Inject;
import com.mairwunnx.ui.annotations.Issue;
import com.mairwunnx.ui.annotations.LocalizationStatus;
import com.mairwunnx.ui.annotations.ViewApiStatus;
import com.mairwunnx.ui.annotations.types.LocalizationStatusVariant;
import com.mairwunnx.ui.annotations.types.ViewApiStatusVariant;
import com.mairwunnx.ui.commons.ImageUtils;
import com.mairwunnx.ui.lib.apis.TopBarApi;
import com.mairwunnx.ui.lib.exceptions.ViewInitializationException;
import com.mairwunnx.ui.lib.managers.BadgeManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.controlsfx.control.textfield.CustomTextField;
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
@SuppressWarnings({"unused", "FieldCanBeLocal"})
@Issue(id = 1, value = "https://github.com/Electronic-Shop-Demo/client-javafx/issues/1")
@ViewApiStatus(ViewApiStatusVariant.IN_DEV)
@LocalizationStatus(LocalizationStatusVariant.DONE)
public final class TopBar extends AnchorPane implements JfxView, JfxCompactable, TopBarApi {
    private static final double WIDE_MODE_WIDTH_THRESHOLD = 1_500.0;
    private static final int WIDE_MORE_CONSTRAINT_MULTIPLIER = 6;
    private static final double DEFAULT_MODE_CONSTRAINT = 0.0;
    private static final int AVATAR_RADII = 12;
    private static final int AVATAR_SIZE = 28;

    private static final Insets SIGN_IN_COMPACT_INSETS = new Insets(0, 32, 0, 8);
    private static final Insets SIGN_IN_DEFAULT_INSETS = new Insets(0, 16, 0, 8);

    @FXML private AnchorPane root;
    @FXML private GridPane rootGrid;
    @FXML private Pane topbarBackgroundPane;
    @FXML private Button backButton;
    @FXML private CustomTextField search;
    @FXML private Button favorite;
    @FXML private StackPane favoriteBadge;
    @FXML private Label favoriteBadgeText;
    @FXML private Button cart;
    @FXML private StackPane cartBadge;
    @FXML private Label cartBadgeText;
    @FXML private Button locationButton;
    @FXML private Button signin;
    @FXML private ImageView profile;
    private final ImageView searchImageButton = new ImageView();

    private ChangeListener<Number> imageProgressListener;
    private ChangeListener<String> searchTextListener;
    private ChangeListener<Number> wideScreenListener;
    private ChangeListener<Number> smallScreenListener;

    @Getter(value = AccessLevel.PRIVATE, onMethod_ = {@Nullable})
    @Setter(onParam_ = {@NotNull})
    private Runnable onBackRequested;

    @Getter(value = AccessLevel.PRIVATE, onMethod_ = {@Nullable})
    @Setter(onParam_ = {@NotNull})
    private Supplier<String> onLocationDialogRequested;

    @Getter(value = AccessLevel.PRIVATE, onMethod_ = {@Nullable})
    @Setter(onParam_ = {@NotNull})
    private Consumer<String> onSearchChanged;

    @Getter(value = AccessLevel.PRIVATE, onMethod_ = {@Nullable})
    @Setter(onParam_ = {@NotNull})
    private Consumer<String> onSearchRequested;

    @Getter(value = AccessLevel.PRIVATE, onMethod_ = {@Nullable})
    @Setter(onParam_ = {@NotNull})
    private Runnable onFavoriteClicked;

    @Getter(value = AccessLevel.PRIVATE, onMethod_ = {@Nullable})
    @Setter(onParam_ = {@NotNull})
    private Runnable onCartClicked;

    @Getter(value = AccessLevel.PRIVATE, onMethod_ = {@Nullable})
    @Setter(onParam_ = {@NotNull})
    private Runnable onLoginOrRegisterClicked;

    @Getter(value = AccessLevel.PRIVATE, onMethod_ = {@Nullable})
    @Setter(onParam_ = {@NotNull})
    private Runnable onProfileClicked;

    @Getter
    private boolean backAvailable;

    public void setBackAvailable(final boolean value) {
        backAvailable = value;
        backButton.setDisable(!value);
    }

    @Override
    public void setLocation(@NotNull final String value) {
        locationButton.setText(value);
    }

    @Override
    public void setSearchText(@NotNull final String value) {
        search.setText(value);
    }

    @Override
    public void setFavoriteCount(final int value) {
        badgeManager.use(favoriteBadge, favoriteBadgeText).withCount(value).apply();
    }

    @Override
    public void setCartCount(final int value) {
        badgeManager.use(cartBadge, cartBadgeText).withCount(value).apply();
    }

    @Override
    public void setIsLoggedIn(final boolean isLoggedIn) {
        signin.setVisible(!isLoggedIn);
        signin.setManaged(!isLoggedIn);
        profile.setManaged(isLoggedIn);
        profile.setVisible(isLoggedIn);

        if (isLoggedIn) ImageUtils.clip(profile, AVATAR_RADII, AVATAR_SIZE);
    }

    @Override
    public void setAvatarImage(final @NotNull String ref) {
        final var image = new Image(ref, true);

        imageProgressListener = (observable, oldValue, newValue) -> {
            if (newValue.doubleValue() == 1.0) {
                profile.setImage(image);
                ImageUtils.clip(profile, AVATAR_RADII, AVATAR_SIZE);
            }
        };

        image.progressProperty().addListener(new WeakChangeListener<>(imageProgressListener));
    }

    @NotNull
    public TopBarApi getApi() {
        return this;
    }

    @Getter private boolean isCompactModeEnabled;
    @Getter private final HashMap<Node, Object> compactSavedParams = new HashMap<>();

    private String compactSignInText;
    private double minCompactSize;

    @Inject private ResourceBundle bundle;
    @Inject private BadgeManager badgeManager;

    @NotNull
    @Override
    public TopBar onViewCreate() {
        extractBundleValues();
        expose();
        return this;
    }

    @Override
    public void onViewCreated() {
        initializeSearchButton();
        updateWidthConstraints(root.getWidth());
        setCompactModeEnabled(root.getWidth() <= minCompactSize);
        subscribeOnRootWidthChanged();
        subscribeOnBackButtonClicked();
        subscribeOnSearchTextChanged();
        subscribeOnFavoriteClicked();
        subscribeOnCartClicked();
        subscribeOnLoginOrRegisterRequested();
        subscribeOnProfileClicked();
    }

    @Override
    public @NotNull String layoutPath() {
        return "/uilib/topbar.fxml";
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

    private void extractBundleValues() {
        compactSignInText = bundle.getString("compactButtonSignInText");
        minCompactSize = Double.parseDouble(
            bundle.getString("layoutTopbarMinCompactSize")
        );
    }

    private void initializeSearchButton() {
        final var resource = getClass().getResource("/uilib/assets/round_search_black_18dp.jpg");
        final String path;

        try {
            path = resource != null ? resource.toURI().toString() : null;
        } catch (final URISyntaxException e) {
            throw new ViewInitializationException("An error occurred while resource translating to URI", e);
        }

        if (path != null) {
            searchImageButton.setImage(new Image(path));
            searchImageButton.setCursor(Cursor.HAND);
            searchImageButton.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    if (getOnSearchRequested() != null) {
                        getOnSearchRequested().accept(search.getText());
                    }
                }
            });
            search.setRight(searchImageButton);
        } else {
            throw new ViewInitializationException("Search image was not loaded correctly, path == null");
        }
    }

    private void subscribeOnRootWidthChanged() {
        wideScreenListener = (o, oldValue, newValue) -> handleWideScreen(oldValue, newValue);
        smallScreenListener = (o, oldValue, newValue) -> handleSmallScreen(oldValue, newValue);

        root.widthProperty().addListener(new WeakChangeListener<>(wideScreenListener));
        root.widthProperty().addListener(new WeakChangeListener<>(smallScreenListener));
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

    private void subscribeOnLocationButtonClicked() {
        setOnUserInteract(locationButton, button -> {
            if (getOnLocationDialogRequested() != null) {
                locationButton.setText(getOnLocationDialogRequested().get());
            }
        });
    }

    private void subscribeOnSearchTextChanged() {
        searchTextListener = (observable, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                if (getOnSearchChanged() != null) {
                    getOnSearchChanged().accept(newValue);
                }
            }
        };

        search.textProperty().addListener(new WeakChangeListener<>(searchTextListener));
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

    private void subscribeOnProfileClicked() {
        setOnUserInteract(profile, imageView -> {
            if (getOnProfileClicked() != null) {
                getOnProfileClicked().run();
            }
        });
    }
}
