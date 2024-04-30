package model.entity;

public class ClientModel {
    private final int clientID;
    private final String cname;
    private final int age;
    private final String gender;
    private final String membership;
    private final int points;
    private final String demographicGroup;

    public ClientModel(int clientID, String cname, int age, String gender, String membership, int points,
                       String demographicGroup) {
        this.clientID = clientID;
        this.cname = cname;
        this.age = age;
        this.gender = gender;
        this.membership = membership;
        this.points = points;
        this.demographicGroup = demographicGroup;
    }

    public int getClientID() {
        return clientID;
    }

    public String getCname() {
        return cname;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getMembership() {
        return membership;
    }

    public int getPoints() {
        return points;
    }

    public String getDemographicGroup() {
        return demographicGroup;
    }
}
