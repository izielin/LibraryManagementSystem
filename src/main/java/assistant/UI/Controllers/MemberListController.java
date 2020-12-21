package assistant.UI.Controllers;

import assistant.Utils.Utils;
import assistant.database.DatabaseHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static assistant.Utils.Utils.getResourceBundle;
import static assistant.alert.AlertMaker.alertConfirm;
import static assistant.alert.AlertMaker.showSimpleAlert;

public class MemberListController implements Initializable {
    ObservableList<Member> list = FXCollections.observableArrayList();
    private static final String FXML_ADD_MEMBER = "/fxml/AddMember.fxml";


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
    @FXML
    private TextField selectedMemberID;
    @FXML
    private TextField selectedMemberName;
    @FXML
    private TextField selectedMemberMobile;
    @FXML
    private TextField selectedMemberEmail;

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
        list.clear();
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

    @FXML
    private void executeMemberDelete() {
        //Fetch the selected row
        MemberListController.Member selectedForDeletion = tableView.getSelectionModel().getSelectedItem();
        if (selectedForDeletion == null) {
            showSimpleAlert("error","No member selected", "Please select a member for deletion.","");
            return;
        }
        if (DatabaseHandler.getInstance().isMemberHasAnyBooks(selectedForDeletion)) {
            showSimpleAlert("error","Cant be deleted", "This member has some books.","");
        }else {
            Optional<ButtonType> response =alertConfirm("Deleting book", "Are you sure want to delete " + selectedForDeletion.getNameProperty() + " ?","");

        if (response.orElse(null) == ButtonType.OK) {
            boolean result = DatabaseHandler.getInstance().deleteMember(selectedForDeletion);
            if (result) {
                showSimpleAlert("information","Book deleted", selectedForDeletion.getNameProperty() + " was deleted successfully.","");
                list.remove(selectedForDeletion);
            } else {
                showSimpleAlert("error", "Failed", selectedForDeletion.getNameProperty() + " could not be deleted", "");
            }
        } else {
            showSimpleAlert("error","Deletion cancelled", "Deletion process cancelled", "");
        }
        }
    }

    @FXML
    private void handleRowData(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) { // checking the number of mouse clicks on a single row
            Member rowData = tableView.getSelectionModel().getSelectedItem(); // creating Book object from data in selected row
            if (rowData == null) { // check if selected row is not null
                showSimpleAlert("error", "No member selected", "No data to load", "Please select row with book data");
            } else {
                //passing values to text fields
                selectedMemberID.setText(rowData.getIdProperty());
                selectedMemberName.setText(rowData.getNameProperty());
                selectedMemberMobile.setText(rowData.getMobileProperty());
                selectedMemberEmail.setText(rowData.getEmailProperty());
            }
        }
    }

    public void deleteSelectedMember() {
        // check if selectedBookID text field is empty, if not, it means that the book to be deleted has been selected
        if (!selectedMemberID.getText().isEmpty()) {
            String memberID = selectedMemberID.getText(); // get id of selected book
            String query = "SELECT * FROM MEMBER WHERE id = '" + memberID + "'";

            ResultSet resultSet = DatabaseHandler.getInstance().execQuery(query);
            try {
                while (resultSet.next()) {
                    // create Book object contains data form db
                    Member member = new Member(resultSet.getString("name"), resultSet.getString("id"),
                            resultSet.getString("mobile"), resultSet.getString("email"));

                    // create confirmation alert
                    Optional<ButtonType> response = alertConfirm("Confirm delete operation",
                            "Are you sure you want to delete the " + selectedMemberName.getText() + "?",
                            "Are you sure you want to delete member?");
                    if (response.orElse(null) == ButtonType.OK) {
                        // check if selected is not lent to someone

                            if (DatabaseHandler.getInstance().deleteMember(member)) { // execute deleting operation
                                showSimpleAlert("information", "Member deleted", "", "Member "
                                        + selectedMemberName.getText() + " was successfully deleted");
                                // clear text fields
                                selectedMemberName.clear();
                                selectedMemberID.clear();
                                selectedMemberMobile.clear();
                                selectedMemberEmail.clear();
                                executeRefresh();
                            } else
                                showSimpleAlert("error", "Failed", "", "Operation ended unsuccessfully");

                    } else
                        showSimpleAlert("information", "Cancelled", "", "Operation was cancelled");
                }
            } catch (SQLException ex) {
                Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            showSimpleAlert("error", "No member selected", "No data to load", "Please select row with member to delete");
        }
    }

    public void updateSelectedMember() {
        Member member = null;
        if (!selectedMemberID.getText().isEmpty()) {
            String memberID = selectedMemberID.getText(); // get id of selected book
            String query = "SELECT * FROM MEMBER WHERE id = '" + memberID + "'";
            ResultSet resultSet = DatabaseHandler.getInstance().execQuery(query);
            try {
                if (resultSet.next()) {
                    // create Book object contains data form db
                    member = new Member(resultSet.getString("name"), resultSet.getString("id"),
                            resultSet.getString("mobile"), resultSet.getString("email"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_ADD_MEMBER), getResourceBundle());
                Parent parent = loader.load();
                AddMemberController controller = loader.getController();
                if (member != null) controller.inflateUI(member);
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.show();
                Utils.setIcon(stage);

                stage.setOnHidden((e)-> executeRefresh()); //refresh table
            } catch (IOException e) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            showSimpleAlert("error", "No member selected", "No data to load", "Please select row with member to delete");
        }
    }

    public void executeRefresh() {
        loadData();
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
