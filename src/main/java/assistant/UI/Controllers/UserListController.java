package assistant.UI.Controllers;

import assistant.FXModels.CityFXModel;
import assistant.FXModels.UserFXModel;
import assistant.Utils.Initialize;
import assistant.Utils.converters.UserConverter;
import assistant.Utils.exceptions.ApplicationException;
import assistant.database.dao.CommonDao;
import assistant.database.models.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class UserListController implements Initializable {
    @FXML
    private TableView<UserFXModel> tableView;
    @FXML
    private TableColumn<UserFXModel, Integer> idColumn;
    @FXML
    private TableColumn<UserFXModel, String> usernameColumn;
    @FXML
    private TableColumn<UserFXModel, String> firstNameColumn;
    @FXML
    private TableColumn<UserFXModel, String> lastNameColumn;
    @FXML
    private Text selectedUserName;

    @FXML
    private Text selectedUserMobile;

    @FXML
    private Text selectedUserEmail;

    @FXML
    private Text selectedUserStreet;

    @FXML
    private Text selectedUserCity;

    @FXML
    private Text selectedUserType;

    @FXML
    private Text selectedUserLibraryName;

    @FXML
    private ToggleGroup searchGroup;

    @FXML
    private JFXRadioButton radioId;

    @FXML
    private JFXRadioButton radioName;

    @FXML
    private JFXTextField searchInput;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initColumn();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    private void initColumn() throws ApplicationException {
        tableView.setItems(loadData());
        idColumn.setCellValueFactory(cellDataFeatures -> new SimpleIntegerProperty(cellDataFeatures.getValue().getId()).asObject());
        usernameColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().usernameProperty());
        firstNameColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().lastNameProperty());
    }

    private ObservableList<UserFXModel> loadData() throws ApplicationException {
        ObservableList<UserFXModel> observableArrayList = FXCollections.observableArrayList();
        CommonDao dao = new CommonDao();
        List<User> users = dao.queryForAll(User.class);
        observableArrayList.clear();
        users.forEach(user -> {
            if (LoginController.currentlyLoggedUser.getUserType().equals("ADMIN")) {
                    UserFXModel userFx = UserConverter.convertToUserFx(user);
                    observableArrayList.add(userFx);
            } else {
                if (user.getLibrary().getId() == LoginController.currentlyLoggedUser.getLibrary().getId() && user.getUserType().equals("MEMBER")) {
                    UserFXModel userFx = UserConverter.convertToUserFx(user);
                    observableArrayList.add(userFx);
                }
            }
        });

        // Wrapping ObservableList in a FilteredList ( display all Data)
        FilteredList<UserFXModel> filteredData = new FilteredList<UserFXModel>(observableArrayList, b -> true);
        searchInput.setDisable(true);
        //setting filter predicate whenever the filter changes
        searchGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableSearchGroup, Toggle oldValueSearchGroup, Toggle newValueSearchGroup) {
                if (searchGroup.getSelectedToggle() != null) {
                    searchInput.setDisable(false);
                    searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
                        filteredData.setPredicate(user -> {

                            //if filter is empty, display all records
                            if (newValue == null || newValue.isEmpty()) {
                                return true;
                            }

                            String lowerCaseFilter = newValue.toLowerCase();
                            // check if filter is set on id or name
                            if (radioId.isSelected()) {
                                String userId = String.valueOf(user.getId());
                                if (userId.contains(lowerCaseFilter)) {
                                    return true; // filter matches id
                                } else {
                                    return false;
                                }
                            } else if (radioName.isSelected()) {
                                //compare first name ald last name of every person with filter text
                                if (user.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                                    return true; // filter matches first name
                                } else if (user.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                                    return true; // filter matches last name
                                } else {
                                    return false; // dose not match
                                }
                            }
                            return false;
                        });
                    });
                }
            }
        });


        return new SortedList<>((filteredData));
    }

    @FXML
    private void handleRowData(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) { // checking the number of mouse clicks on a single row
            UserFXModel rowData = tableView.getSelectionModel().getSelectedItem(); // creating Book object from data in selected row
            if (rowData == null) { // check if selected row is not null
//                showSimpleAlert("error", "No member selected", "No data to load", "Please select row with book data");
            } else {
                //passing values to text fields
                selectedUserName.setText(rowData.getFirstName() + " " + rowData.getLastName());
                selectedUserMobile.setText(rowData.getMobile());
                selectedUserEmail.setText(rowData.getEmail());
                selectedUserStreet.setText(rowData.getStreet());
                selectedUserCity.setText(rowData.getCity().getName());

                switch (rowData.getUserType()) {
                    case "EMPLOYEE":
                        selectedUserType.setText("PRACOWNIK");
                        break;
                    case "ADMIN":
                        selectedUserType.setText("ADMINISTRATOR");
                        break;
                    default:
                        selectedUserType.setText("CZYTELNIK");

                }

                selectedUserLibraryName.setText(rowData.getLibrary().getName());
            }
        }
    }
}
