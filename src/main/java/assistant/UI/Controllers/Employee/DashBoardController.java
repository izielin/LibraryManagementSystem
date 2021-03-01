package assistant.UI.Controllers.Employee;

import assistant.FXModels.UserFXModel;
import assistant.UI.Controllers.LoginController;
import assistant.Utils.ApplicationException;
import assistant.Utils.Converters;
import assistant.Utils.DonutChart;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.*;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

public class DashBoardController {
    public Label libraryName;
    public Label userAmount;
    public Label bookAmount;
    public Label borrowedAmount;
    public Label categoryAmount;
    public Label newUserToday;
    public Label newBookToday;
    public Label borrowedToday;
    public Label submittedToday;
    public AnchorPane donutChartPane;
    public AnchorPane pieChartPane;

    public BarChart<String, Integer> barChart;
    public BarChart<String, Integer> genderDifference;
    public LineChart<String, Integer> newUsers;
    public BarChart<String, Integer> submittedBorrowedBooks;
    public AreaChart<String, Integer> allUsers;
    public VBox progressbarVbox;

    public TableView<UserFXModel> employeeTable;
    public TableColumn<UserFXModel, UserFXModel> profPicture;
    public TableColumn<UserFXModel, String> userData;
    public TableColumn<UserFXModel, UserFXModel> details;
    public Label borrowedGrow;
    public Label authorsAmount;
    public Label booksGrow;
    public Label userGrow;
    public VBox messagesVbox;


    DataAccessObject dataAccessObject = new DataAccessObject();
    String[] colours;

    {
        colours = new String[]{"#fcf4a3", "#fde64b", "#fdc12a", "#fce205", "#fabd02", "#ffc30b", "#f9a602"};
    }

    public static final String ICON = "/icons/details.png";
    public static final Image INCREASE = new Image("/icons/up.png");
    public static final Image DECREASE = new Image("/icons/down.png");

    public void initialize() throws ApplicationException {
        initCharts();
        initColumns();
        initMessages();
    }

    private void initCharts() {
        DataAccessObject dao = new DataAccessObject();
        int libraryID = LoginController.currentlyLoggedUser.getLibraryID();
        try {
            libraryName.setText(dao.findById(Library.class, libraryID).getName());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        try {
            // area chart -> number of users for the last 12 months
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            XYChart.Series<String, Integer> areaSeries = new XYChart.Series<>();
            String query = "select date, USERS_NUMBER from reports " +
                    " where library_id = " + libraryID +
                    " AND DATE > \"" + LocalDate.now().minusMonths(12) + "\"";
            List<String[]> usersList = dataAccessObject.executeRawQuery(User.class, query);
            for (String[] strings : usersList) {
                LocalDate currentDate = LocalDate.parse(strings[0], formatter);
                System.out.println(currentDate + " " + strings[1] + "\n");
                areaSeries.getData().add(new XYChart.Data<>(currentDate.getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault()), Integer.parseInt(strings[1])));
            }
            allUsers.setTitle("Number of users for the last 12 months");
            allUsers.getData().add(areaSeries);

            // line chart -> user grow by month
            XYChart.Series<String, Integer> lineSeries = new XYChart.Series<>();
            int grow = Integer.parseInt(usersList.get(0)[1]);
            System.out.println(grow);
            lineSeries.getData().add(new XYChart.Data<>(LocalDate.parse(usersList.get(0)[0], formatter).getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()), grow));
            for (int i = 1; i < usersList.size(); i++) {
                if (Integer.parseInt(usersList.get(i)[1]) > Integer.parseInt(usersList.get(i - 1)[1])) {
                    grow = Integer.parseInt(usersList.get(i)[1]) - grow;
                } else {
                    grow = -(grow - Integer.parseInt(usersList.get(i)[1]));
                }
                System.out.println(grow);
                lineSeries.getData().add(new XYChart.Data<>(LocalDate.parse(usersList.get(i)[0], formatter).getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()), grow));
                grow = Integer.parseInt(usersList.get(i)[1]);
            }

            newUsers.setTitle("Increase of new users in the last 12 months");
            newUsers.getData().add(lineSeries);

            // returned & borrowed books in month
            String query2 = "select DATE, B_BOOKS_AMOUNT , R_BOOKS_AMOUNT, BOOKS_AMOUNT from reports " +
                    " where library_id = " + libraryID +
                    " AND DATE > \"" + LocalDate.now().minusYears(1) + "\"";

            List<String[]> bBooks = dataAccessObject.executeRawQuery(Report.class, query2);

