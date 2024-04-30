package model.relationship;

public class wantToVisit {
    private final int clientID;
    private final String destName;
    private final String parentRegion;

    public wantToVisit(int clientID,String destName, String parentRegion){
        this.clientID = clientID;
        this.destName = destName;
        this.parentRegion = parentRegion;
    }
    public String getDestName() {
        return destName;
    }

    public String getParentRegion() {
        return parentRegion;
    }

    public int getClientID(){return clientID;}
}
