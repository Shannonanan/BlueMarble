package co.za.bluemarble.features.GetAllImages;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.za.bluemarble.R;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoObj;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoPojos;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoSchema;
import co.za.bluemarble.features.common.ImageLoader;

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

        byte[] byteArrayEarthImage = earthInfoObj.getEnhancedEarthImage();
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArrayEarthImage, 0, byteArrayEarthImage.length);
        holder.iv_enhanced.setImageBitmap(bmp);

//            String getDate = earthInfoObj.getDate();
//            String[] dateSplit = getDate.split("-");
//            String year = dateSplit[0];
//            String month = dateSplit[1];
//            String dayHasTimeAttached = dateSplit[2];
//
//            String dayWithoutTime = dayHasTimeAttached.substring(0, dayHasTimeAttached.indexOf(" "));
//
//            String url = "https://api.nasa.gov/EPIC/archive/enhanced/" +
//                    year + "/" + month + "/" + dayWithoutTime +
//                    "/png/" + earthInfoObj.getImage() + ".png?api_key=2qbtLM8G62k7Um5iwKE7gTlPJKUyP67u4J7h9sUw";
//
//            Glide.with(mContext)
//                    .asBitmap()
//                    .load(url)
//                    .into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//
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
}
