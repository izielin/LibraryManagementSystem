package assistant.UI.Controllers;

import assistant.FXModels.BookFXModel;
import assistant.FXModels.UserFXModel;
import assistant.Utils.Converters;
import assistant.Utils.exceptions.ApplicationException;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.Book;
import assistant.database.models.BorrowedBook;
import assistant.database.models.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static assistant.alert.AlertMaker.showJFXButton;
import static assistant.alert.AlertMaker.showTableDialog;

public class LendBookController implements Initializable {


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
    private VBox bookInfo;

    @FXML
    private Text bookTitleHolder;

    @FXML
    private Text bookAuthorHolder;

    @FXML
    private Text bookPublisherHolder;

    @FXML
    private Text bookIDHolder;

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

    List<String[]> titleList;
    private final List<String> possibleTitleItems = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // filling dialog associated with autocomplete field
            DataAccessObject dao = new DataAccessObject();
            String query = "SELECT DISTINCT TITLE FROM BOOKS";
            titleList = dao.executeRawQuery(Book.class, query);
            titleList.forEach(item -> possibleTitleItems.add(item[0]));
            Set<String> possibleTitleSet = new HashSet<>(possibleTitleItems);
            TextFields.bindAutoCompletion(bookTitleInput, possibleTitleSet);

            // filling tableView
            initColumn();

