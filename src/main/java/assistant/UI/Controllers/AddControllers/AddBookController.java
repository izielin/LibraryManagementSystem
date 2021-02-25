package assistant.UI.Controllers.AddControllers;

import assistant.FXModels.BookFXModel;
import assistant.UI.Controllers.LoginController;
import assistant.Utils.ApplicationException;
import assistant.Utils.MessageMaker;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static assistant.Utils.AlertMaker.showJFXButton;

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
            authorList = dao.executeRawQuery(Author.class, query);
            categoryList = dao.executeRawQuery(Category.class, query2);
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

        bookTitle.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!bookTitle.focusedProperty().getValue()) {
                autoCompletionLearnWord(bookTitle.getText());
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
    private void chooseFile() throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("*.png", "*.jpg"));
        File file = fileChooser.showOpenDialog(null);

        System.out.println(file);

        if (file != null) {
            bookCover.setImage(new Image(file.toURI().toURL().toExternalForm()));
            filePath.setText("Selected File: " + file.getAbsolutePath());
        }

        selectedCover = file;
        System.out.println(selectedCover);

    }

    // TODO: simplify getting full name of author

    @FXML
    void saveButton() throws ApplicationException, SQLException, IOException {
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

        if (selectedCover != null) {
            BufferedImage bufferedImage = ImageIO.read(selectedCover);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            byte[] data = outputStream.toByteArray();

            book.setBookCover(data);
        }
        book.setAuthor(author.getId());
        book.setCategory(category.getId());
        book.setLibrary(LoginController.currentlyLoggedUser.getLibraryID());
        book.setPublishingCompany(publishingCompany.getId());

        filePath.setText("");
        bookCover.setImage(new Image("/images/book.png"));

        dao.createOrUpdate(book);
        showJFXButton(rootPane, mainAnchorPane, new ArrayList<>(), "Success", "Book was successfully created!");
        dao.createOrUpdate(MessageMaker.bookCreationMessage(bookTitle.getText(), LoginController.currentlyLoggedUser, date));
        System.out.println("Message created");
        if (editedBookID != null) {
            JFXButton okButton = new JFXButton("Okay");
            okButton.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> ((Stage) bookTitle.getScene().getWindow()).close());
            showJFXButton(rootPane, mainAnchorPane, Arrays.asList(okButton), "Success", "Book was successfully created!");
        } else
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

    public void inflateUI(BookFXModel book) throws IOException, ApplicationException {
        editedBookID = book.getId();
        bookTitle.textProperty().bindBidirectional(book.titleProperty());
        isbn10.textProperty().bindBidirectional(book.isbn10Property());
        isbn13.textProperty().bindBidirectional(book.isbn13Property());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = book.getAddedDate();
        LocalDate localDate = LocalDate.parse(date, formatter);
        addedDate.setValue(localDate);

        description.textProperty().bindBidirectional(book.descriptionProperty());
        releaseYear.textProperty().bindBidirectional(book.publicationDateProperty());
        bookCategory.textProperty().bindBidirectional(new SimpleStringProperty(dao.findById(Category.class, book.getCategoryID()).getName()));
        bookAuthor.textProperty().bindBidirectional(new SimpleStringProperty(dao.findById(Author.class, book.getAuthorID()).getFullName()));
        publishingCompanyName.textProperty().bindBidirectional(new SimpleStringProperty(dao.findById(PublishingCompany.class, book.getPublishingCompanyID()).getName()));

        if (book.getBookCover() != null) {
            ByteArrayInputStream bis = new ByteArrayInputStream(book.getBookCover());
            BufferedImage bImage2 = ImageIO.read(bis);
            Image image = javafx.embed.swing.SwingFXUtils.toFXImage(bImage2, null);
            bookCover.setImage(image);
        } else {
            bookCover.setImage(new Image("/images/book.png"));

        }
    }
}
