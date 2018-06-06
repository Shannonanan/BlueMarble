package co.za.bluemarble.features.GetAllImages.domain.usecase;

import java.util.List;

import co.za.bluemarble.common.UseCase;
import co.za.bluemarble.common.executor.PostExecutionThread;
import co.za.bluemarble.common.executor.ThreadExecutor;
import co.za.bluemarble.data.EpicRepository;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfo;
import io.reactivex.Observable;


public class GetAllImages extends UseCase<List<EarthInfo>, Void > {

    EpicRepository epicRepository;

    public GetAllImages(ThreadExecutor threadExecutor,
                        PostExecutionThread postExecutionThread,
                        EpicRepository epicRepository) {
        super(threadExecutor, postExecutionThread);
        this.epicRepository = epicRepository;
    }

    @Override
    public Observable<List<EarthInfo>> buildUseCaseObservable(Void aVoid) {
        return epicRepository.getEarthInfo();
    }
}
