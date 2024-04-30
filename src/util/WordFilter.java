package util;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.DocumentFilter;
import javax.swing.text.BadLocationException;

public class WordFilter extends DocumentFilter { //taken from https://stackoverflow.com/questions/14058505/jtextfield-accept-only-alphabet-and-white-space

    @Override
    public void replace(FilterBypass fb, int i, int i1, String string, AttributeSet as) throws BadLocationException {
        for (int n = string.length(); n > 0; n--) {//an inserted string may be more than a single character i.e a copy and paste of 'aaa123d', also we iterate from the back as super.XX implementation will put last insterted string first and so on thus 'aa123d' would be 'daa', but because we iterate from the back its 'aad' like we want
            char c = string.charAt(n - 1);//get a single character of the string
            if (Character.isAlphabetic(c) || c == ' ') {
                super.replace(fb, i, i1, String.valueOf(c), as);//allow update to take place for the given character
            } else {//it was not an alphabetic character or white space
                JOptionPane.showMessageDialog(null, "Only alphabet characters and whitespace are allowed.");
            }
        }
    }

    @Override
    public void remove(FilterBypass fb, int i, int i1) throws BadLocationException {
        super.remove(fb, i, i1);
    }

    @Override
    public void insertString(FilterBypass fb, int i, String string, AttributeSet as) throws BadLocationException {
        super.insertString(fb, i, string, as);
    }
}
