package assistant.UI.Controllers;

import assistant.FXModels.UserFXModel;
import assistant.Utils.ApplicationException;
import assistant.Utils.Converters;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

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
    public VBox submissionDataContainer;
    public JFXButton clearButton;
    public JFXButton submitButton;
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
        List<BorrowedBook> borrowedBooks = dao.findByColumnName(BorrowedBook.class, "USER_ID", userID);
//        List<Node> nodes = new ArrayList<>();
        borrowedBooks.forEach(book -> {
            System.out.println("here!");
            if (!book.getReturned()) {
                try {
                    submissionDataContainer.getChildren().add(generateBookCell(book.getBookID(), book));
                } catch (ApplicationException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // grid pane
//        for (int i = 0; i < submissionDataContainer.getRowCount(); i++) {
//            for (int j = 0; j < submissionDataContainer.getColumnCount(); j++) {
//                if (j + i < nodes.size())
//                    submissionDataContainer.add(nodes.get(i + j), i, j);
//            }
//        }
    }

    private Node generateBookCell(int bookID, BorrowedBook borrowedBook) throws ApplicationException, IOException {
        System.out.println("Here 2");
        DataAccessObject dao = new DataAccessObject();
        Book book = dao.findById(Book.class, bookID);
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        hBox.setStyle("-fx-background-color: white;");
        hBox.setMaxWidth(350);
        Image img;
        try {
            img = loadImage(book.getBookCover());
        } catch (NullPointerException e) {
            img = new Image("/images/book.png");
        }
        Circle circle = new Circle();
        circle.setRadius(50.0f);
        circle.setFill(new ImagePattern(img));
        Label titleHolder = new Label();
        Label authorHolder = new Label();
        Label categoryHolder = new Label();
        Label ISBNHolder = new Label();
        JFXButton button = new JFXButton();
        button.setText("submit book");
        button.setOnAction(event -> {
            try {
                returnBook(book, borrowedBook);
            } catch (ApplicationException e) {
                e.printStackTrace();
            }
        });
        titleHolder.setText(book.getTitle());
        authorHolder.setText(dao.findById(Author.class, book.getAuthor()).getFullName());
        titleHolder.setText(dao.findById(Category.class, book.getCategory()).getName());
        categoryHolder.setText(book.getTitle());
        ISBNHolder.setText(book.getIsbn13());
        vBox.getChildren().addAll(titleHolder, authorHolder, categoryHolder, ISBNHolder, button);
        hBox.getChildren().addAll(circle, vBox);
        return hBox;
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
    void returnBook(Book book, BorrowedBook borrowedBook) throws ApplicationException {
        DataAccessObject dataAccessObject = new DataAccessObject();
        book.setAvailability(true);

        borrowedBook.setReturnTime(LocalDate.now().toString());
        borrowedBook.setReturned(true);
        dataAccessObject.createOrUpdate(book);
        dataAccessObject.createOrUpdate(borrowedBook);

        showJFXButton(rootPane, mainAnchorPane, new ArrayList<>(), "Success", "Book was successfully returned!");

    }
}
