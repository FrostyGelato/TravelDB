package ui;

import delegates.EntitiesWindowDelegate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransSearchForGroupTab extends JDialog {
    private EntitiesWindowDelegate delegate = null;
    private JPanel p;


    protected DefaultTableModel tableModel;
    protected JTable table;
    protected  JScrollPane scrollPane;
    private EntitiesWindow parent;



    public TransSearchForGroupTab(EntitiesWindow parent,EntitiesWindowDelegate delegate){
        super(parent, "Group By Destination", ModalityType.APPLICATION_MODAL);
        this.delegate = delegate;
        this.parent = parent;
        setPreferredSize(new Dimension(1200, 800));
        p = new JPanel();
        setLayout(new BorderLayout());
        setContentPane(p);
        createTable();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private void createTable(){
        tableModel = new DefaultTableModel();
        delegate.getTransportsGroupBy(tableModel);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1200,600));
        p.add(scrollPane,BorderLayout.NORTH);



    }





}
