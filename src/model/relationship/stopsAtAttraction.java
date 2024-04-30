package model.relationship;

public class stopsAtAttraction {
    private final String attrName;
    private final float latitude;
    private final float longitude;
    private final String destName;
    private final String parentRegion;
    private final String number;
    private final String type;

    public stopsAtAttraction(String attrName, float latitude, float longitude, String destName, String parentRegion,
                             String number, String type) {
        this.attrName = attrName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.destName = destName;
        this.parentRegion = parentRegion;
        this.number = number;
        this.type = type;
    }

    public String getParentRegion() {
        return parentRegion;
    }

    public String getDestName() {
        return destName;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public String getAttrName() {
        return attrName;
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }
}
