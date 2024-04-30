package model.relationship;

public class clientSpeaks {
    private final String langName;
    private final int clientID;
    private final int fluency;

    public clientSpeaks(String langName, int clientID, int fluency) {
        this.langName = langName;
        this.clientID = clientID;
        this.fluency = fluency;
    }

    public int getClientID() {
        return clientID;
    }

    public int getFluency() {
        return fluency;
    }

    public String getLangName() {
        return langName;
    }
}
