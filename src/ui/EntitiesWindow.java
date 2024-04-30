package ui;

import delegates.EntitiesWindowDelegate;
import ui.tabs.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EntitiesWindow extends JFrame {
    private JPanel viewerPanel;
    private JPanel joinPanel;

    private JPanel destinationPanel;
    private JPanel attractionPanel;
    private JPanel publicTransportPanel;
    private JPanel developerPanel;

    private JPanel tourPanel;

    private JPanel tourGuidePanel;
    private JPanel clientPanel;
    private JPanel accommodationPanel;
    private JPanel activityPanel;
    private JTabbedPane tabbedPane;

    private EntitiesWindowDelegate delegate = null;


    public EntitiesWindow(EntitiesWindowDelegate delegate) {
        super("Travel Database Viewer");

        setPreferredSize(new Dimension(1200, 800));
        //setExtendedState(JFrame.MAXIMIZED_BOTH); // full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.delegate = delegate;

        tabbedPane = new JTabbedPane();
        tabbedPane.setFocusable(false);
        add(tabbedPane);

        viewerPanel = new ViewerTab(delegate);
        destinationPanel = new DestinationTab(delegate);
        attractionPanel = new AttractionTab(delegate);
        publicTransportPanel = new PublicTransportTab(delegate);
        tourPanel = new TourTab(delegate);
        tourGuidePanel = new TourGuideTab(delegate);
        clientPanel = new ClientTab(delegate);
        accommodationPanel = new AccommodationTab(delegate);
        activityPanel = new ActivityTab(delegate);

        tabbedPane.add("Viewer", viewerPanel);
        tabbedPane.add("Destination", destinationPanel); //Joshua
        tabbedPane.add("Attraction", attractionPanel); //Joshua
        tabbedPane.add("Public Transport", publicTransportPanel); //Joshua
        tabbedPane.add("Tour", tourPanel); //Zimo
        tabbedPane.add("Tour Guide", tourGuidePanel); //Zimo
        tabbedPane.add("Client", clientPanel); //Vanda
        tabbedPane.add("Accommodation", accommodationPanel);// Vanda
        tabbedPane.add("Activity", activityPanel); //Zimo

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                delegate.close();
                System.exit(0);
            }
        });

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void showFrame() {
        setVisible(true);
    }
}
