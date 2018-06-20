package co.za.bluemarble.features.common;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

public class ImageLoader {

    private final Activity mActivity;
    Bitmap theBitmap;

    private final RequestOptions mDefaultRequestOptions;

    public ImageLoader(Activity activity) {
        mActivity = activity;

        mDefaultRequestOptions = new RequestOptions()
                .centerCrop();
    }

    public void loadImage(String uri, ImageView target) {
        Glide.with(mActivity).load(uri).apply(mDefaultRequestOptions).into(target);

    }

    public Bitmap loadImageIntoBitmap(String uri) {
        Glide.with(mActivity)
                .asBitmap()
                .load(uri)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        theBitmap = resource;
                    }
                });

        return  theBitmap;
    }
}
