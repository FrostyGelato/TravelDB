package model.relationship;

public class interestedIn {
    private final int clientID;
    private final String actiName;


    public interestedIn(int clientID, String actiName) {
        this.clientID = clientID;
        this.actiName = actiName;
    }

    public int getClientID() {
        return clientID;
    }

    public String getActiName() {
        return actiName;
    }
}
