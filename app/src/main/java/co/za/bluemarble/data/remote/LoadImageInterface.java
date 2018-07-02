package co.za.bluemarble.data.remote;

import android.graphics.Bitmap;
import java.util.List;



import static co.za.bluemarble.data.EpicDataSource.*;

public interface LoadImageInterface {

    interface LoadImageCallback {

        void onImageLoaded(List<byte[]> allPics);

        void onImageNotAvailable();
    }

    void getImages(List<String> allUrls, final LoadImageCallback callback);
}
