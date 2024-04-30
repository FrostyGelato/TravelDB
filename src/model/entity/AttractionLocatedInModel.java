package model.entity;

import model.RelationModel;

import java.sql.Time;

public class AttractionLocatedInModel implements RelationModel {
    private final String attrName;
    private final float latitude;
    private final float longitude;
    private final float cost;
    private final Time openTime;
    private final Time closeTime;
    private final String website;
    private final String type;
    private final String targetedDemographic;
    private final int minimumAge;
    private final String destName;
    private final String parentRegion;

    private final String cuisine;
    private final String merchandise;
    private final int shopNum;
    private final int completion_year;
    private final String geographicalFeature;

    public AttractionLocatedInModel(String attrName, float latitude, float longitude, float cost, Time openTime,
                                    Time closeTime, String website, String type, String targetedDemographic,
                                    int minimumAge, String destName, String parentRegion, String cuisine,
                                    String merchandise, int shopNum, int completionYear, String geographicalFeature) {
        this.attrName = attrName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cost = cost;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.website = website;
        this.type = type;
        this.targetedDemographic = targetedDemographic;
        this.minimumAge = minimumAge;
        this.destName = destName;
        this.parentRegion = parentRegion;
        this.cuisine = cuisine;
        this.merchandise = merchandise;
        this.shopNum = shopNum;
        completion_year = completionYear;
        this.geographicalFeature = geographicalFeature;
    }

//    public AttractionLocatedInModel(String attrName, float latitude, float longitude, float cost, Time openTime,
//                                    Time closeTime, String website, String type, String targetedDemographic,
//                                    int minimumAge, String destName, String parentRegion) {
//        this.attrName = attrName;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.cost = cost;
//        this.openTime = openTime;
//        this.closeTime = closeTime;
//        this.website = website;
//        this.type = type;
//        this.targetedDemographic = targetedDemographic;
//        this.minimumAge = minimumAge;
//        this.destName = destName;
//        this.parentRegion = parentRegion;
//    }

    public String getAttrName() {
        return attrName;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getCost() {
        return cost;
    }

    public Time getOpenTime() {
        return openTime;
    }

    public Time getCloseTime() {
        return closeTime;
    }

    public String getWebsite() {
        return website;
    }

    public String getType() {
        return type;
    }

    public String getTargetedDemographic() {
        return targetedDemographic;
    }

    public int getMinimumAge() {
        return minimumAge;
    }

    public String getDestName() {
        return destName;
    }

    public String getParentRegion() {
        return parentRegion;
    }

    public int getCompletion_year() {
        return completion_year;
    }

    public int getShopNum() {
        return shopNum;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getGeographicalFeature() {
        return geographicalFeature;
    }

    public String getMerchandise() {
        return merchandise;
    }

    @Override
    public String getDisplayName() {
        return attrName + " (" + latitude + ", " + longitude + ")";
    }
}
