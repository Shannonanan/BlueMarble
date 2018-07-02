package co.za.bluemarble.features.GetAllImages.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EarthInfo implements Serializable {

    @Expose
    private List<EarthInfoSchema> earthInfoSchema;

    public void setResult(List<EarthInfoSchema> earthInfoSchema) {
        this.earthInfoSchema = earthInfoSchema;
    }

    public List<EarthInfoSchema> getEarthInfoSchema() {
        return earthInfoSchema;
    }
}


