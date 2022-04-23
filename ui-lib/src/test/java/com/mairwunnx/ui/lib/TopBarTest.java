package com.mairwunnx.ui.lib;

import com.mairwunnx.ui.lib.apis.TopBarApi;
import com.mairwunnx.ui.lib.base.UiLibTest;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.text.NumberFormat;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("unused")
@ExtendWith(ApplicationExtension.class)
final class TopBarTest {
    private static final class R {
        public static final String btnBack = "#ns-c-topbar__btn_back";
        public static final String btnLocation = "#ns-c-topbar__btn_location";
        public static final String fieldSearch = "#ns-c-topbar__field_search";
        public static final String imvSearch = "#ns-c-topbar__field_search__imv_search";
        public static final String btnFavorite = "#ns-c-topbar__btn_favorite";
        public static final String paneFavoriteBadge = "#ns-c-topbar__pane_favorite_badge";
        public static final String lblFavoriteBadge = "#ns-c-topbar__favorite__lbl_badge";
        public static final String btnCart = "#ns-c-topbar__btn_cart";
        public static final String paneCartBadge = "#ns-c-topbar__pane_cart_badge";
        public static final String lblCartBadge = "#ns-c-topbar__cart__lbl_badge";
        public static final String btnSignIn = "#ns-c-topbar__btn_signin";
        public static final String imvProfile = "#ns-c-topbar__imv_profile";
    }

    private static final NumberFormat compactFormatter = NumberFormat.getCompactNumberInstance();

    private TopBarApi api;

    @BeforeAll
    static void setUp() {
        UiLibTest.initialize();
    }

    @Start
    private void start(final Stage stage) {
        final TopBar topBar = UiLibTest.constructView(TopBar.class);
        api = topBar.getApi();

        final var anchor = new AnchorPane(topBar);

        AnchorPane.setLeftAnchor(topBar, 0.0);
        AnchorPane.setRightAnchor(topBar, 0.0);

        final var scene = new Scene(anchor);
        scene.getStylesheets().addAll(UiLibTest.STYLESHEET);
        stage.setScene(scene);
        stage.setWidth(1200);
        stage.setHeight(600);
        stage.setMinWidth(stage.getScene().getRoot().minWidth(stage.getHeight()));
        stage.show();
    }

    @Test
    @Order(0)
    void setBackAvailable(final FxRobot robot) throws ExecutionException, InterruptedException {
        WaitForAsyncUtils.asyncFx(() -> api.setBackAvailable(true)).get();
        Assertions.assertThat(robot.lookup(R.btnBack).queryButton()).isEnabled();

        WaitForAsyncUtils.asyncFx(() -> api.setBackAvailable(false)).get();
        Assertions.assertThat(robot.lookup(R.btnBack).queryButton()).isDisabled();
    }


    boolean backButtonClickRequested = false;

    @Test
    @Order(1)
    void setOnBackRequested(final FxRobot robot) throws ExecutionException, InterruptedException {
        WaitForAsyncUtils.asyncFx(() -> api.setBackAvailable(true)).get();
        api.setOnBackRequested(() -> backButtonClickRequested = true);

        final var button = robot.lookup(R.btnBack).queryButton();
        robot.moveTo(button);
        robot.press(MouseButton.PRIMARY);
        robot.release(MouseButton.PRIMARY);
        robot.clickOn(button);
        robot.press(KeyCode.SPACE);

        org.assertj.core.api.Assertions.assertThat(backButtonClickRequested).isTrue();
    }

    @Test
    @Order(2)
    void setLocation(final FxRobot robot) throws ExecutionException, InterruptedException {
        final var location = "Moscow";

        WaitForAsyncUtils.asyncFx(() -> api.setLocation(location)).get();
        Assertions.assertThat(robot.lookup(R.btnLocation).queryButton()).hasText(location);
    }

