package assistant.Utils;

import assistant.UI.Controllers.LoginController;
import assistant.database.dao.DataAccessObject;
import assistant.database.models.Book;
import assistant.database.models.BorrowedBook;
import assistant.database.models.Report;
import assistant.database.models.User;

import java.sql.SQLException;
import java.time.LocalDate;

public class GenerateReport {
    public static void generateNewRaport() throws SQLException, ApplicationException {
        DataAccessObject dataAccessObject = new DataAccessObject();
        int libraryID = LoginController.currentlyLoggedUser.getLibraryID();
        LocalDate lastDay = LocalDate.now().withDayOfMonth(1).minusDays(1);
        int bookAmount = Integer.parseInt(dataAccessObject.countRecords(Book.class,"Select count(*) from books where library_id = " + libraryID));
        int userNumber = Integer.parseInt(dataAccessObject.countRecords(User.class, "select count(*) from users where library_id = " + libraryID + " and USER_TYPE = \"MEMBER\""));
        int womanNumber = Integer.parseInt(dataAccessObject.countRecords(User.class, "select count(*) from users where gender =\"K\" and library_id = " + libraryID + " and USER_TYPE = \"MEMBER\""));
        int bBooksAmount = Integer.parseInt(dataAccessObject.countRecords(BorrowedBook.class, "select count(*) from BORROWED_BOOKS where IS_RETURNED = false and LIBRARY_ID = " + libraryID + " and borrow_time >=\"" + LocalDate.now().withDayOfMonth(1).minusMonths(1)+"\""));
        int rBooksAmount = Integer.parseInt(dataAccessObject.countRecords(BorrowedBook.class, "select count(*) from BORROWED_BOOKS where IS_RETURNED = true and LIBRARY_ID = " + libraryID + " and return_time >=\"" + LocalDate.now().withDayOfMonth(1).minusMonths(1)+"\""));

        System.out.println(bookAmount);
        Report report = new Report();
        report.setLibraryID(libraryID);
        report.setDate(lastDay.toString());
        report.setBooksAmount(bookAmount);
        report.setUsersNumber(userNumber);
        report.setWomanNumber(womanNumber);
        report.setbBooksAmount(bBooksAmount);
        report.setrBooksAmount(rBooksAmount);
        dataAccessObject.createOrUpdate(report);
    }
}
