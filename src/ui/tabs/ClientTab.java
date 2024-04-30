package ui.tabs;

import delegates.EntitiesWindowDelegate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class ClientTab extends EmptyTab{
    protected DefaultTableModel tableModel;
    protected JTable table;
    protected JScrollPane scrollPane;


    private static final String WARNING_TAG = "[WARNING]";
    private BufferedReader br = null;
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    public ClientTab(EntitiesWindowDelegate delegate){
        super(delegate);
        createTable();

    }

    protected void createTable(){
        tableModel = new DefaultTableModel();
        delegate.getClient(tableModel);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400,600));
        add(scrollPane,BorderLayout.NORTH);
    }
    @Override
    protected void handleInsert() {
        System.out.println("Inserted client");

    }

    @Override
    protected void handleUpdate() {
        int id = Integer.MIN_VALUE;
        while (id == Integer.MIN_VALUE){
            System.out.print("enter client ID");
            id = readInteger(false);
            System.out.print("1");
        }
        Integer age = null;
        while(age==null||age<=0){
            System.out.print("enter age");
            age = readInteger(false);
        }
        delegate.updateClientAge(id,age);
        createTable();
    }

    @Override
    protected void handleDelete() {

    }

    private int readInteger(boolean allowEmpty){
        String line = null;
        int input = Integer.MIN_VALUE;
        try {
            line = br.readLine();
            input = Integer.parseInt(line);
        } catch (IOException e){
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        } catch (NumberFormatException e){
            if(allowEmpty && line.length()== 0){
                input = 0;
            } else {
                System.out.println(WARNING_TAG + " Your input was not an integer");
            }
        }
        return input;
    }


}
