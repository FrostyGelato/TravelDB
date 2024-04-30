package ui;

import delegates.EntitiesWindowDelegate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AccommSearch3 extends JDialog {

    private JPanel mainPanel;
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane scrollPane;
    private EntitiesWindowDelegate delegate = null;

    public AccommSearch3(EntitiesWindow parent, EntitiesWindowDelegate delegate, String whereCondition) {
        super(parent, "Search using WHERE clause", ModalityType.APPLICATION_MODAL);
        this.delegate = delegate;

        setPreferredSize(new Dimension(1400, 800));

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        delegate.searchAccommodation(tableModel, whereCondition);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1000, 400));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
}
