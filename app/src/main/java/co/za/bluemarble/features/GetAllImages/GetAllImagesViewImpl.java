package co.za.bluemarble.features.GetAllImages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import co.za.bluemarble.features.common.mvcviews.BaseViewMvc;

public class GetAllImagesViewImpl extends BaseViewMvc<GetAllImagesContract.Listener>
implements GetAllImagesContract.View{


    public GetAllImagesViewImpl(LayoutInflater inflater, ViewGroup container) {

    }

    @Override
    public void populateView() {

    }

    @Override
    public void setPresenter(GetAllImagesContract.Presenter presenter) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context applicationContext() {
        return null;
    }

    @Override
    public Context activityContext() {
        return null;
    }
}
