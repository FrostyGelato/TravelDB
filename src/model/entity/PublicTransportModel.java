package model.entity;

import model.RelationModel;

public class PublicTransportModel implements RelationModel {
    private final String destName;
    private final String parentRegion;
    private final String num;
    private final String type;
    private final float fare;
    private final String operator;

    public PublicTransportModel(String destName, String parentRegion, String num, String type, float fare,
                                String operator) {
        this.destName = destName;
        this.parentRegion = parentRegion;
        this.num = num;
        this.type = type;
        this.fare = fare;
        this.operator = operator;
    }

    public String getDestName() {
        return destName;
    }

    public String getParentRegion() {
        return parentRegion;
    }

    public String getNum() {
        return num;
    }

    public String getType() {
        return type;
    }

    public float getFare() {
        return fare;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public String getDisplayName() {
        return type + " #" + num;
    }
}
