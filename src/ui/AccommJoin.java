package ui;

import delegates.EntitiesWindowDelegate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class AccommJoin extends JDialog {
    private EntitiesWindowDelegate delegate;
    private EntitiesWindow parent;


    protected DefaultTableModel tableModel;
    private JPanel joinPanel;
    private JTable table;
    private JScrollPane scrollPane;
    private JComboBox accommodationEnter;
    private JLabel accommodationText;
    private JButton applyButton;
    private String filter;


    public AccommJoin(EntitiesWindow parent, EntitiesWindowDelegate delegate) {
        super(parent, "Join with Public Transportation", ModalityType.APPLICATION_MODAL);
        this.delegate = delegate;
        this.parent = parent;

        setPreferredSize(new Dimension(1200, 800));
        joinPanel = new JPanel();
        addToPanel();
        setContentPane(joinPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public void addToPanel() {
        tableModel = new DefaultTableModel();
        delegate.handleJoin(tableModel);
        table = new JTable((tableModel));

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1000,400));
        joinPanel.add(scrollPane, BorderLayout.CENTER);

        String[] accommodations = delegate.getAccommodationsString();
        accommodationText = new JLabel("Select one accommodation to view the public transportation around: ");
        accommodationEnter = new JComboBox<>(addToBeginningOfArray(accommodations, ""));
        applyButton = new JButton("Apply");

        joinPanel.add(accommodationText);
        joinPanel.add(accommodationEnter);
        joinPanel.add(applyButton);

        tableModel.setRowCount(0);
        loadTableContents();
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retrieveFieldValue();
                handleJoinAfterSelected(tableModel, filter);
            }
        });

    }
    protected void loadTableContents() {
        delegate.handleJoin(tableModel);
    }

    private static <T> T[] addToBeginningOfArray(T[] elements, T element) {// taken from https://stackoverflow.com/questions/36807712/simplest-way-to-add-an-item-to-beginning-of-an-array-in-java
        T[] newArray = Arrays.copyOf(elements, elements.length + 1);
        newArray[0] = element;
        System.arraycopy(elements, 0, newArray, 1, elements.length);

        return newArray;
    }

    private void retrieveFieldValue() {
        String accommodation = (String) accommodationEnter.getSelectedItem();
        if (!accommodation.equals("")) {
            int comma1 = accommodation.indexOf(",");
            int comma2 = accommodation.indexOf(",", comma1 +1);
            filter = "Accommodation_Offers.contact = '" + accommodation.substring(0, comma1) +
                    "' AND Accommodation_Offers.accom_name = '" + accommodation.substring(comma1 + 2, comma2) +
                    "' AND Accommodation_Offers.address = '" + accommodation.substring(comma2 + 2, accommodation.length()) + "'";
        }
    }

    private void handleJoinAfterSelected(DefaultTableModel tableModel,String filter) {
        delegate.handleJoinAfterSelected(tableModel, filter);
    }
}
