package model;

public class StringPair {
    private String forComboBox;
    private String forSQL;

    public StringPair(String forComboBox, String forSQL) {
        this.forComboBox = forComboBox;
        this.forSQL = forSQL;
    }

    public String toString() {
        return forComboBox;
    }

    public String getKey() {
        return forSQL;
    }
}
