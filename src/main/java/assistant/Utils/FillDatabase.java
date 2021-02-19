package assistant.Utils;

import assistant.database.dao.DataAccessObject;
import assistant.database.models.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FillDatabase {
    private static final String CITIES_SOURCE = "src/main/resources/dataInputFiles/cities.csv";
    private static final String CATEGORIES_SOURCE = "src/main/resources/dataInputFiles/categories.csv";
    private static final String PUBLISHERS_SOURCE = "src/main/resources/dataInputFiles/publishing_companies.csv";
    private static final String AUTHORS_SOURCE = "src/main/resources/dataInputFiles/authors.csv";
    private static final String LIBRARIES_SOURCE = "src/main/resources/dataInputFiles/libraries.csv";
    private static final String USERS_SOURCE = "src/main/resources/dataInputFiles/users.csv";
    private static final String BOOKS_SOURCE = "src/main/resources/dataInputFiles/books.csv";
    private static final String BORROWED_BOOKS_SOURCE = "src/main/resources/dataInputFiles/borrowed_books.csv";

    public static void fillDatabase() {

        readCitiesFromCSV();
        readCategoriesFromCSV();
        readPublishersFromCSV();
        readAuthorsFromCSV();
        readLibrariesFromCSV();
        readBooksFromCSV();
        readUsersFromCSV();
        readBorrowedBooksFromCSV();
    }

    private static void readCitiesFromCSV() {
        Path pathToFile = Paths.get(CITIES_SOURCE);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            String line = br.readLine();
            while (line != null) {
                DataAccessObject dao = new DataAccessObject();

                String[] attributes = line.split(",");
                City city = new City();
                city.setId(Integer.parseInt(attributes[0]));
                city.setName(attributes[1]);
                dao.createOrUpdate(city);

                line = br.readLine();
            }
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }

    private static void readCategoriesFromCSV() {
        Path pathToFile = Paths.get(CATEGORIES_SOURCE);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            String line = br.readLine();
            while (line != null) {
                DataAccessObject dao = new DataAccessObject();

                String[] attributes = line.split(",");
                Category category = new Category();
                category.setId(Integer.parseInt(attributes[0]));
                category.setName(attributes[1]);
                dao.createOrUpdate(category);

                line = br.readLine();
            }
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }

    private static void readPublishersFromCSV() {
        Path pathToFile = Paths.get(PUBLISHERS_SOURCE);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            String line = br.readLine();
            while (line != null) {
                DataAccessObject dao = new DataAccessObject();

                String[] attributes = line.split(",");
                PublishingCompany publishingCompany = new PublishingCompany();
                publishingCompany.setId(Integer.parseInt(attributes[0]));
                publishingCompany.setName(attributes[1]);
                dao.createOrUpdate(publishingCompany);

                line = br.readLine();
            }
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }

    private static void readAuthorsFromCSV() {
        Path pathToFile = Paths.get(AUTHORS_SOURCE);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            String line = br.readLine();
            while (line != null) {
                DataAccessObject dao = new DataAccessObject();

                String[] attributes = line.split(",");
                Author author = new Author();
                author.setId(Integer.parseInt(attributes[0]));
                author.setFistName(attributes[1]);
                author.setMiddleName(attributes[2]);
                author.setLastName(attributes[3]);
                dao.createOrUpdate(author);

                line = br.readLine();
            }
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }

    private static void readLibrariesFromCSV() {
        Path pathToFile = Paths.get(LIBRARIES_SOURCE);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            String line = br.readLine();
            while (line != null) {
                DataAccessObject dao = new DataAccessObject();

                String[] attributes = line.split(",");
                Library library = new Library();
                library.setId(Integer.parseInt(attributes[0]));
                library.setName(attributes[1]);
                library.setStreet(attributes[2]);
                library.setZipCode(attributes[3]);
                library.setCity(dao.findById(City.class, Integer.parseInt(attributes[4])));
                dao.createOrUpdate(library);

                line = br.readLine();
            }
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }

    private static void readBooksFromCSV() {
        Path pathToFile = Paths.get(BOOKS_SOURCE);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            String line = br.readLine();
            while (line != null) {
                DataAccessObject dao = new DataAccessObject();

                System.out.println();
                System.out.println();

                String[] attributes = line.split(";");
                System.out.println(Arrays.toString(attributes));
                Book book = new Book();
                book.setId(Integer.parseInt(attributes[0]));
                book.setIsbn10(attributes[1]);
                book.setIsbn13(attributes[2]);
                book.setTitle(attributes[3]);
                book.setAddedDate(attributes[4]);
                book.setLastSubmission(attributes[5]);
                book.setDescription(attributes[6]);
                book.setPublicationDate(attributes[7]);
                book.setAvailability(Boolean.valueOf(attributes[9]));
                book.setAuthor(dao.findById(Author.class, Integer.parseInt(attributes[10])));
                book.setCategory(dao.findById(Category.class, Integer.parseInt(attributes[11])));
                book.setPublishingCompany(dao.findById(PublishingCompany.class, Integer.parseInt(attributes[12])));
                book.setLibrary(dao.findById(Library.class, Integer.parseInt(attributes[13])));


                dao.createOrUpdate(book);

                line = br.readLine();
            }
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }

    private static void readUsersFromCSV() {
        Path pathToFile = Paths.get(USERS_SOURCE);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            String line = br.readLine();
            while (line != null) {
                DataAccessObject dao = new DataAccessObject();

                String[] attributes = line.split(",");
                User user = new User();
                user.setId(Integer.parseInt(attributes[0]));
                user.setUsername(attributes[1]);
                user.setPassword(DigestUtils.sha1Hex(attributes[2]));
                user.setFirstName(attributes[3]);
                user.setLastName(attributes[4]);
                user.setMobile(attributes[5]);
                user.setEmail(attributes[6]);
                user.setRegistrationDate(attributes[7]);
                user.setStreet(attributes[8]);
                user.setZipCode(attributes[9]);
                user.setUserType(attributes[10]);
                user.setLibrary(dao.findById(Library.class, Integer.parseInt(attributes[11])));
                dao.createOrUpdate(user);

                line = br.readLine();
            }
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }

    private static void readBorrowedBooksFromCSV() {
        Path pathToFile = Paths.get(BORROWED_BOOKS_SOURCE);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            String line = br.readLine();
            System.out.println();
            System.out.println();
            System.out.println(line);
            while (line != null) {
                DataAccessObject dao = new DataAccessObject();

                String[] attributes = line.split(",");

                BorrowedBook borrowedBook = new BorrowedBook();
                borrowedBook.setId(Integer.parseInt(attributes[0]));
                borrowedBook.setBorrowTime(attributes[1]);
                borrowedBook.setNumberOfRenewals(Integer.parseInt(attributes[2]));
                borrowedBook.setReturned(Boolean.valueOf(attributes[3]));
                borrowedBook.setBook(dao.findById(Book.class, Integer.parseInt(attributes[4])));
                borrowedBook.setUser(dao.findById(User.class, Integer.parseInt(attributes[5])));
                borrowedBook.setLibrary(dao.findById(Library.class, Integer.parseInt(attributes[6])));
                dao.createOrUpdate(borrowedBook);

                line = br.readLine();
            }
        } catch (IOException | ApplicationException e) {
            e.printStackTrace();
        }
    }
}
