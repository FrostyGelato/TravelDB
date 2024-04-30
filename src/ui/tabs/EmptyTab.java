package ui.tabs;

import delegates.EntitiesWindowDelegate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class EmptyTab extends JPanel {

    protected EntitiesWindowDelegate delegate = null;

    protected JPanel toolBarPanel;
    protected JButton insertBtn, updateBtn, deleteBtn;

    protected EmptyTab(EntitiesWindowDelegate delegate) {
        setLayout(new BorderLayout());

        this.delegate = delegate;

        createToolBar();
    }

    protected void createToolBar() {
        toolBarPanel = new JPanel();
        toolBarPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
        toolBarPanel.setLayout(new FlowLayout());

        insertBtn = new JButton("Insert");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");

        insertBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleInsert();
            }
        });

        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdate();
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDelete();
            }
        });



        toolBarPanel.add(insertBtn);
        toolBarPanel.add(updateBtn);
        toolBarPanel.add(deleteBtn);
        add(toolBarPanel, BorderLayout.SOUTH);
    }

    abstract protected void handleInsert();
    abstract protected void handleUpdate();
    abstract protected void handleDelete();
}
