package model.relationship;

public class stayAt {
    private final int tourID;
    private final String accomName;
    private final String address;
    private final String contact;

    public stayAt(int tourID, String accomName, String address, String contact) {
        this.tourID = tourID;
        this.accomName = accomName;
        this.address = address;
        this.contact = contact;
    }

    public int getTourID() {
        return tourID;
    }

    public String getAccomName() {
        return accomName;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }
}
