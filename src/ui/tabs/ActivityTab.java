package ui.tabs;

import controller.DBController;
import delegates.EntitiesWindowDelegate;
import model.entity.ActivityModel;
import model.entity.DestinationModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ActivityTab extends EmptyTab {
    protected DefaultTableModel actiTableModel;
    protected JTable actiTable;

    JScrollPane actiScroll;
    JPanel actiInsertPanel;
    JCheckBox actiInsertCheck;
    JLabel actinameText;
    JLabel equipText;
    JTextField actinameEnter;
    JTextField equipEnter;
    JLabel updateName;
    JLabel updateEquip;
    JTextField uNEnter;
    JTextField uEEnter;
    JCheckBox updateCheck;
    private static final String EXCEPTION_TAG = "[EXCEPTION]";


    private BufferedReader bufferedReader = null;
    public ActivityTab(EntitiesWindowDelegate delegate) {
        super(delegate);
        addToPanel();
    }

    @Override
    protected void handleInsert() {
        String name = null;
        while(name == null || name.length() == 0) {
            name = actinameEnter.getText();
        }

        // the required equipment can be null
        String equip = equipEnter.getText();
        if(equip.length() == 0) {
            equip = null;
        }
        ActivityModel activityModel = new ActivityModel(name, equip);
        delegate.insertActivity(activityModel);

    }

    @Override
    protected void handleUpdate() {
        String name = null;
        while(name==null || name.length()==0){
            name = uNEnter.getText();
        }
        String equip = uEEnter.getText();
        if(equip.length()==0){
            equip=null;
        }
        delegate.updateEquipment(name,equip);


    }

    @Override
    protected void handleDelete() {

    }


    protected void addToPanel() {
//        JLabel attractionLabel = new JLabel("Corresponds Attraction");
//        JCheckBoxMenuItem insertCheck = new JCheckBoxMenuItem();
        // scroll panel to show info
        actiTableModel = new DefaultTableModel();
        delegate.getActivities(actiTableModel);
        actiTable = new JTable(actiTableModel);
        actiTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        actiScroll = new JScrollPane(actiTable);
//        scroll.setPreferredSize(new Dimension(400, 600));

        // insert with checkbox
        actiInsertPanel = new JPanel();
        actiInsertPanel.setLayout(new GridLayout(5,1));
        actiInsertPanel.setPreferredSize(new Dimension(600,100));
        actiInsertCheck = new JCheckBox("Insert");
        actinameText = new JLabel("Enter the name of the activity:");
        equipText = new JLabel("Enter the equipment required by the activity: ");
        actinameEnter = new JTextField(10);
        equipEnter = new JTextField(10);

        updateCheck = new JCheckBox("Update");
        updateName = new JLabel("Select the Name of the Activity You Want to Modify");
        updateEquip = new JLabel("Update the Equipment");
        uNEnter = new JTextField(10);
        uEEnter = new JTextField(10);


        actiInsertPanel.add(actiInsertCheck);
        actiInsertPanel.add(updateCheck);

        actiInsertPanel.add(actinameText);
        actiInsertPanel.add(updateName);

        actiInsertPanel.add(actinameEnter);
        actiInsertPanel.add(uNEnter);

        actiInsertPanel.add(equipText);
        actiInsertPanel.add(updateEquip);

        actiInsertPanel.add(equipEnter);
        actiInsertPanel.add(uEEnter);



//        add(attractionLabel);
        add(actiScroll, BorderLayout.NORTH);
        add(actiInsertPanel, BorderLayout.WEST);
//        detailsPanel.add(insertCheck);
    }

//    private String readLine() {
//        String result = null;
//        try {
//            result = bufferedReader.readLine();
//        } catch (IOException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//        return result;
//    }

}
