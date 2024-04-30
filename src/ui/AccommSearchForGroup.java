package ui;

import delegates.EntitiesWindowDelegate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;

public class AccommSearchForGroup extends JDialog {
    private EntitiesWindowDelegate delegate = null;
    private JPanel p;
    private  JButton Having;
    private  JButton nested;
    protected DefaultTableModel tableModel;
    protected JTable table;
    protected  JScrollPane scrollPane;
    private EntitiesWindow parent;
    JLabel havingCount;
    JSpinner countEnter;


    public AccommSearchForGroup(EntitiesWindow parent,EntitiesWindowDelegate delegate){
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
        delegate.getGroupBy(tableModel);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1200,600));
        p.add(scrollPane,BorderLayout.NORTH);
        Having = new JButton("execute HAVING");
        havingCount = new JLabel("Count >=  ");

        countEnter = new JSpinner();
        Component priceFieldEditor = countEnter.getEditor(); //based off https://stackoverflow.com/questions/25188926/change-jspinner-size-width
        JFormattedTextField jftf = ((JSpinner.DefaultEditor) priceFieldEditor).getTextField();
        jftf.setColumns(10);

        Having.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleHaving();
            }
        });

        p.add(havingCount);
        p.add(countEnter);
        p.add(Having);
        nested = new JButton("Find the Lowest Average Price");
        nested.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findMin();
            }
        });
        p.add(nested);


    }
    private void findMin(){
        tableModel.setRowCount(0);
        delegate.findMin(tableModel);
    }
    private void handleHaving(){
        int count = (int) countEnter.getValue();

        tableModel.setRowCount(0);
        loadTableContents(count);
    }

    protected void loadTableContents(int count) {
        delegate.handleHaving(tableModel,count);
    }



}

