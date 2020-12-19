package assistant.database;

import assistant.UI.Controllers.BookListController;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import java.sql.*;

public class DatabaseHandler {
    private static DatabaseHandler handler = null;

    private static final String DB_URL = "jdbc:derby:derbyDB;create=true"; // place where database will be create (folder name, if database not exist create one)
    private static Connection connection = null; // store connection between application and database
    private static Statement statement = null; // executing statement (create, delete)
    public String driver = "org.apache.derby.jdbc.EmbeddedDriver";

    private DatabaseHandler() {
        createConnection();
        setupBookTable(); // create book table in db
        setupMemberTable(); // create member table in db
        setupCheckOutTable(); // create check out table
    }

    public static DatabaseHandler getInstance() {
        // creating one handler for all Controllers, in the first calling, new object will be creating and in the next one, existing object will be return
        return (handler == null) ? handler = new DatabaseHandler() : handler;
    }

    void createConnection() {
        try {
            Class.forName(driver).getDeclaredConstructor(); // creating an instance of class
            connection = DriverManager.getConnection(DB_URL); // creating connection
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can not load database", "DatabaseError", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    void setupMemberTable() {
        String TABLE_NAME = "MEMBER";
        try {
            statement = connection.createStatement();

            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists. Ready for go!");
            } else {
                statement.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "id varchar(200) primary key,\n"
                        + "name varchar(200),\n"
                        + "mobile varchar(15),\n"
                        + "email varchar(100)\n"
                        + ")");

                System.out.println("Table " + TABLE_NAME + " was successfully created!");

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " --- setupDatabase");
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
                System.out.println("Table " + TABLE_NAME + " already exists. Ready for go!");
            } else {
                // create book table
                statement.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "id varchar(200) primary key, "
                        + "title varchar(200), "
                        + "author varchar(200), "
                        + "publisher varchar(100),"
                        + "isAvailable boolean default true"
                        + " )");

                System.out.println("Table " + TABLE_NAME + " was successfully created!");

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        }
    }

    void setupCheckOutTable() {
        String TABLE_NAME = "CHECK_OUT";
        try {

            statement = connection.createStatement();
            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet tables = metaData.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists. Ready for go!");
            } else {
                statement.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "bookID varchar(200) primary key,"
                        + "memberID varchar(200),"
                        + "checkOut timestamp default CURRENT_TIMESTAMP,"
                        + "renew_count integer default 0,"
                        + "FOREIGN KEY (bookID) REFERENCES BOOK(id),"
                        + "FOREIGN KEY (memberID) REFERENCES MEMBER(id)"
                        + " )");

                System.out.println("Table " + TABLE_NAME + " was successfully created!");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " --- setupDatabase");
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

    public boolean execAction(String action) {
        // using to doing action in db for example insert data
        try {
            statement = connection.createStatement();
            statement.execute(action);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error occurred", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execAction:dataHandler" + e.getLocalizedMessage());
            return false;
        }
    }

    public boolean deleteBook(BookListController.Book book) {
        try {
            String deleteStatement = "DELETE FROM BOOK WHERE ID = ?";
            PreparedStatement statement = connection.prepareStatement(deleteStatement);
            statement.setString(1, book.getIdProperty());
            int result = statement.executeUpdate();
            if (result == 1) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
