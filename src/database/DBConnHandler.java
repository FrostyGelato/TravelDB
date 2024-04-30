package database;

import model.entity.*;
import util.PrintablePreparedStatement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

// Taken from CPSC 304 Java Sample Project
public class DBConnHandler {

    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    public DBConnHandler() {
        try {
            // Load the Oracle JDBC driver
            // Note that the path could change for new drivers
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            DriverManager.setLoginTimeout(10);
        } catch (SQLException e) {
            showExceptionDialog("initialize DBConnHandler", e.getMessage());
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            showExceptionDialog("close connection", e.getMessage());
        }
    }

    public boolean login(String username, String password) {
        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            connection.setAutoCommit(false);
            return true;
        } catch (SQLException e) {
            showExceptionDialog("login", e.getMessage());
            return false;
        }
    }

    public void getTables(DefaultListModel listModel) {
        try {
            String query = "SELECT table_name FROM user_tables";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listModel.addElement(rs.getString(1));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("get list of tables", e.getMessage());
        }
    }

    public void getAttributes(String table, DefaultListModel listModel) {
        try {
            String query = "select column_name from user_tab_columns where table_name = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, table.toUpperCase());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listModel.addElement(rs.getString(1));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("get attributes for selected table", e.getMessage());
        }
    }

    public void getTableWithAttributes(String table, List<String> attributes, DefaultTableModel tableModel) {
        try {
            String query = String.format("SELECT %s FROM " + table,
                    attributes.stream()
                            .collect(Collectors.joining(", ")));
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int colNum = rsmd.getColumnCount();
            String[] colNames = new String[colNum];
            for (int i = 0; i < colNum; i++) {
                colNames[i] = rsmd.getColumnName(i + 1);
            }
            tableModel.setColumnIdentifiers(colNames);

            while (rs.next()) {
                String[] row = new String[colNum];
                for (int i = 0; i < colNum; i++) {
                    row[i] = rs.getString(i + 1);
                }
                tableModel.addRow(row);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("get table and its attributes", e.getMessage());
        }
    }

    public void getDestinations(DefaultTableModel tableModel) {
        try {
            String query = "SELECT * FROM Destination";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int colNum = rsmd.getColumnCount();
            String[] colNames = new String[colNum];
            for (int i = 0; i < colNum; i++) {
                colNames[i] = rsmd.getColumnName(i + 1);
            }
            tableModel.setColumnIdentifiers(colNames);

            while (rs.next()) {
                String[] row = new String[colNum];
                for (int i = 0; i < colNum; i++) {
                    row[i] = rs.getString(i + 1);
                }
                tableModel.addRow(row);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("get destinations", e.getMessage());
        }
    }


    public void getAttractionsTable(DefaultTableModel tableModel) {
        try {
            String query = "SELECT * FROM Attraction_Located_In_Main";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int colNum = rsmd.getColumnCount();
            String[] colNames = new String[colNum];
            for (int i = 0; i < colNum; i++) {
                colNames[i] = rsmd.getColumnName(i + 1);
            }
            tableModel.setColumnIdentifiers(colNames);

            while (rs.next()) {
                String[] row = new String[colNum];
                for (int i = 0; i < colNum; i++) {
                    row[i] = rs.getString(i + 1);
                }
                tableModel.addRow(row);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("get Attraction_Located_In_Main", e.getMessage());
        }
    }

    public DestinationModel[] getDestinations() {
        ArrayList<DestinationModel> result = new ArrayList<DestinationModel>();

        try {
            String query = "SELECT * FROM Destination";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DestinationModel model = new DestinationModel(rs.getString("dest_name"),
                        rs.getString("parent_region"),
                        rs.getString("administrative_unit"));
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new DestinationModel[result.size()]);
    }

    public String[] getDestinationsString() {
        DestinationModel[] destinationModels = getDestinations();
        String[] strings = new String[destinationModels.length];
        for (int i = 0; i < destinationModels.length; i++) {
            strings[i] = destinationModels[i].getDestAndParent();
        }
        return strings;
    }

    public void deleteDestination(DefaultTableModel destination, int selectedRow) {
        try {
            String query = "DELETE FROM Destination WHERE dest_name = ? AND parent_region = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, (String) destination.getValueAt(selectedRow, 0));
            ps.setString(2, (String) destination.getValueAt(selectedRow, 1));

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(null, WARNING_TAG + (String) destination.getValueAt(selectedRow, 0) + ", " + (String) destination.getValueAt(selectedRow, 1) + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("delete destination", e.getMessage());
            rollbackConnection();
        }
    }

    public PublicTransportModel[] getPublicTransports() {
        ArrayList<PublicTransportModel> result = new ArrayList<PublicTransportModel>();

        try {
            String query = "SELECT * FROM Public_Transportation";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PublicTransportModel model = new PublicTransportModel(rs.getString("P.dest_name"),
                        rs.getString("P.parent_region"), rs.getString("num"), rs.getString("P.type"),
                        rs.getFloat("fare"), rs.getString("P.operator"));
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("get public transportation", e.getMessage());
        }

        return result.toArray(new PublicTransportModel[result.size()]);
    }

    public void updateClientAge(int clientID, int age) {
        try {
            String query = "UPDATE Client SET age = ? WHERE clientID = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, age);
            ps.setInt(2, clientID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(null, WARNING_TAG + "Client" + clientID + " does not exist!");
            }
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("update client age", e.getMessage());
            rollbackConnection();
        }
    }

    public void getClient(DefaultTableModel tableModel) {
        try {
            String query = "SELECT * FROM Client";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query,
                    false);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            DefaultTableModel model = tableModel;

            int colNum = rsmd.getColumnCount();
            String[] colNames = new String[colNum];
            for (int i = 0; i < colNum; i++) {
                colNames[i] = rsmd.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(colNames);

            while (rs.next()) {
                String[] row = new String[colNum];
                for (int i = 0; i < colNum; i++) {
                    row[i] = rs.getString(i + 1);
                }
                model.addRow(row);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("get client", e.getMessage());
        }
    }

    public void insertActivity(ActivityModel activity) {
        try {
            PreparedStatement ps = connection.prepareStatement(("INSERT INTO activity VALUES (?,?)"));
            ps.setString(1, activity.getActiName());
            ps.setString(2, activity.getEquipmentRequired());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("insert activity", e.getMessage());
            rollbackConnection();
        }
    }

    public ActivityModel[] getActivities(DefaultTableModel tableModel) {
        ArrayList<ActivityModel> activies = new ArrayList<ActivityModel>();

        try {
            String query = "SELECT * FROM Activity";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            DefaultTableModel model = tableModel;

            int colNum = rsmd.getColumnCount();
            String[] colNames = new String[colNum];
            for (int i = 0; i < colNum; i++) {
                colNames[i] = rsmd.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(colNames);

            while (rs.next()) {
                String[] row = new String[colNum];
                for (int i = 0; i < colNum; i++) {
                    row[i] = rs.getString(i + 1);
                }
                model.addRow(row);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("get activities", e.getMessage());
        }

        return activies.toArray(new ActivityModel[activies.size()]);
    }

    public TourGuideModel[] getTourGuides(DefaultTableModel tableModel) {
        ArrayList<TourGuideModel> guides = new ArrayList<TourGuideModel>();

        try {
            String query = "SELECT * FROM Tour_Guide_Main";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            DefaultTableModel model = tableModel;

            int colNum = rsmd.getColumnCount();
            String[] colNames = new String[colNum];
            for (int i = 0; i < colNum; i++) {
                colNames[i] = rsmd.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(colNames);

            while (rs.next()) {
                String[] row = new String[colNum];
                for (int i = 0; i < colNum; i++) {
                    row[i] = rs.getString(i + 1);
                }
                model.addRow(row);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("get tour guides", e.getMessage());
        }

        return guides.toArray(new TourGuideModel[guides.size()]);
    }

    public void insertGuide(TourGuideModel guide) {
        try {
            PreparedStatement ps = connection.prepareStatement(("INSERT INTO Tour_Guide_Main VALUES (?,?,?,?)"));
            ps.setInt(1, guide.getGuideID());
            ps.setString(2, guide.getGuide_name());
            ps.setInt(3, guide.getAge());
            ps.setInt(4, guide.getExperience());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("insert guide", e.getMessage());
            rollbackConnection();
        }
    }

    public void updateEquipment(String name, String equip) {
        try {
            String query = "UPDATE Activity SET equipment_required = ? WHERE acti_name = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, equip);
            ps.setString(2, name);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(null, WARNING_TAG + "Activity" + name + " does not exist!");
            }
            connection.commit();


        } catch (SQLException e) {
            showExceptionDialog("update equipment", e.getMessage());
            rollbackConnection();
        }
    }

    public void getAccommodation(DefaultTableModel tableModel) {
        try {
            String query = "SELECT * FROM Accommodation_Offers";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query,
                    false);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int colNum = rsmd.getColumnCount();
            String[] colNames = new String[colNum];
            for (int i = 0; i < colNum; i++) {
                colNames[i] = rsmd.getColumnName(i + 1);
            }
            tableModel.setColumnIdentifiers(colNames);
            while (rs.next()) {
                String[] row = new String[colNum];
                for (int i = 0; i < colNum; i++) {
                    row[i] = rs.getString(i + 1);
                }
                tableModel.addRow(row);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("get accommodations", e.getMessage());
        }
    }

    public void updateAccomm(String contact, String accom_name, String address, int petFriendly, double estimated_price,
                             int star_rating, String type, String dest_name, String parent_region, String owner) {
        try {
            String query = "UPDATE Accommodation_Offers SET pet_friendly = ?, estimated_price=?,star_rating=?," +
                    "type=?,dest_name=?,parent_region=?,owner=? WHERE contact = ? AND accom_name=? AND address=?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, petFriendly);
            ps.setDouble(2, estimated_price);
            ps.setInt(3, star_rating);
            ps.setString(4, type);
            ps.setString(5, dest_name);
            ps.setString(6, parent_region);
            ps.setString(7, owner);
            ps.setString(8, contact);
            ps.setString(9, accom_name);
            ps.setString(10, address);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(null, WARNING_TAG + "Accommodation with contact:"
                        + contact + "and name:" + accom_name + "at" + address + " does not exist!");
            } else {
                connection.commit();

                ps.close();
                JOptionPane.showMessageDialog(null, "Tuple updated.");
            }

        } catch (SQLException e) {
            showExceptionDialog("update accommodation", e.getMessage());
            rollbackConnection();
        }
    }

    public void deleteAccommodation(DefaultTableModel accommodations, int selectedRow) {
        try {
            String query = "DELETE FROM Accommodation_Offers WHERE contact = ? AND accom_name = ? AND address = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, (String) accommodations.getValueAt(selectedRow, 0));
            ps.setString(2, (String) accommodations.getValueAt(selectedRow, 1));
            ps.setString(3, (String) accommodations.getValueAt(selectedRow, 2));

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(null, WARNING_TAG + (String) accommodations.getValueAt(selectedRow, 1) + " owned by " + (String) accommodations.getValueAt(selectedRow, 0) + " does not exist!");
            }

            connection.commit();

            ps.close();
            JOptionPane.showMessageDialog(null, "Tuple deleted.");
        } catch (SQLException e) {
            showExceptionDialog("delete accommodation", e.getMessage());
            rollbackConnection();
        }
    }

    public void insertAccommodation(AccommodationOffersModel accommodation) {
        try {
            PreparedStatement ps = connection.prepareStatement(("INSERT INTO Accommodation_Offers VALUES (?,?,?,?,?,?,?,?,?,?)"));
            ps.setString(1, accommodation.getContact());
            ps.setString(2, accommodation.getAccomName());
            ps.setString(3, accommodation.getAddress());
            ps.setInt(4, accommodation.getPetFriendly());
            ps.setFloat(5, accommodation.getEstimatedPrice());
            ps.setInt(6, accommodation.getStarRating());
            ps.setString(7, accommodation.getType());
            ps.setString(8, accommodation.getDestName());
            ps.setString(9, accommodation.getParentRegion());
            ps.setString(10, accommodation.getOwner());

            ps.executeUpdate();
            connection.commit();

            ps.close();
            JOptionPane.showMessageDialog(null, "Tuple inserted.");
        } catch (SQLException e) {
            showExceptionDialog("insert accommodation", e.getMessage());
            rollbackConnection();
        }
    }

    public void searchAccommodation(DefaultTableModel tableModel, LinkedHashMap<String, String> conditions, ArrayList<String> operators) {
        tableModel.setRowCount(0);
        try {
            Collection<String> keys = conditions.keySet();
            Iterator<String> iterator = keys.iterator();
            String whereCondition = iterator.next() + "?";
            int operatorCount = 0;
            while (iterator.hasNext()) {
                whereCondition = whereCondition + " " + operators.get(operatorCount) + " " + iterator.next() + "?";
                operatorCount++;
            }
            String query = "SELECT * FROM Accommodation_Offers WHERE " + whereCondition;
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);

            int parameterCount = 1;
            for (String s : keys) {
                String value = conditions.get(s);
                if (s.equals("star_rating = ") || s.equals("estimated_price < ") || s.equals("pet_friendly = ")) {
                    ps.setInt(parameterCount, Integer.parseInt(value));
                } else {
                    ps.setString(parameterCount, value);
                }
                parameterCount++;
            }
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int colNum = rsmd.getColumnCount();
            String[] colNames = new String[colNum];
            for (int i = 0; i < colNum; i++) {
                colNames[i] = rsmd.getColumnName(i + 1);
            }
            tableModel.setColumnIdentifiers(colNames);

            while (rs.next()) {
                String[] row = new String[colNum];
                for (int i = 0; i < colNum; i++) {
                    row[i] = rs.getString(i + 1);
                }
                tableModel.addRow(row);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("perform search on accommodation", e.getMessage());
        }
    }

    public void searchAccommodation(DefaultTableModel tableModel, String whereCondition) {
        tableModel.setRowCount(0);
        try {
            String query = "SELECT * FROM Accommodation_Offers WHERE " + whereCondition;
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int colNum = rsmd.getColumnCount();
            String[] colNames = new String[colNum];
            for (int i=0; i<colNum; i++) {
                colNames[i] = rsmd.getColumnName(i+1);
            }
            tableModel.setColumnIdentifiers(colNames);

            while(rs.next()) {
                String[] row = new String[colNum];
                for (int i=0; i<colNum; i++) {
                    row[i] = rs.getString(i+1);
                }
                tableModel.addRow(row);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("select tuples", e.getMessage());
        }
    }

    public void getGroupBy(DefaultTableModel tableModel) {
        try {
            String query = "SELECT parent_region,dest_name,AVG(estimated_price) FROM Accommodation_Offers GROUP BY dest_name, parent_region";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query,
                    false);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            DefaultTableModel model = tableModel;

            int colNum = rsmd.getColumnCount();
            String[] colNames = new String[colNum];
            for (int i = 0; i < colNum; i++) {
                colNames[i] = rsmd.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(colNames);

            while (rs.next()) {
                String[] row = new String[colNum];
                for (int i = 0; i < colNum; i++) {
                    row[i] = rs.getString(i + 1);
                }
                model.addRow(row);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("get group by", e.getMessage());
        }

    }

    public void handleHaving(DefaultTableModel tableModel, int count) {
        try {
            String query = "SELECT parent_region,dest_name,AVG(estimated_price) FROM Accommodation_Offers GROUP BY dest_name, parent_region HAVING COUNT(*)>=?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query,
                    false);
            ps.setInt(1, count);

            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            DefaultTableModel model = tableModel;

            int colNum = rsmd.getColumnCount();
            String[] colNames = new String[colNum];
            for (int i = 0; i < colNum; i++) {
                colNames[i] = rsmd.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(colNames);

            while (rs.next()) {
                String[] row = new String[colNum];
                for (int i = 0; i < colNum; i++) {
                    row[i] = rs.getString(i + 1);
                }
                model.addRow(row);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("handle having", e.getMessage());
        }

    }

    public void findMin(DefaultTableModel tableModel) {
        try {
            String query = "SELECT parent_region,dest_name,AVG(estimated_price) FROM Accommodation_Offers GROUP BY dest_name, parent_region " +
                    "HAVING AVG(estimated_price) <= ALL (SELECT AVG(estimated_price) FROM Accommodation_Offers GROUP BY dest_name, parent_region)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query,
                    false);

            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            DefaultTableModel model = tableModel;

            int colNum = rsmd.getColumnCount();
            String[] colNames = new String[colNum];
            for (int i = 0; i < colNum; i++) {
                colNames[i] = rsmd.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(colNames);

            while (rs.next()) {
                String[] row = new String[colNum];
                for (int i = 0; i < colNum; i++) {
                    row[i] = rs.getString(i + 1);
                }
                model.addRow(row);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("find min price", e.getMessage());
        }

    }

    public void handleJoin(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        try {
            String query = "SELECT Accommodation_Offers.dest_name, Accommodation_Offers.parent_region, Accommodation_Offers.contact, Accommodation_Offers.accom_name, Accommodation_Offers.address, Accommodation_Offers.pet_friendly, Accommodation_Offers.estimated_price, Accommodation_Offers.star_rating, Accommodation_Offers.type, Accommodation_Offers.owner, PUBLIC_TRANSPORTATION.type, PUBLIC_TRANSPORTATION.num, PUBLIC_TRANSPORTATION.fare, PUBLIC_TRANSPORTATION.operator\n" +
                    "FROM Accommodation_Offers, Stops_At_Accommodation, PUBLIC_TRANSPORTATION\n" +
                    "WHERE Accommodation_Offers.dest_name = Stops_At_Accommodation.dest_name AND Accommodation_Offers.parent_region = Stops_At_Accommodation.parent_region AND Accommodation_Offers.contact = Stops_At_Accommodation.contact AND Accommodation_Offers.accom_name = Stops_At_Accommodation.accom_name AND Accommodation_Offers.address = Stops_At_Accommodation.address\n" +
                    "    AND PUBLIC_TRANSPORTATION.dest_name = Stops_At_Accommodation.dest_name AND PUBLIC_TRANSPORTATION.parent_region = Stops_At_Accommodation.parent_region AND PUBLIC_TRANSPORTATION.type = Stops_At_Accommodation.type AND PUBLIC_TRANSPORTATION.num = Stops_At_Accommodation.num\n" +
                    "    AND PUBLIC_TRANSPORTATION.dest_name = Stops_At_Accommodation.dest_name AND PUBLIC_TRANSPORTATION.parent_region = Stops_At_Accommodation.parent_region AND PUBLIC_TRANSPORTATION.type = Stops_At_Accommodation.type AND PUBLIC_TRANSPORTATION.num = Stops_At_Accommodation.num";

            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int colNum = rsmd.getColumnCount();
            String[] colNames = new String[colNum];
            for (int i = 0; i < colNum; i++) {
                colNames[i] = rsmd.getColumnName(i + 1);
            }
            tableModel.setColumnIdentifiers(colNames);

            while (rs.next()) {
                String[] row = new String[colNum];
                for (int i = 0; i < colNum; i++) {
                    row[i] = rs.getString(i + 1);
                }
                tableModel.addRow(row);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("perform join", e.getMessage());
        }
    }

    public void getPublicTransportations(DefaultTableModel tableModel) {
        try {
            String query = "SELECT * FROM PUBLIC_TRANSPORTATION";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();


            int colNum = rsmd.getColumnCount();
            String[] colNames = new String[colNum];
            for (int i = 0; i < colNum; i++) {
                colNames[i] = rsmd.getColumnName(i + 1);
            }
            tableModel.setColumnIdentifiers(colNames);

            while (rs.next()) {
                String[] row = new String[colNum];
                for (int i = 0; i < colNum; i++) {
                    row[i] = rs.getString(i + 1);
                }
                tableModel.addRow(row);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("get public transportation", e.getMessage());
        }
    }

    public void handleDivision(DefaultTableModel tableModel, String destName, String parentRegion) {
        try {
            String query = "SELECT PUBLIC_TRANSPORTATION.DEST_NAME, PUBLIC_TRANSPORTATION.parent_region, PUBLIC_TRANSPORTATION.type, PUBLIC_TRANSPORTATION.num\n" +
                    "FROM PUBLIC_TRANSPORTATION\n" +
                    "WHERE PUBLIC_TRANSPORTATION.dest_name = ? AND PUBLIC_TRANSPORTATION.parent_region = ? AND NOT EXISTS\n" +
                    "((SELECT Attraction_Located_In_Main.longitude, Attraction_Located_In_Main.latitude, Attraction_Located_In_Main.attr_name\n" +
                    "FROM Attraction_Located_In_Main " +
                    "WHERE Attraction_Located_In_Main.DEST_NAME = ? AND Attraction_Located_In_Main.PARENT_REGION = ?) " +
                    "MINUS\n" +
                    "(SELECT Stops_At_Attraction.longitude, Stops_At_Attraction.latitude, Stops_At_Attraction.attr_name\n" +
                    "FROM Stops_At_Attraction\n" +
                    "WHERE Stops_At_Attraction.dest_name = Public_Transportation.dest_name AND Stops_At_Attraction.parent_region = Public_Transportation.parent_region AND Stops_At_Attraction.type = Public_Transportation.type AND Stops_At_Attraction.num= Public_Transportation.num " +
                    "AND Public_Transportation.dest_name = ? AND Public_Transportation.parent_region = ?))";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, destName);
            ps.setString(2, parentRegion);
            ps.setString(3, destName);
            ps.setString(4, parentRegion);
            ps.setString(5, destName);
            ps.setString(6, parentRegion);

            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int colNum = rsmd.getColumnCount();
            String[] colNames = new String[colNum];
            for (int i = 0; i < colNum; i++) {
                colNames[i] = rsmd.getColumnName(i + 1);
            }
            tableModel.setColumnIdentifiers(colNames);

            while (rs.next()) {
                String[] row = new String[colNum];
                for (int i = 0; i < colNum; i++) {
                    row[i] = rs.getString(i + 1);
                }
                tableModel.addRow(row);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("perform division", e.getMessage());
        }
    }

    public void filterByDestination(DefaultTableModel tableModel, String selectedItem) {
        tableModel.setRowCount(0);
        try {
            String query = "SELECT * FROM PUBLIC_TRANSPORTATION WHERE " + selectedItem;
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int colNum = rsmd.getColumnCount();
            String[] colNames = new String[colNum];
            for (int i = 0; i < colNum; i++) {
                colNames[i] = rsmd.getColumnName(i + 1);
            }
            tableModel.setColumnIdentifiers(colNames);

            while (rs.next()) {
                String[] row = new String[colNum];
                for (int i = 0; i < colNum; i++) {
                    row[i] = rs.getString(i + 1);
                }
                tableModel.addRow(row);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("filter by destination", e.getMessage());
        }
    }

    public void insertDestination(DestinationModel destination) {
        try {
            PreparedStatement ps = connection.prepareStatement(("INSERT INTO Destination VALUES (?,?,?)"));
            ps.setString(1, destination.getDestName());
            ps.setString(2, destination.getParentRegion());
            ps.setString(3, destination.getAdministrativeUnit());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("insert destination", e.getMessage());
            rollbackConnection();
        }
    }

    private void rollbackConnection() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            showExceptionDialog("rollback connection", e.getMessage());
        }
    }

    private void showExceptionDialog(String failedAction, String message) {
        JOptionPane.showMessageDialog(null, "Failed to " + failedAction + ": " + EXCEPTION_TAG + " " + message);
    }

    public AccommodationOffersModel[] getAccommodationsOffer() {
        ArrayList<AccommodationOffersModel> result = new ArrayList<AccommodationOffersModel>();
        try {
            String query = "SELECT * FROM Accommodation_Offers";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AccommodationOffersModel model = new AccommodationOffersModel(rs.getString("accom_name"),
                        rs.getString("owner"),
                        rs.getInt("pet_friendly"),
                        rs.getString("address"),
                        rs.getFloat("estimated_price"),
                        rs.getString("type"),
                        rs.getString("contact"),
                        rs.getInt("star_rating"),
                        rs.getString("dest_Name"),
                        rs.getString("parent_region"));
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new AccommodationOffersModel[result.size()]);
    }

    public String[] getAccommodationsString() {
        AccommodationOffersModel[] accommodationOffersModels = getAccommodationsOffer();
        String[] strings = new String[accommodationOffersModels.length];
        for (int i = 0; i < accommodationOffersModels.length; i++) {
            strings[i] = accommodationOffersModels[i].getContactNameAddress();
        }
        return strings;
    }

    public void getTransportsGroupBy(DefaultTableModel tableModel) {
        try {
            String query = "SELECT parent_region,dest_name,AVG(fare) FROM Public_Transportation GROUP BY dest_name, parent_region";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query,
                    false);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            DefaultTableModel model = tableModel;

            int colNum = rsmd.getColumnCount();
            String[] colNames = new String[colNum];
            for (int i = 0; i < colNum; i++) {
                colNames[i] = rsmd.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(colNames);

            while (rs.next()) {
                String[] row = new String[colNum];
                for (int i = 0; i < colNum; i++) {
                    row[i] = rs.getString(i + 1);
                }
                model.addRow(row);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("get group by", e.getMessage());
        }

    }

    public void getAttractionGroupBy(DefaultTableModel tableModel) {
        try {
            String query = "SELECT parent_region,dest_name,type,AVG(cost) FROM Attraction_Located_In_Main GROUP BY parent_region,dest_name,type" +
                    " HAVING AVG(cost) >= ALL (SELECT AVG(cost) FROM Attraction_Located_In_Main GROUP BY parent_region,dest_name,type)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query,
                    false);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            DefaultTableModel model = tableModel;

            int colNum = rsmd.getColumnCount();
            String[] colNames = new String[colNum];
            for (int i = 0; i < colNum; i++) {
                colNames[i] = rsmd.getColumnName(i + 1);
            }
            model.setColumnIdentifiers(colNames);

            while (rs.next()) {
                String[] row = new String[colNum];
                for (int i = 0; i < colNum; i++) {
                    row[i] = rs.getString(i + 1);
                }
                model.addRow(row);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("get group by", e.getMessage());
        }
    }

    public void getTourTable(DefaultTableModel tableModel) {
        try {
            String query = "SELECT Tour_Main.tourID, Tour_Main.tour_name,Tour_Main.price,Tour_Main.space_available," +
                    " Tour_Main.start_time, Tour_Main.end_time, Tour_1.duration, Tour_2.refundable " +
                    " FROM Tour_1,Tour_2,Tour_Main WHERE Tour_1.start_time=Tour_Main.start_time AND" +
                    " Tour_1.end_time=Tour_Main.end_time AND Tour_2.start_time=Tour_Main.start_time AND" +
                    " Tour_2.end_time=Tour_Main.end_time";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();


            int colNum = rsmd.getColumnCount();
            String[] colNames = new String[colNum];
            for (int i = 0; i < colNum; i++) {
                colNames[i] = rsmd.getColumnName(i + 1);
            }
            tableModel.setColumnIdentifiers(colNames);

            while (rs.next()) {
                String[] row = new String[colNum];
                for (int i = 0; i < colNum; i++) {
                    row[i] = rs.getString(i + 1);
                }
                tableModel.addRow(row);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("get tour", e.getMessage());
        }
    }

    public void handleJoinAfterSelected(DefaultTableModel tableModel, String filter) {
        tableModel.setRowCount(0);
        try {
            String query = "SELECT Accommodation_Offers.dest_name, Accommodation_Offers.parent_region, Accommodation_Offers.contact, Accommodation_Offers.accom_name, Accommodation_Offers.address, Accommodation_Offers.pet_friendly, Accommodation_Offers.estimated_price, Accommodation_Offers.star_rating, Accommodation_Offers.type, Accommodation_Offers.owner, PUBLIC_TRANSPORTATION.type, PUBLIC_TRANSPORTATION.num, PUBLIC_TRANSPORTATION.fare, PUBLIC_TRANSPORTATION.operator" +
                    " FROM Accommodation_Offers, Stops_At_Accommodation, PUBLIC_TRANSPORTATION" +
                    " WHERE " + filter + " AND " +
                    "Accommodation_Offers.dest_name = Stops_At_Accommodation.dest_name AND Accommodation_Offers.parent_region = Stops_At_Accommodation.parent_region AND Accommodation_Offers.contact = Stops_At_Accommodation.contact AND Accommodation_Offers.accom_name = Stops_At_Accommodation.accom_name AND Accommodation_Offers.address = Stops_At_Accommodation.address\n" +
                    "    AND PUBLIC_TRANSPORTATION.dest_name = Stops_At_Accommodation.dest_name AND PUBLIC_TRANSPORTATION.parent_region = Stops_At_Accommodation.parent_region AND PUBLIC_TRANSPORTATION.type = Stops_At_Accommodation.type AND PUBLIC_TRANSPORTATION.num = Stops_At_Accommodation.num\n";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int colNum = rsmd.getColumnCount();
            String[] colNames = new String[colNum];
            for (int i = 0; i < colNum; i++) {
                colNames[i] = rsmd.getColumnName(i + 1);
            }
            tableModel.setColumnIdentifiers(colNames);

            while (rs.next()) {
                String[] row = new String[colNum];
                for (int i = 0; i < colNum; i++) {
                    row[i] = rs.getString(i + 1);
                }
                tableModel.addRow(row);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            showExceptionDialog("perform join", e.getMessage());
        }
    }
}


