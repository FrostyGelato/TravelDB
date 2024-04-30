package ui.tabs;

import delegates.EntitiesWindowDelegate;
import model.entity.ActivityModel;
import model.entity.TourGuideModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class TourGuideTab extends EmptyTab {
    private static final String WARNING_TAG = "[WARNING]";
    private BufferedReader br = null;
    private static final String EXCEPTION_TAG = "[EXCEPTION]";

    protected DefaultTableModel guideTableModel;
    protected JTable guideTable;

    JScrollPane guideScroll;
    // insert with checkbox
    JPanel guideInsertPanel;
    JCheckBox guideInsertCheck;
    JLabel guideIdText;
    JLabel guideNameText;
    JLabel guideAgeText;
    JLabel guideExperienceText;
    JLabel guideSalaryText;
    JTextField guideIdEnter;
    JTextField guideNameEnter;
    JTextField guideAgeEnter;
    JTextField guideExperienceEnter;
    JTextField guideSalaryEnter;




    public TourGuideTab(EntitiesWindowDelegate delegate) {
        super(delegate);
        addToPanel();
    }

    @Override
    protected void handleInsert() {
        int id = 0;
        while(id == Integer.MIN_VALUE) {
            id = parseInt(guideIdEnter.getText());
        }

        String name = null;
        while(name == null || name.length() == 0) {
            name = guideNameEnter.getText();
        }

        int age = 0;
        while(age == Integer.MIN_VALUE) {
            age = parseInt(guideAgeEnter.getText());
        }

        int experience = 0;
        while(experience == Integer.MIN_VALUE) {
            experience= parseInt(guideExperienceEnter.getText());
        }

        int salary = 0;
        while(salary == Integer.MIN_VALUE) {
            salary = parseInt(guideSalaryEnter.getText());
        }

        TourGuideModel guideModel = new TourGuideModel(id, name, age, experience, salary);
        delegate.insertGuide(guideModel);
        new TourGuideTab(delegate);

    }

    @Override
    protected void handleUpdate() {

    }

    @Override
    protected void handleDelete() {

    }

    protected void addToPanel() {
        guideTableModel = new DefaultTableModel();
        delegate.getTourGuides(guideTableModel);
        guideTable = new JTable(guideTableModel);
        guideTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        guideScroll = new JScrollPane(guideTable);
//        scroll.setPreferredSize(new Dimension(400, 600));

        // insert panel with checkbox
        guideInsertPanel = new JPanel();
        guideInsertPanel.setLayout(new GridLayout(11,1));
        guideInsertPanel.setPreferredSize(new Dimension(300,100));

        guideInsertCheck = new JCheckBox("Insert");
        guideIdText = new JLabel("Enter the guide ID: ");
        guideNameText = new JLabel("Enter the guide name: ");
        guideAgeText = new JLabel("Enter the guide age: ");
        guideExperienceText = new JLabel("Enter the year of guide experience: ");
        guideSalaryText = new JLabel("Enter the guide salary");
        guideIdEnter = new JTextField(10);
        guideNameEnter = new JTextField(10);
        guideAgeEnter = new JTextField(10);
        guideExperienceEnter = new JTextField(10);
        guideSalaryEnter = new JTextField(10);

        guideInsertPanel.add(guideInsertCheck);
        guideInsertPanel.add(guideIdText);
        guideInsertPanel.add(guideIdEnter);
        guideInsertPanel.add(guideNameText);
        guideInsertPanel.add(guideNameEnter);
        guideInsertPanel.add(guideAgeText);
        guideInsertPanel.add(guideAgeEnter);
        guideInsertPanel.add(guideExperienceText);
        guideInsertPanel.add(guideExperienceEnter);
        guideInsertPanel.add(guideSalaryText);
        guideInsertPanel.add(guideSalaryEnter);

        // add to empty tab
        add(guideScroll, BorderLayout.NORTH);
        add(guideInsertPanel, BorderLayout.WEST);
    }

    protected void resetPanel() {
        guideTableModel = new DefaultTableModel();
        delegate.getTourGuides(guideTableModel);
        guideTable = new JTable(guideTableModel);
        guideTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        guideScroll = new JScrollPane(guideTable);
        // add to empty tab
        add(guideScroll, BorderLayout.NORTH);
    }
}
