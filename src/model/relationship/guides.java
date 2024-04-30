package model.relationship;

public class guides {
    private final int tourID;
    private final int guideID;

    public guides(int tourID, int guideID) {
        this.tourID = tourID;
        this.guideID = guideID;
    }

    public int getTourID() {
        return tourID;
    }

    public int getGuideID() {
        return guideID;
    }
}
