package assistant.UI.Controllers;

import assistant.FXModels.BookFXModel;
import assistant.FXModels.UserFXModel;
import assistant.UI.Controllers.Employee.AddControllers.AddUserController;
import assistant.Utils.ApplicationException;
import assistant.Utils.Converters;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.*;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static assistant.Utils.AlertMaker.showJFXButton;
import static assistant.Utils.ProjectTools.getResourceBundle;
import static assistant.Utils.ProjectTools.loadWindow;

public class ProfileController implements Initializable {

    public Tab borrowedTab;
    public Tab historyTab;
    public Tab watchedTab;
    public AnchorPane mainAnchorPane;
    public StackPane rootPane;
    @FXML
    private ImageView profileImage;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label libraryLabel;

    @FXML
    private JFXButton editButton;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label streetLabel;

    @FXML
    private Label cityLabel;

    @FXML
    private VBox progressBar;

    @FXML
    private AnchorPane borrowedPane;

    @FXML
    private AnchorPane historyPane;

    @FXML
    private AnchorPane watchedPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = LoginController.currentlyLoggedUser;
        DataAccessObject dao = new DataAccessObject();
        try {
            setUserData(user.getId());
            String allBorrowedQuery = "select count(*) FROM BORROWED_BOOKS " +
                    "where USER_ID = " + user.getId();
            String allBorrowed = dao.countRecords(BorrowedBook.class, allBorrowedQuery);
            if (!allBorrowed.equals("0"))
                setProgressBars(user, dao, allBorrowed);
        } catch (IOException | ApplicationException | SQLException e) {
            e.printStackTrace();
        }
        try {
            generateBorrowedBooksList(loadData("SELECT BOOK_ID FROM BORROWED_BOOKS where USER_ID = " + user.getId() + " and IS_RETURNED = FALSE "), borrowedPane);
            generateBorrowedBooksList(loadData("SELECT BOOK_ID FROM BORROWED_BOOKS where USER_ID = " + user.getId() + " and IS_RETURNED = TRUE "), historyPane);
            // fetching the first book id (from the BOOKS table) with the title selected from the WATCHED table
            String query = "SELECT MIN(BOOKS.id) FROM BOOKS, WATCHED, (SELECT title, id FROM BOOKS GROUP BY title) s" +
                    " WHERE s.title = BOOKS.title" +
                    " AND WATCHED.BOOK_TITLE = BOOKS.title" +
                    " AND s.id = BOOKS.id" +
                    " AND WATCHED.USER_ID = " + user.getId() +
                    " GROUP BY BOOKS.title, BOOKS.id";
            generateBorrowedBooksList(loadData(query), watchedPane);
        } catch (ApplicationException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void generateBorrowedBooksList(ObservableList<Book> list, AnchorPane pane) throws ApplicationException, SQLException {
        GridPane table = new GridPane();
        table.setHgap(20);
        table.setVgap(20);
        int rows;
        if (list.size() < 5)
            rows = 1;
        else
            rows = (int) (Math.round(list.size() / 5.0));
        List<Node> nodes = new ArrayList<>();
        list.forEach(book -> {
            System.out.println("here!");
            try {
                nodes.add(generateBookCell(book));
            } catch (ApplicationException | IOException e) {
                e.printStackTrace();
            }
        });

        // grid pane
        int k = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 5; j++) {
                if (k < nodes.size())
                    table.add(nodes.get(k++), j, i);
            }
        }
        pane.getChildren().add(table);
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

    private ObservableList<Book> loadData(String query) throws ApplicationException, SQLException {
        ObservableList<Book> observableArrayList = FXCollections.observableArrayList();
        DataAccessObject dao = new DataAccessObject();
        List<String[]> bookList = dao.executeRawQuery(Book.class, query);
        observableArrayList.clear();
        bookList.forEach(element -> {
            try {
                Book book = dao.findById(Book.class, Integer.parseInt(element[0]));
                observableArrayList.add(book);
            } catch (ApplicationException e) {
                e.printStackTrace();
            }
        });
        return observableArrayList;
    }

