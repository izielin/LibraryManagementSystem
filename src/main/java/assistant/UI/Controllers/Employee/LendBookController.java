package assistant.UI.Controllers.Employee;

import assistant.FXModels.BookFXModel;
import assistant.FXModels.UserFXModel;
import assistant.UI.Controllers.LoginController;
import assistant.Utils.Converters;
import assistant.Utils.ApplicationException;
import assistant.Utils.CreateSets;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.controlsfx.control.textfield.TextFields;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static assistant.Utils.AlertMaker.showJFXButton;
import static assistant.Utils.AlertMaker.showTableDialog;
import static assistant.Utils.ProjectTools.loadImage;

public class LendBookController implements Initializable {


    public Circle imageCircle;
    public JFXButton addBook;
    public JFXButton clear;
    @FXML
    private StackPane rootPane;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private JFXTextField bookTitleInput;

    @FXML
    private JFXTextField memberIDInput;

    @FXML
    private JFXTextField userFirstNameInput;

    @FXML
    private JFXTextField userLastNameInput;

    @FXML
    private HBox submissionDataContainer;

    @FXML
    private VBox userInfo;

    @FXML
    private Text memberNameHolder;

    @FXML
    private Text memberLastNameHolder;

    @FXML
    private Text memberAddressHolder;

    @FXML
    private Text memberContactHolder;

    @FXML
    private Text memberIdHolder;

    @FXML
    private JFXTextField searchInput;

    @FXML
    private TableView<BookFXModel> tableView;

    @FXML
    private TableColumn<BookFXModel, String> isbnColumn;

    @FXML
    private TableColumn<BookFXModel, String> titleColumn;
    List<Node> nodes = new ArrayList<>();
    List<BorrowedBook> bookToBorrow = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // filling dialog associated with autocomplete field
            DataAccessObject dao = new DataAccessObject();
            TextFields.bindAutoCompletion(bookTitleInput, CreateSets.createTitleSet("SELECT DISTINCT TITLE FROM BOOKS"));

            // filling tableView
            initColumn();

            // adding listener detecting changing focus
            addBook.setOnAction(event -> {
                try {
                    List<Book> list = dao.searchDuplicateBooksTitles(bookTitleInput.getText(), LoginController.currentlyLoggedUser.getLibraryID());
                    if (list.size() == 1) { // if return 1 record - set data of returned book
//
                        Book selectedBook = dao.findById(Book.class, list.get(0).getId());
                        nodes.add(generateBookCell(selectedBook));
                        operation(selectedBook.getId());
                        System.out.println(nodes.size());
                        System.out.println(bookToBorrow.size());
                        generateBorrowedBooksList();
                    } else if (list.size() == 0) { // if return 0 - the book with the given title does not exist in the database, return warning
                        showJFXButton(rootPane, mainAnchorPane, new ArrayList<>(), "Wrong Title", "The book with the given title was not found in the system");
                        bookTitleInput.setText("");
                    } else { // if return >1 - show list with more detailed information about returned books
                        tableForDuplicatedBook(list);
                    }
                } catch (ApplicationException | SQLException | IOException | NoSuchFieldException applicationException) {
                    applicationException.printStackTrace();
                }
            });


            userLastNameInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!userFirstNameInput.getText().isEmpty() && !userLastNameInput.getText().isEmpty() && memberIdHolder.getText().isEmpty()
                        || !userFirstNameInput.getText().equals(memberNameHolder.getText()) && !userLastNameInput.getText().equals(memberLastNameHolder.getText())) {
                    try {
                        userFirstNameInput.getParent().requestFocus();
                        List<User> list = dao.searchDuplicatedNames(userFirstNameInput.getText(), userLastNameInput.getText());
                        if (list.size() == 1) {
                            User user = dao.findById(User.class, list.get(0).getId());
                            Image image = loadImage(user.getProfilePicture());
                            imageCircle.setFill(new ImagePattern(image));
                            memberIdHolder.setText(Integer.toString(user.getId()));
                            memberNameHolder.setText(user.getFirstName() + " " + user.getLastName());
                            memberContactHolder.setText(user.getMobile() + " " + user.getEmail());
                            memberAddressHolder.setText(user.getStreet() + " ," + user.getZipCode());
                            userInfo.setOpacity(1);
                            bookTitleInput.setDisable(false);
                            addBook.setDisable(false);
                            clear.setDisable(false);
                        } else if (list.size() == 0) {
                            showJFXButton(rootPane, mainAnchorPane, new ArrayList<>(), "Wrong Name Data", "The user with the given name data was not found in the system");
                            userFirstNameInput.setText("");
                            userLastNameInput.setText("");
                        } else {
                            tableForDuplicatedUser(list);
                        }
                    } catch (ApplicationException | NullPointerException | SQLException | IOException e) {
                        e.printStackTrace();
                    }
                }
            });

