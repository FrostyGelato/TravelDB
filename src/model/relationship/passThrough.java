package model.relationship;

public class passThrough {
    private final int tourID;
    private final String destName;
    private final String parentRegion;

    public passThrough(int tourID, String destName, String parentRegion) {
        this.tourID = tourID;
        this.destName = destName;
        this.parentRegion = parentRegion;
    }

    public String getParentRegion() {
        return parentRegion;
    }

    public String getDestName() {
        return destName;
    }

    public int getTourID() {
        return tourID;
    }
}
