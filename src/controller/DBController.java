package controller;

import database.DBConnHandler;
import delegates.EntitiesWindowDelegate;
import delegates.LoginWindowDelegate;
import model.entity.*;
import ui.EntitiesWindow;
import ui.LoginWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

// Taken from CPSC 304 Java Sample Project
public class DBController implements LoginWindowDelegate, EntitiesWindowDelegate {

    private DBConnHandler dbHandler = null;
    private LoginWindow loginWindow = null;

    public DBController() {
        dbHandler = new DBConnHandler();
    }

    public void start() {
        loginWindow = new LoginWindow();
        loginWindow.showFrame(this);
    }

    public void login(String username, String password) {
        boolean didConnect = dbHandler.login(username, password);

        if (didConnect) {
            // Once connected, remove login window and start text transaction flow
            loginWindow.dispose();

            EntitiesWindow entities = new EntitiesWindow(this);
            entities.showFrame();
        } else {
            loginWindow.handleLoginFailed();

            if (loginWindow.hasReachedMaxLoginAttempts()) {
                loginWindow.dispose();
                System.exit(-1);
            }
        }
    }

    public void getTables(DefaultListModel listModel) {
        dbHandler.getTables(listModel);
    }

    public void getAttributes(String table, DefaultListModel listModel) {
        dbHandler.getAttributes(table, listModel);
    }

    public void getTableWithAttributes(String table, List<String> attributes, DefaultTableModel tableModel) {
        dbHandler.getTableWithAttributes(table, attributes, tableModel);
    }

    public void getDestinations(DefaultTableModel model) {
        dbHandler.getDestinations(model);
    }

    public DestinationModel[] getDestinations() {
        return dbHandler.getDestinations();
    }

    public String[] getDestinationsString() {
        return dbHandler.getDestinationsString();
    }

    public void deleteDestination(DefaultTableModel destination, int selectedRow) {
        dbHandler.deleteDestination(destination, selectedRow);
    }

    public void getClient(DefaultTableModel model) {
        dbHandler.getClient(model);
    }

    @Override
    public PublicTransportModel[] getPublicTransports() {
        return dbHandler.getPublicTransports();
    }

    public void updateClientAge(int clientID, int age) {
        dbHandler.updateClientAge(clientID, age);
    }

    public void updateEquipment(String name, String equip) {
        dbHandler.updateEquipment(name, equip);
    }

    public void getAccommodation(DefaultTableModel model) {
        dbHandler.getAccommodation(model);
    }

    public void getTourTable(DefaultTableModel model) {
        dbHandler.getTourTable(model);
    }

    @Override
    public void updateAccomm(String contact, String accom_name, String address, int petFriendly, double estimated_price,
                             int star_rating, String type, String dest_name, String parent_region, String owner) {
        dbHandler.updateAccomm(contact, accom_name, address, petFriendly, estimated_price, star_rating, type,
                dest_name, parent_region, owner);
    }

    public void deleteAccommodation(DefaultTableModel accommodation, int selectedRow) {
        dbHandler.deleteAccommodation(accommodation, selectedRow);
    }

    public void insertAccommodation(AccommodationOffersModel accommodation) {
        dbHandler.insertAccommodation(accommodation);
    }

    public void searchAccommodation(DefaultTableModel tableModel, LinkedHashMap<String, String> conditions, ArrayList<String> operators) {
        dbHandler.searchAccommodation(tableModel, conditions, operators);
    }

    public void searchAccommodation(DefaultTableModel tableModel, String whereCondition) {
        dbHandler.searchAccommodation(tableModel, whereCondition);
    }

    @Override
    public void getGroupBy(DefaultTableModel model) {
        dbHandler.getGroupBy(model);
    }

    public void getAttractionGroupBy(DefaultTableModel model) {
        dbHandler.getAttractionGroupBy(model);
    }

    public void getTransportsGroupBy(DefaultTableModel model) {
        dbHandler.getTransportsGroupBy(model);
    }

    @Override
    public ActivityModel[] getActivities(DefaultTableModel model) {
        return dbHandler.getActivities(model);
    }

    public void insertActivity(ActivityModel activityModel) {
        dbHandler.insertActivity(activityModel);
    }

    @Override
    public TourGuideModel[] getTourGuides(DefaultTableModel model) {
        return dbHandler.getTourGuides(model);
    }

    @Override
    public void insertGuide(TourGuideModel guideModel) {
        dbHandler.insertGuide(guideModel);
    }

    public void close() {
        dbHandler.close();
    }

    public void handleHaving(DefaultTableModel model, int count) {
        dbHandler.handleHaving(model, count);
    }

    public void findMin(DefaultTableModel tableModel) {
        dbHandler.findMin(tableModel);
    }

    public void handleJoin(DefaultTableModel tableModel) {
        dbHandler.handleJoin(tableModel);
    }

    public void handleJoinAfterSelected(DefaultTableModel tableModel, String filter) {
        dbHandler.handleJoinAfterSelected(tableModel, filter);
    }

    public void getPublicTransportations(DefaultTableModel tableModel) {
        dbHandler.getPublicTransportations(tableModel);
    }

    public void handleDivision(DefaultTableModel tableModel, String destName, String parentRegion) {
        dbHandler.handleDivision(tableModel, destName, parentRegion);
    }

    public void filterByDestination(DefaultTableModel tableModel, String selectedItem) {
        dbHandler.filterByDestination(tableModel, selectedItem);
    }

    public void insertDestination(DestinationModel tableModel) {
        dbHandler.insertDestination(tableModel);
    }

    public String[] getAccommodationsString() {
        return dbHandler.getAccommodationsString();
    }

    public void getAttractionsTable(DefaultTableModel tableModel) {
        dbHandler.getAttractionsTable(tableModel);
    }

    public AccommodationOffersModel[] getAccommodationsOffer() {
        return dbHandler.getAccommodationsOffer();
    }
}
