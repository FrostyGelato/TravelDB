package model.relationship;

public class stopsAtAccommodation {
    private final String destName;
    private final String parentRegion;
    private final String number;
    private final String type;
    private final String accomName;
    private final String address;
    private final String contact;

    public stopsAtAccommodation(String destName, String parentRegion, String number, String type, String accomName, String address, String contact) {
        this.destName = destName;
        this.parentRegion = parentRegion;
        this.number = number;
        this.type = type;
        this.accomName = accomName;
        this.address = address;
        this.contact = contact;
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public String getParentRegion() {
        return parentRegion;
    }

    public String getDestName() {
        return destName;
    }

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }

    public String getAccomName() {
        return accomName;
    }
}
