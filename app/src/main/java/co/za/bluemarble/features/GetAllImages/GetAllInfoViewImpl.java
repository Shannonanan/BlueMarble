package co.za.bluemarble.features.GetAllImages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfo;
import co.za.bluemarble.features.common.mvcviews.BaseViewMvc;

public class GetAllInfoViewImpl extends BaseViewMvc<GetAllInfoContract.Listener>
implements GetAllInfoContract.View{


    public GetAllInfoViewImpl(LayoutInflater inflater, ViewGroup container) {

    }

    @Override
    public void populateView() {

    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showLoadingTasksError() {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showInfo(List<EarthInfo> info) {

    }

    @Override
    public void showNoInfo() {

    }

    @Override
    public void setPresenter(GetAllInfoContract.Presenter presenter) {

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
