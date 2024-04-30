package model.relationship;

public class guideSpeaks {
    private final String langName;
    private final int fluency;
    private final int guideID;

    public guideSpeaks(String langName, int fluency, int guideID) {
        this.langName = langName;
        this.fluency = fluency;
        this.guideID = guideID;
    }

    public int getFluency() {
        return fluency;
    }

    public String getLangName() {
        return langName;
    }

    public int getGuideID() {
        return guideID;
    }
}