    @Test
    @Order(3)
    void setSearchText(final FxRobot robot) throws ExecutionException, InterruptedException {
        final var search = "32 inch";

        Assertions.assertThat(robot.lookup(R.fieldSearch).queryTextInputControl()).hasText("");

        WaitForAsyncUtils.asyncFx(() -> api.setSearchText(search)).get();
        Assertions.assertThat(robot.lookup(R.fieldSearch).queryTextInputControl()).hasText(search);
        WaitForAsyncUtils.asyncFx(() -> api.setSearchText("")).get();
    }

    @Test
    @Order(4)
    void setFavoriteCount(final FxRobot robot) throws ExecutionException, InterruptedException {
        WaitForAsyncUtils.asyncFx(() -> api.setFavoriteCount(0)).get();
        Assertions.assertThat(robot.lookup(R.paneFavoriteBadge).queryParent()).isInvisible();

        WaitForAsyncUtils.asyncFx(() -> api.setFavoriteCount(10)).get();
        Assertions.assertThat(robot.lookup(R.paneFavoriteBadge).queryParent()).isVisible();
        Assertions.assertThat(robot.lookup(R.lblFavoriteBadge).queryLabeled()).hasText("10");

        WaitForAsyncUtils.asyncFx(() -> api.setFavoriteCount(100)).get();
        Assertions.assertThat(robot.lookup(R.paneFavoriteBadge).queryParent()).isVisible();
        Assertions.assertThat(robot.lookup(R.lblFavoriteBadge).queryLabeled()).hasText("99+");

        WaitForAsyncUtils.asyncFx(() -> api.setFavoriteCount(2600)).get();
        Assertions.assertThat(robot.lookup(R.paneFavoriteBadge).queryParent()).isVisible();
        Assertions.assertThat(robot.lookup(R.lblFavoriteBadge).queryLabeled()).hasText(compactFormatter.format(2600));
    }

    @Test
    @Order(5)
    void setCartCount(final FxRobot robot) throws ExecutionException, InterruptedException {
        WaitForAsyncUtils.asyncFx(() -> api.setCartCount(0)).get();
        Assertions.assertThat(robot.lookup(R.paneCartBadge).queryParent()).isInvisible();

        WaitForAsyncUtils.asyncFx(() -> api.setCartCount(10)).get();
        Assertions.assertThat(robot.lookup(R.paneCartBadge).queryParent()).isVisible();
        Assertions.assertThat(robot.lookup(R.lblCartBadge).queryLabeled()).hasText("10");

        WaitForAsyncUtils.asyncFx(() -> api.setCartCount(100)).get();
        Assertions.assertThat(robot.lookup(R.paneCartBadge).queryParent()).isVisible();
        Assertions.assertThat(robot.lookup(R.lblCartBadge).queryLabeled()).hasText("99+");

        WaitForAsyncUtils.asyncFx(() -> api.setCartCount(2600)).get();
        Assertions.assertThat(robot.lookup(R.paneCartBadge).queryParent()).isVisible();
        Assertions.assertThat(robot.lookup(R.lblCartBadge).queryLabeled()).hasText(compactFormatter.format(2600));
    }

    @Test
    @Order(6)
    void setIsLoggedIn(final FxRobot robot) throws ExecutionException, InterruptedException {
        WaitForAsyncUtils.asyncFx(() -> api.setIsLoggedIn(false)).get();

        Assertions.assertThat(robot.lookup(R.btnSignIn).queryButton()).isVisible();
        Assertions.assertThat(robot.lookup(R.imvProfile).queryAs(ImageView.class)).isInvisible();

        WaitForAsyncUtils.asyncFx(() -> api.setIsLoggedIn(true)).get();
        Assertions.assertThat(robot.lookup(R.btnSignIn).queryButton()).isInvisible();
        Assertions.assertThat(robot.lookup(R.imvProfile).queryAs(ImageView.class)).isVisible();
    }

    @Test
    @Order(7)
    void setAvatarImage(final FxRobot robot) throws ExecutionException, InterruptedException {
        final var image = robot.lookup(R.imvProfile).queryAs(ImageView.class);

        WaitForAsyncUtils.asyncFx(() -> api.setIsLoggedIn(true)).get();
        Assertions.assertThat(robot.lookup(R.imvProfile).queryAs(ImageView.class)).isVisible();

        WaitForAsyncUtils.asyncFx(() -> {
            image.getImage().progressProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.doubleValue() == 1.0) {
                    org.assertj.core.api.Assertions.assertThat(true).isTrue();
                }
            });

