package assistant.database;

import assistant.UI.Controllers.BookListController.Book;
import assistant.UI.Controllers.MemberListController.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import java.sql.*;

public class db {
    private static db handler = null;

    private static final String DB_URL = "jdbc:derby:derbyDB;create=true"; // place where database will be create (folder name, if database not exist create one)
    private static Connection connection = null; // store connection between application and database
    private static Statement statement = null; // executing statement (create, delete)
    public String driver = "org.apache.derby.jdbc.EmbeddedDriver";

    private db() {
        createConnection();
        setupAuthorsTable();
        setupCategoriesTable();
        setupPublishersTable();
        setupBooksTable();
        setupMembersTable();
        setupBorrowedBooksTable();
    }

    public static db getInstance() {
        // creating one handler for all Controllers, in the first calling, new object will be creating and in the next one, existing object will be return
        return (handler == null) ? handler = new db() : handler;
    }

    void createConnection() {
        try {
            Class.forName(driver).getDeclaredConstructor(); // creating an instance of class
            connection = DriverManager.getConnection(DB_URL); // creating connection (session) with a database.
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can not load database", "DatabaseError", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    void setupMembersTable() {
        String TABLE_NAME = "MEMBER"; // initialize table name
        try {
            statement = connection.createStatement(); // creating a Statement object for sending SQL statements to the database.

            /*
           DatabaseMetaData -> Comprehensive information about the database as a whole. The metadata includes information about the database's tables, its supported SQL grammar,
           its stored procedures, the capabilities of this connection, and so on.
           getMetaData -> Retrieves a DatabaseMetaData object that contains metadata about the database to which this Connection object represents a connection.
             */
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            // check if table already exist
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists. Ready for go!");
            } else {
                // creating  member table
                statement.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "id INTEGER PRIMARY KEY,"
                        + "first_name VARCHAR(200) NOT NULL, "
                        + "last_name VARCHAR(200) NOT NULL, "
                        + "gender VARCHAR (1),"
                        + "mobile VARCHAR (12),"
                        + "street VARCHAR(100),"
                        + "email VARCHAR(100)"
                        + ")");

                System.out.println("Table " + TABLE_NAME + " was successfully created!");

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " --- setupDatabase");
        }
    }

    void setupBooksTable() {
        String TABLE_NAME = "BOOK"; // initialize table name

        try {
            statement = connection.createStatement(); // creating a Statement object for sending SQL statements to the database.

            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            // check if table already exist
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists. Ready for go!");
            } else {
                // creating book table
                statement.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "id INTEGER PRIMARY KEY, "
                        + "isbn10 VARCHAR(13), "
                        + "isbn13 VARCHAR(17), "
                        + "title VARCHAR(200), "
                        + "addition_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                        + "author_id INTEGER, "
                        + "category_id INTEGER, "
                        + "publisher_id INTEGER,"
                        + "isAvailable BOOLEAN DEFAULT true,"
                        + "FOREIGN KEY (author_id) REFERENCES AUTHOR(id),"
                        + "FOREIGN KEY (category_id) REFERENCES CATEGORY(id),"
                        + "FOREIGN KEY (publisher_id) REFERENCES PUBLISHER(id)"
                        + " )"
                );

                System.out.println("Table " + TABLE_NAME + " was successfully created!");

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        }
    }

    void setupCategoriesTable() {
        String TABLE_NAME = "CATEGORY"; // initialize table name
        try {
            statement = connection.createStatement(); // creating a Statement object for sending SQL statements to the database.
            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet tables = metaData.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            // check if table already exist
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists. Ready for go!");
            } else {
                // creating check out table
                statement.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "id INTEGER PRIMARY KEY,"
                        + "category_name VARCHAR(200) "
                        + " )"
                );

                System.out.println("Table " + TABLE_NAME + " was successfully created!");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " --- setupDatabase");
        }
    }

    void setupPublishersTable() {
        String TABLE_NAME = "PUBLISHER"; // initialize table name
        try {
            statement = connection.createStatement(); // creating a Statement object for sending SQL statements to the database.
            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet tables = metaData.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            // check if table already exist
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists. Ready for go!");
            } else {
                // creating check out table
                statement.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "id INTEGER PRIMARY KEY,"
                        + "publishing_company_name VARCHAR(200) "
                        + " )"
                );

                System.out.println("Table " + TABLE_NAME + " was successfully created!");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " --- setupDatabase");
        }
    }

    void setupAuthorsTable() {
        String TABLE_NAME = "AUTHOR"; // initialize table name
        try {

            statement = connection.createStatement(); // creating a Statement object for sending SQL statements to the database.
            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet tables = metaData.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            // check if table already exist
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists. Ready for go!");
            } else {
                // creating check out table
                statement.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "id INTEGER PRIMARY KEY,"
                        + "first_name VARCHAR(200) NOT NULL, "
                        + "last_name VARCHAR(200) NOT NULL "
                        + " )"
                );

                System.out.println("Table " + TABLE_NAME + " was successfully created!");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " --- setupDatabase");
        }
    }

    void setupBorrowedBooksTable() {
        String TABLE_NAME = "BORROWED_BOOKS"; // initialize table name
        try {

            statement = connection.createStatement(); // creating a Statement object for sending SQL statements to the database.
            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet tables = metaData.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            // check if table already exist
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists. Ready for go!");
            } else {
                // creating check out table
                statement.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "book_id INTEGER PRIMARY KEY,"
                        + "member_id INTEGER,"
                        + "borrowTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP," // TODO: change name to checkOutTime
                        + "renew_count INTEGER DEFAULT 0,"
                        + "FOREIGN KEY (book_id) REFERENCES BOOK(id),"
                        + "FOREIGN KEY (member_id) REFERENCES MEMBER(id)"
                        + " )" +
                        "");

                System.out.println("Table " + TABLE_NAME + " was successfully created!");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " --- setupDatabase");
        }
    }

    public ResultSet execQuery(String query) {
        // method using to get data from db
        ResultSet resultSet;
        try {
            statement = connection.createStatement(); // creating a Statement object for sending SQL statements to the database.
            resultSet = statement.executeQuery(query); // executing query
        } catch (SQLException e) {
            System.out.println("Exception at execQuery:dataHandler" + e.getLocalizedMessage());
            return null;
        }
        return resultSet;
    }

    public boolean execAction(String action) {
        // method using to doing other action in db (insert data, update)
        try {
            statement = connection.createStatement(); // creating a Statement object for sending SQL statements to the database.
            statement.execute(action);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error:" + e.getMessage(), "Error occurred", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execAction:dataHandler" + e.getLocalizedMessage());
            return false;
        }
    }

    public boolean deleteBook(Book book) {
        // method using to delete record from db
        try {
            String deleteStatement = "DELETE FROM BOOK WHERE ID = ?"; // selecting an item to delete
            PreparedStatement statement = connection.prepareStatement(deleteStatement); // deletion operation
            statement.setString(1, book.getIdProperty());
            int result = statement.executeUpdate();
            if (result == 1) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean deleteMember(Member member) {
        try {
            String deleteStatement = "DELETE FROM MEMBER WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(deleteStatement);
            statement.setString(1, member.getIdProperty());
            int res = statement.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isInMagazine(Book book) {
        // method using to check if the book is lent to someone
        try {
            String checkStatement = "SELECT COUNT(*) FROM CHECK_OUT WHERE bookID = ?"; // chek if item is in the check out table
            // prepareStatement -> Create a PreparedStatement object for sending parameterized SQL statements to the database.
            PreparedStatement statement = connection.prepareStatement(checkStatement);
            statement.setString(1, book.getIdProperty()); // replacement of an question mark by valuer returned by book.getIdProperty()
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1); // selecting int on 1st index (indexes starts from 1 not form 0)
                return count == 0; // if count != 0 it means that book is lend to someone ant can't be deleted
            }
        } catch (SQLException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean isMemberHasAnyBooks(Member member) {
        try {
            String checkStatement = "SELECT COUNT(*) FROM CHECK_OUT WHERE memberID=?";
            PreparedStatement statement = connection.prepareStatement(checkStatement);
            statement.setString(1, member.getIdProperty());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return (count > 0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean updateBook(Book book) {
        try {
            String update = "UPDATE BOOK SET TITLE=?, AUTHOR=?, PUBLISHER=? WHERE ID=?";
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, book.getTitleProperty());
            statement.setString(2, book.getAuthorProperty());
            statement.setString(3, book.getPublisherProperty());
            statement.setString(4, book.getIdProperty());
            int result = statement.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public boolean updateMember(Member member) {
        try {
            String update = "UPDATE MEMBER SET NAME=?, EMAIL=?, MOBILE=? WHERE ID=?";
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, member.getNameProperty());
            statement.setString(2, member.getEmailProperty());
            statement.setString(3, member.getMobileProperty());
            statement.setString(4, member.getIdProperty());
            int res = statement.executeUpdate();
            return (res > 0);
        } catch (SQLException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ObservableList<PieChart.Data> getBookStatistics() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        try {
            String bookInMagazine = "SELECT COUNT (*) FROM BOOK WHERE isAvailable = true";
            String bookCheckedOut = "SELECT COUNT (*) FROM BOOK WHERE isAvailable = false";

            ResultSet resultSet = execQuery(bookInMagazine);
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                data.add(new PieChart.Data("Books in magazine (" + count + ")", count));
            }

            resultSet = execQuery(bookCheckedOut);
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                data.add(new PieChart.Data("Issued books (" + count + ")", count));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}