            XYChart.Series<String, Integer> bBooksSeries = new XYChart.Series<>();
            XYChart.Series<String, Integer> rBooksSeries = new XYChart.Series<>();
            bBooksSeries.setName("Borrowed Books");
            rBooksSeries.setName("Returned Books");
            bBooks.forEach(element -> {
                bBooksSeries.getData().add(new XYChart.Data<>(
                        element[0], Integer.parseInt(element[1])));
                rBooksSeries.getData().add(new XYChart.Data<>(
                        element[0], Integer.parseInt(element[2])));
            });
            submittedBorrowedBooks.getData().addAll(bBooksSeries, rBooksSeries);
            submittedBorrowedBooks.setTitle("The ratio of borrowed and returned books");
            submittedBorrowedBooks.setBarGap(2);

            // gender bar chart
            String query3 = "select DATE, USERS_NUMBER , WOMAN_NUMBER from reports " +
                    " where library_id = " + libraryID +
                    " AND DATE > \"" + LocalDate.now().minusMonths(6) + "\"";

            List<String[]> genderStatistics = dataAccessObject.executeRawQuery(Report.class, query3);

            XYChart.Series<String, Integer> manSeries = new XYChart.Series<>();
            XYChart.Series<String, Integer> womanSeries = new XYChart.Series<>();
            manSeries.setName("Men");
            manSeries.setName("Women");
            genderStatistics.forEach(element -> {
                manSeries.getData().add(new XYChart.Data<>(
                        element[0], Integer.parseInt(element[1]) - Integer.parseInt(element[2])));
                womanSeries.getData().add(new XYChart.Data<>(
                        element[0], Integer.parseInt(element[2])));
            });
            genderDifference.getData().addAll(manSeries, womanSeries);
            genderDifference.setTitle("Division of users by gender");
            genderDifference.setBarGap(2);

            // donut chart
            ObservableList<PieChart.Data> donutChartData = FXCollections.observableArrayList();
            String queryBookByCategory = "SELECT CATEGORIES.NAME , COUNT(*) as numbers FROM BOOKS" +
                    " INNER JOIN CATEGORIES ON BOOKS.CATEGORY_ID = CATEGORIES.ID" +
                    " WHERE library_id = " + libraryID +
                    " GROUP BY CATEGORIES.NAME" +
                    " ORDER BY numbers desc";
            double allBooks = Integer.parseInt(dao.countRecords(Book.class, "SELECT  COUNT(*)  FROM BOOKS WHERE library_id = 1"));
            try {
                int others = 0;
                List<String[]> categoryList = dataAccessObject.executeRawQuery(User.class, queryBookByCategory);
                for (int i = 0; i < categoryList.size(); i++) {
                    if (i < 6) {
                        double percents = Integer.parseInt(categoryList.get(i)[1]) / allBooks;
                        donutChartData.add(new PieChart.Data(categoryList.get(i)[0], Integer.parseInt(categoryList.get(i)[1])));
                        generateProgressBar(categoryList.get(i)[0], categoryList.get(i)[1], percents, colours[i]);
                    } else {
                        others += Integer.parseInt(categoryList.get(i)[1]);
                    }
                }
                donutChartData.add(new PieChart.Data("Others", others));
                generateProgressBar("Others", Integer.toString(others), others / allBooks, colours[colours.length - 1]);

            } catch (ApplicationException | SQLException e) {
                e.printStackTrace();
            }
            DonutChart donutChart = new DonutChart(donutChartData);
            donutChart.setTitle("Amount of books broken down into categories");
            donutChart.setLabelsVisible(false);
            donutChartPane.getChildren().add(donutChart);
            donutChart.setLegendVisible(false);
            donutChart.setMaxHeight(250);

            for (int i = 0; i < donutChartData.size(); i++) {
                donutChartData.get(i).getNode().setStyle(
                        "-fx-pie-color: " + colours[i] + ";"
                );
            }

            // pie chart
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            String queryBookByAvailability = "SELECT availability, COUNT(*) FROM BOOKS " +
                    " WHERE library_id = " + libraryID +
                    " GROUP BY availability";
            try {
                List<String[]> statusList = dataAccessObject.executeRawQuery(User.class, queryBookByAvailability);
                statusList.forEach(availabilityStatus -> {
                    String status = (availabilityStatus[0].equals("1")) ? "Available Books" : "Borrowed Books";
                    pieChartData.add(new PieChart.Data(status, Integer.parseInt(availabilityStatus[1])));
                });


            } catch (ApplicationException | SQLException e) {
                e.printStackTrace();
            }
            PieChart pieChart = new PieChart(pieChartData);
            pieChart.setMaxHeight(300);
            pieChart.setLabelsVisible(false);
            pieChartPane.getChildren().add(pieChart);
            pieChart.setTitle("The ratio of borrowed books to those left in the library ");

