package assistant.UI.Controllers;

import assistant.database.DatabaseHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemberListController implements Initializable {
    ObservableList<Member> list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Member> tableView;
    @FXML
    private TableColumn<Member, String> idColumn;
    @FXML
    private TableColumn<Member, String> nameColumn;
    @FXML
    private TableColumn<Member, String> mobileColumn;
    @FXML
    private TableColumn<Member, String> emailColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initColumn();
        loadData();
    }

    private void initColumn() {
        nameColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().nameProperty);
        idColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().idProperty);
        mobileColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().mobileProperty);
        emailColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().emailProperty);
    }

    private void loadData() {
        DatabaseHandler handler = DatabaseHandler.getInstance();
        String query = "SELECT * FROM MEMBER";
        ResultSet resultSet = handler.execQuery(query);
        try {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String mobile = resultSet.getString("mobile");
                String id = resultSet.getString("id");
                String email = resultSet.getString("email");

                // creating member object contains all data from db which can be added to the ObservableList
                Member member = new Member(name, id, mobile, email);
                list.add(member);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableView.getItems().setAll(list); // associating list containing all members records from db with table
    }


    public static class Member {
        private final SimpleStringProperty nameProperty;
        private final SimpleStringProperty idProperty;
        private final SimpleStringProperty mobileProperty;
        private final SimpleStringProperty emailProperty;

        Member(String name, String id, String mobile, String email) {
            this.nameProperty = new SimpleStringProperty(name);
            this.idProperty = new SimpleStringProperty(id);
            this.mobileProperty = new SimpleStringProperty(mobile);
            this.emailProperty = new SimpleStringProperty(email);
        }

        public String getNameProperty() {
            return nameProperty.get();
        }

        public String getIdProperty() {
            return idProperty.get();
        }

        public String getMobileProperty() {
            return mobileProperty.get();
        }

        public String getEmailProperty() {
            return emailProperty.get();
        }
    }

}
