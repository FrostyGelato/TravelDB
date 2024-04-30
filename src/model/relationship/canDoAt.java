package model.relationship;

public class canDoAt {
    private final String actiName;
    private final String attrName;
    private final float latitude;
    private final float longitude;

    public canDoAt(String actiName, String attrName, float latitude, float longitude) {
        this.actiName = actiName;
        this.attrName = attrName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getActiName() {
        return actiName;
    }

    public String getAttrName() {
        return attrName;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}