            image.getImage().exceptionProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    org.assertj.core.api.Assertions.fail("Image was not loaded");
                }
            });

            api.setAvatarImage("https://previews.123rf.com/images/aquir/aquir1311/aquir131100316/23569861-sample-grunge-red-round-stamp.jpg");
        }).get();
    }

    @Test
    @Order(8)
    void setOnLocationDialogRequested(final FxRobot robot) {
        final AtomicBoolean requested = new AtomicBoolean(false);

        api.setOnLocationDialogRequested(() -> requested.set(true));

        final var button = robot.lookup(R.btnLocation).queryButton();
        robot.clickOn(button);

        org.assertj.core.api.Assertions.assertThat(requested.get()).isTrue();
    }

    @Test
    @Order(9)
    void setOnSearchChanged(final FxRobot robot) {
        final AtomicBoolean changed = new AtomicBoolean(false);

        api.setOnSearchChanged(newString -> changed.set(true));

        final var field = robot.lookup(R.fieldSearch).queryTextInputControl();
        robot.clickOn(field);
        robot.write(R.fieldSearch);

        Assertions.assertThat(robot.lookup(R.fieldSearch).queryTextInputControl()).hasText(R.fieldSearch);
        org.assertj.core.api.Assertions.assertThat(changed.get()).isTrue();
    }

    @Test
    @Order(10)
    void setOnSearchRequested(final FxRobot robot) {
        final AtomicBoolean requested = new AtomicBoolean(false);

        api.setOnSearchRequested((searchVal) -> requested.set(true));

        final var field = robot.lookup(R.fieldSearch).queryTextInputControl();
        robot.clickOn(field);
        robot.write(R.fieldSearch);
        robot.clickOn(R.imvSearch);

        org.assertj.core.api.Assertions.assertThat(requested.get()).isTrue();

        requested.set(false);

        robot.clickOn(field);
        robot.eraseText(R.fieldSearch.length());
        robot.write(R.fieldSearch);
        robot.type(KeyCode.ENTER);

        org.assertj.core.api.Assertions.assertThat(requested.get()).isTrue();
    }

    @Test
    @Order(11)
    void setOnFavoriteClicked(final FxRobot robot) {
        final AtomicBoolean clicked = new AtomicBoolean(false);

        api.setOnFavoriteClicked(() -> clicked.set(true));

        final var button = robot.lookup(R.btnFavorite).queryButton();
        robot.clickOn(button);

        org.assertj.core.api.Assertions.assertThat(clicked.get()).isTrue();
    }

    @Test
    @Order(12)
    void setOnCartClicked(final FxRobot robot) {
        final AtomicBoolean clicked = new AtomicBoolean(false);

        api.setOnCartClicked(() -> clicked.set(true));

        final var button = robot.lookup(R.btnCart).queryButton();
        robot.clickOn(button);

        org.assertj.core.api.Assertions.assertThat(clicked.get()).isTrue();
    }

    @Test
    @Order(13)
    void setOnLoginOrRegisterClicked(final FxRobot robot) {
        final AtomicBoolean clicked = new AtomicBoolean(false);

        api.setOnLoginOrRegisterClicked(() -> clicked.set(true));

        final var button = robot.lookup(R.btnSignIn).queryButton();
        robot.clickOn(button);

        org.assertj.core.api.Assertions.assertThat(clicked.get()).isTrue();
    }

    @Test
    @Order(14)
    void setOnProfileClicked(final FxRobot robot) {
        final AtomicBoolean clicked = new AtomicBoolean(false);

        WaitForAsyncUtils.asyncFx(() -> api.setIsLoggedIn(true));
        api.setOnProfileClicked(() -> clicked.set(true));

        final var image = robot.lookup(R.imvProfile).queryAs(ImageView.class);
        robot.clickOn(image);

        org.assertj.core.api.Assertions.assertThat(clicked.get()).isTrue();
    }
}
