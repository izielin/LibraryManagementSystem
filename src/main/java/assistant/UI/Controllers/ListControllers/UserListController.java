package assistant.UI.Controllers.ListControllers;

import assistant.FXModels.UserFXModel;
import assistant.UI.Controllers.AddControllers.AddUserController;
import assistant.UI.Controllers.LoginController;
import assistant.UI.Controllers.MainController;
import assistant.Utils.Converters;
import assistant.Utils.ApplicationException;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.BorrowedBook;
import assistant.database.models.Library;
import assistant.database.models.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static assistant.Utils.ProjectTools.getResourceBundle;
import static assistant.Utils.AlertMaker.showJFXButton;

public class UserListController implements Initializable {
    @FXML
    private Text selectedUserID;
    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private VBox detailsData;
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
        observableArrayList.clear();
        DataAccessObject dao = new DataAccessObject();
        List<User> users = dao.queryForAll(User.class);
        users.forEach(user -> {
            if (LoginController.currentlyLoggedUser.getUserType().equals("ADMIN")) {
                UserFXModel userFx = Converters.convertToUserFx(user);
                observableArrayList.add(userFx);
            } else {
                if (user.getLibraryID()== LoginController.currentlyLoggedUser.getLibraryID() && user.getUserType().equals("MEMBER")) {
                    UserFXModel userFx = Converters.convertToUserFx(user);
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
        DataAccessObject dao = new DataAccessObject();
        if (mouseEvent.getClickCount() == 2) { // checking the number of mouse clicks on a single row
            UserFXModel rowData = tableView.getSelectionModel().getSelectedItem(); // creating User object from data in selected row
            if (rowData == null) { // check if selected row is not null
                showJFXButton(rootPane, mainAnchorPane, new ArrayList<>(), "No user selected", "Please select row with user data");
            } else {
                //passing values to text fields
                selectedUserID.setText(Integer.toString(rowData.getId()));
                selectedUserName.setText(rowData.getFirstName() + " " + rowData.getLastName());
                selectedUserMobile.setText(rowData.getMobile());
                selectedUserEmail.setText(rowData.getEmail());
                selectedUserStreet.setText(rowData.getStreet());

                switch (rowData.getUserType()) {
                    case "EMPLOYEE" -> selectedUserType.setText("PRACOWNIK");
                    case "ADMIN" -> selectedUserType.setText("ADMINISTRATOR");
                    default -> selectedUserType.setText("CZYTELNIK");
                }

                try {
                    selectedUserLibraryName.setText(dao.findById(Library.class, rowData.getLibraryID()).getName());
                } catch (ApplicationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void executeUpdateAction() {
        if (selectedUserID.getText().isEmpty()) {
            showJFXButton(rootPane, mainAnchorPane, new ArrayList<>(), "No user selected", "Please select row with user data");
        } else {
            try {
                DataAccessObject dao = new DataAccessObject();
                String userID = selectedUserID.getText(); // get id of selected user
                System.out.println(userID);
                User user = dao.findById(User.class, Integer.parseInt(userID));

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addViews/AddUser.fxml"), getResourceBundle());
                Parent parent = loader.load();

                AddUserController controller = loader.getController();
                if (user != null) controller.inflateUI(Converters.convertToUserFx(user));
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.show();

                stage.setOnHidden((e) -> {
                    try {
                        tableView.setItems(loadData());
                        clearFields();
                    } catch (ApplicationException applicationException) {
                        applicationException.printStackTrace();
                    }
                }); //refresh table

            } catch (IOException | ApplicationException e) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void executeDeleteAction() {
        if (!selectedUserID.getText().isEmpty()) {
            String userID = selectedUserID.getText(); // get id of selected user
            DataAccessObject dao = new DataAccessObject();
            JFXButton yesButton = new JFXButton("YES");
            JFXButton cancelButton = new JFXButton("Cancel");
            yesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                try {
                    //checking if the user being deleted has a borrowed book
                    List<BorrowedBook> list = dao.findByColumnName(BorrowedBook.class, "USER_ID", Integer.parseInt(userID));
                    if (list.isEmpty()) {
                        dao.deleteById(User.class, Integer.parseInt(userID));
                        clearFields();
                        tableView.setItems(loadData());

                    } else {
                        showJFXButton(rootPane, mainAnchorPane, new ArrayList<>(), "Error", "User cannot be deleted. Cause: has a borrowed book!");
                    }
                } catch (ApplicationException | SQLException e) {
                    e.printStackTrace();
                }

            });
            showJFXButton(rootPane, mainAnchorPane, Arrays.asList(yesButton, cancelButton), "Confirm delete operation",
                    "Are you sure you want to delete selected item?");
        }
    }

    private void clearFields() {
        selectedUserID.setText("");
        selectedUserName.setText("");
        selectedUserMobile.setText("");
        selectedUserEmail.setText("");
        selectedUserStreet.setText("");
        selectedUserCity.setText("");
        selectedUserType.setText("");
        selectedUserLibraryName.setText("");
    }
}
