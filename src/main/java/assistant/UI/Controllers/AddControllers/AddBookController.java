package assistant.UI.Controllers.AddControllers;
//
//import assistant.UI.Controllers.BookListController.Book;
//import assistant.Utils.Utils;
//import assistant.database.db;
//import com.jfoenix.controls.JFXButton;
//import com.jfoenix.controls.JFXTextField;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.BorderPane;
//import javafx.stage.Stage;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
//import static assistant.alert.AlertMaker.showSimpleAlert;
//

import assistant.UI.Controllers.LoginController;
import assistant.Utils.exceptions.ApplicationException;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static assistant.alert.AlertMaker.showJFXButton;

public class AddBookController implements Initializable {

    @FXML
    private StackPane rootPane;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private JFXButton btnFileChooser;

    @FXML
    private Text filePath;

    @FXML
    private ImageView bookCover;

    @FXML
    private JFXTextField bookTitle;

    @FXML
    private JFXTextField bookAuthor;

    @FXML
    private JFXTextField bookCategory;

    @FXML
    private JFXTextField publishingCompanyName;

    @FXML
    private JFXTextArea description;

    @FXML
    private JFXDatePicker addedDate;

    @FXML
    private JFXTextField releaseYear;

    @FXML
    private JFXTextField isbn10;

    @FXML
    private JFXTextField isbn13;

    File selectedCover;
    DataAccessObject dao = new DataAccessObject();
    Integer editedBookID = null;

    List<String[]> authorList;
    List<String[]> categoryList;
    List<String[]> publisherList;

    private AutoCompletionBinding<String> autoCompletionBinding;
    private final List<String> possibleAuthorsItems = new ArrayList<>();
    private final List<String> possiblePublishersItems = new ArrayList<>();
    private final List<String> possibleCategoriesItems = new ArrayList<>();

    private Set<String> possibleAuthorsSet;
    private Set<String> possiblePublishersSet;
    private Set<String> possibleCategoriesSet;
    private final Set<String> possibleTitleSet = new HashSet<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // creating autocomplete TextField
        try {
            String query = "SELECT DISTINCT FIRST_NAME, MIDDLE_NAME, LAST_NAME, ID FROM AUTHORS";
            String query2 = "SELECT DISTINCT NAME, ID FROM CATEGORIES";
            String query3 = "SELECT DISTINCT NAME, ID FROM PUBLISHERS";
            authorList = dao.executeRawQuery(PublishingCompany.class, query);
            categoryList = dao.executeRawQuery(PublishingCompany.class, query2);
            publisherList = dao.executeRawQuery(PublishingCompany.class, query3);

            categoryList.forEach(item -> possibleCategoriesItems.add(item[0]));
            publisherList.forEach(item -> possiblePublishersItems.add(item[0]));
            authorList.forEach(item -> possibleAuthorsItems.add(
                    (item[1] == null) ? item[0] + " " + item[2] : item[0] + " " + item[1] + " " + item[2]));

            possibleCategoriesSet = new HashSet<>(possibleCategoriesItems);
            possibleAuthorsSet = new HashSet<>(possibleAuthorsItems);
            possiblePublishersSet = new HashSet<>(possiblePublishersItems);
        } catch (ApplicationException | SQLException e) {
            e.printStackTrace();
        }

        TextFields.bindAutoCompletion(bookCategory, possibleCategoriesSet);
        TextFields.bindAutoCompletion(bookAuthor, possibleAuthorsSet);
        TextFields.bindAutoCompletion(publishingCompanyName, possiblePublishersSet);

        // creating learning TextField
        autoCompletionBinding = TextFields.bindAutoCompletion(bookTitle, possibleTitleSet);

        bookTitle.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.TAB) {
                    autoCompletionLearnWord(bookTitle.getText());
                }
            }
        });
    }

    private void autoCompletionLearnWord(String newWord) {
        possibleTitleSet.add(newWord);
        if (autoCompletionBinding != null)
            autoCompletionBinding.dispose();
        autoCompletionBinding = TextFields.bindAutoCompletion(bookTitle, possibleTitleSet);
    }

    @FXML
    private void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.png", "*.jpg"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            bookCover.setImage(new Image(file.toURI().toString()));
            filePath.setText("Selected File: " + file.getAbsolutePath());
        }

        selectedCover = file;

    }


    @FXML
    void saveButton(ActionEvent event) throws ApplicationException, SQLException, IOException {
        DataAccessObject dao = new DataAccessObject();
        Book book = null;
        if (editedBookID == null) {
            book = new Book();

        } else {
            book = dao.findById(Book.class, editedBookID);
        }


        Category category = dao.findById(Category.class, Integer.parseInt(
                dao.executeRawQuery(Category.class, "SELECT ID FROM CATEGORIES WHERE NAME = '" + bookCategory.getText() + "'")
                        .get(0)[0]));

        PublishingCompany publishingCompany = dao.findById(PublishingCompany.class, Integer.parseInt(
                dao.executeRawQuery(PublishingCompany.class, "SELECT id FROM PUBLISHERS WHERE NAME = '" + publishingCompanyName.getText() + "'")
                        .get(0)[0]));

        String[] authorFullName = bookAuthor.getText().split(" ");
        Author author = dao.findById(Author.class, Integer.parseInt(
                dao.executeRawQuery(Author.class, "SELECT id FROM AUTHORS " +
                        "WHERE FIRST_NAME = '" + authorFullName[0] + "'" +
                        " AND LAST_NAME ='" + authorFullName[authorFullName.length - 1] + "'")
                        .get(0)[0]));


        book.setTitle(bookTitle.getText());
        book.setIsbn10(isbn10.getText());
        book.setIsbn13(isbn13.getText());

        String date = addedDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        book.setAddedDate(date);

        book.setDescription(description.getText());
        book.setPublicationDate(releaseYear.getText());

        BufferedImage bufferedImage = ImageIO.read(selectedCover);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", outputStream);
        byte[] data = outputStream.toByteArray();

        book.setBookCover(data);
        book.setAuthor(author);
        book.setCategory(category);
        book.setLibrary(LoginController.currentlyLoggedUser.getLibrary());
        book.setPublishingCompany(publishingCompany);

        filePath.setText("");
        bookCover.setImage(new Image("/images/book.png"));

        dao.createOrUpdate(book);
        showJFXButton(rootPane, mainAnchorPane, new ArrayList<>(), "Success", "Book was successfully created!");
        clearFields();

    }

    private void clearFields() {
        bookTitle.setText("");
        isbn10.setText("");
        isbn13.setText("");
        addedDate.setValue(null);
        addedDate.getEditor().clear();
        description.setText("");
        releaseYear.setText("");
        bookAuthor.setText("");
        bookCategory.setText("");
        publishingCompanyName.setText("");
    }

}


//    public void inflateUI(Book book) {
//        bookTitle.setText(book.getTitleProperty());
//        bookID.setText(book.getIdProperty());
//        bookAuthorName.setText(book.getAuthorProperty());
//        publishingCompanyName.setText(book.getPublisherProperty());
//        bookID.setEditable(false);
//        isInEditMode = true;
//    }
//}
//
