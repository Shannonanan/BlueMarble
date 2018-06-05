package co.za.bluemarble.features.GetAllImages.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EarthInfo {

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
    @SerializedName("date")
    @Expose
    private String date;
//    @SerializedName("coords")
//    @Expose
//    private Coords coords;

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