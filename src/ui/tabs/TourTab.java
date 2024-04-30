package ui.tabs;

import delegates.EntitiesWindowDelegate;

import javax.swing.*;

public class TourTab extends TableTab {
    public TourTab(EntitiesWindowDelegate delegate) {
        super(delegate);
        tcm = table.getColumnModel();
    }

    @Override
    protected void addToAttributesPanel() {

    }

    @Override
    protected void loadTableContents() {
        delegate.getTourTable(tableModel);
    }

    @Override
    protected void handleInsert() { System.out.println("Inserted tour");

    }

    @Override
    protected void handleUpdate() {

    }

    @Override
    protected void handleDelete() {

    }



}
