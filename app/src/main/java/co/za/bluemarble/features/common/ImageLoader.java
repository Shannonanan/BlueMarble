package co.za.bluemarble.features.common;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import co.za.bluemarble.data.EpicDataSource;
import co.za.bluemarble.data.remote.LoadImageInterface;

public class ImageLoader implements LoadImageInterface {


    List<byte[]> allBitmaps = new ArrayList<>();
    ByteArrayOutputStream stream = new ByteArrayOutputStream();

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

    public void loadImagesIntoBitmaps(List<String> uri) {
//        loadImageInterface = new LoadImageInterface() {
//            @Override
//            public void getImages(LoadImageCallback callback) {
//                Runnable runnable = new Runnable() {
//                    @Override
//                    public void run () {
//                        for (int i = 0; i <= uri.size(); i++) {
//                            try {
//                                InputStream input = new java.net.URL(uri.get(i)).openStream();
//                                theBitmap = BitmapFactory.decodeStream(input);
//                                Bitmap alteredSize = Bitmap.createScaledBitmap(theBitmap, 150, 150, false);
//                                allBitmaps.add(alteredSize);
//
//                            }catch(MalformedURLException e) {
//                                String es = e.getMessage();
//                            }catch (IOException ex){
//                                String es = ex.getMessage();
//                            }
//                        }
//
//                        callback.onImageLoaded(allBitmaps);
//                    }
//                };
//                new Thread(runnable).start();
//            }
//        };
//        Glide.with(mActivity)
//////                .asBitmap()
//////                .load(uri)
//////                .into(new SimpleTarget<Bitmap>() {
//////                    @Override
//////                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//////                        theBitmap = resource;
//////                        ImageLoader.this.listenToGlide.imageLoaded(theBitmap);
//////                    }
//////                });


    }


    @Override
    public void getImages(List<String> allUrls, LoadImageCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= allUrls.size(); i++) {
                    try {
                        InputStream input = new java.net.URL(allUrls.get(i)).openStream();
                        theBitmap = BitmapFactory.decodeStream(input);
                        //  Bitmap alteredSize = Bitmap.createScaledBitmap(theBitmap, 150, 150, false);
                        theBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArrayImage = stream.toByteArray();
                        allBitmaps.add(byteArrayImage);
                        theBitmap.recycle();

                        if (allBitmaps.size() == allUrls.size()) {
                            callback.onImageLoaded(allBitmaps);
                        }

                    } catch (MalformedURLException e) {
                        String es = e.getMessage();
                    } catch (IOException ex) {
                        String es = ex.getMessage();
                    }
                }
            }
        };
        new Thread(runnable).start();
    }


}

