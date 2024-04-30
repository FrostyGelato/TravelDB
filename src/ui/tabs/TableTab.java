package ui.tabs;

import delegates.EntitiesWindowDelegate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public abstract class TableTab extends EmptyTab {

    protected DefaultTableModel tableModel;
    protected JTable table;
    TableColumnModel tcm; //taken from https://stackoverflow.com/questions/1492217/how-to-make-a-columns-in-jtable-invisible-for-swing-java
    protected JScrollPane scrollPane;
    protected JPanel attributesPanel;

    protected TableTab(EntitiesWindowDelegate delegate) {
        super(delegate);
        createTable();
        createAttributesPanel();
    }

    protected void createTable() {
        tableModel = new DefaultTableModel();
        loadTableContents();
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 600));
        add(scrollPane, BorderLayout.NORTH);
    }

    protected void createAttributesPanel() {
        attributesPanel = new JPanel();
        attributesPanel.setLayout(new FlowLayout());
        addToAttributesPanel();
        add(attributesPanel, BorderLayout.CENTER);
    }

    protected void hideColumn(String columnName) //taken from https://github.com/tips4java/tips4java/blob/main/source/TableColumnManager.java
    {
        if (columnName == null) return;

        for (int i = 0; i < tcm.getColumnCount(); i++)
        {
            TableColumn column = tcm.getColumn( i );

            if (columnName.equals((String) column.getHeaderValue()))
            {
                tcm.removeColumn(column);
                break;
            }
        }
    }

    protected abstract void addToAttributesPanel();

    protected abstract void loadTableContents();
}
