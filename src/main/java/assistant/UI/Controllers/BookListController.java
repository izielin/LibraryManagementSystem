package assistant.UI.Controllers;

import assistant.alert.AlertMaker;
import assistant.database.DatabaseHandler;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static assistant.alert.AlertMaker.alertConfirm;
import static assistant.alert.AlertMaker.showSimpleAlert;

public class BookListController implements Initializable {

    ObservableList<Book> list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, String> idColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> publisherColumn;
    @FXML
    private TableColumn<Book, Boolean> availabilityColumn;

    @FXML
    private TextField selectedBookID;
    @FXML
    private TextField selectedBookPublisher;
    @FXML
    private TextField selectedBookAuthor;
    @FXML
    private TextField selectedBookTitle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initColumn();
        loadData();
    }

    private void initColumn() {
        titleColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().titleProperty);
        idColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().idProperty);
        authorColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().authorProperty);
        publisherColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().publisherProperty);
        availabilityColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().availabilityProperty);
    }

    private void loadData() {
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String query = "SELECT * FROM BOOK";
        ResultSet resultSet = handler.execQuery(query);
        try {
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String id = resultSet.getString("id");
                String publisher = resultSet.getString("publisher");
                Boolean avail = resultSet.getBoolean("isAvailable");

                // creating book object contains all data from db which can be added to the ObservableList
                Book book = new Book(title, id, author, publisher, avail);
                list.add(book);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableView.getItems().setAll(list); // associating list containing all books records from db with table
    }

    public void deleteSelectedBook() {

        if (!selectedBookID.getText().isEmpty()) {
            String bookID = selectedBookID.getText();
            String query = "SELECT * FROM BOOK WHERE id = '" + bookID + "'";
            Optional<ButtonType> response = alertConfirm("Confirm delete operation",
                    "Are you sure you want to delete the '" + selectedBookTitle.getText() + "' book?",
                    "Are you sure you want to return the book?");
            if (response.orElse(null) == ButtonType.OK) {
                ResultSet resultSet = DatabaseHandler.getInstance().execQuery(query);
                try {
                    while (resultSet.next()) {
                        Book book = new Book(resultSet.getString("title"), resultSet.getString("id"),
                                resultSet.getString("author"), resultSet.getString("publisher"),
                                resultSet.getBoolean("isAvailable"));
                        boolean result = DatabaseHandler.getInstance().deleteBook(book);
                        if (result) {
                            showSimpleAlert("information", "Book deleted", "", "Book '"
                                    + selectedBookTitle.getText() + "' was successfully deleted");
                            selectedBookTitle.clear();
                            selectedBookID.clear();
                            selectedBookAuthor.clear();
                            selectedBookPublisher.clear();
                        } else {
                            showSimpleAlert("error", "Failed", "", "Operation ended unsuccessfully");
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                showSimpleAlert("information", "Cancelled", "", "Operation was cancelled");
            }
        } else {
            showSimpleAlert("error", "No book selected", "No data to load", "Please select row with book to delete");
        }
    }

    public void handleRowData(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            Book rowData = tableView.getSelectionModel().getSelectedItem();
            if (rowData == null) {
                showSimpleAlert("error", "No book selected", "No data to load", "Please select row with book data");
            } else {
                selectedBookID.setText(rowData.getIdProperty());
                selectedBookTitle.setText(rowData.getTitleProperty());
                selectedBookAuthor.setText(rowData.getAuthorProperty());
                selectedBookPublisher.setText(rowData.getPublisherProperty());
            }
        }
    }


    public static class Book {
        private final SimpleStringProperty titleProperty;
        private final SimpleStringProperty idProperty;
        private final SimpleStringProperty authorProperty;
        private final SimpleStringProperty publisherProperty;
        private final SimpleBooleanProperty availabilityProperty;

        Book(String title, String id, String autor, String publisher, Boolean availability) {
            this.titleProperty = new SimpleStringProperty(title);
            this.idProperty = new SimpleStringProperty(id);
            this.authorProperty = new SimpleStringProperty(autor);
            this.publisherProperty = new SimpleStringProperty(publisher);
            this.availabilityProperty = new SimpleBooleanProperty(availability);
        }

        public String getTitleProperty() {
            return titleProperty.get();
        }

        public String getIdProperty() {
            return idProperty.get();
        }

        public String getAuthorProperty() {
            return authorProperty.get();
        }

        public String getPublisherProperty() {
            return publisherProperty.get();
        }

        public boolean isAvailabilityProperty() {
            return availabilityProperty.get();
        }

    }

}
