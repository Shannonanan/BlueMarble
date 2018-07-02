package co.za.bluemarble.features.GetAllImages;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.za.bluemarble.R;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoPojos;
import co.za.bluemarble.features.common.mvcviews.BaseViewMvc;

public class GetAllInfoViewImpl extends BaseViewMvc<GetAllInfoContract.Listener>
implements GetAllInfoContract{

    @BindView(R.id.swiperRefresh) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rv_AllImages) RecyclerView recyclerView;




    private GetAllImagesAdapter getAllImagesAdapter;

    public GetAllInfoViewImpl(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.activity_get_all_images, container, false);
        setRootView(view);
        ButterKnife.bind(this, view);


//        setAppearance();
//        refreshLayout.setOnRefreshListener(() -> updateViews());
        setupAdapter();
    }

    void setupAdapter(){

        getAllImagesAdapter = new GetAllImagesAdapter(getContext());
        //recycler view setup
        recyclerView.setLayoutManager(new LinearLayoutManager(applicationContext()));
        recyclerView.setAdapter(getAllImagesAdapter);

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
    public void showInfo(List<EarthInfoPojos> info) {
        if (info != null) {
            this.getAllImagesAdapter.setInfoCollection(info);
        }
    }

    @Override
    public void showNoInfo() {
        Toast.makeText(getContext(), "No info to show", Toast.LENGTH_LONG).show();
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
