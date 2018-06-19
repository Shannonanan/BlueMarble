package co.za.bluemarble.features.GetAllImages;

import android.os.Bundle;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.za.bluemarble.R;
import co.za.bluemarble.common.UseCaseHandler;
import co.za.bluemarble.data.EpicDataSource;
import co.za.bluemarble.data.EpicRepository;
import co.za.bluemarble.data.local.EarthDao;
import co.za.bluemarble.data.local.EpicLocalDataSource;
import co.za.bluemarble.features.GetAllImages.domain.usecase.GetAllInfo;
import co.za.bluemarble.features.common.BaseActivity;
import co.za.bluemarble.features.common.mvcviews.ViewMvcFactory;

public class GetAllInfoActivity extends BaseActivity implements GetAllInfoContract.Listener{


    @Inject
    GetAllInfoPresenter getAllInfoPresenter;
    @Inject
    ViewMvcFactory viewMvcFactory;


    private GetAllInfoContract mViewMvc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresentationComponent().inject(this);
        mViewMvc = viewMvcFactory.newInstance(GetAllInfoContract.class,null);
        setContentView(mViewMvc.getRootView());

//        EpicLocalDataSource epicLocalDataSource = new EpicLocalDataSource();
//        EpicRepository epicRepository = new EpicRepository();
//        GetAllInfo mGetAllInfo = new GetAllInfo()
//        getAllInfoPresenter(GetAllInfo mGetAllInfo,
//                UseCaseHandler mUseCaseHandler,
//                GetAllInfoContract.View mInfoView) {
//            return new GetAllInfoPresenter(mGetAllInfo, mUseCaseHandler, mInfoView);

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.getAllInfoPresenter.setView(mViewMvc);
        mViewMvc.registerListener(this);


    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
    }



    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void refresh() {

    }

    @Override
    public void getDetail() {

    }

    @Override
    public void loadData() {
        getAllInfoPresenter.start();
    }
}
