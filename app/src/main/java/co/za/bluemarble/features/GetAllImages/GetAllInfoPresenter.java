package co.za.bluemarble.features.GetAllImages;

import android.support.annotation.NonNull;

import java.util.List;

import co.za.bluemarble.common.UseCase;
import co.za.bluemarble.common.UseCaseHandler;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoObj;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoSchema;
import co.za.bluemarble.features.GetAllImages.domain.usecase.GetAllInfo;

public class GetAllInfoPresenter {

private final GetAllInfo mGetAllInfoUseCase;
private final UseCaseHandler mUseCaseHandler;
private GetAllInfoContract mInfoView;
private String dated = "2018-06-01";

    private boolean mFirstLoad = true;

    public GetAllInfoPresenter(GetAllInfo mGetAllInfo,
                               UseCaseHandler mUseCaseHandler) {
        this.mGetAllInfoUseCase = mGetAllInfo;
        this.mUseCaseHandler = mUseCaseHandler;

    }

    public void setView(@NonNull GetAllInfoContract view) {
        this.mInfoView = view;
    }


    public void start() {
        loadInfo(false);
    }


    public void result(int requestCode, int resultCode) {

    }

    public void loadInfo(boolean forceUpdate) {
        loadInfo(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadInfo(boolean forceUpdate, final boolean showLoadingUI) {
//        if (showLoadingUI) {
//            mInfoView.setLoadingIndicator(true);
//        }

        GetAllInfo.RequestValues requestValue = new GetAllInfo.RequestValues(forceUpdate,
                dated);

        mUseCaseHandler.execute(mGetAllInfoUseCase, requestValue,
                new UseCase.UseCaseCallback<GetAllInfo.ResponseValue>() {
                    @Override
                    public void onSuccess(GetAllInfo.ResponseValue response) {
                        List<EarthInfoSchema> info = response.getInfo();
                        // The view may not be able to handle UI updates anymore
//                        if (!mInfoView.isActive()) {
//                            return;
//                        }
                        if (showLoadingUI) {
                            mInfoView.setLoadingIndicator(false);
                        }

                        processInfo(info);
                    }

                    @Override
                    public void onError() {
                        // The view may not be able to handle UI updates anymore
                        if (!mInfoView.isActive()) {
                            return;
                        }
                        mInfoView.showLoadingTasksError();
                    }
                });
    }

    private void processInfo(List<EarthInfoSchema> info) {
        if (info.isEmpty()) {
            // Show a message indicating there is no info.
            mInfoView.showNoInfo();
        } else {
            // Show the list of tasks
            mInfoView.showInfo(info);
        }
    }

}
