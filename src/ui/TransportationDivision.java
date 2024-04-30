package ui;

import delegates.EntitiesWindowDelegate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class TransportationDivision extends JDialog {
    private EntitiesWindow parent;
    private EntitiesWindowDelegate delegate;

    private JPanel divisionPanel;
    protected DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scrollPane;
    private JComboBox destinationEnter;
    private JButton applyButton;
    private String filter;
    private JLabel destinationText;
    private String destName;
    private String parentRegion;

    public TransportationDivision(EntitiesWindow parent, EntitiesWindowDelegate delegate) {
        super(parent, "Filter out the public transportation that passing through all the attraction at one destination");
        this.delegate = delegate;
        this.parent = parent;

        setPreferredSize(new Dimension(1200, 800));
        divisionPanel = new JPanel();
        divisionPanel.setLayout(new BorderLayout());
        addToPanel();
        setContentPane(divisionPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public void addToPanel() {
        tableModel = new DefaultTableModel();
        delegate.getPublicTransportations(tableModel);
        table = new JTable((tableModel));

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1000,400));
        divisionPanel.add(scrollPane, BorderLayout.NORTH);

//        tableModel.setRowCount(0);
//        loadTableContents();

        String[] destinations = delegate.getDestinationsString();
        destinationText = new JLabel("Select the destination: ");
        destinationEnter = new JComboBox<>(addToBeginningOfArray(destinations, ""));
        applyButton = new JButton("Apply");
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retrieveFieldValue();
//                filterByDestination(filter);
                tableModel.setRowCount(0);
                loadTableContents();
            }
        });

        divisionPanel.add(destinationText, BorderLayout.WEST);
        divisionPanel.add(destinationEnter, BorderLayout.CENTER);
        divisionPanel.add(applyButton, BorderLayout.SOUTH);


    }

//    private void filterByDestination(String selectedItem) {
//        tableModel.setRowCount(0);
//        delegate.filterByDestination(tableModel, selectedItem);
//    }

    protected void loadTableContents() {
        delegate.handleDivision(tableModel, destName, parentRegion);
    }

    private void retrieveFieldValue() {
        String destination = (String) destinationEnter.getSelectedItem();
        if (!destination.equals("")) {
            int comma = destination.indexOf(",");
            destName = destination.substring(0, comma);
            parentRegion = destination.substring(comma + 2, destination.length());
        } else if (destination.equals("All Destinations")) {
            destName = "";
            parentRegion = "";
        }
    }

    private static <T> T[] addToBeginningOfArray(T[] elements, T element) {// taken from https://stackoverflow.com/questions/36807712/simplest-way-to-add-an-item-to-beginning-of-an-array-in-java
        T[] newArray = Arrays.copyOf(elements, elements.length + 1);
        newArray[0] = element;
        System.arraycopy(elements, 0, newArray, 1, elements.length);

        return newArray;
    }
}