            // bar chart
            String queryAuthors = "SELECT AUTHORS.ID, COUNT(*) as quantity FROM BOOKS, BORROWED_BOOKS, AUTHORS " +
                    "WHERE BOOKS.ID = BORROWED_BOOKS.BOOK_ID " +
                    "AND BOOKS.AUTHOR_ID = AUTHORS.ID " +
                    "AND BOOKS.LIBRARY_ID = " + libraryID +
                    " GROUP BY AUTHORS.ID " +
                    "ORDER BY quantity desc " +
                    "LIMIT 5";

            List<String[]> authorsList = dataAccessObject.executeRawQuery(Author.class, queryAuthors);
            XYChart.Series<String, Integer> borrowedBooksSeries = new XYChart.Series<>();
            borrowedBooksSeries.setName("No. of borrowed books by the author ");
            authorsList.forEach(element -> {
                try {
                    borrowedBooksSeries.getData().add(new XYChart.Data<>(
                            dao.findById(Author.class, Integer.parseInt(element[0])).getFullName(), Integer.parseInt(element[1])));
                } catch (ApplicationException e) {
                    e.printStackTrace();
                }
            });
            barChart.getData().add(borrowedBooksSeries);
            barChart.setTitle("The most popular authors");

            // top statistics
            String userNum = dataAccessObject.countRecords(User.class, "select count(*) from users where user_type='MEMBER' and library_id=" + libraryID);
            userAmount.setText(userNum);
            setPercentageIncrease(Double.parseDouble(userNum), Double.parseDouble(usersList.get(usersList.size() - 1)[1]), userGrow);

            String bookNum = dataAccessObject.countRecords(User.class, "select count(*) from books where library_id=" + libraryID);
            bookAmount.setText(bookNum);
            setPercentageIncrease(Double.parseDouble(bookNum), Double.parseDouble(bBooks.get(bBooks.size() - 1)[3]), booksGrow);

            String bBookNum = dataAccessObject.countRecords(User.class, "select count(*) from borrowed_books where library_id=" + libraryID + " and is_returned=0 and borrow_time >=\"" + LocalDate.now().withDayOfMonth(1) + "\"");
            borrowedAmount.setText(bBookNum);
            setPercentageIncrease(Double.parseDouble(bBookNum), Double.parseDouble(bBooks.get(bBooks.size() - 1)[1]), borrowedGrow);

            categoryAmount.setText(dataAccessObject.countRecords(User.class, "select count(*) from categories"));
            authorsAmount.setText(dataAccessObject.countRecords(Author.class, "select count(*) from authors"));

