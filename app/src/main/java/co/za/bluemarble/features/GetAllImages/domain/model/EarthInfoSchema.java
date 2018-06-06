package co.za.bluemarble.features.GetAllImages.domain.model;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class EarthInfoSchema {

        @SerializedName("identifier")
        @Expose
        private String identifier;
        @SerializedName("caption")
        @Expose
        private String caption;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("version")
        @Expose
        private String version;
        @SerializedName("date")
        @Expose
        private String date;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
