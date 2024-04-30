package ui;

import delegates.EntitiesWindowDelegate;
import util.ContactFilter;
import util.WordFilter;
import util.LettersDigitsFilter;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;

public class AccommSearch extends JDialog {
    private EntitiesWindowDelegate delegate = null;
    private JPanel mainPanel;
    private GridBagConstraints gbc = new GridBagConstraints();
    private JTextField nameField, addressField, ownerField, contactField, typeField, whereField;
    private JLabel nameLabel, addressLabel, ownerLabel, contactLabel, ratingLabel, typeLabel, priceLabel, petLabel, destinationLabel;
    private JComboBox<Integer> ratingField, petField;
    private JComboBox<String> destinationField;
    private JSpinner priceField;
    private JButton andOrBtn, whereBtn;
    private final int textFieldCol = 15;
    private final int longTextFieldCol = 50;
    private EntitiesWindow parent;
    private HashMap<String, String> filtersMap;

    public AccommSearch(EntitiesWindow parent, EntitiesWindowDelegate delegate) {
        super(parent, "Search for Accommodations", ModalityType.APPLICATION_MODAL);
        this.delegate = delegate;
        this.parent = parent;

        setPreferredSize(new Dimension(1200, 800));

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        setContentPane(mainPanel);

        addComponents();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private void addComponents() {
        nameLabel = new JLabel("Accommodation name: ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(nameLabel, gbc);

        nameField = new JTextField(textFieldCol);
        ((AbstractDocument) nameField.getDocument()).setDocumentFilter(new LettersDigitsFilter());
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(nameField, gbc);

        addressLabel = new JLabel("Address: ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(addressLabel, gbc);

        addressField = new JTextField(textFieldCol);
        ((AbstractDocument) addressField.getDocument()).setDocumentFilter(new LettersDigitsFilter());
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(addressField, gbc);

        ownerLabel = new JLabel("Owner: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(ownerLabel, gbc);

        ownerField = new JTextField(textFieldCol);
        ((AbstractDocument) ownerField.getDocument()).setDocumentFilter(new WordFilter());
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(ownerField, gbc);

        contactLabel = new JLabel("Contact info: ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(contactLabel, gbc);

        contactField = new JTextField(textFieldCol);
        ((AbstractDocument) contactField.getDocument()).setDocumentFilter(new ContactFilter());
        gbc.gridx = 1;
        gbc.gridy = 3;
        mainPanel.add(contactField, gbc);

        ratingLabel = new JLabel("Star rating = ");
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(ratingLabel, gbc);

        Integer[] ratings = {-1, 0, 1, 2, 3, 4, 5};
        ratingField = new JComboBox<>(ratings);
        gbc.gridx = 1;
        gbc.gridy = 4;
        mainPanel.add(ratingField, gbc);

        typeLabel = new JLabel("Type: ");
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(typeLabel, gbc);

        typeField = new JTextField(textFieldCol);
        ((AbstractDocument) typeField.getDocument()).setDocumentFilter(new WordFilter());
        gbc.gridx = 1;
        gbc.gridy = 5;
        mainPanel.add(typeField, gbc);

        priceLabel = new JLabel("Price < ");
        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(priceLabel, gbc);

        priceField = new JSpinner();
        Component priceFieldEditor = priceField.getEditor(); //based off https://stackoverflow.com/questions/25188926/change-jspinner-size-width
        JFormattedTextField jftf = ((JSpinner.DefaultEditor) priceFieldEditor).getTextField();
        jftf.setColumns(10);
        gbc.gridx = 1;
        gbc.gridy = 6;
        mainPanel.add(priceField, gbc);

        petLabel = new JLabel("Pet policy: ");
        gbc.gridx = 0;
        gbc.gridy = 7;
        mainPanel.add(petLabel, gbc);

        Integer[] policy = {0, 1, 2};
        petField = new JComboBox<>(policy);
        gbc.gridx = 1;
        gbc.gridy = 7;
        mainPanel.add(petField, gbc);

        destinationLabel = new JLabel("Destination: ");
        gbc.gridx = 0;
        gbc.gridy = 8;
        mainPanel.add(destinationLabel, gbc);

        String[] destinations = delegate.getDestinationsString();
        destinationField = new JComboBox<>(addToBeginningOfArray(destinations, ""));
        gbc.gridx = 1;
        gbc.gridy = 8;
        mainPanel.add(destinationField, gbc);

        andOrBtn = new JButton("Select and/or conditions");
        andOrBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addValuesToMap();
                if(filtersMap.size() < 1) {
                    JOptionPane.showMessageDialog(null,"Please fill in at least 1 field.");
                } else {
                    AccommSearch2 accommSearch2 = new AccommSearch2(parent, delegate, filtersMap);
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 9;
        mainPanel.add(andOrBtn, gbc);

        whereField = new JTextField(longTextFieldCol);
        gbc.gridx = 1;
        gbc.gridy = 10;
        mainPanel.add(whereField, gbc);

        whereBtn = new JButton("Search using WHERE clause");
        whereBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (whereField.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Please enter a WHERE clause");
                } else {
                    AccommSearch3 accommSearch3 = new AccommSearch3(parent, delegate, whereField.getText());
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 10;
        mainPanel.add(whereBtn, gbc);
    }

    private void checkAndAddToMap(JTextField field, String restOfFilter) {
        if (!field.getText().isBlank()) {
            filtersMap.put(restOfFilter, field.getText().toLowerCase());
        }
    }

    private void addValuesToMap() {
        filtersMap = new HashMap<>();
        checkAndAddToMap(nameField, "accom_name = ");
        checkAndAddToMap(addressField, "address = ");
        checkAndAddToMap(ownerField, "owner = ");
        checkAndAddToMap(contactField, "contact = ");
        if ((Integer) ratingField.getSelectedItem() != 0) {
            filtersMap.put("star_rating = ", Integer.toString((Integer) ratingField.getSelectedItem()));
        }
        checkAndAddToMap(typeField, "type = ");
        if (!priceField.getValue().equals(0)) {
            filtersMap.put("estimated_price < ", Integer.toString((Integer) priceField.getValue()));
        }
        if ((Integer) petField.getSelectedItem() != 0) {
            filtersMap.put("pet_friendly = ", Integer.toString((Integer) petField.getSelectedItem()));
        }
        String destination = (String) destinationField.getSelectedItem();
        if (!destination.equals("")) {
            int comma = destination.indexOf(",");
            filtersMap.put("dest_name = ", destination.substring(0, comma));
            filtersMap.put("parent_region = ", destination.substring(comma + 2));
        }
    }

    private static <T> T[] addToBeginningOfArray(T[] elements, T element) {// taken from https://stackoverflow.com/questions/36807712/simplest-way-to-add-an-item-to-beginning-of-an-array-in-java
        T[] newArray = Arrays.copyOf(elements, elements.length + 1);
        newArray[0] = element;
        System.arraycopy(elements, 0, newArray, 1, elements.length);

        return newArray;
    }
}
