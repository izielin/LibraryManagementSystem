package assistant.UI.Controllers.Member;

import assistant.UI.Controllers.LoginController;
import assistant.Utils.ApplicationException;
import assistant.Utils.ProjectTools;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.sql.SQLException;

public class BookDetailsController {

    @FXML
    private StackPane rootPane;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private Label titleLabel;

    @FXML
    private ImageView bookCoverHolder;

    @FXML
    private Label authorLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private Label isbn10Label;

    @FXML
    private Label isbn13Label;

    @FXML
    private Label publisherLabel;

    @FXML
    private Label detailsLabel;

    @FXML
    private Label bookAmount;

    @FXML
    private Label readyToBorrow;

    @FXML
    private Label borrowingNumber;


    @FXML
    void addToWatched() throws ApplicationException {
        DataAccessObject dao = new DataAccessObject();
        Watched watched = new Watched();
        watched.setBookTitle(titleLabel.getText());
        watched.setUserID(LoginController.currentlyLoggedUser.getId());

        dao.createOrUpdate(watched);
    }

    public void setData(Book book, String authorText, String categoryText) throws IOException, ApplicationException, SQLException {
        DataAccessObject dao = new DataAccessObject();
        titleLabel.setText(book.getTitle());
        if (book.getBookCover() == null)
            bookCoverHolder.setImage(new Image("/images/book.png"));
        else
            bookCoverHolder.setImage(ProjectTools.loadImage(book.getBookCover()));
        authorLabel.setText(authorText);
        categoryLabel.setText(categoryText);
        isbn10Label.setText("isbn 10: " + book.getIsbn10());
        isbn13Label.setText("isbn 13: " + book.getIsbn13());
        publisherLabel.setText("Publisher: " + dao.findById(PublishingCompany.class, book.getPublishingCompany()).getName());
        detailsLabel.setText(book.getDescription());

        String bookQuery = "SELECT COUNT(*) FROM BOOKS" +
                " WHERE LIBRARY_ID = " + LoginController.currentlyLoggedUser.getLibraryID() +
                " AND TITLE = \"" + book.getTitle() + "\"";
        String nowBorrowedQuery = "SELECT COUNT(*) FROM BOOKS" +
                " WHERE LIBRARY_ID = " + LoginController.currentlyLoggedUser.getLibraryID() +
                " AND TITLE = \"" + book.getTitle() + "\"" +
                " AND AVAILABILITY = FALSE ";
        String borrowingNumberQuery = "SELECT COUNT(*) FROM BORROWED_BOOKS, BOOKS" +
                " WHERE BOOKS.LIBRARY_ID = " + LoginController.currentlyLoggedUser.getLibraryID() +
                " AND BOOK_ID = BOOKS.ID" +
                " AND TITLE = \"" + book.getTitle() + "\"";

        String bookResult = dao.countRecords(Book.class, bookQuery);
        String nowBorrowedResult = dao.countRecords(Book.class, nowBorrowedQuery);
        String borrowingNumberResult = dao.countRecords(BorrowedBook.class, borrowingNumberQuery);

        bookAmount.setText(bookResult);
        readyToBorrow.setText(nowBorrowedResult);
        borrowingNumber.setText(borrowingNumberResult);
    }

}
