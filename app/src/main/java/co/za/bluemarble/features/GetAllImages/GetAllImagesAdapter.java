package co.za.bluemarble.features.GetAllImages;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.za.bluemarble.R;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoPojos;

public class GetAllImagesAdapter extends RecyclerView.Adapter<GetAllImagesAdapter.GellAllImagesViewHolder> {



    private List<EarthInfoPojos> infoCollection;
    private Context mContext;

    public GetAllImagesAdapter(Context context) {
        this.infoCollection = Collections.emptyList();
        mContext = context;
    }

    @NonNull
    @Override
    public GellAllImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.cell_all_images;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        GellAllImagesViewHolder viewHolder = new GellAllImagesViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GellAllImagesViewHolder holder, int position) {

        EarthInfoPojos earthInfoObj = this.infoCollection.get(position);

//        byte[] byteArrayEarthImage = earthInfoObj.getEnhancedEarthImage();
//        Bitmap bmp = BitmapFactory.decodeByteArray(byteArrayEarthImage, 0, byteArrayEarthImage.length);
         String url = earthInfoObj.getImage();



            Glide.with(mContext)
                    .load(url)
                    .into(holder.iv_enhanced);

        // .asBitmap()
                            //(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//                holder.iv_enhanced.setImageBitmap(resource);
//                saveImage(resource,earthInfoObj.getIdentifier());
//            }
//        });

      //  mImageLoader.loadImage(url, holder.iv_enhanced);

        //Save to phone


        //enhanced
        //https://epic.gsfc.nasa.gov/api/enhanced/date/2018-06-01
        //https://api.nasa.gov/EPIC/archive/enhanced/2018/06/01/png/epic_RGB_20180601010436.png?api_key=2qbtLM8G62k7Um5iwKE7gTlPJKUyP67u4J7h9sUw

        //natural
        //https://api.nasa.gov/EPIC/api/natural/date/2018-06-01?api_key=DEMO_KEY
        //https://epic.gsfc.nasa.gov/archive/natural/2018/06/01/png/epic_1b_20180601010436.png
    }

    @Override
    public int getItemCount() {
        return (this.infoCollection != null) ? this.infoCollection.size() : 0 ;
    }

    static class GellAllImagesViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_enhanced) ImageView iv_enhanced;

        public GellAllImagesViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public void setInfoCollection(Collection<EarthInfoPojos> INFO) {
        this.validateCollection(INFO);
        this.infoCollection = (List<EarthInfoPojos>) INFO;
        this.notifyDataSetChanged();
    }

    private void validateCollection(Collection<EarthInfoPojos> infoCollection) {
        if (infoCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }


//    private void saveImage(Bitmap image, String imageIdentifier) {
//        String savedImagePath = null;
//
//        String imageFileName = "JPEG_" + imageIdentifier + ".jpg";
//        File storageDir = new File(mContext.getFilesDir(), "blueMarble");
//        FileOutputStream outputStream;
//
//        try {
//            outputStream = openFileOutput("blueMarble", Context.MODE_PRIVATE);
//            outputStream.write(image.getBytes());
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        boolean success = true;
//        if (!storageDir.exists()) {
//            success = storageDir.mkdirs();
//        }
//        if (success) {
//            File imageFile = new File(storageDir, imageFileName);
//            savedImagePath = imageFile.getAbsolutePath();
//            try {
//                OutputStream fOut = new FileOutputStream(imageFile);
//                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
//                fOut.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            // Add the image to the system gallery
        //    galleryAddPic(savedImagePath);
     //   }
      //  return savedImagePath;
  //  }

    private void galleryAddPic(String imagePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        mContext.sendBroadcast(mediaScanIntent);
    }
}
