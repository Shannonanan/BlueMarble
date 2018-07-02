package co.za.bluemarble.features.common.mvcviews;

import android.content.Context;

/**
 * Interface representing a View that will use to load data.
 *
 * Created by mminev on 2017/10/25.
 */
public interface LoadDataView {
    /**
     * Show a view with a progress bar indicating a loading process.
     */
    void showLoading();

    /**
     * Hide a loading view.
     */
    void hideLoading();

    /**
     * Show a retry view in case of an error when retrieving data.
     */
    void showRetry();

    /**
     * Hide a retry view shown if there was an error when retrieving data.
     */
    void hideRetry();

    /**
     * Show a sign in view in case there is no user signed in.
     */
 //  void showSignIn();

    /**
     * Hide a sign in view shown if there was no user signed in.
     */
  //  void hideSignIn();

    /**
     * Show an error message
     *
     * @param message A string representing an error.
     */
    void showError(String message);

    /**
     * Get a {@link Context}.
     */
    Context applicationContext();

    /**
     * Get a {@link Context}.
     */
    Context activityContext();
}

