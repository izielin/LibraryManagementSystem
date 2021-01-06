package assistant.UI.Controllers.AddControllers;

import assistant.FXModels.CityFXModel;
import assistant.FXModels.UserFXModel;
import assistant.Utils.Converters;
import assistant.Utils.exceptions.ApplicationException;
import assistant.database.dao.CommonDao;
import assistant.database.models.City;
import assistant.database.models.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

public class AddUserController implements Initializable {


    private boolean isInEditMode = false;

    @FXML
    private JFXTextField memberUsername;
    @FXML
    private JFXTextField memberFirstName;
    @FXML
    private JFXTextField memberLastName;
    @FXML
    private JFXTextField memberStreet;
    @FXML
    private JFXTextField memberZipCode;
    @FXML
    private JFXComboBox<CityFXModel> memberCity;

    @FXML
    private TableColumn<UserFXModel, String> mobileColumn;
    @FXML
    private TableColumn<UserFXModel, String> emailColumn;
    @FXML
    private TableColumn<UserFXModel, String> streetColumn;
    @FXML
    private TableColumn<UserFXModel, String> zipCodeColumn;
    @FXML
    private TableColumn<UserFXModel, City> cityColumn;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXTextField memberName;
    @FXML
    private JFXTextField memberID;
    @FXML
    private JFXTextField memberMobile;
    @FXML
    private JFXTextField memberEmail;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private JFXButton saveAndCloseButton;

    @FXML
    private BorderPane mainBorderPane;

    private ObjectProperty<UserFXModel> userFXModel = new SimpleObjectProperty<>(new UserFXModel());
    private static ObservableList<CityFXModel> cityFXModelObservableList = FXCollections.observableArrayList();







    public void executeSaveAction() throws ApplicationException {
        User user = Converters.convertToUser(getUserFXModel());
        CommonDao commonDao = new CommonDao();
        City city = commonDao.findById(City.class, getUserFXModel().getCity().getId());

        user.setCity(city);
        commonDao.createOrUpdate(user);
    }

//    public void executeSaveAction(ActionEvent event) {
//        String name = memberName.getText();
//        String id = memberID.getText();
//        String mobile = memberMobile.getText();
//        String email = memberEmail.getText();
//
//        if (id.isEmpty() || name.isEmpty() || mobile.isEmpty() || email.isEmpty()) {
//            showSimpleAlert("error", "", "", "Please enter data in all fields");
//        } else {
//            if (isInEditMode) {
//                Member member = new Member(name, id, mobile, email);
//                if (databaseHandler.updateMember(member)) {
//                    showSimpleAlert("information", "Success", "", "Member:" + member.getNameProperty() + " was successfully updated");
//                    ((Stage) memberID.getScene().getWindow()).close();
//                } else {
//                    showSimpleAlert("error", "Error", "", "Something went wrong");
//                }
//            } else {
//                String action = "INSERT INTO MEMBER VALUES (" +
//                        "'" + id + "'," +
//                        "'" + name + "'," +
//                        "'" + mobile + "'," +
//                        "'" + email + "'" +
//                        ")";
//                System.out.println(action);
//                if (databaseHandler.execAction(action)) {
//                    showSimpleAlert("information", "", "", "Member: " + name + "was successfully added to database");
//                    memberEmail.clear();
//                    memberID.clear();
//                    memberName.clear();
//                    memberMobile.clear();
//                    try {
//                        Button button = (Button) event.getSource();
//                        if (button.getId().equals("saveAndCloseButton"))
//                            ((Stage) memberID.getScene().getWindow()).close();
//                    } catch (ClassCastException ignored) {
//                    }
//                } else {
//                    showSimpleAlert("error", "", "", "Something went wrong");
//                }
//            }
//        }
//    }

