package com.mairwunnx.ui.lib.apis;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Top bar UI element API. Recommended to use it because
 * more clearly namespace.
 *
 * @since 1.0.0.
 */
@SuppressWarnings("unused")
public interface TopBarApi {
    /**
     * On back requested callback. Calls when back arrow button is clicked
     * with mouse or space or enter while focused.
     *
     * @since 1.0.0.
     */
    void setOnBackRequested(@NotNull final Runnable onBackRequested);

    /**
     * On location requested dialog callback. Calls when location button is clicked
     * with mouse or space or enter while focused.
     * <p>
     * By idea must display in-app fullscreen dialog with selecting city if city was
     * detected not correctly.
     *
     * @since 1.0.0.
     */
    void setOnLocationDialogRequested(@NotNull final Runnable onLocationDialogRequested);

    /**
     * On search changed callback. Calls when text in search text field
     * is changed.
     * <p>
     * By idea for every typed character must be shown in-app fullscreen dialog
     * with selecting preview product.
     *
     * @since 1.0.0.
     */
    void setOnSearchChanged(@NotNull final Consumer<String> onSearchChanged);

    /**
     * On search requested callback. Calls when search requested by user, by
     * clicking on search icon or enter while focused in text field.
     * <p>
     * By idea must navigate to other page with displaying that thing user
     * has requested.
     *
     * @since 1.0.0.
     */
    void setOnSearchRequested(@NotNull final Consumer<String> onSearchRequested);

    /**
     * On favorite requested callback. Calls when user clicked on
     * favorite button.
     * <p>
     * By idea must display new page with favorite list.
     *
     * @since 1.0.0.
     */
    void setOnFavoriteClicked(@NotNull final Runnable onFavoriteClicked);

    /**
     * On cart requested callback. Calls when user clicked on cart button.
     * <p>
     * By idea must display new page with cart.
     *
     * @since 1.0.0.
     */
    void setOnCartClicked(@NotNull final Runnable onCartClicked);

    /**
     * On login requested callback. Calls when user clicked on login or
     * register button.
     * <p>
     * By idea must display new page with login\register page.
     *
     * @since 1.0.0.
     */
    void setOnLoginOrRegisterClicked(@NotNull final Runnable onLoginOrRegisterClicked);

    /**
     * On profile requested callback. Calls when user clicked on avatar or
     * profile button.
     * <p>
     * By idea must display new page with profile page.
     *
     * @since 1.0.0.
     */
    void setOnProfileClicked(@NotNull final Runnable onProfileClicked);

    /**
     * Does set available status for back button. True to enable, false
     * to disable.
     * <p/>
     * By idea, we must disable this back button when router graph is empty.
     *
     * @param value value representing enabled state of button.
     * @since 1.0.0.
     */
    void setBackAvailable(final boolean value);

    /**
     * @return interact-available status of back button.
     * @since 1.0.0.
     */
    boolean isBackAvailable();

    /**
     * Sets location text to button.
     *
     * @param value value representing enabled state of button.
     * @since 1.0.0.
     */
    void setLocation(@NotNull final String value);

    /**
     * Sets text into search text field.
     * <p/>
     * By idea after screen switch we must see saved search text value
     * for better UX.
     *
     * @param value search text to set into search text field.
     * @since 1.0.0.
     */
    void setSearchText(@NotNull final String value);

    /**
     * Sets favorite count into favorite button.
     * <p/>
     * By idea, we must show count of favorite items,
     * in case if favorite items count less than 1 then
     * we must hide counter.
     *
     * @param value favorite count to set.
     * @since 1.0.0.
     */
    void setFavoriteCount(final int value);

    /**
     * Sets cart count into cart button.
     * <p/>
     * By idea, we must show count of cart items,
     * in case if cart items count less than 1 then
     * we must hide counter.
     *
     * @param value cart count to set.
     * @since 1.0.0.
     */
    void setCartCount(final int value);

    /**
     * Sets logged in status for top bar.
     * <p/>
     * By idea, visibility of {@code Log in or register} button
     * must depend on this state. In case if we logged in we
     * must show avatar instead {@code Log in or register} button.
     *
     * @param isLoggedIn is logged in status to set.
     * @since 1.0.0.
     */
    void setIsLoggedIn(final boolean isLoggedIn);

    /**
     * Sets avatar image into image view.
     * <p/>
     * By idea, image must present in image view as avatar,
     * it can be rounded and with small border and focusable.
     *
     * @param ref image reference (external link to download image).
     * @since 1.0.0.
     */
    void setAvatarImage(@NotNull final String ref);
}
