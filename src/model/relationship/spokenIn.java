package model.relationship;

public class spokenIn {
    private final String langName;
    private final String destName;
    private final String parentRegion;

    public spokenIn(String langName, String destName, String parentRegion) {
        this.langName = langName;
        this.destName = destName;
        this.parentRegion = parentRegion;
    }

    public String getLangName() {
        return langName;
    }

    public String getDestName() {
        return destName;
    }

    public String getParentRegion() {
        return parentRegion;
    }
}