            // adding listener detecting changing focus
            bookTitleInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!bookTitleInput.getText().equals("") && bookIDHolder.getText().equals("") || !bookTitleInput.getText().equals(bookTitleHolder.getText())) {
                    try {
                        bookTitleInput.getParent().requestFocus();
                        List<Book> list = dao.searchDuplicateBooksTitles(bookTitleInput.getText(), LoginController.currentlyLoggedUser.getId()); // create list of books with the same id
                        if (list.size() == 1) { // if return 1 record - set data of returned book
                            Book book = dao.findById(Book.class, list.get(0).getId());
                            bookIDHolder.setText(Integer.toString(book.getId()));
                            bookTitleHolder.setText(book.getTitle());
                            bookAuthorHolder.setText(book.getAuthor().getFistName() + " " + book.getAuthor().getMiddleName() + " " + book.getAuthor().getLastName());
                            bookPublisherHolder.setText(book.getPublishingCompany().getName());
                            bookInfo.setOpacity(1);
                        } else if (list.size() == 0) { // if return 0 - the book with the given title does not exist in the database, return warning
                            showJFXButton(rootPane, mainAnchorPane, new ArrayList<>(), "Wrong Title", "The book with the given title was not found in the system");
                            bookTitleInput.setText("");
                        } else { // if return >1 - show list with more detailed information about returned books
                            tableForDuplicatedBook(list);
                        }
                    } catch (ApplicationException | NullPointerException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            });

            userLastNameInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!userFirstNameInput.getText().isEmpty() && !userLastNameInput.getText().isEmpty() && memberIdHolder.getText().isEmpty()
                        || !userFirstNameInput.getText().equals(memberNameHolder.getText()) && !userLastNameInput.getText().equals(memberLastNameHolder.getText())) {
                    try {
                        userFirstNameInput.getParent().requestFocus();
                        List<User> list = dao.searchDuplicatedNames(userFirstNameInput.getText(), userLastNameInput.getText());
                        if (list.size() == 1) {
                            User user = dao.findById(User.class, Integer.parseInt(memberIDInput.getText()));
                            memberNameHolder.setText(user.getFirstName() + " " + user.getLastName());
                            memberContactHolder.setText(user.getMobile() + " " + user.getEmail());
                            memberAddressHolder.setText(user.getStreet() + " ," + user.getZipCode() + " " + user.getCity().getName());
                            userInfo.setOpacity(1);
                        } else if (list.size() == 0) {
                            showJFXButton(rootPane, mainAnchorPane, new ArrayList<>(), "Wrong Name Data", "The user with the given name data was not found in the system");
                            userFirstNameInput.setText("");
                            userLastNameInput.setText("");
                        } else {
                            tableForDuplicatedUser(list);
                        }
                    } catch (ApplicationException | NullPointerException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            });


            List<JFXTextField> textFields = List.of(bookTitleInput, userLastNameInput, userFirstNameInput);
            textFields.forEach(t -> {
                // listener clears fields showing details of selected records, if input field is empty
                t.textProperty().addListener((observable, oldValue, newValue) -> {
                    VBox vBox;
                    if (bookTitleInput.getText().isEmpty())
                        vBox = bookInfo;
                    else
                        vBox = userInfo;

                    for (Node node : vBox.getChildren()) {
                        if (node instanceof Text) {
                            ((Text) node).setText("");
                        }
                    }
                    vBox.setOpacity(0);
                });
            });


        } catch (ApplicationException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void tableForDuplicatedBook(List<Book> list) {
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
                bookIDHolder.setText(Integer.toString(newSelection.getId()));
                bookTitleHolder.setText(newSelection.getTitle());
                bookAuthorHolder.setText(newSelection.getAuthorFX().getFistName() + " " + newSelection.getAuthorFX().getMiddleName() + " " + newSelection.getAuthorFX().getLastName());
                bookPublisherHolder.setText(newSelection.getPublishingCompanyFX().getName());
                bookInfo.setOpacity(1);
            });
        });
        showTableDialog(rootPane, mainAnchorPane, Arrays.asList(choiceButton), "Ambiguous data was provided",
                "More than one object with the mentioned values exists in the database. \n" +
                        "Choose a specific object from the list below, and then confirm with the button 'Choose selected book'", duplicates);
    }

    private void tableForDuplicatedUser(List<User> list) {
        ObservableList<UserFXModel> observableList = FXCollections.observableArrayList();
        list.forEach(user -> observableList.add(Converters.convertToUserFx(user)));

        TableView<UserFXModel> duplicates = new TableView<>();
        TableColumn<UserFXModel, String> firstName = new TableColumn<>();
        TableColumn<UserFXModel, String> lastName = new TableColumn<>();
        TableColumn<UserFXModel, String> mobile = new TableColumn<>();
        TableColumn<UserFXModel, String> street = new TableColumn<>();
        TableColumn<UserFXModel, String> city = new TableColumn<>();

        duplicates.setItems(observableList);

        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        street.setCellValueFactory(new PropertyValueFactory<>("street"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));

        duplicates.getColumns().addAll(firstName, lastName, mobile, street, city);

        duplicates.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        JFXButton choiceButton = new JFXButton("Choose selected person");
        duplicates.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            choiceButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                memberIdHolder.setText(Integer.toString(newSelection.getId()));
                memberNameHolder.setText(newSelection.getFirstName());
                memberLastNameHolder.setText(newSelection.getLastName());
                memberContactHolder.setText(newSelection.getMobile() + ", " + newSelection.getEmail());
                memberAddressHolder.setText(newSelection.getStreet() + ", " + newSelection.getZipCode() + " " + newSelection.getCity().getName());
                userInfo.setOpacity(1);
            });
        });
        showTableDialog(rootPane, mainAnchorPane, Arrays.asList(choiceButton), "Ambiguous data was provided",
                "More than one object with the mentioned values exists in the database. \n" +
                        "Choose a specific object from the list below, and then confirm with the button 'Choose selected person'", duplicates);

    }

    private void initColumn() throws ApplicationException, SQLException {
        tableView.setItems(loadData());
        titleColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().titleProperty());
        isbnColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().isbn13Property());

    }

    private ObservableList<BookFXModel> loadData() throws ApplicationException, SQLException {
        ObservableList<BookFXModel> observableArrayList = FXCollections.observableArrayList();
        DataAccessObject dao = new DataAccessObject();
        List<String[]> books = dao.executeRawQuery(Book.class, "SELECT ID FROM BOOKS WHERE AVAILABILITY = 1");
        observableArrayList.clear();
        books.forEach(bookID -> {
            try {
                Book book = dao.findById(Book.class, Integer.parseInt(bookID[0]));
                if (book.getLibrary().getId() == LoginController.currentlyLoggedUser.getLibrary().getId()) {
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
    private void handleRowData(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) { // checking the number of mouse clicks on a single row
            BookFXModel rowData = tableView.getSelectionModel().getSelectedItem(); // creating User object from data in selected row
            if (rowData == null) { // check if selected row is not null
                showJFXButton(rootPane, mainAnchorPane, new ArrayList<>(), "No book selected", "Please select row with book data");
            } else {
                bookTitleInput.setText(rowData.getTitle());
                bookIDHolder.setText(Integer.toString(rowData.getId()));
                bookTitleHolder.setText(rowData.getTitle());
                bookAuthorHolder.setText(rowData.getAuthorFX().getFistName() + " " + rowData.getAuthorFX().getMiddleName() + " " + rowData.getAuthorFX().getLastName());
                bookPublisherHolder.setText(rowData.getPublishingCompanyFX().getName());
                bookInfo.setOpacity(1);
            }
        }
    }

    @FXML
    private void executeCheckOutOperation() {
        String memberID = memberIdHolder.getText();
        String bookID = bookIDHolder.getText();

        JFXButton yesButton = new JFXButton("YES");
        yesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            DataAccessObject dao = new DataAccessObject();
            try {
                Book book = dao.findById(Book.class, Integer.parseInt(bookID));
                User user = dao.findById(User.class, Integer.parseInt(memberID));

                BorrowedBook borrowedBook = new BorrowedBook();
                borrowedBook.setBook(book);
                borrowedBook.setUser(user);
                borrowedBook.setBorrowTime(LocalDate.now().toString());
                borrowedBook.setLibrary(LoginController.currentlyLoggedUser.getLibrary());

                dao.createOrUpdate(borrowedBook);
                dao.updateItem(Book.class, book.getId(), "AVAILABILITY", "false");
                showJFXButton(rootPane, mainAnchorPane, new ArrayList<>(), " Success", "Operation ended successfully");
                clearCheckOutEntries();
                initColumn();
            } catch (ApplicationException | SQLException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        });

        JFXButton cancelButton = new JFXButton("Cancel");
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) ->
                showJFXButton(rootPane, mainAnchorPane, new ArrayList<>(), "Failed", "Operation ended unsuccessfully"));

        showJFXButton(rootPane, mainAnchorPane, Arrays.asList(yesButton, cancelButton), "Confirm chek book out operation",
                "Are you sure you want to lend '" + bookTitleHolder.getText() + "' to " + memberNameHolder.getText() + " " + memberLastNameHolder.getText() + "?");
    }

    private void clearCheckOutEntries() {
        userFirstNameInput.clear();
        userLastNameInput.clear();
        bookTitleInput.clear();

        List<VBox> boxes = List.of(userInfo, bookInfo);
        boxes.forEach(vBox -> {
            for (Node node : vBox.getChildren()) {
                if (node instanceof Text) {
                    ((Text) node).setText("");
                }
            }
        });
    }

}