    @FXML
    private void executeCancelAction() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

//    public void inflateUI(Member member) {
//        memberName.setText(member.getNameProperty());
//        memberID.setText(member.getIdProperty());
//        memberMobile.setText(member.getMobileProperty());
//        memberEmail.setText(member.getEmailProperty());
//        memberID.setEditable(false);
//        isInEditMode = true;
//    }

    /*
    @FXML
    private void executeMemberDelete() {
        //Fetch the selected row
        MemberListController.Member selectedForDeletion = tableView.getSelectionModel().getSelectedItem();
        if (selectedForDeletion == null) {
            showSimpleAlert("error","No member selected", "Please select a member for deletion.","");
            return;
        }
        if (db.getInstance().isMemberHasAnyBooks(selectedForDeletion)) {
            showSimpleAlert("error","Cant be deleted", "This member has some books.","");
        }else {
            Optional<ButtonType> response =alertConfirm("Deleting book", "Are you sure want to delete " + selectedForDeletion.getNameProperty() + " ?","");

        if (response.orElse(null) == ButtonType.OK) {
            boolean result = db.getInstance().deleteMember(selectedForDeletion);
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



    public void deleteSelectedMember() {
        // check if selectedBookID text field is empty, if not, it means that the book to be deleted has been selected
        if (!selectedMemberID.getText().isEmpty()) {
            String memberID = selectedMemberID.getText(); // get id of selected book
            String query = "SELECT * FROM MEMBER WHERE id = '" + memberID + "'";

            ResultSet resultSet = db.getInstance().execQuery(query);
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

                            if (db.getInstance().deleteMember(member)) { // execute deleting operation
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
            ResultSet resultSet = db.getInstance().execQuery(query);
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
                UserController controller = loader.getController();
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
     */

