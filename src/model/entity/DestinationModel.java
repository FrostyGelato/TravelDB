package model.entity;

import model.RelationModel;

public class DestinationModel implements RelationModel {
    private final String destName;
    private final String parentRegion;
    private final String administrativeUnit;

    public DestinationModel(String destName, String parentRegion, String administrativeUnit) {
        this.destName = destName;
        this.parentRegion = parentRegion;
        this.administrativeUnit = administrativeUnit;
    }

    public String getDestName() {
        return destName;
    }

    public String getParentRegion() {
        return parentRegion;
    }

    public String getAdministrativeUnit() {
        return administrativeUnit;
    }

    public String getDisplayName() {
        return destName + ", " + parentRegion + " (" + administrativeUnit + ")";
    }

    public String getDestAndParent() {
        return destName + ", " + parentRegion;
    }
}