    private void setProgressBars(User user, DataAccessObject dao, String allBorrowed) throws SQLException, ApplicationException {
        String categoryQuery = "select name, count(*) as quantity FROM BORROWED_BOOKS, BOOKS, CATEGORIES " +
                "where USER_ID = " + user.getId() + " AND BOOK_ID = BOOKS.ID and category_id = CATEGORIES.ID " +
                "GROUP BY name " +
                "ORDER BY quantity DESC " +
                "LIMIT 1";
        List<String[]> categories = dao.executeRawQuery(BorrowedBook.class, categoryQuery);
        generateProgressBar("Favorite category: " + categories.get(0)[0], categories.get(0)[1], Double.parseDouble(allBorrowed));

        String authorsQuery = "SELECT FIRST_NAME, MIDDLE_NAME, LAST_NAME, COUNT(*) as quantity FROM AUTHORS, BOOKS ,BORROWED_BOOKS " +
                "where USER_ID = " + user.getId() + " AND BOOK_ID = BOOKS.ID AND BOOKS.AUTHOR_ID = AUTHORS.ID " +
                "GROUP BY AUTHORS.ID " +
                "ORDER BY quantity DESC " +
                "LIMIT 1";
        String allAuthorsQuery = "SELECT DISTINCT AUTHORS.ID FROM AUTHORS, BOOKS ,BORROWED_BOOKS " +
                "where USER_ID = " + user.getId() + " AND BOOK_ID = BOOKS.ID AND BOOKS.AUTHOR_ID = AUTHORS.ID ";
        List<String[]> authors = dao.executeRawQuery(BorrowedBook.class, authorsQuery);
        List<String[]> allAuthors = dao.executeRawQuery(BorrowedBook.class, allAuthorsQuery);
        generateProgressBar("Favorite author: " + authors.get(0)[0] + ((authors.get(0)[1] == null) ? " " : " " + authors.get(0)[1] + " ")
                + authors.get(0)[2], authors.get(0)[3], allAuthors.size());

        String byMonthQuery = "SELECT strftime ('%Y', BORROW_TIME) as extracted_year, strftime ('%m', BORROW_TIME) as extracted_month , count(*) as quantity" +
                " FROM BORROWED_BOOKS where USER_ID = " + user.getId() +
                " GROUP by extracted_year, extracted_month ORDER By quantity DESC " +
                "LIMIT 1";
        List<String[]> byMonth = dao.executeRawQuery(BorrowedBook.class, byMonthQuery);
        LocalDate date = LocalDate.of(Integer.parseInt(byMonth.get(0)[0]), Integer.parseInt(byMonth.get(0)[1]), 1);
        generateProgressBar("Most borrowed in: " + date.getMonth() + " " + date.getYear(), byMonth.get(0)[2], Double.parseDouble(allBorrowed));

    }

    private void generateProgressBar(String label, String amount, double allRecords) {
        VBox vBox = new VBox();

        HBox hBox = new HBox();
        hBox.setMinWidth(150.0);
        hBox.setSpacing(20.0);
        Label label1 = new Label();
        Label label2 = new Label();

        double percents = Integer.parseInt(amount) / allRecords;
        label1.setText(label);
        label2.setText(amount + " (" + (int) (percents * 100) + "%)");

        ProgressBar processBar = new ProgressBar(percents);
        processBar.setMinWidth(300.0);

        hBox.getChildren().addAll(label1, label2);
        vBox.getChildren().addAll(hBox, processBar);
        VBox.setMargin(vBox, new Insets(20, 0, 0, 0));
        progressBar.getChildren().add(vBox);
    }

    private void setUserData(int id) throws IOException, ApplicationException {
        DataAccessObject dao = new DataAccessObject();
        User user = dao.findById(User.class, id);
        Library library = dao.findById(Library.class, user.getLibraryID());
        profileImage.setImage(loadImage(user.getProfilePicture()));
        usernameLabel.setText(user.getUsername());
        nameLabel.setText(user.getFirstName() + " " + user.getLastName());
        libraryLabel.setText(library.getName());
        phoneLabel.setText(user.getMobile());
        emailLabel.setText(user.getEmail());
        streetLabel.setText(user.getStreet());
        cityLabel.setText(user.getZipCode() + " " + dao.findById(City.class, library.getCityID()).getName());
    }

    private Image loadImage(byte[] byte_array) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(byte_array);
        BufferedImage bImage2 = ImageIO.read(bis);
        return javafx.embed.swing.SwingFXUtils.toFXImage(bImage2, null);
    }

    public void executeUpdateAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Employee/addViews/AddUser.fxml"), getResourceBundle());
            Parent parent = loader.load();

            AddUserController controller = loader.getController();
            User user = LoginController.currentlyLoggedUser;
            controller.inflateUI(Converters.convertToUserFx(user));
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();

            stage.setOnHidden((e) -> {
                try {
                    setUserData(user.getId());
                } catch (IOException | ApplicationException ioException) {
                    ioException.printStackTrace();
                }
            }); //refresh table

        } catch (IOException e) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void executeDeleteAction() {
        DataAccessObject dao = new DataAccessObject();
        JFXButton yesButton = new JFXButton("YES");
        JFXButton cancelButton = new JFXButton("Cancel");
        yesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            try {
                //checking if the user being deleted has a borrowed book
                User user = LoginController.currentlyLoggedUser;
                List<BorrowedBook> list = dao.findByColumnName(BorrowedBook.class, "USER_ID", user.getId());
                if (list.isEmpty()) {
                    dao.deleteById(User.class, user.getId());
                    ((Stage) deleteButton.getScene().getWindow()).close();
                    loadWindow("/fxml/Login.fxml");
                } else {
                    showJFXButton(rootPane, mainAnchorPane, new ArrayList<>(), "Error", "User cannot be deleted. Cause: has a borrowed book!");
                }
            } catch (ApplicationException | SQLException e) {
                e.printStackTrace();
            }

        });
        showJFXButton(rootPane, mainAnchorPane, Arrays.asList(yesButton, cancelButton), "Confirm delete operation",
                "Are you sure you want to delete your account?");
    }


}
