package co.za.bluemarble.features.GetAllImages;

import android.os.Bundle;

import co.za.bluemarble.R;
import co.za.bluemarble.features.common.BaseActivity;

public class GetAllImagesActivity extends BaseActivity implements GetAllImagesContract.Listener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_images);
    }

    @Override
    public void refresh() {

    }

    @Override
    public void getDetail() {

    }
}
