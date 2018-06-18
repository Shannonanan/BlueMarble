package co.za.bluemarble.features.GetAllImages;

import java.util.List;

import co.za.bluemarble.common.UseCase;
import co.za.bluemarble.common.UseCaseHandler;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfo;
import co.za.bluemarble.features.GetAllImages.domain.usecase.GetAllInfo;

public class GetAllInfoPresenter implements GetAllInfoContract.Presenter {

private final GetAllInfo mGetAllInfoUseCase;
private final UseCaseHandler mUseCaseHandler;
private final GetAllInfoContract.View mInfoView;
    private String dated = "2018-06-01";

    private boolean mFirstLoad = true;

    public GetAllInfoPresenter(GetAllInfo mGetAllInfo,
                               UseCaseHandler mUseCaseHandler,
                               GetAllInfoContract.View mInfoView) {
        this.mGetAllInfoUseCase = mGetAllInfo;
        this.mUseCaseHandler = mUseCaseHandler;
        this.mInfoView = mInfoView;

        mInfoView.setPresenter(this);
    }

    @Override
    public void start() {
        loadInfo(false);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadInfo(boolean forceUpdate) {
        loadInfo(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadInfo(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mInfoView.setLoadingIndicator(true);
        }

        GetAllInfo.RequestValues requestValue = new GetAllInfo.RequestValues(forceUpdate,
                dated);

        mUseCaseHandler.execute(mGetAllInfoUseCase, requestValue,
                new UseCase.UseCaseCallback<GetAllInfo.ResponseValue>() {
                    @Override
                    public void onSuccess(GetAllInfo.ResponseValue response) {
                        List<EarthInfo> info = response.getInfo();
                        // The view may not be able to handle UI updates anymore
                        if (!mInfoView.isActive()) {
                            return;
                        }
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

    private void processInfo(List<EarthInfo> info) {
        if (info.isEmpty()) {
            // Show a message indicating there is no info.
            mInfoView.showNoInfo();
        } else {
            // Show the list of tasks
            mInfoView.showInfo(info);
        }
    }

}
