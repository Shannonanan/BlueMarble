package co.za.bluemarble.features.GetAllImages.domain.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Data Access Object for the EarthInfoObjEnhanced table.
 */


@Entity(tableName = "EarthInfoObjEnhanced")
public final class EarthInfoObjEnhanced {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "identifier")
    private String identifier;

    @Nullable
    @ColumnInfo(name = "caption")
    private String caption;

    @Nullable
    @ColumnInfo(name = "image")
    private String image;

    @Nullable
    @ColumnInfo(name = "version")
    private String version;

    @Nullable
    @ColumnInfo(name = "date")
    private String date;

    public EarthInfoObjEnhanced(@NonNull String identifier, String caption, String image,
                                String version, String date) {
        this.identifier = identifier;
        this.caption = caption;
        this.image = image;
        this.version = version;
        this.date = date;
    }

    @NonNull
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @NonNull
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @NonNull
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @NonNull
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

    }}

