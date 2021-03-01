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
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHandler.class);

    private static final String DB_URL = "jdbc:sqlite:SQLiteDatabase.db"; // Declare JDBC Driver -> place where database will be create (folder name, if database not exist create one)
    private static ConnectionSource connection; // store connection between application and database

    public static void initDatabase(boolean rebuildDatabase){
        createConnectionSource();
        if(rebuildDatabase){
        dropTable();
        createTable();
        }
    }

    private static void createConnectionSource(){
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
            TableUtils.createTableIfNotExists(connection, Library.class);
            TableUtils.createTableIfNotExists(connection, PublishingCompany.class);
            TableUtils.createTableIfNotExists(connection, Category.class);
            TableUtils.createTableIfNotExists(connection, Message.class);
            TableUtils.createTableIfNotExists(connection, Report.class);
            TableUtils.createTableIfNotExists(connection, Watched.class);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    private  static  void  dropTable(){
        try {
            TableUtils.dropTable(connection, User.class, true);
            TableUtils.dropTable(connection, Author.class, true);
            TableUtils.dropTable(connection, Book.class, true);
            TableUtils.dropTable(connection, BorrowedBook.class, true);
            TableUtils.dropTable(connection, City.class, true);
            TableUtils.dropTable(connection, Library.class, true);
            TableUtils.dropTable(connection, PublishingCompany.class, true);
            TableUtils.dropTable(connection, Category.class, true);
            TableUtils.dropTable(connection, Message.class, true);
            TableUtils.dropTable(connection, Report.class, true);
            TableUtils.dropTable(connection, Watched.class, true);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        }
    }
}
