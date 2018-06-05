package co.za.bluemarble.features.GetAllImages;

import co.za.bluemarble.features.common.mvcviews.BasePresenter;
import co.za.bluemarble.features.common.mvcviews.BaseView;
import co.za.bluemarble.features.common.mvcviews.ObservableViewMvc;

public interface GetAllImagesContract extends ObservableViewMvc<GetAllImagesContract.Listener> {

    /**
     * Listens for user actions and alerts the activity/presenter
     */
    interface Listener{

         void refresh();
         void getDetail();
    }

    interface View extends BaseView<Presenter> {

        void populateView();
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadImages(boolean forceUpdate);
    }
}
