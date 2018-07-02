package co.za.bluemarble.features.common;


import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import co.za.bluemarble.MyApplication;
import co.za.bluemarble.R;
import co.za.bluemarble.data.remote.LoadImageInterface;
import co.za.bluemarble.di.application.ApplicationComponent;
import co.za.bluemarble.di.presentation.PresentationComponent;
import co.za.bluemarble.di.presentation.PresentationModule;


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



}
