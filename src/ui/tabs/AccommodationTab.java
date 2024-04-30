package ui.tabs;

import delegates.EntitiesWindowDelegate;
import model.entity.AccommodationOffersModel;
import model.entity.DestinationModel;
import ui.AccommJoin;
import ui.AccommSearch;
import ui.AccommSearchForGroup;
import ui.EntitiesWindow;
import util.NumbersFilter;
import util.WordFilter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class AccommodationTab extends EmptyTab {

    protected DefaultTableModel tableModel;
    protected JTable table;
    protected JScrollPane scrollPane;
    JPanel nPanel;

    JLabel contactText, accommNameText, addressText, pet_friendlyText, estimatedPriceText, star_ratingText, typeText, dest_nameText,
            parentRegionText, ownerText, administrativeUnitText;
    JTextField contactEnter, accommNameEnter, addressEnter, estimatedPriceEnter, typeEnter, dest_nameEnter, parentRegionEnter, ownerEnter, administrativeUnitEnter;
    JComboBox<Integer> pet_friendlyEnter, star_ratingEnter;
    private JButton searchBtn, searchForGroup, findTransportationAround, selectUpdate;

    public AccommodationTab(EntitiesWindowDelegate delegate) {
        super(delegate);
        createTable();
    }

    protected void createTable() {
        tableModel = new DefaultTableModel();
        delegate.getAccommodation(tableModel);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 450));
        add(scrollPane, BorderLayout.NORTH);

        nPanel = new JPanel();
        nPanel.setLayout(new GridLayout(6, 4));
        nPanel.setPreferredSize(new Dimension(400, 500));

        contactText = new JLabel("Contact:");
        accommNameText = new JLabel("Accommodation Name:");
        addressText = new JLabel("Address:");
        pet_friendlyText = new JLabel("Pet Friendly?(1:Yes, 0:No, 2: Unknown):");
        estimatedPriceText = new JLabel("Estimated Price:");
        star_ratingText = new JLabel("Star Rating");
        typeText = new JLabel("Type:");
        ownerText = new JLabel("Owner Name:");
        dest_nameText = new JLabel("Destination Name:");
        parentRegionText = new JLabel("Destination Parent Region:");
        administrativeUnitText = new JLabel("Destination Administrative Unit (if you want to insert a:");


        contactEnter = new JTextField(10);
        accommNameEnter = new JTextField(10);
        addressEnter = new JTextField(10);
        Integer[] policy = {0, 1, 2};
        pet_friendlyEnter = new JComboBox<>(policy);
        estimatedPriceEnter = new JTextField(10);
        Integer[] ratings = {0, 1, 2, 3, 4, 5};
        star_ratingEnter = new JComboBox<>(ratings);
        typeEnter = new JTextField(10);
        dest_nameEnter = new JTextField(10);
        parentRegionEnter = new JTextField(10);
        ownerEnter = new JTextField(10);
        administrativeUnitEnter = new JTextField((10));

        nPanel.add(contactText);
        nPanel.add(accommNameText);
        nPanel.add(addressText);
        nPanel.add(pet_friendlyText);


        nPanel.add(contactEnter);
        nPanel.add(accommNameEnter);
        nPanel.add(addressEnter);
        nPanel.add(pet_friendlyEnter);

        nPanel.add(estimatedPriceText);
        nPanel.add(star_ratingText);
        nPanel.add(typeText);
        nPanel.add(ownerText);

        nPanel.add(estimatedPriceEnter);
        nPanel.add(star_ratingEnter);
        nPanel.add(typeEnter);
        nPanel.add(ownerEnter);

        nPanel.add(dest_nameText);
        nPanel.add(parentRegionText);
        nPanel.add(administrativeUnitText);
        nPanel.add(administrativeUnitEnter);

        nPanel.add(dest_nameEnter);
        nPanel.add(parentRegionEnter);


        add(nPanel);
        selectUpdate = new JButton("Load Fields");
        selectUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmToUpDate();
            }
        });
        toolBarPanel.add(selectUpdate);

        searchBtn = new JButton("Search");

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccommSearch accommSearch = new AccommSearch((EntitiesWindow) getTopLevelAncestor(), delegate);
            }
        });

        toolBarPanel.add(searchBtn);


        searchForGroup = new JButton("Search For Group");
        searchForGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccommSearchForGroup a = new AccommSearchForGroup((EntitiesWindow) getTopLevelAncestor(), delegate);

            }
        });
        toolBarPanel.add(searchForGroup);

        findTransportationAround = new JButton("Find Public Transportation Around");
        findTransportationAround.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccommJoin accommJoin = new AccommJoin((EntitiesWindow) getTopLevelAncestor(), delegate);

            }
        });
        toolBarPanel.add(findTransportationAround);
    }

    @Override
    protected void handleInsert() {
        if (isAnyOfFieldsEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all the fields.");
            return;
        }
        if (!isPriceNumeric()) {
            JOptionPane.showMessageDialog(null, "Price must be a number");
            return;
        }

        String contact = contactEnter.getText();
        String accom_name = accommNameEnter.getText();
        String address = addressEnter.getText();
        int pet_friendly = (Integer) pet_friendlyEnter.getSelectedItem();
        float estimated_price = parseFloat(estimatedPriceEnter.getText());
        int star_rating = (Integer) star_ratingEnter.getSelectedItem();
        String type = typeEnter.getText();
        String dest_name = dest_nameEnter.getText();
        String parent_region = parentRegionEnter.getText();
        String owner = ownerEnter.getText();

        if (!administrativeUnitEnter.getText().isEmpty()) {
            String administrativeUnit = administrativeUnitEnter.getText();
            DestinationModel destinationModel = new DestinationModel(dest_name, parent_region, administrativeUnit);
            delegate.insertDestination(destinationModel);
        }

        AccommodationOffersModel accommodation = new AccommodationOffersModel(accom_name, owner, pet_friendly,
                address, estimated_price, type, contact, star_rating, dest_name, parent_region);
        delegate.insertAccommodation(accommodation);

        tableModel.setRowCount(0);
        loadTableContents();
    }

    @Override
    protected void handleUpdate() {
        String contact = contactEnter.getText();
        String accom_name = accommNameEnter.getText();
        String address = addressEnter.getText();

        int petFriendly = (Integer) pet_friendlyEnter.getSelectedItem();
        int star_rating = (Integer) star_ratingEnter.getSelectedItem();
        String type = typeEnter.getText();
        String dest_name = dest_nameEnter.getText();
        String parent_region = parentRegionEnter.getText();
        String owner = ownerEnter.getText();

        if (type.length() == 0) {
            type = null;
        }
        if (owner.length() == 0) {
            owner = null;
        }

        if (contact.isBlank() || accom_name.isBlank() || address.isBlank() || dest_name.isBlank() || parent_region.isBlank()) {
            JOptionPane.showMessageDialog(null, "Contact, accommodation name, address, destination name, and destination parent region cannot be empty");
            return;
        }
        if (!isPriceNumeric()) {
            JOptionPane.showMessageDialog(null, "Price must be a number");
            return;
        }

        double estimated_price;
        if (estimatedPriceEnter.getText().isEmpty()) {
            estimated_price = 0;
        } else {
            estimated_price = Double.parseDouble(estimatedPriceEnter.getText());
        }
        delegate.updateAccomm(contact, accom_name, address, petFriendly, estimated_price, star_rating, type, dest_name,
                parent_region, owner);
        tableModel.setRowCount(0);
        loadTableContents();
    }

    @Override
    protected void handleDelete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            delegate.deleteAccommodation(tableModel, selectedRow);
            tableModel.setRowCount(0);
            loadTableContents();
        }
    }

    protected void confirmToUpDate() {
        int selectedRow = table.getSelectedRow();
        contactEnter.setText((String) tableModel.getValueAt(selectedRow, 0));
        accommNameEnter.setText((String) tableModel.getValueAt(selectedRow, 1));
        addressEnter.setText((String) tableModel.getValueAt(selectedRow, 2));
        String petValue = (String) tableModel.getValueAt(selectedRow, 3);
        int isPet = Integer.parseInt(petValue);

        pet_friendlyEnter.setSelectedItem(isPet);
        String starValue = (String) tableModel.getValueAt(selectedRow, 5);
        int star = Integer.parseInt(starValue);

        star_ratingEnter.setSelectedItem(star);

        String p = (String) tableModel.getValueAt(selectedRow, 4);
        estimatedPriceEnter.setText(p);
        typeEnter.setText((String) tableModel.getValueAt(selectedRow, 6));
        ownerEnter.setText((String) tableModel.getValueAt(selectedRow, 9));
        dest_nameEnter.setText((String) tableModel.getValueAt(selectedRow, 7));
        parentRegionEnter.setText((String) tableModel.getValueAt(selectedRow, 8));
    }

    protected void loadTableContents() {
        delegate.getAccommodation(tableModel);
    }

    private Boolean isAnyOfFieldsEmpty() {
        String contact = contactEnter.getText();
        String accom_name = accommNameEnter.getText();
        String address = addressEnter.getText();
        String estimated_price = estimatedPriceEnter.getText();
        String type = typeEnter.getText();
        String dest_name = dest_nameEnter.getText();
        String parent_region = parentRegionEnter.getText();
        String owner = ownerEnter.getText();

        return (accom_name.isBlank()) || (owner.isBlank()) || (address.isBlank()) || (estimated_price.isBlank()) || (type.isBlank()) ||
                (contact.isBlank()) || (dest_name.isBlank()) || (parent_region.isBlank());
    }

    private Boolean isPriceNumeric() {
        try {
            double value = Double.parseDouble(estimatedPriceEnter.getText());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