//


        } catch (ApplicationException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void generateBorrowedBooksList() {
        GridPane table = new GridPane();
        table.setHgap(20);
        table.setVgap(20);
        int rows;
        if (nodes.size() < 2)
            rows = 1;
        else
            rows = (int) (Math.round(nodes.size() / 2.0));
        // grid pane
        int k = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 2; j++) {
                if (k < nodes.size())
                    table.add(nodes.get(k++), j, i);
            }
        }
        submissionDataContainer.getChildren().add(table);
    }


    private Node generateBookCell(Book book) throws ApplicationException, IOException {
        DataAccessObject dao = new DataAccessObject();
        VBox mainVbox = new VBox();
        mainVbox.setStyle("-fx-border-color: #fdc12a");

        VBox titleVBox = new VBox();
        titleVBox.setAlignment(Pos.CENTER);
        titleVBox.setPadding(new Insets(15, 15, 15, 15));

        Label titleHolder = new Label();
        titleHolder.setText(book.getTitle());
        titleHolder.setStyle("-fx-text-fill: black");
        titleHolder.setStyle("-fx-font-weight: bold;");
        titleHolder.setStyle("-fx-font-size: 14;");
        titleHolder.setWrapText(true);

        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.getStyleClass().add("card_separator");
        VBox contentVbox = new VBox();
        contentVbox.setMinWidth(200);
        contentVbox.setStyle("-fx-background-color: #FFC107");
        contentVbox.setPadding(new Insets(15, 10, 10, 5));
        contentVbox.setSpacing(10);

        Label authorHolder = new Label();
        Label categoryHolder = new Label();
        Label ISBNHolder = new Label();

        authorHolder.setText("Author: " + dao.findById(Author.class, book.getAuthor()).getFullName());
        categoryHolder.setText("Category: " + dao.findById(Category.class, book.getCategory()).getName());
        ISBNHolder.setText("ISBN: " + book.getIsbn13());
        contentVbox.getChildren().addAll(authorHolder, categoryHolder, ISBNHolder);
        mainVbox.getChildren().addAll(titleHolder, separator, contentVbox);
        return mainVbox;

    }

    private void tableForDuplicatedBook(List<Book> list) {
        DataAccessObject dao = new DataAccessObject();
        ObservableList<BookFXModel> observableList = FXCollections.observableArrayList();
        list.forEach(book -> observableList.add(Converters.convertToBookFx(book)));

        TableView<BookFXModel> duplicates = new TableView<>();
        TableColumn<BookFXModel, String> titleColumn = new TableColumn<>();
        TableColumn<BookFXModel, String> isbnColumn = new TableColumn<>();

        duplicates.setItems(observableList);

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn13"));

        duplicates.getColumns().addAll(titleColumn, isbnColumn);

        duplicates.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        JFXButton choiceButton = new JFXButton("Choose selected book");

        duplicates.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            choiceButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                try {
                    Book selectedBook = dao.findById(Book.class, newSelection.getId());
                    nodes.add(generateBookCell(selectedBook));
                    operation(selectedBook.getId());

                    generateBorrowedBooksList();
                } catch (ApplicationException | IOException | NoSuchFieldException | SQLException e) {
                    e.printStackTrace();
                }
            });
        });
        showTableDialog(rootPane, mainAnchorPane, Arrays.asList(choiceButton), "Ambiguous data was provided",
                "More than one object with the mentioned values exists in the database. \n" +
                        "Choose a specific object from the list below, and then confirm with the button 'Choose selected book'", duplicates);
    }

    private void tableForDuplicatedUser(List<User> list) {
        ObservableList<UserFXModel> observableList = FXCollections.observableArrayList();
        list.forEach(user -> observableList.add(Converters.convertToUserFx(user)));

        TableView<UserFXModel> duplicates = getTableView(observableList);

        duplicates.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        JFXButton choiceButton = new JFXButton("Choose selected person");
        duplicates.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            choiceButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                memberIdHolder.setText(Integer.toString(newSelection.getId()));
                memberNameHolder.setText(newSelection.getFirstName());
                memberLastNameHolder.setText(newSelection.getLastName());
                memberContactHolder.setText(newSelection.getMobile() + ", " + newSelection.getEmail());
                memberAddressHolder.setText(newSelection.getStreet() + ", " + newSelection.getZipCode());
                userInfo.setOpacity(1);
            });
        });
        showTableDialog(rootPane, mainAnchorPane, Arrays.asList(choiceButton), "Ambiguous data was provided",
                "More than one object with the mentioned values exists in the database. \n" +
                        "Choose a specific object from the list below, and then confirm with the button 'Choose selected person'", duplicates);

    }

    public TableView<UserFXModel> getTableView(ObservableList<UserFXModel> observableList) {
        TableView<UserFXModel> duplicates = new TableView<>();
        TableColumn<UserFXModel, String> firstName = new TableColumn<>();
        TableColumn<UserFXModel, String> lastName = new TableColumn<>();
        TableColumn<UserFXModel, String> mobile = new TableColumn<>();
        TableColumn<UserFXModel, String> street = new TableColumn<>();

        duplicates.setItems(observableList);

        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        street.setCellValueFactory(new PropertyValueFactory<>("street"));

        duplicates.getColumns().addAll(firstName, lastName, mobile, street);
        return duplicates;
    }

    private void initColumn() throws ApplicationException, SQLException {
        tableView.setItems(loadData());
        titleColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().titleProperty());
        isbnColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().isbn13Property());
    }

    private ObservableList<BookFXModel> loadData() throws ApplicationException, SQLException {
        ObservableList<BookFXModel> observableArrayList = FXCollections.observableArrayList();
        DataAccessObject dao = new DataAccessObject();
        List<String[]> books = dao.executeRawQuery(Book.class, "SELECT ID, TITLE FROM BOOKS WHERE AVAILABILITY = 1 " +
                "AND LIBRARY_ID =" + LoginController.currentlyLoggedUser.getLibraryID() + " ORDER BY TITLE ASC ");
        observableArrayList.clear();
        books.forEach(bookID -> {
            try {
                Book book = dao.findById(Book.class, Integer.parseInt(bookID[0]));
                if (book.getLibrary() == LoginController.currentlyLoggedUser.getLibraryID()) {
                    BookFXModel bookFXModel = Converters.convertToBookFx(book);
                    observableArrayList.add(bookFXModel);
                }
            } catch (ApplicationException e) {
                e.printStackTrace();
            }
        });

        FilteredList<BookFXModel> filteredData = new FilteredList<>(observableArrayList, b -> true);
        searchInput.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(book -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    return book.getTitle().toLowerCase().contains(lowerCaseFilter);
                }));
        return new SortedList<>((filteredData));
    }

    @FXML
    private void handleRowData(MouseEvent mouseEvent) throws ApplicationException, IOException, NoSuchFieldException, SQLException {
        DataAccessObject dao = new DataAccessObject();

        if (mouseEvent.getClickCount() == 2) { // checking the number of mouse clicks on a single row
            BookFXModel rowData = tableView.getSelectionModel().getSelectedItem(); // creating User object from data in selected row
            if (rowData == null) { // check if selected row is not null
                showJFXButton(rootPane, mainAnchorPane, new ArrayList<>(), "No book selected", "Please select row with book data");
            } else {
                Book selectedBook = dao.findById(Book.class, rowData.getId());
                nodes.add(generateBookCell(selectedBook));
                operation(selectedBook.getId());

                generateBorrowedBooksList();
            }
        }
    }

    @FXML
    private void executeCheckOutOperation() {
        DataAccessObject dao = new DataAccessObject();

        JFXButton yesButton = new JFXButton("YES");
        yesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            bookToBorrow.forEach(borrowedBook -> {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            dao.createOrUpdate(borrowedBook);
                            dao.updateItem(Book.class, borrowedBook.getBookID(), "AVAILABILITY", "false");
                        } catch (ApplicationException | SQLException | NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                    }
                });
            });
            clearCheckOutEntries();
            try {
                initColumn();
            } catch (ApplicationException | SQLException e) {
                e.printStackTrace();
            }
            showJFXButton(rootPane, mainAnchorPane, new ArrayList<>(), " Success", "Operation ended successfully");
        });

        JFXButton cancelButton = new JFXButton("Cancel");
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) ->
                showJFXButton(rootPane, mainAnchorPane, new ArrayList<>(), "Failed", "Operation ended unsuccessfully"));

        showJFXButton(rootPane, mainAnchorPane, Arrays.asList(yesButton, cancelButton), "Confirm chek book out operation",
                "Are you sure you want to lend " + bookToBorrow.size() + " books to " + memberNameHolder.getText() + " " + memberLastNameHolder.getText() + "?");
    }

    private void operation(int id) throws ApplicationException, NoSuchFieldException, SQLException {
        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setBookID(id);
        borrowedBook.setUserID(Integer.parseInt(memberIdHolder.getText()));
        borrowedBook.setBorrowTime(LocalDate.now().toString());
        borrowedBook.setLibraryID(LoginController.currentlyLoggedUser.getLibraryID());
        bookToBorrow.add(borrowedBook);

    }

    private void clearCheckOutEntries() {
        userFirstNameInput.clear();
        userLastNameInput.clear();
        bookTitleInput.clear();
        bookTitleInput.setDisable(true);
        addBook.setDisable(true);
        clear.setDisable(true);

        submissionDataContainer.getChildren().clear();
        List<VBox> boxes = List.of(userInfo);
        boxes.forEach(vBox -> {
            for (Node node : vBox.getChildren()) {
                if (node instanceof Text) {
                    ((Text) node).setText("");
                }
            }
        });
    }

}
