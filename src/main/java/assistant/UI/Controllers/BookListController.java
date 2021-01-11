package assistant.UI.Controllers;

import assistant.FXModels.AuthorFXModel;
import assistant.FXModels.BookFXModel;
import assistant.FXModels.CategoryFXModel;
import assistant.FXModels.LibraryFXModel;
import assistant.UI.Controllers.AddControllers.AddBookController;
import assistant.Utils.Converters;
import assistant.Utils.exceptions.ApplicationException;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.Book;
import assistant.database.models.BorrowedBook;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static assistant.Utils.ProjectTools.getResourceBundle;
import static assistant.alert.AlertMaker.showJFXButton;

public class BookListController implements Initializable {

    @FXML
    private StackPane rootPane;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private TableView<BookFXModel> tableView;

    @FXML
    private TableColumn<BookFXModel, CategoryFXModel> categoryColumn;

    @FXML
    private TableColumn<BookFXModel, Integer> idColumn;

    @FXML
    private TableColumn<BookFXModel, String> titleColumn;

    @FXML
    private TableColumn<BookFXModel, AuthorFXModel> authorColumn;

    @FXML
    private TableColumn<BookFXModel, String> isbn13;

    @FXML
    private TableColumn<BookFXModel, LibraryFXModel> libraryColumn;

    @FXML
    private TableColumn<BookFXModel, String> cityColumn;

    @FXML
    private TableColumn<BookFXModel, String> availabilityColumn;

    @FXML
    private TableColumn<BookFXModel, BookFXModel> deleteColumn;

    @FXML
    private TableColumn<BookFXModel, BookFXModel> editColumn;

    public static final String DELETE_ICON = "/icons/times.png";
    public static final String EDIT_ICON = "/icons/edit.png";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initColumn();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    private void initColumn() throws ApplicationException {
        tableView.setItems(loadData());
        categoryColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().categoryFXProperty());
        idColumn.setCellValueFactory(cellDataFeatures -> new SimpleIntegerProperty(cellDataFeatures.getValue().getId()).asObject());
        titleColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().titleProperty());
        authorColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().authorFXProperty());
        isbn13.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().isbn13Property());
        libraryColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().libraryFXProperty());
        cityColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().getLibraryFX().getCityFX().nameProperty());
        availabilityColumn.setCellValueFactory(cellData -> (cellData.getValue().isAvailability()) ? new SimpleStringProperty("Dostępna") : new SimpleStringProperty("Wypożyczona"));
        deleteColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        editColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));

        deleteColumn.setCellFactory(param -> new TableCell<BookFXModel, BookFXModel>() {
            final Button button = createButton(DELETE_ICON);

            @Override
            protected void updateItem(BookFXModel item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setGraphic(button);
                    button.setOnAction(event -> executeDeleteAction(item));
                } else {
                    setGraphic(null);
                }
            }
        });

        editColumn.setCellFactory(param -> new TableCell<BookFXModel, BookFXModel>() {
            final Button button = createButton(EDIT_ICON);

            @Override
            protected void updateItem(BookFXModel book, boolean empty) {
                super.updateItem(book, empty);
                if (!empty) {
                    setGraphic(button);
                    button.setOnAction(event -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addViews/AddBook.fxml"), getResourceBundle());
                            Parent parent = loader.load();
                            AddBookController controller = loader.getController();
                            controller.inflateUI(book);
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.show();

                            stage.setOnHidden((e) -> {
                                try {
                                    tableView.setItems(loadData());
                                } catch (ApplicationException applicationException) {
                                    applicationException.printStackTrace();
                                }
                            }); //refresh table
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    setGraphic(null);
                }
            }
        });
    }

    public Button createButton(String path) {
        JFXButton button = new JFXButton();
        Image image = new Image(path);
        ImageView imageView = new ImageView(image);
        button.setGraphic(imageView);
        return button;
    }

    public void executeDeleteAction(BookFXModel book) {
        DataAccessObject dao = new DataAccessObject();
        JFXButton yesButton = new JFXButton("YES");
        JFXButton cancelButton = new JFXButton("Cancel");
        yesButton.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            try {
                //checking if the book being deleted is borrowed
                List<BorrowedBook> list = dao.findByColumnName(BorrowedBook.class, "BOOK_ID", book.getId());
                if (list.isEmpty()) {
                    dao.deleteById(Book.class, book.getId());
                    tableView.setItems(loadData());
                } else {
                    showJFXButton(rootPane, mainAnchorPane, new ArrayList<>(), "Error", "Book cannot be deleted. Cause: is borrowed by someone!");
                }
            } catch (ApplicationException | SQLException e) {
                e.printStackTrace();
            }
        });
        showJFXButton(rootPane, mainAnchorPane, Arrays.asList(yesButton, cancelButton), "Confirm delete operation",
                "Are you sure you want to delete selected item?");
    }


    private ObservableList<BookFXModel> loadData() throws ApplicationException {
        ObservableList<BookFXModel> observableArrayList = FXCollections.observableArrayList();
        DataAccessObject dao = new DataAccessObject();
        List<Book> books = dao.queryForAll(Book.class);
        observableArrayList.clear();
        books.forEach(book -> {
            if (book.getLibrary().getId() == LoginController.currentlyLoggedUser.getLibrary().getId()) {
                BookFXModel bookFXModel = Converters.convertToBookFx(book);
                observableArrayList.add(bookFXModel);
            }
        });
        return observableArrayList;
    }
}