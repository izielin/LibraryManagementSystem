package assistant.UI.Controllers;

import assistant.Utils.DonutChart;
import assistant.Utils.ApplicationException;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DashBoardController {
    public Label libraryName;
    public Label userAmount;
    public Label bookAmount;
    public Label borrowedAmount;
    public Label categoryAmount;
    public Label newUserToday;
    public LineChart<?, ?> lineChart;
    public Label newBookToday;
    public Label borrowedToday;
    public Label submittedToday;
    public AnchorPane donutChartPane;
    public BarChart<?, ?> barChart;
    public AnchorPane pieChartPane;
    public CategoryAxis xAxisLineChart;
    public NumberAxis yAxisLineChart;

    DataAccessObject dataAccessObject = new DataAccessObject();


    public void initialize() {
        initCharts();
    }

    private void initCharts() {
        libraryName.setText(LoginController.currentlyLoggedUser.getLibrary().getName());
        try {
            userAmount.setText(dataAccessObject.countRecords(User.class, "select count(*) from users where user_type='MEMBER' and library_id=" + LoginController.currentlyLoggedUser.getLibrary().getId()));
            bookAmount.setText(dataAccessObject.countRecords(User.class, "select count(*) from books where library_id=" + LoginController.currentlyLoggedUser.getLibrary().getId()));
            borrowedAmount.setText(dataAccessObject.countRecords(User.class, "select count(*) from borrowed_books where library_id=" + LoginController.currentlyLoggedUser.getLibrary().getId()));
            categoryAmount.setText(dataAccessObject.countRecords(User.class, "select count(*) from categories"));

            newUserToday.setText(dataAccessObject.countRecords(User.class, "select count(*) from users where user_type='MEMBER' and library_id = " + LoginController.currentlyLoggedUser.getLibrary().getId() + " and registration_date = '" + LocalDate.now() + "'"));
            newBookToday.setText(dataAccessObject.countRecords(User.class, "select count(*) from books where library_id = " + LoginController.currentlyLoggedUser.getLibrary().getId() + " and added_date = '" + LocalDate.now() + "'"));
            submittedToday.setText(dataAccessObject.countRecords(User.class, "select count(*) from books where library_id = " + LoginController.currentlyLoggedUser.getLibrary().getId() + " and last_submission = '" + LocalDate.now() + "'"));
            borrowedToday.setText(dataAccessObject.countRecords(User.class, "select count(*) from borrowed_books where library_id = " + LoginController.currentlyLoggedUser.getLibrary().getId() + " and borrow_time = '" + LocalDate.now() + "'"));


            // line chart
            xAxisLineChart.setLabel("Registration date");

            XYChart.Series series = new XYChart.Series();
            series.setName("No. of new users");
            String query = "select registration_date, count(*) from users " +
                    " where library_id = " + LoginController.currentlyLoggedUser.getLibrary().getId() +
                    " group by registration_date";
            List<String[]> usersList = dataAccessObject.executeRawQuery(User.class, query);

            for (String[] element : usersList) {
                series.getData().add(new XYChart.Data(element[0], Integer.parseInt(element[1])));
            }

            lineChart.getData().add(series);

            // donut chart

            ObservableList<PieChart.Data> donutChartData = FXCollections.observableArrayList();
            String queryBookByCategory = "SELECT CATEGORIES.NAME , COUNT(*) FROM BOOKS " +
                    "INNER JOIN CATEGORIES ON BOOKS.CATEGORY_ID = CATEGORIES.ID " +
                    "WHERE library_id = " + LoginController.currentlyLoggedUser.getLibrary().getId() +
                    " GROUP BY CATEGORIES.NAME";
            try {
                List<String[]> categoryList = dataAccessObject.executeRawQuery(User.class, queryBookByCategory);
                categoryList.forEach(category -> donutChartData.add(new PieChart.Data(category[0] + "(" + category[1] + ")", Integer.parseInt(category[1]))));


            } catch (ApplicationException | SQLException e) {
                e.printStackTrace();
            }
            DonutChart donutChart = new DonutChart(donutChartData);
            donutChart.setTitle("Amount of books broken down into categories");
            donutChartPane.getChildren().add(donutChart);

            // pie chart
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            String queryBookByAvailability = "SELECT availability, COUNT(*) FROM BOOKS " +
                    " WHERE library_id = " + LoginController.currentlyLoggedUser.getLibrary().getId()
                    + " GROUP BY availability";
            try {
                List<String[]> statusList = dataAccessObject.executeRawQuery(User.class, queryBookByAvailability);
                statusList.forEach(availabilityStatus -> {
                    String status = (availabilityStatus[0].equals("1")) ? "Available Books" : "Borrowed Books";
                    pieChartData.add(new PieChart.Data(status, Integer.parseInt(availabilityStatus[1])));
                });


            } catch (ApplicationException | SQLException e) {
                e.printStackTrace();
            }

            pieChartPane.getChildren().add(new PieChart(pieChartData));

            // bar chart
            // top 5 authors by borrowed books
            // top 3 category for each of authors

        } catch (ApplicationException | SQLException e) {
            e.printStackTrace();
        }
    }
}
