package assistant.database;

import assistant.database.models.*;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;

public class DatabaseHandler {
    private static DatabaseHandler handler = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHandler.class);

    private static final String DB_URL = "jdbc:derby:derbyDB;create=true"; // Declare JDBC Driver -> place where database will be create (folder name, if database not exist create one)
    private static ConnectionSource connection; // store connection between application and database
    private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";

    public static void initDatabase(){
        createConnectionSource();
        createTable();
    }

    public static DatabaseHandler getInstance() {
        // creating one handler for all Controllers, in the first calling, new object will be creating and in the next one, existing object will be return
        return (handler == null) ? handler = new DatabaseHandler() : handler;
    }

    private static void createConnectionSource(){
       //Setting JDBC Driver
        try {
            Class.forName(driver).getDeclaredConstructor();
        } catch (ClassNotFoundException |  NoSuchMethodException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
        }
        System.out.println("Oracle JDBC Driver Registered!");

        try {
            connection = new JdbcConnectionSource(DB_URL); // creating connection (session) with a database.
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
            System.exit(0);
        }
    }

    public static ConnectionSource getConnectionSource(){
        if(connection == null){
            createConnectionSource();
        }
        return connection;
    }

    public static void closeConnectionSource(){
        if(connection!=null){
            try {
                connection.close();
            } catch (IOException e) {
                LOGGER.warn(e.getMessage());
            }
        }
    }

    private static void createTable(){
        try {
            TableUtils.createTableIfNotExists(connection, User.class);
            TableUtils.createTableIfNotExists(connection, Author.class);
            TableUtils.createTableIfNotExists(connection, Book.class);
            TableUtils.createTableIfNotExists(connection, BorrowedBook.class);
            TableUtils.createTableIfNotExists(connection, City.class);
            TableUtils.createTableIfNotExists(connection, Country.class);
            TableUtils.createTableIfNotExists(connection, Library.class);
            TableUtils.createTableIfNotExists(connection, PublishingCompany.class);
            TableUtils.createTableIfNotExists(connection, Category.class);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        }
    }
}
