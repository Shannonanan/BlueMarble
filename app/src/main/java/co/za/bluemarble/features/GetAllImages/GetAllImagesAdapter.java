package co.za.bluemarble.features.GetAllImages;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.za.bluemarble.R;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoObj;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoSchema;

public class GetAllImagesAdapter extends RecyclerView.Adapter<GetAllImagesAdapter.GellAllImagesViewHolder> {



    private List<EarthInfoSchema> infoCollection;


    public GetAllImagesAdapter() {
        this.infoCollection = Collections.emptyList();;
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

        EarthInfoSchema earthInfoObj = this.infoCollection.get(position);
        holder.description.setText(earthInfoObj.getCaption());
    }

    @Override
    public int getItemCount() {
        return (this.infoCollection != null) ? this.infoCollection.size() : 0 ;
    }

    static class GellAllImagesViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.description) TextView description;

        public GellAllImagesViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public void setInfoCollection(Collection<EarthInfoSchema> INFO) {
        this.validateCollection(INFO);
        this.infoCollection = (List<EarthInfoSchema>) INFO;
        this.notifyDataSetChanged();
    }

    private void validateCollection(Collection<EarthInfoSchema> infoCollection) {
        if (infoCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }
}
