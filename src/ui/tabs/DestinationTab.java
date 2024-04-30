package ui.tabs;

import delegates.EntitiesWindowDelegate;
import model.entity.ActivityModel;
import model.entity.DestinationModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DestinationTab extends TableTab {
    JCheckBox hideDestName;
    JCheckBox hideParentRegion;
    JCheckBox hideAdminUnit;
    JButton confirmAttributes;

    public DestinationTab(EntitiesWindowDelegate delegate) {
        super(delegate);
        tcm = table.getColumnModel();
    }

    @Override
    protected void addToAttributesPanel() {
        JLabel hideLabel = new JLabel("Hide attributes:");
        attributesPanel.add(hideLabel);
        hideDestName = new JCheckBox("Destination name");
        attributesPanel.add(hideDestName);
        hideParentRegion = new JCheckBox("Parent region");
        attributesPanel.add(hideParentRegion);
        hideAdminUnit = new JCheckBox("Administrative unit");
        attributesPanel.add(hideAdminUnit);
        confirmAttributes = new JButton("Confirm");
        confirmAttributes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tcm.getColumnCount() == 1) return;
                if (hideDestName.isSelected()) {
                    hideColumn("DEST_NAME");
                }
                if (hideParentRegion.isSelected()) {
                    hideColumn("PARENT_REGION");
                }
                if (hideAdminUnit.isSelected()) {
                    hideColumn("ADMINISTRATIVE_UNIT");
                }
            }
        });




        attributesPanel.add(confirmAttributes);
    }

//    protected void addAttributeCheckbox(JCheckBox checkbox, String label) {
//        checkbox = new JCheckBox(label);
//        attributesPanel.add(checkbox);
//    }

    @Override
    protected void handleInsert() {
//        String destinationName = null;
//        while(destinationName == null || destinationName.length() == 0) {
//            destinationName = destinationNameEnter.getText();
//        }
//
//        String parentRegion = null;
//        while(parentRegion == null || parentRegion.length() == 0) {
//            parentRegion = parentRegionEnter.getText();
//        }
//
//        String administrativeUnit = null;
//        while(administrativeUnit == null || administrativeUnit.length() == 0) {
//            administrativeUnit = administrativeUnitEnter.getText();
//        }
//
//        DestinationModel destinationModel = new DestinationModel(destinationName, parentRegion, administrativeUnit);
//        delegate.insertDestination(destinationModel);
    }

    @Override
    protected void handleUpdate() {

    }

    @Override
    protected void handleDelete() {
        int selectedRow = table.getSelectedRow();
        delegate.deleteDestination(tableModel, selectedRow);
        tableModel.setRowCount(0);
        loadTableContents();
    }

    protected void loadTableContents() {
        delegate.getDestinations(tableModel);
    }

}