            // daily statistics
            newUserToday.setText(dataAccessObject.countRecords(User.class, "select count(*) from users where user_type='MEMBER' and library_id = " + libraryID + " and registration_date = '" + LocalDate.now() + "'"));
            newBookToday.setText(dataAccessObject.countRecords(User.class, "select count(*) from books where library_id = " + libraryID + " and added_date = '" + LocalDate.now() + "'"));
            submittedToday.setText(dataAccessObject.countRecords(User.class, "select count(*) from borrowed_books where library_id = " + libraryID + " and return_time = '" + LocalDate.now() + "'"));
            borrowedToday.setText(dataAccessObject.countRecords(User.class, "select count(*) from borrowed_books where library_id = " + libraryID + " and borrow_time = '" + LocalDate.now() + "'"));


        } catch (ApplicationException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void setPercentageIncrease(double grater, double lower, Label label) {
        int percents = (int) (((grater / lower) - 1) * 100);
        ImageView view;
        String text;
        if (percents > 0) {
            view = new ImageView(INCREASE);
            text = "+" + percents + "% from last month";
        } else {
            view = new ImageView(DECREASE);
            text = percents + "% from last month";
        }
        view.setFitHeight(20);
        view.setFitWidth(20);
        view.setPreserveRatio(true);

        label.setGraphic(view);
        label.setText(text);

    }

    private void initColumns() throws ApplicationException {
        employeeTable.setItems(loadData());
        profPicture.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        profPicture.setCellFactory(cellData -> {
            final ImageView imageView = new ImageView();
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            TableCell<UserFXModel, UserFXModel> cell = new TableCell<>() {
                public void updateItem(UserFXModel item, boolean empty) {
                    if (item != null) {
                        try {
                            imageView.setImage(loadImage(item.getProfilePicture()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            cell.setGraphic(imageView);
            return cell;
        });

        userData.setCellValueFactory(cellDataFeatures -> {
            String string = cellDataFeatures.getValue().getFirstName() + " " + cellDataFeatures.getValue().getLastName() + "\n" + cellDataFeatures.getValue().getEmail();
            return new SimpleStringProperty(string);
        });

        details.setCellFactory(param -> new TableCell<>() {
            final Button button = createButton();

            @Override
            protected void updateItem(UserFXModel item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setGraphic(button);
                    // TODO: show details
                    button.setOnAction(event -> System.out.println("I'm working"));
                } else {
                    setGraphic(null);
                }
            }
        });
    }

    private void initMessages() throws ApplicationException {
        DataAccessObject dao = new DataAccessObject();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Message> messages = dao.queryForAll(Message.class);
        messages.forEach(message -> {
            try {
                long daysBetween = ChronoUnit.DAYS.between(LocalDate.parse(message.getDate(), df), LocalDate.now());
                if (daysBetween < 30)
                    messagesVbox.getChildren().add(generateMessage(message));
                else
                    dao.deleteById(Message.class, message.getId());
            } catch (ApplicationException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    private Button createButton() {
        JFXButton button = new JFXButton();
        Image image = new Image(DashBoardController.ICON);
        ImageView imageView = new ImageView(image);
        button.setGraphic(imageView);
        return button;
    }

    private ObservableList<UserFXModel> loadData() throws ApplicationException {
        ObservableList<UserFXModel> observableArrayList = FXCollections.observableArrayList();
        observableArrayList.clear();
        DataAccessObject dao = new DataAccessObject();
        List<User> users = dao.queryForAll(User.class);
        users.forEach(user -> {
            if (user.getLibraryID() == LoginController.currentlyLoggedUser.getLibraryID() && user.getUserType().equals("EMPLOYEE")) {
                UserFXModel userFx = Converters.convertToUserFx(user);
                observableArrayList.add(userFx);
            }
        });
        return observableArrayList;
    }

    private Image loadImage(byte[] byte_array) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(byte_array);
        BufferedImage bImage2 = ImageIO.read(bis);
        return javafx.embed.swing.SwingFXUtils.toFXImage(bImage2, null);
    }

    private void generateProgressBar(String categoryName, String amount, double percents, String color) {
        VBox vBox = new VBox();

        HBox hBox = new HBox();
        hBox.setMinWidth(150.0);
        hBox.setSpacing(20.0);
        Label label1 = new Label();
        Label label2 = new Label();

        label1.setText(categoryName);
        label2.setText(amount + " (" + (int) (percents * 100) + "%)");

        ProgressBar processBar = new ProgressBar(percents);
        processBar.setMinWidth(300.0);
        processBar.setStyle("-fx-accent:" + color + ";");

        hBox.getChildren().addAll(label1, label2);
        vBox.getChildren().addAll(hBox, processBar);
        progressbarVbox.getChildren().add(vBox);
    }

    private Node generateMessage(Message message) throws ApplicationException, IOException {
        VBox vBox1 = new VBox();
        VBox vBox2 = new VBox();
        VBox vBox3 = new VBox();
        HBox hBox = new HBox();

        vBox1.setSpacing(20);
        vBox2.setSpacing(10);
        hBox.setSpacing(20);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        Label autor = new Label();
        Label email = new Label();
        Label title = new Label();
        Label date = new Label();
        Label details = new Label();

        DataAccessObject dao = new DataAccessObject();
        User user = dao.findById(User.class, message.getUserID());

        autor.setText(user.toString());
        email.setText(user.getEmail());
        imageView.setImage(loadImage(user.getProfilePicture()));
        title.setText(message.getTitle());
        date.setText(message.getDate() + "\n\n");
        details.setText(message.getDetails());

        title.setFont(new Font("Arial", 18));

        vBox3.getChildren().addAll(autor, email);
        hBox.getChildren().addAll(imageView, vBox3);
        vBox2.getChildren().addAll(title, date);
        vBox1.getChildren().addAll(hBox, vBox2, details);

        vBox1.setStyle("-fx-background-color: white;");
        vBox1.setStyle("-fx-border-color-color: lightgray;");
        return vBox1;
    }
}

