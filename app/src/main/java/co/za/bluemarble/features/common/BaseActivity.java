package co.za.bluemarble.features.common;

import android.content.Intent;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.airbnb.lottie.LottieAnimationView;

import co.za.bluemarble.MyApplication;
import co.za.bluemarble.R;
import co.za.bluemarble.data.remote.EpicRemoteDataSource;
import co.za.bluemarble.data.remote.LoadImageInterface;
import co.za.bluemarble.di.application.ApplicationComponent;
import co.za.bluemarble.di.presentation.PresentationComponent;
import co.za.bluemarble.di.presentation.PresentationModule;
import co.za.bluemarble.features.GetAllImages.GetAllInfoActivity;

public abstract class BaseActivity extends AppCompatActivity {


    private boolean mIsInjectorUsed;
    LoadImageInterface loadImageInterface = new ImageLoader(this);

    @UiThread
    protected PresentationComponent getPresentationComponent() {
        if (mIsInjectorUsed) {
            throw new RuntimeException("there is no need to use injector more than once");
        }
        mIsInjectorUsed = true;
        return getApplicationComponent()
                .newPresentationComponent(new PresentationModule(this));

    }

    private ApplicationComponent getApplicationComponent() {
        return ((MyApplication) getApplication()).getApplicationComponent();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        switch (itemThatWasClickedId) {
            case R.id.action_get_about:
                //loadAboutData();
                break;
            case R.id.action_get_enhanced_images:
                loadEnhancedData();
                break;
            case R.id.action_get_natural_images:
                //loadnaturaldata
                break;
            default:
                break;
        }
        //  return true;
        return super.onOptionsItemSelected(item);
    }

    private void loadEnhancedData() {

        Intent intent = new Intent(this, GetAllInfoActivity.class);
        startActivity(intent);
    }

}
