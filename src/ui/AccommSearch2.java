package ui;

import delegates.EntitiesWindowDelegate;
import model.StringPair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class AccommSearch2 extends JDialog {
    private EntitiesWindowDelegate delegate = null;
    private JPanel mainPanel, filtersPanel;
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scrollPane;
    private StringPair[] filters;
    private String[] operators;
    private ArrayList<JComboBox<StringPair>> conditionsComboBoxes;
    private ArrayList<JComboBox<String>> operatorComboBoxes;
    private JButton searchBtn, addBtn;
    private LinkedHashMap<String, String> conditions;
    private ArrayList<String> conjunctions;
    private HashMap<String, String> filtersMap;

    public AccommSearch2(EntitiesWindow parent, EntitiesWindowDelegate delegate, HashMap<String, String> selectedFilters) {
        super(parent, "Select And/Or", ModalityType.APPLICATION_MODAL);
        this.delegate = delegate;
        filtersMap = selectedFilters;
        this.filters = new StringPair[selectedFilters.size() + 1];
        String[] keys = new String[selectedFilters.size()];
        String[] values = new String[selectedFilters.size()];
        int j = 0;
        for (Map.Entry<String, String> e: selectedFilters.entrySet()) {
            keys[j] = e.getKey();
            values[j] = e.getValue();
            j++;
        }
        filters[0] = new StringPair("", "");
        for (int i = 0; i < selectedFilters.size(); i++) {
            filters[i+1] = new StringPair(keys[i] + values[i], keys[i]);
        }
        operators = new String[]{"", "and", "or"};

        setPreferredSize(new Dimension(1400, 800));

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        filtersPanel = new JPanel();
        filtersPanel.setLayout(new BoxLayout(filtersPanel, BoxLayout.PAGE_AXIS));

        conditionsComboBoxes = new ArrayList<>();
        conditionsComboBoxes.add(new JComboBox<>(filters));
        operatorComboBoxes = new ArrayList<>();

        addBtn = new JButton("Add Conditions");
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conditionsComboBoxes.add(new JComboBox<>(filters));
                operatorComboBoxes.add(new JComboBox<>(operators));
                filtersPanel.add(operatorComboBoxes.get(operatorComboBoxes.size()-1));
                filtersPanel.add(conditionsComboBoxes.get(conditionsComboBoxes.size()-1));

                filtersPanel.validate();
                filtersPanel.repaint();
            }
        });
        filtersPanel.add(addBtn);

        searchBtn = new JButton("Search");
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readComboBoxes2();
                if (!conditions.isEmpty()) {
                    delegate.searchAccommodation(tableModel, conditions, conjunctions);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select at least one condition.");
                }
            }
        });
        filtersPanel.add(searchBtn);
        filtersPanel.add(conditionsComboBoxes.get(0));

        mainPanel.add(filtersPanel, BorderLayout.WEST);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1000, 400));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private void readComboBoxes2() {
        conditions = new LinkedHashMap<>();
        conjunctions = new ArrayList<>();
        if (((StringPair) conditionsComboBoxes.get(0).getSelectedItem()).toString().equals("")) {
            return;
        }
        String key = ((StringPair) conditionsComboBoxes.get(0).getSelectedItem()).getKey();
        conditions.put(key, filtersMap.get(key));
        for (int i = 0; i < operatorComboBoxes.size(); i++) {
            if (operatorComboBoxes.get(i).getSelectedItem().equals("") || conditionsComboBoxes.get(i+1).getSelectedItem().equals("")) {
                return;
            }
            conjunctions.add((String) operatorComboBoxes.get(i).getSelectedItem());
            String key2 = ((StringPair) conditionsComboBoxes.get(i+1).getSelectedItem()).getKey();
            conditions.put(key2, filtersMap.get(key2));
        }
    }
}
