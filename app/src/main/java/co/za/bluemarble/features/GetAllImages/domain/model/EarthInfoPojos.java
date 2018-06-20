package co.za.bluemarble.features.GetAllImages.domain.model;

import android.graphics.Bitmap;

import java.util.List;

public class EarthInfoPojos {


    private String identifier;
    private String caption;
    private String image;
    private String version;
    private String date;
    public  byte[] enhancedEarthImage;

    public EarthInfoPojos(String identifier, String caption, String image, String version,
                          String date, byte[] enhancedEarthImage) {
        this.identifier = identifier;
        this.caption = caption;
        this.image = image;
        this.version = version;
        this.date = date;
        this.enhancedEarthImage = enhancedEarthImage;
    }


    public byte[] getEnhancedEarthImage() {
        return enhancedEarthImage;
    }

    public void setEnhancedEarthImage(byte[] enhancedEarthImage) {
        this.enhancedEarthImage = enhancedEarthImage;
    }

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