    public void handleRowData(MouseEvent mouseEvent) {
    }

//    public static class User {
//        private final SimpleStringProperty idProperty;
//        private final SimpleStringProperty usernameProperty;
//        private final SimpleStringProperty passwordProperty;
//        private final SimpleStringProperty firstNameProperty;
//        private final SimpleStringProperty lastNameProperty;
//        private final SimpleStringProperty mobileProperty;
//        private final SimpleStringProperty emailProperty;
//        private final SimpleStringProperty streetProperty;
//        private final SimpleStringProperty zipCodeProperty;
//        private final ObjectProperty<City>  cityProperty;
//        private final SimpleStringProperty  userTypeProperty;
//        private final ObjectProperty<Library>  libraryProperty;
//
//        User(String id, String username, String password, String firstName, String lastName,
//             String mobile, String email, String street, String zipCode, City city,
//             String userType, Library library) {
//            this.idProperty = new SimpleStringProperty(id);
//            this.usernameProperty = new SimpleStringProperty(username);
//            this.passwordProperty = new SimpleStringProperty(password);
//            this.firstNameProperty = new SimpleStringProperty(firstName);
//            this.lastNameProperty = new SimpleStringProperty(lastName);
//            this.mobileProperty = new SimpleStringProperty(mobile);
//            this.emailProperty = new SimpleStringProperty(email);
//            this.streetProperty = new SimpleStringProperty(street);
//            this.zipCodeProperty = new SimpleStringProperty(zipCode);
//            this.cityProperty = new SimpleObjectProperty<>(city);
//            this.userTypeProperty = new SimpleStringProperty(username);
//            this.libraryProperty = new SimpleObjectProperty<>(library);
//        }
//
//        public String getIdProperty() {
//            return idProperty.get();
//        }
//
//        public SimpleStringProperty idPropertyProperty() {
//            return idProperty;
//        }
//
//        public void setIdProperty(String idProperty) {
//            this.idProperty.set(idProperty);
//        }
//
//        public String getUsernameProperty() {
//            return usernameProperty.get();
//        }
//
//        public SimpleStringProperty usernamePropertyProperty() {
//            return usernameProperty;
//        }
//
//        public void setUsernameProperty(String usernameProperty) {
//            this.usernameProperty.set(usernameProperty);
//        }
//
//        public String getPasswordProperty() {
//            return passwordProperty.get();
//        }
//
//        public SimpleStringProperty passwordPropertyProperty() {
//            return passwordProperty;
//        }
//
//        public void setPasswordProperty(String passwordProperty) {
//            this.passwordProperty.set(passwordProperty);
//        }
//
//        public String getFirstNameProperty() {
//            return firstNameProperty.get();
//        }
//
//        public SimpleStringProperty firstNamePropertyProperty() {
//            return firstNameProperty;
//        }
//
//        public void setFirstNameProperty(String firstNameProperty) {
//            this.firstNameProperty.set(firstNameProperty);
//        }
//
//        public String getLastNameProperty() {
//            return lastNameProperty.get();
//        }
//
//        public SimpleStringProperty lastNamePropertyProperty() {
//            return lastNameProperty;
//        }
//
//        public void setLastNameProperty(String lastNameProperty) {
//            this.lastNameProperty.set(lastNameProperty);
//        }
//
//        public String getMobileProperty() {
//            return mobileProperty.get();
//        }
//
//        public SimpleStringProperty mobilePropertyProperty() {
//            return mobileProperty;
//        }
//
//        public void setMobileProperty(String mobileProperty) {
//            this.mobileProperty.set(mobileProperty);
//        }
//
//        public String getEmailProperty() {
//            return emailProperty.get();
//        }
//
//        public SimpleStringProperty emailPropertyProperty() {
//            return emailProperty;
//        }
//
//        public void setEmailProperty(String emailProperty) {
//            this.emailProperty.set(emailProperty);
//        }
//
//        public String getStreetProperty() {
//            return streetProperty.get();
//        }
//
//        public SimpleStringProperty streetPropertyProperty() {
//            return streetProperty;
//        }
//
//        public void setStreetProperty(String streetProperty) {
//            this.streetProperty.set(streetProperty);
//        }
//
//        public String getZipCodeProperty() {
//            return zipCodeProperty.get();
//        }
//
//        public SimpleStringProperty zipCodePropertyProperty() {
//            return zipCodeProperty;
//        }
//
//        public void setZipCodeProperty(String zipCodeProperty) {
//            this.zipCodeProperty.set(zipCodeProperty);
//        }
//
//        public City getCityProperty() {
//            return cityProperty.get();
//        }
//
//        public ObjectProperty<City> cityPropertyProperty() {
//            return cityProperty;
//        }
//
//        public void setCityProperty(City cityProperty) {
//            this.cityProperty.set(cityProperty);
//        }
//
//        public String getUserTypeProperty() {
//            return userTypeProperty.get();
//        }
//
//        public SimpleStringProperty userTypePropertyProperty() {
//            return userTypeProperty;
//        }
//
//        public void setUserTypeProperty(String userTypeProperty) {
//            this.userTypeProperty.set(userTypeProperty);
//        }
//
//        public Library getLibraryProperty() {
//            return libraryProperty.get();
//        }
//
//        public ObjectProperty<Library> libraryPropertyProperty() {
//            return libraryProperty;
//        }
//
//        public void setLibraryProperty(Library libraryProperty) {
//            this.libraryProperty.set(libraryProperty);
//        }
//    }


    public UserFXModel getUserFXModel() {
        return userFXModel.get();
    }

    public ObjectProperty<UserFXModel> userFXModelProperty() {
        return userFXModel;
    }

    public void setUserFXModel(UserFXModel userFXModel) {
        this.userFXModel.set(userFXModel);
    }

    public static ObservableList<CityFXModel> getCityFXModelObservableList() {
        return cityFXModelObservableList;
    }

    public static void setCityFXModelObservableList(ObservableList<CityFXModel> cityFXModelObservableList) {
        AddUserController.cityFXModelObservableList = cityFXModelObservableList;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
