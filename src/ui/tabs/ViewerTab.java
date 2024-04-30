package ui.tabs;

import delegates.EntitiesWindowDelegate;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ViewerTab extends JPanel {
    protected EntitiesWindowDelegate delegate = null;
    private DefaultListModel<String> tableListModel, attributeListModel;
    private JList<String> tableList, attributeList;
    private DefaultTableModel tableModel;
    private JTable table;
    private JButton showBtn;
    private JScrollPane tableListScrollPane, attributeListScrollPane, tableScrollPane;

    public ViewerTab(EntitiesWindowDelegate delegate) {
        setLayout(new BorderLayout());

        this.delegate = delegate;
        setup();
    }

    protected void setup() {
        tableListModel = new DefaultListModel<>();

        delegate.getTables(tableListModel);

        tableList = new JList<>(tableListModel);
        tableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableList.setLayoutOrientation(JList.VERTICAL);
        tableList.setVisibleRowCount(-1);
        tableList.setSelectionBackground(Color.LIGHT_GRAY);

        tableListScrollPane = new JScrollPane(tableList);
        tableListScrollPane.setPreferredSize(new Dimension(400, 700));
        add(tableListScrollPane, BorderLayout.WEST);

        attributeListModel = new DefaultListModel<>();

        attributeList = new JList<>(attributeListModel);
        attributeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        attributeList.setLayoutOrientation(JList.VERTICAL);
        attributeList.setVisibleRowCount(-1);
        attributeList.setSelectionBackground(Color.LIGHT_GRAY);

        attributeListScrollPane = new JScrollPane(attributeList);
        attributeListScrollPane.setPreferredSize(new Dimension(400, 700));
        add(attributeListScrollPane, BorderLayout.CENTER);

        tableModel = new DefaultTableModel();

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(400, 600));
        add(tableScrollPane, BorderLayout.SOUTH);

        showBtn = new JButton("Show");
        showBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                if (attributeList.isSelectionEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please select one or more attributes");
                } else {
                    java.util.List<String> selectedAttributes= attributeList.getSelectedValuesList();
                    ArrayList<String> attributes = new ArrayList<>();
                    for (Object a: selectedAttributes) {
                        attributes.add((String) a);
                    }
                    delegate.getTableWithAttributes(tableList.getSelectedValue(), attributes, tableModel);
                }
            }
        });
        add(showBtn, BorderLayout.EAST);

        tableList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    if (tableList.getSelectedIndex() != -1) {
                        attributeListModel.clear();
                        delegate.getAttributes(tableList.getSelectedValue(), attributeListModel);
                    }
                }
            }
        });
    }
}
