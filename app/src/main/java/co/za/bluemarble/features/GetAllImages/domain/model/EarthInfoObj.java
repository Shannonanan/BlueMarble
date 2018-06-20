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
 * Data Access Object for the EarthInfoObj table.
 */


@Entity(tableName = "EarthInfoObj")
public final class EarthInfoObj {

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


    //Best is to store file locally into file system & only add reference entry in db.
    @Nullable
    @ColumnInfo(name = "enhanced_image_ref")
    private byte[] enhanced_images;

    public EarthInfoObj(@NonNull String identifier, String caption, String image,
                        String version, String date, byte[] enhanced_images) {
        this.identifier = identifier;
        this.caption = caption;
        this.image = image;
        this.version = version;
        this.date = date;
        this.enhanced_images = enhanced_images;
    }
//    @SerializedName("coords")
//    @Expose
//    private Coords coords;

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


    @Nullable
    public byte[] getEnhanced_images() {
        return enhanced_images;
    }

    public void setEnhanced_images(@Nullable byte[] enhanced_images) {
        this.enhanced_images = enhanced_images;
    }
//    public CentroidCoordinates getCentroidCoordinates() {
//        return centroidCoordinates;
//    }
//
//    public void setCentroidCoordinates(CentroidCoordinates centroidCoordinates) {
//        this.centroidCoordinates = centroidCoordinates;
//    }
//
//    public DscovrJ2000Position getDscovrJ2000Position() {
//        return dscovrJ2000Position;
//    }
//
//    public void setDscovrJ2000Position(DscovrJ2000Position dscovrJ2000Position) {
//        this.dscovrJ2000Position = dscovrJ2000Position;
//    }
//
//    public LunarJ2000Position getLunarJ2000Position() {
//        return lunarJ2000Position;
//    }
//
//    public void setLunarJ2000Position(LunarJ2000Position lunarJ2000Position) {
//        this.lunarJ2000Position = lunarJ2000Position;
//    }
//
//    public SunJ2000Position getSunJ2000Position() {
//        return sunJ2000Position;
//    }
//
//    public void setSunJ2000Position(SunJ2000Position sunJ2000Position) {
//        this.sunJ2000Position = sunJ2000Position;
//    }
//
//    public AttitudeQuaternions getAttitudeQuaternions() {
//        return attitudeQuaternions;
//    }
//
//    public void setAttitudeQuaternions(AttitudeQuaternions attitudeQuaternions) {
//        this.attitudeQuaternions = attitudeQuaternions;
//    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

//    public Coords getCoords() {
//        return coords;
//    }
//
//    public void setCoords(Coords coords) {
//        this.coords = coords;
//    }

}


//    @SerializedName("centroid_coordinates")
//    @Expose
//    private CentroidCoordinates centroidCoordinates;
//    @SerializedName("dscovr_j2000_position")
//    @Expose
//    private DscovrJ2000Position dscovrJ2000Position;
//    @SerializedName("lunar_j2000_position")
//    @Expose
//    private LunarJ2000Position lunarJ2000Position;
//    @SerializedName("sun_j2000_position")
//    @Expose
//    private SunJ2000Position sunJ2000Position;
//    @SerializedName("attitude_quaternions")
//    @Expose
//    private AttitudeQuaternions attitudeQuaternions;