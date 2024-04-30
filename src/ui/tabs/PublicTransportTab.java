package ui.tabs;

import delegates.EntitiesWindowDelegate;
import model.entity.PublicTransportModel;
import ui.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PublicTransportTab extends EmptyTab {
    protected DefaultTableModel tableModel;
    protected JTable table;
    protected JScrollPane scrollPane;
    private JButton divisionButton;
    private JButton searchForGroup;

    public PublicTransportTab(EntitiesWindowDelegate delegate) {
        super(delegate);
        creatTable();
    }

    private void creatTable() {
        tableModel = new DefaultTableModel();
        delegate.getPublicTransportations(tableModel);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400,600));
        add(scrollPane, BorderLayout.NORTH);

        divisionButton = new JButton("Find transport passing through every attraction");
        divisionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransportationDivision division = new TransportationDivision((EntitiesWindow) getTopLevelAncestor(),delegate);

            }
        });
        toolBarPanel.add(divisionButton);
        searchForGroup = new JButton("Search For Group");
        searchForGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransSearchForGroupTab a = new TransSearchForGroupTab((EntitiesWindow) getTopLevelAncestor(),delegate);

            }
        });
        toolBarPanel.add(searchForGroup);
    }
    @Override
    protected void handleInsert() {

    }

    @Override
    protected void handleUpdate() {

    }

    @Override
    protected void handleDelete() {

    }

}
