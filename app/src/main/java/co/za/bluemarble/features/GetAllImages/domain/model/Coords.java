package co.za.bluemarble.features.GetAllImages.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coords {

    @SerializedName("centroid_coordinates")
    @Expose
    private CentroidCoordinates_ centroidCoordinates;
    @SerializedName("dscovr_j2000_position")
    @Expose
    private DscovrJ2000Position_ dscovrJ2000Position;
    @SerializedName("lunar_j2000_position")
    @Expose
    private LunarJ2000Position_ lunarJ2000Position;
    @SerializedName("sun_j2000_position")
    @Expose
    private SunJ2000Position_ sunJ2000Position;
    @SerializedName("attitude_quaternions")
    @Expose
    private AttitudeQuaternions_ attitudeQuaternions;

    public CentroidCoordinates_ getCentroidCoordinates() {
        return centroidCoordinates;
    }

    public void setCentroidCoordinates(CentroidCoordinates_ centroidCoordinates) {
        this.centroidCoordinates = centroidCoordinates;
    }

    public DscovrJ2000Position_ getDscovrJ2000Position() {
        return dscovrJ2000Position;
    }

    public void setDscovrJ2000Position(DscovrJ2000Position_ dscovrJ2000Position) {
        this.dscovrJ2000Position = dscovrJ2000Position;
    }

    public LunarJ2000Position_ getLunarJ2000Position() {
        return lunarJ2000Position;
    }

    public void setLunarJ2000Position(LunarJ2000Position_ lunarJ2000Position) {
        this.lunarJ2000Position = lunarJ2000Position;
    }

    public SunJ2000Position_ getSunJ2000Position() {
        return sunJ2000Position;
    }

    public void setSunJ2000Position(SunJ2000Position_ sunJ2000Position) {
        this.sunJ2000Position = sunJ2000Position;
    }

    public AttitudeQuaternions_ getAttitudeQuaternions() {
        return attitudeQuaternions;
    }

    public void setAttitudeQuaternions(AttitudeQuaternions_ attitudeQuaternions) {
        this.attitudeQuaternions = attitudeQuaternions;
    }

}
