package assistant.UI.Controllers.AddControllers;

import assistant.FXModels.CityFXModel;
import assistant.FXModels.LibraryFXModel;
import assistant.FXModels.UserFXModel;
import assistant.Utils.Initializers;
import assistant.Utils.exceptions.ApplicationException;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.City;
import assistant.database.models.Library;
import assistant.database.models.User;
import com.jfoenix.controls.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;


import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static assistant.alert.AlertMaker.showJFXButton;

public class AddUserController implements Initializable {

    @FXML
    private JFXTextField usernameInput;
    @FXML
    private JFXTextField passwordInput;
    @FXML
    private JFXCheckBox defaultCheckBox;
    @FXML
    private Text generatedUsername;
    @FXML
    private Text generatedPassword;
    @FXML
    private VBox personalData;
    @FXML
    private VBox libraryData;
    @FXML
    private VBox accountData;
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private StackPane rootPane;
    @FXML
    private JFXTextField firstNameInput;
    @FXML
    private JFXTextField lastNameInput;
    @FXML
    private JFXTextField mobileInput;
    @FXML
    private JFXTextField emailInput;
    @FXML
    private JFXTextField streetInput;
    @FXML
    private JFXTextField zipCodeInput;
    @FXML
    private ComboBox<CityFXModel> cityComboBox;
    @FXML
    private JFXDatePicker selectedDate;
    @FXML
    private JFXRadioButton memberRadio;
    @FXML
    private ToggleGroup userTypeGroup;
    @FXML
    private JFXRadioButton employeeRadio;
    @FXML
    private JFXRadioButton adMinRadio;
    @FXML
    private ComboBox<LibraryFXModel> libraryComboBox;

    private static final ObservableList<CityFXModel> cityFXModelObservableList = FXCollections.observableArrayList();
    private static final ObservableList<LibraryFXModel> libraryFXModelObservableList = FXCollections.observableArrayList();

    private String defaultLoginData;
    private String oldPassword;
    Integer editedUserID = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Initializers.initCityList(cityFXModelObservableList);
            Initializers.initLibraryList(libraryFXModelObservableList);

            cityComboBox.setItems(cityFXModelObservableList);
            libraryComboBox.setItems(libraryFXModelObservableList);

            defaultCheckBox.selectedProperty().addListener(
                    (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                        DataAccessObject dao = new DataAccessObject();
                        try {

                            usernameInput.setDisable(true);
                            passwordInput.setDisable(true);

                            List<User> list = dao.queryForAll(User.class);
                            int lastID = list.get(list.size() - 1).getId(); // get last registered user id

                            defaultLoginData = "user" + (lastID + 1); // creating default login from template user + lastID +1

                            generatedUsername.setText(defaultLoginData);
                            generatedPassword.setText(defaultLoginData);

                        } catch (ApplicationException e) {
                            e.printStackTrace();
                        }
                    });

        } catch (ApplicationException e) {
            e.printStackTrace();
        }

    }

    public void saveAction() throws ApplicationException {
        DataAccessObject dao = new DataAccessObject();
        User user = null;
        if (editedUserID == null) {
            user = new User();

        } else {
            user = dao.findById(User.class, editedUserID);
        }

        City city = dao.findById(City.class, cityComboBox.getValue().getId());
        Library library = dao.findById(Library.class, libraryComboBox.getValue().getId());

        if (defaultCheckBox.isSelected()) {
            user.setUsername(defaultLoginData);
            user.setPassword(DigestUtils.sha1Hex(defaultLoginData));
        } else {
            user.setUsername(usernameInput.getText());
            if (passwordInput.isDisable()) {
                user.setPassword(oldPassword);
            } else {
                user.setPassword(DigestUtils.sha1Hex(passwordInput.getText()));
            }
        }

        user.setFirstName(firstNameInput.getText());
        user.setLastName(lastNameInput.getText());
        user.setMobile(mobileInput.getText());
        user.setEmail(emailInput.getText());
        user.setStreet(streetInput.getText());
        user.setZipCode(zipCodeInput.getText());
        user.setCity(city);
        String date = selectedDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        user.setRegistrationDate(date);
        RadioButton selectedRadioButton = (RadioButton) userTypeGroup.getSelectedToggle();
        String selectedValue = selectedRadioButton.getText();
        user.setUserType(selectedValue);
        user.setLibrary(library);

        dao.createOrUpdate(user);
        if (editedUserID != null) {
            JFXButton okButton = new JFXButton("Okay");
            okButton.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> ((Stage)usernameInput.getScene().getWindow()).close());
            showJFXButton(rootPane, mainAnchorPane, Arrays.asList(okButton), "Success", "User was successfully created!");
        }
        else
            clearFields();
    }

    private void clearFields() {
        // clear all fields in form
        List<VBox> boxes = List.of(personalData, libraryData, accountData);
        boxes.forEach(vBox -> {
            for (Node node : vBox.getChildren()) {
                System.out.println(node);
                if (node instanceof Text) {
                    ((Text) node).setText("");
                }
                if (node instanceof JFXTextField)
                    ((JFXTextField) node).clear();
                if (node instanceof CheckBox)
                    ((CheckBox) node).setSelected(false);

                if (node instanceof DatePicker) {
                    ((DatePicker) node).setValue(null);
                    ((DatePicker) node).getEditor().clear();
                }
            }
        });
        userTypeGroup.selectToggle(null);
    }

    public void inflateUI(UserFXModel user) {
        editedUserID = user.getId();
        firstNameInput.textProperty().bindBidirectional(user.firstNameProperty());
        lastNameInput.textProperty().bindBidirectional(user.lastNameProperty());
        mobileInput.textProperty().bindBidirectional(user.mobileProperty());
        emailInput.textProperty().bindBidirectional(user.emailProperty());
        streetInput.textProperty().bindBidirectional(user.streetProperty());
        zipCodeInput.textProperty().bindBidirectional(user.zipCodeProperty());

        cityComboBox.setItems(cityFXModelObservableList);
        cityComboBox.valueProperty().bindBidirectional(user.cityProperty());


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = user.getRegistrationDate();
        LocalDate localDate = LocalDate.parse(date, formatter);
        selectedDate.setValue(localDate);

        switch (user.getUserType()) {
            case "EMPLOYEE" -> employeeRadio.setSelected(true);
            case "ADMIN" -> adMinRadio.setSelected(true);
            default -> memberRadio.setSelected(true);
        }

        libraryComboBox.setItems(libraryFXModelObservableList);
        libraryComboBox.valueProperty().bindBidirectional(user.libraryProperty());

        usernameInput.setText(user.getUsername());
        passwordInput.setDisable(true);
        defaultCheckBox.setDisable(true);
        generatedUsername.opacityProperty().setValue(0);
        generatedPassword.opacityProperty().setValue(0);

        oldPassword = user.getPassword();
    }
}
