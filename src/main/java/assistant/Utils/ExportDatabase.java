package assistant.Utils;

import assistant.database.dao.DataAccessObject;
import assistant.database.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ExportDatabase {
    private static final String CITIES_SOURCE = "src/main/resources/dataInputFiles/cities.csv";
    private static final String CATEGORIES_SOURCE = "src/main/resources/dataInputFiles/categories.csv";
    private static final String PUBLISHERS_SOURCE = "src/main/resources/dataInputFiles/publishing_companies.csv";
    private static final String AUTHORS_SOURCE = "src/main/resources/dataInputFiles/authors.csv";
    private static final String LIBRARIES_SOURCE = "src/main/resources/dataInputFiles/libraries.csv";
    private static final String USERS_SOURCE = "src/main/resources/dataInputFiles/users.csv";
    private static final String BOOKS_SOURCE = "src/main/resources/dataInputFiles/books.csv";
    private static final String BORROWED_BOOKS_SOURCE = "src/main/resources/dataInputFiles/borrowed_books.csv";
    private static final String REPORTS_SOURCE = "src/main/resources/dataInputFiles/reports.csv";

    public static void exportDatabase() {

        exportCitiesToCSV();
        exportCategoriesToCSV();
        exportPublishersToCSV();
        exportAuthorsToCSV();
        exportLibrariesToCSV();
        exportUsersToCSV();
        exportBooksToCSV();
        exportBorrowedBooksToCSV();
        exportReportsToCSV();
    }

    private static void exportReportsToCSV() {
        DataAccessObject dataAccessObject = new DataAccessObject();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(REPORTS_SOURCE);

            List<Report> list = dataAccessObject.queryForAll(Report.class);
            // write header line containing column names
            String header = "ID;LIBRARY_ID;DATE;BOOKS_AMOUNT;BORROWED_BOOKS_AMOUNT;RETURNED_BOOKS_AMOUNT;USER_NUMBER;WOMAN_NUMBER\n";
            fileOutputStream.write(header.getBytes(StandardCharsets.UTF_8));
            list.forEach(element -> {
                String line = String.format("%d;%d;%s;%s;%d;%d;%d;%d",
                        element.getId(), element.getLibraryID(), element.getDate(), element.getBooksAmount(), element.getbBooksAmount(),
                        element.getrBooksAmount(), element.getUsersNumber(), element.getWomanNumber());
                try {
                    fileOutputStream.write(line.getBytes(StandardCharsets.UTF_8));
                    fileOutputStream.write("\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileOutputStream.close();
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }

    private static void exportBorrowedBooksToCSV() {
        DataAccessObject dataAccessObject = new DataAccessObject();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(BORROWED_BOOKS_SOURCE);

            List<BorrowedBook> list = dataAccessObject.queryForAll(BorrowedBook.class);
            System.out.println(list.size());
            // write header line containing column names
            String header = "ID;BORROW_TIME;RETURN_TIME;IS_RETURNED;BOOK_ID;USER_ID;LIBRARY_ID\n";
            fileOutputStream.write(header.getBytes(StandardCharsets.UTF_8));
            list.forEach(element -> {
                String line = String.format("%d;%s;%s;%s;%d;%d;%d",
                        element.getId(), element.getBorrowTime(), element.getReturnTime(),
                        element.getReturned(), element.getBookID(), element.getUserID(), element.getLibraryID());
                try {
                    fileOutputStream.write(line.getBytes(StandardCharsets.UTF_8));
                    fileOutputStream.write("\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileOutputStream.close();
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }

    }

    private static void exportBooksToCSV() {
        DataAccessObject dataAccessObject = new DataAccessObject();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(BOOKS_SOURCE);

            List<Book> list = dataAccessObject.queryForAll(Book.class);
            // write header line containing column names
            String header = "ID;ISBN-01;ISBN-13;TITLE;ADDED_DATE;DESCRIPTION;PUBLICATION_DATE;AVAILABILITY;AUTHOR_ID;CATEGORY_ID;PUBLISHER_ID;LIBRARY_ID;BOOK_COVER\n";
            fileOutputStream.write(header.getBytes(StandardCharsets.UTF_8));
            list.forEach(element -> {
                String line = element.getId() + ";" + element.getIsbn10() + ";" + element.getIsbn13() + ";" + element.getTitle() + ";" +
                        element.getAddedDate() + ";" + element.getDescription() + ";" + element.getPublicationDate() + ";" + element.getAvailability() + ";" +
                        element.getAuthor() + ";" + element.getCategory() + ";" + element.getPublishingCompany() + ";" + element.getLibrary() + ";";
                try {
                    fileOutputStream.write(line.getBytes(StandardCharsets.UTF_8));
//                    if (element.getBookCover() != null) {
//                        fileOutputStream.write(element.getBookCover());
//                    }
                    fileOutputStream.write("\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileOutputStream.close();
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }

    private static void exportUsersToCSV() {
        DataAccessObject dataAccessObject = new DataAccessObject();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(USERS_SOURCE);

            List<User> list = dataAccessObject.queryForAll(User.class);
            // write header line containing column names
            String header = "ID;USERNAME;PASSWORD;FIRST_NAME;LAST_NAME;GENDER;MOBILE;EMAIL;REGISTRATION_DATE;STREET;ZIP_CODE;USER_TYPE;LIBRARY_ID\n";
            fileOutputStream.write(header.getBytes(StandardCharsets.UTF_8));
            list.forEach(element -> {
                // TODO: add profile picture
                String line = String.format("%d;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%d;",
                        element.getId(), element.getUsername(), element.getPassword(), element.getFirstName(),
                        element.getLastName(), element.getGender(), element.getMobile(), element.getEmail(),
                        element.getRegistrationDate(), element.getStreet(), element.getZipCode(), element.getUserType(),
                        element.getLibraryID());
                try {
                    fileOutputStream.write(line.getBytes(StandardCharsets.UTF_8));
                    fileOutputStream.write("\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileOutputStream.close();
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }

    private static void exportAuthorsToCSV() {
        DataAccessObject dataAccessObject = new DataAccessObject();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(AUTHORS_SOURCE);

            List<Author> list = dataAccessObject.queryForAll(Author.class);
            // write header line containing column names
            String header = "ID;FIRST_NAME;MIDDLE_NAME;LAST_NAME\n";
            fileOutputStream.write(header.getBytes(StandardCharsets.UTF_8));
            list.forEach(element -> {
                String line = String.format("%d;%s;%s;%s",
                        element.getId(), element.getFistName(), element.getMiddleName(), element.getLastName());
                try {
                    fileOutputStream.write(line.getBytes(StandardCharsets.UTF_8));
                    fileOutputStream.write("\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileOutputStream.close();
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }

    private static void exportPublishersToCSV() {
        DataAccessObject dataAccessObject = new DataAccessObject();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(PUBLISHERS_SOURCE);

            List<PublishingCompany> list = dataAccessObject.queryForAll(PublishingCompany.class);
            // write header line containing column names
            String header = "ID;NAME\n";
            fileOutputStream.write(header.getBytes(StandardCharsets.UTF_8));
            list.forEach(element -> {
                String line = String.format("%d;%s", element.getId(), element.getName());
                try {
                    fileOutputStream.write(line.getBytes(StandardCharsets.UTF_8));
                    fileOutputStream.write("\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileOutputStream.close();
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }

    private static void exportCitiesToCSV() {
        DataAccessObject dataAccessObject = new DataAccessObject();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(CITIES_SOURCE);

            List<City> list = dataAccessObject.queryForAll(City.class);
            // write header line containing column names
            String header = "ID;NAME\n";
            fileOutputStream.write(header.getBytes(StandardCharsets.UTF_8));
            list.forEach(element -> {
                String line = String.format("%d;%s", element.getId(), element.getName());
                try {
                    fileOutputStream.write(line.getBytes(StandardCharsets.UTF_8));
                    fileOutputStream.write("\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileOutputStream.close();
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }

    private static void exportCategoriesToCSV() {
        DataAccessObject dataAccessObject = new DataAccessObject();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(CATEGORIES_SOURCE);

            List<Category> list = dataAccessObject.queryForAll(Category.class);
            // write header line containing column names
            String header = "ID;CATEGORY_NAME\n";
            fileOutputStream.write(header.getBytes(StandardCharsets.UTF_8));
            list.forEach(element -> {
                String line = String.format("%d;%s", element.getId(), element.getName());
                try {
                    fileOutputStream.write(line.getBytes(StandardCharsets.UTF_8));
                    fileOutputStream.write("\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileOutputStream.close();
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }

    private static void exportLibrariesToCSV() {
        DataAccessObject dataAccessObject = new DataAccessObject();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(LIBRARIES_SOURCE);

            List<Library> list = dataAccessObject.queryForAll(Library.class);
            // write header line containing column names
            String header = "ID;NAME;STREET;ZIP_CODE;CITY_ID\n";
            fileOutputStream.write(header.getBytes(StandardCharsets.UTF_8));
            list.forEach(element -> {
                String line = String.format("%d;%s;%s;%s;%d",
                        element.getId(), element.getName(), element.getStreet(), element.getZipCode(), element.getCityID());
                try {
                    fileOutputStream.write(line.getBytes(StandardCharsets.UTF_8));
                    fileOutputStream.write("\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileOutputStream.close();
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }
}
