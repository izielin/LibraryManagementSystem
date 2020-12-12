package assistant.database;

import javax.swing.*;
import java.sql.*;

public class DatabaseHandler {
    private static DatabaseHandler handler;

    private static final String DB_URL = "jdbc:derby:derbyDB;create=true"; // place where database will be create (folder name, if database not exist create one)
    private static Connection connection = null; // store connection between application and database
    private static Statement statement = null; // executing statement (create, delete)
    public String driver = "org.apache.derby.jdbc.EmbeddedDriver";

    public DatabaseHandler() {
        createConnection();
        setupBookTable();
    }

    void createConnection() {
        try {
            Class.forName(driver).getDeclaredConstructor(); // creating an instance of class
            connection = DriverManager.getConnection(DB_URL); // creating connection
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setupBookTable() {
        String TABLE_NAME = "BOOK";

        try {
            statement = connection.createStatement();

            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            // check if table already exist
            if (tables.next()) {
                System.out.println("Table" + TABLE_NAME + " already exists. Ready for go!");
            } else {
                // create book table
                statement.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "id varchar(200) primary key, "
                        + "title varchar(200), "
                        + "author varchar(200), "
                        + "publisher varchar(100),"
                        + "isAvail boolean default true"
                        + " )");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        }
    }

    public ResultSet execQuery(String query) {
        // using to get data from db
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Exception at execQuery:dataHandler" + e.getLocalizedMessage());
            return null;
        }
        return resultSet;
    }

    public boolean execAction(String action){
        // using to doing action in db for example insert data
        try{
            statement = connection.createStatement();
            statement.execute(action);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error occurred", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execAction:dataHandler" + e.getLocalizedMessage());
            return false;
        }
    }

}
