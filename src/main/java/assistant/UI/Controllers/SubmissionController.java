package assistant.UI.Controllers;

import assistant.Utils.ApplicationException;
import assistant.Utils.CreateSets;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.Book;
import assistant.database.models.BorrowedBook;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static assistant.alert.AlertMaker.showJFXButton;

public class SubmissionController implements Initializable {

    @FXML
    private StackPane rootPane;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private JFXTextField bookTitleInput;

    @FXML
    private HBox submissionDataContainer;

    @FXML
    private Text memberNameHolder;

    @FXML
    private Text memberLastNameHolder;

    @FXML
    private Text memberAddressHolder;

    @FXML
    private Text memberContactHolder;

    @FXML
    private Text bookTitleHolder;

    @FXML
    private Text bookAuthorHolder;

    @FXML
    private Text bookPublisherHolder;

    @FXML
    private Text checkOutHolder;

    @FXML
    private Text dayHolder;

    @FXML
    private Text feeHolder;

    @FXML
    private JFXButton submissionButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DataAccessObject dao = new DataAccessObject();
        TextFields.bindAutoCompletion(bookTitleInput, CreateSets.createTitleSet("SELECT TITLE FROM BORROWED_BOOKS INNER JOIN BOOKS ON BOOK_ID = BOOKS.ID WHERE IS_RETURNED = 0 "));
        bookTitleInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!bookTitleInput.getText().equals("")&& bookTitleHolder.getText().equals("") || !bookTitleInput.getText().equals(bookTitleHolder.getText())) {
                try {
                    bookTitleInput.getParent().requestFocus();
                    String query = "SELECT BOOK_ID , USER_ID , BORROW_TIME,\n" +
                            "USERS.FIRST_NAME, USERS.LAST_NAME , USERS.MOBILE, USERS.EMAIL, USERS.STREET ," +
                            "BOOKS.TITLE,\n" +
                            "AUTHORS.FIRST_NAME , AUTHORS.MIDDLE_NAME ,AUTHORS.LAST_NAME \n" +
                            "FROM BORROWED_BOOKS \n" +
                            "INNER JOIN BOOKS ON BOOK_ID = BOOKS.ID \n" +
                            "INNER JOIN USERS ON USER_ID = USERS.ID \n" +
                            "INNER JOIN AUTHORS ON BOOKS.AUTHOR_ID = AUTHORS.ID \n" +
                            "WHERE IS_RETURNED = 0 and BOOKS.TITLE = '" + bookTitleInput.getText() + "'";
                    List<String[]> list = dao.executeRawQuery(BorrowedBook.class, query); // create list of books with the same id

                    for (String[] s : list) {
                        System.out.println(Arrays.toString(s));
                    }
                    if (list.size() == 1) { // if return 1 record - set data of returned book
                        memberNameHolder.setText(list.get(0)[3]);
                        memberLastNameHolder.setText(list.get(0)[4]);
                        memberContactHolder.setText(list.get(0)[5] + ", " + list.get(0)[6]);
                        memberAddressHolder.setText(list.get(0)[7]);
                        bookTitleHolder.setText(list.get(0)[8]);
                        bookAuthorHolder.setText((list.get(0)[10] == null) ? list.get(0)[9] + " " + list.get(0)[11] : list.get(0)[9] + " " + list.get(0)[10] + " " + list.get(0)[11]);

                        checkOutHolder.setText(list.get(0)[2]);

                        LocalDate chekOutTime = LocalDate.parse(list.get(0)[2]);
                        LocalDate now = LocalDate.now();
                        long daysElapsed = ChronoUnit.DAYS.between(chekOutTime, now);
                        dayHolder.setText(Long.toString(daysElapsed));

                    } else if (list.size() == 0) { // if return 0 - the book with the given title does not exist in the database, return warning
                        showJFXButton(rootPane, mainAnchorPane, new ArrayList<>(), "Wrong Title", "The book with the given title was not found in the system");
                        bookTitleInput.setText("");
                    }
//                    else { // if return >1 - show list with more detailed information about returned books
//                        tableForDuplicatedBook(list);

                } catch (ApplicationException | NullPointerException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @FXML
    void returnBook() {


    }
}
