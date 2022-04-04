package com.mairwunnx.application.application.views;

import com.mairwunnx.application.application.contracts.JfxView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public final class TopBar extends AnchorPane implements JfxView {
    public static final double WIDE_MODE_WIDTH_THRESHOLD = 1_500.0;
    public static final int WIDE_MORE_CONSTRAINT_MULTIPLIER = 6;
    public static final double DEFAULT_MODE_CONSTRAINT = 0.0;

    @FXML private AnchorPane root;
    @FXML private GridPane grid;
    @FXML private Pane pane;
    @FXML private Button backButton;
    @FXML private TextField search;
    @FXML private Button favorite;
    @FXML private Button cart;
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

    @Override
    public @NotNull String layoutPath() {
        return "/com/mairwunnx/application/layouts/shared/topbar.fxml";
    }

    public TopBar() {
        initialize();
    }

    private void initialize() {
        expose();
        updateWidthConstraints(root.getWidth());
        subscribeOnRootWidthChanged();
        subscribeOnBackButtonClicked();
        subscribeOnSearchTextChanged();
        subscribeOnFavoriteClicked();
        subscribeOnCartClicked();
        subscribeOnLoginOrRegisterRequested();
    }

    private void subscribeOnRootWidthChanged() {
        root.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() > WIDE_MODE_WIDTH_THRESHOLD) {
                if (oldValue.doubleValue() != newValue.doubleValue()) {
                    updateWidthConstraints(newValue.doubleValue());
                }
            } else {
                updateWidthConstraints(DEFAULT_MODE_CONSTRAINT);
            }
        });
    }

    private void updateWidthConstraints(final double width) {
        if (width > WIDE_MODE_WIDTH_THRESHOLD) {
            if (!pane.isVisible()) {
                pane.setManaged(true);
                pane.setVisible(true);
            }
        } else {
            if (pane.isVisible()) {
                pane.setManaged(false);
                pane.setVisible(false);
            }
        }

        if (width == DEFAULT_MODE_CONSTRAINT) {
            AnchorPane.setLeftAnchor(grid, DEFAULT_MODE_CONSTRAINT);
            AnchorPane.setRightAnchor(grid, DEFAULT_MODE_CONSTRAINT);
        } else {
            final var calculatedMargins = width / WIDE_MORE_CONSTRAINT_MULTIPLIER;
            AnchorPane.setLeftAnchor(grid, calculatedMargins);
            AnchorPane.setRightAnchor(grid, calculatedMargins);
        }
    }

    private void subscribeOnBackButtonClicked() {
        backButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (getOnBackRequested() != null) {
                    getOnBackRequested().run();
                }
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
        favorite.setOnMouseClicked(event -> {
            if (getOnFavoriteClicked() != null) {
                getOnFavoriteClicked().run();
            }
        });
    }

    private void subscribeOnCartClicked() {
        cart.setOnMouseClicked(event -> {
            if (getOnCartClicked() != null) {
                getOnCartClicked().run();
            }
        });
    }

    private void subscribeOnLoginOrRegisterRequested() {
        signin.setOnMouseClicked(event -> {
            if (getOnLoginOrRegisterClicked() != null) {
                getOnLoginOrRegisterClicked().run();
            }
        });
    }
}
