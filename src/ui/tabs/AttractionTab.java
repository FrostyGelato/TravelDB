package ui.tabs;

import delegates.EntitiesWindowDelegate;
import model.entity.AttractionLocatedInModel;
import model.entity.DestinationModel;
import ui.AttractionGroupBy;
import ui.EntitiesWindow;
import ui.TransSearchForGroupTab;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttractionTab extends TableTab {
    private JButton searchForGroup;

    public AttractionTab(EntitiesWindowDelegate delegate) {
        super(delegate);
        tcm = table.getColumnModel();
    }

    @Override
    protected void addToAttributesPanel() {
        searchForGroup = new JButton("Search For Group");
        searchForGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AttractionGroupBy a = new AttractionGroupBy((EntitiesWindow) getTopLevelAncestor(),delegate);

            }
        });
        toolBarPanel.add(searchForGroup);
    }

    @Override
    protected void loadTableContents() {
        delegate.getAttractionsTable(tableModel);
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
