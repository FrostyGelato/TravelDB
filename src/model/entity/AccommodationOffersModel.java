package model.entity;

public class AccommodationOffersModel {
    private final String accomName;
    private final String owner;
    private final int petFriendly;
    private final String address;
    private final float estimatedPrice;
    private final String type;
    private final String contact;
    private final int starRating;
    private final String destName;
    private final String parentRegion;

    public AccommodationOffersModel(String accomName, String owner, int petFriendly, String address,
                                    float estimatedPrice, String type, String contact, int starRating,
                                    String destName, String parentRegion) {
        this.accomName = accomName;
        this.owner = owner;
        this.petFriendly = petFriendly;
        this.address = address;
        this.estimatedPrice = estimatedPrice;
        this.type = type;
        this.contact = contact;
        this.starRating = starRating;
        this.destName = destName;
        this.parentRegion = parentRegion;
    }

    public String getAccomName() {
        return accomName;
    }

    public String getOwner() {
        return owner;
    }

    public int getPetFriendly() {return petFriendly; }

    public String getAddress() {
        return address;
    }

    public float getEstimatedPrice() {
        return estimatedPrice;
    }

    public String getType() {
        return type;
    }

    public String getContact() {
        return contact;
    }

    public int getStarRating() {
        return starRating;
    }

    public String getDestName() {
        return destName;
    }

    public String getParentRegion() {
        return parentRegion;
    }

    public String getContactNameAddress() {
        return contact + ", " + accomName + ", " + address;
    }


}
