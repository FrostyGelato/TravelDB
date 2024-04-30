package model.relationship;

public class visit {
    private final int tourID;
    private final String attrName;
    private final float latitude;
    private final float longitude;

    public visit(int tourID, String attrName, float latitude, float longitude) {
        this.tourID = tourID;
        this.attrName = attrName;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public int getTourID() {
        return tourID;
    }
}
