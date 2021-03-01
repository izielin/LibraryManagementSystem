package assistant.UI.Controllers.Employee;

import assistant.FXModels.UserFXModel;
import assistant.Utils.ApplicationException;
import assistant.Utils.Converters;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

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

import static assistant.Utils.AlertMaker.showJFXButton;
import static assistant.Utils.AlertMaker.showTableDialog;

public class SubmissionController implements Initializable {


    public JFXTextField userFirstNameInput;
    public StackPane rootPane;
    public AnchorPane mainAnchorPane;
    public JFXButton clearButton;
    public JFXButton submitButton;
    public ScrollPane bookList;
    @FXML
    private JFXTextField userLastNameInput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DataAccessObject dao = new DataAccessObject();
        submitButton.setOnAction(event -> generateUserList(dao));
        clearButton.setOnAction(event -> {
            userFirstNameInput.clear();
            userLastNameInput.clear();
        });
    }

    private void generateUserList(DataAccessObject dao) {
        try {
            userFirstNameInput.getParent().requestFocus();
            List<User> list = dao.searchDuplicatedNames(userFirstNameInput.getText(), userLastNameInput.getText());
            if (list.size() == 1) {
                System.out.println("One user case");
                generateBorrowedBooksList(list.get(0).getId());
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

    private void generateBorrowedBooksList(int userID) throws ApplicationException, SQLException {
        DataAccessObject dao = new DataAccessObject();
        GridPane table = new GridPane();
        table.setHgap(20);
        table.setVgap(20);
        List<BorrowedBook> borrowedBooks = dao.findByColumnName(BorrowedBook.class, "USER_ID", userID);
        int rows;
        if (borrowedBooks.size() < 3)
            rows = 1;
        else
            rows = (int) (Math.round(borrowedBooks.size() / 3.0));
        List<Node> nodes = new ArrayList<>();
        borrowedBooks.forEach(book -> {
            System.out.println("here!");
            if (!book.getReturned()) {
                try {
                    nodes.add(generateBookCell(book.getBookID(), book, "Return Book"));
                } catch (ApplicationException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // grid pane
        int k = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 3; j++) {
                if (k < nodes.size())
                    table.add(nodes.get(k++), j, i);
            }
        }
        bookList.setContent(table);
    }

    private Node generateBookCell(int bookID, BorrowedBook borrowedBook, String buttonText) throws ApplicationException, IOException {
        DataAccessObject dao = new DataAccessObject();
        Book book = dao.findById(Book.class, bookID);
        HBox mainHBox = new HBox();
        mainHBox.setMaxWidth(350);
        mainHBox.setSpacing(20);

        ImageView imageView = new ImageView();
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
        Image img;
        try {
            img = loadImage(book.getBookCover());
        } catch (NullPointerException e) {
            img = new Image("/images/book.png");
        }

        imageView.setImage(img);

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
        JFXButton button = new JFXButton();
        button.setStyle("-fx-background-color: #fdc12a");
        button.setStyle("-fx-text-fill: black");
        button.setText(buttonText);
        button.setOnAction(event -> {
            try {
                returnBook(book, borrowedBook);
            } catch (ApplicationException | SQLException e) {
                e.printStackTrace();
            }
        });

        authorHolder.setText("Author: " + dao.findById(Author.class, book.getAuthor()).getFullName());
        categoryHolder.setText("Category: " + dao.findById(Category.class, book.getCategory()).getName());
        ISBNHolder.setText("ISBN: " + book.getIsbn13());
        contentVbox.getChildren().addAll(authorHolder, categoryHolder, ISBNHolder, button);
        mainVbox.getChildren().addAll(titleHolder, separator, contentVbox);
        mainHBox.getChildren().addAll(imageView, mainVbox);
        return mainHBox;
    }

    private Image loadImage(byte[] byte_array) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(byte_array);
        BufferedImage bImage2 = ImageIO.read(bis);
        return javafx.embed.swing.SwingFXUtils.toFXImage(bImage2, null);
    }

    private void tableForDuplicatedUser(List<User> list) {
        ObservableList<UserFXModel> observableList = FXCollections.observableArrayList();
        list.forEach(user -> observableList.add(Converters.convertToUserFx(user)));
        LendBookController controller = new LendBookController();
        TableView<UserFXModel> duplicates = controller.getTableView(observableList);

        duplicates.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        JFXButton choiceButton = new JFXButton("Choose selected person");
        duplicates.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            choiceButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                try {
                    generateBorrowedBooksList(newSelection.getId());
                } catch (ApplicationException | SQLException e) {
                    e.printStackTrace();
                }
            });
        });
        showTableDialog(rootPane, mainAnchorPane, Arrays.asList(choiceButton), "Ambiguous data was provided",
                "More than one object with the mentioned values exists in the database. \n" +
                        "Choose a specific object from the list below, and then confirm with the button 'Choose selected person'", duplicates);
    }

    @FXML
    void returnBook(Book book, BorrowedBook borrowedBook) throws ApplicationException, SQLException {
        DataAccessObject dataAccessObject = new DataAccessObject();
        book.setAvailability(true);

        borrowedBook.setReturnTime(LocalDate.now().toString());
        borrowedBook.setReturned(true);
        dataAccessObject.createOrUpdate(book);
        dataAccessObject.createOrUpdate(borrowedBook);

        generateBorrowedBooksList(borrowedBook.getUserID());
        showJFXButton(rootPane, mainAnchorPane, new ArrayList<>(), "Success", "Book was successfully returned!");

    }
}
