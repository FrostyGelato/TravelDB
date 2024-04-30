package delegates;

import model.entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public interface EntitiesWindowDelegate {

    void getTables(DefaultListModel listModel);
    void getAttributes(String table, DefaultListModel listModel);
    void getTableWithAttributes(String table, List<String> attributes, DefaultTableModel tableModel);
    void getDestinations(DefaultTableModel model);
    public DestinationModel[] getDestinations();
    public String[] getDestinationsString();

    void deleteDestination(DefaultTableModel destination, int selectedRow);

    void getClient(DefaultTableModel model);

    void getTourTable(DefaultTableModel model);

    PublicTransportModel[] getPublicTransports();
    ActivityModel[] getActivities(DefaultTableModel model);
    void insertActivity(ActivityModel model);
    void updateClientAge(int clientID, int age);

    TourGuideModel[] getTourGuides(DefaultTableModel model);
    void insertGuide(TourGuideModel model);
    void updateEquipment(String name, String equip);
    void getAccommodation(DefaultTableModel model);

    void getAttractionsTable(DefaultTableModel model);
    void updateAccomm(String contact, String accom_name, String address, int petFriendly, double estimated_price,
                             int star_rating, String type, String dest_name,String parent_region,String owner);
    void deleteAccommodation(DefaultTableModel accommodation, int selectedRow);
    void insertAccommodation(AccommodationOffersModel accommodation);
    void searchAccommodation(DefaultTableModel tableModel, LinkedHashMap<String, String> conditions, ArrayList<String> operators);
    void searchAccommodation(DefaultTableModel tableModel, String whereCondition);
    void getGroupBy(DefaultTableModel model);

    void getAttractionGroupBy(DefaultTableModel model);
    void getTransportsGroupBy(DefaultTableModel model);
    void close();
    void handleHaving(DefaultTableModel model,int count);
    void findMin(DefaultTableModel model);
    void handleJoin(DefaultTableModel model);
    void handleJoinAfterSelected(DefaultTableModel model, String filter);
    void getPublicTransportations(DefaultTableModel model);
    void handleDivision(DefaultTableModel model, String destName, String parentRegion);

    void filterByDestination(DefaultTableModel tableModel, String selectedItem);
    void insertDestination(DestinationModel tableModel);
    public String[] getAccommodationsString();
    public AccommodationOffersModel[] getAccommodationsOffer();
}
