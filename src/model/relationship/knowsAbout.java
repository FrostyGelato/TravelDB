package model.relationship;

public class knowsAbout {
    private final int guideID;
    private final String destName;
    private final String parentRegion;

    public knowsAbout(int guideID, String destName, String parentRegion) {
        this.guideID = guideID;
        this.destName = destName;
        this.parentRegion = parentRegion;
    }

    public int getGuideID() {
        return guideID;
    }

    public String getDestName() {
        return destName;
    }

    public String getParentRegion() {
        return parentRegion;
    }
}
