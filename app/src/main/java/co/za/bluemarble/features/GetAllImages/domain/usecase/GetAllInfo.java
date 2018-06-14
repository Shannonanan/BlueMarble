package co.za.bluemarble.features.GetAllImages.domain.usecase;

import java.util.List;

import co.za.bluemarble.common.UseCase;
import co.za.bluemarble.data.EpicDataSource.LoadInfoCallback;
import co.za.bluemarble.data.EpicRepository;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfo;



public class GetAllInfo extends UseCase<GetAllInfo.RequestValues, GetAllInfo.ResponseValue> {

    EpicRepository epicRepository;

    public GetAllInfo(EpicRepository epicRepository) {
        this.epicRepository = epicRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        if (requestValues.isForceUpdate()) {
            epicRepository.refreshTasks();
        }

        epicRepository.getEarthInfo(requestValues.mDate, new LoadInfoCallback() {
            @Override
            public void onDataLoaded(List<EarthInfo> info) {
                ResponseValue responseValue = new ResponseValue(info);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError();
            }
        });
    }

//Each usecase has its own request values and response values that get passed into the
// Base Usecase class
    public static final class RequestValues implements UseCase.RequestValues
    {
        private final boolean mForceUpdate;
        private final String mDate;

        public RequestValues(boolean mForceUpdate, String mDate) {
            this.mForceUpdate = mForceUpdate;
            this.mDate = mDate;
        }
        public boolean isForceUpdate() {
            return mForceUpdate;
        }

        public String getDateforEarthPosts() {
            return mDate;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue{
        private final List<EarthInfo> mEarthInfo;

        public ResponseValue(List<EarthInfo> mEarthInfo) {
            this.mEarthInfo = mEarthInfo;
        }

        public List<EarthInfo> getInfo() {
            return mEarthInfo;
        }
    }
}
