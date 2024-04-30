package model.entity;

public class ActivityModel {
    private final String equipmentRequired;
    private final String actiName;

    public ActivityModel(String actiName, String equipmentRequired) {
        this.actiName = actiName;
        this.equipmentRequired = equipmentRequired;
    }

    public String getEquipmentRequired() {
        return equipmentRequired;
    }

    public String getActiName() {
        return actiName;
    }
}
