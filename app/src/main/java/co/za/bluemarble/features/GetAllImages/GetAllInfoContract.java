package co.za.bluemarble.features.GetAllImages;

import java.util.List;

import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoPojos;
import co.za.bluemarble.features.common.mvcviews.BasePresenter;
import co.za.bluemarble.features.common.mvcviews.ObservableViewMvc;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface GetAllInfoContract extends ObservableViewMvc<GetAllInfoContract.Listener> {

    /**
     * Listens for user actions and alerts the activity/presenter
     */
    interface Listener{

         void refresh();
         void getDetail();
         void loadData();
    }

 //   interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);
        void showLoadingTasksError();
        boolean isActive();
        void showInfo(List<EarthInfoPojos> info);
        void showNoInfo();
  //  }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadInfo(boolean forceUpdate);
    }
}
