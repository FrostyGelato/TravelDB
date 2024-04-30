package model.entity;

import oracle.sql.TIMESTAMP;

public class TourModel {
    private final int tourID;
    private final String tourName;
    private final int spaceAvailable;
    private final float price;
    private final int duration;
    private final int refundable;
    private final TIMESTAMP startTime;
    private final TIMESTAMP endTime;

    public TourModel(int tourID, String tourName, int spaceAvailable, float price, int refundable, int duration
    , TIMESTAMP startTime, TIMESTAMP endTime) {
        this.tourID = tourID;
        this.tourName = tourName;
        this.spaceAvailable = spaceAvailable;
        this.price = price;
        this.refundable = refundable;
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public int getTourID() {return tourID; }

    public String getTourName() {return tourName; }

    public int getSpaceAvailable() {return spaceAvailable; }

    public float getPrice() {return price; }

    public TIMESTAMP getStartTime() {return startTime; }

    public TIMESTAMP getEndTime() {return endTime; }

    public int getDuration() {return duration; }

    public int getRefundable() {return refundable; }
}
