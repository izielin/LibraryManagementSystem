package assistant.UI.Controllers.ListControllers;

import assistant.FXModels.*;
import assistant.Utils.Converters;
import assistant.Utils.ProjectTools;
import assistant.Utils.ApplicationException;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.*;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static assistant.Utils.AlertMaker.showJFXButton;

public class ListsController {
    public StackPane rootPane;
    public AnchorPane mainAnchorPane;
    @FXML
    private TableView<AuthorFXModel> authorTable;
    @FXML
    private TableColumn<AuthorFXModel, String> authorFistName;
    @FXML
    private TableColumn<AuthorFXModel, String> authorMiddleName;
    @FXML
    private TableColumn<AuthorFXModel, String> authorLastName;
    @FXML
    private TableView<CategoryFXModel> categoryTable;
    @FXML
    private TableColumn<CategoryFXModel, String> categoryName;
    @FXML
    private TableView<CityFXModel> cityTable;
    @FXML
    private TableColumn<CityFXModel, String> cityName;
    @FXML
    private TableView<PublishingCompanyFXModel> publisherTable;
    @FXML
    private TableColumn<PublishingCompanyFXModel, String> publisherName;

    DataAccessObject dao = new DataAccessObject();

    public void initialize() {
        try {
            System.out.println("work3");

            initAuthorTable();
            initCategoryTable();
            initCityTable();
            initPublisherTable();
            System.out.println("work4");

        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    private void initPublisherTable() throws ApplicationException {
        ObservableList<PublishingCompanyFXModel> observableArrayList = FXCollections.observableArrayList();
        List<PublishingCompany> publishers = dao.queryForAll(PublishingCompany.class);
        observableArrayList.clear();
        publishers.forEach(publisher -> {
            PublishingCompanyFXModel fxModel = Converters.convertToPublisherFX(publisher);
            observableArrayList.add(fxModel);
        });

        publisherTable.setItems(observableArrayList);
        publisherName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    }

    private void initCityTable() throws ApplicationException {
        ObservableList<CityFXModel> observableArrayList = FXCollections.observableArrayList();
        List<City> cities = dao.queryForAll(City.class);
        observableArrayList.clear();
        cities.forEach(city -> {
            CityFXModel fxModel = Converters.convertToCityFXModel(city);
            observableArrayList.add(fxModel);
        });

        cityTable.setItems(observableArrayList);
        cityName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    }

    private void initCategoryTable() throws ApplicationException {
        ObservableList<CategoryFXModel> observableArrayList = FXCollections.observableArrayList();
        List<Category> categories = dao.queryForAll(Category.class);
        observableArrayList.clear();
        categories.forEach(category -> {
            CategoryFXModel fxModel = Converters.convertToCategoryFx(category);
            observableArrayList.add(fxModel);
        });

        categoryTable.setItems(observableArrayList);
        categoryName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    }

    private void initAuthorTable() throws ApplicationException {
        ObservableList<AuthorFXModel> observableArrayList = FXCollections.observableArrayList();
        List<Author> authors = dao.queryForAll(Author.class);
        observableArrayList.clear();
        authors.forEach(author -> {
            AuthorFXModel fxModel = Converters.convertToAuthorFXModel(author);
            observableArrayList.add(fxModel);
        });

        authorTable.setItems(observableArrayList);
        authorFistName.setCellValueFactory(cellData -> cellData.getValue().fistNameProperty());
        authorMiddleName.setCellValueFactory(cellData -> cellData.getValue().middleNameProperty());
        authorLastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
    }


    public void executeRefresh() {
        System.out.println("work2");

        initialize();
    }

    public void addCity() {
        ProjectTools.loadAddWindow("/fxml/addViews/AddCity.fxml", ListsController.this);
    }

    public void addCountry() {
        ProjectTools.loadAddWindow("/fxml/addViews/AddCountry.fxml", ListsController.this);
    }

    public void addPublishingCompany() {
        ProjectTools.loadAddWindow("/fxml/addViews/AddPublisher.fxml", ListsController.this);
    }

    public void addCategory() {
        ProjectTools.loadAddWindow("/fxml/addViews/AddCategory.fxml", ListsController.this);
    }

    public void addAuthor() {
        ProjectTools.loadAddWindow("/fxml/addViews/AddAuthor.fxml", ListsController.this);
    }


    @FXML
    private void handleRowData(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) { // checking the number of mouse clicks on a single row
            String tableViewID = ((TableView<?>) mouseEvent.getSource()).getId();
            TableView<?> tableView = (TableView<?>) mainAnchorPane.lookup("#" + tableViewID);
            JFXButton yesButton = new JFXButton("YES");
            JFXButton cancelButton = new JFXButton("Cancel");

            switch (tableViewID) {
                case "authorTable" -> {
                    AuthorFXModel rowData = (AuthorFXModel) tableView.getSelectionModel().getSelectedItem();
                    if (rowData != null) {
                        yesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                            try {
                                deleteAuthorById(rowData.getId());
                                executeRefresh();
                            } catch (ApplicationException | SQLException e) {
                                e.printStackTrace();
                            }

                        });
                    }
                }
                case "categoryTable" -> {
                    CategoryFXModel rowData = (CategoryFXModel) tableView.getSelectionModel().getSelectedItem();
                    if (rowData != null) {
                        yesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                            try {
                                deleteCategoryById(rowData.getId());
                                executeRefresh();
                            } catch (ApplicationException | SQLException e) {
                                e.printStackTrace();
                            }

                        });
                    }
                }
                case "cityTable" -> {
                    CityFXModel rowData = (CityFXModel) tableView.getSelectionModel().getSelectedItem();
                    if (rowData != null) {
                        yesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                            try {
                                deleteCityById(rowData.getId());
                                executeRefresh();
                            } catch (ApplicationException | SQLException e) {
                                e.printStackTrace();
                            }

                        });
                    }
                }
                default -> {
                    PublishingCompanyFXModel rowData = (PublishingCompanyFXModel) tableView.getSelectionModel().getSelectedItem();
                    if (rowData != null) {
                        yesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                            try {
                                deletePublisherById(rowData.getId());
                                executeRefresh();
                            } catch (ApplicationException | SQLException e) {
                                e.printStackTrace();
                            }

                        });
                    }
                }
            }

            showJFXButton(rootPane, mainAnchorPane, Arrays.asList(yesButton, cancelButton), "Confirm delete operation",
                    "Are you sure you want to delete selected item?");

        }
    }

    public void deleteCategoryById(int id) throws ApplicationException, SQLException {
        DataAccessObject dao = new DataAccessObject();
        dao.deleteById(Category.class, id);
        dao.deleteByColumnName(Book.class, "CATEGORY_ID", id);
    }

    public void deleteCityById(int id) throws ApplicationException, SQLException {
        DataAccessObject dao = new DataAccessObject();

        List<Library> libraryList = dao.findByColumnName(Library.class, "CITY_ID", id);
        libraryList.forEach(l -> {
            try {
                dao.deleteByColumnName(Book.class, "LIBRARY_ID", l.getId()); // delete books connected with a deleted library
                dao.deleteByColumnName(User.class, "LIBRARY_ID", l.getId()); // delete users connected with a deleted library
            } catch (ApplicationException | SQLException e) {
                e.printStackTrace();
            }
        });

        dao.deleteById(City.class, id);
        dao.deleteByColumnName(Library.class, "CITY_ID", id); // delete library by city id
        dao.deleteByColumnName(User.class, "CITY_ID", id); // delete user by city id
    }

    public void deletePublisherById(int id) throws ApplicationException, SQLException {
        DataAccessObject dao = new DataAccessObject();
        dao.deleteById(PublishingCompany.class, id);
        dao.deleteByColumnName(Book.class, "PUBLISHER_ID", id);
    }

    public void deleteAuthorById(int id) throws ApplicationException, SQLException {
        DataAccessObject dao = new DataAccessObject();
        dao.deleteById(Author.class, id);
        dao.deleteByColumnName(Book.class, "AUTHOR_ID", id);
    }

}