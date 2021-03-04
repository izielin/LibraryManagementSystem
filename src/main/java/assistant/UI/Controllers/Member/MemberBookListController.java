package assistant.UI.Controllers.Member;

import assistant.UI.Controllers.LoginController;
import assistant.Utils.ApplicationException;
import assistant.Utils.ProjectTools;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.*;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static assistant.Utils.ProjectTools.getResourceBundle;

public class MemberBookListController implements Initializable {

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private Label titleLabel;

    @FXML
    private ScrollPane scrollPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            generateBorrowedBooksList();
        } catch (ApplicationException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void generateBorrowedBooksList() throws ApplicationException, SQLException {
        DataAccessObject dao = new DataAccessObject();
        GridPane table = new GridPane();
        table.setHgap(20);
        table.setVgap(20);
        String query = "SELECT DISTINCT * FROM BOOKS" +
                " WHERE LIBRARY_ID = " + LoginController.currentlyLoggedUser.getLibraryID() +
                " GROUP BY TITLE ";
        List<String[]> queryResult = dao.executeRawQuery(Book.class, query);
        List<Node> nodes = new ArrayList<>();
        queryResult.forEach(element -> {
            try {
                nodes.add(generateBookCell(dao.findById(Book.class, Integer.parseInt(element[0]))));

            } catch (ApplicationException | IOException e) {
                e.printStackTrace();
            }
        });
        int rows;
        if (queryResult.size() < 3)
            rows = 1;
        else
            rows = (int) (Math.round(queryResult.size() / 3.0));

        // grid pane
        int k = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 3; j++) {
                if (k < nodes.size())
                    table.add(nodes.get(k++), j, i);
            }
        }
        scrollPane.setContent(table);
    }

    private Node generateBookCell(Book book) throws ApplicationException, IOException {
        DataAccessObject dao = new DataAccessObject();
        HBox mainHBox = new HBox();
        mainHBox.setMaxWidth(350);
        mainHBox.setSpacing(20);

        ImageView imageView = new ImageView();
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
        Image img;
        try {
            img = ProjectTools.loadImage(book.getBookCover());
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
        authorHolder.setText("Author: " + dao.findById(Author.class, book.getAuthor()).getFullName());
        categoryHolder.setText("Category: " + dao.findById(Category.class, book.getCategory()).getName());
        ISBNHolder.setText("ISBN: " + book.getIsbn13());

        JFXButton button = new JFXButton();
        button.setStyle("-fx-background-color: #fdc12a");
        button.setStyle("-fx-text-fill: black");
        button.setText("See details");
        button.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Member/BookDetails.fxml"), getResourceBundle());
                Parent parent = loader.load();

                BookDetailsController controller = loader.getController();
                controller.setData(book, authorHolder.getText(), categoryHolder.getText());
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (ApplicationException | SQLException | IOException e) {
                e.printStackTrace();
            }
        });


        contentVbox.getChildren().addAll(authorHolder, categoryHolder, ISBNHolder, button);
        mainVbox.getChildren().addAll(titleHolder, separator, contentVbox);
        mainHBox.getChildren().addAll(imageView, mainVbox);
        return mainHBox;
    }

}
